package com.xlizy.middleware.cc.client.spring.utils;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.Objects;

/**
 * @author xlizy
 * @date 2018/5/9
 */
public class BeanRegistrationUtil {

    public static boolean registerBeanDefinitionIfNotExists(BeanDefinitionRegistry registry, String beanName,
                                                            Class<?> beanClass) {
        if (registry.containsBeanDefinition(beanName)) {
            return false;
        }

        String[] candidates = registry.getBeanDefinitionNames();

        for (String candidate : candidates) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(candidate);
            if (Objects.equals(beanDefinition.getBeanClassName(), beanClass.getName())) {
                return false;
            }
        }

        BeanDefinition annotationProcessor = BeanDefinitionBuilder.genericBeanDefinition(beanClass).getBeanDefinition();
        registry.registerBeanDefinition(beanName, annotationProcessor);

        return true;
    }

}
