package com.example.guhao.myweather.ui;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.guhao.myweather.R;
import com.example.guhao.myweather.ui.adapter.SearchListAdapter;
import com.example.guhao.myweather.data.bean.CityEntity;
import com.example.guhao.myweather.data.bean.SearchEntity;
import com.example.guhao.myweather.data.WeatherConstant;
import com.example.guhao.myweather.data.network.SubscriberOnNextListener;
import com.example.guhao.myweather.data.presenter.DBOperation;
import com.example.guhao.myweather.data.presenter.WeatherPre;
import com.example.guhao.myweather.ui.transition.CircularReveal;
import com.example.guhao.myweather.util.IconUtil;
import com.example.guhao.myweather.util.TransitionsUtil;
//import com.example.guhao.myweather.data.presenter.WeatherPre;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.internal.FastBlur;

public class CitySearchingActivity extends BaseActivity {
    private final String TAG = "citysearch";

    private SubscriberOnNextListener subscriberOnNextListener;
    private DBOperation dbOperation;
    private SearchView searchView;
    private ListView listView;
    private LinearLayout listview_ll;
    private LinearLayout scroll_ll;
//    private WeatherPre weatherPre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_searching);

        initData();
        findView();
        initView();
        initListener();
        //setUpTransitions();

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(this
                    .openFileInput("myImage"));
            listview_ll.setBackground(new BitmapDrawable(bitmap));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void initData(){
        dbOperation = new DBOperation(this);
//        weatherPre = new WeatherPre();
    }

    public void findView(){
        searchView = (SearchView)findViewById(R.id.searchView);
        listView = (ListView)findViewById(R.id.list_view);
        listview_ll = (LinearLayout)findViewById(R.id.listview_ll);
        scroll_ll = (LinearLayout)findViewById(R.id.scroll_ll);
    }

    public void initView(){
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark,null));
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.setQueryHint("");
        searchView.requestFocusFromTouch();


        //listview_ll.setBackground(getResources().getDrawable(R.color.white));
        Bundle bundle = getIntent().getExtras();

        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        if (searchEditText != null) {
            searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        }

        subscriberOnNextListener = new SubscriberOnNextListener<SearchEntity>(){
            @Override
            public void onNext(SearchEntity searchEntity) {
                List<CityEntity> newList = new ArrayList<>();
                SearchEntity.HeWeather5Bean.BasicBean bean = searchEntity.getHeWeather5().get(0).getBasic();
                String area_code = bean.getId();
                String city_cn = bean.getCity();
                CityEntity entity = new CityEntity(area_code,"",city_cn,"","","","","","");
                newList.add(entity);
                setCityList(newList);
                searchListListener(newList);
            }
        };
    }

    public void initListener(){
//        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    showInputMethod(v.findFocus());
//                }
//            }
//        });
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
                //search international cities
                //showShort("search");
                WeatherPre.getCityRequest(query, subscriberOnNextListener);
                return false;
            }
        });


    }

    private void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,0);
    }

    public void searchListListener(final List<CityEntity> cityList){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityEntity cityEntity = cityList.get(position);
                //showShort(cityEntity.getCity_cn());

                WeatherConstant.addCitySlot(cityEntity.getCity_cn(),getApplicationContext());
                Intent intent = new Intent();
                intent.putExtra("city", cityEntity.getArea_code());
                setResult(RESULT_OK, intent);
//                showShort(cityEntity.getCity_cn());
                finish();
            }
        });
    }

    public void setCityList(List<CityEntity> list){
        listView.setAdapter(new SearchListAdapter(this,R.layout.city_list_dropdown_item,list));
    }

    public void setUpTransitions(){
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                if (sharedElements != null && !sharedElements.isEmpty()) {
                    View searchIcon = sharedElements.get(0);
                    if (searchIcon.getId() != R.id.action_search) return;
                    //transition
                    int centerX = (searchIcon.getLeft() + searchIcon.getRight()) / 2;
                    CircularReveal hideResults = (CircularReveal) TransitionsUtil.findTransition(
                            (TransitionSet) getWindow().getReturnTransition(),
                            CircularReveal.class, R.id.searchView);
                    if (hideResults != null) {
                        hideResults.setCenter(new Point(centerX, 0));
                    }
                }
            }
        });
    }
}
