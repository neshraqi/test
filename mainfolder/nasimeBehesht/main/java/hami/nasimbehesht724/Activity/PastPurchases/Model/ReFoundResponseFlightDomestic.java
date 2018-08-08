package hami.mainapp.Activity.PastPurchases.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-11-27.
 */

public class ReFoundResponseFlightDomestic {
    @SerializedName("msg")
    private String msg;
    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private ReFoundResponseDataFlightDomestic reFoundResponseDataFlightDomestic;
    public String nextIdPassenger = "";
    //-----------------------------------------------


    public ReFoundResponseFlightDomestic() {
    }

    //-----------------------------------------------

    public String getNextIdPassenger() {
        return nextIdPassenger;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public ReFoundResponseDataFlightDomestic getReFoundResponseDataFlightDomestic() {
        return reFoundResponseDataFlightDomestic;
    }

    public void setNextIdPassenger(String nextIdPassenger) {
        this.nextIdPassenger = nextIdPassenger;
    }
}
