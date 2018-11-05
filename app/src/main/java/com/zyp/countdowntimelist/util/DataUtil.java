package com.zyp.countdowntimelist.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        SimpleDateFormat sdf = threadLocal.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat("HH:mm:ss");
            threadLocal.set(sdf);
        }
        return sdf;
    }


    public static String formatTime(long time) {
        return getSimpleDateFormat("HH:mm:ss").format(new Date(time));
    }
}
