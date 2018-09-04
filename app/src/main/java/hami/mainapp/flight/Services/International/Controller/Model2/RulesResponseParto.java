package hami.mainapp.flight.Services.International.Controller.Model2;

import com.google.gson.annotations.SerializedName;
import hami.mainapp.flight.Services.International.Controller.Model.BaggaegeResponse;

public class RulesResponseParto {


    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("flag")
    private int flag;

    @SerializedName("message")
    private String message;

    @SerializedName("baggage")
    private BaggaegeResponse baggage;

//    @SerializedName("rules")
    private RulesParto rules;
    //-----------------------------------------------


    public RulesResponseParto() {
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

    public RulesParto getRules() {
        return rules;
    }
}
