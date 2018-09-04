package hami.mainapp.hotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-02-10.
 */

public class DomesticHotelRoomPrice implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("nameFa")
    private String nameFa;
    @SerializedName("fullBoard")
    private String fullBoard;
    @SerializedName("beds")
    private String beds;
    @SerializedName("onlineReservation")
    private String onlineReservation;
    @SerializedName("reservationStatus")
    private String reservationStatus;
    @SerializedName("discount")
    private DomesticHotelRoomDiscount discount;
    @SerializedName("totalPrice")
    private DomesticHotelRoomDiscount totalPrice;
    @SerializedName("priceList")
    private Object priceList;
    //-----------------------------------------------

    public DomesticHotelRoomPrice() {
    }
    //-----------------------------------------------


    protected DomesticHotelRoomPrice(Parcel in) {
        id = in.readString();
        nameFa = in.readString();
        fullBoard = in.readString();
        beds = in.readString();
        onlineReservation = in.readString();
        reservationStatus = in.readString();
        discount = in.readParcelable(DomesticHotelRoomDiscount.class.getClassLoader());
        totalPrice = in.readParcelable(DomesticHotelRoomDiscount.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nameFa);
        dest.writeString(fullBoard);
        dest.writeString(beds);
        dest.writeString(onlineReservation);
        dest.writeString(reservationStatus);
        dest.writeParcelable(discount, flags);
        dest.writeParcelable(totalPrice, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DomesticHotelRoomPrice> CREATOR = new Creator<DomesticHotelRoomPrice>() {
        @Override
        public DomesticHotelRoomPrice createFromParcel(Parcel in) {
            return new DomesticHotelRoomPrice(in);
        }

        @Override
        public DomesticHotelRoomPrice[] newArray(int size) {
            return new DomesticHotelRoomPrice[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getNameFa() {
        return nameFa;
    }

    public String getFullBoard() {
        return fullBoard;
    }

    public String getBeds() {
        return beds;
    }

    public String getOnlineReservation() {
        return onlineReservation;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public DomesticHotelRoomDiscount getDiscount() {
        return discount;
    }

    public DomesticHotelRoomDiscount getTotalPrice() {
        return totalPrice;
    }

    public Object getPriceList() {
        return priceList;
    }
}
