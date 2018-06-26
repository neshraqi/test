package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 1/28/2017.
 */

public class ViewRequest {
    @SerializedName("ticket_id")
    private String ticketId;
    @SerializedName("sumfinalprice")
    private String sumFinalPrice;
    @SerializedName("bank")
    private String bank;
    @SerializedName("fbank")
    private String Farsibank;
    //-----------------------------------------------

    public ViewRequest() {
    }
    //-----------------------------------------------

    public String getTicketId() {
        return ticketId;
    }

    public String getSumFinalPrice() {
        return sumFinalPrice;
    }

    public String getBank() {
        return bank;
    }

    public String getFarsibank() {
        return Farsibank;
    }
}
