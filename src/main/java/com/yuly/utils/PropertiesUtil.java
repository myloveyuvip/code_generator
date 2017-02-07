package com.yuly.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by yuliyao on 2016/11/30.
 */
public class PropertiesUtil {


    public static String getProperty(String fileName,String key) {
        Properties properties = new Properties();
        try {
            String filePath = PropertiesUtil.class.getClassLoader().getResource("").getFile();
            System.out.println("filePath:" + filePath + ";fileName:" + fileName);
            filePath = filePath.substring(0, filePath.length() - 1);
            filePath.substring(0, filePath.lastIndexOf("/"));
            int begin = 0;
            if (filePath.startsWith("file:/")) {
                begin = 6;
            }
            File file = new File(filePath.substring(begin, filePath.lastIndexOf("/")+1)+fileName);
            System.out.println(file.getAbsolutePath());
            if (file.exists()) {
                properties.load(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            } else {
                properties.load(new InputStreamReader(PropertiesUtil.class.getResourceAsStream("/"+fileName),"UTF-8"));
            }
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getProperty(String key) {
        return getProperty("application.properties", key);
    }


}
