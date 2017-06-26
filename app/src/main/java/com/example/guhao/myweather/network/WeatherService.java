package com.example.guhao.myweather.network;


import com.example.guhao.myweather.bean.CurrentEntity;
import com.example.guhao.myweather.bean.ForecastEntity;
import com.example.guhao.myweather.bean.SearchEntity;
import com.example.guhao.myweather.bean.WeatherEntity;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by guhao on 6/13/17.
 */

public interface WeatherService {

    @GET("weather?")
    Observable<WeatherEntity> getWeatherData(@Query("city")String city,
                                             @Query("key")String key);

    @GET("forecast?")
    Observable<ForecastEntity> getForecastBean(@Query("city")String city,
                                         @Query("key")String key);

    @GET("now?")
    Observable<CurrentEntity> getCurrentBean(@Query("city")String city,
                                       @Query("key")String key);

    @GET("search?")
    Observable<SearchEntity> getSearchBean(@Query("city")String city,
                                     @Query("key")String key);

}
