package com.example.guhao.myweather.util;

import com.example.guhao.myweather.bean.WeatherEntity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guhao on 6/15/17.
 */

public class StringUtil {
    public static boolean isChinese(String str){

        String regEx = "[\\u4e00-\\u9fa5]+";

        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher(str);

        if(m.find())
            return true;
        else
            return false;

    }

    public static String takeOutLastChar(String str){
        return str.substring(0,str.length()-1);
    }

    public static String getDisplay(WeatherEntity entity){
        String city = entity.getHeWeather5().get(0).getBasic().getCity();
        String temp = entity.getHeWeather5().get(0).getNow().getTmp();
        String cond = entity.getHeWeather5().get(0).getNow().getCond().getTxt();

        return city + "\n" + temp + "\n" + cond;
    }
}
