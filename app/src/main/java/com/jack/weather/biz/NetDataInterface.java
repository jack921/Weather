package com.jack.weather.biz;

/**
 * Created by Jack on 2016/6/26.
 */
public interface NetDataInterface {
    public void GetCountryData();
    public void GetWeatherInfo(final String province,final String city);
}
