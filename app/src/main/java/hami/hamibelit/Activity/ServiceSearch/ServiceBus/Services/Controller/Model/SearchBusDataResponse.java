package hami.hamibelit.Activity.ServiceSearch.ServiceBus.Services.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2017-02-11.
 */

public class SearchBusDataResponse {
    @SerializedName("data")
    private ArrayList<SearchBusResponse> listSearchBusResponse;
    //-----------------------------------------------

    public SearchBusDataResponse() {
    }
    //-----------------------------------------------

    public ArrayList<SearchBusResponse> getListSearchBusResponse() {
        return listSearchBusResponse;
    }
}
