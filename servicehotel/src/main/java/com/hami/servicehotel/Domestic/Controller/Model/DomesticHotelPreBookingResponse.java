package com.hami.servicehotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-02-10.
 */

public class DomesticHotelPreBookingResponse implements Parcelable {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("roomInfo")
    private DomesticHotelRoom roomInfo;
    @SerializedName("hotelInfoRoom")
    private DomesticHotelBookingProcessData hotelInfoRoom;
    @SerializedName("roomOptions")
    private ArrayList<DomesticHotelRoomOption> roomOptions;
    //-----------------------------------------------

    public DomesticHotelPreBookingResponse() {
    }
    //-----------------------------------------------


    protected DomesticHotelPreBookingResponse(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        roomInfo = in.readParcelable(DomesticHotelRoom.class.getClassLoader());
        hotelInfoRoom = in.readParcelable(DomesticHotelBookingProcessData.class.getClassLoader());
        roomOptions = in.createTypedArrayList(DomesticHotelRoomOption.CREATOR);
    }

    public static final Creator<DomesticHotelPreBookingResponse> CREATOR = new Creator<DomesticHotelPreBookingResponse>() {
        @Override
        public DomesticHotelPreBookingResponse createFromParcel(Parcel in) {
            return new DomesticHotelPreBookingResponse(in);
        }

        @Override
        public DomesticHotelPreBookingResponse[] newArray(int size) {
            return new DomesticHotelPreBookingResponse[size];
        }
    };

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public DomesticHotelRoom getRoomInfo() {
        return roomInfo;
    }

    public DomesticHotelBookingProcessData getHotelInfoRoom() {
        return hotelInfoRoom;
    }

    public ArrayList<DomesticHotelRoomOption> getRoomOptions() {
        return roomOptions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeParcelable(roomInfo, flags);
        dest.writeParcelable(hotelInfoRoom, flags);
        dest.writeTypedList(roomOptions);
    }
}
