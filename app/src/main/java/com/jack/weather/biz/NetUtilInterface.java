package com.jack.weather.biz;

import com.jack.weather.model.Countries;
import com.jack.weather.model.WeatherInfo;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Jack on 2016/6/25.
 *
 */

public interface NetUtilInterface {

    @GET("citys")
    Observable<Countries> getCountryData(@Query("key") String key);

    @GET("query")
    Observable<WeatherInfo> getWeatherInfo(@Query("key") String province,@Query("city") String city);

}
