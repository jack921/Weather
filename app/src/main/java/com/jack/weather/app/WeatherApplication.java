package com.jack.weather.app;

/*
 _ooOoo_
 o8888888o
 88" . "88
 (| -_- |)
 O\  =  /O
 ____/`---'\____
 .'  \\|     |//  `.
 /  \\|||  :  |||//  \
 /  _||||| -:- |||||-  \
 |   | \\\  -  /// |   |
 | \_|  ''\---/''  |   |
 \  .-\__  `-`  ___/-. /
 ___`. .'  /--.--\  `. . __
 ."" '<  `.___\_<|>_/___.'  >'"".
 | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 \  \ `-.   \_ __\ /__ _/   .-` /  /
 ======`-.____`-.___\_____/___.-`____.-'======
 `=---='
 ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 佛祖保佑       永无BUG
 */


import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.jack.weather.util.LocalData;
import com.jack.weather.util.LogUtil;
import com.jack.weather.util.Util;

/**
 * Created by Jack on 2016/6/25.
 */
public class WeatherApplication extends Application{

    private static Context context=null;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = null;
    public static String nowCity="";
    public boolean CityFirst=true;
    public static String showCity="";

    @Override
    public void onCreate() {
        super.onCreate();
        myListener = new WeatherLocationListener();
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.setLocOption(Util.initLocationOptions());
        mLocationClient.registerLocationListener(myListener);
        mLocationClient.start();
        context=getApplicationContext();
        initCity();
    }

    //获取提醒城市
    public void initCity(){
        showCity = LocalData.getSharedPreferencesData(this,"nowcity","city");
        if(showCity.equals("")){
            showCity=nowCity;
        }
    }

    public static Context getWeatherApplication(){
        if(context!=null){
            return context;
        }else{
            return null;
        }
    }

    //回调接口回去当前位置
    public class WeatherLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(CityFirst){
                CityFirst=false;
                nowCity=bdLocation.getCity();
            }
        }
    }


}
