package com.example.guhao.myweather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.fragment.SingleCityFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: GuHao
 * Date: 6/27/17
 * Time: 2:52 PM
 * Desc:
 */

public class CityFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<SingleCityFragment> list;
    private FragmentManager fm;

    public CityFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
        list = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public void updateFragment(int position, SingleCityFragment fragment, WeatherEntity entity){
        if (list.size() > 0){
            if (position == 0 || position == 1){
                setInfo(position,entity);
            }else {
                list.set(position, fragment);
            }

            notifyDataSetChanged();
        }else{
            addFragment(fragment);
            notifyDataSetChanged();
        }
    }

    public void addFragment(SingleCityFragment fragment){
        list.add(fragment);
        notifyDataSetChanged();
    }

    public void clear(){
        list = new ArrayList<>();
    }

    public void setInfo(int position, WeatherEntity entity){
        list.get(position).setWeatherInfo(entity);
    }
}
