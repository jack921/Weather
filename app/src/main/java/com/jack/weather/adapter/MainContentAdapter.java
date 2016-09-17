package com.jack.weather.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jack.weather.R;
import com.jack.weather.model.WeatherInfo;
import com.jack.weather.model.WeatherItem;
import com.jack.weather.util.Util;
import com.jack.weather.view.widget.WeatherLineChart;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2016/8/5.
 */

public class MainContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private int TYPE_MAIN=0;
    private int TYPE_HORIZONTAL=1;
    private int TYPE_TEMPERATURE=2;
    private int TYPE_FOOTER=3;

    private Context context;
    private WeatherContentAdapter weatherContentAdapter;

    public List<Float> YValueMax=null;
    public List<Float> YValueMin=null;

    public WeatherInfo weatherInfo=null;
    public List<WeatherItem> listData=null;

    public SimpleDateFormat mOldSimpleDateFormat=null;
    public SimpleDateFormat mNewSimpleDateFormat=null;

    public MainContentAdapter(Context context, List<Float> YValueMax,List<Float> YValueMin,WeatherInfo weatherInfo){
        this.context=context;
        mOldSimpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        mNewSimpleDateFormat=new SimpleDateFormat("MM/dd");
        listData=new ArrayList<>();
        try{
            for(int i=0;i<6;i++){
                WeatherItem item=new WeatherItem();
                item.setDate(mNewSimpleDateFormat.format(mOldSimpleDateFormat.
                        parseObject(weatherInfo.getResult().get(0).getFuture().get(i).getDate())));
                item.setNight(weatherInfo.getResult().get(0).getFuture().get(i).getNight());
                item.setTemperature(weatherInfo.getResult().get(0).getFuture().get(i).getTemperature());
                item.setWeek(weatherInfo.getResult().get(0).getFuture().get(i).getWeek());
                item.setWind(weatherInfo.getResult().get(0).getFuture().get(i).getWind().substring(0,
                        weatherInfo.getResult().get(0).getFuture().get(i).getWind().indexOf(" ")));
                listData.add(item);
            }
        }catch(Exception e){}
        this.weatherContentAdapter=new WeatherContentAdapter(context,listData);
        this.YValueMax=YValueMax;
        this.YValueMin=YValueMin;
        this.weatherInfo=weatherInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(TYPE_MAIN==viewType){
           return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.content_main_header,parent,false));
        }else if(TYPE_HORIZONTAL==viewType){
           return new HorizontalViewHolder(LayoutInflater.from(context).inflate(R.layout.content_main_horizontal_view,parent,false));
        }else if(TYPE_TEMPERATURE==viewType){
           return new TemperatureViewHolder(LayoutInflater.from(context).inflate(R.layout.content_main_temperature,parent,false));
        }else{
            return new FooterViewHolder(LayoutInflater.from(context).inflate(R.layout.content_main_footer,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch(position){
            case 0:
                MainViewHolder mainViewHolder=(MainViewHolder)holder;
                String weath=weatherInfo.getResult().get(0).getTemperature().substring(0,2);
                mainViewHolder.weather_wendu.setText(weath);
                mainViewHolder.weather_status.setText(weatherInfo.getResult().get(0).getWeather());
                mainViewHolder.weather_tigan.setText(weatherInfo.getResult().get(0).getTime());
                mainViewHolder.weather_shidu.setText(weatherInfo.getResult().get(0).getHumidity()
                        +" "+weatherInfo.getResult().get(0).getWind());
                mainViewHolder.weather_zhishu.setText("空气质量"+weatherInfo.getResult().get(0).getPollutionIndex()
                        +" "+weatherInfo.getResult().get(0).getAirCondition());
                break;
            case 1:
                ((HorizontalViewHolder) holder).recyclerView.setAdapter(weatherContentAdapter);
                break;
            case 2:
                TemperatureViewHolder temperature=(TemperatureViewHolder)holder;
                temperature.weatherLineChart.setYValueMax(YValueMax);
                temperature.weatherLineChart.setYValueMin(YValueMin);
                temperature.weatherLineChart.invalidate();

                try{
                    for(int i=0;i<6;i++){
                        temperature.listDay.get(i).setText(weatherInfo.getResult().get(0).getFuture().get(i+1).getWeek());
                        temperature.listwendu.get(i).setText(mNewSimpleDateFormat.format(
                                mOldSimpleDateFormat.parse(weatherInfo.getResult().get(0).getFuture().get(i+1).getDate())
                        ));
                        String weatherday=weatherInfo.getResult().get(0).getFuture().get(i+1).getDayTime();
                        temperature.liststatus.get(i).setText(weatherday);
                        if(weatherday.contains("雨")){
                            temperature.listDayImage.get(i).setImageResource(R.drawable.ic_weather_heavy_rain);
                        }else if(weatherday.contains("雪")){
                            temperature.listDayImage.get(i).setImageResource(R.drawable.ic_weather_heavy_snow);
                        }else if(weatherday.contains("云")){
                            temperature.listDayImage.get(i).setImageResource(R.drawable.ic_weather_cloudy_day);
                        }else {
                            temperature.listDayImage.get(i).setImageResource(R.drawable.ic_weather_select);
                        }
                        String weatherNight=weatherInfo.getResult().get(0).getFuture().get(i+1).getNight();
                        temperature.listnight.get(i).setText(weatherNight);
                        if(weatherNight.contains("雨")){
                            temperature.listNightImage.get(i).setImageResource(R.drawable.ic_weather_heavy_rain);
                        }else if(weatherNight.contains("雪")){
                            temperature.listNightImage.get(i).setImageResource(R.drawable.ic_weather_heavy_snow);
                        }else if(weatherNight.contains("云")){
                            temperature.listNightImage.get(i).setImageResource(R.drawable.ic_weather_cloudy_day);
                        }else {
                            temperature.listNightImage.get(i).setImageResource(R.drawable.ic_weather_select);
                        }
                        String wind=weatherInfo.getResult().get(0).getFuture().get(i+1).getWind();
                        int index=wind.indexOf(" ");
                        temperature.listcloud.get(i).setText(wind.substring(0,index));
                        temperature.listcloudstatus.get(i).setText(wind.substring(index,wind.length()));


                    }
                }catch(Exception e){}

                break;
            case 3:
                FooterViewHolder footerViewHolder=(FooterViewHolder)holder;
                footerViewHolder.tip_text.setText(weatherInfo.getResult().get(0).getAirCondition());
                footerViewHolder.tip_text2.setText(weatherInfo.getResult().get(0).getDressingIndex());
                footerViewHolder.tip_text3.setText(weatherInfo.getResult().get(0).getWashIndex());
                footerViewHolder.tip_text4.setText(weatherInfo.getResult().get(0).getColdIndex());
                footerViewHolder.tip_text5.setText(weatherInfo.getResult().get(0).getExerciseIndex());
                footerViewHolder.tip_text6.setText(weatherInfo.getResult().get(0).getPollutionIndex());
                break;
        }
        Util.RecyclerViewItemAnim(context,holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if(TYPE_MAIN==position){
            return 0;
        }else if(TYPE_HORIZONTAL==position){
            return 1;
        }else if(TYPE_TEMPERATURE==position){
            return 2;
        }else{
            return 3;
        }
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{
        private TextView weather_wendu;
        private TextView weather_status;
        private TextView weather_tigan;
        private TextView weather_shidu;
        private TextView weather_zhishu;

        public MainViewHolder(View itemView) {
            super(itemView);
            weather_wendu=(TextView)itemView.findViewById(R.id.wendu);
            weather_status=(TextView)itemView.findViewById(R.id.weatherstatus);
            weather_tigan=(TextView)itemView.findViewById(R.id.weather_tigan);
            weather_shidu=(TextView)itemView.findViewById(R.id.weather_shidu);
            weather_zhishu=(TextView)itemView.findViewById(R.id.weather_zhishu);
        }
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView recyclerView;
        public HorizontalViewHolder(View itemView) {
            super(itemView);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.content_recycler_view);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    public class TemperatureViewHolder extends RecyclerView.ViewHolder{
        public WeatherLineChart weatherLineChart;
        public TextView day1;
        public TextView day_wendu1;
        public ImageView day_icon1;
        public TextView day_status1;
        public TextView day2;
        public TextView day_wendu2;
        public ImageView day_icon2;
        public TextView day_status2;
        public TextView day3;
        public TextView day_wendu3;
        public ImageView day_icon3;
        public TextView day_status3;
        public TextView day4;
        public TextView day_wendu4;
        public ImageView day_icon4;
        public TextView day_status4;
        public TextView day5;
        public TextView day_wendu5;
        public ImageView day_icon5;
        public TextView day_status5;
        public TextView day6;
        public TextView day_wendu6;
        public ImageView day_icon6;
        public TextView day_status6;

        public TextView night;
        public ImageView night_icon;
        public TextView night_cloud;
        public TextView night_cloudstatus;
        public TextView night2;
        public ImageView night_icon2;
        public TextView night_cloud2;
        public TextView night_cloudstatus2;
        public TextView night3;
        public ImageView night_icon3;
        public TextView night_cloud3;
        public TextView night_cloudstatus3;
        public TextView night4;
        public ImageView night_icon4;
        public TextView night_cloud4;
        public TextView night_cloudstatus4;
        public TextView night5;
        public ImageView night_icon5;
        public TextView night_cloud5;
        public TextView night_cloudstatus5;
        public TextView night6;
        public ImageView night_icon6;
        public TextView night_cloud6;
        public TextView night_cloudstatus6;

        public List<TextView> listDay=new ArrayList<>();
        public List<TextView> listwendu=new ArrayList<>();
        public List<ImageView> listDayImage=new ArrayList<>();
        public List<TextView> liststatus=new ArrayList<>();
        public List<TextView> listnight=new ArrayList<>();
        public List<ImageView> listNightImage=new ArrayList<>();
        public List<TextView> listcloud=new ArrayList<>();
        public List<TextView> listcloudstatus=new ArrayList<>();

        public TemperatureViewHolder(View itemView) {
            super(itemView);
            weatherLineChart=(WeatherLineChart)itemView.findViewById(R.id.main_weatherlineview);
            day1=(TextView)itemView.findViewById(R.id.day1);
            day_wendu1=(TextView)itemView.findViewById(R.id.day_wendu1);
            day_icon1=(ImageView) itemView.findViewById(R.id.day_icon1);
            day_status1=(TextView)itemView.findViewById(R.id.day_status1);

            day2=(TextView)itemView.findViewById(R.id.day2);
            day_wendu2=(TextView)itemView.findViewById(R.id.day_wendu2);
            day_icon2=(ImageView)itemView.findViewById(R.id.day_icon2);
            day_status2=(TextView)itemView.findViewById(R.id.day_status2);

            day3=(TextView)itemView.findViewById(R.id.day3);
            day_wendu3=(TextView)itemView.findViewById(R.id.day_wendu3);
            day_icon3=(ImageView)itemView.findViewById(R.id.day_icon3);
            day_status3=(TextView)itemView.findViewById(R.id.day_status3);

            day4=(TextView)itemView.findViewById(R.id.day4);
            day_wendu4=(TextView)itemView.findViewById(R.id.day_wendu4);
            day_icon4=(ImageView)itemView.findViewById(R.id.day_icon4);
            day_status4=(TextView)itemView.findViewById(R.id.day_status4);

            day5=(TextView)itemView.findViewById(R.id.day5);
            day_wendu5=(TextView)itemView.findViewById(R.id.day_wendu5);
            day_icon5=(ImageView)itemView.findViewById(R.id.day_icon5);
            day_status5=(TextView)itemView.findViewById(R.id.day_status5);

            day6=(TextView)itemView.findViewById(R.id.day6);
            day_wendu6=(TextView)itemView.findViewById(R.id.day_wendu6);
            day_icon6=(ImageView)itemView.findViewById(R.id.day_icon6);
            day_status6=(TextView)itemView.findViewById(R.id.day_status6);

            listDay.add(day1);
            listDay.add(day2);
            listDay.add(day3);
            listDay.add(day4);
            listDay.add(day5);
            listDay.add(day6);

            listwendu.add(day_wendu1);
            listwendu.add(day_wendu2);
            listwendu.add(day_wendu3);
            listwendu.add(day_wendu4);
            listwendu.add(day_wendu5);
            listwendu.add(day_wendu6);

            listDayImage.add(day_icon1);
            listDayImage.add(day_icon2);
            listDayImage.add(day_icon3);
            listDayImage.add(day_icon4);
            listDayImage.add(day_icon5);
            listDayImage.add(day_icon6);

            liststatus.add(day_status1);
            liststatus.add(day_status2);
            liststatus.add(day_status3);
            liststatus.add(day_status4);
            liststatus.add(day_status5);
            liststatus.add(day_status6);


            night=(TextView)itemView.findViewById(R.id.night);
            night_icon=(ImageView) itemView.findViewById(R.id.night_icon);
            night_cloud=(TextView)itemView.findViewById(R.id.night_cloud);
            night_cloudstatus=(TextView)itemView.findViewById(R.id.night_cloudstatus);

            night2=(TextView)itemView.findViewById(R.id.night2);
            night_icon2=(ImageView)itemView.findViewById(R.id.night_icon2);
            night_cloud2=(TextView)itemView.findViewById(R.id.night_cloud2);
            night_cloudstatus2=(TextView)itemView.findViewById(R.id.night_cloudstatus2);

            night3=(TextView)itemView.findViewById(R.id.night3);
            night_icon3=(ImageView)itemView.findViewById(R.id.night_icon3);
            night_cloud3=(TextView)itemView.findViewById(R.id.night_cloud3);
            night_cloudstatus3=(TextView)itemView.findViewById(R.id.night_cloudstatus3);

            night4=(TextView)itemView.findViewById(R.id.night4);
            night_icon4=(ImageView)itemView.findViewById(R.id.night_icon4);
            night_cloud4=(TextView)itemView.findViewById(R.id.night_cloud4);
            night_cloudstatus4=(TextView)itemView.findViewById(R.id.night_cloudstatus4);

            night5=(TextView)itemView.findViewById(R.id.night5);
            night_icon5=(ImageView)itemView.findViewById(R.id.night_icon5);
            night_cloud5=(TextView)itemView.findViewById(R.id.night_cloud5);
            night_cloudstatus5=(TextView)itemView.findViewById(R.id.night_cloudstatus5);

            night6=(TextView)itemView.findViewById(R.id.night6);
            night_icon6=(ImageView)itemView.findViewById(R.id.night_icon6);
            night_cloud6=(TextView)itemView.findViewById(R.id.night_cloud6);
            night_cloudstatus6=(TextView)itemView.findViewById(R.id.night_cloudstatus6);

            listnight.add(night);
            listnight.add(night2);
            listnight.add(night3);
            listnight.add(night4);
            listnight.add(night5);
            listnight.add(night6);

            listNightImage.add(night_icon);
            listNightImage.add(night_icon2);
            listNightImage.add(night_icon3);
            listNightImage.add(night_icon4);
            listNightImage.add(night_icon5);
            listNightImage.add(night_icon6);

            listcloud.add(night_cloud);
            listcloud.add(night_cloud2);
            listcloud.add(night_cloud3);
            listcloud.add(night_cloud4);
            listcloud.add(night_cloud5);
            listcloud.add(night_cloud6);

            listcloudstatus.add(night_cloudstatus);
            listcloudstatus.add(night_cloudstatus2);
            listcloudstatus.add(night_cloudstatus3);
            listcloudstatus.add(night_cloudstatus4);
            listcloudstatus.add(night_cloudstatus5);
            listcloudstatus.add(night_cloudstatus6);

        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder{
        public TextView tip_text;
        public TextView tip_text2;
        public TextView tip_text3;
        public TextView tip_text4;
        public TextView tip_text5;
        public TextView tip_text6;

        public FooterViewHolder(View itemView) {
            super(itemView);
            tip_text=(TextView)itemView.findViewById(R.id.tip_text);
            tip_text2=(TextView)itemView.findViewById(R.id.tip_text2);
            tip_text3=(TextView)itemView.findViewById(R.id.tip_text3);
            tip_text4=(TextView)itemView.findViewById(R.id.tip_text4);
            tip_text5=(TextView)itemView.findViewById(R.id.tip_text5);
            tip_text6=(TextView)itemView.findViewById(R.id.tip_text6);
        }
    }

}
