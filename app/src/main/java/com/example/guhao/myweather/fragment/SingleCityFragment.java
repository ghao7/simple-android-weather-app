package com.example.guhao.myweather.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guhao.myweather.R;
import com.example.guhao.myweather.bean.WeatherEntity;


public class SingleCityFragment extends Fragment{
    private TextView city_name;
    private TextView now_temp;
    private TextView now_cond;
    private View view;

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
        outState.putString("city_name",city_name.getText().toString());
        outState.putString("now_temp",now_temp.getText().toString());
        outState.putString("now_cond",now_cond.getText().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            city_name.setText(savedInstanceState.getString("city_name"));
            now_temp.setText(savedInstanceState.getString("now_temp"));
            now_cond.setText(savedInstanceState.getString("now_cond"));
        }
    }

    public void findView(){
        city_name = (TextView)view.findViewById(R.id.city_name);
        now_temp = (TextView) view.findViewById(R.id.now_temp);
        now_cond = (TextView) view.findViewById(R.id.now_cond);
    }

    public void setContent(WeatherEntity entity){
        city_name.setText(entity.getHeWeather5().get(0).getBasic().getCity());
        now_temp.setText(entity.getHeWeather5().get(0).getNow().getTmp());
        now_cond.setText(entity.getHeWeather5().get(0).getNow().getCond().getTxt());
    }

    public void initData(){
        if (getArguments() != null) {
            city_name.setText(getArguments().getString("city_name"));
            now_temp.setText(getArguments().getString("now_temp"));
            now_cond.setText(getArguments().getString("now_cond"));
        }
    }

    public void setWeatherInfo(WeatherEntity entity){
        city_name.setText(entity.getHeWeather5().get(0).getBasic().getCity());
        now_temp.setText(entity.getHeWeather5().get(0).getNow().getTmp());
        now_cond.setText(entity.getHeWeather5().get(0).getNow().getCond().getTxt());

    }
}
