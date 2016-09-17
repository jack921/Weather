package com.jack.weather.util;

import com.jack.weather.adapter.ManagerCityAdapter;
import com.jack.weather.app.WeatherApplication;
import com.jack.weather.biz.ObjectComplete;
import com.jack.weather.model.Countries;
import com.jack.weather.model.WeatherInfo;

/**
 * Created by Jack on 2016/6/25.
 * 网络操作类
 */


public class NetData {

    private static LocalData mLocalData;


    /*
    * 获取国家所有的地区的数据
    * */
    public static void GetCountryData() {
        if(mLocalData==null)
          mLocalData=new LocalData();
        NetUtil.sendHttp(RetrofitUtil.getRetrofitHttp(Constant.WeatherAllCity)
                .getCountryData(Constant.key), new ObjectComplete() {
            @Override
            public void Result(Object object) {
                  mLocalData.SaveCountryData(WeatherApplication.getWeatherApplication(),(Countries)object);
            }
        });
    }

    /*
    * 获取具体的天气数据
    * */
    public static void GetWeatherInfo(final String city,final ManagerCityAdapter.managerViewHoler viewHoler) {
        if(mLocalData==null)
            mLocalData=new LocalData();
        NetUtil.sendHttp(RetrofitUtil.getRetrofitHttp(Constant.WeatherQueryFromCity)
                .getWeatherInfo(Constant.key,city), new ObjectComplete() {
            @Override
            public void Result(Object object) {
                if(object!=null&&viewHoler!=null){
                    WeatherInfo info=(WeatherInfo) object;
                    viewHoler.city_location.setText(info.getResult().get(0).getDistrct());
                    viewHoler.city_max.setText(info.getResult().get(0).getFuture().get(0).getTemperature()+"");
                    viewHoler.city_explain.setText(info.getResult().get(0).getWeather());
                }
            }
        });
    }

}
