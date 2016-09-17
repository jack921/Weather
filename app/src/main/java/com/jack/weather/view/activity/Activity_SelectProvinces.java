package com.jack.weather.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jack.weather.R;
import com.jack.weather.adapter.SelectProvinceAdapter;
import com.jack.weather.app.BaseActivity;
import com.jack.weather.app.ToolbarActivity;
import com.jack.weather.biz.ProvincePresenterInterface;
import com.jack.weather.biz.RecyclerOnItemLintener;
import com.jack.weather.model.ProvincesModel;
import com.jack.weather.presenter.ProvincePresenter;

import java.util.List;

/**
 * Created by Jack on 2016/7/3.
 *
 */

public class Activity_SelectProvinces extends ToolbarActivity
        implements ProvincePresenterInterface, RecyclerOnItemLintener, View.OnClickListener {

    private RecyclerView provincesView;
    private SelectProvinceAdapter adapter=null;
    private List<ProvincesModel> listModel=null;
    private ProvincePresenter persenter=null;
    private CollapsingToolbarLayout collapsingToolbarLayout=null;
    private ImageView app_header=null;

    @Override
    protected int provideContentViewId() {
        return R.layout.act_selectprovinces;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    /*
    * 初始化变量
    * */
    public void initView(){
        app_header=(ImageView)findViewById(R.id.app_header_img);
        persenter=new ProvincePresenter(this);
        //初始化RecycerView
        provincesView=(RecyclerView)findViewById(R.id.provinces_recyclerview);
        provincesView.setLayoutManager(new LinearLayoutManager(this));
        // 设置ItemAnimator
        provincesView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        provincesView.setHasFixedSize(true);
        //初始化ToolBar
        getToolbar().setNavigationIcon(R.drawable.back_white);
        setSupportActionBar(getToolbar());
        getToolbar().setTitleTextColor(getResources().getColor(R.color.white));
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case  android.R.id.home:
                        finish();
                        break;
                }
                return true;
            }
        });
        collapsingToolbarLayout=((CollapsingToolbarLayout)findViewById(R.id.province_collapsing_toolbar_layout));
        collapsingToolbarLayout.setTitle(getString(R.string.select_province));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));

        //获取数据构建视图
        persenter.initDataView();

        Glide.with(this).load(R.drawable.app_header).into(app_header);
    }


    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public SelectProvinceAdapter getAdapter() {
        return adapter;
    }

    @Override
    public List<ProvincesModel> getListData() {
        return listModel;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return provincesView;
    }

    @Override
    public void setProvinceAdapter(SelectProvinceAdapter adapter) {
        this.adapter=adapter;
    }

    @Override
    public void setRecyclerView(RecyclerView recyclerView) {
        this.provincesView=recyclerView;
    }

    @Override
    public void setListData(List<ProvincesModel> listData) {
        this.listModel=listData;
    }

    @Override
    public void setLintener() {
        adapter.setRecyclerOnItemLintener(this);
    }

    @Override
    public void onItemOnClick(View view, int position) {
        Intent intent=new Intent(this,Activity_SelectCity.class);
        intent.putExtra("provionce",listModel.get(position).getProvinces());
        startActivityForResult(intent,1);
        finish();
    }

    @Override
    public void onLongItemOnClick(View view, int position) {

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
