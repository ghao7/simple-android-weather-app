package com.example.guhao.myweather.service;

import android.util.Log;

import com.example.guhao.myweather.Bean.ForecastEntity;
import com.example.guhao.myweather.Bean.SearchEntity;
import com.example.guhao.myweather.Bean.WeatherEntity;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by guhao on 6/13/17.
 */

public class WeatherOperation {

    private final String BASE_URL = "https://free-api.heweather.com/v5/";
    private final String KEY = "deceba7359de462db05f243f3fb1660c";
    private final String TAG = "test";
    private WeatherEntity weather;
    private ForecastEntity forecast;
    private SearchEntity search;


    public WeatherOperation(){
        getWeather();
    }

    public void getWeather(){
        Retrofit retrofit = new Retrofit
                .Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);

        //getGeneralWeather(service);

        service.getWeatherData("Beijing", KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherEntity>() {
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
                });


    }

//    public void getGeneralWeather(WeatherService service){
//        final Call<WeatherEntity> call = service.getWeatherData("Beijing",KEY);
//        //getting all weather
//        call.enqueue(new Callback<WeatherEntity>() {
//            @Override
//            public void onResponse(Call<WeatherEntity> call, Response<WeatherEntity> response) {
//
//                Log.d(TAG, "onResponse all weather: " + response.body().getHeWeather5().get(0).getStatus());
//                weather = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<WeatherEntity> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }



}
