package com.jack.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jack.weather.R;
import com.jack.weather.model.WeatherItem;
import com.jack.weather.util.Util;

import java.util.List;

/**
 * Created by Jack on 2016/7/24.
 */
public class WeatherContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<WeatherItem> listItem;

    public WeatherContentAdapter(Context context, List<WeatherItem> listItem){
        this.context=context;
        this.listItem=listItem;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new weatherItem(LayoutInflater.from(context).inflate(R.layout.weather_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        weatherItem viewHolder=(weatherItem)holder;
        viewHolder.day.setText(listItem.get(position).getDate());
        String weather=listItem.get(position).getNight();
        viewHolder.condition.setText(weather);
        viewHolder.temperature.setText(listItem.get(position).getTemperature());
        viewHolder.weather.setText(listItem.get(position).getWind());
        if(weather.contains("雨")){
            viewHolder.img.setImageResource(R.drawable.ic_weather_heavy_rain);
        }else if(weather.contains("雪")){
            viewHolder.img.setImageResource(R.drawable.ic_weather_heavy_snow);
        }else if(weather.contains("云")){
            viewHolder.img.setImageResource(R.drawable.ic_weather_cloudy_day);
        }else {
            viewHolder.img.setImageResource(R.drawable.ic_weather_select);
        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class weatherItem extends RecyclerView.ViewHolder{

        private TextView day;
        private TextView condition;
        private ImageView img;
        private TextView temperature;
        private TextView weather;

        public weatherItem(View itemView) {
            super(itemView);
            day=(TextView)itemView.findViewById(R.id.weather_day);
            condition=(TextView)itemView.findViewById(R.id.weather_result);
            img=(ImageView)itemView.findViewById(R.id.weather_img);
            temperature=(TextView)itemView.findViewById(R.id.weather_tempearture);
            weather=(TextView)itemView.findViewById(R.id.weather_status);
        }
    }

}
