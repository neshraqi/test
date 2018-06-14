package hami.hamibelit.Activity.ServiceHotel.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-01-17.
 */

public class HotelCancellationPolicies {
    @SerializedName("Amount")
    private Double Amount;
    @SerializedName("FromDate")
    private String FromDate;
    //-----------------------------------------------

    public HotelCancellationPolicies() {
    }
    //-----------------------------------------------

    public Double getAmount() {
        return Amount;
    }

    public String getFromDate() {
        return FromDate;
    }
}
