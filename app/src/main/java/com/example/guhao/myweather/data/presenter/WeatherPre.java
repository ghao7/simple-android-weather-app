package com.example.guhao.myweather.data.presenter;

import com.example.guhao.myweather.data.network.CitySubscriber;
import com.example.guhao.myweather.data.network.HttpMethods;
import com.example.guhao.myweather.data.network.SubscriberOnNextListener;
import com.example.guhao.myweather.data.network.WeatherSubscriber;

/**
 * Author: GuHao
 * Date: 6/20/17
 * Time: 11:31 AM
 * Desc:
 */

public class WeatherPre {
    private static final String KEY = "deceba7359de462db05f243f3fb1660c";

    public static void getWeatherRequest(String city, SubscriberOnNextListener listener){
        HttpMethods.getInstance().getWeather(new WeatherSubscriber(listener), city, KEY);
    }

    public static void getCityRequest(String city, SubscriberOnNextListener listener){
        HttpMethods.getInstance().getCity(new CitySubscriber(listener), city, KEY);
    }
}
