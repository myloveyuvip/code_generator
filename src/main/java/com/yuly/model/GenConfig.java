package com.yuly.model;

import com.yuly.utils.DateUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by yuliyao on 2016/12/1.
 */
@Component
@ConfigurationProperties
public class GenConfig {

    private String packagePath;

    private String dateString = DateUtil.getDateString();

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

}
