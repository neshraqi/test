package hami.hamibelit.Activity.ServiceHotel.Domestic.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-02-07.
 */

public class DomesticHotel {
    @SerializedName("hotelId")
    private String hotelId;
    @SerializedName("hotelName")
    private String hotelName;
    @SerializedName("hotelNameFa")
    private String hotelNameFa;
    @SerializedName("hotelAddress")
    private String hotelAddress;
    @SerializedName("hotelCategory")
    private String hotelCategory;
    @SerializedName("hotelStar")
    private String hotelStar;
    @SerializedName("hotelClass")
    private String hotelClass;
    @SerializedName("hotelLatitude")
    private String hotelLatitude;
    @SerializedName("hotelLongitude")
    private String hotelLongitude;
    @SerializedName("hotelDiscount")
    private String hotelDiscount;
    @SerializedName("HotelApiType")
    private int HotelApiType;
    @SerializedName("hotelImage")
    private String hotelImage;
    //-----------------------------------------------

    public DomesticHotel() {
    }

    //-----------------------------------------------

    public String getHotelId() {
        return hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getHotelNameFa() {
        return hotelNameFa;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public String getHotelCategory() {
        return hotelCategory;
    }

    public String getHotelStar() {
        return hotelStar;
    }

    public String getHotelClass() {
        return hotelClass;
    }

    public String getHotelLatitude() {
        return hotelLatitude;
    }

    public String getHotelLongitude() {
        return hotelLongitude;
    }

    public String getHotelDiscount() {
        return hotelDiscount;
    }

    public int getHotelApiType() {
        return HotelApiType;
    }

    public String getHotelImage() {
        return hotelImage;
    }

    public void setHotelApiType(int hotelApiType) {
        HotelApiType = hotelApiType;
    }
}
