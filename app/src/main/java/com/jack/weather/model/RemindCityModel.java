package com.jack.weather.model;

import java.io.Serializable;

/**
 * Created by Jack on 2016/8/14.
 */

public class RemindCityModel implements Serializable{
    private String name;
    private boolean status;

    public RemindCityModel(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
