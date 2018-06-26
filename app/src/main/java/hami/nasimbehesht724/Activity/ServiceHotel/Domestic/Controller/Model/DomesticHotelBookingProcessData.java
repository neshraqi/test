package hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-02-10.
 */

public class DomesticHotelBookingProcessData implements Parcelable {

    @SerializedName("hotelId")
    private int hotelId;
    @SerializedName("hotelName")
    private String hotelName;
    @SerializedName("hotelNameFa")
    private String hotelNameFa;
    @SerializedName("hotelDescription")
    private String hotelDescription;
    @SerializedName("hotelAddress")
    private String hotelAddress;
    @SerializedName("hotelCategory")
    private String hotelCategory;
    @SerializedName("hotelStar")
    private int hotelStar;
    @SerializedName("hotelClass")
    private int hotelClass;
    @SerializedName("hotelLatitude")
    private double hotelLatitude;
    @SerializedName("hotelLongitude")
    private double hotelLongitude;
    @SerializedName("hotelDiscount")
    private float hotelDiscount;
    @SerializedName("onlineReservation")
    private String onlineReservation;
    @SerializedName("numberOfNights")
    private int numberOfNights;
    @SerializedName("hotelRooms")
    private int hotelRooms;
    @SerializedName("hotelFloor")
    private int hotelFloor;
    @SerializedName("hotelBeds")
    private int hotelBeds;
    @SerializedName("hotelTraffic")
    private String hotelTraffic;
    @SerializedName("hotelLaby")
    private String hotelLaby;
    @SerializedName("hotelOpenning")
    private String hotelOpenning;
    @SerializedName("hotelRepair")
    private String hotelRepair;
    @SerializedName("hotelView")
    private String hotelView;
    @SerializedName("moreInfo")
    private ArrayList<DomesticHotelMoreInfo> moreInfo;
    @SerializedName("images")
    private ArrayList<String> images;
    @SerializedName("rooms")
    private ArrayList<DomesticHotelRoom> rooms;
    @SerializedName("options")
    private DomesticHotelOptions options;
    @SerializedName("rules")
    private ArrayList<DomesticHotelMoreInfo> rules;
    @SerializedName("roomOptions")
    private ArrayList<DomesticHotelRoomOption> roomOptions;
    //-----------------------------------------------


    public DomesticHotelBookingProcessData() {
    }

    //-----------------------------------------------

    protected DomesticHotelBookingProcessData(Parcel in) {
        hotelId = in.readInt();
        hotelName = in.readString();
        hotelNameFa = in.readString();
        hotelDescription = in.readString();
        hotelAddress = in.readString();
        hotelCategory = in.readString();
        hotelStar = in.readInt();
        hotelClass = in.readInt();
        hotelLatitude = in.readDouble();
        hotelLongitude = in.readDouble();
        hotelDiscount = in.readFloat();
        onlineReservation = in.readString();
        numberOfNights = in.readInt();
        hotelRooms = in.readInt();
        hotelFloor = in.readInt();
        hotelBeds = in.readInt();
        hotelTraffic = in.readString();
        hotelLaby = in.readString();
        hotelOpenning = in.readString();
        hotelRepair = in.readString();
        hotelView = in.readString();
        moreInfo = in.createTypedArrayList(DomesticHotelMoreInfo.CREATOR);
        images = in.createStringArrayList();
        rooms = in.createTypedArrayList(DomesticHotelRoom.CREATOR);
        options = in.readParcelable(DomesticHotelOptions.class.getClassLoader());
        rules = in.createTypedArrayList(DomesticHotelMoreInfo.CREATOR);
        roomOptions = in.createTypedArrayList(DomesticHotelRoomOption.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hotelId);
        dest.writeString(hotelName);
        dest.writeString(hotelNameFa);
        dest.writeString(hotelDescription);
        dest.writeString(hotelAddress);
        dest.writeString(hotelCategory);
        dest.writeInt(hotelStar);
        dest.writeInt(hotelClass);
        dest.writeDouble(hotelLatitude);
        dest.writeDouble(hotelLongitude);
        dest.writeFloat(hotelDiscount);
        dest.writeString(onlineReservation);
        dest.writeInt(numberOfNights);
        dest.writeInt(hotelRooms);
        dest.writeInt(hotelFloor);
        dest.writeInt(hotelBeds);
        dest.writeString(hotelTraffic);
        dest.writeString(hotelLaby);
        dest.writeString(hotelOpenning);
        dest.writeString(hotelRepair);
        dest.writeString(hotelView);
        dest.writeTypedList(moreInfo);
        dest.writeStringList(images);
        dest.writeTypedList(rooms);
        dest.writeParcelable(options, flags);
        dest.writeTypedList(rules);
        dest.writeTypedList(roomOptions);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DomesticHotelBookingProcessData> CREATOR = new Creator<DomesticHotelBookingProcessData>() {
        @Override
        public DomesticHotelBookingProcessData createFromParcel(Parcel in) {
            return new DomesticHotelBookingProcessData(in);
        }

        @Override
        public DomesticHotelBookingProcessData[] newArray(int size) {
            return new DomesticHotelBookingProcessData[size];
        }
    };

    public int getHotelId() {
        return hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelNameFa() {
        return hotelNameFa;
    }

    public String getHotelDescription() {
        return hotelDescription;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public String getHotelCategory() {

        return (hotelCategory == null || hotelCategory.length() == 0) ? "" : hotelCategory;
    }

    public int getHotelStar() {
        return hotelStar;
    }

    public int getHotelClass() {
        return hotelClass;
    }

    public double getHotelLatitude() {
        return hotelLatitude;
    }

    public double getHotelLongitude() {
        return hotelLongitude;
    }

    public float getHotelDiscount() {
        return hotelDiscount;
    }

    public String getOnlineReservation() {
        return onlineReservation;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public int getHotelRooms() {
        return hotelRooms;
    }

    public int getHotelFloor() {
        return hotelFloor;
    }

    public int getHotelBeds() {
        return hotelBeds;
    }

    public String getHotelTraffic() {
        return hotelTraffic;
    }

    public String getHotelLaby() {
        return hotelLaby;
    }

    public String getHotelOpenning() {
        return hotelOpenning;
    }

    public String getHotelRepair() {
        return hotelRepair;
    }

    public String getHotelView() {
        return hotelView;
    }

    public ArrayList<DomesticHotelMoreInfo> getMoreInfo() {
        return moreInfo;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public ArrayList<DomesticHotelRoom> getRooms() {
        return rooms;
    }

    public DomesticHotelOptions getOptions() {
        return options;
    }

    public ArrayList<DomesticHotelMoreInfo> getRules() {
        return rules;
    }

    public ArrayList<DomesticHotelRoomOption> getRoomOptions() {
        return roomOptions;
    }
}
