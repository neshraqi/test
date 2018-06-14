package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 1/24/2017.
 */

public class BaggageInfo {
    @SerializedName("Arrival")
    private String Arrival;
    @SerializedName("Baggage")
    private String Baggage;
    @SerializedName("Departure")
    private String Departure;
    @SerializedName("FlightNo")
    private String FlightNo;
    //-----------------------------------------------

    public BaggageInfo() {
    }
    //-----------------------------------------------

    public String getArrival() {
        return Arrival;
    }

    public String getBaggage() {
        return Baggage;
    }

    public String getDeparture() {
        return Departure;
    }

    public String getFlightNo() {
        return FlightNo;
    }
}
