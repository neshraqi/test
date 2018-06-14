package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 1/28/2017.
 */

public class ResultFactorResponse {
    @SerializedName("msg")
    private String msg;
    @SerializedName("code")
    private int code;
    @SerializedName("view")
    private ViewRequest viewRequest;
    //-----------------------------------------------

    public ResultFactorResponse() {
    }
    //-----------------------------------------------

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public ViewRequest getViewRequest() {
        return viewRequest;
    }
}
