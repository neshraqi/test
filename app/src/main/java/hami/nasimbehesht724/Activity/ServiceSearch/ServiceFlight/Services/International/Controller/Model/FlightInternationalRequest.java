package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by renjer on 1/11/2017.
 */

public class FlightInternationalRequest implements Serializable {
    public final static String KEY_JSON_SEARCH = "kjs";
    @SerializedName("date1")
    private String date1;
    @SerializedName("date2")
    private String date2;
    @SerializedName("origin")
    private String origin;
    private String originPersian;
    @SerializedName("destination")
    private String destination;
    private String destinationPersian;
    @SerializedName("originflag")
    private String originFlag;
    @SerializedName("destinationflag")
    private String destinationFlag;
    @SerializedName("infant")
    private String infant;
    @SerializedName("child")
    private String child;
    @SerializedName("adult")
    private String adult;
    @SerializedName("cabinType")
    private String cabinType;

    @SerializedName("searchType")
    private String searchType;


    private String wentDate;
    private String returnDate;

    public final static String SEARCH_TYPE_PARTO = "1";
    public final static String SEARCH_TYPE_IAEI = "2";

    //-----------------------------------------------


    public FlightInternationalRequest() {
        adult = "1";
        child = "0";
        infant = "0";
    }

    public FlightInternationalRequest(String date1, String date2, String origin, String destination, String originFlag, String destinationFlag, String adult, String child, String infant, String cabinType, String wentDate, String returnDate) {
        this.date1 = date1;
        this.date2 = date2;
        this.origin = origin;
        this.destination = destination;
        this.originFlag = originFlag;
        this.destinationFlag = destinationFlag;
        this.infant = infant;
        this.child = child;
        this.adult = adult;
        this.cabinType = cabinType;
        this.wentDate = wentDate;
        this.returnDate = returnDate;
    }
    //-----------------------------------------------

    public FlightInternationalRequest(String date1, String origin, String destination, String originFlag, String destinationFlag, String adult, String child, String infant, String cabinType) {
        this.date1 = date1;
        this.origin = origin;
        this.destination = destination;
        this.originFlag = originFlag;
        this.destinationFlag = destinationFlag;
        this.infant = infant;
        this.child = child;
        this.adult = adult;
        this.cabinType = cabinType;

    }

    //-----------------------------------------------
    public void movementSourceWithDest() {
        String temp = getOrigin();
        String tempFlag = getOriginFlag();
        String tempPersian = getOriginPersian();
        setOrigin(getDestination());
        setOriginFlag(getDestinationFlag());
        setOriginPersian(getDestinationPersian());
        setDestination(temp);
        setDestinationFlag(tempFlag);
        setDestinationPersian(tempPersian);
    }

    public String getWentDate() {
        return wentDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getDate1() {
        return date1;
    }

    public String getDate2() {
        return date2;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getOriginFlag() {
        return originFlag;
    }

    public String getDestinationFlag() {
        return destinationFlag;
    }

    public String getInfant() {
        return infant;
    }

    public String getChild() {
        return child;
    }

    public String getAdult() {
        return adult;
    }

    public String getCabinType() {
        return cabinType;
    }

    public String getDestinationPersian() {
        return destinationPersian;
    }

    public String getOriginPersian() {
        return originPersian;
    }
//-----------------------------------------------

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setOriginFlag(String originFlag) {
        this.originFlag = originFlag;
    }

    public void setDestinationFlag(String destinationFlag) {
        this.destinationFlag = destinationFlag;
    }

    public void setInfant(String infant) {
        this.infant = infant;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public void setCabinType(String cabinType) {
        this.cabinType = cabinType;
    }

    public void setDestinationPersian(String destinationPersian) {
        this.destinationPersian = destinationPersian;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setWentDate(String wentDate) {
        this.wentDate = wentDate;
    }

    public void setOriginPersian(String originPersian) {
        this.originPersian = originPersian;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

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

    public int getAdultInt() {
        return (adult != null && adult.length() > 0) ? Integer.valueOf(adult) : 1;
    }

    public int getChildInte() {
        return (child != null && child.length() > 0) ? Integer.valueOf(child) : 0;
    }

    public int getInfantInt() {
        return (infant != null && infant.length() > 0) ? Integer.valueOf(infant) : 0;
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
