package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 1/24/2017.
 */

public class BaggaegeResponse {
    @SerializedName("Success")
    private Boolean errorCode;

//    @SerializedName("Error")
//    private Object error;

//    @SerializedName("Services")
//    private Object Services;

    @SerializedName("BaggageInfoes")
    private ArrayList<BaggageInfo> baggageInfoList;
    //-----------------------------------------------

    public BaggaegeResponse() {
    }
    //-----------------------------------------------

    public Boolean getErrorCode() {
        return errorCode;
    }

//    public Object getError() {
//        return error;
//    }

//    public Object getServices() {
//        return Services;
//    }

    public ArrayList<BaggageInfo> getBaggageInfoList() {
        return baggageInfoList;
    }
}
