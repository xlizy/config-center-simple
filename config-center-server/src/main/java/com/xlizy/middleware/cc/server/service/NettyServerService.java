package com.xlizy.middleware.cc.server.service;

import com.xlizy.middleware.cc.server.enums.SendConfigType;

/**
 * Netty相关服务接口
 * @author xlizy
 * @date 2018/5/28
 */
public interface NettyServerService {

    /**
     * 发送配置
     * @param app 应用name
     * @param env 环境name
     * @param version 环境版本
     * @param cluster 集群/机房
     * @param cluster 集群/机房
     * @param type 配置推送类型
     * */
    void sendConfig(String app, String env, String version, String cluster, SendConfigType type);

}
