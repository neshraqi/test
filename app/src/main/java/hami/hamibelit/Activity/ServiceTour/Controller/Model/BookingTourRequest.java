package hami.hamibelit.Activity.ServiceTour.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import hami.hamibelit.BaseController.ToStringClass;

/**
 * Created by renjer on 2018-03-11.
 */

public class BookingTourRequest extends ToStringClass {

    @SerializedName("passengers")
    private ArrayList<PassengerTour> passengers;
    @SerializedName("userData")
    private BookingTourUserData userData;
    @SerializedName("tourID")
    private String tourID;
    @SerializedName("captcha")
    private String captcha;
    //-----------------------------------------------

    public BookingTourRequest() {
    }
    //-----------------------------------------------

    public ArrayList<PassengerTour> getPassengers() {
        return passengers;
    }

    public BookingTourUserData getUserData() {
        return userData;
    }

    public String getTourID() {
        return tourID;
    }

    public String getCaptcha() {
        return captcha;
    }
    //-----------------------------------------------

    public void setPassengers(ArrayList<PassengerTour> passengers) {
        this.passengers = passengers;
    }

    public void setUserData(BookingTourUserData userData) {
        this.userData = userData;
    }

    public void setTourID(String tourID) {
        this.tourID = tourID;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
