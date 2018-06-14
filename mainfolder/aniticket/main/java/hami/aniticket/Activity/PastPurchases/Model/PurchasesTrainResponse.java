package hami.hamibelit.Activity.PastPurchases.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2017-11-23.
 */

public class PurchasesTrainResponse {

    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("request_token_id")
    private String requestTokenId;
    @SerializedName("list")
    private ArrayList<PurchasesTrain> purchasesTrainList;
    //-----------------------------------------------


    public PurchasesTrainResponse() {
    }

    //-----------------------------------------------

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getUserId() {
        return userId;
    }

    public String getRequestTokenId() {
        return requestTokenId;
    }

    public ArrayList<PurchasesTrain> getPurchasesTrainList() {
        return purchasesTrainList;
    }
}
