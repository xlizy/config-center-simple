package com.xlizy.middleware.cc.server.annotations;


import com.xlizy.middleware.cc.server.enums.OPType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解
 * @author xlizy
 * @date 2018/3/26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface OperationLog {

    /** 操作行为类型 */
    OPType bizType() default OPType.DEFAULT;

    /** 描述 */
    String remark() default "";

}
