package com.hami.serviceflight.Services.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-03-12.
 */

public class ViewParamsDomestic implements Parcelable {
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
    public DomesticParams domesticParams;
    //-----------------------------------------------


    public ViewParamsDomestic() {
    }
    //-----------------------------------------------


    protected ViewParamsDomestic(Parcel in) {
        discount = in.readInt();
        wallet = in.readInt();
        credit = in.readInt();
        price = in.readString();
        finalPrice = in.readString();
        domesticParams = in.readParcelable(DomesticParams.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(discount);
        dest.writeInt(wallet);
        dest.writeInt(credit);
        dest.writeString(price);
        dest.writeString(finalPrice);
        dest.writeParcelable(domesticParams, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ViewParamsDomestic> CREATOR = new Creator<ViewParamsDomestic>() {
        @Override
        public ViewParamsDomestic createFromParcel(Parcel in) {
            return new ViewParamsDomestic(in);
        }

        @Override
        public ViewParamsDomestic[] newArray(int size) {
            return new ViewParamsDomestic[size];
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

    public DomesticParams getDomesticParams() {
        return domesticParams;
    }



}
