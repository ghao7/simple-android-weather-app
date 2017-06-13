package com.example.guhao.myweather;


import com.example.guhao.myweather.Bean.CurrentBean;
import com.example.guhao.myweather.Bean.ForecastBean;
import com.example.guhao.myweather.Bean.SearchBean;
import com.example.guhao.myweather.Bean.WeatherBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by guhao on 6/13/17.
 */

public interface WeatherService {

    @GET("weather?")
    Call<WeatherBean> getWeatherData(@Query("city")String city,
                                     @Query("key")String key);

    @GET("forecast?")
    Call<ForecastBean> getForecastBean(@Query("city")String city,
                                       @Query("key")String key);

    @GET("now?")
    Call<CurrentBean> getCurrentBean(@Query("city")String city,
                                     @Query("key")String key);

    @GET("search?")
    Call<SearchBean> getSearchBean(@Query("city")String city,
                                   @Query("key")String key);

}
