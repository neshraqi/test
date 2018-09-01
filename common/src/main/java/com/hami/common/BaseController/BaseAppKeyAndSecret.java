package com.hami.common.BaseController;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.hami.common.Const.KeyConst;


/**
 * Created by renjer on 2018-02-10.
 */

public class BaseAppKeyAndSecret {
    @SerializedName("appKey")
    private String appKey;
    @SerializedName("appSecret")
    private String appSecret;
    //-----------------------------------------------


    public BaseAppKeyAndSecret() {
        this.appKey = KeyConst.appKey;
        this.appSecret = KeyConst.appSecret;
    }
    //-----------------------------------------------
    public BaseAppKeyAndSecret(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
    }
    //-----------------------------------------------


    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
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
