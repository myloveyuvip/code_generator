package com.yuly.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by yuliyao on 2016/11/30.
 */
public class PropertiesUtil {


    public static String getProperty(String filePath,String key) {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(PropertiesUtil.class.getResourceAsStream(filePath),"UTF-8"));
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getProperty(String key) {
        return getProperty("/config/genConf.properties", key);
    }

}
