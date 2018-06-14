package hami.hamibelit.Activity.Authentication.Controller;

import com.google.gson.annotations.SerializedName;



/**
 * Created by renjer on 2017-11-22.
 */

public class UserResult {


    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_cellphone")
    private String userCellphone;
    @SerializedName("password")
    private String password;
    @SerializedName("user_email")
    private String user_email;
    @SerializedName("user_type")
    private String user_type;
    @SerializedName("user_mojodi")
    private String user_mojodi;
    @SerializedName("user_level")
    private String user_level;
    @SerializedName("user_etebar")
    private String user_etebar;
    @SerializedName("user_namekart")
    private String user_namekart;
    @SerializedName("user_shomareshaba")
    private String user_shomareshaba;
    //-----------------------------------------------

    public UserResult() {
    }

    //-----------------------------------------------

    public String getUserName() {
        return userName;
    }

    public String getUserCellphone() {
        return userCellphone;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getUser_mojodi() {
        return user_mojodi;
    }

    public String getUser_level() {
        return user_level;
    }

    public String getUser_etebar() {
        return user_etebar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_namekart() {
        return user_namekart;
    }

    public String getUser_shomareshaba() {
        return user_shomareshaba;
    }
}
