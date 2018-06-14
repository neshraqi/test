package hami.nasimbehesht724.Util.Database;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import hami.nasimbehesht724.Activity.Authentication.Controller.UserResponse;

/**
 * Created by renjer on 2017-05-16.
 */

public class DataSaver {
    private Context context;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String SPLASH = "splash";
    public static final String USER_MOBILE = "mobileUser";
    public static final String USER_JSON = "userJson";
    public static final String USER_PASSWORD = "mobilePassword";

    public DataSaver(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public Boolean hasLogin() {
        try {
            SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String userJson = sharedpreferences.getString(USER_JSON, "");
            if (userJson != null && userJson.length() > 0)
                return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    //-----------------------------------------------
    public UserResponse getUser() {
        try {
            SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String userJson = sharedpreferences.getString(USER_JSON, "");
            Gson gson = new Gson();
            UserResponse userResponse = gson.fromJson(userJson, UserResponse.class);
            return userResponse;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    //-----------------------------------------------
    public void logout() {
        try {
            SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(USER_JSON, "");
            editor.commit();
        } catch (Exception e) {

        }
    }

    //-----------------------------------------------
    public void setLogin(String userJson) {
        try {
            SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(USER_JSON, userJson);
            editor.commit();
        } catch (Exception e) {

        }
    }

    //-----------------------------------------------
    public void updateLogin(String requestTokenId, String userId) {
        try {
            UserResponse userResponse = getUser();
            userResponse.setRequestTokenId(requestTokenId);
            userResponse.setUserId(userId);
            SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(USER_JSON, userResponse.toString());
            editor.commit();
        } catch (Exception e) {

        }
    }

    //-----------------------------------------------
    public void setMobileLogin(String mobile) {
        try {
            SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(USER_MOBILE, mobile);
            editor.commit();
        } catch (Exception e) {

        }
    }
    //-----------------------------------------------
    public String getMobileLogin() {
        try {
            SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            return sharedpreferences.getString(USER_MOBILE, "");
        } catch (Exception e) {
            return "";
        }
    }
    //-----------------------------------------------
    public void setEmailMobile(String mobile, String email) {
        try {
            SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Phone, mobile);
            editor.putString(Email, email);
            editor.commit();
        } catch (Exception e) {

        }
    }

    //-----------------------------------------------
    public void startedSplash() {
        try {
            SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(SPLASH, true);
            editor.commit();
        } catch (Exception e) {

        }
    }

    //-----------------------------------------------
    public Boolean isShowSplash() {
        try {
            SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            return sharedpreferences.getBoolean(SPLASH, false);
        } catch (Exception e) {
            return false;
        }
    }

    //-----------------------------------------------
    public String getEmail() {
        try {
            SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            return sharedpreferences.getString(Email, "");
        } catch (Exception e) {
            return "";
        }
    }

    //-----------------------------------------------
    public String getMobile() {
        try {
            SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            return sharedpreferences.getString(Phone, "");
        } catch (Exception e) {
            return "";
        }
    }
}
