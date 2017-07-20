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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //setUpBackGround();

    }

    private void setUpBackGround(){
        int scaleRatio = 10;
        int blurRadius = 8;

        Bitmap bitmap = getBitmapFromView(scroll_ll);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
                bitmap.getWidth() / scaleRatio,
                bitmap.getHeight() / scaleRatio,
                false);
        Bitmap blur = FastBlur.blur(bitmap,blurRadius,true);

        listview_ll.setBackground(new BitmapDrawable(blur));
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

    public Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);

        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
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
                //search international cities
                //showShort("search");
                WeatherPre.getCityRequest(query, subscriberOnNextListener);
                return false;
            }
        });


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
