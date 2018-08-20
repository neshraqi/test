package com.hami.servicehotel.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-01-17.
 */

public class HotelDetail {

    @SerializedName("FareSourceCode")
    private String FareSourceCode;
    @SerializedName("Offer")
    private String Offer;
    @SerializedName("NonRefundable")
    private Boolean NonRefundable;
    @SerializedName("PaymentDeadline")
    private String PaymentDeadline;
    @SerializedName("Currency")
    private String Currency;
    @SerializedName("PlainTextCancellationPolicy")
    private String PlainTextCancellationPolicy;
    @SerializedName("NetRate")
    private Double NetRate;
    @SerializedName("Rooms")
    private ArrayList<RoomInfo> rooms;
    @SerializedName("CancellationPolicies")
    private ArrayList<HotelCancellationPolicies> cancellationPolicies;
    @SerializedName("Remarks")
    private String[] remarks;
    @SerializedName("reserveId")
    private String reserveId;
    @SerializedName("HotelContactInfo")
    private ArrayList<HotelContactInfo> hotelContactInfoList;
    @SerializedName("id")
    private String id;
    @SerializedName("noe")
    private int noe;
    @SerializedName("level")
    private int level;
    @SerializedName("HotelInfo")
    private HotelInfo hotelInfo;
    @SerializedName("numberp")
    private int numberp;
    @SerializedName("night")
    private int night;
    @SerializedName("price")
    private String price;
    @SerializedName("discountprice")
    private String discountprice;
    @SerializedName("images")
    private String[] images;

    //-----------------------------------------------

    public HotelDetail() {
    }

    //-----------------------------------------------

    public String getFareSourceCode() {
        return FareSourceCode;
    }

    public String getOffer() {
        return Offer;
    }

    public Boolean getNonRefundable() {
        return NonRefundable;
    }

    public String getPaymentDeadline() {
        return PaymentDeadline;
    }

    public String getCurrency() {
        return Currency;
    }

    public String getPlainTextCancellationPolicy() {
        return PlainTextCancellationPolicy;
    }

    public Double getNetRate() {
        return NetRate;
    }

    public ArrayList<RoomInfo> getRooms() {
        return rooms;
    }

    public ArrayList<HotelCancellationPolicies> getCancellationPolicies() {
        return cancellationPolicies;
    }

    public String[] getRemarks() {
        return remarks;
    }

    public String getId() {
        return id;
    }

    public int getNoe() {
        return noe;
    }

    public int getLevel() {
        return level;
    }

    public HotelInfo getHotelInfo() {
        return hotelInfo;
    }

    public int getNumberp() {
        return numberp;
    }

    public int getNight() {
        return night;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscountprice() {
        return discountprice;
    }

    public String getReserveId() {
        return reserveId;
    }

    public ArrayList<HotelContactInfo> getHotelContactInfoList() {
        return hotelContactInfoList;
    }

    public String[] getImages() {
        return images;
    }
}

