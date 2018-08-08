package hami.mainapp.Activity.ServiceHotel.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by renjer on 2018-01-10.
 */

public class SearchCity implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Code")
    private String code;
    @SerializedName("countrycode")
    private String countryCode;
    @SerializedName("time")
    private String time;
    //-----------------------------------------------

    public SearchCity() {
    }

    //-----------------------------------------------


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getTime() {
        return time;
    }
}
