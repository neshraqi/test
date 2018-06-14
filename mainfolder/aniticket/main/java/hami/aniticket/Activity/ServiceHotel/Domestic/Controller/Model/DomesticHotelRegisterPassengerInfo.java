package hami.hamibelit.Activity.ServiceHotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-02-15.
 */

public class DomesticHotelRegisterPassengerInfo implements Parcelable {
    @SerializedName("name")
    private ArrayList<String> name;
    @SerializedName("family")
    private ArrayList<String> family;
    @SerializedName("email")
    private ArrayList<String> email;
    @SerializedName("mobile")
    private ArrayList<String> mobile;
    @SerializedName("phone")
    private ArrayList<String> phone;
    @SerializedName("price")
    private ArrayList<String> price;
    //-----------------------------------------------

    public DomesticHotelRegisterPassengerInfo() {
    }
    //-----------------------------------------------

    protected DomesticHotelRegisterPassengerInfo(Parcel in) {
        name = in.createStringArrayList();
        family = in.createStringArrayList();
        email = in.createStringArrayList();
        mobile = in.createStringArrayList();
        phone = in.createStringArrayList();
        price = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(name);
        dest.writeStringList(family);
        dest.writeStringList(email);
        dest.writeStringList(mobile);
        dest.writeStringList(phone);
        dest.writeStringList(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DomesticHotelRegisterPassengerInfo> CREATOR = new Creator<DomesticHotelRegisterPassengerInfo>() {
        @Override
        public DomesticHotelRegisterPassengerInfo createFromParcel(Parcel in) {
            return new DomesticHotelRegisterPassengerInfo(in);
        }

        @Override
        public DomesticHotelRegisterPassengerInfo[] newArray(int size) {
            return new DomesticHotelRegisterPassengerInfo[size];
        }
    };

    public ArrayList<String> getName() {
        return name;
    }

    public ArrayList<String> getFamily() {
        return family;
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    public ArrayList<String> getMobile() {
        return mobile;
    }

    public ArrayList<String> getPhone() {
        return phone;
    }

    public ArrayList<String> getPrice() {
        return price;
    }
}
