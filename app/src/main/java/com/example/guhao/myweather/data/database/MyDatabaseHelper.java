package com.example.guhao.myweather.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by guhao on 6/14/17.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE = "create table 中国城市列表 ("
            + "地区编码 text primary key, "
            + "英文 text, "
            + "中文 text, "
            + "国家代码 text, "
            + "国家英文 text, "
            + "国家中文 text, "
            + "省英文 text, "
            + "省中文 text, "
            + "所属上级市英文 text, "
            + "所属上级市中文 text, "
            + "纬度 real, "
            + "经度 real)";

    private Context context;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Toast.makeText(context, "Table created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
