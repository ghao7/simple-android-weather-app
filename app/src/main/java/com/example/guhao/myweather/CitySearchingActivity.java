package com.example.guhao.myweather;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.guhao.myweather.adapter.SearchListAdapter;
import com.example.guhao.myweather.bean.CityEntity;
import com.example.guhao.myweather.data.WeatherConstant;
import com.example.guhao.myweather.presenter.DBOperation;
//import com.example.guhao.myweather.presenter.WeatherPre;

import java.util.ArrayList;
import java.util.List;

public class CitySearchingActivity extends BaseActivity {
    private final String TAG = "citysearch";


    private DBOperation dbOperation;
    private SearchView searchView;
    private ListView listView;
//    private WeatherPre weatherPre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_searching);

        initData();
        findView();
        initView();
        initListener();

    }

    public void initData(){
        dbOperation = new DBOperation(this);
//        weatherPre = new WeatherPre();
    }

    public void findView(){
        searchView = (SearchView)findViewById(R.id.searchView);
        listView = (ListView)findViewById(R.id.list_view);
    }

    public void initView(){
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
    }

    public void initListener(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText)) {
                    final List<CityEntity> cityList = dbOperation.getCityResult(newText);
                    setCityList(cityList);
                    searchListListener(cityList);
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


    }

    public void searchListListener(final List<CityEntity> cityList){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityEntity cityEntity = cityList.get(position);
                //showShort(cityEntity.getCity_cn());

                //Intent intent = new Intent(CitySearchingActivity.this, CityListScrollingActivity.class);
                WeatherConstant.citySlotList.add(cityEntity.getCity_cn());
                Intent intent = new Intent();
                intent.putExtra("city",cityEntity.getArea_code());
                setResult(RESULT_OK, intent);
                //startActivity(intent);



                finish();
            }
        });
    }

    public void setCityList(List<CityEntity> list){
        listView.setAdapter(new SearchListAdapter(this,R.layout.city_list_dropdown_item,list));
    }
}
