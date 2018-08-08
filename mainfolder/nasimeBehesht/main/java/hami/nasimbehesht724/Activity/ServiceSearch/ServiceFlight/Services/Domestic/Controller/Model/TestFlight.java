package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 1/30/2017.
 */

public class TestFlight {

    @SerializedName("class")
    private String class_;
    @SerializedName("price")
    private String price;
    //-----------------------------------------------


    public TestFlight() {
    }
    //-----------------------------------------------

    public String getClass_() {
        return class_;
    }

    public String getPrice() {
        return price;
    }
}
