package com.hami.servicehotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-01-17.
 */

public class RoomInfo implements Parcelable{

    @SerializedName("RoomId")
    private String RoomId;
    @SerializedName("RoomName")
    private String RoomName;
    @SerializedName("CountAdult")
    private int CountAdult;
    @SerializedName("CountChild")
    private int CountChild;
    @SerializedName("RoomPrice")
    private String RoomPrice;

//    @SerializedName("DailyPrices")
//    private String DailyPrices;
    //-----------------------------------------------

    public RoomInfo() {
    }
    //-----------------------------------------------

    protected RoomInfo(Parcel in) {
        RoomId = in.readString();
        RoomName = in.readString();
        CountAdult = in.readInt();
        CountChild = in.readInt();
        RoomPrice = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(RoomId);
        dest.writeString(RoomName);
        dest.writeInt(CountAdult);
        dest.writeInt(CountChild);
        dest.writeString(RoomPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RoomInfo> CREATOR = new Creator<RoomInfo>() {
        @Override
        public RoomInfo createFromParcel(Parcel in) {
            return new RoomInfo(in);
        }

        @Override
        public RoomInfo[] newArray(int size) {
            return new RoomInfo[size];
        }
    };

    public String getRoomId() {
        return RoomId;
    }

    public String getRoomName() {
        return RoomName;
    }

    public int getCountAdult() {
        return CountAdult;
    }

    public int getCountChild() {
        return CountChild;
    }

    public String getRoomPrice() {
        return RoomPrice;
    }
}

