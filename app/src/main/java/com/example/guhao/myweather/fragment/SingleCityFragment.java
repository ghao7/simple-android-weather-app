package com.example.guhao.myweather.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guhao.myweather.R;
import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.data.WeatherConstant;
import com.example.guhao.myweather.util.StringUtil;


public class SingleCityFragment extends Fragment {
    private final String TAG = "fragment";

    private TextView test_tv;
    private View view;

    public SingleCityFragment() {
        // Required empty public constructor
        Log.d(TAG, "SingleCityFragment: fragment is created!!!");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_single_city, container, false);
        findView();
        initData();
        Log.d(TAG, "onCreateView: view is created!!!");
        return view;
    }

    public static SingleCityFragment newInstance(WeatherEntity entity){
        Bundle args = new Bundle();
        String info = StringUtil.getDisplay(entity);
        args.putString("weather",info);
        SingleCityFragment fragment = new SingleCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void findView(){
        test_tv = (TextView)view.findViewById(R.id.test_textView);
    }

    public void initData(){

    }

    public void setWeatherInfo(WeatherEntity entity){
        String str = StringUtil.getDisplay(entity);
        test_tv.setText(str);
    }
}
