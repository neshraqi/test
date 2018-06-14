package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by renjer on 2017-02-06.
 */

public class Country implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("fullname")
    private String fullName;
    @SerializedName("persian")
    private String persian;
    @SerializedName("filename")
    private String fileName;
    @SerializedName("code2")
    private String code;
    @SerializedName("code3")
    private String code3;
    //-----------------------------------------------

    public Country() {
    }

    //-----------------------------------------------
    public Country(String id, String fullName, String persian, String code2, String code3) {
        this.id = id;
        this.fullName = fullName;
        this.persian = persian;
        this.code = code2;
        this.code3 = code3;
    }
    //-----------------------------------------------

    public String getCode3() {
        return code3;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPersian() {
        return persian;
    }

    public String getFileName() {
        return fileName;
    }

    public String getCode() {
        return code;
    }
}
