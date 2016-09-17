package com.jack.weather.view.activity;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import com.jack.weather.R;
import com.jack.weather.adapter.ManagerCityAdapter;
import com.jack.weather.app.BaseActivity;
import com.jack.weather.app.ToolbarActivity;
import com.jack.weather.app.WeatherApplication;
import com.jack.weather.biz.ManagerPresenterInterface;
import com.jack.weather.biz.RecyclerOnItemLintener;
import com.jack.weather.model.ManageCityModel;
import com.jack.weather.presenter.ManagerPresenter;
import com.jack.weather.util.LocalData;
import com.jack.weather.util.SimpleItemTouchHelperCallback;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2016/7/9.
 */

public class Activity_ManagerCity extends ToolbarActivity implements ManagerPresenterInterface,
        RecyclerOnItemLintener,ManagerCityAdapter.onStartDragListener, Toolbar.OnMenuItemClickListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private String distric=null;
    private List<ManageCityModel> listData=null;
    private ManagerCityAdapter adapter=null;
    private ManagerPresenter managerPresenter=null;
    private UpdateManager updateManager=null;
    private ItemTouchHelper mItemTouchHelper;

    private ActionMenuItemView edit=null;
    private ActionMenuItemView finish=null;
    private ActionMenuItemView refresh=null;

    @Override
    protected int provideContentViewId() {
        return R.layout.act_managercity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    public void initView(){
        recyclerView=(RecyclerView)findViewById(R.id.manager_city);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getToolbar().setTitle("城市管理");
        getToolbar().setNavigationIcon(R.drawable.back_white);
        getToolbar().setTitleTextColor(getResources().getColor(R.color.white));
        getToolbar().setNavigationOnClickListener(this);
        getToolbar().inflateMenu(R.menu.manager_city_menu);
        getToolbar().setOnMenuItemClickListener(this);

        refresh=(ActionMenuItemView) getToolbar().findViewById(R.id.manage_refresh);
        edit=(ActionMenuItemView)getToolbar().findViewById(R.id.manage_edit_icon);
        finish=(ActionMenuItemView)getToolbar().findViewById(R.id.act_finish_edit);
        finish.setVisibility(View.GONE);

        updateManager=new UpdateManager();
        IntentFilter intentFilter=new IntentFilter(getString(R.string.update_manager_city));
        registerReceiver(updateManager,intentFilter);
    }

    public void initData(){
        distric=getIntent().getStringExtra("distric");
        managerPresenter=new ManagerPresenter(this);
        listData=new ArrayList<>();
        managerPresenter.initView(this);
    }

    @Override
    public void setManagerAdapter(ManagerCityAdapter adapter) {
        this.adapter=adapter;
    }
    @Override
    public ManagerCityAdapter getManagerAdapter() {
        return adapter;
    }
    @Override
    public void setListData(List<ManageCityModel> listData) {
        this.listData=listData;
    }
    @Override
    public List<ManageCityModel> getListData() {
        return listData;
    }
    @Override
    public String getDistric() {
        return distric;
    }
    @Override
    public Context getManagecontext() {
        return this;
    }
    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    @Override
    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView=recyclerView;
    }
    @Override
    public void setLintener() {
        adapter.setLintener(this);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }
    @Override
    public List<String> GetSaveCity() {
        List<String> result=new ArrayList<>();
        for(int i=0;i<listData.size();i++){
            result.add(listData.get(i).getLocation());
        }
        return result;
    }
    @Override
    public Activity_ManagerCity getManageActivity() {
        return this;
    }
    @Override
    public void onItemOnClick(View view, int position) {
        startActivityForResult(new Intent(this,Activity_SelectProvinces.class),1);
    }
    @Override
    public void onLongItemOnClick(View view, int position) {
        turnEdit();
    }

    //转为编辑模式
    public void turnEdit(){
        edit.setVisibility(View.GONE);
        finish.setVisibility(View.VISIBLE);
        managerPresenter.TurnEditModel(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(updateManager);
    }

    public void RefreshAnimation(ActionMenuItemView img){
        ObjectAnimator animator=ObjectAnimator.ofFloat(img,"rotation",0f,1080f);
        animator.setDuration(2000);
        animator.start();
    }

    @Override
    public void startDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.manage_refresh:
                RefreshAnimation(refresh);
                managerPresenter.WeatherRefresh();
            break;
            case R.id.manage_edit_icon:
                turnEdit();
            break;
            case R.id.act_finish_edit:
                edit.setVisibility(View.VISIBLE);
                finish.setVisibility(View.GONE);
                managerPresenter.TurnEditModel(false);
            break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    //广播监听城市的变化
    class UpdateManager extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("status").equals("add")){
                managerPresenter.SaveDistrict(context,intent.getStringExtra("distric"));
            }else if(intent.getStringExtra("status").equals("changefouce")){
                managerPresenter.ChangeFouceCity(context,intent.getIntExtra("position",0));
                LocalData.sharedPreferencesSave(context,"nowcity","city",intent.getStringExtra("city"));
                WeatherApplication.showCity=intent.getStringExtra("city");
            }
        }
    }

}
