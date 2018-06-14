package hami.nasimbehesht724.Activity.ServiceHotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HotelMoreInfoFacilities implements Parcelable {
    @SerializedName("Facility")
    private ArrayList<HotelMoreInfoFacility> hotelMoreInfoFacilities;
    //-----------------------------------------------

    public HotelMoreInfoFacilities() {
    }
    //-----------------------------------------------

    protected HotelMoreInfoFacilities(Parcel in) {
        hotelMoreInfoFacilities = in.createTypedArrayList(HotelMoreInfoFacility.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(hotelMoreInfoFacilities);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotelMoreInfoFacilities> CREATOR = new Creator<HotelMoreInfoFacilities>() {
        @Override
        public HotelMoreInfoFacilities createFromParcel(Parcel in) {
            return new HotelMoreInfoFacilities(in);
        }

        @Override
        public HotelMoreInfoFacilities[] newArray(int size) {
            return new HotelMoreInfoFacilities[size];
        }
    };

    public ArrayList<HotelMoreInfoFacility> getHotelMoreInfoFacilities() {
        return hotelMoreInfoFacilities;
    }

}
