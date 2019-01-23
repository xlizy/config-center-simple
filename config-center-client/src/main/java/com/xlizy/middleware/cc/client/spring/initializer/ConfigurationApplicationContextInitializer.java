package com.xlizy.middleware.cc.client.spring.initializer;

import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.client.common.ClientHelper;
import com.xlizy.middleware.cc.client.common.HeartbeatHandler;
import com.xlizy.middleware.cc.client.common.ThreadPools;
import com.xlizy.middleware.cc.client.core.ConfigCenter;
import com.xlizy.middleware.cc.client.netty.NettyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * spring初始化回调类
 * @author xlizy
 * @date 2018/5/9
 */
@Order(value = Integer.MIN_VALUE)
public class ConfigurationApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationApplicationContextInitializer.class);
    private static final String CONFIG_CENTER_PROPERTIES_NAME = "ConfigCenterProperties";

    /**
     * 这接口是在spring容器刷新之前执行的一个回调函数
     * */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        logger.debug("调用了ConfigurationApplicationContextInitializer.initialize方法,准备加载配置中心参数...");
        //首次连接配置中心获取配置
        Properties properties = firstGetConfigFormConfigCenter();

        //如果properties对象是空表示首次从config-center-server获取配置失败了,将尝试从本地缓存中获取
        if(properties.isEmpty()){
            logger.debug("启动时从config-center-server获取配置失败,开始试图从本地缓存中获取");
            properties = loadPropertiesByLocalCache();
            try {
                //为保证ConfigCenter只对用户暴露获取相关的方法，这里通过反射调用load方法刷新配置
                Method reloadProperties = ConfigCenter.class.getDeclaredMethod("reloadProperties",Properties.class);
                //类中的方法为private,故必须进行此操作
                reloadProperties.setAccessible(true);
                //调用
                reloadProperties.invoke(Properties.class,properties);
            } catch (Exception e) {
                logger.warn("调用ConfigCenter.reloadProperties方法失败,msg:{}",e.getMessage());
            }
        }
        //将配置装配到spring容器中
        PropertiesPropertySource propertySource = new PropertiesPropertySource(CONFIG_CENTER_PROPERTIES_NAME, properties);
        applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);

        //初始化容器配置后，建立个长连接监听配置文件的变化
        //获取个netty客户端实例
        NettyClient nettyClient = NettyClient.getInstance();
        //启动netty客户端
        nettyClient.start();
    }

    /**
     * 从本地缓存中加载配置
     * */
    public Properties loadPropertiesByLocalCache(){
        Properties properties = new Properties();
        //获取ClientHelper帮助类
        ClientHelper clientHelper = ClientHelper.getInstance();
        String host = clientHelper.getHost();
        int port = clientHelper.getPort();

        //本地缓存文件的文件全路径
        //用户主目录
        String localCacheFilePath = System.getProperty("user.home");
        //应用名称
        String app = System.getProperty("app.name");
        //环境
        String env = System.getProperty("app.env","dev");
        //版本
        String version = System.getProperty("app.version","1.0.0");
        //集群/机房
        String cluster = System.getProperty("app.cluster","default");
        if(ClientHelper.isOSWindows()){
            localCacheFilePath += "\\.configcenter\\" + host + "_" + port + "_" + app + "_" + env + "_" + version + "_" + cluster + ".cache";
        }else{
            localCacheFilePath += "/.configcenter/" + host + "_" + port + "_" + app + "_" + env + "_" + version + "_" + cluster + ".cache";
        }
        File file = new File(localCacheFilePath);

        if(file.exists()){
            FileInputStream fis = null;
            InputStreamReader isr = null;
            try {
                fis = new FileInputStream(file);
                isr = new InputStreamReader(fis,"UTF-8");
                //将文件中的信息加载到properties对象里
                properties.load(isr);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(isr != null){
                    try {
                        isr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(fis != null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return properties;
    }

    /**
     * 首次连接配置中心获取配置
     * */
    public Properties firstGetConfigFormConfigCenter(){
        //线程池
        ExecutorService exec = ThreadPools.configCenterPool;
        //配置中心host
        final String host = ClientHelper.getInstance().getHost();
        //配置中心port
        final int port = ClientHelper.getInstance().getPort();
        try {
            Future<String> future = exec.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return new Client().init(host, port).listen();
                }
            });
            // 任务处理超时时间设为 5 秒
            String res = future.get(7, TimeUnit.SECONDS);

            //为保证ConfigCenter只对用户暴露获取相关的方法，这里通过反射调用load方法刷新配置
            Method reloadProperties = ConfigCenter.class.getDeclaredMethod("reloadProperties",JSONObject.class);
            //类中的方法为private,故必须进行此操作
            reloadProperties.setAccessible(true);
            JSONObject prop = JSONObject.parseObject(res);
            //调用
            reloadProperties.invoke(JSONObject.class,prop);

            //将配置文件重新写入本地缓存文件中
            ClientHelper.getInstance().savePropertiesToLocalCache(ConfigCenter.getProperties());
        } catch (TimeoutException e) {
            logger.info("首次连接config-center-server获取配置超时,msg={}",e.getMessage());
            return new Properties();
        } catch (Exception e) {
            logger.info("首次连接config-center-server获取配置发生异常,msg={}",e.getMessage());
            return new Properties();
        }
        return ConfigCenter.getProperties();
    }

    /**
     * 首次连接用的客户端
     * */
    class Client{
        /** 管道管理器 */
        private Selector selector;

        public Client init(String serverIp, int port) throws IOException{
            //获取socket通道
            SocketChannel channel = SocketChannel.open();

            channel.configureBlocking(false);
            //获得通道管理器
            selector=Selector.open();

            //客户端连接服务器，需要调用channel.finishConnect();才能实际完成连接。
            channel.connect(new InetSocketAddress(serverIp, port));
            //为该通道注册SelectionKey.OP_CONNECT事件
            channel.register(selector, SelectionKey.OP_CONNECT);
            return this;
        }

        public String listen() throws IOException {
            JSONObject request = new JSONObject();
            request.put("app",System.getProperty("app.name"));
            request.put("env",System.getProperty("app.env","dev"));
            request.put("version",System.getProperty("app.version","1.0.0"));
            request.put("cluster",System.getProperty("app.cluster","default"));

            while(true){
                //选择注册过的io操作的事件(第一次为SelectionKey.OP_CONNECT)
                selector.select();
                Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
                while(ite.hasNext()){
                    SelectionKey key = ite.next();
                    //删除已选的key，防止重复处理
                    ite.remove();
                    if(key.isConnectable()){
                        SocketChannel channel=(SocketChannel)key.channel();

                        //如果正在连接，则完成连接
                        if(channel.isConnectionPending()){
                            channel.finishConnect();
                        }

                        channel.configureBlocking(false);
                        //向服务器发送消息
                        byte[] body = request.toJSONString().getBytes();
                        int bodyLength = body.length;
                        int totalLength = 5 + bodyLength;
                        byte[] data = new byte[totalLength];

                        data[0] = (byte) ((totalLength >> 24) & 0xFF);
                        data[1] = (byte) ((totalLength >> 16) & 0xFF);
                        data[2] = (byte) ((totalLength >> 8) & 0xFF);
                        data[3] = (byte) (totalLength & 0xFF);
                        data[4] = HeartbeatHandler.DATA_MSG;
                        for (int i = 5; i < data.length; i++) {
                            data[i] = body[i-5];
                        }
                        channel.write(ByteBuffer.wrap(data));

                        //连接成功后，注册接收服务器消息的事件
                        channel.register(selector, SelectionKey.OP_READ);
                        logger.debug("首次连接config-center-server成功");
                        //有可读数据事件。
                    }else if(key.isReadable()){
                        SocketChannel channel = (SocketChannel)key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(0x200000);
                        channel.read(buffer);
                        byte[] data = buffer.array();
                        String msg = new String(data,5,data.length - 5);
                        logger.debug("首次连接config-center-server,并接受到返回值:{}",msg);

                        channel.close();
                        return msg;
                    }
                }
            }
        }
    }
}
