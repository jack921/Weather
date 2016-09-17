package com.jack.weather.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jack.weather.R;
import com.jack.weather.adapter.SelectProvinceAdapter;
import com.jack.weather.biz.ProvincePresenterInterface;
import com.jack.weather.biz.RecyclerOnItemLintener;
import com.jack.weather.model.ProvincesModel;
import com.jack.weather.util.LocalData;
import com.jack.weather.util.LogUtil;
import com.jack.weather.view.activity.Activity_SelectCity;

import java.util.List;

/**
 * Created by Jack on 2016/7/3.
 */
public class ProvincePresenter {

    private ProvincePresenterInterface ppi;
    private LocalData mLocalData=null;
    private SelectProvinceAdapter adapter=null;
    private List<ProvincesModel> listModel=null;
    private RecyclerView recyclerView=null;

    public ProvincePresenter(ProvincePresenterInterface ppi){
        this.ppi=ppi;
        mLocalData=new LocalData();
    }

    public void initDataView(){
        List<ProvincesModel> listModel=mLocalData.GetCountryData(ppi.getMyContext());
        if(listModel!=null){
            //假如有数据
            ppi.setProvinceAdapter(new SelectProvinceAdapter(R.layout.act_selectprovinces_item,
                    listModel,ppi.getMyContext()));
            ppi.setListData(listModel);
            ppi.getRecyclerView().setAdapter(ppi.getAdapter());
            ppi.setLintener();
        }else{
            //假如没有数据
        }
    }

}
