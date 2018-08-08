package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 1/19/2017.
 */

public class RulesItemParto {
    @SerializedName("Airline")
    private String Airline;

    @SerializedName("CityPair")
    private String CityPair;

    @SerializedName("FareBasis")
    private String FareBasis;

    @SerializedName("RuleDetails")
    private ArrayList<RulesDetailItemParto> RuleDetailsList;

    //-----------------------------------------------


    public RulesItemParto() {
    }

    //-----------------------------------------------


    public String getAirline() {
        return Airline;
    }

    public String getCityPair() {
        return CityPair;
    }

    public String getFareBasis() {
        return FareBasis;
    }

    public ArrayList<RulesDetailItemParto> getRuleDetailsList() {
        return RuleDetailsList;
    }
}
