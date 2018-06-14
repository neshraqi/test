package hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by renjer on 2018-02-08.
 */

public class DomesticHotelResponse {
    @SerializedName("hotels")
    private ArrayList<DomesticHotel> domesticHotels;

    private ArrayList<String> filterRate;
    //-----------------------------------------------


    public DomesticHotelResponse() {
    }

    //-----------------------------------------------

    public ArrayList<DomesticHotel> getDomesticHotels() {
        return domesticHotels;
    }

    public void setFilterRate(ArrayList<String> filterRate) {
        this.filterRate = filterRate;
    }

    public ArrayList<String> getFilterRate() {
        Collections.sort(filterRate);
        return filterRate;
    }
}
