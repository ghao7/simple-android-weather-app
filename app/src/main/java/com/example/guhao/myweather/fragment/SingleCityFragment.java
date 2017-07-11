package com.example.guhao.myweather.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.guhao.myweather.R;
import com.example.guhao.myweather.adapter.MyPageScrollListener;
import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.util.DateUtil;
import com.example.guhao.myweather.util.IconUtil;

import java.util.List;


public class SingleCityFragment extends Fragment{
    private final int BAR_MAX_LENGTH = 160;
    private TextView now_temp;
    private TextView now_cond;
    private TextView max_min;
    private ScrollView scrollView;
    private View view;
    private MyPageScrollListener listener;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout_hourly;
    private LinearLayout linearLayout_suggestion;
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

    public void inflateSuggestion(){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        WeatherEntity.HeWeather5Bean.SuggestionBean bean = entity.getHeWeather5().get(0).getSuggestion();

        if (bean != null) {
            View v = inflater.inflate(R.layout.layout_suggestions, null);
            linearLayout_suggestion.addView(v);

            TextView clothes_suggestion_content = (TextView) v.findViewById(R.id.clothes_suggestion_content);
            TextView work_out_suggestion_content = (TextView) v.findViewById(R.id.work_out_suggestion_content);
            TextView cold_suggestion_content = (TextView) v.findViewById(R.id.cold_suggestion_content);
            TextView travel_suggestion_content = (TextView) v.findViewById(R.id.travel_sugegstion_content);
            TextView comf_suggestion_content = (TextView) v.findViewById(R.id.comf_suggestion_content);
            TextView uv_suggestion_content = (TextView) v.findViewById(R.id.uv_suggestion_content);
            TextView cw_suggestion_content = (TextView) v.findViewById(R.id.cw_suggestion_content);

            clothes_suggestion_content.setText(bean.getDrsg().getTxt());
            work_out_suggestion_content.setText(bean.getSport().getTxt());
            cold_suggestion_content.setText(bean.getFlu().getTxt());
            travel_suggestion_content.setText(bean.getTrav().getTxt());
            comf_suggestion_content.setText(bean.getComf().getTxt());
            uv_suggestion_content.setText(bean.getUv().getTxt());
            cw_suggestion_content.setText(bean.getCw().getTxt());
        }

    }

    public void inflateHourly(){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        List<WeatherEntity.HeWeather5Bean.HourlyForecastBean> list = entity.getHeWeather5().get(0).getHourly_forecast();

        for (int i = 0; i < list.size(); i++){
            View v = inflater.inflate(R.layout.layout_hourly,null);
            linearLayout_hourly.addView(v);
            TextView hourly_time = (TextView) v.findViewById(R.id.hourly_time);
            TextView hourly_temp = (TextView) v.findViewById(R.id.hourly_temp);
            ImageView hourly_icon = (ImageView) v.findViewById(R.id.hourly_cond_icon);

            String fullDate = list.get(i).getDate();
            String[] result = fullDate.split("\\s");

            hourly_time.setText(result[1]);
            hourly_temp.setText(list.get(i).getTmp()+"˚");

            String weatherID = list.get(i).getCond().getCode();
            hourly_icon.setImageDrawable(getResources().getDrawable(IconUtil.getWeatherID(weatherID),null));
        }
    }

    public void inflateTempBar() {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());

        List<WeatherEntity.HeWeather5Bean.DailyForecastBean> list = entity.getHeWeather5().get(0).getDaily_forecast();

        //找到最大最小温度 以此调节温度调位置和长度
        int absMin = Integer.MAX_VALUE;
        int absMax = Integer.MIN_VALUE;
        for (int i = 0; i < list.size();i++){
            int tempMax = Integer.parseInt(list.get(i).getTmp().getMax());
            int tempMin = Integer.parseInt(list.get(i).getTmp().getMin());
            if (tempMax > absMax){
                absMax = tempMax;
            }
            if (tempMin < absMin){
                absMin = tempMin;
            }
        }

        int unit = BAR_MAX_LENGTH/(absMax - absMin);

        for (int i = 0; i < list.size(); i++) {
            View v = inflater.inflate(R.layout.layout_temp_bar,null);

            linearLayout.addView(v);
            TextView temp_min = (TextView) v.findViewById(R.id.temp_min);
            TextView temp_max = (TextView) v.findViewById(R.id.temp_max);
            TextView date = (TextView) v.findViewById(R.id.date);
            ImageView tempBar = (ImageView) v.findViewById(R.id.temp_bar);
            ImageView icon = (ImageView) v.findViewById(R.id.weather_icon);
            TextView padding = (TextView) v.findViewById(R.id.padding);

            icon.setImageDrawable(getResources().getDrawable(IconUtil.getWeatherID(list.get(i).getCond().getCode_d()),null));

            WeatherEntity.HeWeather5Bean.DailyForecastBean.TmpBean tempBean = list.get(i).getTmp();
            String tempMin = tempBean.getMin();
            String tempMax = tempBean.getMax();
            String rawDate = list.get(i).getDate();

            String XinQiji = getXinQiJi(i, rawDate);

            temp_min.setText(tempMin+"˚");
            temp_max.setText(tempMax+"˚");
            date.setText(XinQiji);

            ViewGroup.LayoutParams params = tempBar.getLayoutParams();
            int min = Integer.parseInt(tempMin);
            int max = Integer.parseInt(tempMax);

            params.width = (max - min)* unit*3;
            int pad = unit*3*(min-absMin);

            ViewGroup.LayoutParams paddingParas = padding.getLayoutParams();
            paddingParas.width = pad;

            String pop = list.get(i).getPop();
            if (Integer.parseInt(pop) > 0){
                View vv = inflater.inflate(R.layout.pop,null);
                TextView pop_tv = (TextView) vv.findViewById(R.id.tv_pop);
                pop_tv.setText(pop + "%");
                LinearLayout layout = (LinearLayout) v.findViewById(R.id.daily_jyl);
                layout.addView(vv);

                date.setTextSize(16);
            }

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
        String condComb = nowCond;
        String maxMin = min+"˚"+"/"+max+"˚"+"C";
        now_temp.setText(nowTemp+"˚");
        now_cond.setText(condComb);
        max_min.setText(maxMin);
        inflateTempBar();
        inflateHourly();
        inflateSuggestion();
    }

    public void backToTop(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                //scrollView.fullScroll(ScrollView.FOCUS_UP);
                scrollView.scrollTo(0,0);
            }
        });
    }

    public void findView(){
        now_temp = (TextView) view.findViewById(R.id.now_temp);
        now_cond = (TextView) view.findViewById(R.id.now_cond);
        max_min = (TextView) view.findViewById(R.id.max_min);
        scrollView = (ScrollView) view.findViewById(R.id.fragment_scroll_view);
        linearLayout = (LinearLayout) view.findViewById(R.id.fragment_linear_layout);
        linearLayout_hourly = (LinearLayout) view.findViewById(R.id.hourly_forecast);
        linearLayout_suggestion = (LinearLayout) view.findViewById(R.id.suggestion);
    }

    public void initData(){


        if (getArguments() != null) {
            this.entity = (WeatherEntity) getArguments().getSerializable("weather");
            initView();

        }

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                listener.setRefresh(scrollY == 0);
            }
        });


    }

}
