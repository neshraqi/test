package com.hami.servicehotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HotelMoreInfoFacilityRecords implements Parcelable {
    @SerializedName("Record")
    private ArrayList<String> recordList;
    //-----------------------------------------------


    public HotelMoreInfoFacilityRecords() {
    }
    //-----------------------------------------------

    protected HotelMoreInfoFacilityRecords(Parcel in) {
        recordList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(recordList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotelMoreInfoFacilityRecords> CREATOR = new Creator<HotelMoreInfoFacilityRecords>() {
        @Override
        public HotelMoreInfoFacilityRecords createFromParcel(Parcel in) {
            return new HotelMoreInfoFacilityRecords(in);
        }

        @Override
        public HotelMoreInfoFacilityRecords[] newArray(int size) {
            return new HotelMoreInfoFacilityRecords[size];
        }
    };

    public ArrayList<String> getRecordList() {
        return recordList;
    }
}
