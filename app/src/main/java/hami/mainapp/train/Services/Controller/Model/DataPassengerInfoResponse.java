package hami.mainapp.train.Services.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2/1/2017.
 */

public class DataPassengerInfoResponse {
    @SerializedName("msg")
    private String msg;
    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private DataPassengerInfo dataPassengerInfo;
    //-----------------------------------------------

    public DataPassengerInfoResponse() {
    }
    //-----------------------------------------------

    public DataPassengerInfoResponse(String msg, int code, DataPassengerInfo dataPassengerInfo) {
        this.msg = msg;
        this.code = code;
        this.dataPassengerInfo = dataPassengerInfo;
    }

    //-----------------------------------------------

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public DataPassengerInfo getDataPassengerInfo() {
        return dataPassengerInfo;
    }
}
