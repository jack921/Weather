package com.jack.weather.biz;

import android.view.View;

/**
 * Created by Jack on 2016/7/3.
 */
public interface RecyclerOnItemLintener {
    public void onItemOnClick(View view,int position);
    public void onLongItemOnClick(View view,int position);
}
