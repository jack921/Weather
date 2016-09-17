package com.jack.weather.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import com.jack.weather.R;
import com.jack.weather.app.WeatherApplication;
import com.jack.weather.biz.ObjectComplete;
import com.jack.weather.model.WeatherInfo;
import com.jack.weather.util.Constant;
import com.jack.weather.util.LocalData;
import com.jack.weather.util.LogUtil;
import com.jack.weather.util.NetUtil;
import com.jack.weather.util.RetrofitUtil;
import com.jack.weather.view.activity.MainActivity;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jack
 */

public class NotificationService extends Service {

    private final int NOTIFICATION_FLAG = 1;
    private SimpleDateFormat newSimpleDataFormat=null;
    private SimpleDateFormat oldSimpleDataFormat=null;
    private NotificationManager manager=null;
    private Notification notify=null;
    private RemoteViews remoteViews;
    private Timer timer=null;
    private final int HourTime=3600;
    private int delayTime=0;
    private String time="";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        time= LocalData.getSharedPreferencesData(getBaseContext(),"notification","time");
        if(time.equals("")){
            time="1";
        }
        manager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        oldSimpleDataFormat=new SimpleDateFormat("yyyy-MM-dd");
        newSimpleDataFormat=new SimpleDateFormat("yyyy年MM月dd日");
        timer=new Timer();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showNotificationWeather();
        return  super.onStartCommand(intent,START_STICKY,startId);
    }

    public void showNotificationWeather(){
        if(WeatherApplication.showCity==null||time.equals("")){
            return;
        }
        delayTime=Integer.valueOf(time)*HourTime;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            NetUtil.sendHttp(RetrofitUtil.getRetrofitHttp(Constant.WeatherQueryFromCity)
                    .getWeatherInfo(Constant.key,WeatherApplication.showCity), new ObjectComplete() {
                @Override
                public void Result(Object object) {
                    if(object!=null){
                        WeatherInfo weather=(WeatherInfo) object;
                        initNotification(weather);
                    }
                }
             });
            }
        },0,delayTime);
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    public void initNotification(WeatherInfo weather){
        try{
            if(notify==null){
                remoteViews=new RemoteViews(getPackageName(),R.layout.notification_view);
            }
            remoteViews.setTextViewText(R.id.notification_name,weather.getResult().get(0).getCity());
            remoteViews.setTextViewText(R.id.notification_time,weather.getResult().get(0).getTime());
            remoteViews.setTextViewText(R.id.notification_content,weather.getResult().get(0).getWeather()
                    +"  当前温度:"+weather.getResult().get(0).getTemperature());
            remoteViews.setImageViewResource(R.id.notification_img,R.mipmap.ic_launcher);
            remoteViews.setTextViewText(R.id.notification_temperature,weather.getResult().get(0).getFuture().get(0).getTemperature());
            String weatherNight=weather.getResult().get(0).getWeather();
            if(weatherNight.contains("雨")){
                remoteViews.setImageViewResource(R.id.notification_weatherimg,R.drawable.ic_weather_heavy_rain);
            }else if(weatherNight.contains("雪")){
                remoteViews.setImageViewResource(R.id.notification_weatherimg,R.drawable.ic_weather_heavy_snow);
            }else if(weatherNight.contains("云")){
                remoteViews.setImageViewResource(R.id.notification_weatherimg,R.drawable.ic_weather_cloudy_day);
            }else {
                remoteViews.setImageViewResource(R.id.notification_weatherimg,R.drawable.ic_weather_select);
            }
            if(notify==null){
                PendingIntent pendingIntent = PendingIntent.getActivity(this,0,new Intent(this, MainActivity.class),0);
                notify= new Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setTicker(weatherNight)
                        .setContentTitle(weather.getResult().get(0).getCity())
                        .setContentText(weatherNight)
                        .setContentIntent(pendingIntent)
                        .getNotification();
                notify.contentView=remoteViews;
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
            }
            manager.notify(NOTIFICATION_FLAG, notify);
        }catch(Exception e){
            LogUtil.e("NotificationException",e.getMessage());
        }
    }

}
