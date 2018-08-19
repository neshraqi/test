package com.hami.serviceflight.Services.International.Controller.Model2;

import com.google.gson.annotations.SerializedName;
import com.hami.serviceflight.Services.International.Controller.Model.BaggaegeResponse;

import java.util.ArrayList;

public class RulesResponseIati {



    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("flag")
    private int flag;

    @SerializedName("message")
    private String message;

    @SerializedName("baggage")
    private BaggaegeResponse baggage;

    @SerializedName("rules")
    private ArrayList<RulesIati> rules ;
    //-----------------------------------------------


    public RulesResponseIati() {
    }
    //-----------------------------------------------


    public int getErrorCode() {
        return errorCode;
    }

    public int getFlag() {
        return flag;
    }

    public String getMessage() {
        return message;
    }

    public BaggaegeResponse getBaggage() {
        return baggage;
    }

    public ArrayList<RulesIati> getRules() {
        return rules;
    }
}
