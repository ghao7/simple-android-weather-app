package com.example.guhao.myweather.data;

import com.example.guhao.myweather.bean.WeatherEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: GuHao
 * Date: 6/21/17
 * Time: 4:35 PM
 * Desc:
 */

public class WeatherConstant {
    public static List<WeatherEntity> weatherList = new ArrayList<>();

    public static void add(WeatherEntity entity){
        if (!checkIfExist(entity)){
            weatherList.add(0,entity);
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

    public static List<WeatherEntity> getWeatherList(){
        return weatherList;
    }
}
