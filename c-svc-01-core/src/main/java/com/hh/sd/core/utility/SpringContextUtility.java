package com.hh.sd.core.utility;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtility {

    static ApplicationContext context;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        context=applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static void autowireBean(Object bean) {
        context.getAutowireCapableBeanFactory().autowireBean(bean);
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }
}
