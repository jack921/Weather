package com.jack.weather.biz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.jack.weather.adapter.SelectCityAdapter;
import com.jack.weather.model.CityModel;

import java.util.List;



public interface CityPresenterInterface {

    public Context getMyContext();

    public SelectCityAdapter getAdapter();

    public List<CityModel> getListData();

    public RecyclerView getRecyclerView();

    public String getProvionName();

    public void setCityAdapter(SelectCityAdapter adapter);

    public void setListData(List<CityModel> listData);

    public void setRecyclerView(RecyclerView recyclerView);

    public void setLintener();

}
