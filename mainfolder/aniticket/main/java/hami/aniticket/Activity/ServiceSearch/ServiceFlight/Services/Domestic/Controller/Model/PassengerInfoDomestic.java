package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-03-05.
 */

public class PassengerInfoDomestic {
    @SerializedName("code")
    private String code;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("meliat")
    private String meliat;
    @SerializedName("gender")
    private String gender;
    @SerializedName("englishFirstName")
    private String englishFirstName;
    @SerializedName("englishLastName")
    private String englishLastName;
    @SerializedName("persianFirstName")
    private String persianFirstName;
    @SerializedName("persianLastName")
    private String persianLastName;
    @SerializedName("expdate")
    private String expDate;
    @SerializedName("passport_number")
    private String passportNumber;
    @SerializedName("nationalitycode")
    private String nationalityCode;
    @SerializedName("passengerType")
    private String passengerType;
    //-----------------------------------------------

    public PassengerInfoDomestic(String code, String birthday, String meliat, String gender, String englishFirstName, String englishLastName, String persianFirstName, String persianLastName, String expDate, String passportNumber, String nationalityCode, String passengerType) {
        this.code = code;
        this.birthday = birthday;
        this.meliat = meliat;
        this.gender = gender;
        this.englishFirstName = englishFirstName;
        this.englishLastName = englishLastName;
        this.persianFirstName = persianFirstName;
        this.persianLastName = persianLastName;
        this.expDate = expDate;
        this.passportNumber = passportNumber;
        this.nationalityCode = nationalityCode;
        this.passengerType = passengerType;
    }

    //-----------------------------------------------
    public PassengerInfoDomestic(DomesticPassengerInfo domesticPassengerInfo) {
        this.code = domesticPassengerInfo.getNationalCode();
        this.birthday = domesticPassengerInfo.getBirthDayGregorian();
        this.meliat = String.valueOf(domesticPassengerInfo.getNationalityType());
        this.gender = String.valueOf(domesticPassengerInfo.getGender());
        this.englishFirstName = domesticPassengerInfo.getFirstNameEng();
        this.englishLastName = domesticPassengerInfo.getLastNameEng();
        this.persianFirstName = domesticPassengerInfo.getFirstNamePersian();
        this.persianLastName = domesticPassengerInfo.getLastNamePersian();
        this.expDate = domesticPassengerInfo.getPassportExpireDate();
        this.passportNumber = domesticPassengerInfo.getPassportCo();
        this.nationalityCode = domesticPassengerInfo.getNationalitycode();
        this.passengerType = String.valueOf(domesticPassengerInfo.getTypePassenger());
    }
    //-----------------------------------------------

    public String getCode() {
        return code;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getMeliat() {
        return meliat;
    }

    public String getGender() {
        return gender;
    }

    public String getEnglishFirstName() {
        return englishFirstName;
    }

    public String getEnglishLastName() {
        return englishLastName;
    }

    public String getPersianFirstName() {
        return persianFirstName;
    }

    public String getPersianLastName() {
        return persianLastName;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public String getPassengerType() {
        return passengerType;
    }
}
