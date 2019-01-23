package com.xlizy.middleware.cc.server.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 配置管理页面服务接口
 * @author xlizy
 * @date 2018/5/19
 */
public interface ConfigCenterService {

    /**
     * 获取 应用+环境 结合的树形结构
     * @return
     * */
    JSONObject getTree();

}
