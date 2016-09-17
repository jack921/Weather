package com.jack.weather.util;

import com.jack.weather.biz.ObjectComplete;
import com.jack.weather.biz.StringComplete;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jack on 2016/6/25.
 * 网络请求类，看的出将来用Retrfir将来换网络框架怎么办
 */

public class NetUtil {

    //发送http请求
    public static void sendHttp(Observable observable, final ObjectComplete complete){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onFailure",e.getMessage());
                    }
                    @Override
                    public void onNext(Object o) {
                        try {
                            complete.Result(o);
                        } catch (Exception e) {
                        }
                    }});
    }

    //发送Http请求
    public static void sendHttp(Call<ResponseBody> call, final StringComplete complete){
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                LogUtil.e("onResponse",response.body().toString());
                complete.Result(response.body().toString());
            }
            @Override
            public void onFailure(Throwable t) {
                LogUtil.e("onFailure",t.getMessage());
            }
        });
    }

}
