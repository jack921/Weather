package com.jack.weather.view.activity;

import android.content.Context;
import android.content.Intent;
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
import com.jack.weather.adapter.SelectCityAdapter;
import com.jack.weather.adapter.SelectDistrictAdapter;
import com.jack.weather.app.BaseActivity;
import com.jack.weather.app.ToolbarActivity;
import com.jack.weather.biz.DistrictPresenterInterface;
import com.jack.weather.biz.RecyclerOnItemLintener;
import com.jack.weather.model.DistrictModel;
import com.jack.weather.presenter.DistrictPresenter;

import java.util.List;

/**
 * Created by Jack on 2016/7/3.
 */

public class Activity_SelectDistrict extends ToolbarActivity
        implements DistrictPresenterInterface, RecyclerOnItemLintener {

    private RecyclerView recyclerView;
    private SelectDistrictAdapter adapter;
    private List<DistrictModel> listModel;
    private String CityName;
    private DistrictPresenter districtPresenter=null;
    private CollapsingToolbarLayout collapsingToolbarLayout=null;
    private ImageView app_header=null;

    @Override
    protected int provideContentViewId() {
        return R.layout.act_selectdistrict;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CityName=getIntent().getStringExtra("city");
        initView();
    }

    public void initView(){
        app_header=(ImageView)findViewById(R.id.app_header_img);
        //初始化RecycerView
        recyclerView=(RecyclerView)findViewById(R.id.district_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 设置ItemAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        recyclerView.setHasFixedSize(true);
        //初始化ToolBar
        getToolbar().setNavigationIcon(R.drawable.back_white);
        setSupportActionBar(getToolbar());
        getToolbar().setTitleTextColor(getResources().getColor(R.color.white));
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case android.R.id.home:
                        finish();
                        break;
                }
                return true;
            }
        });
        collapsingToolbarLayout=((CollapsingToolbarLayout)findViewById(R.id.district_collapsing_toolbar_layout));
        collapsingToolbarLayout.setTitle(getString(R.string.select_district));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));

        districtPresenter=new DistrictPresenter(this);
        districtPresenter.initDataView();

        Glide.with(this).load(R.drawable.app_header).into(app_header);
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public SelectDistrictAdapter getAdapter() {
        return adapter;
    }

    @Override
    public List<DistrictModel> getListData() {
        return listModel;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void setDisAdapter(SelectDistrictAdapter adapter) {
        this.adapter=adapter;
    }

    @Override
    public void setListData(List<DistrictModel> listdata) {
        this.listModel=listdata;
    }

    @Override
    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView=recyclerView;
    }

    @Override
    public String getCityName() {
        return CityName;
    }

    @Override
    public void setDistricListener() {
        adapter.setLintener(this);
    }

    @Override
    public void onItemOnClick(View view, int position) {
        Intent intent=new Intent(getString(R.string.update_manager_city));
        intent.putExtra("distric",listModel.get(position).getDist());
        intent.putExtra("status","add");
        sendBroadcast(intent);
        finish();
    }

    @Override
    public void onLongItemOnClick(View view, int position) {

    }
}
