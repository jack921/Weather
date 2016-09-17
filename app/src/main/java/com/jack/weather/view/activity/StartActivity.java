package com.jack.weather.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.jack.weather.R;
import com.jack.weather.app.BaseActivity;
import com.jack.weather.model.ProvincesModel;
import com.jack.weather.service.CityService;
import com.jack.weather.service.NotificationService;
import com.jack.weather.util.LocalData;
import java.util.List;

/**
 * Created by Jack on 2016/6/25.
 * 整个App的启动页面
 */

public class StartActivity extends BaseActivity {

    private Context context=null;
    private LocalData mLocalData=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemBarTint();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_startactivity);
        this.context=this;
        mLocalData=new LocalData();
        new startDeal().execute();

        CheckNotification();
    }

    /*
    * 暂时的启动操作
    * */
    class startDeal extends AsyncTask<Void,Void,Integer>{
        @Override
        protected Integer doInBackground(Void... params){
            try{
                List<ProvincesModel> listModel=mLocalData.GetCountryData(context);
                if(!(listModel!=null&&listModel.size()>0)){
                    Intent intent=new Intent(StartActivity.this, CityService.class);
                    startService(intent);
                }
                Thread.sleep(2000);
            }catch(Exception e){}
            return 1;
        }
        @Override
        protected void onPostExecute(Integer result) {
            if(result==1){
                startActivity(new Intent(context,MainActivity.class));
                finish();
            }
        }
    }

    public void CheckNotification(){
        if(LocalData.getSharedPreferencesData(this,"notification","status").equals("true")){
            Intent intent=new Intent(this, NotificationService.class);
            startService(intent);
        }
    }

}
