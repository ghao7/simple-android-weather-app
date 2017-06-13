package com.example.guhao.myweather;

import android.util.Log;

import java.io.IOException;

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

    public WeatherOperation(){
        Retrofit retrofit = new Retrofit
                .Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherBean> call = service.getWeatherData("Beijing",KEY);


        call.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                /*
                try {
                    System.out.println(response.body().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */
                //System.out.println(response.body().toString());
                Log.d(TAG, "onResponse: " + response.body().getHeWeather5().get(0).getStatus());
                Log.d(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
