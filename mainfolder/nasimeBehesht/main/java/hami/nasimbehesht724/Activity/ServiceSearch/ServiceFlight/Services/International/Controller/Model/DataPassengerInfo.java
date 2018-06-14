package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

import hami.nasimbehesht724.Const.FlightRules;

/**
 * Created by renjer on 2/1/2017.
 */

public class DataPassengerInfo {

    @SerializedName("nationalId")
    private String nationalId;
    @SerializedName("gender")
    private String gender = PassengerInfo.MALE;
    @SerializedName("passengerNamePersian")
    private String passengerNamePersian;
    @SerializedName("passengerFamilyPersian")
    private String passengerFamilyPersian;
    @SerializedName("passengerNameEnglish")
    private String passengerNameEnglish;
    @SerializedName("passengerFamilyEnglish")
    private String passengerFamilyEnglish;
    @SerializedName("dateOfBirth")
    private String dateOfBirth;
    @SerializedName("dateOfBirthp")
    private String dateOfBirthp;
    @SerializedName("passengersType")
    private String passengersType;
    @SerializedName("passportNumber")
    private String passportNumber = "";
    @SerializedName("nationality")
    private String nationality;
    @SerializedName("passportCountry")
    private String passportCountry = "";
    @SerializedName("passportExpiryDate")
    private String passportExpiryDate = "";
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    //-----------------------------------------------

    public DataPassengerInfo() {
    }
    //-----------------------------------------------

    public DataPassengerInfo(String nationalId, String gender, String passengerNamePersian, String passengerFamilyPersian, String passengerNameEnglish, String passengerFamilyEnglish, String dateOfBirth, String dateOfBirthp, String passengersType, String passportNumber, String nationality, String passportCountry, String passportExpiryDate, String email, String phone) {
        this.nationalId = nationalId;
        this.gender = gender;
        this.passengerNamePersian = passengerNamePersian;
        this.passengerFamilyPersian = passengerFamilyPersian;
        this.passengerNameEnglish = passengerNameEnglish;
        this.passengerFamilyEnglish = passengerFamilyEnglish;
        this.dateOfBirth = dateOfBirth;
        this.dateOfBirthp = dateOfBirthp;
        this.passengersType = passengersType;
        this.passportNumber = passportNumber;
        this.nationality = nationality;
        this.passportCountry = passportCountry;
        this.passportExpiryDate = passportExpiryDate;
        this.email = email;
        this.phone = phone;
    }

    //-----------------------------------------------
    public String getNationalId() {
        return nationalId;
    }

    public String getGender() {
        return gender;
    }

    public String getPassengerNamePersian() {
        return passengerNamePersian;
    }

    public String getPassengerFamilyPersian() {
        return passengerFamilyPersian;
    }

    public String getPassengerNameEnglish() {
        return passengerNameEnglish;
    }

    public String getPassengerFamilyEnglish() {
        return passengerFamilyEnglish;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfBirthp() {
        return dateOfBirthp;
    }

    public String getPassengersType() {
        return passengersType;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getNationality() {
        if (nationality == null || nationality.length() == 0)
            return String.valueOf(FlightRules.PN_IRANIAN);
        return nationality;
    }

    public String getPassportCountry() {
        return passportCountry;
    }

    public String getPassportExpiryDate() {
        return passportExpiryDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
