package com.example.guhao.myweather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.guhao.myweather.adapter.CityFragmentPagerAdapter;
import com.example.guhao.myweather.adapter.MyPageScrollListener;
import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.data.WeatherConstant;
import com.example.guhao.myweather.fragment.SingleCityFragment;
import com.example.guhao.myweather.network.SubscriberOnNextListener;
import com.example.guhao.myweather.presenter.DBOperation;
import com.example.guhao.myweather.presenter.WeatherPre;
import com.example.guhao.myweather.util.NetworkUtil;
import com.example.guhao.myweather.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ViewPager.OnPageChangeListener{
    private final String TAG = "main activity";

    private LocationClient locationClient;
    private int currentPageNum = 0;

    private DBOperation dbOperation;
    private Toolbar tb_toolbar;
    private ViewPager viewPager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MenuItem location_item;

    private SubscriberOnNextListener getWeatherOnNext;
    private CityFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (NetworkUtil.isNetworkAvailable(this)) {
            setContentView(R.layout.activity_main);
            findView();
            initData();
            initListener();

            setSupportActionBar(tb_toolbar);
            MyRunnable runnable = new MyRunnable(this);
            new Thread(runnable).start();
            Log.d(TAG, "onCreate: ");

        }else{
            //showLong("请您检查一下网络 更新不了天气了");
            setContentView(R.layout.activity_network_down);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    public void loadCityPreferences(){
        String key = "city";
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("city",MODE_PRIVATE);
        boolean check = true;
        int i = 0;
        viewPager.setOffscreenPageLimit(0);
        while (check){
            String city = preferences.getString(key+i, "null");
            if (!city.equals("null")){
                WeatherConstant.citySlotList.add(city);
                mPagerAdapter.addFragment(getSingleCityFragmentLite(city));
                WeatherConstant.weatherList.add(null);
                WeatherConstant.updateWeather(i, mPagerAdapter,swipeRefreshLayout);
                i++;
            }else{
                check = false;
            }
        }
    }

    /**
     * Refresh views if the user add cities in the list activity
     */
    public void loadCityInfo() {
        mPagerAdapter.clear();
        mPagerAdapter = new CityFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);

        for (int i = 0; i < WeatherConstant.weatherList.size(); i++) {
            Log.d(TAG, "loadCityInfo: " + WeatherConstant.weatherList.get(i).getHeWeather5().get(0).getBasic().getCity());
            WeatherEntity city = WeatherConstant.weatherList.get(i);
            mPagerAdapter.addFragment(getSingleCityFragment(city));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        getMenuInflater().inflate(R.menu.menu_list, menu);

        location_item = menu.findItem(R.id.action_location);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_list:
                Intent intent = new Intent(MainActivity.this, CityListScrollingActivity.class);
                startActivityForResult(intent,1);
                break;

            case R.id.id_settings:
                String str = (NetworkUtil.isNetworkAvailable(this))?"Yes":"No";
                showShort(str);
                break;
            default:
        }
        return true;
    }

    public void initData() {
        locationService();//automatic positioning

        mPagerAdapter = new CityFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOnPageChangeListener(this);
        //viewPager.set
        loadCityPreferences();
        //set scrollable view adapter
        tb_toolbar.setTitle(WeatherConstant.citySlotList.get(viewPager.getCurrentItem()));
//        tb_toolbar.setBackgroundColor(getResources().getColor(R.color.transparent_primary,null));
//        tb_toolbar.getBackground().setAlpha(0);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.myAccent,null));
        swipeRefreshLayout.setOnRefreshListener(this);

        setBackground();

    }

    public void setBackground(){
        //AnimationDrawable animationDrawable =
        //main_layout.setBackground(getResources().getDrawable(R.anim.alpha_anim));
        //Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale);
        //test_anim.startAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StringUtil.showPref(getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    loadCityInfo();
                    final int position = data.getIntExtra("position",0);
//                    viewPager.setCurrentItem(position,false);
                    viewPager.post(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(position);
                        }
                    });
                }
                break;
            default:
        }
    }

    @Override
    public void onRefresh() {
        locationService();
        //WeatherConstant.updateSharedPreferences(MainActivity.this);
        WeatherConstant.updateRawWeather(swipeRefreshLayout,mPagerAdapter);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE);
        tb_toolbar.setTitle(WeatherConstant.citySlotList.get(viewPager.getCurrentItem()));
        location_item.setVisible(viewPager.getCurrentItem() == 0);

    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        SingleCityFragment fragment = (SingleCityFragment)mPagerAdapter.getItem(currentPageNum);
        fragment.backToTop();
        currentPageNum = position;
    }

    /**
     * Solve conflict when swiping refresh and scrolling viewpager
     * @param enable
     */
    protected void enableDisableSwipeRefresh(boolean enable) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(enable);
        }
    }

    public SingleCityFragment getSingleCityFragment(WeatherEntity entity) {
        Bundle args = new Bundle();
        args = StringUtil.makeArgs(args,entity);
        SingleCityFragment cityFragment = new SingleCityFragment();
        cityFragment.setArguments(args);
        cityFragment.setOnMyPageScrollListener(new MyPageScrollListener() {
            @Override
            public void setRefresh(boolean set) {
                if (swipeRefreshLayout != null){
                    swipeRefreshLayout.setEnabled(set);
                }
            }
        });
        return cityFragment;
    }

    public SingleCityFragment getSingleCityFragmentLite(String city) {
        SingleCityFragment cityFragment = new SingleCityFragment();
        cityFragment.setOnMyPageScrollListener(new MyPageScrollListener() {
            @Override
            public void setRefresh(boolean set) {
                if (swipeRefreshLayout != null){
                    swipeRefreshLayout.setEnabled(set);
                }
            }
        });
        return cityFragment;
    }

    public void requestLocation() {
        locationClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
        }
    }

    public class MylocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String city = bdLocation.getCity();
            //showShort(city);
            city = StringUtil.takeOutLastChar(city);

            WeatherConstant.addLocal(city,getApplicationContext());
            WeatherConstant.updateLocal(city,MainActivity.this);
            WeatherPre.getWeatherRequest(city, getWeatherOnNext);
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
                WeatherConstant.addLocalEntity(entity);
                //mPagerAdapter.setInfo(0, entity);

                mPagerAdapter.updateFragment(0,getSingleCityFragment(entity),entity);
            }
        };
    }

    public void findView() {
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        viewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.city_fragment_swipe_refresh);
        //test_anim = (ImageView) findViewById(R.id.test_anim);
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
//        Log.d(TAG, "onDestroy: ");
        WeatherConstant.citySlotList.clear();
        WeatherConstant.weatherList.clear();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        WeatherConstant.citySlotList.clear();
        WeatherConstant.weatherList.clear();
    }

    public void locationService() {
        locationClient = new LocationClient(getApplicationContext());

        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);

        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new MylocationListener());

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
//        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_PHONE_STATE)
//                != PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(android.Manifest.permission.READ_PHONE_STATE);
//        }
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
