package com.example.guhao.myweather.util;

import com.example.guhao.myweather.R;

/**
 * Author: GuHao
 * Date: 7/10/17
 * Time: 2:59 PM
 * Desc:
 */

public class IconUtil {
    public static int getWeatherID(String code){
        int id;
        switch (code){
            case "100":
                id = R.mipmap.icons8_sunny_black;
                break;
            case "101":
                id = R.mipmap.icons8_clouds_black;
                break;
            case "103":
                id = R.mipmap.icons8_partly_cloudy_black;
                break;
            case "104":
                id = R.mipmap.icons8_clouds_black;
                break;
            case "305":
                id = R.mipmap.icons8_light_rain_black;
                break;
            case "306":
                id = R.mipmap.icons8_moderate_rain_black;
                break;
            case "307":
                id = R.mipmap.icons8_heavy_rain_black;
                break;
            case "310":
                id = R.mipmap.icons8_torrential_rain_black;
                break;
            case "308":
                id = R.mipmap.icons8_intense_rain_black;
                break;
            case "302":
                id = R.mipmap.icons8_storm_black;
                break;
            case "300":
                id = R.mipmap.icons8_partly_cloudy_rain_black;
                break;
            case "301":
                id = R.mipmap.icons8_partly_cloudy_rain_black;
                break;
            case "303":
                id = R.mipmap.icons8_cloud_lightning_black;
                break;
            case "309":
                id = R.mipmap.icons8_light_rain_black;
                break;
            default:
                id = R.mipmap.ic_wb_sunny_black_24dp;
                break;
        }

        return id;
    }

//    public static int getWeatherID(String code){
//        int id;
//        switch (code){
//            case "100":
//                id = R.mipmap.icons8_sunny;
//                break;
//            case "101":
//                id = R.mipmap.icons8_clouds;
//                break;
//            case "104":
//                id = R.mipmap.icons8_clouds;
//                break;
//            case "103":
//                id = R.mipmap.icons8_partly_cloudy;
//                break;
//            case "305":
//                id = R.mipmap.icons8_light_rain;
//                break;
//            case "306":
//                id = R.mipmap.icons8_moderate_rain;
//                break;
//            case "307":
//                id = R.mipmap.icons8_heavy_rain;
//                break;
//            case "310":
//                id = R.mipmap.icons8_torrential_rain;
//                break;
//            case "308":
//                id = R.mipmap.icons8_intense_rain;
//                break;
//            case "302":
//                id = R.mipmap.icons8_thunder_storm;
//                break;
//            case "300":
//                id = R.mipmap.icons8_partly_cloudy_rain;
//                break;
//            case "301":
//                id = R.mipmap.icons8_partly_cloudy_rain;
//                break;
//            case "303":
//                id = R.mipmap.icons8_cloud_lightning;
//                break;
//            case "309":
//                id = R.mipmap.icons8_light_rain;
//                break;
//            default:
//                id = R.mipmap.ic_wb_sunny_black_24dp;
//                break;
//        }
//
//        return id;
//    }
}
