package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-03-04.
 */

public class DomesticLocationResponse {
    @SerializedName("Success")
    private Boolean success;
    @SerializedName("Message")
    private String message;
    @SerializedName("Locations")
    private ArrayList<DomesticLocation> locationList;
    //-----------------------------------------------

    public DomesticLocationResponse() {
    }
    //-----------------------------------------------

    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<DomesticLocation> getLocationList() {
        return locationList;
    }
}
