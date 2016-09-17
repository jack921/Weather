package com.jack.weather.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jack on 2016/6/26.
 * 全国所有省份的数据表
 */

public class CountryDatabaseOpenHelper extends SQLiteOpenHelper{

    private String TableCountry="create table country("+
            "id integer primary key autoincrement," +
            "province text"
            +")";

    public CountryDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableCountry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}


