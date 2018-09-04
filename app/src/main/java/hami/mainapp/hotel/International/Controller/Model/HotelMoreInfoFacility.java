package hami.mainapp.hotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class HotelMoreInfoFacility implements Parcelable {
    @SerializedName("Type")
    private String Type;
    @SerializedName("Records")
    private HotelMoreInfoFacilityRecords hotelMoreInfoFacilityRecords;
    //-----------------------------------------------


    public HotelMoreInfoFacility() {
    }
    //-----------------------------------------------

    protected HotelMoreInfoFacility(Parcel in) {
        Type = in.readString();
        hotelMoreInfoFacilityRecords = in.readParcelable(HotelMoreInfoFacilityRecords.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Type);
        dest.writeParcelable(hotelMoreInfoFacilityRecords, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotelMoreInfoFacility> CREATOR = new Creator<HotelMoreInfoFacility>() {
        @Override
        public HotelMoreInfoFacility createFromParcel(Parcel in) {
            return new HotelMoreInfoFacility(in);
        }

        @Override
        public HotelMoreInfoFacility[] newArray(int size) {
            return new HotelMoreInfoFacility[size];
        }
    };

    public String getType() {
        return Type;
    }

    public HotelMoreInfoFacilityRecords getHotelMoreInfoFacilityRecords() {
        return hotelMoreInfoFacilityRecords;
    }
}
