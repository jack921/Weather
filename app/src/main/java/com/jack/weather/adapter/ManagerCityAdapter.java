package com.jack.weather.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jack.weather.R;
import com.jack.weather.biz.DeleteCityInterface;
import com.jack.weather.biz.RecyclerOnItemLintener;
import com.jack.weather.biz.onMoveAndSwipedListener;
import com.jack.weather.model.ManageCityModel;
import com.jack.weather.model.RemindCityModel;
import com.jack.weather.util.LocalData;
import com.jack.weather.util.NetData;
import com.jack.weather.view.activity.RemindCitySetting;
import com.jack.weather.view.widget.DialogShow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2016/7/9.
 */

public class ManagerCityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements onMoveAndSwipedListener {

    private Context context=null;
    private List<ManageCityModel> listData=null;
    private RecyclerOnItemLintener lintener=null;

    private onStartDragListener dragListener;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public interface onStartDragListener {
        void startDrag(RecyclerView.ViewHolder viewHolder);
    }

    public interface onStateChangedListener {
        void onItemSelected();
        void onItemClear();
    }

    public ManagerCityAdapter(Context context, List<ManageCityModel> listData,onStartDragListener dragListener){
        this.context=context;
        this.listData=listData;
        this.dragListener=dragListener;
    }

    public void setLintener(RecyclerOnItemLintener lintener) {
        this.lintener = lintener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){
            return  new addViewHolder(LayoutInflater.from(context).inflate(R.layout.act_manager_add_item,null));
        }else{
            return  new managerViewHoler(LayoutInflater.from(context).inflate(R.layout.act_manager_city_item,null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof managerViewHoler){
            final managerViewHoler viewHoler=(managerViewHoler)holder;
            viewHoler.city_location.setText(listData.get(position).getLocation());
            viewHoler.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //如果按下
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        dragListener.startDrag(viewHoler);
                    }
                    return false;
                }
            });

            if(listData.get(position).isFouce()){
                viewHoler.FouceImage.setVisibility(View.VISIBLE);
                viewHoler.city_tip.setVisibility(View.VISIBLE);
            }else{
                viewHoler.FouceImage.setVisibility(View.GONE);
                viewHoler.city_tip.setVisibility(View.INVISIBLE);
            }

            if(listData.get(position).isRefreshStatus()){
                NetData.GetWeatherInfo(listData.get(position).getLocation(),viewHoler);
                listData.get(position).setRefreshStatus(false);
            }

            if(listData.get(position).isDeleteStatus()){
                viewHoler.DeleteStatus.setVisibility(View.VISIBLE);
            }else{
                viewHoler.DeleteStatus.setVisibility(View.GONE);
            }

            viewHoler.DeleteStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!listData.get(position).isFouce()){
                        LocalData.DeleteDistricData(context,listData.get(position).getLocation());
                        listData.remove(position);
                        notifyDataSetChanged();
                    }else{
                        DialogShow.ShowDeleteTip((Activity) context, new DeleteCityInterface() {
                            @Override
                            public void cancal() {}
                            @Override
                            public void setting() {
                                List<RemindCityModel> listCity=new ArrayList<RemindCityModel>();
                                RemindCityModel cityModel=null;
                                for(int i=0;i<listData.size();i++){
                                    if(listData.get(i).isFouce()){
                                        cityModel=new RemindCityModel(listData.get(i).getLocation(),true);
                                    }else{
                                        cityModel=new RemindCityModel(listData.get(i).getLocation(),false);
                                    }
                                    listCity.add(cityModel);
                                }
                                Intent intent=new Intent(context,RemindCitySetting.class);
                                intent.putExtra("data",(Serializable)listCity);
                                context.startActivity(intent);
                            }
                        });
                    }
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(listData.get(position).isAddView()){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        if(listData==null){
            return 0;
        }else{
            return listData.size();
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        //交换RecyclerView列表中item的位置
        notifyItemMoved(fromPosition, toPosition);
        MoveData(fromPosition,toPosition);
        return true;
    }

    public void MoveData(int fromPosition,int toPosition){
        ManageCityModel fromCity=listData.get(fromPosition);
        listData.remove(fromPosition);
        listData.add(toPosition,fromCity);
    }

    public class managerViewHoler extends RecyclerView.ViewHolder implements onStateChangedListener, View.OnLongClickListener {
        public TextView city_tip;
        public ImageView city_status;
        public TextView city_max;
        public TextView city_explain;
        public TextView city_location;
        public ImageView DeleteStatus;
        public ImageView FouceImage;

        public managerViewHoler(View itemView) {
            super(itemView);
            city_tip=(TextView)itemView.findViewById(R.id.city_tip);
            city_status=(ImageView)itemView.findViewById(R.id.city_status);
            city_max=(TextView)itemView.findViewById(R.id.city_max);
            city_explain=(TextView)itemView.findViewById(R.id.city_explain);
            city_location=(TextView)itemView.findViewById(R.id.city_location);
            DeleteStatus=(ImageView)itemView.findViewById(R.id.manager_cancal);
            FouceImage=(ImageView)itemView.findViewById(R.id.fouceimage);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onItemSelected() {
            //设置item的背景颜色为浅灰色
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            //恢复item的背景颜色
            itemView.setBackgroundColor(0);
            LocalData.SaveMoveDistricData(context,listData);
        }

        @Override
        public boolean onLongClick(View v){
            //长按删除
            lintener.onLongItemOnClick(v,getPosition());
            return false;
        }
    }

    public class addViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView addImageView=null;
        public addViewHolder(View itemView) {
            super(itemView);
            addImageView=(ImageView)itemView.findViewById(R.id.addimageview);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if(lintener!=null){
                lintener.onItemOnClick(v,getPosition());
            }
        }
    }

}
