package com.example.guhao.myweather.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.example.guhao.myweather.util.DateUtil;

import java.util.ArrayList;
import java.util.List;


public class SingleCityFragment extends Fragment{
    private final String TAG = "";

    private TextView city_name;
    private TextView now_temp;
    private TextView now_cond;
    private ScrollView scrollView;
    private View view;
    private MyPageScrollListener listener;
    private LinearLayout linearLayout;
    private WeatherEntity entity;

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

    public void inflateTempBar() {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());

        List<WeatherEntity.HeWeather5Bean.DailyForecastBean> list = entity.getHeWeather5().get(0).getDaily_forecast();

//        Toast.makeText(this.getContext(),list.size()+"",Toast.LENGTH_SHORT).show();
        for (int i = 0; i < list.size(); i++) {
            View v = inflater.inflate(R.layout.layout_temp_bar,null);

            linearLayout.addView(v);
            TextView temp_min = (TextView) v.findViewById(R.id.temp_min);
            TextView temp_max = (TextView) v.findViewById(R.id.temp_max);
            TextView date = (TextView) v.findViewById(R.id.date);

            WeatherEntity.HeWeather5Bean.DailyForecastBean.TmpBean tempBean = list.get(i).getTmp();
            String tempMin = tempBean.getMin();
            String tempMax = tempBean.getMax();
            String rawDate = list.get(i).getDate();

            String XinQiji = getXinQiJi(i, rawDate);

            temp_min.setText(tempMin);
            temp_max.setText(tempMax);
            date.setText(XinQiji);

        }
    }

    public void setOnMyPageScrollListener(MyPageScrollListener listener){
        this.listener = listener;
    }

    public String getXinQiJi(int position, String rawDate){
        if (position == 0){
            return getResources().getString(R.string.today);
        }else{
            String result;
            int num = DateUtil.dayForWeek(rawDate);
            switch (num){
                case 1:
                    result = getResources().getString(R.string.monday);
                    break;
                case 2:
                    result = getResources().getString(R.string.tuesday);
                    break;
                case 3:
                    result = getResources().getString(R.string.wednesday);
                    break;
                case 4:
                    result = getResources().getString(R.string.thusday);
                    break;
                case 5:
                    result = getResources().getString(R.string.friday);
                    break;
                case 6:
                    result = getResources().getString(R.string.saturday);
                    break;
                case 7:
                    result = getResources().getString(R.string.sunday);
                    break;
                default:
                    result = "Wrong";
            }
            return result;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("weather",entity);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void initView(){
        String cityName = entity.getHeWeather5().get(0).getBasic().getCity();
        String nowTemp = entity.getHeWeather5().get(0).getNow().getTmp();
        WeatherEntity.HeWeather5Bean.DailyForecastBean.TmpBean tmpBean = entity.getHeWeather5().get(0).getDaily_forecast().get(0).getTmp();
        String max = tmpBean.getMax();
        String min = tmpBean.getMin();
        String nowCond = entity.getHeWeather5().get(0).getNow().getCond().getTxt();
        String condComb = nowCond+" "+max+"˚"+"/"+min+"˚"+"C";
        city_name.setText(cityName);
        now_temp.setText(nowTemp+"˚");
        now_cond.setText(condComb);
        inflateTempBar();
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
            this.entity = (WeatherEntity) getArguments().getSerializable("weather");
            initView();

        }

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (listener != null) {
                    listener.setRefresh(scrollY == 0);
                }
            }
        });


    }

}
