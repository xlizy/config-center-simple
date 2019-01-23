package com.xlizy.middleware.cc.client.common;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * @author xlizy
 * @date 2018/5/11
 */
public class ThreadPools {

    public static final ThreadFactory CONFIG_CENTER_CLIENT_NAME_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("config-center-client-%d").build();


    public static ExecutorService configCenterPool =  new ThreadPoolExecutor(5, 5,
                                          0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10), CONFIG_CENTER_CLIENT_NAME_THREAD_FACTORY, new ThreadPoolExecutor.AbortPolicy());


}
