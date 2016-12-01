package com.yuly.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by yuliyao on 2016/11/30.
 */
public class PropertiesUtil {


    public static String getProperty(String filePath,String key) {
        Properties properties = new Properties();
        try {
            properties.load(PropertiesUtil.class.getResourceAsStream(filePath));
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Resource resource = new ClassPathResource("application.properties");
    }

}
