package com.example.guhao.myweather;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guhao.myweather.adapter.CityRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class CityListScrollingActivity extends Activity {
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    List<String> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list_scrolling);

        findView();
        initData();
        fabListener();
    }

    public void initData(){
        initList();
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CityRecycleViewAdapter(mlist));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration();
    }

    public void initList(){
        mlist = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            mlist.add(i+"");
        }
    }

    public void findView(){
        fab = (FloatingActionButton)findViewById(R.id.fab);
        recyclerView = (RecyclerView)findViewById(R.id.city_list_recycler_view);

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
