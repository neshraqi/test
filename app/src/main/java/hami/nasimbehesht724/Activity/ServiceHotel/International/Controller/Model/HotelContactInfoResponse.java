package hami.nasimbehesht724.Activity.ServiceHotel.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-01-30.
 */

public class HotelContactInfoResponse {
    @SerializedName("tech")
    private String tech;
    @SerializedName("value")
    private String value;
    //-----------------------------------------------

    public HotelContactInfoResponse() {
    }
    //-----------------------------------------------

    public String getTech() {
        return tech;
    }

    public String getValue() {
        return value;
    }
}
