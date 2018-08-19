package com.hami.servicebus.Services.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-02-21.
 */

public class BusTicketInformationResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private BusTicketInformation busTicketInformation;

    @SerializedName("msg")
    private String msg;
    //-----------------------------------------------

    public int getCode() {
        return code;
    }

    public BusTicketInformation getBusTicketInformation() {
        return busTicketInformation;
    }

    public String getMsg() {
        return msg;
    }
}
