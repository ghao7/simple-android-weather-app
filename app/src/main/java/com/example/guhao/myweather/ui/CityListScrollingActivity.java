package com.example.guhao.myweather.ui;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.guhao.myweather.R;
import com.example.guhao.myweather.ui.adapter.CityRecycleViewAdapter;
import com.example.guhao.myweather.ui.adapter.MyItemOnClickListener;
import com.example.guhao.myweather.ui.adapter.SimpleItemTouchHelperCallback;
import com.example.guhao.myweather.data.bean.WeatherEntity;
import com.example.guhao.myweather.data.WeatherConstant;
import com.example.guhao.myweather.data.network.SubscriberOnNextListener;
import com.example.guhao.myweather.data.presenter.WeatherPre;
import com.example.guhao.myweather.util.FileUtil;
import com.example.guhao.myweather.util.IconUtil;
import com.example.guhao.myweather.util.MathUtil;
import com.example.guhao.myweather.util.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import jp.wasabeef.glide.transformations.internal.FastBlur;

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
    }

    public void recyclerviewListener(){
        rvAdapter.setItemOnClickListener(new MyItemOnClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent();
                intent.putExtra("position",position);
                setResult(RESULT_OK,intent);
//                finish();
                finishAfterTransition();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: cityslotlist" + WeatherConstant.citySlotList.size());
        Log.d(TAG, "onStart: weatherlist" + WeatherConstant.weatherList.size());
        StringUtil.showPref(getApplicationContext());

    }

    public void update(){
        for (int i = 0; i < WeatherConstant.weatherList.size(); i++){
            //String info = StringUtil.getDisplay(WeatherConstant.weatherList.get(i));
            updateMultipleData(i,WeatherConstant.weatherList.get(i));
        }
    }

    public void updateMultipleData(int position, WeatherEntity entity){
        if (entity != null) {
            WeatherEntity.HeWeather5Bean heWeather5Bean = entity.getHeWeather5().get(0);
            String city = heWeather5Bean.getBasic().getCity();
            String cond = heWeather5Bean.getNow().getCond().getCode();
            String temp = heWeather5Bean.getNow().getTmp();
            rvAdapter.addItems();
            rvAdapter.updateData(city, cond, temp, position);
        }else{
            showShort("null");
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
                View searchMenu = tb_toolbar.findViewById(R.id.action_search);
                Intent intent = new Intent(CityListScrollingActivity.this, CitySearchingActivity.class);
                Bundle imageBundle = new Bundle();
                Bitmap bitmap = getBitmapFromView(recyclerView);
                Bitmap blur = FastBlur.blur(bitmap,45,true);
                int width = blur.getWidth();
                int height = blur.getHeight();
                //int cut = MathUtil.convertDpToPixels(56,this);
                //blur = Bitmap.createBitmap(blur,0, cut,width,height-cut);

                createImageFromBitmap(blur);

                Bundle options = ActivityOptions.makeSceneTransitionAnimation(this, searchMenu,
                        getString(R.string.search)).toBundle();
                startActivityForResult(intent, 1);

                //overridePendingTransition(0,0);
                break;

            default:
        }
        return true;
    }

    public String createImageFromBitmap(Bitmap bitmap) {
        String fileName = "myImage";//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
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
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(rvAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(rvAdapter);

        setSupportActionBar(tb_toolbar);
        tb_toolbar.setTitle(R.string.citylist);
        tb_toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


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

    public void findView() {
        recyclerView = (RecyclerView) findViewById(R.id.city_list_recycler_view);
        tb_toolbar = (Toolbar) findViewById(R.id.tb_toolbar);
//        fab = (FloatingActionButton) findViewById(R.id.fab);
    }


    public void weatherSubscriberListener() {
        getWeatherOnNext = new SubscriberOnNextListener<WeatherEntity>() {
            @Override
            public void onNext(WeatherEntity entity) {
                updateMultipleData(WeatherConstant.weatherList.size(),entity);
                WeatherConstant.addWeatherEntity(entity);
            }
        };
    }


}
