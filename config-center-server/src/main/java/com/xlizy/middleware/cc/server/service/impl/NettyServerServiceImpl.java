package com.xlizy.middleware.cc.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.client.common.HeartbeatHandler;
import com.xlizy.middleware.cc.server.common.base.ThreadPools;
import com.xlizy.middleware.cc.server.common.utils.SpringContextUtil;
import com.xlizy.middleware.cc.server.entity.CcApp;
import com.xlizy.middleware.cc.server.entity.CcEnv;
import com.xlizy.middleware.cc.server.entity.CcProperties;
import com.xlizy.middleware.cc.server.enums.Enable;
import com.xlizy.middleware.cc.server.enums.SendConfigType;
import com.xlizy.middleware.cc.server.netty.NettyServer;
import com.xlizy.middleware.cc.server.service.AppService;
import com.xlizy.middleware.cc.server.service.EnvService;
import com.xlizy.middleware.cc.server.service.NettyServerService;
import com.xlizy.middleware.cc.server.service.PropertiesService;
import com.xlizy.middleware.cc.server.service.PushPropertiesLogService;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Netty相关服务接口实现
 * @author xlizy
 * @date 2018/5/28
 */
@Service
@Slf4j
public class NettyServerServiceImpl implements NettyServerService {

    @Autowired
    AppService appService;

    @Autowired
    EnvService envService;

    @Autowired
    PropertiesService propertiesService;

    @Override
    public void sendConfig(String _app, String _env, String _version, String _cluster, SendConfigType type) {

        StringBuilder key = new StringBuilder();
        key.append(_app).append("@")
                .append(_env).append("@")
                .append(_version).append("@")
                .append(_cluster);

        String _key = key.toString();

        log.info("试图向{}推送配置信息,查找本节点是否跟它建立了通道",_key);
        if(NettyServer.allServerSocketChannel.get(_key).size() > 0){
            log.info("{}与本节点建立了{}个通道",_key,NettyServer.allServerSocketChannel.get(_key).size());
            CcApp app = appService.getApp(_app);
            log.info("获取对应的App:{}",app);

            JSONObject prop = new JSONObject();

            if(app != null){
                CcEnv env = envService.getEnv(app.getId(),_env,_version,_cluster);
                log.info("获取对应的Env:{}",env);
                if(env != null){
                    List<CcProperties> list =  propertiesService.getPropertiess(env.getId());
                    log.info("获取对应的Properties:{}",list);

                    list.stream().filter(p -> Enable.YES.equals(p.getEnable())).forEach(p -> prop.put(p.getKey(),p.getValue()) );

                    log.info("将对应的配置响应给客户端,response:{}",prop);
                }else{
                    log.info("没有找到对应的Env信息");
                }
            }else{
                log.info("没有找到对应的App信息");
            }

            NettyServer.allServerSocketChannel.get(_key).forEach(c -> {

                //将配置推送记录到log表里
                ThreadPools.publicUsePool.submit(() -> SpringContextUtil.getBean(PushPropertiesLogService.class).addPushPropertiesLog(_app,_env,_version,_cluster,c.remoteAddress().toString(),type) );

                //往通道写数据
                log.info("发送给:{}",c);
                ByteBuf buf = c.alloc().buffer(5 + prop.toJSONString().getBytes().length);
                buf.writeInt(5 + prop.toJSONString().getBytes().length);
                buf.writeByte(HeartbeatHandler.DATA_MSG);
                buf.writeBytes(prop.toJSONString().getBytes());
                c.writeAndFlush(buf);
            });
        }else{
            log.info("{}没有与本节点建立起通道!",_key);
        }
    }

}
