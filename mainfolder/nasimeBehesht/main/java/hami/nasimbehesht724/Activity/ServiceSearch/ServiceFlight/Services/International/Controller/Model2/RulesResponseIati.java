package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.BaggaegeResponse;


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
