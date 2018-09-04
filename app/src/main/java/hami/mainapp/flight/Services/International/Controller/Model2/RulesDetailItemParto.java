package hami.mainapp.flight.Services.International.Controller.Model2;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 1/19/2017.
 */

public class RulesDetailItemParto {
    @SerializedName("Category")
    private String Category;

    @SerializedName("Rules")
    private String Rules;
    //-----------------------------------------------


    public RulesDetailItemParto() {
    }
    //-----------------------------------------------


    public String getCategory() {
        return Category;
    }

    public String getRules() {
        return Rules;
    }
}
