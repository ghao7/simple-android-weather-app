package com.example.guhao.myweather.presenter;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guhao.myweather.MainActivity;
import com.example.guhao.myweather.bean.WeatherEntity;
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
    private String str;


    public void getWeather(String city, final TextView textView){
        subscriber = new Subscriber<WeatherEntity>(){
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WeatherEntity weatherEntity) {
                str = weatherEntity.getHeWeather5().get(0).getDaily_forecast().get(0).getCond().getTxt_d();
                Log.d(TAG, "onResponse all weather: " + str);
                textView.setText(str);
            }
        };

        HttpMethods.getInstance().getWeather(subscriber, city, KEY);

    }
}
