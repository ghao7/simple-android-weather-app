package com.example.guhao.myweather.presenter;

import android.content.Context;
import android.util.Log;

import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.data.WeatherConstant;
import com.example.guhao.myweather.service.HttpMethods;

import rx.Subscriber;

/**
 * Author: GuHao
 * Date: 6/20/17
 * Time: 11:31 AM
 * Desc:
 */

public class WeatherPre {
    private Subscriber subscriber;
    private final String KEY = "deceba7359de462db05f243f3fb1660c";
    private final String TAG = "Weather Presenter";


    public void getWeather(String city){
        subscriber = new Subscriber<WeatherEntity>(){
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WeatherEntity weatherEntity) {
                String temp = weatherEntity.getHeWeather5().get(0).getNow().getTmp();
                String city = weatherEntity.getHeWeather5().get(0).getBasic().getCity();
                Log.d(TAG, "onResponse all weather: " + city + " " +temp);
                WeatherConstant.add(weatherEntity);
            }
        };

        HttpMethods.getInstance().getWeather(subscriber, city, KEY);

    }
}
