package com.jack.weather.model;

/**
 * Created by Jack on 2016/6/26.
 */
public class DistrictModel {

    private int id;
    private String city;
    private String dist;

    public DistrictModel(int id, String city, String dist) {
        this.id = id;
        this.city = city;
        this.dist = dist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }
}
