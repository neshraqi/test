package hami.mainapp.flight.Services.Domestic.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-02-08.
 */

public class FlightInformationItem {
    @SerializedName("airline")
    private String airline;
    @SerializedName("flight_number")
    private String flightNumber;
    @SerializedName("source")
    private String source;
    @SerializedName("status")
    private String status;
    @SerializedName("naghale")
    private String naghale;
    @SerializedName("real_time")
    private String realTime;
    @SerializedName("airplan")
    private String airPlan;
    @SerializedName("day")
    private String day;
    @SerializedName("plan_time")
    private String planTime;
    @SerializedName("date")
    private String date;
    //-----------------------------------------------


    public FlightInformationItem() {
    }
    //-----------------------------------------------

    public String getAirline() {
        return airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSource() {
        return source;
    }

    public String getStatus() {
        return status;
    }

    public String getNaghale() {
        return naghale;
    }

    public String getRealTime() {
        return realTime;
    }

    public String getAirPlan() {
        return airPlan;
    }

    public String getDay() {
        return day;
    }

    public String getPlanTime() {
        return planTime;
    }

    public String getDate() {
        return date;
    }
}
