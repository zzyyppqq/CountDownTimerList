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


    public static String formatDownTime(long time) {
        if (time <= 0){
            return String.format("%02d天%02d时%02d分%02d秒", 0, 0, 0, 0);
        }

        int S = 1000;
        int M = S * 60;
        int H = M * 60;
        int D = H * 24;

        long day =  time / D;
        time -= day * D;
        long hour = time / H;
        time -= hour * H;
        long min = time / M;
        time -= min * M;
        long s = time / S;

        return String.format("%02d天%02d时%02d分%02d秒", day, hour, min, s);
    }
}
