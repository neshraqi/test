package com.hami.serviceflight.Services.International.Controller.Model2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-11-02.
 */

public class OperatingAirlineParto implements Parcelable {
    @SerializedName("Code")
    private String code;
    @SerializedName("FlightNumber")
    private String flightNumber;
    @SerializedName("Equipment")
    private String equipment;
    //-----------------------------------------------

    public OperatingAirlineParto() {
    }
    //-----------------------------------------------


    protected OperatingAirlineParto(Parcel in) {
        code = in.readString();
        flightNumber = in.readString();
        equipment = in.readString();
    }

    public static final Creator<OperatingAirlineParto> CREATOR = new Creator<OperatingAirlineParto>() {
        @Override
        public OperatingAirlineParto createFromParcel(Parcel in) {
            return new OperatingAirlineParto(in);
        }

        @Override
        public OperatingAirlineParto[] newArray(int size) {
            return new OperatingAirlineParto[size];
        }
    };

    public String getCode() {
        return code;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getEquipment() {
        return equipment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(flightNumber);
        dest.writeString(equipment);
    }
}
