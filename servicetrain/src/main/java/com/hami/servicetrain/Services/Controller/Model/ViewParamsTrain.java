package com.hami.servicetrain.Services.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-03-12.
 */

public class ViewParamsTrain implements Parcelable{
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

    protected ViewParamsTrain(Parcel in) {
        discount = in.readInt();
        wallet = in.readInt();
        credit = in.readInt();
        price = in.readString();
        finalPrice = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(discount);
        dest.writeInt(wallet);
        dest.writeInt(credit);
        dest.writeString(price);
        dest.writeString(finalPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ViewParamsTrain> CREATOR = new Creator<ViewParamsTrain>() {
        @Override
        public ViewParamsTrain createFromParcel(Parcel in) {
            return new ViewParamsTrain(in);
        }

        @Override
        public ViewParamsTrain[] newArray(int size) {
            return new ViewParamsTrain[size];
        }
    };

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
