package hami.mainapp.Activity.ServiceHotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-01-20.
 */

public class HotelContactInfo implements Parcelable {
    @SerializedName("tech")
    private String tech;
    @SerializedName("value")
    private String value;
    //-----------------------------------------------

    public HotelContactInfo() {
    }
    //-----------------------------------------------

    protected HotelContactInfo(Parcel in) {
        tech = in.readString();
        value = in.readString();
    }

    public static final Creator<HotelContactInfo> CREATOR = new Creator<HotelContactInfo>() {
        @Override
        public HotelContactInfo createFromParcel(Parcel in) {
            return new HotelContactInfo(in);
        }

        @Override
        public HotelContactInfo[] newArray(int size) {
            return new HotelContactInfo[size];
        }
    };

    public String getTech() {
        return tech;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tech);
        dest.writeString(value);
    }
}
