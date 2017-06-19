package com.example.guhao.myweather;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

public class CityListScrollingActivity extends Activity {
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list_scrolling);

        findView();
        fabListener();
    }

    public void initData(){

    }

    public void findView(){
        fab = (FloatingActionButton)findViewById(R.id.fab);
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
