package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.PassengerInfo;

/**
 * Created by renjer on 2017-02-25.
 */

public class DomesticPassengerInfo implements Parcelable {
    private int typePassenger;
    private String nationalCode;
    private String birthDayPersian;
    private String birthDayGregorian;
    private String firstNamePersian;
    private String lastNamePersian;
    private String firstNameEng;
    private String lastNameEng;
    private int gender;
    private String passportExpireDate;
    private String passportCo;
    private String exportingCountry;
    private String nationalitycode;
    private String exportingCountryName;
    private int nationalityType;
    //-----------------------------------------------
    public final static int GENDER_MALE = 1;
    public final static int GENDER_FEMALE = 2;
    public final static int EXPORTING_COUNTRY_IRAN = 1;
    public final static int EXPORTING_COUNTRY_FOREIGN = 2;

    public DomesticPassengerInfo() {
        nationalityType = EXPORTING_COUNTRY_IRAN;
        exportingCountry = "";
        nationalitycode = "";
        passportCo = "";
        passportExpireDate = "";
        gender = Integer.parseInt(PassengerInfo.MALE);

    }

    protected DomesticPassengerInfo(Parcel in) {
        typePassenger = in.readInt();
        nationalCode = in.readString();
        birthDayPersian = in.readString();
        birthDayGregorian = in.readString();
        firstNamePersian = in.readString();
        lastNamePersian = in.readString();
        firstNameEng = in.readString();
        lastNameEng = in.readString();
        gender = in.readInt();
        passportExpireDate = in.readString();
        passportCo = in.readString();
        exportingCountry = in.readString();
        exportingCountryName = in.readString();
        nationalityType = in.readInt();
        nationalitycode = in.readString();
    }

    public static final Creator<DomesticPassengerInfo> CREATOR = new Creator<DomesticPassengerInfo>() {
        @Override
        public DomesticPassengerInfo createFromParcel(Parcel in) {
            return new DomesticPassengerInfo(in);
        }

        @Override
        public DomesticPassengerInfo[] newArray(int size) {
            return new DomesticPassengerInfo[size];
        }
    };

    //-----------------------------------------------
    public static DomesticPassengerInfo newInstanceIran(int typePassenger, String birthDayPersian, String birthDayGregorian, int gender, String firstNamePersian, String lastNamePersian, String firstNameEng, String lastNameEng, String nationalCode) {
        DomesticPassengerInfo domesticPassengerInfo = new DomesticPassengerInfo();
        domesticPassengerInfo.birthDayPersian = birthDayPersian;
        domesticPassengerInfo.birthDayGregorian = birthDayGregorian;
        domesticPassengerInfo.gender = gender;
        domesticPassengerInfo.firstNamePersian = firstNamePersian;
        domesticPassengerInfo.lastNamePersian = lastNamePersian;
        domesticPassengerInfo.firstNameEng = firstNameEng;
        domesticPassengerInfo.lastNameEng = lastNameEng;
        domesticPassengerInfo.nationalCode = nationalCode;
        domesticPassengerInfo.passportCo = "0";
        domesticPassengerInfo.nationalityType = EXPORTING_COUNTRY_IRAN;
        domesticPassengerInfo.typePassenger = typePassenger;
        return domesticPassengerInfo;
    }

    //-----------------------------------------------
    public static DomesticPassengerInfo newInstanceForeign(int typePassenger, String birthDayPersian, String birthDayGregorian, int gender, String firstNamePersian, String lastNamePersian, String firstNameEng, String lastNameEng, String passportExpireDate, String passportCo, String exportingCountry, String exportingCountryName) {
        DomesticPassengerInfo domesticPassengerInfo = new DomesticPassengerInfo();
        domesticPassengerInfo.birthDayPersian = birthDayPersian;
        domesticPassengerInfo.birthDayGregorian = birthDayGregorian;
        domesticPassengerInfo.gender = gender;
        domesticPassengerInfo.firstNamePersian = firstNamePersian;
        domesticPassengerInfo.lastNamePersian = lastNamePersian;
        domesticPassengerInfo.nationalCode = "0";
        domesticPassengerInfo.firstNameEng = firstNameEng;
        domesticPassengerInfo.lastNameEng = lastNameEng;
        domesticPassengerInfo.passportCo = passportCo;
        domesticPassengerInfo.passportExpireDate = passportExpireDate;
        domesticPassengerInfo.exportingCountry = exportingCountry;
        domesticPassengerInfo.nationalityType = EXPORTING_COUNTRY_FOREIGN;
        domesticPassengerInfo.exportingCountryName = exportingCountryName;
        domesticPassengerInfo.typePassenger = typePassenger;
        return domesticPassengerInfo;
    }
    //-----------------------------------------------

    public int getNationalityType() {
        return nationalityType;
    }

    public int getTypePassenger() {
        return typePassenger;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public String getBirthDayPersian() {
        return birthDayPersian;
    }

    public String getBirthDayGregorian() {
        return birthDayGregorian;
    }

    public String getFirstNamePersian() {

        return firstNamePersian == null || firstNamePersian.length() == 0 ? "---" : firstNamePersian;
    }

    public String getLastNamePersian() {
        return lastNamePersian == null || lastNamePersian.length() == 0 ? "---" : lastNamePersian;
    }

    public String getFirstNameEng() {
        return firstNameEng;
    }

    public String getLastNameEng() {
        return lastNameEng;
    }

    public int getGender() {
        return gender;
    }

    public String getPassportExpireDate() {
        return passportExpireDate;
    }

    public String getPassportCo() {
        return passportCo;
    }

    public String getExportingCountry() {
        return exportingCountry;
    }

    public String getExportingCountryName() {
        return exportingCountryName;
    }

    public String getNationalitycode() {
        return nationalitycode;
    }
    //-----------------------------------------------

    public void setTypePassenger(int typePassenger) {
        this.typePassenger = typePassenger;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public void setBirthDayPersian(String birthDayPersian) {
        this.birthDayPersian = birthDayPersian;
    }

    public void setBirthDayGregorian(String birthDayGregorian) {
        this.birthDayGregorian = birthDayGregorian;
    }

    public void setFirstNamePersian(String firstNamePersian) {
        this.firstNamePersian = firstNamePersian;
    }

    public void setLastNamePersian(String lastNamePersian) {
        this.lastNamePersian = lastNamePersian;
    }

    public void setNationalitycode(String nationalitycode) {
        this.nationalitycode = nationalitycode;
    }

    public void setFirstNameEng(String firstNameEng) {
        this.firstNameEng = firstNameEng;
    }

    public void setLastNameEng(String lastNameEng) {
        this.lastNameEng = lastNameEng;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setPassportExpireDate(String passportExpireDate) {
        this.passportExpireDate = passportExpireDate;
    }

    public void setPassportCo(String passportCo) {
        this.passportCo = passportCo;
    }

    public void setExportingCountry(String exportingCountry) {
        this.exportingCountry = exportingCountry;
    }

    public void setExportingCountryName(String exportingCountryName) {
        this.exportingCountryName = exportingCountryName;
    }

    public void setNationalityType(int nationalityType) {
        this.nationalityType = nationalityType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(typePassenger);
        dest.writeString(nationalCode);
        dest.writeString(birthDayPersian);
        dest.writeString(birthDayGregorian);
        dest.writeString(firstNamePersian);
        dest.writeString(lastNamePersian);
        dest.writeString(firstNameEng);
        dest.writeString(lastNameEng);
        dest.writeInt(gender);
        dest.writeString(passportExpireDate);
        dest.writeString(passportCo);
        dest.writeString(exportingCountry);
        dest.writeString(exportingCountryName);
        dest.writeInt(nationalityType);
        dest.writeString(nationalitycode);
    }
}
