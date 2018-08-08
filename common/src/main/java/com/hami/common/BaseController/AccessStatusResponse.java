package com.hami.common.BaseController;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by renjer on 2017-04-03.
 */

public class AccessStatusResponse implements Serializable {
    @SerializedName("Success")
    private Boolean success;
    @SerializedName("bus")
    private String bus;
    @SerializedName("flight")
    private String flight;
    @SerializedName("international")
    private String international;
    @SerializedName("train")
    private String train;
    @SerializedName("domesticHotel")
    private String domesticHotel;
    @SerializedName("internationalHotel")
    private String internationalHotel;
    @SerializedName("tour")
    private String tour;
    //-----------------------------------------------

    public AccessStatusResponse() {
    }
    //-----------------------------------------------

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getBus() {
        if (bus.contains("1"))
            return true;
        return false;
    }

    public Boolean getFlight() {
        if (flight.contains("1"))
            return true;
        return false;
    }

    public Boolean getInternational() {
        if (international.contains("1"))
            return true;
        return false;
    }

    public Boolean getTrain() {
        if (train.contains("1"))
            return true;
        return false;
    }

    public Boolean getDomesticHotel() {
        if (domesticHotel.contains("1"))
            return true;
        return false;
    }

    public Boolean getInternationalHotel() {
        if (internationalHotel.contains("1"))
            return true;
        return false;
    }

    public Boolean getTour() {
        if (tour.contains("1"))
            return true;
        return false;
    }
}
