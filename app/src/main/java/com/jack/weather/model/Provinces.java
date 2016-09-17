package com.jack.weather.model;

import java.util.List;

/**
 * Created by Jack on 2016/6/25.
 */
public class Provinces {

    private String province;
    /**
     * city : 合肥
     * district : [{"district":"合肥"},{"district":"长丰"},{"district":"肥东"},{"district":"肥西"},{"district":"巢湖"},{"district":"庐江"}]
     */

    private List<City> city;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

}
