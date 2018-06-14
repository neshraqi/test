package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-03-04.
 */

public class DomesticLocation {

    @SerializedName("Code")
    private String code;
    @SerializedName("EnglishName")
    private String englishName;
    @SerializedName("PersianName")
    private String persianName;
    //-----------------------------------------------

    public DomesticLocation() {
    }
    //-----------------------------------------------

    public String getCode() {
        return code;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getPersianName() {
        return persianName;
    }
}
