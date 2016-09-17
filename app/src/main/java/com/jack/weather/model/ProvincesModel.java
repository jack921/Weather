package com.jack.weather.model;

/**
 * Created by Jack on 2016/6/26.
 */
public class ProvincesModel {

    private int id;
    private String Provinces;

    public ProvincesModel(int id, String provinces) {
        this.id = id;
        this.Provinces = provinces;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinces() {
        return Provinces;
    }

    public void setProvinces(String provinces) {
        Provinces = provinces;
    }

}
