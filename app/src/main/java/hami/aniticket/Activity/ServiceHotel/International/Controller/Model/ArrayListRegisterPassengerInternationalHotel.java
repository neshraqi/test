package hami.aniticket.Activity.ServiceHotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-02-03.
 */

public class ArrayListRegisterPassengerInternationalHotel implements Parcelable {

    @SerializedName("name")
    private ArrayList<String> nameList;

    @SerializedName("family")
    private ArrayList<String> familyList;

    @SerializedName("room")
    private ArrayList<String> roomList;

    @SerializedName("typep")
    private ArrayList<String> typePassengerList;

    //-----------------------------------------------

    public ArrayListRegisterPassengerInternationalHotel() {
    }

    //-----------------------------------------------

    protected ArrayListRegisterPassengerInternationalHotel(Parcel in) {
        nameList = in.createStringArrayList();
        familyList = in.createStringArrayList();
        roomList = in.createStringArrayList();
        typePassengerList = in.createStringArrayList();
    }

    public static final Creator<ArrayListRegisterPassengerInternationalHotel> CREATOR = new Creator<ArrayListRegisterPassengerInternationalHotel>() {
        @Override
        public ArrayListRegisterPassengerInternationalHotel createFromParcel(Parcel in) {
            return new ArrayListRegisterPassengerInternationalHotel(in);
        }

        @Override
        public ArrayListRegisterPassengerInternationalHotel[] newArray(int size) {
            return new ArrayListRegisterPassengerInternationalHotel[size];
        }
    };

    public ArrayList<String> getNameList() {
        return nameList;
    }

    public ArrayList<String> getFamilyList() {
        return familyList;
    }

    public ArrayList<String> getRoomList() {
        return roomList;
    }

    public ArrayList<String> getTypePassengerList() {
        return typePassengerList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(nameList);
        dest.writeStringList(familyList);
        dest.writeStringList(roomList);
        dest.writeStringList(typePassengerList);
    }
}
