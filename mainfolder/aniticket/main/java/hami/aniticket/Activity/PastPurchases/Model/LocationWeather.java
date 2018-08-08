package hami.mainapp.Activity.PastPurchases.Model;

/**
 * Created by renjer on 2017-10-16.
 */

public class LocationWeather {
    private String temp;
    private int icon;

    //-----------------------------------------------
    public LocationWeather() {
    }
    //-----------------------------------------------

    public LocationWeather(String temp, int icon) {
        this.temp = temp;
        this.icon = icon;
    }

    //-----------------------------------------------

    public String getTemp() {
        return temp;
    }

    public int getIcon() {
        return icon;
    }
}
