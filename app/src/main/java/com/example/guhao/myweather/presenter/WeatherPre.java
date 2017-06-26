package com.example.guhao.myweather.presenter;

import android.content.Context;

import com.example.guhao.myweather.network.HttpMethods;
import com.example.guhao.myweather.network.SubscriberOnNextListener;
import com.example.guhao.myweather.network.WeatherSubscriber;

/**
 * Author: GuHao
 * Date: 6/20/17
 * Time: 11:31 AM
 * Desc:
 */

public class WeatherPre {
    private static final String KEY = "deceba7359de462db05f243f3fb1660c";

    public static void getWeatherRequest(String city, SubscriberOnNextListener listener, Context context){
        HttpMethods.getInstance().getWeather(new WeatherSubscriber(listener, context), city, KEY);
    }
}
