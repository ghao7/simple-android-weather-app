package com.example.guhao.myweather.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by guhao on 6/14/17.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE = "create table if not exists china_city_list ("
            + "area_code text primary key, "
            + "city_en text, "
            + "city_cn text, "
            + "cnty_code text, "
            + "cnty_en text, "
            + "cnty_cn text, "
            + "state_en text, "
            + "state_cn text, "
            + "up_en text, "
            + "up_cn text, "
            + "latitude real, "
            + "longitude real)";

    private static final String SQL = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";

    private Context context;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        //db.execSQL(SQL);
        //Toast.makeText(context, "Table created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("drop table if exists china_city_list");
        //onCreate(db);
    }
}
