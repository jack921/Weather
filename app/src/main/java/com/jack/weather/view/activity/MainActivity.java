package com.jack.weather.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.jack.weather.R;
import com.jack.weather.adapter.MainContentAdapter;
import com.jack.weather.app.BaseActivity;
import com.jack.weather.app.WeatherApplication;
import com.jack.weather.biz.ObjectComplete;
import com.jack.weather.model.WeatherInfo;
import com.jack.weather.util.Constant;
import com.jack.weather.util.NetUtil;
import com.jack.weather.util.RetrofitUtil;
import com.jack.weather.view.widget.ScrollBlurView;
import java.util.ArrayList;
import java.util.List;

/*
* App总的控制类，是类中的大哥大，老大
* */

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public List<Float> YValueMax=null;
    public List<Float> YValueMin=null;

    private Context context=null;
    private MainContentAdapter mMainContentAdapter=null;
    private RecyclerView mMainRecyclerView=null;
    private DrawerLayout drawer;
    private ScrollBlurView mScrollBlurView;
    private TextView headerLocation;

    private int ScrollY=255;
    private String city;
    private TextView main_nowcity;
    private MainWeatherChange mainWaatherChange=null;
    private ImageView user_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemBarTint();
        setContentView(R.layout.activity_main);
        initView();

        Glide.with(this).load(R.drawable.app_main_header).into(user_view);

        city=WeatherApplication.showCity.replace("市","");
        if(!city.equals("")){
            initNetData(city);
        }else{
            if(WeatherApplication.nowCity!=null){
                city=WeatherApplication.nowCity.replace("市","");
                WeatherApplication.showCity=city;
                initNetData(city);
            }else{
                city="深圳";
                WeatherApplication.showCity=city;
                initNetData(city);
            }
        }
        main_nowcity.setText(city);
        headerLocation.setText(city);
    }

    public void initView(){
        IntentFilter intentFilter=new IntentFilter(getString(R.string.update_main_city));
        mainWaatherChange=new MainWeatherChange();
        registerReceiver(mainWaatherChange,intentFilter);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        main_nowcity=(TextView)findViewById(R.id.main_nowcity);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        user_view=(ImageView)navigationView.getHeaderView(0).findViewById(R.id.user_header_bg);
        headerLocation=(TextView)navigationView.getHeaderView(0).findViewById(R.id.nav_header_location);
        this.context=this;
        YValueMax=new ArrayList<>();
        YValueMin=new ArrayList<>();
        mScrollBlurView=(ScrollBlurView)findViewById(R.id.mScrollBlurView);
        mMainRecyclerView=(RecyclerView)findViewById(R.id.content_main_recyclerview);
        mMainRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //网络获取当前地区数据
    public void initNetData(String city){
        NetUtil.sendHttp(RetrofitUtil.getRetrofitHttp(Constant.WeatherQueryFromCity)
                .getWeatherInfo(Constant.key,city), new ObjectComplete() {
            @Override
            public void Result(Object object) {
                if(object!=null){
                    WeatherInfo info=(WeatherInfo) object;
                    initYValue(info);
                    mMainContentAdapter=new MainContentAdapter(context,YValueMax,YValueMin,info);
                    mMainRecyclerView.setAdapter(mMainContentAdapter);
                    mMainRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            ScrollY-=(int)(dy*0.2);
                            if(ScrollY<=0){
                                ScrollY=0;
                            }
                            if(ScrollY>=255){
                                ScrollY=255;
                            }
                            mScrollBlurView.setScrollBlur(ScrollY);
                        }
                    });
                }
            }
        });

    }

    public void initYValue(WeatherInfo info){
       try{
           YValueMax.clear();
           YValueMin.clear();
           for(int i=1;i<=info.getResult().get(0).getFuture().size();i++){
               String temperature=info.getResult().get(0).getFuture().get(i).getTemperature();
               int conterIndex=temperature.indexOf("/");
               String max=temperature.substring(0,conterIndex-3);
               String min=temperature.substring(conterIndex+2,temperature.length()-2);
               YValueMax.add(Float.valueOf(max));
               YValueMin.add(Float.valueOf(min));
           }
       }catch (Exception e){
       }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_city:
                startActivity(new Intent(this,Activity_ManagerCity.class));
                break;
            case R.id.nav_share:
                startActivity(new Intent(this,SettingActivity.class));
                break;
            case R.id.nav_send:
                Intent intent=new Intent(this,AboutActivity.class);
                startActivity(intent);
                break;
        }
        item.setChecked(false);
        drawer.closeDrawers();
        return true;
    }

    @Override
    public void onClick(View v) {
       switch(v.getId()){
       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mainWaatherChange);
    }

    class MainWeatherChange extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            main_nowcity.setText(intent.getStringExtra("city"));
            initNetData(intent.getStringExtra("city"));
        }
    }


}

