package hami.hamibelit.Activity.PastPurchases.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-12-02.
 */

public class SplitResponse {

    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("newticketid")
    private long newTicketId;
    @SerializedName("newpnr")
    private String newPnr;
    //-----------------------------------------------

    public SplitResponse() {
    }

    //-----------------------------------------------

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public long getNewTicketId() {
        return newTicketId;
    }

    public String getNewPnr() {
        return newPnr;
    }
}
