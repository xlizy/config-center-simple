package com.xlizy.middleware.cc.server.service.impl;

import com.xlizy.middleware.cc.server.entity.CcPushPropertiesLog;
import com.xlizy.middleware.cc.server.enums.SendConfigType;
import com.xlizy.middleware.cc.server.mapper.CcPushPropertiesLogMapper;
import com.xlizy.middleware.cc.server.service.PushPropertiesLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xlizy
 * @date 2018/6/7
 */
@Service
public class PushPropertiesLogServiceImpl implements PushPropertiesLogService {

    @Autowired
    CcPushPropertiesLogMapper pushPropertiesLogMapper;

    @Override
    public void addPushPropertiesLog(String app, String env, String version, String cluster, String clientAddress, SendConfigType type) {
        CcPushPropertiesLog log = new CcPushPropertiesLog();
        log.setApp(app);
        log.setEnv(env);
        log.setVersion(version);
        log.setCluster(cluster);
        log.setPushReason(type);
        log.setCreateTime(new Date());
        log.setClientAddress(clientAddress);
        pushPropertiesLogMapper.insertSelective(log);
    }
}
