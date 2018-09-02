package com.hami.servicebus.Services.Controller.Model;

import java.io.Serializable;

/**
 * Created by renjer on 2017-02-21.
 */

public class City implements Serializable {
    private String cid;
    private String country;
    private String cityName;
    private int type = CITY;
    public final static int HEADER = 0;
    public final static int CITY = 1;

    //-----------------------------------------------
    public static City newInstance(String cid, String country, String cityName) {
        City city = new City();
        city.type = CITY;
        city.cid = cid;
        city.country = country;
        city.cityName = cityName;
        return city;
    }

    //-----------------------------------------------
    public static City newInstance(String title) {
        City city = new City();
        city.type = HEADER;
        city.cid = "";
        city.country = "";
        city.cityName = title;
        return city;
    }
    //-----------------------------------------------

    public City() {
    }

    public City(String cid, String country, String cityName) {
        this.cid = cid;
        this.country = country;
        this.cityName = cityName;
    }
    //-----------------------------------------------

    public int getType() {
        return type;
    }

    public String getCid() {
        return cid;
    }

    public String getCountry() {
        return country;
    }

    public String getCityName() {
        return cityName;
    }
}
