package com.example.guhao.myweather.util;

import com.example.guhao.myweather.R;

/**
 * Author: GuHao
 * Date: 7/10/17
 * Time: 2:59 PM
 * Desc:
 */

public class iconUtil {
    public static int getWeatherID(String code){
        int id;
        switch (code){
            case "100":
                id = R.mipmap.icons8_sunny;
                break;
            case "101":
                id = R.mipmap.icons8_clouds;
                break;
            case "103":
                id = R.mipmap.icons8_partly_cloudy;
                break;
            case "305":
                id = R.mipmap.icons8_light_rain;
                break;
            case "306":
                id = R.mipmap.icons8_moderate_rain;
                break;
            case "307":
                id = R.mipmap.icons8_heavy_rain;
                break;
            case "310":
                id = R.mipmap.icons8_storm;
                break;
            case "308":
                id = R.mipmap.icons8_intense_rain;
                break;
            case "302":
                id = R.mipmap.icons8_thunder_storm;
                break;
            default:
                id = R.mipmap.ic_wb_sunny_black_24dp;
        }

        return id;
    }
}
