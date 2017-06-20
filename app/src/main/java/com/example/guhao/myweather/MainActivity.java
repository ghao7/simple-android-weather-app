package com.example.guhao.myweather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.guhao.myweather.bean.CityEntity;
import com.example.guhao.myweather.presenter.DBOperation;
import com.example.guhao.myweather.presenter.WeatherPre;

import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener{
    private final String TAG = "main activity";
    private DBOperation dbOperation;
    private WeatherPre weatherPre;

    private Button city_list_button;
    private Button test_button;
    private TextView text_view_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        findView();
        setOnClickListener();

        //WeatherOperation op = new WeatherOperation();
        //op.getWeather("beijing");
        //weather = op.getWeatherResult();
        //Log.d(TAG, "onCreate: " + op.getStr());

    }

    public void initData(){
        MyRunnable runnable = new MyRunnable(this);
        new Thread(runnable).start();
        weatherPre = new WeatherPre();
    }

    public void setOnClickListener(){
        city_list_button.setOnClickListener(this);
        test_button.setOnClickListener(this);
    }

    public void findView(){
        city_list_button = (Button)findViewById(R.id.city_list_button);
        test_button = (Button)findViewById(R.id.test_button);
        text_view_test = (TextView)findViewById(R.id.textView_test);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.city_list_button:
                Intent intent = new Intent(MainActivity.this, CityListScrollingActivity.class);
                startActivity(intent);
                break;
            case R.id.test_button:
                //weatherPre.getWeather("Beijing", text_view_test);
                //Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();

        }
    }

    class MyRunnable implements Runnable{
        Context context;
        public MyRunnable(Context context){
            this.context = context;
        }
        @Override
        public void run() {
            dbOperation = new DBOperation(context);
            List<CityEntity> cityList = dbOperation.getCityResult("changzhou");
        }
    }


}
