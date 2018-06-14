package hami.hamibelit.Activity.ServiceHotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import hami.hamibelit.BaseController.FailSafeNumberTypeAdapter;
import hami.hamibelit.BaseController.FailSafeStringTypeAdapter;

public class HotelMoreInfoRoomObject implements Parcelable {
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String SuggestId;
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String Board;
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String Discount;
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String DiscountType;
    @JsonAdapter(FailSafeStringTypeAdapter.class)
    private String SuggestPrice;
    @SerializedName("Rooms")
    private RoomsObject Rooms;
    @JsonAdapter(FailSafeNumberTypeAdapter.class)
    private Integer night;
    @SerializedName("searchID")
    private String searchID;
    @JsonAdapter(FailSafeNumberTypeAdapter.class)
    private Integer numberp;
    @JsonAdapter(FailSafeNumberTypeAdapter.class)
    private Integer noe;
    @JsonAdapter(FailSafeNumberTypeAdapter.class)
    private Integer price;
    @JsonAdapter(FailSafeNumberTypeAdapter.class)
    private Integer discountprice;
    @SerializedName("images")
    private ArrayList<String> images;
    //-----------------------------------------------

    public HotelMoreInfoRoomObject() {
    }
    //-----------------------------------------------

    protected HotelMoreInfoRoomObject(Parcel in) {
        SuggestId = in.readString();
        Board = in.readString();
        Discount = in.readString();
        DiscountType = in.readString();
        SuggestPrice = in.readString();
        Rooms = in.readParcelable(Rooms.class.getClassLoader());
        if (in.readByte() == 0) {
            night = null;
        } else {
            night = in.readInt();
        }
        searchID = in.readString();
        if (in.readByte() == 0) {
            numberp = null;
        } else {
            numberp = in.readInt();
        }
        if (in.readByte() == 0) {
            noe = null;
        } else {
            noe = in.readInt();
        }
        price = in.readInt();
        discountprice = in.readInt();
        images = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SuggestId);
        dest.writeString(Board);
        dest.writeString(Discount);
        dest.writeString(DiscountType);
        dest.writeString(SuggestPrice);
        dest.writeParcelable(Rooms, flags);
        if (night == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(night);
        }
        dest.writeString(searchID);
        if (numberp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numberp);
        }
        if (noe == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(noe);
        }
        dest.writeInt(price);
        dest.writeInt(discountprice);
        dest.writeStringList(images);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotelMoreInfoRoomObject> CREATOR = new Creator<HotelMoreInfoRoomObject>() {
        @Override
        public HotelMoreInfoRoomObject createFromParcel(Parcel in) {
            return new HotelMoreInfoRoomObject(in);
        }

        @Override
        public HotelMoreInfoRoomObject[] newArray(int size) {
            return new HotelMoreInfoRoomObject[size];
        }
    };

    public String getSuggestId() {
        return SuggestId;
    }

    public String getBoard() {
        return Board;
    }

    public String getDiscount() {
        return Discount;
    }

    public String getDiscountType() {
        return DiscountType;
    }

    public String getSuggestPrice() {
        return SuggestPrice;
    }

    public RoomsObject getRooms() {
        return Rooms;
    }

    public Integer getNight() {
        return night;
    }

    public String getSearchID() {
        return searchID;
    }

    public Integer getNumberp() {
        return numberp;
    }

    public Integer getNoe() {
        return noe;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getDiscountprice() {
        return discountprice;
    }

    public ArrayList<String> getImages() {
        return images;
    }
}
