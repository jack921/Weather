package com.jack.weather.biz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.jack.weather.adapter.ManagerCityAdapter;
import com.jack.weather.model.ManageCityModel;
import com.jack.weather.view.activity.Activity_ManagerCity;

import java.util.List;
import java.util.Map;

/**
 * Created by Jack on 2016/7/10.
 */

public interface ManagerPresenterInterface {

    public void setManagerAdapter(ManagerCityAdapter adapter);
    public ManagerCityAdapter getManagerAdapter();
    public void setListData(List<ManageCityModel> listData) ;
    public List<ManageCityModel> getListData();
    public String getDistric();
    public Context getManagecontext();
    public RecyclerView getRecyclerView();
    public void setRecyclerView(RecyclerView recyclerView);
    public void setLintener();

    public List<String> GetSaveCity();
    public Activity_ManagerCity getManageActivity();

}
