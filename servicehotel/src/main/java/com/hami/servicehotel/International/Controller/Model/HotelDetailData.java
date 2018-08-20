package com.hami.servicehotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-01-17.
 */

public class HotelDetailData implements Parcelable {

    @SerializedName("searchId")
    private String searchId;

    @SerializedName("reserveId")
    private String reserveId;

    @SerializedName("wsearchId")
    private String wsearchId;

    @SerializedName("searchinfo")
    private InternationalHotelSearchRequest hotelSearchRequest;

    @SerializedName("hotels")
    private HotelsMoreInfo hotels;
    //-----------------------------------------------


    public HotelDetailData() {
    }
    //-----------------------------------------------


    protected HotelDetailData(Parcel in) {
        searchId = in.readString();
        reserveId = in.readString();
        wsearchId = in.readString();
        hotelSearchRequest = in.readParcelable(InternationalHotelSearchRequest.class.getClassLoader());
        hotels = in.readParcelable(HotelsMoreInfo.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(searchId);
        dest.writeString(reserveId);
        dest.writeString(wsearchId);
        dest.writeParcelable(hotelSearchRequest, flags);
        dest.writeParcelable(hotels, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotelDetailData> CREATOR = new Creator<HotelDetailData>() {
        @Override
        public HotelDetailData createFromParcel(Parcel in) {
            return new HotelDetailData(in);
        }

        @Override
        public HotelDetailData[] newArray(int size) {
            return new HotelDetailData[size];
        }
    };

    public String getSearchId() {
        return searchId;
    }

    public String getReserveId() {
        return reserveId;
    }

    public String getWsearchId() {
        return wsearchId;
    }

    public InternationalHotelSearchRequest getHotelSearchRequest() {
        return hotelSearchRequest;
    }

    public HotelsMoreInfo getHotels() {
        return hotels;
    }

}
