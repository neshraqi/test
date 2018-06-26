package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 1/19/2017.
 */

public class RulesParto {
    @SerializedName("success")
    private Boolean Success;

    @SerializedName("Error")
    private String Error;

    @SerializedName("FareRules")
    private ArrayList<RulesItemParto> FareRulesList;
    //-----------------------------------------------


    public RulesParto() {
    }
    //-----------------------------------------------


    public Boolean getSuccess() {
        return Success;
    }

    public String getError() {
        return Error;
    }

    public ArrayList<RulesItemParto> getFareRulesList() {
        return FareRulesList;
    }
}
