package com.hami.serviceflight.Services.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by renjer on 1/10/2017.
 */

public class DomesticFlightResponse implements Parcelable {
    @SerializedName("data")
    private ArrayList<DomesticFlight> domesticFlights;

    private HashMap<String, String> airlineList;
    //-----------------------------------------------

    public DomesticFlightResponse() {
    }

    //-----------------------------------------------

    protected DomesticFlightResponse(Parcel in) {
        domesticFlights = in.createTypedArrayList(DomesticFlight.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(domesticFlights);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DomesticFlightResponse> CREATOR = new Creator<DomesticFlightResponse>() {
        @Override
        public DomesticFlightResponse createFromParcel(Parcel in) {
            return new DomesticFlightResponse(in);
        }

        @Override
        public DomesticFlightResponse[] newArray(int size) {
            return new DomesticFlightResponse[size];
        }
    };

    public ArrayList<DomesticFlight> getDomesticFlights() {
        return domesticFlights;
    }

    public HashMap<String, String> getAirlineList() {
        return airlineList;
    }

    public void setAirlineList(HashMap<String, String> airlineList) {
        this.airlineList = airlineList;
    }
}
