package hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-02-10.
 */

public class DomesticHotelMoreInfoValue implements Parcelable {
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("value")
    private String value;
    //-----------------------------------------------

    public DomesticHotelMoreInfoValue() {
    }

    //-----------------------------------------------

    protected DomesticHotelMoreInfoValue(Parcel in) {
        key = in.readString();
        name = in.readString();
        value = in.readString();
    }

    public static final Creator<DomesticHotelMoreInfoValue> CREATOR = new Creator<DomesticHotelMoreInfoValue>() {
        @Override
        public DomesticHotelMoreInfoValue createFromParcel(Parcel in) {
            return new DomesticHotelMoreInfoValue(in);
        }

        @Override
        public DomesticHotelMoreInfoValue[] newArray(int size) {
            return new DomesticHotelMoreInfoValue[size];
        }
    };

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
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
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(value);
    }
}
