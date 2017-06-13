package com.example.guhao.myweather;


import java.util.Observable;

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

}
