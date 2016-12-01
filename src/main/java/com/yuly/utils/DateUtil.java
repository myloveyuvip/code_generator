package com.yuly.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuliyao on 2016/12/1.
 */
public class DateUtil {

    public static String format(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return simpleDateFormat.format(date);
    }

    public static String getDateString() {
        return format(new Date());
    }

}
