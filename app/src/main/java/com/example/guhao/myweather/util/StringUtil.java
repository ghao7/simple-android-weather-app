package com.example.guhao.myweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.guhao.myweather.data.bean.WeatherEntity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guhao on 6/15/17.
 */

public class StringUtil {
    private static final String TAG = "";
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

    public static void showPref(Context context){
        Log.d(TAG, "showPref: !!!!!!!!!!!!!!!");
        SharedPreferences preference = context.getSharedPreferences("city", Context.MODE_PRIVATE);
        boolean check = true;
        String key = "city";
        int i = 0;
        while (check){
            Log.d(TAG, "showPref: " + preference.getString(key+i,"null"));
            i++;
            if ((preference.getString(key+i,"null").equals("null"))){
                check = false;
            }
        }

    }

    public static Bundle makeArgs(Bundle args, WeatherEntity entity){
        args.putSerializable("weather",entity);
        return args;
    }


}
