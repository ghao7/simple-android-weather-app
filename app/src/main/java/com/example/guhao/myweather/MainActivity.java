package com.example.guhao.myweather;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.guhao.myweather.bean.CityEntity;
import com.example.guhao.myweather.presenter.DBOperation;
import com.example.guhao.myweather.presenter.WeatherPre;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener{
    private final String TAG = "main activity";
    private DBOperation dbOperation;
    private WeatherPre weatherPre;
    private LocationClient locationClient;


    private Button city_list_button;
    private Button test_button;
    private TextView text_view_test;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        findView();
        initListener();

        //WeatherOperation op = new WeatherOperation();
        //op.getWeather("beijing");
        //weather = op.getWeatherResult();
        //Log.d(TAG, "onCreate: " + op.getStr());

    }

    public void initData(){
        MyRunnable runnable = new MyRunnable(this);
        new Thread(runnable).start();
        weatherPre = new WeatherPre();

        locationService();

    }

    public void locationService(){
        locationClient = new LocationClient(getApplicationContext());

        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //option.setOpenGps(true);

        //option.setScanSpan(0);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new MylocationListener());

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }else{
            requestLocation();
        }
    }

    public void requestLocation(){
        locationClient.start();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0){
                    for (int result : grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"请同意所有权限",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else{
                    Toast.makeText(this,"申请权限发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    public class MylocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            String latitude = bdLocation.getLatitude() + " ";
            String longitude = bdLocation.getLongitude() + " ";
            String locType = bdLocation.getLocType() + " ";
            String text = latitude + longitude + locType;
            String city = bdLocation.getCity();

            Log.d(TAG, "onReceiveLocation: " + city);
            //showShort(city);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    public void initListener(){
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
    }
}
