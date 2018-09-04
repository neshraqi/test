package hami.mainapp.hotel.Domestic.Controller.Model;

import java.io.Serializable;

/**
 * Created by renjer on 2018-02-07.
 */

public class DomesticHotelSearchRequest implements Serializable {
    private String cityNameEng;
    private String cityNamePersian;
    private String checkInPersianShort;
    private String checkOutPersianShort;
    private String checkInPersian;
    private String checkOutPersian;
    private String checkIn;
    private String checkOut;
    private String hotelId;
    private String hotelName;
    private String apiType;
    private String checkInPersianShortYear;
    //-----------------------------------------------

    public DomesticHotelSearchRequest() {
    }

    //-----------------------------------------------

    public String getCityNameEng() {
        return cityNameEng;
    }

    public String getCityNamePersian() {
        return cityNamePersian;
    }

    public String getCheckInPersianShort() {
        return checkInPersianShort;
    }

    public String getCheckOutPersianShort() {
        return checkOutPersianShort;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getCheckInPersianShortYear() {
        return checkInPersianShortYear;
    }
    //-----------------------------------------------

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public void setCityNameEng(String cityNameEng) {
        this.cityNameEng = cityNameEng;
    }

    public void setCityNamePersian(String cityNamePersian) {
        this.cityNamePersian = cityNamePersian;
    }

    public void setCheckInPersianShort(String checkInPersianShort) {
        this.checkInPersianShort = checkInPersianShort;
    }

    public void setCheckOutPersianShort(String checkOutPersianShort) {
        this.checkOutPersianShort = checkOutPersianShort;
    }

    public void setCheckInPersian(String checkInPersian) {
        this.checkInPersian = checkInPersian;
    }

    public void setCheckOutPersian(String checkOutPersian) {
        this.checkOutPersian = checkOutPersian;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getCheckInPersian() {
        return checkInPersian;
    }

    public String getCheckOutPersian() {
        return checkOutPersian;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public void setCheckInPersianShortYear(String checkInPersianShortYear) {
        this.checkInPersianShortYear = checkInPersianShortYear;
    }
}
