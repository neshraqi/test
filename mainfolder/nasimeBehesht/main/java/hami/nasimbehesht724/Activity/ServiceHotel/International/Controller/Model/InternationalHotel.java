package hami.nasimbehesht724.Activity.ServiceHotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import hami.nasimbehesht724.BaseController.FailSafeNumberTypeAdapter;
import hami.nasimbehesht724.BaseController.FailSafeStringTypeAdapter;


public class InternationalHotel implements Parcelable {

    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String HotelId;
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String HotelName;
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String Lat;
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String Long;
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String Address;
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String Location;
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String HotelStar;
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String HotelDescription;
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String HotelImage;
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String searchID;
    @JsonAdapter(FailSafeNumberTypeAdapter.class)
    private int night;
    @JsonAdapter(FailSafeNumberTypeAdapter.class)
    private int numberp;
    @JsonAdapter(FailSafeNumberTypeAdapter.class)
    private int noe;
    //    @JsonAdapter(FailSafeStringTypeAdapter.class)
    @SerializedName("reserveId")
    private String reserveId;
    @JsonAdapter(FailSafeNumberTypeAdapter.class)
    private int discountprice;
    @JsonAdapter(FailSafeNumberTypeAdapter.class)
    private int price;
    //-----------------------------------------------

    public InternationalHotel() {
    }
    //-----------------------------------------------

    protected InternationalHotel(Parcel in) {
        HotelId = in.readString();
        HotelName = in.readString();
        Lat = in.readString();
        Long = in.readString();
        Address = in.readString();
        Location = in.readString();
        HotelStar = in.readString();
        HotelDescription = in.readString();
        HotelImage = in.readString();
        searchID = in.readString();
        night = in.readInt();
        numberp = in.readInt();
        noe = in.readInt();
        reserveId = in.readString();
        discountprice = in.readInt();
        price = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(HotelId);
        dest.writeString(HotelName);
        dest.writeString(Lat);
        dest.writeString(Long);
        dest.writeString(Address);
        dest.writeString(Location);
        dest.writeString(HotelStar);
        dest.writeString(HotelDescription);
        dest.writeString(HotelImage);
        dest.writeString(searchID);
        dest.writeInt(night);
        dest.writeInt(numberp);
        dest.writeInt(noe);
        dest.writeString(reserveId);
        dest.writeInt(discountprice);
        dest.writeInt(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InternationalHotel> CREATOR = new Creator<InternationalHotel>() {
        @Override
        public InternationalHotel createFromParcel(Parcel in) {
            return new InternationalHotel(in);
        }

        @Override
        public InternationalHotel[] newArray(int size) {
            return new InternationalHotel[size];
        }
    };

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

    public String getLocation() {
        return Location;
    }

    public String getHotelStar() {
        return HotelStar;
    }

    public String getHotelDescription() {
        return HotelDescription;
    }

    public String getHotelImage() {
        return HotelImage;
    }

    public String getSearchID() {
        return searchID;
    }

    public int getNight() {
        return night;
    }

    public int getNumberp() {
        return numberp;
    }

    public int getNoe() {
        return noe;
    }

    public String getReserveId() {
        return reserveId;
    }

    public int getDiscountprice() {
        return discountprice;
    }

    public int getPrice() {
        return price;
    }
    //-----------------------------------------------

}

