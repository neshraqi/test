package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 1/21/2017.
 */

public class Passenger {
    @SerializedName("id")
    private String id;
    @SerializedName("gender")
    private String gender;
    @SerializedName("nid")
    private String nid;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("name")
    private String name;
    @SerializedName("family")
    private String family;
    @SerializedName("expdate")
    private String expDate;
    @SerializedName("passport_number")
    private String passportNumber;
    @SerializedName("cou_nationality")
    private String couNationality;
    @SerializedName("nationalitycode")
    private String nationalityCode;
    @SerializedName("typep")
    private String typeP;
    //-----------------------------------------------


    public Passenger() {
    }

    //-----------------------------------------------

    public Passenger(String id, String gender, String nid, String birthday, String name, String family, String expDate, String passportNumber, String couNationality, String nationalityCode, String typeP) {
        this.id = id;
        this.gender = gender;
        this.nid = nid;
        this.birthday = birthday;
        this.name = name;
        this.family = family;
        this.expDate = expDate;
        this.passportNumber = passportNumber;
        this.couNationality = couNationality;
        this.nationalityCode = nationalityCode;
        this.typeP = typeP;
    }
    //-----------------------------------------------

    public String getId() {
        return id;
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

    public String getFamily() {
        return family;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getCouNationality() {
        return couNationality;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public String getTypeP() {
        return typeP;
    }
    //-----------------------------------------------

    public void setId(String id) {
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setCouNationality(String couNationality) {
        this.couNationality = couNationality;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public void setTypeP(String typeP) {
        this.typeP = typeP;
    }
}
