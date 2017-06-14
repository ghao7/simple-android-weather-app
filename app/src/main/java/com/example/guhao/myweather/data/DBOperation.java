package com.example.guhao.myweather.data;

import android.content.Context;

import com.example.guhao.myweather.data.database.MyDatabaseHelper;

/**
 * Created by guhao on 6/14/17.
 */

public class DBOperation {
    private MyDatabaseHelper dbHelper;

    public DBOperation(Context context){
        dbHelper = new MyDatabaseHelper(context, "china-city-list.db", null, 1);
        dbHelper.getWritableDatabase();
    }

}
