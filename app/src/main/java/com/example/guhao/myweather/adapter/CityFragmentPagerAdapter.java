package com.example.guhao.myweather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.guhao.myweather.SingleCityFragment;

/**
 * Author: GuHao
 * Date: 6/27/17
 * Time: 2:52 PM
 * Desc:
 */

public class CityFragmentPagerAdapter extends FragmentStatePagerAdapter {
    public CityFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new SingleCityFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
