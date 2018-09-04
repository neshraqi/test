package hami.mainapp.tour.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-03-11.
 */

public class BookingTourUserData {
    @SerializedName("user_mobile")
    private String userMobile;
    @SerializedName("user_email")
    private String userEmail;
    //-----------------------------------------------

    public BookingTourUserData(String userMobile, String userEmail) {
        this.userMobile = userMobile;
        this.userEmail = userEmail;
    }
    //-----------------------------------------------

    public BookingTourUserData() {
    }
    //-----------------------------------------------

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
