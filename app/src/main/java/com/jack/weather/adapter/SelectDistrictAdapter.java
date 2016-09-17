package com.jack.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jack.weather.R;
import com.jack.weather.biz.RecyclerOnItemLintener;
import com.jack.weather.model.DistrictModel;
import java.util.List;

/**
 * Created by Jack on 2016/7/3.
 */

public class SelectDistrictAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int ResourceId;
    private List<DistrictModel> listModle=null;
    private Context context=null;
    private RecyclerOnItemLintener lintener=null;

    public SelectDistrictAdapter(int ResourceId, List<DistrictModel> listdata, Context context){
        this.ResourceId=ResourceId;
        this.listModle=listdata;
        this.context=context;
    }

    public void setLintener(RecyclerOnItemLintener lintener) {
        this.lintener = lintener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DistrictItem(LayoutInflater.from(context).inflate(ResourceId,parent,false),lintener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DistrictModel model=listModle.get(position);
        DistrictItem item=(DistrictItem)holder;
        item.itemname.setText(model.getDist());
    }

    @Override
    public int getItemCount() {
        return listModle.size();
    }

    public class DistrictItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemname=null;
        private RecyclerOnItemLintener lintener;
        public DistrictItem(View itemView,RecyclerOnItemLintener lintener) {
            super(itemView);
            this.itemname=(TextView)itemView.findViewById(R.id.provinces_itemname);
            this.lintener=lintener;
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
