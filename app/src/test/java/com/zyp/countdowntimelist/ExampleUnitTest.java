package com.zyp.countdowntimelist;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        System.out.println("start...");
        int i = 40;
        long ONE_DAY_MS = 24 * 60 * 60 * 1000;
        final long endTime = System.currentTimeMillis()  + ONE_DAY_MS * i + i * 1000;

        final String downTime = formatDownTime(endTime - System.currentTimeMillis());


        System.out.println(downTime);

        assertEquals(4, 2 + 2);
    }


    public String formatDownTime(long t) {

        int S = 1000;
        int M = S * 60;
        int H = M * 60;
        int D = H * 24;

        long time = t;

        System.out.println("0."+time);
        long day = (long) (time / D);
        time -= day * D;
        System.out.println("1."+time);
        long hour = (long) (time / H);
        time -= hour * H;
        System.out.println("2."+time);
        long min = (long) (time / M);
        time -= min * M;
        System.out.println("3."+time);
        long s = (long) (time / S);

        return String.format("%s天%s时%s分%s秒", day, hour, min, s);
    }

}