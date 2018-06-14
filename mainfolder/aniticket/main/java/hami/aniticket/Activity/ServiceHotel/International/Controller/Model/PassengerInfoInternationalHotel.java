package hami.hamibelit.Activity.ServiceHotel.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

import hami.hamibelit.R;


/**
 * Created by renjer on 2018-01-31.
 */

public class PassengerInfoInternationalHotel implements RowTypeHotelPassenger {
    @SerializedName("typep")
    private int typePassenger;
    @SerializedName("name")
    private String firstName;
    @SerializedName("family")
    private String lastName;
    @SerializedName("room")
    private int room;
    @SerializedName("age")
    private String age;
    private int hasError = 1;
    public final static int TYPE_ADULTS = 1;
    public final static int TYPE_CHILD = 2;
    //-----------------------------------------------

    public PassengerInfoInternationalHotel() {
        hasError = -1;
    }

    //-----------------------------------------------
    public static PassengerInfoInternationalHotel newInstanceAdults(String firstName, String lastName, int roomId) {
        PassengerInfoInternationalHotel passengerInfoInternationalHotel = new PassengerInfoInternationalHotel();
        passengerInfoInternationalHotel.firstName = firstName;
        passengerInfoInternationalHotel.lastName = lastName;
        passengerInfoInternationalHotel.room = roomId;
        if (firstName.length() > 0 && lastName.length() > 0)
            passengerInfoInternationalHotel.hasError = 0;
        else if (firstName.length() == 0 && lastName.length() == 0)
            passengerInfoInternationalHotel.hasError = -1;
        else if (firstName.length() == 0 || lastName.length() == 0)
            passengerInfoInternationalHotel.hasError = 0;
        else
            passengerInfoInternationalHotel.hasError = -1;
        passengerInfoInternationalHotel.age = "0";
        passengerInfoInternationalHotel.typePassenger = TYPE_ADULTS;
        return passengerInfoInternationalHotel;
    }

    //-----------------------------------------------
    public static PassengerInfoInternationalHotel newInstanceChild(String firstName, String lastName, int roomId) {
        PassengerInfoInternationalHotel passengerInfoInternationalHotel = new PassengerInfoInternationalHotel();
        passengerInfoInternationalHotel.firstName = firstName;
        passengerInfoInternationalHotel.lastName = lastName;
        passengerInfoInternationalHotel.room = roomId;
        if (firstName.length() > 0 && lastName.length() > 0)
            passengerInfoInternationalHotel.hasError = 0;
        else if (firstName.length() == 0 && lastName.length() == 0)
            passengerInfoInternationalHotel.hasError = -1;
        else if (firstName.length() == 0 || lastName.length() == 0)
            passengerInfoInternationalHotel.hasError = 0;
        else
            passengerInfoInternationalHotel.hasError = -1;
        passengerInfoInternationalHotel.age = "0";
        passengerInfoInternationalHotel.typePassenger = TYPE_CHILD;
        return passengerInfoInternationalHotel;
    }
    //-----------------------------------------------

    public int getTypePassengerResource() {
        if (typePassenger == TYPE_ADULTS) {
            return R.string.adultEng;
        } else if (typePassenger == TYPE_CHILD) {
            return R.string.childEng;
        } else
            return -1;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setHasError(Boolean hasError) {
        if (hasError)
            this.hasError = 1;
        else
            this.hasError = 0;
    }

    public int getHasError() {
        return hasError;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
