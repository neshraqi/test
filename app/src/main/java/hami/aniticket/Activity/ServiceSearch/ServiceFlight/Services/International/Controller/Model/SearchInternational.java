package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by renjer on 1/10/2017.
 */

public class SearchInternational implements Serializable {
    @SerializedName("country")
    private String country;
    @SerializedName("countryNameE")
    private String countryNameE;
    @SerializedName("countryNameP")
    private String countryNameP;
    @SerializedName("data")
    private String data;
    @SerializedName("dataf")
    private String dataF;
    @SerializedName("yata")
    private String yata;
    @SerializedName("airport")
    private String airport;
    @SerializedName("airportf")
    private String airportF;
    @SerializedName("parent")
    private String parent;
    private int type = CITY;
    public final static int HEADER = 0;
    public final static int CITY = 1;

    //-----------------------------------------------
    public static SearchInternational newInstance(String data, String dataF, String yata) {
        SearchInternational searchInternational = new SearchInternational();
        searchInternational.type = CITY;
        searchInternational.country = "IRAN";
        searchInternational.data = data;
        searchInternational.dataF = dataF;
        searchInternational.yata = yata;
        searchInternational.parent = "";
        searchInternational.airport = "";
        searchInternational.airportF = "";
        searchInternational.countryNameE = "";
        searchInternational.countryNameP = "";
        return searchInternational;
    }

    public static SearchInternational newInstance(String data) {
        SearchInternational searchInternational = new SearchInternational();
        searchInternational.type = HEADER;
        searchInternational.country = "IRAN";
        searchInternational.data = data;
        searchInternational.dataF = "";
        searchInternational.yata = "";
        searchInternational.parent = "";
        searchInternational.airport = "";
        searchInternational.airportF = "";
        searchInternational.countryNameE = "";
        searchInternational.countryNameP = "";
        return searchInternational;
    }

    //-----------------------------------------------
    public void setYata(String yata) {
        this.yata = yata;
    }

    public SearchInternational() {
    }

    //-----------------------------------------------

    public String getData() {
        return data;
    }

    public String getDataF() {
        return dataF;
    }

    public String getYata() {
        return yata;
    }

    public String getAirport() {
        return airport;
    }

    public String getAirportF() {
        return airportF;
    }

    public String getParent() {
        return parent;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryNameE() {
        return countryNameE;
    }

    public String getCountryNameP() {
        return countryNameP;
    }

    public int getType() {
        return type;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDataF(String dataF) {
        this.dataF = dataF;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public void setAirportF(String airportF) {
        this.airportF = airportF;
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
