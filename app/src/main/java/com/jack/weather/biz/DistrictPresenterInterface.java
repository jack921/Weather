package com.jack.weather.biz;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.jack.weather.adapter.SelectDistrictAdapter;
import com.jack.weather.model.DistrictModel;
import java.util.List;

/**
 * Created by Jack on 2016/7/3.
 */
public interface DistrictPresenterInterface {

    public Context getMyContext();

    public SelectDistrictAdapter getAdapter();

    public List<DistrictModel> getListData();

    public RecyclerView getRecyclerView();

    public void setDisAdapter(SelectDistrictAdapter adapter);

    public void setListData(List<DistrictModel> listdata);

    public void setRecyclerView(RecyclerView recyclerView);

    public String getCityName();

    public void setDistricListener();



}
