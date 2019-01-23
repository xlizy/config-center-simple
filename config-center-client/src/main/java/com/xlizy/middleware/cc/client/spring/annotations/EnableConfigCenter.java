package com.xlizy.middleware.cc.client.spring.annotations;

import com.xlizy.middleware.cc.client.spring.common.ConfigCenterRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author xlizy
 * @date 2018/5/9
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ConfigCenterRegistrar.class)
public @interface EnableConfigCenter {

}
