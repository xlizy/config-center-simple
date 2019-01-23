package com.xlizy.middleware.cc.client.spring.common;

import com.xlizy.middleware.cc.client.spring.utils.BeanRegistrationUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author xlizy
 * @date 2018/5/31
 */
public class ConfigPropertySourcesProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, PropertySourcesPlaceholderConfigurer.class.getName(),
                PropertySourcesPlaceholderConfigurer.class);

        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, SpringValueProcessor.class.getName(), SpringValueProcessor.class);
        processSpringValueDefinition(registry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    private void processSpringValueDefinition(BeanDefinitionRegistry registry) {
        SpringValueDefinitionProcessor springValueDefinitionProcessor = new SpringValueDefinitionProcessor();

        springValueDefinitionProcessor.postProcessBeanDefinitionRegistry(registry);
    }
}
