package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 1/10/2017.
 */

public class FlightInternational {
    @SerializedName("outbound")
    private ArrayList<OutBound> outBounds;
    @SerializedName("return")
    private ArrayList<OutBound> returns;
    //-----------------------------------------------

    public FlightInternational() {

    }
    //-----------------------------------------------

    public ArrayList<OutBound> getOutBounds() {
        return outBounds;
    }

    public ArrayList<OutBound> getFullListReturns() {
        return returns;
    }
}



