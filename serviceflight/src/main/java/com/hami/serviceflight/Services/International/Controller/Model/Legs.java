package com.hami.serviceflight.Services.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 1/12/2017.
 */

public class Legs implements Parcelable {

    public final static String KEY_LEGS = "KEY_LEGS";
    @SerializedName("JourneyDuration")
    private String JourneyDuration;
    @SerializedName("arrivalAirport")
    private String arrivalAirport;
    @SerializedName("arrivalAirportName")
    private String arrivalAirportName;
    @SerializedName("arrivalCityName")
    private String arrivalCityName;
    @SerializedName("arrivalTime")
    private String arrivalTime;
    @SerializedName("carrierCode")
    private String carrierCode;
    @SerializedName("carrierName")
    private String carrierName;
    @SerializedName("departureAirport")
    private String departureAirport;
    @SerializedName("departureAirportName")
    private String departureAirportName;
    @SerializedName("departureCityName")
    private String departureCityName;
    @SerializedName("departureTerminal")
    private String departureTerminal;
    @SerializedName("departureTime")
    private String departureTime;
    @SerializedName("flightNo")
    private String flightNo;
    @SerializedName("operatorCode")
    private String operatorCode;
    @SerializedName("operatorName")
    private String operatorName;
    @SerializedName("arrivalCityPersian")
    private String arrivalCityPersian;
    @SerializedName("arrivalAirportPersian")
    private String arrivalAirportPersian;
    @SerializedName("departureCityPersian")
    private String departureCityPersian;
    @SerializedName("departureAirportPersian")
    private String departureAirportPersian;
    //-----------------------------------------------

    public Legs() {
    }
    //-----------------------------------------------

    protected Legs(Parcel in) {
        JourneyDuration = in.readString();
        arrivalAirport = in.readString();
        arrivalAirportName = in.readString();
        arrivalCityName = in.readString();
        arrivalTime = in.readString();
        carrierCode = in.readString();
        carrierName = in.readString();
        departureAirport = in.readString();
        departureAirportName = in.readString();
        departureCityName = in.readString();
        departureTerminal = in.readString();
        departureTime = in.readString();
        flightNo = in.readString();
        operatorCode = in.readString();
        operatorName = in.readString();
        arrivalCityPersian = in.readString();
        arrivalAirportPersian = in.readString();
        departureCityPersian = in.readString();
        departureAirportPersian = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(JourneyDuration);
        dest.writeString(arrivalAirport);
        dest.writeString(arrivalAirportName);
        dest.writeString(arrivalCityName);
        dest.writeString(arrivalTime);
        dest.writeString(carrierCode);
        dest.writeString(carrierName);
        dest.writeString(departureAirport);
        dest.writeString(departureAirportName);
        dest.writeString(departureCityName);
        dest.writeString(departureTerminal);
        dest.writeString(departureTime);
        dest.writeString(flightNo);
        dest.writeString(operatorCode);
        dest.writeString(operatorName);
        dest.writeString(arrivalCityPersian);
        dest.writeString(arrivalAirportPersian);
        dest.writeString(departureCityPersian);
        dest.writeString(departureAirportPersian);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Legs> CREATOR = new Creator<Legs>() {
        @Override
        public Legs createFromParcel(Parcel in) {
            return new Legs(in);
        }

        @Override
        public Legs[] newArray(int size) {
            return new Legs[size];
        }
    };

    public String getJourneyDuration() {
        return JourneyDuration;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public String getArrivalCityName() {
        return arrivalCityName;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public String getDepartureCityName() {
        return departureCityName;
    }

    public String getDepartureTerminal() {
        return departureTerminal;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getArrivalCityPersian() {
        return arrivalCityPersian;
    }

    public String getArrivalAirportPersian() {
        return arrivalAirportPersian;
    }

    public String getDepartureCityPersian() {
        return departureCityPersian;
    }

    public String getDepartureAirportPersian() {
        return departureAirportPersian;
    }
}