package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 1/29/2017.
 */

public class ReserveRequest {
    //{"ticketId":"15000091","sessionId":"f9481e9bc3d49906bccd0c36173c869c"}:
    @SerializedName("sessionId")
    private String searchId;
    @SerializedName("ticketId")
    private String ticketId;
    //-----------------------------------------------


    public ReserveRequest(String searchId, String ticketId) {
        this.searchId = searchId;
        this.ticketId = ticketId;
    }
    //-----------------------------------------------

    public ReserveRequest() {
    }
    //-----------------------------------------------

    public String getSearchId() {
        return searchId;
    }

    public String getTicketId() {
        return ticketId;
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
