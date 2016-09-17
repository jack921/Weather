package com.jack.weather.presenter;

import android.content.Context;

import com.jack.weather.adapter.ManagerCityAdapter;
import com.jack.weather.app.WeatherApplication;
import com.jack.weather.biz.ManagerPresenterInterface;
import com.jack.weather.model.DistrictModel;
import com.jack.weather.model.ManageCityModel;
import com.jack.weather.util.LocalData;
import com.jack.weather.util.LogUtil;

import java.util.List;

/**
 * Created by Jack on 2016/7/10.
 */

public class ManagerPresenter {

    private ManagerPresenterInterface mpi;

    public ManagerPresenter(ManagerPresenterInterface mpi){
        this.mpi=mpi;
    }

    //加载视图
    public void initView(Context context){
        mpi.setListData(GetCityData(context));
        mpi.getListData().add(new ManageCityModel(false,0,0,"","","",true,false));
        mpi.setManagerAdapter(new ManagerCityAdapter(mpi.getManagecontext(),mpi.getListData(),mpi.getManageActivity()));
        mpi.getRecyclerView().setAdapter(mpi.getManagerAdapter());
        mpi.setLintener();
    }

    //判断是不是保存数据有没有当前城市
    public List<ManageCityModel> GetCityData(Context context){
        List<ManageCityModel> citydata=LocalData.GetDistrictData(mpi.getManagecontext());
        //ture为有，false为没有数据
        boolean noData=true;
        for(int i=0;i<citydata.size();i++){
            if(WeatherApplication.showCity.contains(citydata.get(i).getLocation())){
                citydata.get(i).setFouce(true);
                ManageCityModel temp=citydata.get(i);
                citydata.remove(i);
                citydata.add(0,temp);
                noData=false;
            }
        }

        if(noData){
            //没有数据就保存数据
            String StrCity=WeatherApplication.showCity.replace("市","");
            ManageCityModel model=new ManageCityModel(true,0,0,"","",StrCity,false,false);
            LocalData.SaveDistricData(context,model);
            LocalData.sharedPreferencesSave(context,"nowcity","city",StrCity);
            citydata.add(0,model);
        }

        return citydata;
    }

    //存储城市数据
    public void SaveDistrict(Context context, String district){
      try{
          ManageCityModel model=new ManageCityModel(false,0,0,"","",district,false,false);
          LocalData.SaveDistricData(context,model);
          mpi.getListData().add(mpi.getListData().size()-1,model);
          mpi.getManagerAdapter().notifyDataSetChanged();
      }catch(Exception e){
          LogUtil.e("SaveDistrict",e.getMessage());
      }
    }

    //转换为编辑模式和完成编辑
    public void TurnEditModel(boolean status){
        for(int i=0;i<mpi.getListData().size();i++){
            mpi.getListData().get(i).setDeleteStatus(status);
        }
        mpi.getManagerAdapter().notifyDataSetChanged();
    }

    //刷新天气
    public void WeatherRefresh(){
       for(int i=0;i<mpi.getListData().size();i++) {
           mpi.getListData().get(i).setRefreshStatus(true);
       }
        mpi.getManagerAdapter().notifyDataSetChanged();
    }

    //编辑状态转化关注城市
    public void ChangeFouceCity(Context context,int position){
        for(int i=0;i<mpi.getListData().size();i++){
            if(mpi.getListData().get(i).isFouce()){
                LocalData.UpdateCityData(context,mpi.getListData().get(i),false);
            }
            mpi.getListData().get(i).setFouce(false);
        }
        mpi.getListData().get(position).setFouce(true);
        ManageCityModel model=mpi.getListData().get(position);
        mpi.getListData().remove(position);
        mpi.getListData().add(0,model);
        mpi.getManagerAdapter().notifyDataSetChanged();
    }

}
