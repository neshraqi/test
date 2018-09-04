package hami.mainapp.hotel.Domestic.Controller.Model;

import com.google.gson.annotations.SerializedName;
import com.hami.common.BaseController.ToStringClass;

import java.io.Serializable;


/**
 * Created by renjer on 2018-02-14.
 */

public class DomesticHotelPreBookingRequest extends ToStringClass implements Serializable {

    private String city;

    private String cityName;

    private String id;

    private String hotel;

    private String fromDate;

    private String inDate;

    private String numberOfNights;
    @SerializedName("roomId")
    private String roomId;
    @SerializedName("search_id")
    private String searchId;
    //-----------------------------------------------

    public DomesticHotelPreBookingRequest() {
    }
    //-----------------------------------------------

    public DomesticHotelPreBookingRequest(String city, String cityName, String id, String hotel, String fromDate, String inDate, String numberOfNights, String roomId) {
        this.city = city;
        this.cityName = cityName;
        this.id = id;
        this.hotel = hotel;
        this.fromDate = fromDate;
        setInDate(inDate);
        this.numberOfNights = numberOfNights;
        this.roomId = roomId;
    }

    //-----------------------------------------------
    public DomesticHotelPreBookingRequest(String city, String cityName, String id, String hotel, String fromDate, String inDate, String numberOfNights, String roomId, String searchId) {
        this.city = city;
        this.cityName = cityName;
        this.id = id;
        this.hotel = hotel;
        this.fromDate = fromDate;
        setInDate(inDate);
        this.numberOfNights = numberOfNights;
        this.roomId = roomId;
        this.searchId = searchId;
    }
    //-----------------------------------------------

    public String getCity() {
        return city;
    }

    public String getCityName() {
        return cityName;
    }

    public String getId() {
        return id;
    }

    public String getHotel() {
        return hotel;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getInDate() {
        return inDate;
    }

    public String getNumberOfNights() {
        return numberOfNights;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getSearchId() {
        return searchId;
    }
//-----------------------------------------------

    public void setCity(String city) {
        this.city = city;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public void setNumberOfNights(String numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
}

