package com.jack.weather.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.jack.weather.R;
import com.jack.weather.adapter.RemindCiryAdapter;
import com.jack.weather.app.ToolbarActivity;
import com.jack.weather.biz.RemindCityLintener;
import com.jack.weather.model.RemindCityModel;
import com.jack.weather.view.widget.DividerItemDecoration;

import java.util.List;

/**
 * Created by Jack on 2016/8/14.
 */

public class RemindCitySetting extends ToolbarActivity implements RemindCityLintener {

    private RecyclerView remind_recyclerview=null;
    private RemindCiryAdapter adapter=null;
    private List<RemindCityModel> listData=null;
    private ImageView back;

    @Override
    protected int provideContentViewId() {
        return R.layout.act_remindcitysetting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        remind_recyclerview=(RecyclerView)findViewById(R.id.remind_recyclerview);
        remind_recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        initView();
    }

    public void initView(){
        getToolbar().setTitle(getString(R.string.remindcitysetting));
        getToolbar().setNavigationIcon(R.drawable.back_white);
        getToolbar().setTitleTextColor(getResources().getColor(R.color.white));
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        remind_recyclerview=(RecyclerView)findViewById(R.id.remind_recyclerview);
        remind_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        remind_recyclerview.setItemAnimator(new DefaultItemAnimator());
        listData=(List<RemindCityModel>) getIntent().getSerializableExtra("data");
        adapter=new RemindCiryAdapter(this,listData,this);
        remind_recyclerview.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        ChangeItemClick(position);
        SendChangeMessage(position,listData.get(position).getName());
    }

    //改变item的关注
    public void ChangeItemClick(int position){
        for(int i=0;i<listData.size();i++){
            listData.get(i).setStatus(false);
        }
        listData.get(position).setStatus(true);
        adapter.notifyDataSetChanged();
    }

    //发送改变信号
    public void SendChangeMessage(int position,String city){
        Intent intent1=new Intent(getString(R.string.update_manager_city));
        intent1.putExtra("status","changefouce");
        intent1.putExtra("position",position);
        intent1.putExtra("city",city);
        sendBroadcast(intent1);
        Intent intent2=new Intent(getString(R.string.update_main_city));
        intent2.putExtra("city",city);
        sendBroadcast(intent2);
    }



}
