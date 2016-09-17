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
import com.jack.weather.app.BaseActivity;
import com.jack.weather.app.ToolbarActivity;
import com.jack.weather.biz.CityPresenterInterface;
import com.jack.weather.biz.RecyclerOnItemLintener;
import com.jack.weather.model.CityModel;
import com.jack.weather.presenter.CityPresenter;

import java.util.List;

/**
 * Created by Jack on 2016/7/3.
 */
public class Activity_SelectCity extends ToolbarActivity implements CityPresenterInterface, RecyclerOnItemLintener {

    private RecyclerView recyclerView;
    private SelectCityAdapter adapter;
    private List<CityModel> listModel;
    private CityPresenter cityPresenter=null;
    private String CityName;
    private CollapsingToolbarLayout collapsingToolbarLayout=null;
    private ImageView app_header=null;

    @Override
    protected int provideContentViewId() {
        return R.layout.act_selectcity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CityName=getIntent().getStringExtra("provionce");
        initView();
    }

    public void initView(){
        app_header=(ImageView)findViewById(R.id.app_header_img);
        //初始化RecycerView
        recyclerView=(RecyclerView)findViewById(R.id.city_recyclerview);
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
                    case  android.R.id.home:
                        finish();
                        break;
                }
                return true;
            }
        });
        collapsingToolbarLayout=((CollapsingToolbarLayout)findViewById(R.id.city_collapsing_toolbar_layout));
        collapsingToolbarLayout.setTitle(getString(R.string.select_city));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));

        cityPresenter=new CityPresenter(this);
        cityPresenter.initDataView();

        Glide.with(this).load(R.drawable.app_header).into(app_header);

    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public SelectCityAdapter getAdapter() {
        return adapter;
    }

    @Override
    public List<CityModel> getListData() {
        return listModel;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public String getProvionName() {
        return CityName;
    }

    @Override
    public void setCityAdapter(SelectCityAdapter adapter) {this.adapter=adapter;}

    @Override
    public void setListData(List<CityModel> listData) {this.listModel=listData;}

    @Override
    public void setRecyclerView(RecyclerView recyclerView) {this.recyclerView=recyclerView;}

    @Override
    public void setLintener() {adapter.setLintener(this);}

    @Override
    public void onItemOnClick(View view, int position) {
        Intent intent=new Intent(this, Activity_SelectDistrict.class);
        intent.putExtra("city",listModel.get(position).getCity());
        startActivityForResult(intent,1);
        finish();
    }

    @Override
    public void onLongItemOnClick(View view, int position) {

    }
}
