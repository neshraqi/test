package hami.mainapp.hotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HotelsMoreInfo implements Parcelable {

    @SerializedName("MoreInfo")
    private HotelsMoreInfoObject hotelsMoreInfoData;
    @SerializedName("HotelInfo")
    private HotelInfo hotelInfo;

    private ArrayList<HotelMoreInfoRoom> roomsInfo;
    private ArrayList<HotelMoreInfoRoomObject> roomsInfoRoomObjectsList;

    //-----------------------------------------------


    public HotelsMoreInfo() {
    }
    //-----------------------------------------------


    protected HotelsMoreInfo(Parcel in) {
        hotelsMoreInfoData = in.readParcelable(HotelsMoreInfoObject.class.getClassLoader());
        hotelInfo = in.readParcelable(HotelInfo.class.getClassLoader());
        roomsInfo = in.createTypedArrayList(HotelMoreInfoRoom.CREATOR);
        roomsInfoRoomObjectsList = in.createTypedArrayList(HotelMoreInfoRoomObject.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(hotelsMoreInfoData, flags);
        dest.writeParcelable(hotelInfo, flags);
        dest.writeTypedList(roomsInfo);
        dest.writeTypedList(roomsInfoRoomObjectsList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotelsMoreInfo> CREATOR = new Creator<HotelsMoreInfo>() {
        @Override
        public HotelsMoreInfo createFromParcel(Parcel in) {
            return new HotelsMoreInfo(in);
        }

        @Override
        public HotelsMoreInfo[] newArray(int size) {
            return new HotelsMoreInfo[size];
        }
    };

    public HotelsMoreInfoObject getHotelsMoreInfoData() {
        return hotelsMoreInfoData;
    }

    public HotelInfo getHotelInfo() {
        return hotelInfo;
    }

    public ArrayList<HotelMoreInfoRoom> getRoomsInfo() {
        return roomsInfo;
    }

    public ArrayList<HotelMoreInfoRoomObject> getRoomsInfoRoomObjectsList() {
        return roomsInfoRoomObjectsList;
    }

    public void setRoomsInfo(ArrayList<HotelMoreInfoRoom> roomsInfo) {
        this.roomsInfo = roomsInfo;
    }

    public void setRoomsInfoRoomObjectsList(ArrayList<HotelMoreInfoRoomObject> roomsInfoRoomObjectsList) {
        this.roomsInfoRoomObjectsList = roomsInfoRoomObjectsList;
    }
}
