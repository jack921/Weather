package com.jack.weather.model;

/**
 * Created by Jack on 2016/7/9.
 */

public class ManageCityModel {

    private boolean fouce;
    private int maxTemperature;
    private int minTemperature;
    private String status;
    private String explain;
    private String location;
    private boolean isAddView;

    //状态值
    private boolean DeleteStatus;

    private boolean RefreshStatus=true;

    public ManageCityModel(boolean fouce, int maxTemperature, int minTemperature, String status,
                           String explain, String location,boolean isAddView,boolean DeleteStatus) {
        this.fouce = fouce;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.status = status;
        this.explain = explain;
        this.location = location;
        this.isAddView=isAddView;
        this.DeleteStatus=DeleteStatus;
    }

    public boolean isRefreshStatus() {
        return RefreshStatus;
    }

    public void setRefreshStatus(boolean refreshStatus) {
        RefreshStatus = refreshStatus;
    }

    public boolean isDeleteStatus() {
        return DeleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        DeleteStatus = deleteStatus;
    }

    public boolean isAddView() {
        return isAddView;
    }

    public void setAddView(boolean addView) {
        isAddView = addView;
    }

    public boolean isFouce() {
        return fouce;
    }

    public void setFouce(boolean fouce) {
        this.fouce = fouce;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
