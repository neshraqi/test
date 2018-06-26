package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 1/24/2017.
 */

public class RuleDetails {

    @SerializedName("category")
    private String category;

    @SerializedName("Category")
    private String Category;

    @SerializedName("RulesIati")
    private String Rules;

    @SerializedName("rules")
    private String rules;
    //-----------------------------------------------


    public RuleDetails() {
    }

    //-----------------------------------------------
    public String getCategory() {
        if(category!=null){
            return category;
        }
        else
            return Category;
    }

    public String getRules() {
        if(rules!=null){
            return rules;
        }
        else
            return Rules;
    }
}
