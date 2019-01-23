package com.xlizy.middleware.cc.server.scheduled;

import com.alibaba.fastjson.JSONObject;
import com.xlizy.middleware.cc.client.common.HeartbeatHandler;
import com.xlizy.middleware.cc.server.common.utils.SpringContextUtil;
import com.xlizy.middleware.cc.server.entity.CcApp;
import com.xlizy.middleware.cc.server.entity.CcEnv;
import com.xlizy.middleware.cc.server.entity.CcProperties;
import com.xlizy.middleware.cc.server.enums.Enable;
import com.xlizy.middleware.cc.server.enums.SendConfigType;
import com.xlizy.middleware.cc.server.netty.NettyServer;
import com.xlizy.middleware.cc.server.service.AppService;
import com.xlizy.middleware.cc.server.service.EnvService;
import com.xlizy.middleware.cc.server.service.PropertiesService;
import com.xlizy.middleware.cc.server.service.PushPropertiesLogService;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @author xlizy
 * @date 2018/6/4
 */
@Component
@Slf4j
public class SendConfigScheduled {

    @Autowired
    ExecutorService publicUsePool;

    @Autowired
    AppService appService;

    @Autowired
    EnvService envService;

    @Autowired
    PropertiesService propertiesService;

    @Scheduled(cron = "0 0 */1 * * ?")
    //@Scheduled(cron = "0 * * * * ?")
    public void sendConfig() {
        log.info("定时给当前节点维护的客户端推送配置");
        NettyServer.allServerSocketChannel.keySet().forEach(k -> {
            String[] key = k.split("@");
            String appName = key[0];
            String envName = key[1];
            String version = key[2];
            String cluster = key[3];

            CcApp app = appService.getApp(appName);
            CcEnv env = envService.getEnv(app.getId(),envName,version,cluster);
            List<CcProperties> propertiesList = propertiesService.getPropertiess(env.getId());

            JSONObject prop = new JSONObject();
            propertiesList
                    .stream()
                    .filter(p -> Enable.YES.equals(p.getEnable()))
                    .collect(Collectors.toList())
                    .forEach(p -> prop.put(p.getKey(),p.getValue()));

            if(prop.keySet().size() > 0){
                NettyServer.allServerSocketChannel.get(k).forEach(c -> {

                    //将配置推送记录到log表里
                    publicUsePool.execute(() -> SpringContextUtil.getBean(PushPropertiesLogService.class).addPushPropertiesLog(appName,envName,version,cluster,c.remoteAddress().toString(),SendConfigType.SCHEDULED) );

                    //往通道写数据
                    ByteBuf buf = c.alloc().buffer(5 + prop.toJSONString().getBytes().length);
                    buf.writeInt(5 + prop.toJSONString().getBytes().length);
                    buf.writeByte(HeartbeatHandler.DATA_MSG);
                    buf.writeBytes(prop.toJSONString().getBytes());
                    c.writeAndFlush(buf);
                });
            }

        });
    }


}
