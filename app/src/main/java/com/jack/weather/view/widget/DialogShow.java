package com.jack.weather.view.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jack.weather.R;
import com.jack.weather.biz.DeleteCityInterface;
import com.jack.weather.biz.TimeLintener;

/**
 * Created by Jack on 2016/8/14.
 */

public class DialogShow {

    //删除提醒城市的提示
    public static void ShowDeleteTip(final Activity context, final DeleteCityInterface lintener){
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_delete_tip,null);
        final PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.popwin_buttom_anim_style);
        final WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha =0.5f;
        context.getWindow().setAttributes(lp);
        popupWindow.showAsDropDown(view,0,0,Gravity.BOTTOM);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha =1f;
                context.getWindow().setAttributes(lp);
                popupWindow.dismiss();
            }
        });
        ((TextView)view.findViewById(R.id.city_setting)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                popupWindow.dismiss();
                lintener.setting();
            }
        });
        ((TextView)view.findViewById(R.id.city_cancal)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                lintener.cancal();
            }
        });
    }

    public static void setTimeDialog(final Activity context, final TimeLintener lintener){
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_time_change,null);
        final TextView sure=(TextView)view.findViewById(R.id.time_sure);
        final SeekBar mSeekBar=(SeekBar)view.findViewById(R.id.auto_update_seekbar);
        final TextView tip=(TextView)view.findViewById(R.id.auto_update_tip);
        final PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        final WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha =0.5f;
        context.getWindow().setAttributes(lp);
        popupWindow.showAtLocation(view,Gravity.CENTER,0,0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha =1f;
                context.getWindow().setAttributes(lp);
                popupWindow.dismiss();
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tip.setText(String.format("每%s小时",progress));
                tip.setTag(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                lintener.getTime((int)tip.getTag());
            }
        });

    }


}
