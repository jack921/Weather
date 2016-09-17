package com.jack.weather.presenter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.jack.weather.R;
import com.jack.weather.adapter.SelectCityAdapter;
import com.jack.weather.biz.CityPresenterInterface;
import com.jack.weather.biz.RecyclerOnItemLintener;
import com.jack.weather.model.CityModel;
import com.jack.weather.util.LocalData;
import com.jack.weather.view.activity.Activity_SelectDistrict;
import java.util.List;

/**
 * Created by Jack on 2016/7/3.
 */

public class CityPresenter {

    private CityPresenterInterface cpi;
    private LocalData mLocalData=null;

    public CityPresenter(CityPresenterInterface cpi){
        this.cpi=cpi;
        mLocalData=new LocalData();
    }

    public void initDataView(){
        List<CityModel> listModel=mLocalData.GetProvinceData(cpi.getMyContext(),cpi.getProvionName());
        if(listModel!=null){
            //假如有数据
            cpi.setCityAdapter(new SelectCityAdapter(R.layout.act_selectprovinces_item,listModel,cpi.getMyContext()));
            cpi.setListData(listModel);
            cpi.getRecyclerView().setAdapter(cpi.getAdapter());
            cpi.setLintener();
        }else{
            //假如没有数据
        }
    }

}
