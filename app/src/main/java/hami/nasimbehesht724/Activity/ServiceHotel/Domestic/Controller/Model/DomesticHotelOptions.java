package hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-02-10.
 */

public class DomesticHotelOptions implements Parcelable {
    @SerializedName("hotelOptions")
    private ArrayList<DomesticHotelMoreInfo> hotelOptions;
    @SerializedName("roomOptions")
    private ArrayList<DomesticHotelMoreInfo> roomOptions;
//    @SerializedName("extraOptions")
//    private ArrayList<DomesticHotelOptionsExtra> extraOptions;
    //-----------------------------------------------

    public DomesticHotelOptions() {
    }
    //-----------------------------------------------

    protected DomesticHotelOptions(Parcel in) {
        hotelOptions = in.createTypedArrayList(DomesticHotelMoreInfo.CREATOR);
        roomOptions = in.createTypedArrayList(DomesticHotelMoreInfo.CREATOR);
        //extraOptions = in.readParcelable(DomesticHotelOptionsExtra.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(hotelOptions);
        dest.writeTypedList(roomOptions);
        //dest.writeTypedList(extraOptions);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DomesticHotelOptions> CREATOR = new Creator<DomesticHotelOptions>() {
        @Override
        public DomesticHotelOptions createFromParcel(Parcel in) {
            return new DomesticHotelOptions(in);
        }

        @Override
        public DomesticHotelOptions[] newArray(int size) {
            return new DomesticHotelOptions[size];
        }
    };

    public ArrayList<DomesticHotelMoreInfo> getHotelOptions() {
        return hotelOptions;
    }

    public ArrayList<DomesticHotelMoreInfo> getRoomOptions() {
        return roomOptions;
    }

//    public ArrayList<DomesticHotelOptionsExtra> getExtraOptions() {
//        return extraOptions;
//    }
}

