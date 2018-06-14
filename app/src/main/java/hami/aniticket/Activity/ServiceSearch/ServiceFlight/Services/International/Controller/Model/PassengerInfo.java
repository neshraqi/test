package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import hami.aniticket.Const.FlightRules;
import hami.aniticket.R;
import hami.aniticket.Util.Database.FlightDomesticOffline;

/**
 * Created by renjer on 1/25/2017.
 */

public class PassengerInfo implements Parcelable {

    public static String MALE = "1";
    public static String FEMALE = "2";
    private String gender;
    private String nid;
    private String birthday;
    private String name;
    private String family;
    private String namePersian;
    private String familyPersian;
    private String expDate;
    private String passport_number;
    private String exportingCountry;
    private String exportingCountryPersian;
    private String exportingCountryCode;

    private String nationalityCountry;
    private String nationalityCountryPersian;
    private String nationalityCountryCode;
    private String typep;
    private String dateOfIssueOfThePassport;
    private int hasError = 1;
    private int hasValidate = -1;

    //-----------------------------------------------
    public PassengerInfo(String gender, String nid, String birthday, String name, String family, String expdate, String passport_number, String nationalityCountry, String exportingCountryCode, String typep) {
        this.gender = gender;
        this.nid = nid;
        this.birthday = birthday;
        this.name = name;
        this.family = family;
        this.expDate = expdate;
        this.passport_number = passport_number;
        this.exportingCountryCode = exportingCountryCode;
        this.nationalityCountry = nationalityCountry;
        this.typep = typep;
    }

    //-----------------------------------------------
    public PassengerInfo(Context context, String typep) {
        hasError = 1;
        FlightDomesticOffline flightOffline = new FlightDomesticOffline(context);
        Country country = flightOffline.getCountryByName("Iran");
        this.gender = FEMALE;
        this.nid = "";
        this.birthday = "";
        this.name = "";
        this.family = "";
        this.expDate = "";
        this.passport_number = "";
        this.dateOfIssueOfThePassport = "";
        this.exportingCountry = country.getFullName();
        this.exportingCountryCode = country.getCode();
        this.exportingCountryPersian = country.getPersian();

        this.nationalityCountry = country.getFullName();
        this.nationalityCountryCode = country.getCode();
        this.nationalityCountryPersian = country.getPersian();

        this.typep = typep;
        hasValidate = -1;
    }
    //-----------------------------------------------


    protected PassengerInfo(Parcel in) {
        gender = in.readString();
        nid = in.readString();
        birthday = in.readString();
        name = in.readString();
        family = in.readString();
        namePersian = in.readString();
        familyPersian = in.readString();
        expDate = in.readString();
        passport_number = in.readString();
        exportingCountry = in.readString();
        exportingCountryPersian = in.readString();
        exportingCountryCode = in.readString();
        nationalityCountry = in.readString();
        nationalityCountryPersian = in.readString();
        nationalityCountryCode = in.readString();
        dateOfIssueOfThePassport = in.readString();
        typep = in.readString();
        hasError = in.readInt();
        hasValidate = in.readInt();
    }

    public static final Creator<PassengerInfo> CREATOR = new Creator<PassengerInfo>() {
        @Override
        public PassengerInfo createFromParcel(Parcel in) {
            return new PassengerInfo(in);
        }

        @Override
        public PassengerInfo[] newArray(int size) {
            return new PassengerInfo[size];
        }
    };

    public void setHasError(Boolean hasError) {
        if (hasError)
            this.hasError = 1;
        else
            this.hasError = 0;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setBirthday(String year, String month, String day) {

        this.birthday = birthday;
    }

    public void setDateOfIssueOfThePassport(String dateOfIssueOfThePassport) {
        this.dateOfIssueOfThePassport = dateOfIssueOfThePassport;
    }

    public void setHasValidate(int hasValidate) {
        this.hasValidate = hasValidate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public void setPassportNumber(String passport_number) {
        this.passport_number = passport_number;
    }

    public void setExportingCountry(String exportingCountry) {
        this.exportingCountry = exportingCountry;
    }

    public void setNationalityCountry(String nationalityname) {
        this.nationalityCountry = nationalityname;
    }

    public void setNationalityCountryCode(String nationalityCode) {
        this.nationalityCountryCode = nationalityCode;
    }

    public void setExportingCountryCode(String exportingCountryCode) {
        this.exportingCountryCode = exportingCountryCode;
    }

    public void setExportingCountryPersian(String exportingCountryPersian) {
        this.exportingCountryPersian = exportingCountryPersian;
    }

    public void setNationalityCountryPersian(String nationalityCountryPersian) {
        this.nationalityCountryPersian = nationalityCountryPersian;
    }

    public void setFamilyPersian(String familyPersian) {
        this.familyPersian = familyPersian;
    }

    public void setNamePersian(String namePersian) {
        this.namePersian = namePersian;
    }

    //-----------------------------------------------
    public int getTypePassengerResource() {
        switch (Integer.valueOf(typep)) {
            case FlightRules.TP_ADULTS:
                return R.string.pAdults;
            case FlightRules.TP_CHILDREN:
                return R.string.pChildren;
            case FlightRules.TP_INFANT:
                return R.string.pInfant;
        }
        return 0;
    }

    public int getTypePassengerResource2() {
        switch (Integer.valueOf(typep)) {
            case FlightRules.TP_ADULTS:
                return R.string.adults;
            case FlightRules.TP_CHILDREN:
                return R.string.children;
            case FlightRules.TP_INFANT:
                return R.string.infant;
        }
        return 0;
    }

    public Boolean getHasError() {
        if (hasError == 0)
            return false;
        return true;
    }

    public String getDateOfIssueOfThePassport() {
        return dateOfIssueOfThePassport;
    }

    public String getPriceByPassenger(String priceAdults, String priceChild, String priceInfant) {
        switch (Integer.valueOf(typep)) {
            case FlightRules.TP_ADULTS:
                return priceAdults;
            case FlightRules.TP_CHILDREN:
                return priceChild;
            case FlightRules.TP_INFANT:
                return priceInfant;
        }
        return "";
    }

    public String getPriceByPassenger(ArrayList<String> lists) {
        switch (Integer.valueOf(typep)) {
            case FlightRules.TP_ADULTS:
                return lists.get(0);
            case FlightRules.TP_CHILDREN:
                return lists.get(1);
            case FlightRules.TP_INFANT:
                return lists.get(2);
        }
        return "";
    }

    public String getGender() {
        return gender;
    }

    public String getNid() {
        return nid;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getName() {
        return name;
    }

    public String getNamePersian() {
        return namePersian;
    }

    public String getFamilyPersian() {
        return familyPersian;
    }

    public String getFamily() {
        return family;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getPassportNumber() {
        return passport_number;
    }

    public String getExportingCountry() {
        return exportingCountry;
    }

    public String getExportingCountryPersian() {
        return exportingCountryPersian;
    }

    public String getNationalityCountryPersian() {
        return nationalityCountryPersian;
    }

    public String getNationalityCountry() {
        return nationalityCountry;
    }

    public String getNationalityCountryCode() {
        return nationalityCountryCode;
    }

    public String getTypep() {
        return typep;
    }

    public int getHasValidate() {
        return hasValidate;
    }

    public String getExportingCountryCode() {
        return exportingCountryCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gender);
        dest.writeString(nid);
        dest.writeString(birthday);
        dest.writeString(name);
        dest.writeString(family);
        dest.writeString(namePersian);
        dest.writeString(familyPersian);
        dest.writeString(expDate);
        dest.writeString(passport_number);
        dest.writeString(exportingCountry);
        dest.writeString(exportingCountryPersian);
        dest.writeString(exportingCountryCode);
        dest.writeString(nationalityCountry);
        dest.writeString(nationalityCountryPersian);
        dest.writeString(nationalityCountryCode);
        dest.writeString(dateOfIssueOfThePassport);
        dest.writeString(typep);
        dest.writeInt(hasError);
        dest.writeInt(hasValidate);
    }


    //-----------------------------------------------
}
