package hami.aniticket.Activity.ServiceHotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RoomsObject implements Parcelable {


    @SerializedName("Room")
    private RoomInfo roomInfo;

    //-----------------------------------------------
    public RoomsObject() {
    }
    //-----------------------------------------------


    protected RoomsObject(Parcel in) {
        roomInfo = in.readParcelable(RoomInfo.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(roomInfo, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RoomsObject> CREATOR = new Creator<RoomsObject>() {
        @Override
        public RoomsObject createFromParcel(Parcel in) {
            return new RoomsObject(in);
        }

        @Override
        public RoomsObject[] newArray(int size) {
            return new RoomsObject[size];
        }
    };

    public RoomInfo getRoomInfo() {
        return roomInfo;
    }

}
