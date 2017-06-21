package com.example.guhao.myweather;

import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.guhao.myweather.adapter.CityListAdapter;
import com.example.guhao.myweather.bean.CityEntity;
import com.example.guhao.myweather.presenter.DBOperation;

import java.util.ArrayList;
import java.util.List;

public class CitySearchingActivity extends BaseActivity {
    private final String TAG = "citysearch";

    private DBOperation dbOperation;
    private SearchView searchView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_searching);

        initData();
        findView();
        initListener();

    }

    public void initData(){
        dbOperation = new DBOperation(this);
    }

    public void findView(){
        searchView = (SearchView)findViewById(R.id.searchView);
        listView = (ListView)findViewById(R.id.list_view);
    }

    public void initListener(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText)) {
                    setCityList(dbOperation.getCityResult(newText));
                }else{
                    setCityList(new ArrayList<CityEntity>());
                }
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                //showShort(query);
                return false;
            }
        });

        //listView.setOnClickListener();
    }

    public void setCityList(List<CityEntity> list){
        listView.setAdapter(new CityListAdapter(this,R.layout.city_list_dropdown_item,list));
    }
}
