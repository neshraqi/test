package hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-03-12.
 */

public class ViewParamsTrain {
    @SerializedName("discount")
    public int discount;
    @SerializedName("wallet")
    public int wallet;
    @SerializedName("etebar")
    public int credit;
    @SerializedName("price")
    public String price;
    @SerializedName("fprice")
    public String finalPrice;
    @SerializedName("params")
    public ParamTrain params;
    //-----------------------------------------------


    public ViewParamsTrain() {
    }
    //-----------------------------------------------

    public int getDiscount() {
        return discount;
    }

    public int getWallet() {
        return wallet;
    }

    public int getCredit() {
        return credit;
    }

    public String getPrice() {
        return price;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public ParamTrain getParams() {
        return params;
    }
}
