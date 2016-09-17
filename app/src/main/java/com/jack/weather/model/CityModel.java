package com.jack.weather.model;

/**
 * Created by Jack on 2016/6/26.
 */
public class CityModel {

    private int id=0;
    private String province;
    private String city;

    public CityModel(int id, String province, String city) {
        this.id = id;
        this.province = province;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
