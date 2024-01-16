package com.roche.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> serviceName) {
        return (T) applicationContext.getBean(serviceName);
    }

    public static <T> T getValue(String key, Class<T> valueType) {
        return (T) applicationContext.getEnvironment().getProperty(key, valueType);
    }

    public static ObjectMapper getObjectMapper() {
        return applicationContext.getBean(ObjectMapper.class);
    }
}