package com.xlizy.middleware.cc.server.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 发送配置
 * @author xlizy
 * @date 2018/5/28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SendConfig {

    /** 应用ID */
    String appId() default "";

    /** 环境ID */
    String envId() default "";

    /** 配置ID */
    String prodId() default "";

}
