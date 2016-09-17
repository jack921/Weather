package com.jack.weather.util;

/**
 * Created by Jack on 2016/6/25.
 *该类是放主域名，api接口
 *
 */

public class Constant {

    //以后要加密呀，不然就麻烦了
    public static String key="1449d7303f4aa";

    //根据城市名查询天气
    public static String WeatherQueryFromCity="http://apicloud.mob.com/v1/weather/query";

    //城市列表查询接口
    public static String WeatherAllCity="http://apicloud.mob.com/v1/weather/";

    //根据IP查询天气
    public static String WeatherQueryFromIp="http://apicloud.mob.com/v1/weather/ip";

}
