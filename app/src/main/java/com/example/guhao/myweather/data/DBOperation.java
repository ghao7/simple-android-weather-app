package com.example.guhao.myweather.data;

import android.content.Context;

import com.example.guhao.myweather.data.database.MyDatabaseHelper;
import com.example.guhao.myweather.util.CopyFileUtil;

/**
 * Created by guhao on 6/14/17.
 */

public class DBOperation {
    private MyDatabaseHelper dbHelper;

    public DBOperation(Context context){
        dbHelper = new MyDatabaseHelper(context, "china-city-list.db", null, 1);
        dbHelper.getWritableDatabase();

        copyDbFile();
    }

    public void copyDbFile(){
        CopyFileUtil.copyFile("/data/data/com.example.guhao.myweather/databases/china-city-list.db",
                "/res/test.db");
    }
}
