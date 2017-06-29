package com.example.guhao.myweather.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.guhao.myweather.adapter.CityFragmentPagerAdapter;
import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.fragment.SingleCityFragment;
import com.example.guhao.myweather.network.SubscriberOnNextListener;
import com.example.guhao.myweather.presenter.WeatherPre;
import com.example.guhao.myweather.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Author: GuHao
 * Date: 6/21/17
 * Time: 4:35 PM
 * Desc:
 */

public class WeatherConstant{
    public static List<WeatherEntity> weatherList = new ArrayList<>();
    public static List<String> citySlotList = new ArrayList<>();
    public static List<TextView> cardList = new ArrayList<>();
    private static SubscriberOnNextListener listener;

    public static void addLocal(String city, Context context){
        if (citySlotList.size() == 0){
            addCitySlot(city,context);
        }else{
            citySlotList.set(0,city);
        }
    }

    public static void addLocalEntity(WeatherEntity entity){
        if (weatherList.size() == 0){
            addWeatherEntity(entity);
        }else{
            weatherList.set(0,entity);
        }
    }

    public static void addCitySlot(String city, Context context){
        citySlotList.add(city);
        SharedPreferences.Editor editor = context.getSharedPreferences("city",Context.MODE_PRIVATE).edit();
        String key = "city" + (citySlotList.size()-1);
        editor.putString(key, city);
        editor.apply();

    }

    public static void addWeatherEntity(WeatherEntity entity){
        if (!checkIfExist(entity)){
            weatherList.add(entity);
        }
    }

    public static boolean checkIfExist(WeatherEntity entity){
        for (int i = 0; i < weatherList.size(); i++){
            if (weatherList.get(i).getHeWeather5().get(0).getBasic().getId().equals(entity.getHeWeather5().get(0).getBasic().getId())){
                return true;
            }
        }
        return false;
    }

    public static boolean checkIfExistCitySlot(String cityName){
        for (int i = 0; i < citySlotList.size(); i++){
            if (citySlotList.get(i).equals(cityName)){
                return true;
            }
        }
        return false;
    }

    public static void updateWeather(final int position, String city, final Context context, final CityFragmentPagerAdapter adapter){
        listener = new SubscriberOnNextListener<WeatherEntity>(){
            @Override
            public void onNext(WeatherEntity entity) {
                weatherList.set(position,entity);
                adapter.updateFragment(position,getSingleCityFragment(entity));
            }
        };
        WeatherPre.getWeatherRequest(city,listener,context);

    }

    public static SingleCityFragment getSingleCityFragment(WeatherEntity entity) {
        Bundle args = new Bundle();
        String weather = StringUtil.getDisplay(entity);
        args.putString("weather", weather);
        SingleCityFragment cityFragment = new SingleCityFragment();
        cityFragment.setArguments(args);
        return cityFragment;
    }

}
