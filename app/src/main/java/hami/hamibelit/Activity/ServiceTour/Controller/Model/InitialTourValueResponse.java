package hami.hamibelit.Activity.ServiceTour.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2017-12-21.
 */

public class InitialTourValueResponse {

    @SerializedName("kind")
    private ArrayList<NameValue> kindList;
    @SerializedName("from")
    private ArrayList<NameValue> fromList;
    @SerializedName("to")
    private ArrayList<NameValue> toList;
    @SerializedName("start_date")
    private ArrayList<DateTour> dateTourCalendar;
    //-----------------------------------------------

    public InitialTourValueResponse() {
    }
    //-----------------------------------------------

}
