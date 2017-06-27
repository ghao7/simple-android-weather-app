package com.example.guhao.myweather;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.guhao.myweather.adapter.CityFragmentPagerAdapter;
import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.data.WeatherConstant;
import com.example.guhao.myweather.fragment.SingleCityFragment;
import com.example.guhao.myweather.network.SubscriberOnNextListener;
import com.example.guhao.myweather.presenter.DBOperation;
import com.example.guhao.myweather.presenter.WeatherPre;
import com.example.guhao.myweather.util.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private final String TAG = "main activity";
    private static final int NUM_PAGES = 5;

    private DBOperation dbOperation;
    private LocationClient locationClient;

    private Toolbar tb_toolbar;
    private ViewPager viewPager;

    private SubscriberOnNextListener getWeatherOnNext;
    private CityFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        initData();
        initListener();

        setSupportActionBar(tb_toolbar);
        MyRunnable runnable = new MyRunnable(this);
        new Thread(runnable).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
                Intent intent = new Intent(MainActivity.this, CityListScrollingActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    public void initData() {
//        weatherPre = new WeatherPre();
        locationService();
        mPagerAdapter = new CityFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);

        SingleCityFragment cityFragment = new SingleCityFragment();
        mPagerAdapter.addFragment(cityFragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //showShort("main resume");
        loadCityInfo();
    }

    public void loadCityInfo(){
        if (mPagerAdapter.getCount() < WeatherConstant.weatherList.size()){

            int pagerCount = mPagerAdapter.getCount();
            for (int i = pagerCount; i < WeatherConstant.weatherList.size(); i++){
                SingleCityFragment cityFragment = new SingleCityFragment();
                //cityFragment.setWeatherInfo(WeatherConstant.weatherList.get(i));
                mPagerAdapter.addFragment(cityFragment);
                WeatherEntity city = WeatherConstant.weatherList.get(i);
                mPagerAdapter.setInfo(i,city);

                Log.d(TAG, "loadCityInfo: " + mPagerAdapter.getCount());
                Log.d(TAG, "loadCityInfo: " + WeatherConstant.citySlotList.size() );
                Log.d(TAG, "loadCityInfo: " + WeatherConstant.weatherList.size());
            }

        }

    }

    public void requestLocation() {
        locationClient.start();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "请同意所有权限", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "申请权限发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    public class MylocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String city = bdLocation.getCity();
            //showShort(city);
            city = StringUtil.takeOutLastChar(city);
            Log.d(TAG, "onReceiveLocation: " + city);
            WeatherConstant.citySlotList.add(city);
            WeatherPre.getWeatherRequest(city, getWeatherOnNext, MainActivity.this);
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    public void initListener() {
        getWeatherOnNext = new SubscriberOnNextListener<WeatherEntity>() {
            @Override
            public void onNext(WeatherEntity entity) {
                Log.d(TAG, "onNext: Get response");
                String temp = entity.getHeWeather5().get(0).getNow().getTmp();
                String city = entity.getHeWeather5().get(0).getBasic().getCity();
                Log.d(TAG, "onResponse all weather: " + city + " " + temp);
                WeatherConstant.weatherList.add(entity);
                mPagerAdapter.setInfo(0,entity);
            }
        };
    }

    public void findView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        viewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);
    }


    class MyRunnable implements Runnable {
        Context context;

        public MyRunnable(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            dbOperation = new DBOperation(context);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
    }

    public void locationService() {
        locationClient = new LocationClient(getApplicationContext());

        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //option.setOpenGps(true);

        //option.setScanSpan(0);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new MylocationListener());

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        } else {
            requestLocation();
        }
    }
}
