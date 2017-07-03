package com.example.guhao.myweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.guhao.myweather.adapter.CityRecycleViewAdapter;
import com.example.guhao.myweather.adapter.MyItemOnClickListener;
import com.example.guhao.myweather.adapter.SimpleItemTouchHelperCallback;
import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.data.WeatherConstant;
import com.example.guhao.myweather.network.SubscriberOnNextListener;
import com.example.guhao.myweather.presenter.WeatherPre;
import com.example.guhao.myweather.util.StringUtil;

import java.util.List;

public class CityListScrollingActivity extends BaseActivity {
    private final String TAG = "";
    private RecyclerView recyclerView;
    private Toolbar tb_toolbar;
    private CityRecycleViewAdapter rvAdapter;
    private FloatingActionButton fab;

    private SubscriberOnNextListener getWeatherOnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_list_scrolling);
        findView();
        initData();
        initListner();
        update();
    }

    public void initListner() {
        weatherSubscriberListener();
        recyclerviewListener();

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                StringUtil.showPref(getApplicationContext());
            }
        });
    }

    public void recyclerviewListener(){
        rvAdapter.setItemOnClickListener(new MyItemOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent();
                intent.putExtra("position",position);
                setResult(RESULT_OK,intent);
                finish();
//                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        String result = getIntent().getStringExtra("city");
//        showShort(result);
        //update();

        Log.d(TAG, "onStart: cityslotlist" + WeatherConstant.citySlotList.size());
        Log.d(TAG, "onStart: weatherlist" + WeatherConstant.weatherList.size());
        StringUtil.showPref(getApplicationContext());

    }

    public void update(){
        String str;
        if (WeatherConstant.weatherList.size() > 0) {
            str = StringUtil.getDisplay(WeatherConstant.weatherList.get(0));
        } else if (WeatherConstant.citySlotList.size() > 0) {
            str = WeatherConstant.citySlotList.get(0);
        } else {
            str = "定位中";
        }
        rvAdapter.updateData(str, 0);


        for (int i = 0; i < WeatherConstant.weatherList.size(); i++){
            String info = StringUtil.getDisplay(WeatherConstant.weatherList.get(i));
            rvAdapter.updateData(info,i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                //showShort("Clicked");
                Intent intent = new Intent(CityListScrollingActivity.this, CitySearchingActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, 1);
                break;

            default:
        }
        return true;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnedData = data.getStringExtra("city");
                    WeatherPre.getWeatherRequest(returnedData, getWeatherOnNext);
                }
                break;
            default:
        }
    }

    public void initData(){
        rvAdapter = new CityRecycleViewAdapter(WeatherConstant.citySlotList, getApplicationContext());
//        Log.d(TAG, "initData: cityslotlist size is " + WeatherConstant.citySlotList.size());
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(rvAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rvAdapter);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration();
        //showShort("呵呵");

        setSupportActionBar(tb_toolbar);
        tb_toolbar.setTitle(R.string.citylist);
    }

    public void findView() {
        recyclerView = (RecyclerView) findViewById(R.id.city_list_recycler_view);
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }


    public void weatherSubscriberListener() {
        getWeatherOnNext = new SubscriberOnNextListener<WeatherEntity>() {
            @Override
            public void onNext(WeatherEntity entity) {
                String cityName = entity.getHeWeather5().get(0).getBasic().getCity();
//                showShort(cityName);
                //WeatherConstant.cardList.get(0).setText(StringUtil.getDisplay(entity));
                rvAdapter.updateData(StringUtil.getDisplay(entity), WeatherConstant.citySlotList.size() - 1);
                WeatherConstant.addWeatherEntity(entity);
            }
        };
    }


}
