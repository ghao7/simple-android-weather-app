package com.example.guhao.myweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.guhao.myweather.adapter.CityRecycleViewAdapter;
import com.example.guhao.myweather.bean.WeatherEntity;
import com.example.guhao.myweather.data.WeatherConstant;
import com.example.guhao.myweather.network.SubscriberOnNextListener;
import com.example.guhao.myweather.presenter.WeatherPre;
import com.example.guhao.myweather.util.StringUtil;

import java.util.List;

public class CityListScrollingActivity extends BaseActivity {
    private final String TAG = "";
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private Toolbar tb_toolbar;
    private RecyclerView rv_city_card;

    List<String> mlist;
    private SubscriberOnNextListener getWeatherOnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_list_scrolling);
        findView();
        initData();
        setSupportActionBar(tb_toolbar);
        fabListener();
        weatherSubscriberListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        String result = getIntent().getStringExtra("city");
//        showShort(result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        Log.d(TAG, "onCreateOptionsMenu: ");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        switch (item.getItemId()){
            case R.id.action_search:
                showShort("Clicked");
                break;

            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    String returnedData = data.getStringExtra("city");
                    //showShort(returnedData);
                    WeatherPre.getWeatherRequest(returnedData,getWeatherOnNext,CityListScrollingActivity.this);

                }
                break;
            default:
        }
    }

    public void initData(){
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new CityRecycleViewAdapter(WeatherConstant.citySlotList));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration();


    }

    public void findView(){
        fab = (FloatingActionButton)findViewById(R.id.fab);
        recyclerView = (RecyclerView)findViewById(R.id.city_list_recycler_view);
        tb_toolbar = (Toolbar)findViewById(R.id.tb_toolbar);
        rv_city_card = (RecyclerView)findViewById(R.id.city_list_recycler_view);
    }

    public void fabListener(){
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityListScrollingActivity.this, CitySearchingActivity.class);
                //startActivity(intent);
                startActivityForResult(intent,1);
            }
        });
    }

    public void weatherSubscriberListener(){
        getWeatherOnNext = new SubscriberOnNextListener<WeatherEntity>(){
            @Override
            public void onNext(WeatherEntity entity) {
                String cityName = entity.getHeWeather5().get(0).getBasic().getCity();
//                showShort(cityName);
                //WeatherConstant.cardList.get(0).setText(StringUtil.getDisplay(entity));



            }
        };
    }

}
