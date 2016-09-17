package com.jack.weather.biz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.jack.weather.adapter.SelectProvinceAdapter;
import com.jack.weather.model.ProvincesModel;

import java.util.List;

/**
 * Created by Jack on 2016/7/3.
 */
public interface ProvincePresenterInterface {

    public Context getMyContext();

    public SelectProvinceAdapter getAdapter();

    public List<ProvincesModel> getListData();

    public RecyclerView getRecyclerView();

    public void setProvinceAdapter(SelectProvinceAdapter adapter);

    public void setRecyclerView(RecyclerView recyclerView);

    public void setListData(List<ProvincesModel> listData);

    public void setLintener();

}
