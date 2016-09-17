package com.jack.weather.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.jack.weather.R;
import com.jack.weather.app.ToolbarActivity;
import com.jack.weather.view.fragment.SettingFragment;

/**
 * Created by Jack on 2016/8/18.
 */

public class SettingActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {

    @Override
    protected int provideContentViewId() {
        return R.layout.act_setting;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView(){
        getToolbar().setTitle("设置");
        getToolbar().setNavigationIcon(R.drawable.back_white);
        getToolbar().setTitleTextColor(getResources().getColor(R.color.white));
        getToolbar().setNavigationOnClickListener(this);
        getFragmentManager().beginTransaction().replace(R.id.setting_framelayout, new SettingFragment()).commit();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
