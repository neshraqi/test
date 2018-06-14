package hami.hamibelit.Activity.ServiceHotel.Domestic.Controller.Model;

import com.google.gson.annotations.SerializedName;

import hami.hamibelit.BaseController.ToStringClass;
import hami.hamibelit.Const.KeyConst;


public class DomesticHotelRegisterPassengerRequest extends ToStringClass {

    @SerializedName("name")
    private String name;
    @SerializedName("family")
    private String family;
    @SerializedName("Internationalcode")
    private String Internationalcode;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("email")
    private String email;
    @SerializedName("roomId")
    private String roomId;
    @SerializedName("extraPerson")
    private String extraPerson;
    @SerializedName("phone")
    private String phone;
    @SerializedName("description")
    private String description;
    @SerializedName("appKey")
    private String appKey;
    @SerializedName("appSecret")
    private String appSecret;
    @SerializedName("search_id")
    private String searchId;
    //-----------------------------------------------

    public DomesticHotelRegisterPassengerRequest() {
        appKey = KeyConst.appKey;
        appSecret = KeyConst.appSecret;
    }
    //-----------------------------------------------


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getInternationalcode() {
        return Internationalcode;
    }

    public void setInternationalcode(String internationalcode) {
        Internationalcode = internationalcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getExtraPerson() {
        return extraPerson;
    }

    public void setExtraPerson(String extraPerson) {
        this.extraPerson = extraPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
}
