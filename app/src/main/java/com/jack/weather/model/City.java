package com.jack.weather.model;

import java.util.List;

/**
 * Created by Jack on 2016/6/25.
 */

public class City {

    private String city;
    /**
     * district : 合肥
     */
    private List<District> district;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<District> getDistrict() {
        return district;
    }

    public void setDistrict(List<District> district) {
        this.district = district;
    }



}
