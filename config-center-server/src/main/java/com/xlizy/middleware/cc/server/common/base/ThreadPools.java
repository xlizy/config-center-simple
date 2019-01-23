package com.xlizy.middleware.cc.server.common.base;

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

    public static final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("publicUsePool-%d").build();

    public static ExecutorService publicUsePool = new ThreadPoolExecutor(8, 16,
                                   1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), threadFactory);


}
