package com.example.guhao.myweather.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guhao.myweather.R;
import com.example.guhao.myweather.adapter.MyPageScrollListener;
import com.example.guhao.myweather.bean.WeatherEntity;


public class SingleCityFragment extends Fragment{
    private TextView city_name;
    private TextView now_temp;
    private TextView now_cond;
    private ScrollView scrollView;
    private View view;
    private MyPageScrollListener listener;
    private LinearLayout linearLayout;

    public SingleCityFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_single_city, container, false);
        findView();
        initData();
        inflateTempBar(inflater, container);
        return view;
    }

    public void inflateTempBar(LayoutInflater inflater, ViewGroup container) {
        for (int i = 0; i < 3; i++) {
            View v = inflater.inflate(R.layout.layout_temp_bar, container, false);
            linearLayout.addView(v);
        }
    }

    public void setOnMyPageScrollListener(MyPageScrollListener listener){
        this.listener = listener;
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
        scrollView = (ScrollView) view.findViewById(R.id.fragment_scroll_view);
        linearLayout = (LinearLayout) view.findViewById(R.id.fragment_linear_layout);
    }

    public void initData(){
        if (getArguments() != null) {
            city_name.setText(getArguments().getString("city_name"));
            now_temp.setText(getArguments().getString("now_temp"));
            now_cond.setText(getArguments().getString("now_cond"));
        }

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                listener.setRefresh(scrollY == 0);
            }
        });


    }

    public void setWeatherInfo(WeatherEntity entity){
        WeatherEntity.HeWeather5Bean.DailyForecastBean.TmpBean tmpBean = entity.getHeWeather5().get(0).getDaily_forecast().get(0).getTmp();
        String max = tmpBean.getMax();
        String min = tmpBean.getMin();
        String nowCond = entity.getHeWeather5().get(0).getNow().getCond().getTxt();
        String condComb = nowCond+" "+max+"˚"+"/"+min+"˚"+"C";

        city_name.setText(entity.getHeWeather5().get(0).getBasic().getCity());
        now_temp.setText(entity.getHeWeather5().get(0).getNow().getTmp()+"˚");
        now_cond.setText(condComb);
    }
}
