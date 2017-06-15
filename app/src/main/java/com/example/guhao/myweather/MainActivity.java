package com.example.guhao.myweather;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.guhao.myweather.Bean.CityBean;
import com.example.guhao.myweather.data.DBOperation;
import com.example.guhao.myweather.service.WeatherOperation;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "main activity";

    class MyRunnable implements Runnable{
        Context context;

        public MyRunnable(Context context){
            this.context = context;
        }

        @Override
        public void run() {
            DBOperation dbOperation = new DBOperation(context);
            //List<CityBean> cityList = dbOperation.getCityResult("changzhou");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                WeatherOperation op = new WeatherOperation();
            }
        }).start();
        */

        //DBOperation dbOperation = new DBOperation(this);
        //List<CityBean> cityList = dbOperation.getCityResult("changzhou");

        MyRunnable runnable = new MyRunnable(this);
        new Thread(runnable).start();


    }
}
