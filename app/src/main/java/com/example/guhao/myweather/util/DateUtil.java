package com.example.guhao.myweather.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Author: GuHao
 * Date: 7/7/17
 * Time: 3:48 PM
 * Desc:
 */

public class DateUtil {
    public static int dayForWeek(String pTime){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(format.parse(pTime));
            int dayForWeek = 0;
            if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
            return dayForWeek;
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
}
