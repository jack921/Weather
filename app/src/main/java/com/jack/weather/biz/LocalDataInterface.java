package com.jack.weather.biz;

import android.content.Context;

import com.jack.weather.model.Countries;

/**
 * Created by Jack on 2016/6/25.
 */
public interface LocalDataInterface {

    //保存全国所有省份的数据
    public void SaveCountryData(Context context, Countries country);

}
