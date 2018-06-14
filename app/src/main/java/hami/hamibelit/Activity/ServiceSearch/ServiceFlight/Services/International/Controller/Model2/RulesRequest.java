package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by renjer on 1/24/2017.
 */

public class RulesRequest implements Serializable {
    @SerializedName("sessionId")
    private String searchId;
    @SerializedName("searchId")
    private List<String> flightNumber;
    //-----------------------------------------------

    public RulesRequest() {
    }

    //-----------------------------------------------
    public RulesRequest(String searchId, String flightNumber) {
        this.searchId = searchId;
        this.flightNumber = new ArrayList<>();
        this.flightNumber.add(flightNumber);
    }
    //-----------------------------------------------

    public String getSearchId() {
        return searchId;
    }

    public String getFlightNumber() {
        return "";
    }
    //-----------------------------------------------


    @Override
    public String toString() {
        try{
            Exclude ex = new Exclude();
            Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
            String jsonString = gson.toJson(this);
            return jsonString;
        }
        catch (Exception e){
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
            if(ns != null)
                return false;
            return true;
        }
    }
}
