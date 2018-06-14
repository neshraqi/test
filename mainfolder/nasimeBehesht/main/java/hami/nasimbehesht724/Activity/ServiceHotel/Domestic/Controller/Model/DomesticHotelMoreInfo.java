package hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-02-10.
 */

public class DomesticHotelMoreInfo implements Parcelable {
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("value")
    private ArrayList<DomesticHotelMoreInfoValue> domesticHotelMoreInfoValue;
    @SerializedName("image")
    private String image;
    //-----------------------------------------------

    public DomesticHotelMoreInfo() {
    }
    //-----------------------------------------------

    protected DomesticHotelMoreInfo(Parcel in) {
        key = in.readString();
        name = in.readString();
        description = in.readString();
        domesticHotelMoreInfoValue = in.createTypedArrayList(DomesticHotelMoreInfoValue.CREATOR);
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeTypedList(domesticHotelMoreInfoValue);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DomesticHotelMoreInfo> CREATOR = new Creator<DomesticHotelMoreInfo>() {
        @Override
        public DomesticHotelMoreInfo createFromParcel(Parcel in) {
            return new DomesticHotelMoreInfo(in);
        }

        @Override
        public DomesticHotelMoreInfo[] newArray(int size) {
            return new DomesticHotelMoreInfo[size];
        }
    };

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<DomesticHotelMoreInfoValue> getDomesticHotelMoreInfoValue() {
        return domesticHotelMoreInfoValue;
    }

    public String getImage() {
        return image;
    }
}
