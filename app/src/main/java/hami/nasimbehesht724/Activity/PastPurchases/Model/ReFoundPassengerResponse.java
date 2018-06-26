package hami.nasimbehesht724.Activity.PastPurchases.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2017-11-27.
 */

public class ReFoundPassengerResponse {
    @SerializedName("msg")
    private String msg;

    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private ArrayList<ReFoundPassenger> reFoundPassengers;
    //-----------------------------------------------

    public ReFoundPassengerResponse() {
    }

    //-----------------------------------------------

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public ArrayList<ReFoundPassenger> getReFoundPassengers() {
        return reFoundPassengers;
    }
}
