package com.jack.weather.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jack on 2016/7/10.
 */

public class ManageDistrictDatabaseOpenHelper extends SQLiteOpenHelper {

//    private boolean fouce;
//    private int maxTemperature;
//    private int minTemperature;
//    private String status;
//    private String explain;
//    private String location;
//    private boolean isAddView;
//

    private String createManage="create table managedistric("
            +"id integer primary key,"
            +"fouce text,"
            +"max integer,"
            +"min integer,"
            +"status text,"
            +"explain text,"
            +"location text,"
            +"isaddview text"
            + ")";


    public ManageDistrictDatabaseOpenHelper(Context context, String name,
                                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createManage);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
