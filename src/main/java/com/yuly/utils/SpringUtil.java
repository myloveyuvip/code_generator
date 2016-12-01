package com.yuly.utils;

import org.springframework.context.ApplicationContext;

/**
 * Created by yuliyao on 2016/12/1.
 */
public class SpringUtil {

    private static ApplicationContext applicationContext;

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static<T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static<T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

}
