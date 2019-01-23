package com.xlizy.middleware.cc.server.common.utils;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * 安全关闭服务工具类
 * @author xlizy
 * @date 2018/4/13
 */
@Component
public class ShutdownUtil {

    private static final Map<String, String> NO_CONTEXT_MESSAGE = Collections
            .unmodifiableMap(
                    Collections.singletonMap("message", "No context to shutdown."));

    private static final Map<String, String> SHUTDOWN_MESSAGE = Collections
            .unmodifiableMap(
                    Collections.singletonMap("message", "Shutting down, bye..."));

    public Map<String, String> shutdown() {
        if (SpringContextUtil.getApplicationContext() == null) {
            return NO_CONTEXT_MESSAGE;
        }
        try {
            return SHUTDOWN_MESSAGE;
        }
        finally {
            Thread thread = new Thread(this::performShutdown);
            thread.setContextClassLoader(getClass().getClassLoader());
            thread.start();
        }
    }

    private void performShutdown() {
        try {
            Thread.sleep(500L);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        ((ConfigurableApplicationContext)SpringContextUtil.getApplicationContext()).close();
    }

}
