package hami.aniticket.Activity.PastPurchases.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2017-10-15.
 */

public class ResponseWeather {
    @SerializedName("pubDate")
    private String pubDate;
    @SerializedName("lat")
    private double lat;
    @SerializedName("long")
    private double lng;
    @SerializedName("Forecast")
    private ArrayList<Forecast> forecast;
    @SerializedName("condition")
    private Forecast condition;
    //-----------------------------------------------


    public ResponseWeather() {
    }
    //-----------------------------------------------

    public String getPubDate() {
        return pubDate;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public Forecast getCondition() {
        return condition;
    }

    public ArrayList<Forecast> getForecast() {
        return forecast;
    }
}
