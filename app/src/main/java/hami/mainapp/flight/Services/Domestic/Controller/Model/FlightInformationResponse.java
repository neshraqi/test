package hami.mainapp.flight.Services.Domestic.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by renjer on 2017-02-08.
 */

public class FlightInformationResponse {
    @SerializedName("in")
    private List<FlightInformationItem> input;
    @SerializedName("out")
    private List<FlightInformationItem> output;
    //-----------------------------------------------

    public FlightInformationResponse() {
    }
    //-----------------------------------------------

    public List<FlightInformationItem> getInput() {
        return input;
    }

    public List<FlightInformationItem> getOutput() {
        return output;
    }
}
