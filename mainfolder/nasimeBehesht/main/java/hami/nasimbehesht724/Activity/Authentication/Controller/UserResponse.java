package hami.nasimbehesht724.Activity.Authentication.Controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;



/**
 * Created by renjer on 2017-11-22.
 */

public class UserResponse {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("request_token_id")
    private String requestTokenId;
    @SerializedName("result")
    private UserResult User;
    //-----------------------------------------------


    public UserResponse() {
    }

    //-----------------------------------------------

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getUserId() {
        return userId;
    }

    public String getRequestTokenId() {
        return requestTokenId;
    }

    public UserResult getUser() {
        return User;
    }
    //-----------------------------------------------

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRequestTokenId(String requestTokenId) {
        this.requestTokenId = requestTokenId;
    }

    public void setUser(UserResult user) {
        User = user;
    }

    //-----------------------------------------------
    @Override
    public String toString() {
        try {
            Exclude ex = new Exclude();
            Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
            String jsonString = gson.toJson(this);
            return jsonString;
        } catch (Exception e) {
            e.getMessage();
        }
        return "";
    }

    //-----------------------------------------------
    class Exclude implements ExclusionStrategy {

        @Override
        public boolean shouldSkipClass(Class<?> arg0) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            SerializedName ns = field.getAnnotation(SerializedName.class);
            if (ns != null)
                return false;
            return true;
        }
    }
}
