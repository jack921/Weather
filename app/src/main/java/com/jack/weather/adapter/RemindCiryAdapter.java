package com.jack.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jack.weather.R;
import com.jack.weather.biz.RemindCityLintener;
import com.jack.weather.model.RemindCityModel;
import java.util.List;

/**
 * Created by Jack on 2016/8/14.
 */

public class RemindCiryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context=null;
    private List<RemindCityModel> listData=null;
    private RemindCityLintener lintener=null;

    public RemindCiryAdapter(Context context, List<RemindCityModel> listData, RemindCityLintener lintener){
        this.context=context;
        this.listData=listData;
        this.lintener=lintener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RemindViewHodler(LayoutInflater.from(context).inflate(R.layout.act_remindcity_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RemindViewHodler viewHodler=(RemindViewHodler)holder;
        viewHodler.name.setText(listData.get(position).getName());
        if(listData.get(position).isStatus()){
            viewHodler.status.setVisibility(View.VISIBLE);
        }else{
            viewHodler.status.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class RemindViewHodler extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageView status;
        public RemindViewHodler(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.remind_name);
            status=(ImageView)itemView.findViewById(R.id.remind_duihao);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lintener.onItemClick(v,getAdapterPosition());
                }
            });
        }
    }

}
