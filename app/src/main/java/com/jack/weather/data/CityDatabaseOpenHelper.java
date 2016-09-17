package com.jack.weather.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jack on 2016/6/26.
 * 全国所有省份对应的城市的数据表
 */

public class CityDatabaseOpenHelper extends SQLiteOpenHelper{

    private String TableCity="create table city("
            +"id integer primary key autoincrement,"
            +"province text,"
            +"city text"
            +")";


    public CityDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableCity);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
