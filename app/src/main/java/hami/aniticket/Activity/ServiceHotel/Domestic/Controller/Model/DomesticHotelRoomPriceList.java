package hami.aniticket.Activity.ServiceHotel.Domestic.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-02-10.
 */

public class DomesticHotelRoomPriceList {
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

    public DomesticHotelRoomPriceList() {
    }
    //-----------------------------------------------

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
