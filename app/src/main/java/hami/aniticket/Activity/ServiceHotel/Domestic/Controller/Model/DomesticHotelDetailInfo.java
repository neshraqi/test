package hami.aniticket.Activity.ServiceHotel.Domestic.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-02-10.
 */

public class DomesticHotelDetailInfo {
    @SerializedName("city")
    private String city;
    @SerializedName("cityName")
    private String cityName;
    @SerializedName("id")
    private String id;
    @SerializedName("hotel")
    private String hotel;
    @SerializedName("fromDate")
    private String fromDate;
    @SerializedName("inDate")
    private String inDate;
    @SerializedName("numberOfNights")
    private String numberOfNights;
    //-----------------------------------------------

    public DomesticHotelDetailInfo() {
    }

    //-----------------------------------------------

    public String getCity() {
        return city;
    }

    public String getCityName() {
        return cityName;
    }

    public String getId() {
        return id;
    }

    public String getHotel() {
        return hotel;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getInDate() {
        return inDate;
    }

    public String getNumberOfNights() {
        return numberOfNights;
    }
}
