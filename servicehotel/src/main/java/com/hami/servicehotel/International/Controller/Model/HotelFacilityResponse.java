package com.hami.servicehotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-01-27.
 */

public class HotelFacilityResponse implements Parcelable {
    @SerializedName("Location")
    private String[] location;

    @SerializedName("Methods of payment")
    private String[] methodsOfPayment;

    @SerializedName("Facilities")
    private String[] facilities;

    @SerializedName("Distances (in meters)")
    private String[] distanceSinmeters;

    @SerializedName("Entertainment")
    private String[] entertainment;

    @SerializedName("RoomInfo facilities (Standard room)")
    private String[] roomFacilitiesStandardRoom;

    @SerializedName("Catering")
    private String[] catering;

    @SerializedName("Meals")
    private String[] meals;

    @SerializedName("Sports")
    private String[] sports;

    @SerializedName("InternationalHotel type")
    private String[] HotelType;
    //-----------------------------------------------

    public HotelFacilityResponse() {
    }
    //-----------------------------------------------

    protected HotelFacilityResponse(Parcel in) {
        location = in.createStringArray();
        methodsOfPayment = in.createStringArray();
        facilities = in.createStringArray();
        distanceSinmeters = in.createStringArray();
        entertainment = in.createStringArray();
        roomFacilitiesStandardRoom = in.createStringArray();
        catering = in.createStringArray();
        meals = in.createStringArray();
        sports = in.createStringArray();
        HotelType = in.createStringArray();
    }

    public static final Creator<HotelFacilityResponse> CREATOR = new Creator<HotelFacilityResponse>() {
        @Override
        public HotelFacilityResponse createFromParcel(Parcel in) {
            return new HotelFacilityResponse(in);
        }

        @Override
        public HotelFacilityResponse[] newArray(int size) {
            return new HotelFacilityResponse[size];
        }
    };

    public String[] getLocation() {
        return location;
    }

    public String[] getMethodsOfPayment() {
        return methodsOfPayment;
    }

    public String[] getFacilities() {
        return facilities;
    }

    public String[] getDistanceSinmeters() {
        return distanceSinmeters;
    }

    public String[] getEntertainment() {
        return entertainment;
    }

    public String[] getRoomFacilitiesStandardRoom() {
        return roomFacilitiesStandardRoom;
    }

    public String[] getCatering() {
        return catering;
    }

    public String[] getMeals() {
        return meals;
    }

    public String[] getSports() {
        return sports;
    }

    public String[] getHotelType() {
        return HotelType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(location);
        dest.writeStringArray(methodsOfPayment);
        dest.writeStringArray(facilities);
        dest.writeStringArray(distanceSinmeters);
        dest.writeStringArray(entertainment);
        dest.writeStringArray(roomFacilitiesStandardRoom);
        dest.writeStringArray(catering);
        dest.writeStringArray(meals);
        dest.writeStringArray(sports);
        dest.writeStringArray(HotelType);
    }
}
