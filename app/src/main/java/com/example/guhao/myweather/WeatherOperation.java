package com.example.guhao.myweather;

import android.util.Log;

import com.example.guhao.myweather.Bean.ForecastBean;
import com.example.guhao.myweather.Bean.SearchBean;
import com.example.guhao.myweather.Bean.WeatherBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by guhao on 6/13/17.
 */

public class WeatherOperation {

    private final String BASE_URL = "https://free-api.heweather.com/v5/";
    private final String KEY = "deceba7359de462db05f243f3fb1660c";
    private final String TAG = "test";
    private WeatherBean weather;
    private ForecastBean forecast;
    private SearchBean search;


    public WeatherOperation(){
        Retrofit retrofit = new Retrofit
                .Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);
        final Call<WeatherBean> call = service.getWeatherData("Beijing",KEY);
        final Call<ForecastBean> forecastCall = service.getForecastBean("常州",KEY);
        final Call<SearchBean> searchCall = service.getSearchBean("常州",KEY);

        //getting all weather
        call.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {

                Log.d(TAG, "onResponse all weather: " + response.body().getHeWeather5().get(0).getStatus());
                weather = response.body();
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t) {
                t.printStackTrace();
            }
        });

        //getting weather forecast
        forecastCall.enqueue(new Callback<ForecastBean>() {
            @Override
            public void onResponse(Call<ForecastBean> call, Response<ForecastBean> response) {
                Log.d(TAG, "onResponse forecast: " + response.body().getHeWeather5().get(0).getStatus());
                forecast = response.body();
            }

            @Override
            public void onFailure(Call<ForecastBean> call, Throwable t) {

            }
        });

        //getting city search result
        searchCall.enqueue(new Callback<SearchBean>() {
            @Override
            public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                Log.d(TAG, "onResponse search: " + response.body().getHeWeather5().get(0).getBasic().getId());
                search = response.body();
            }

            @Override
            public void onFailure(Call<SearchBean> call, Throwable t) {

            }
        });
    }

    public WeatherBean getBean() {
        return weather;
    }


}
