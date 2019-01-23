package com.xlizy.middleware.cc.client.spring.common;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.xlizy.middleware.cc.client.common.ClientHelper;
import com.xlizy.middleware.cc.client.spring.property.SpringValue;
import com.xlizy.middleware.cc.client.spring.property.SpringValueDefinition;
import com.xlizy.middleware.cc.client.spring.property.SpringValueRegistry;
import com.xlizy.middleware.cc.client.spring.utils.PlaceholderHelper;
import com.xlizy.middleware.cc.client.spring.utils.SpringInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

/**
 * @author xlizy
 * @date 2018/5/9
 */
public class SpringValueProcessor extends ConfigCenterProcessor implements BeanFactoryPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(SpringValueProcessor.class);

    private final PlaceholderHelper placeholderHelper;
    private final SpringValueRegistry springValueRegistry;

    private static Multimap<String, SpringValueDefinition> beanName2SpringValueDefinitions =
            LinkedListMultimap.create();

    public SpringValueProcessor() {
        placeholderHelper = SpringInjector.getInstance(PlaceholderHelper.class);
        springValueRegistry = SpringInjector.getInstance(SpringValueRegistry.class);
    }

    @Override
    protected void processField(Object bean, String beanName, Field field) {
        // register @Value on field
        Value value = field.getAnnotation(Value.class);
        if (value == null) {
            return;
        }
        Set<String> keys = placeholderHelper.extractPlaceholderKeys(value.value());

        if (keys.isEmpty()) {
            return;
        }

        for (String key : keys) {
            SpringValue springValue = new SpringValue(key, value.value(), bean, beanName, field, false);
            springValueRegistry.register(key, springValue);
            logger.debug("Monitoring {}", springValue);
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        super.postProcessBeforeInitialization(bean, beanName);
        processBeanPropertyValues(bean, beanName);
        return bean;
    }

    @Override
    protected void processMethod(Object bean, String beanName, Method method) {
        //register @Value on method
        Value value = method.getAnnotation(Value.class);
        if (value == null) {
            return;
        }
        //skip Configuration bean methods
        if (method.getAnnotation(Bean.class) != null) {
            return;
        }
        if (method.getParameterTypes().length != 1) {
            logger.error("Ignore @Value setter {}.{}, expecting 1 parameter, actual {} parameters",
                    bean.getClass().getName(), method.getName(), method.getParameterTypes().length);
            return;
        }

        Set<String> keys = placeholderHelper.extractPlaceholderKeys(value.value());

        if (keys.isEmpty()) {
            return;
        }

        for (String key : keys) {
            SpringValue springValue = new SpringValue(key, value.value(), bean, beanName, method, false);
            springValueRegistry.register(key, springValue);
            logger.info("Monitoring {}", springValue);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        beanName2SpringValueDefinitions = SpringValueDefinitionProcessor
                .getBeanName2SpringValueDefinitions();
        ClientHelper.getInstance().setConfigurableListableBeanFactory(configurableListableBeanFactory);
    }

    private void processBeanPropertyValues(Object bean, String beanName) {
        Collection<SpringValueDefinition> propertySpringValues = beanName2SpringValueDefinitions
                .get(beanName);
        if (propertySpringValues == null || propertySpringValues.isEmpty()) {
            return;
        }

        for (SpringValueDefinition definition : propertySpringValues) {
            try {
                PropertyDescriptor pd = BeanUtils
                        .getPropertyDescriptor(bean.getClass(), definition.getPropertyName());
                Method method = pd.getWriteMethod();
                if (method == null) {
                    continue;
                }
                SpringValue springValue = new SpringValue(definition.getKey(), definition.getPlaceholder(),
                        bean, beanName, method, false);
                springValueRegistry.register(definition.getKey(), springValue);
                logger.debug("Monitoring {}", springValue);
            } catch (Throwable ex) {
                logger.error("Failed to enable auto update feature for {}.{}", bean.getClass(),
                        definition.getPropertyName());
            }
        }

        // clear
        beanName2SpringValueDefinitions.removeAll(beanName);
    }
}
