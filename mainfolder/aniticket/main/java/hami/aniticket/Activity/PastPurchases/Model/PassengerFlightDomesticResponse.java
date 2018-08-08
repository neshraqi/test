package hami.mainapp.Activity.PastPurchases.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2017-11-26.
 */

public class PassengerFlightDomesticResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("request_token_id")
    private String request_token_id;
    @SerializedName("list")
    private ArrayList<PassengerFlightDomestic> passengerFlightDomestics;
    //-----------------------------------------------

    public PassengerFlightDomesticResponse() {
    }

    //-----------------------------------------------

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getUserId() {
        return user_id;
    }

    public String getRequestTokenId() {
        return request_token_id;
    }

    public ArrayList<PassengerFlightDomestic> getPassengerFlightDomestics() {
        return passengerFlightDomestics;
    }
}
