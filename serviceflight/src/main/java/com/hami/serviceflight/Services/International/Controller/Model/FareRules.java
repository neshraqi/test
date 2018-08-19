package com.hami.serviceflight.Services.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by renjer on 1/24/2017.
 */

public class FareRules {
    @SerializedName("Airline")
    private String Airline;
    @SerializedName("CityPair")
    private String CityPair;
    @SerializedName("FareBasis")
    private String FareBasis;
    @SerializedName("RuleDetails")
    private List<RuleDetails> ruleDetailsList;
    //-----------------------------------------------

    public FareRules() {
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

    public List<RuleDetails> getRuleDetailsList() {
        return ruleDetailsList;
    }
}
