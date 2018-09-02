package com.hami.servicehotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class HotelsMoreInfoObject implements Parcelable {
    @SerializedName("Description")
    private String Description;
    @SerializedName("Images")
    private HotelsMoreInfoImages hotelsMoreInfoImages;
    @SerializedName("Facilities")
    private HotelMoreInfoFacilities hotelMoreInfoFacilities;
    //-----------------------------------------------


    public HotelsMoreInfoObject() {
    }
    //-----------------------------------------------

    protected HotelsMoreInfoObject(Parcel in) {
        Description = in.readString();
        hotelsMoreInfoImages = in.readParcelable(HotelsMoreInfoImages.class.getClassLoader());
        hotelMoreInfoFacilities = in.readParcelable(HotelMoreInfoFacilities.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Description);
        dest.writeParcelable(hotelsMoreInfoImages, flags);
        dest.writeParcelable(hotelMoreInfoFacilities, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotelsMoreInfoObject> CREATOR = new Creator<HotelsMoreInfoObject>() {
        @Override
        public HotelsMoreInfoObject createFromParcel(Parcel in) {
            return new HotelsMoreInfoObject(in);
        }

        @Override
        public HotelsMoreInfoObject[] newArray(int size) {
            return new HotelsMoreInfoObject[size];
        }
    };

    public String getDescription() {
        return Description;
    }

    public HotelsMoreInfoImages getHotelsMoreInfoImages() {
        return hotelsMoreInfoImages;
    }

    public HotelMoreInfoFacilities getHotelMoreInfoFacilities() {
        return hotelMoreInfoFacilities;
    }
}
