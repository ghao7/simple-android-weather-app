package com.example.guhao.myweather.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guhao.myweather.R;
import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.data.WeatherConstant;
import com.example.guhao.myweather.util.StringUtil;


public class SingleCityFragment extends Fragment{
    private final String TAG = "fragment";

    private TextView test_tv;
    private View view;
//    private SwipeRefreshLayout swipeRefreshLayout;

    public SingleCityFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_single_city, container, false);
        findView();
        initData();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("weather",test_tv.getText().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            String weather = savedInstanceState.getString("weather");
            test_tv.setText(weather);
        }
    }

//    @Override
//    public void onRefresh() {
//        //swipeRefreshLayout.setRefreshing(false);
//        Log.d(TAG, "onRefresh: ");
////        WeatherConstant.updateRawWeather(swipeRefreshLayout);
//        WeatherConstant.updateSingleCity(0,swipeRefreshLayout);
//    }

    public void findView(){
        test_tv = (TextView)view.findViewById(R.id.test_textView);
//        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.city_fragment_swipe_refresh);

    }

    public void setContent(WeatherEntity entity){
        String info = StringUtil.getDisplay(entity);
        test_tv.setText(info);
    }

    public void initData(){
        if (getArguments() != null) {
            test_tv.setText(getArguments().getString("weather"));
        }
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setColorSchemeColors(Color.RED);
    }

    public void setWeatherInfo(WeatherEntity entity){
        String str = StringUtil.getDisplay(entity);
        test_tv.setText(str);

    }
}
