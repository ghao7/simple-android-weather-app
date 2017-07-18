package com.example.guhao.myweather.data.presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.guhao.myweather.data.bean.CityEntity;
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
    String sql_detail_cn;
    String sql_detail_en;
    String sql_cn;
    String sql_en;

    public DBOperation(Context context){

        dbHelper = new MyDatabaseHelper(context, "china-city-list.db", null, 2);
        db = dbHelper.getWritableDatabase();

        //read all elements from china_city_list and insert into db
        readFile(context, db);
    }

    public void initData(String str){
        sql_detail_cn = "select * from china_city_list where up_cn glob '*"
                + str + "*' or city_cn glob '*" + str + "*'";

        sql_detail_en = "select * from china_city_list where up_en glob '*"
                + str + "*' or city_en glob '*" + str + "*'";

        sql_cn = "select * from china_city_list where city_cn glob '*" + str + "*'";
        sql_en = "select * from china_city_list where city_en glob '*" + str + "*'";
    }

    public List<CityEntity> getCityResult(String str){
        initData(str);
        String sql;
        List<CityEntity> cityList = new ArrayList<>();
        if (StringUtil.isChinese(str)){
            sql = sql_detail_cn;
        }else{
            sql = sql_detail_en;
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
                //Log.d(TAG, "getCityResult: " + city_cn);
                //Log.d(TAG, "getCityResult: " + state_en);

                cityList.add(new CityEntity(area_code,city_en,city_cn,cnty_en,cnty_cn,state_en,state_cn,up_en,up_cn));

            }while (cursor.moveToNext());
        }

        //Log.d(TAG, "getCityResult: " + "The size of city list is " + cityList.size());
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
