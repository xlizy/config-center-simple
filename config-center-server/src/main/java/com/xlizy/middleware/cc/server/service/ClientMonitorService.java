package com.xlizy.middleware.cc.server.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 客户端监控服务接口
 * @author xlizy
 * @date 2018/5/29
 */
public interface ClientMonitorService {

    /**
     * 获取信息数
     * @return
     * */
    JSONObject getTree();

}
