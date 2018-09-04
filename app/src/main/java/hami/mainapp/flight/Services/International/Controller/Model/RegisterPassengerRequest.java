package hami.mainapp.flight.Services.International.Controller.Model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renjer on 1/19/2017.
 */

public class RegisterPassengerRequest {
    @SerializedName("idsearch")
    private String idSearch;

    @SerializedName("id")
    private List<String> id = new ArrayList<>();

    @SerializedName("gender")
    private List<String> gender = new ArrayList<>();

    @SerializedName("nid")
    private List<String> nid = new ArrayList<>();

    @SerializedName("birthday")
    private List<String> birthday = new ArrayList<>();

    @SerializedName("name")
    private List<String> name = new ArrayList<>();

    @SerializedName("family")
    private List<String> family = new ArrayList<>();

    @SerializedName("namep")
    private List<String> namePersian = new ArrayList<>();

    @SerializedName("familyp")
    private List<String> familyPersian = new ArrayList<>();

    @SerializedName("expdate")
    private List<String> expdate = new ArrayList<>();

    @SerializedName("passport_number")
    private List<String> passport_number = new ArrayList<>();

    @SerializedName("cou_nationality")
    private List<String> exportingCountry = new ArrayList<>();

    @SerializedName("nationalitycode")
    private List<String> nationalitycode = new ArrayList<>();

    @SerializedName("typep")
    private List<String> typep = new ArrayList<>();


    @SerializedName("cellphone")
    private String cellPhone;

    @SerializedName("email")
    private String email;

    @SerializedName("seccode")
    private String secCode;

    @SerializedName("numberp")
    private String numberP;

    @SerializedName("airtype")
    private String airType;

    @SerializedName("type")
    private String type = "سیستمی";

    @SerializedName("class")
    private String class_;

    @SerializedName("from")
    private String from;

    @SerializedName("to")
    private String to;

    @SerializedName("flight_number")
    private String flightNumber;

    @SerializedName("date")
    private String date;
    //-----------------------------------------------

    public RegisterPassengerRequest() {

    }
    //-----------------------------------------------

    //-----------------------------------------------
    public RegisterPassengerRequest(List<PassengerInfo> passengerInfoList) {
        for (PassengerInfo passengerInfo : passengerInfoList) {
            this.gender.add(passengerInfo.getGender());
            this.nid.add(passengerInfo.getNid());
            this.birthday.add(passengerInfo.getBirthday());
            this.name.add(passengerInfo.getName());
            this.family.add(passengerInfo.getFamily());
            this.namePersian.add(passengerInfo.getNamePersian());
            this.familyPersian.add(passengerInfo.getFamilyPersian());
            this.expdate.add(passengerInfo.getExpDate());
            this.passport_number.add(passengerInfo.getPassportNumber());
            this.exportingCountry.add(passengerInfo.getExportingCountryCode());
            this.nationalitycode.add(passengerInfo.getNationalityCountryCode());
            this.typep.add(passengerInfo.getTypep());
        }

    }

    //-----------------------------------------------
    public RegisterPassengerRequest(PassengerInfo passengerInfo) {
        this.gender.add(passengerInfo.getGender());
        this.nid.add(passengerInfo.getNid());
        this.birthday.add(passengerInfo.getBirthday());
        this.name.add(passengerInfo.getName());
        this.family.add(passengerInfo.getFamily());
        this.expdate.add(passengerInfo.getExpDate());
        this.passport_number.add(passengerInfo.getPassportNumber());
        this.exportingCountry.add(passengerInfo.getExportingCountry());
        this.nationalitycode.add(passengerInfo.getNationalityCountryCode());
        this.typep.add(passengerInfo.getTypep());
    }

    //-----------------------------------------------
    public void setIdWent(String id) {
        this.id.add(id);
    }

    public void setIdReturn(String id) {
        this.id.add(id);
    }
    //-----------------------------------------------
    public void setIdSearch(String idSearch) {
        this.idSearch = idSearch;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public void setNumberP(String numberP) {
        this.numberP = numberP;
    }

    public void setAirType(String airType) {
        this.airType = airType;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
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
