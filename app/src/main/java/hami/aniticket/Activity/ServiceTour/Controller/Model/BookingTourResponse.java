package hami.aniticket.Activity.ServiceTour.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-03-11.
 */

public class BookingTourResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("details")
    private BookingTourDetails details;
    //-----------------------------------------------

    public BookingTourResponse() {
    }
    //-----------------------------------------------

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public BookingTourDetails getDetails() {
        return details;
    }
}
