package hami.aniticket.Activity.ServiceSearch.ServiceBus.Services.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-02-12.
 */

public class SeatResponseData {

    @SerializedName("data")
    private SeatResponse seatResponse;

    @SerializedName("msg")
    private String msg;



    //-----------------------------------------------

    public SeatResponseData() {
    }
    //-----------------------------------------------

    public SeatResponse getSeatResponse() {
        return seatResponse;
    }

    public String getMsg() {
        return msg;
    }

}
