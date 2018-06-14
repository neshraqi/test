package hami.hamibelit.Activity.ServiceHotel.Domestic.Controller.Model;

import java.io.Serializable;

/**
 * Created by renjer on 2018-02-07.
 */

public class HotelDomesticCity implements Serializable {
    private String name;
    private String nameFa;
    private String stateFa;
    private String latitude;
    private String longitude;
    public final static int HEADER = 0;
    public final static int CITY = 1;
    private int type = CITY;
    //-----------------------------------------------

    public HotelDomesticCity() {
    }

    //-----------------------------------------------
    public static HotelDomesticCity newInstance(String name, String nameFa, String stateFa, String latitude, String longitude) {
        HotelDomesticCity city = new HotelDomesticCity();
        city.type = CITY;
        city.name = name;
        city.nameFa = nameFa;
        city.stateFa = stateFa;
        city.latitude = latitude;
        city.longitude = longitude;
        return city;
    }

    //-----------------------------------------------
    public static HotelDomesticCity newInstance(String title) {
        HotelDomesticCity city = new HotelDomesticCity();
        city.type = HEADER;
        city.name = title;
        city.nameFa = title;
        city.stateFa = title;
        return city;
    }

    //-----------------------------------------------
    public HotelDomesticCity(String name, String nameFa, String stateFa, String latitude, String longitude) {
        this.name = name;
        this.nameFa = nameFa;
        this.stateFa = stateFa;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    //-----------------------------------------------

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getNameFa() {
        return nameFa;
    }

    public String getStateFa() {
        return stateFa;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    //-----------------------------------------------

    public void setName(String name) {
        this.name = name;
    }

    public void setNameFa(String nameFa) {
        this.nameFa = nameFa;
    }

    public void setStateFa(String stateFa) {
        this.stateFa = stateFa;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
