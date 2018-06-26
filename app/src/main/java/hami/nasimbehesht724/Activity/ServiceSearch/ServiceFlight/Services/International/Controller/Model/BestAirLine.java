package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-02-06.
 */

public class BestAirLine {
    @SerializedName("2")
    private String price2;
    @SerializedName("1")
    private String price1;
    @SerializedName("3")
    private String price3;
    @SerializedName("carrierName")
    private String carrierName;
    @SerializedName("fileName")
    private String fileName;
    @SerializedName("id")
    private String id;
    //-----------------------------------------------

    public BestAirLine() {
    }
    //-----------------------------------------------

    public String getCarrierName() {
        return carrierName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getId() {
        return id;
    }
    //-----------------------------------------------

}
