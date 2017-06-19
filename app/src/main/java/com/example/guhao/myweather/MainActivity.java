package com.example.guhao.myweather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guhao.myweather.bean.ForecastEntity;
import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.presenter.DBOperation;
import com.example.guhao.myweather.presenter.WeatherOperation;
import com.example.guhao.myweather.service.HttpMethods;

import rx.Subscriber;

public class MainActivity extends Activity implements View.OnClickListener{
    private final String TAG = "main activity";
    private DBOperation dbOperation;
    private WeatherEntity weather;
    private final String KEY = "deceba7359de462db05f243f3fb1660c";
    private Subscriber subscriber;
    private WeatherEntity weatherResult;
    private ForecastEntity forecastResult;
    private String str;

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

        //DBOperation dbOperation = new DBOperation(this);
        //List<CityEntity> cityList = dbOperation.getCityResult("changzhou");

        //WeatherOperation op = new WeatherOperation();
        //op.getWeather("beijing");
        //weather = op.getWeatherResult();
        //Log.d(TAG, "onCreate: " + op.getStr());

    }




    public void initData(){
        MyRunnable runnable = new MyRunnable(this);
        new Thread(runnable).start();
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
                getWeather("Beijing");
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
            //List<CityEntity> cityList = dbOperation.getCityResult("changzhou");
        }
    }

    public void getWeather(String city){

        subscriber = new Subscriber<WeatherEntity>(){
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WeatherEntity weatherEntity) {
                str = weatherEntity.getHeWeather5().get(0).getDaily_forecast().get(0).getCond().getTxt_d();
                Log.d(TAG, "onResponse all weather: " + str);
                weatherResult = weatherEntity;
                text_view_test.setText(str);
            }
        };

        HttpMethods.getInstance().getWeather(subscriber, city, KEY);

    }
}
