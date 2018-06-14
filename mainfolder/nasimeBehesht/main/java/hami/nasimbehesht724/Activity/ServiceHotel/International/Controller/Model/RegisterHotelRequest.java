package hami.nasimbehesht724.Activity.ServiceHotel.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import hami.nasimbehesht724.BaseController.ToStringClass;

/**
 * Created by renjer on 2018-02-03.
 */

public class RegisterHotelRequest extends ToStringClass {
    @SerializedName("passengers")
    private ArrayList<PassengerInfoInternationalHotel> passengers;
    @SerializedName("reserveId")
    private String reserveId;
    @SerializedName("sessionId")
    private String sessionId;
    @SerializedName("searchId")
    private String searchId;
    @SerializedName("suggestId")
    private String suggestId;
    @SerializedName("email")
    private String email;
    @SerializedName("roomId")
    private ArrayList<String> roomId;
    @SerializedName("user_cellphone")
    private String user_cellphone;
    @SerializedName("captcha")
    private String captcha;
    //-----------------------------------------------

    public RegisterHotelRequest(ArrayList<PassengerInfoInternationalHotel> passengers, String reserveId, String searchId, String email, String user_cellphone) {
        this.passengers = passengers;
        this.reserveId = reserveId;
        this.searchId = searchId;
        this.email = email;
        this.user_cellphone = user_cellphone;
        this.captcha = "";
    }
    //-----------------------------------------------

    public RegisterHotelRequest() {
    }
    //-----------------------------------------------

    public ArrayList<PassengerInfoInternationalHotel> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<PassengerInfoInternationalHotel> passengers) {
        this.passengers = passengers;
    }

    public String getReserveId() {
        return reserveId;
    }

    public void setReserveId(String reserveId) {
        this.reserveId = reserveId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getSuggestId() {
        return suggestId;
    }

    public void setSuggestId(String suggestId) {
        this.suggestId = suggestId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getRoomId() {
        return roomId;
    }

    public void setRoomId(ArrayList<String> roomId) {
        this.roomId = roomId;
    }

    public String getUser_cellphone() {
        return user_cellphone;
    }

    public void setUser_cellphone(String user_cellphone) {
        this.user_cellphone = user_cellphone;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
