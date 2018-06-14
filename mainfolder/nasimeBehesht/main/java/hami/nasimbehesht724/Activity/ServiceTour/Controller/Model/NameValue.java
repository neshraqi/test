package hami.nasimbehesht724.Activity.ServiceTour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-12-21.
 */

public class NameValue implements Parcelable {
    @SerializedName("name")
    private String name;
    @SerializedName("value")
    private String value;
    //-----------------------------------------------

    public NameValue() {
    }
    //-----------------------------------------------

    protected NameValue(Parcel in) {
        name = in.readString();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NameValue> CREATOR = new Creator<NameValue>() {
        @Override
        public NameValue createFromParcel(Parcel in) {
            return new NameValue(in);
        }

        @Override
        public NameValue[] newArray(int size) {
            return new NameValue[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
