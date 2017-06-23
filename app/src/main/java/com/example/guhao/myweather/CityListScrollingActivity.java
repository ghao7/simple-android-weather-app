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
import com.example.guhao.myweather.data.WeatherConstant;

import java.util.List;

public class CityListScrollingActivity extends BaseActivity {
    private final String TAG = "";
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private Toolbar tb_toolbar;

    List<String> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_list_scrolling);
        findView();
        setSupportActionBar(tb_toolbar);
        fabListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();

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

    public void initData(){


        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CityRecycleViewAdapter(WeatherConstant.getWeatherList()));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration();


    }

    public void findView(){
        fab = (FloatingActionButton)findViewById(R.id.fab);
        recyclerView = (RecyclerView)findViewById(R.id.city_list_recycler_view);
        tb_toolbar = (Toolbar)findViewById(R.id.tb_toolbar);
    }

    public void fabListener(){
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityListScrollingActivity.this, CitySearchingActivity.class);
                startActivity(intent);
            }
        });
    }

}
