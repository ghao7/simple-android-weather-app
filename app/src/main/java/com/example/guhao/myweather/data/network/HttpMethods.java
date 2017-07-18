package com.example.guhao.myweather.data.network;

import com.example.guhao.myweather.data.bean.ForecastEntity;
import com.example.guhao.myweather.data.bean.SearchEntity;
import com.example.guhao.myweather.data.bean.WeatherEntity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: GuHao
 * Date: 6/19/17
 * Time: 4:08 PM
 * Desc:
 */

public class HttpMethods {
    private final String BASE_URL = "https://free-api.heweather.com/v5/";
    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private WeatherService service;

    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(WeatherService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void getWeather(Subscriber<WeatherEntity> subscriber, String location, String key){
        service.getWeatherData(location,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getForcast(Subscriber<ForecastEntity> subscriber, String location, String key){
        service.getForecastBean(location,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getCity(Subscriber<SearchEntity> subscriber, String location, String key){
        service.getSearchBean(location,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
