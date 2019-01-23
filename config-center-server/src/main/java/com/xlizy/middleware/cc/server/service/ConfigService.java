package com.xlizy.middleware.cc.server.service;

/**
 * @author xlizy
 * @date 2018/4/25
 */
public interface ConfigService {

    /**
     * 清除session缓存
     * */
    void clearSessionCache();

    /**
     * 清除数据缓存
     * */
    void clearDataCache();

}
