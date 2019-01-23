package com.xlizy.middleware.cc.server.service;

import com.xlizy.middleware.cc.server.enums.SendConfigType;

/**
 * @author xlizy
 * @date 2018/6/7
 */
public interface PushPropertiesLogService {

    void addPushPropertiesLog(String app, String env, String version, String cluster, String clientAddress, SendConfigType type);

}
