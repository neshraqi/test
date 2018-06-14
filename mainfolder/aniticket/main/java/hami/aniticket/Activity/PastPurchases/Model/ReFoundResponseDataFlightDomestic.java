package hami.hamibelit.Activity.PastPurchases.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2017-11-27.
 */

public class ReFoundResponseDataFlightDomestic {

    @SerializedName("passengers")
    private ArrayList<PassengerFlightDomestic> passengerFlightDomestic;
    @SerializedName("response")
    private ReFoundPassengerResponse reFoundPassengerResponse;
    //-----------------------------------------------


    public ReFoundResponseDataFlightDomestic() {
    }

    //-----------------------------------------------


    public ArrayList<PassengerFlightDomestic> getPassengerFlightDomestic() {
        return passengerFlightDomestic;
    }

    public ReFoundPassengerResponse getReFoundPassengerResponse() {
        return reFoundPassengerResponse;
    }
}
