package com.xlizy.middleware.cc.server.config;

import com.xlizy.middleware.cc.server.netty.NettyServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 配置中心TCP服务启动类
 * @author xlizy
 * @date 2018/5/28
 */
@Component
public class ConfigCenterStarter {

    @Value("${xlizy.config-center.server.netty-port}")
    int port;

    @PostConstruct
    public void start(){
        new NettyServer(port);
    }

    @PreDestroy
    public void destroy(){
        NettyServer.allServerSocketChannel.keySet().forEach(k -> {
            NettyServer.allServerSocketChannel.get(k).forEach(c -> {
                c.close();
            });
        });
    }
}
