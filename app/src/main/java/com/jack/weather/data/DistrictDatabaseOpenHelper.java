package com.jack.weather.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jack on 2016/6/26.
 * 全国所有城市下属的所有市县的数据表
 */

public class DistrictDatabaseOpenHelper extends SQLiteOpenHelper{

    private String TableDistrict="create table district("
            +"id integer primary key autoincrement,"
            +"city text,"
            +"dist text"
            +")";

    public DistrictDatabaseOpenHelper(Context context, String name,
                                      SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableDistrict);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

}
