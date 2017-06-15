package com.example.guhao.myweather.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.guhao.myweather.Bean.CityBean;
import com.example.guhao.myweather.Bean.ForecastBean;
import com.example.guhao.myweather.Bean.SearchBean;
import com.example.guhao.myweather.R;
import com.example.guhao.myweather.data.database.MyDatabaseHelper;
import com.example.guhao.myweather.util.StringUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhao on 6/14/17.
 */

public class DBOperation {
    private final String TAG = "";
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;

    public DBOperation(Context context){
        dbHelper = new MyDatabaseHelper(context, "china-city-list.db", null, 2);
        db = dbHelper.getWritableDatabase();

        //read all elements from china_city_list and insert into db
        readFile(context, db);
    }

    public List<CityBean> getCityResult(String str){

        List<CityBean> cityList = new ArrayList<>();
        String sql;
        if (StringUtil.isChinese(str)){
            sql = "select * from china_city_list where up_cn glob '*"
                    + str + "*' or city_cn glob '*" + str + "*'";
        }else{
            sql = "select * from china_city_list where up_en glob '*"
                    + str + "*' or city_en glob '*" + str + "*'";
        }

        Cursor cursor = db.rawQuery(sql,null);

        if (cursor.moveToFirst()){
            do {
                String area_code = cursor.getString(cursor.getColumnIndex("area_code"));
                String city_en = cursor.getString(cursor.getColumnIndex("city_en"));
                String city_cn = cursor.getString(cursor.getColumnIndex("city_cn"));
                String cnty_en = cursor.getString(cursor.getColumnIndex("cnty_en"));
                String cnty_cn = cursor.getString(cursor.getColumnIndex("cnty_cn"));
                String state_en = cursor.getString(cursor.getColumnIndex("state_en"));
                String state_cn = cursor.getString(cursor.getColumnIndex("state_cn"));
                String up_en = cursor.getString(cursor.getColumnIndex("up_en"));
                String up_cn = cursor.getString(cursor.getColumnIndex("up_cn"));

                //Log.d(TAG, "getCityResult: " + area_code);
                Log.d(TAG, "getCityResult: " + city_cn);
                //Log.d(TAG, "getCityResult: " + state_en);

                cityList.add(new CityBean(area_code,city_en,city_cn,cnty_en,cnty_cn,state_en,state_cn,up_en,up_cn));

            }while (cursor.moveToNext());
        }

        Log.d(TAG, "getCityResult: " + "The size of city list is " + cityList.size());
        return cityList;
    }

    public void addElementsToDB(String[] data,SQLiteDatabase db){
        String sql = "insert into china_city_list values("
                + "'" + data[0] + "',"
                + "'" + data[1] + "',"
                + "'" + data[2] + "',"
                + "'" + data[3] + "',"
                + "'" + data[4] + "',"
                + "'" + data[5] + "',"
                + "'" + data[6] + "',"
                + "'" + data[7] + "',"
                + "'" + data[8] + "',"
                + "'" + data[9] + "',"
                + "'" + data[10] + "',"
                + "'" + data[11] + "')";

        //Log.d(TAG, "addElementsToDB: " + sql);
        db.execSQL(sql);
    }

    public void readFile(Context context,SQLiteDatabase db){
        InputStream is = context.getResources().openRawResource(R.raw.china_city_list);
        try {
            InputStreamReader isr = new InputStreamReader(is,"utf8");
            BufferedReader br = new BufferedReader(isr);
            String line;

            line = br.readLine();
            line = br.readLine();
            line = br.readLine();
            //Log.d(TAG, "readFile: Check if I'm here ");
            while((line=br.readLine()) != null){
                //Log.d(TAG, line);

                String[] data = line.split("\\s");
                addElementsToDB(data, db);

                //Log.d(TAG, "readFile: ");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
