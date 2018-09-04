package hami.mainapp.train.Services.Controller.Model;

import java.io.Serializable;

/**
 * Created by renjer on 2017-03-12.
 */

public class CityTrain implements Serializable {
    private String cityEng;
    private String cityPersian;
    private int type = CITY;
    public final static int HEADER = 0;
    public final static int CITY = 1;
    //-----------------------------------------------
    public CityTrain(String cityPersian) {
        this.cityPersian = cityPersian;
        type = HEADER;
    }

    //-----------------------------------------------
    public CityTrain(String cityEng, String cityPersian) {
        this.cityEng = cityEng;
        this.cityPersian = cityPersian;
        type = CITY;
    }
    //-----------------------------------------------

    public int getType() {
        return type;
    }

    public String getCityEng() {
        return cityEng;
    }

    public void setCityEng(String cityEng) {
        this.cityEng = cityEng;
    }

    public String getCityPersian() {
        return cityPersian;
    }

    public void setCityPersian(String cityPersian) {
        this.cityPersian = cityPersian;
    }
}
