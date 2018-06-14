package hami.hamibelit.Activity.ServiceHotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-01-17.
 */

public class HotelInfo implements Parcelable{
    @SerializedName("SearchID")
    private String SearchID;
    @SerializedName("CityCode")
    private String CityCode;
    @SerializedName("HotelId")
    private String HotelId;
    @SerializedName("HotelName")
    private String HotelName;
    @SerializedName("Lat")
    private String Lat;
    @SerializedName("Long")
    private String Long;
    @SerializedName("Address")
    private String Address;
    @SerializedName("Phone")
    private String Phone;
    @SerializedName("Location")
    private String Location;
    @SerializedName("Star")
    private String Star;
    //-----------------------------------------------


    public HotelInfo() {
    }
    //-----------------------------------------------


    protected HotelInfo(Parcel in) {
        SearchID = in.readString();
        CityCode = in.readString();
        HotelId = in.readString();
        HotelName = in.readString();
        Lat = in.readString();
        Long = in.readString();
        Address = in.readString();
        Phone = in.readString();
        Location = in.readString();
        Star = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SearchID);
        dest.writeString(CityCode);
        dest.writeString(HotelId);
        dest.writeString(HotelName);
        dest.writeString(Lat);
        dest.writeString(Long);
        dest.writeString(Address);
        dest.writeString(Phone);
        dest.writeString(Location);
        dest.writeString(Star);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotelInfo> CREATOR = new Creator<HotelInfo>() {
        @Override
        public HotelInfo createFromParcel(Parcel in) {
            return new HotelInfo(in);
        }

        @Override
        public HotelInfo[] newArray(int size) {
            return new HotelInfo[size];
        }
    };

    public String getSearchID() {
        return SearchID;
    }

    public String getCityCode() {
        return CityCode;
    }

    public String getHotelId() {
        return HotelId;
    }

    public String getHotelName() {
        return HotelName;
    }

    public String getLat() {
        return Lat;
    }

    public String getLong() {
        return Long;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhone() {
        return Phone;
    }

    public String getLocation() {
        return Location;
    }

    public String getStar() {
        return Star;
    }
}
