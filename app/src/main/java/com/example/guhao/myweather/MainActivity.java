package com.example.guhao.myweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.guhao.myweather.data.DBOperation;
import com.example.guhao.myweather.service.WeatherOperation;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "main activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                WeatherOperation op = new WeatherOperation();
//            }
//        }).start();


        DBOperation dbOperation = new DBOperation(this);
    }
}
