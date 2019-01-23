package com.xlizy.middleware.cc.client.spring.common;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.xlizy.middleware.cc.client.spring.property.SpringValueDefinition;
import com.xlizy.middleware.cc.client.spring.property.SpringValueRegistry;
import com.xlizy.middleware.cc.client.spring.utils.PlaceholderHelper;
import com.xlizy.middleware.cc.client.spring.utils.SpringInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xlizy
 * @date 2018/5/31
 */
public class SpringValueDefinitionProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(SpringValueDefinitionProcessor.class);

    private static final Multimap<String, SpringValueDefinition> beanName2SpringValueDefinitions =
            LinkedListMultimap.create();

    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    private final PlaceholderHelper placeholderHelper;
    private final SpringValueRegistry springValueRegistry;

    public SpringValueDefinitionProcessor() {
        placeholderHelper = SpringInjector.getInstance(PlaceholderHelper.class);
        springValueRegistry = SpringInjector.getInstance(SpringValueRegistry.class);
    }

    public static Multimap<String, SpringValueDefinition> getBeanName2SpringValueDefinitions() {
        return beanName2SpringValueDefinitions;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        processPropertyValues(registry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    private void processPropertyValues(BeanDefinitionRegistry beanRegistry) {
        if (!initialized.compareAndSet(false, true)) {
            // already initialized
            return;
        }

        String[] beanNames = beanRegistry.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = beanRegistry.getBeanDefinition(beanName);
            MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
            List<PropertyValue> propertyValues = mutablePropertyValues.getPropertyValueList();
            for (PropertyValue propertyValue : propertyValues) {
                Object value = propertyValue.getValue();
                if (!(value instanceof TypedStringValue)) {
                    continue;
                }
                String placeholder = ((TypedStringValue) value).getValue();
                Set<String> keys = placeholderHelper.extractPlaceholderKeys(placeholder);

                if (keys.isEmpty()) {
                    continue;
                }

                for (String key : keys) {
                    beanName2SpringValueDefinitions.put(beanName,
                            new SpringValueDefinition(key, placeholder, propertyValue.getName()));
                }
            }
        }
    }
}
