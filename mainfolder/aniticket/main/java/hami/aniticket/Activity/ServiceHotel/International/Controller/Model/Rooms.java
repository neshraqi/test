package hami.hamibelit.Activity.ServiceHotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Rooms implements Parcelable {

    @SerializedName("Room")
    private ArrayList<RoomInfo> roomInfoList;

    //-----------------------------------------------
    public Rooms() {
    }
    //-----------------------------------------------


    protected Rooms(Parcel in) {
        roomInfoList = in.createTypedArrayList(RoomInfo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(roomInfoList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //-----------------------------------------------
    public static final Creator<Rooms> CREATOR = new Creator<Rooms>() {
        @Override
        public Rooms createFromParcel(Parcel in) {
            return new Rooms(in);
        }

        @Override
        public Rooms[] newArray(int size) {
            return new Rooms[size];
        }
    };

    //-----------------------------------------------
    public ArrayList<RoomInfo> getRoomInfoList() {
        return roomInfoList;
    }


}
