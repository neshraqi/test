package com.hami.servicetour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hami.common.Const.FlightRules;


/**
 * Created by renjer on 2018-03-10.
 */

public class PassengerTour implements Parcelable {

    @SerializedName("gender")
    private int gender;
    @SerializedName("m_code")
    private String nationalCode;
    @SerializedName("age_level")
    private int passengerType;
    @SerializedName("latin_name")
    private String firstNameEng;
    @SerializedName("latin_lname")
    private String lastNameEng;
    @SerializedName("name")
    private String firstNamePersian;
    @SerializedName("lname")
    private String lastNamePersian;
    @SerializedName("nationality")
    private String nationalityCountryCode;
    private String nationalityCountryName;
    @SerializedName("b_day")
    private String birthDayPersian;
    @SerializedName("b_day_l")
    private String birthDayPersianTimeStamp;

    private String birthDayGreg;
    @SerializedName("pass_id")
    private String passportNumber;

    @SerializedName("pass_start_date_l")
    private String passStartDateTimeStamp;
    @SerializedName("pass_start_date")
    private String passStartDate;


    @SerializedName("pass_end_date")
    private String expireDate;
    @SerializedName("pass_end_date_l")
    private long expireDateTimeStamp;
    private String price;
    private String priceSingle;
    public final static int GENDER_MALE = 1;
    public final static int GENDER_FEMALE = 2;
    private int hasError = 1;
    private int hasValidate = -1;
    //-----------------------------------------------


    public PassengerTour() {
        gender = GENDER_MALE;
        nationalCode = "";
        passengerType = FlightRules.TP_ADULTS;
        firstNameEng = "";
        lastNameEng = "";
        firstNamePersian = "";
        lastNamePersian = "";
        nationalityCountryCode = "IR";
        nationalityCountryName = "ایران";
        birthDayGreg = "";
        birthDayPersian = "";
        birthDayPersianTimeStamp = "";
        passStartDate = "";
        passStartDateTimeStamp = "";
        passportNumber = "";
        expireDate = "";
        expireDateTimeStamp = 0;
        price = "";
        hasError = 1;
        hasValidate = -1;
        priceSingle = "";
    }
    //-----------------------------------------------


    protected PassengerTour(Parcel in) {
        gender = in.readInt();
        nationalCode = in.readString();
        passengerType = in.readInt();
        firstNameEng = in.readString();
        lastNameEng = in.readString();
        firstNamePersian = in.readString();
        lastNamePersian = in.readString();
        nationalityCountryCode = in.readString();
        nationalityCountryName = in.readString();
        birthDayPersian = in.readString();
        birthDayPersianTimeStamp = in.readString();
        birthDayGreg = in.readString();
        passportNumber = in.readString();
        passStartDate = in.readString();
        passStartDateTimeStamp = in.readString();
        expireDate = in.readString();
        expireDateTimeStamp = in.readLong();
        price = in.readString();
        priceSingle = in.readString();
        hasError = in.readInt();
        hasValidate = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(gender);
        dest.writeString(nationalCode);
        dest.writeInt(passengerType);
        dest.writeString(firstNameEng);
        dest.writeString(lastNameEng);
        dest.writeString(firstNamePersian);
        dest.writeString(lastNamePersian);
        dest.writeString(nationalityCountryCode);
        dest.writeString(nationalityCountryName);
        dest.writeString(birthDayPersian);
        dest.writeString(birthDayPersianTimeStamp);
        dest.writeString(birthDayGreg);
        dest.writeString(passportNumber);
        dest.writeString(passStartDate);
        dest.writeString(passStartDateTimeStamp);
        dest.writeString(expireDate);
        dest.writeLong(expireDateTimeStamp);
        dest.writeString(price);
        dest.writeString(priceSingle);
        dest.writeInt(hasError);
        dest.writeInt(hasValidate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PassengerTour> CREATOR = new Creator<PassengerTour>() {
        @Override
        public PassengerTour createFromParcel(Parcel in) {
            return new PassengerTour(in);
        }

        @Override
        public PassengerTour[] newArray(int size) {
            return new PassengerTour[size];
        }
    };

    public int getGender() {
        return gender;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public String getFirstNameEng() {
        return firstNameEng;
    }

    public String getLastNameEng() {
        return lastNameEng;
    }

    public String getFirstNamePersian() {
        return firstNamePersian;
    }

    public String getLastNamePersian() {
        return lastNamePersian;
    }

    public String getNationalityCountryCode() {
        return nationalityCountryCode;
    }

    public String getNationalityCountryName() {
        return nationalityCountryName;
    }

    public String getBirthDayPersian() {
        return birthDayPersian;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public int getPassengerType() {
        return passengerType;
    }

    public String getBirthDayGreg() {
        return birthDayGreg;
    }

    public String getPrice() {
        return price;
    }

    public String getPriceSingle() {
        return priceSingle;
    }

    public String getBirthDayPersianTimeStamp() {
        return birthDayPersianTimeStamp;
    }

    public String getPassStartDate() {
        return passStartDate;
    }

    public String getPassStartDateTimeStamp() {
        return passStartDateTimeStamp;
    }

    public long getExpireDateTimeStamp() {
        return expireDateTimeStamp;
    }

    public int getHasError() {
        return hasError;
    }

    public int getHasValidate() {
        return hasValidate;
    }
    //-----------------------------------------------

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public void setFirstNameEng(String firstNameEng) {
        this.firstNameEng = firstNameEng;
    }

    public void setLastNameEng(String lastNameEng) {
        this.lastNameEng = lastNameEng;
    }

    public void setFirstNamePersian(String firstNamePersian) {
        this.firstNamePersian = firstNamePersian;
    }

    public void setLastNamePersian(String lastNamePersian) {
        this.lastNamePersian = lastNamePersian;
    }

    public void setNationalityCountryCode(String nationalityCountryCode) {
        this.nationalityCountryCode = nationalityCountryCode;
    }

    public void setNationalityCountryName(String nationalityCountryName) {
        this.nationalityCountryName = nationalityCountryName;
    }

    public void setBirthDayPersian(String birthDayPersian) {
        this.birthDayPersian = birthDayPersian;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setPassengerType(int passengerType) {
        this.passengerType = passengerType;
    }

    public void setBirthDayGreg(String birthDayGreg) {
        this.birthDayGreg = birthDayGreg;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPriceSingle(String priceSingle) {
        this.priceSingle = priceSingle;
    }

    public void setBirthDayPersianTimeStamp(String birthDayPersianTimeStamp) {
        this.birthDayPersianTimeStamp = birthDayPersianTimeStamp;
    }

    public void setPassStartDate(String passStartDate) {
        this.passStartDate = passStartDate;
    }

    public void setPassStartDateTimeStamp(String passStartDateTimeStamp) {
        this.passStartDateTimeStamp = passStartDateTimeStamp;
    }

    public void setExpireDateTimeStamp(long expireDateTimeStamp) {
        this.expireDateTimeStamp = expireDateTimeStamp;
    }

    public void setHasError(int hasError) {
        this.hasError = hasError;
    }

    public void setHasValidate(int hasValidate) {
        this.hasValidate = hasValidate;
    }
}
