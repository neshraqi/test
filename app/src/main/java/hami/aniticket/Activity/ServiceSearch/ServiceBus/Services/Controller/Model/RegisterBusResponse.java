package hami.aniticket.Activity.ServiceSearch.ServiceBus.Services.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-02-19.
 */

public class RegisterBusResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    //-----------------------------------------------

    public RegisterBusResponse() {
    }

    //-----------------------------------------------
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
