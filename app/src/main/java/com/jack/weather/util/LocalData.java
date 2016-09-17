package com.jack.weather.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.jack.weather.R;
import com.jack.weather.biz.LocalDataInterface;
import com.jack.weather.data.CityDatabaseOpenHelper;
import com.jack.weather.data.CountryDatabaseOpenHelper;
import com.jack.weather.data.DistrictDatabaseOpenHelper;
import com.jack.weather.data.ManageDistrictDatabaseOpenHelper;
import com.jack.weather.model.City;
import com.jack.weather.model.CityModel;
import com.jack.weather.model.Countries;
import com.jack.weather.model.District;
import com.jack.weather.model.DistrictModel;
import com.jack.weather.model.ManageCityModel;
import com.jack.weather.model.Provinces;
import com.jack.weather.model.ProvincesModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2016/6/25.
 * 本地持久化数据操作类
 */

public class LocalData implements LocalDataInterface {

    /*
    * 本地化保存所有数据
    * */
    @Override
    public void SaveCountryData(Context context, Countries country) {
        if(country.getRetCode().equals("200")){
            //本地化保存全国所有省份
            SaveProvinceData(context,country.getResult());
        }else{
            Toast.makeText(context, R.string.getdata_error,Toast.LENGTH_SHORT).show();
        }
    }

    /*
    * 本地化保存全国所有省份
    * */
    public void SaveProvinceData(Context context,List<Provinces> ListProvinces){
        try{
            CountryDatabaseOpenHelper countrydata=new CountryDatabaseOpenHelper(context,"country.db",null,1);
            SQLiteDatabase countrydb=countrydata.getWritableDatabase();
            ContentValues contentValues=null;
            for(int i=0;i<ListProvinces.size();i++){
                contentValues=new ContentValues();
                contentValues.put("province",ListProvinces.get(i).getProvince());
                countrydb.insert("country",null,contentValues);
                //本地化保存全国所有城市
                SaveCityData(context,ListProvinces.get(i).getProvince(),ListProvinces.get(i).getCity());
            }
        }catch(Exception e){
            LogUtil.e("SaveProvince",e.getMessage());
        }
    }

    /*
   * 本地化保存全国所有城市
   * */
    public void SaveCityData(Context context,String province,List<City> ListCity){
       try{
           CityDatabaseOpenHelper citydata=new CityDatabaseOpenHelper(context,"city.db",null,1);
           SQLiteDatabase citydb=citydata.getWritableDatabase();
           ContentValues contentValues=null;
           for(int i=0;i<ListCity.size();i++){
               contentValues=new ContentValues();
               contentValues.put("province",province);
               contentValues.put("city",ListCity.get(i).getCity());
               citydb.insert("city",null,contentValues);
               //本地化保存全国所有市县
               SaveDistrictData(context,ListCity.get(i).getCity(),ListCity.get(i).getDistrict());
           }
       }catch(Exception e){
            LogUtil.e("SaveCity",e.getMessage());
       }
    }

    /*
   * 本地化保存全国所有市县
   * */
    public void SaveDistrictData(Context context,String city,List<District> ListDistrict){
        try{
            DistrictDatabaseOpenHelper district=new DistrictDatabaseOpenHelper(context,"district.db",null,1);
            SQLiteDatabase districtdb=district.getWritableDatabase();
            JSONArray array=new JSONArray();
            for(int i=0;i<ListDistrict.size();i++){
                array.put(ListDistrict.get(i).getDistrict());
            }
            ContentValues contentValues=new ContentValues();
            contentValues.put("city",city);
            contentValues.put("dist",array.toString());
            districtdb.insert("district",null,contentValues);
        }catch(Exception e){
            LogUtil.e("SaveDistrict",e.getMessage());
        }
    }


    /*
    * 从本地获取全国各省
    * */
    public static List<ProvincesModel> GetCountryData(Context context){
       try{
           CountryDatabaseOpenHelper countrydata=new CountryDatabaseOpenHelper(context,"country.db",null,1);
           SQLiteDatabase db=countrydata.getWritableDatabase();
           Cursor cursor=db.query("country",null,null,null,null,null,null);
           ProvincesModel model=null;
           List<ProvincesModel> listModel=new ArrayList<>();
           if(cursor.moveToFirst()){
               int idnum=cursor.getColumnIndex("id");
               int pronum=cursor.getColumnIndex("province");
               do{
                 model=new ProvincesModel(cursor.getInt(idnum),cursor.getString(pronum));
                 listModel.add(model);
               }while(cursor.moveToNext());
               cursor.close();
           }
           return listModel;
       }catch(Exception e){
           return null;
       }
    }

    /*
    *从本地获取各省的市区
    * */
    public static List<CityModel> GetProvinceData(Context context, String Province){
        try{
            CityDatabaseOpenHelper citydata=new CityDatabaseOpenHelper(context,"city.db",null,1);
            SQLiteDatabase db=citydata.getWritableDatabase();
            Cursor cursor=db.query("city",null,"province=?",new String[]{Province},null,null,null);
            CityModel model=null;
            List<CityModel> listModel=new ArrayList<>();
            if(cursor.moveToFirst()){
                int idnum=cursor.getColumnIndex("id");
                int pornum=cursor.getColumnIndex("province");
                int citymum=cursor.getColumnIndex("city");
                do{
                    model=new CityModel(cursor.getInt(idnum),cursor.getString(pornum),cursor.getString(citymum));
                    listModel.add(model);
                }while(cursor.moveToNext());
                cursor.close();
            }
            return listModel;
        }catch(Exception e){
            return null;
        }
    }

    /*
    * 从本地获取各市区的市县
    * */
    public static List<DistrictModel> GetDistrictData(Context context, String city){
        try{
            DistrictDatabaseOpenHelper districtdata=new DistrictDatabaseOpenHelper(context,"district.db",null,1);
            SQLiteDatabase db=districtdata.getWritableDatabase();
            Cursor cursor=db.query("district",null,"city=?",new String[]{city},null,null,null);
            List<DistrictModel> listModel=new ArrayList<>();
            DistrictModel model=null;
            if(cursor.moveToFirst()){
                int idnum=cursor.getColumnIndex("id");
                int citynum=cursor.getColumnIndex("city");
                int distnum=cursor.getColumnIndex("dist");
                do{
                    model=new DistrictModel(cursor.getInt(idnum),
                            cursor.getString(citynum),cursor.getString(distnum));
                    listModel.add(model);
                }while(cursor.moveToNext());
                cursor.close();
            }
            return listModel;
        }catch(Exception e){
            return null;
        }
    }

    /*
    *解析市县数据
    * */
    public static List<DistrictModel> ParseDistrictModel(DistrictModel model){
        List<DistrictModel> listModel=new ArrayList<>();
        try{
            if(model!=null&&model.getDist()!=null){
                JSONArray array=new JSONArray(model.getDist());
                DistrictModel districtModel=null;
                for(int i=0;i<array.length();i++){
                    districtModel=new DistrictModel(model.getId(),model.getCity(),array.optString(i));
                    listModel.add(districtModel);
                }
            }
        }catch (Exception e){
        }
        return listModel;
    }

    /*
    *保存城市
    * */
    public static void SaveDistricData(Context context,ManageCityModel model){
        if(model==null){
            return ;
        }
        try{
            ManageDistrictDatabaseOpenHelper district=new ManageDistrictDatabaseOpenHelper(context,"managedistric.db",null,1);
            SQLiteDatabase db=district.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("fouce",model.isFouce()+"");
            values.put("max",model.getMaxTemperature());
            values.put("min",model.getMinTemperature());
            values.put("status",model.getStatus());
            values.put("explain",model.getExplain());
            values.put("location",model.getLocation());
            values.put("isaddview",model.isAddView()+"");
            db.insert("managedistric","",values);
        }catch(Exception e){
            LogUtil.e("GetDistrict",e.getMessage());
        }
    }

    //城市位置变化而更改保存数据
    public static void SaveMoveDistricData(Context context,List<ManageCityModel> listData){
        try{
            ManageDistrictDatabaseOpenHelper district=new ManageDistrictDatabaseOpenHelper(context,"managedistric.db",null,1);
            SQLiteDatabase db=district.getWritableDatabase();
            db.delete("managedistric",null,null);
            for(int i=0;i<listData.size();i++){
                if(listData.get(i).isAddView()){
                    continue;
                }
                ContentValues values=new ContentValues();
                values.put("fouce",listData.get(i).isFouce()+"");
                values.put("max",listData.get(i).getMaxTemperature());
                values.put("min",listData.get(i).getMinTemperature());
                values.put("status",listData.get(i).getStatus());
                values.put("explain",listData.get(i).getExplain());
                values.put("location",listData.get(i).getLocation());
                values.put("isaddview",listData.get(i).isAddView()+"");
                db.insert("managedistric","",values);
            }
        }catch(Exception e){
            LogUtil.e("SaveMoveDistricData",e.getMessage());
        }
    }

    /*
    * 删除城市
    * */
    public static void DeleteDistricData(Context context,String district){
        if(district==null){
            return ;
        }
        try{
            ManageDistrictDatabaseOpenHelper districtdata=new ManageDistrictDatabaseOpenHelper(context,"managedistric.db",null,1);
            SQLiteDatabase db=districtdata.getWritableDatabase();
            db.delete("managedistric","location=?",new String[]{district});
        }catch(Exception e){
        }
    }

    /*
    * 获取所有保存城市
    * */
    public static List<ManageCityModel> GetDistrictData(Context context){
        List<ManageCityModel> listData=new ArrayList<>();
        try{
            ManageDistrictDatabaseOpenHelper district=new ManageDistrictDatabaseOpenHelper(context,"managedistric.db",null,1);
            SQLiteDatabase db=district.getWritableDatabase();
            Cursor cursor=db.query("managedistric",null,null,null,null,null,null);
            if(cursor.moveToFirst()){
                int fouce=cursor.getColumnIndex("fouce");
                int max=cursor.getColumnIndex("max");
                int min=cursor.getColumnIndex("min");
                int status=cursor.getColumnIndex("status");
                int explain=cursor.getColumnIndex("explain");
                int location=cursor.getColumnIndex("location");
                int isaddview=cursor.getColumnIndex("isaddview");
                do{
                    cursor.getString(fouce);
                    cursor.getInt(max);
                    cursor.getInt(min);
                    cursor.getString(status);
                    cursor.getString(explain);
                    cursor.getString(location);
                    cursor.getString(isaddview);
                    listData.add(new ManageCityModel(
                       Boolean.valueOf(cursor.getString(fouce)),cursor.getInt(max),cursor.getInt(min),
                       cursor.getString(status),cursor.getString(explain),cursor.getString(location),
                       Boolean.valueOf(cursor.getString(isaddview)),false)
                    );
                }while(cursor.moveToNext());
                cursor.close();
            }
        }catch(Exception e){
            LogUtil.e("GetDistrictData",e.getMessage());
        }
        return listData;
    }

    //修改城市数据
    public static void UpdateCityData(Context context,ManageCityModel model,boolean fouce){
        try{
            ManageDistrictDatabaseOpenHelper district=new ManageDistrictDatabaseOpenHelper(context,"managedistric.db",null,1);
            SQLiteDatabase db=district.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("fouce",fouce);
            db.update("managedistric",values,"location=?",new String[]{model.getLocation()});
        }catch(Exception e){
            LogUtil.e("UpdateCityData",e.getMessage());
        }
    }

    //SharedPreferences保存数据
    public static boolean sharedPreferencesSave(Context context,String tableName,String key,String value){
        try{
            SharedPreferences.Editor editor=context.getSharedPreferences(tableName,Context.MODE_PRIVATE).edit();
            editor.putString(key,value);
            editor.commit();
            return true;
        }catch(Exception e){
            return false;
        }
    }


    //SharedPreferences读取数据
    public static String getSharedPreferencesData(Context context,String tableName,String key){
        try{
            SharedPreferences sharedPreferences=context.getSharedPreferences(tableName,Context.MODE_PRIVATE);
            return sharedPreferences.getString(key,"");
        }catch (Exception e){
            return "";
        }
    }

    public static boolean clearSharedPreferencesData(Context context,String tableName){
        try{
            SharedPreferences.Editor editor=context.getSharedPreferences(tableName,Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.commit();
            return true;
        }catch(Exception e){
            return false;
        }
    }


}
