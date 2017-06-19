package com.example.guhao.myweather.presenter;

import android.util.Log;

import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.service.HttpMethods;

import rx.Subscriber;


/**
 * Created by guhao on 6/13/17.
 */

public class WeatherOperation {

    private final String KEY = "deceba7359de462db05f243f3fb1660c";
    private final String TAG = "test";
    private Subscriber subscriber;


    public WeatherOperation(){
        getWeather();
    }

    public void getWeather(){
        subscriber = new Subscriber<WeatherEntity>(){
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WeatherEntity weatherEntity) {
                Log.d(TAG, "onResponse all weather: " + weatherEntity.getHeWeather5().get(0).getStatus());
            }
        };

        HttpMethods.getInstance().getWeather(subscriber, "Beijing", KEY);
    }

}
