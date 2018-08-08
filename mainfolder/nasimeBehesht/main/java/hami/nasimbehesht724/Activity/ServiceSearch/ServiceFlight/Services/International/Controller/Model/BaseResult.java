package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 1/29/2017.
 */

public class BaseResult {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    //-----------------------------------------------

    public BaseResult() {
    }
    //-----------------------------------------------

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
