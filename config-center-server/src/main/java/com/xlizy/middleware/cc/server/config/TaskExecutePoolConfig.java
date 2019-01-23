package com.xlizy.middleware.cc.server.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xlizy
 * @date 2019/1/3
 */
@Configuration
public class TaskExecutePoolConfig {

    @Bean(name ="publicUsePool")
    public ExecutorService taskAsyncPool() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("publicUsePool-%d").build();
        return new ThreadPoolExecutor(8, 16,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), threadFactory);
    }

}
