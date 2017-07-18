package com.example.guhao.myweather.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.guhao.myweather.data.bean.WeatherEntity;
import com.example.guhao.myweather.ui.fragment.SingleCityFragment;

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

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void updateFragment(int position, SingleCityFragment fragment, WeatherEntity entity){
        if (list.size() > 0){

                list.set(position, fragment);
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

}
