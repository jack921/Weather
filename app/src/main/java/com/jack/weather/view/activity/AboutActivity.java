package com.jack.weather.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.jack.weather.R;
import com.jack.weather.app.ToolbarActivity;

/**
 * Created by Jack on 2016/8/23.
 */

public class AboutActivity extends ToolbarActivity implements View.OnClickListener {

    private CollapsingToolbarLayout collapsingToolbarLayout=null;
    private NestedScrollView nestedScrollView=null;
    private ImageView app_header;
    private TextView github;

    @Override
    protected int provideContentViewId() {
        return R.layout.act_about;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView(){
        app_header=(ImageView)findViewById(R.id.app_header_img);
        nestedScrollView=(NestedScrollView)findViewById(R.id.about_nestedscrollview);
        getToolbar().setNavigationIcon(R.drawable.back_white);
        setSupportActionBar(getToolbar());
        getToolbar().setTitleTextColor(getResources().getColor(R.color.white));
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case android.R.id.home:
                        finish();
                        break;
                }
                return true;
            }
        });
        collapsingToolbarLayout=((CollapsingToolbarLayout)findViewById(R.id.district_collapsing_toolbar_layout));
        collapsingToolbarLayout.setTitle(getString(R.string.app_name));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        github=(TextView)findViewById(R.id.github);
        github.setOnClickListener(this);

        Glide.with(this).load(R.drawable.app_header).into(app_header);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.github:
                openUrl(github.getText().toString());
                break;
        }
    }

    public void openUrl(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        startActivity(intent);
    }


}
