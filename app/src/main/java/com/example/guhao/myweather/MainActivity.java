package com.example.guhao.myweather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.guhao.myweather.Bean.CityBean;
import com.example.guhao.myweather.data.DBOperation;
import com.example.guhao.myweather.service.WeatherOperation;

import java.util.List;

public class MainActivity extends Activity {
    private final String TAG = "main activity";
    private DBOperation dbOperation;
    private Button city_list_button;

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

        initData();
        findView();

        //DBOperation dbOperation = new DBOperation(this);
        //List<CityBean> cityList = dbOperation.getCityResult("changzhou");

        city_list_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityListActivity.class);
                startActivity(intent);
            }
        });


    }

    public void initData(){
        MyRunnable runnable = new MyRunnable(this);
        new Thread(runnable).start();
    }

    public void findView(){
        city_list_button = (Button)findViewById(R.id.city_list_button);
    }

    class MyRunnable implements Runnable{
        Context context;
        public MyRunnable(Context context){
            this.context = context;
        }
        @Override
        public void run() {
            dbOperation = new DBOperation(context);
            //List<CityBean> cityList = dbOperation.getCityResult("changzhou");
        }
    }
}
