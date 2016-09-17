package com.jack.weather.service;

import android.app.IntentService;
import android.content.Intent;

import com.jack.weather.util.NetData;

/**
 * Created by Jack on 2016/8/12.
 */

public class CityService extends IntentService {

    public CityService() {
        super("cityService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String name="jack";
        NetData.GetCountryData();
    }


}
