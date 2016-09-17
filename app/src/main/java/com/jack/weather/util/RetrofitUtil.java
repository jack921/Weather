package com.jack.weather.util;

import com.jack.weather.biz.NetUtilInterface;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Jack on 2016/6/25.
 */

public class RetrofitUtil {

    public static NetUtilInterface getRetrofitHttp(String host){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        NetUtilInterface netData=retrofit.create(NetUtilInterface.class);
        return netData;
    }


}
