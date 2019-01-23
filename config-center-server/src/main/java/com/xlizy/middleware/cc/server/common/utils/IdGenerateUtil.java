package com.xlizy.middleware.cc.server.common.utils;

import java.util.UUID;

/**
 * @author xlizy
 * @date 2018/3/10
 */
public class IdGenerateUtil {

    /**
     * 获取UUID(36位)，性能稍稍差一点
     * */
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

}
