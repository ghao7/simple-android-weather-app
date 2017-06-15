package com.example.guhao.myweather.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.guhao.myweather.R;
import com.example.guhao.myweather.data.database.MyDatabaseHelper;
import com.example.guhao.myweather.util.CopyFileUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by guhao on 6/14/17.
 */

public class DBOperation {
    private final String TAG = "";
    private MyDatabaseHelper dbHelper;

    public DBOperation(Context context){
        dbHelper = new MyDatabaseHelper(context, "china-city-list.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //read all elements from china_city_list and insert into db
        readFile(context, db);
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

        Log.d(TAG, "addElementsToDB: " + sql);
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
            Log.d(TAG, "readFile: Check if I'm here ");
            while((line=br.readLine()) != null){
                Log.d(TAG, line);

                String[] data = line.split("\\s");
                addElementsToDB(data, db);

                //Log.d(TAG, "readFile: ");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
