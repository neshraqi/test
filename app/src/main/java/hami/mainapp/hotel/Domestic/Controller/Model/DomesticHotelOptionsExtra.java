package hami.mainapp.hotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-02-10.
 */

public class DomesticHotelOptionsExtra implements Parcelable {
    @SerializedName("restaurant")
    private ArrayList<DomesticHotelMoreInfo> restaurant;
    @SerializedName("conferenceRoom")
    private ArrayList<DomesticHotelMoreInfo> conferenceRoom;
    @SerializedName("conferenceHall")
    private ArrayList<DomesticHotelMoreInfo> conferenceHall;
    @SerializedName("businessRoom")
    private ArrayList<DomesticHotelMoreInfo> businessRoom;
    //-----------------------------------------------


    public DomesticHotelOptionsExtra() {
    }
    //-----------------------------------------------

    protected DomesticHotelOptionsExtra(Parcel in) {
        restaurant = in.createTypedArrayList(DomesticHotelMoreInfo.CREATOR);
        conferenceRoom = in.createTypedArrayList(DomesticHotelMoreInfo.CREATOR);
        conferenceHall = in.createTypedArrayList(DomesticHotelMoreInfo.CREATOR);
        businessRoom = in.createTypedArrayList(DomesticHotelMoreInfo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(restaurant);
        dest.writeTypedList(conferenceRoom);
        dest.writeTypedList(conferenceHall);
        dest.writeTypedList(businessRoom);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DomesticHotelOptionsExtra> CREATOR = new Creator<DomesticHotelOptionsExtra>() {
        @Override
        public DomesticHotelOptionsExtra createFromParcel(Parcel in) {
            return new DomesticHotelOptionsExtra(in);
        }

        @Override
        public DomesticHotelOptionsExtra[] newArray(int size) {
            return new DomesticHotelOptionsExtra[size];
        }
    };

    public ArrayList<DomesticHotelMoreInfo> getRestaurant() {
        return restaurant;
    }

    public ArrayList<DomesticHotelMoreInfo> getConferenceRoom() {
        return conferenceRoom;
    }

    public ArrayList<DomesticHotelMoreInfo> getConferenceHall() {
        return conferenceHall;
    }

    public ArrayList<DomesticHotelMoreInfo> getBusinessRoom() {
        return businessRoom;
    }
}

