package com.example.guhao.myweather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

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

    public CityFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
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
    

    public void addFragment(SingleCityFragment fragment){
        list.add(fragment);
        notifyDataSetChanged();
    }

    public void setInfo(int position, WeatherEntity entity){
        list.get(position).setWeatherInfo(entity);
    }
}
