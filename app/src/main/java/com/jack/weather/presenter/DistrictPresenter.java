package com.jack.weather.presenter;

import com.jack.weather.R;
import com.jack.weather.adapter.SelectDistrictAdapter;
import com.jack.weather.biz.DistrictPresenterInterface;
import com.jack.weather.model.DistrictModel;
import com.jack.weather.util.LocalData;
import java.util.List;

/**
 * Created by Jack on 2016/7/3.
 */
public class DistrictPresenter {

    private DistrictPresenterInterface dpi;
    private LocalData mLocalData=null;

    public DistrictPresenter(DistrictPresenterInterface dpi){
        this.dpi=dpi;
        mLocalData=new LocalData();
    }

    public void initDataView(){
        final List<DistrictModel> listModel=mLocalData.GetDistrictData(dpi.getMyContext(),dpi.getCityName());
        if(listModel!=null){
            dpi.setListData(mLocalData.ParseDistrictModel(listModel.get(0)));
            if(listModel!=null){
                //假如有数据
                dpi.setDisAdapter(new SelectDistrictAdapter(R.layout.act_selectprovinces_item,dpi.getListData(),dpi.getMyContext()));
                dpi.getRecyclerView().setAdapter(dpi.getAdapter());
                dpi.setDistricListener();
            }else{
                //假如没有数据
            }
        }
    }




}
