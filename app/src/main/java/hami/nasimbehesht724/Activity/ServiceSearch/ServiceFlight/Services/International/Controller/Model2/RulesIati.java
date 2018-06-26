package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 1/19/2017.
 */

public class RulesIati {
    @SerializedName("name")
    private String name;

    @SerializedName("code")
    private String code;

    @SerializedName("rules")
    private ArrayList<String> rules;
    //-----------------------------------------------


    public RulesIati() {
    }

    //-----------------------------------------------

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<String> getRules() {
        return rules;
    }
}
