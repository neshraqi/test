package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import hami.mainapp.Const.FlightRules;
import hami.mainapp.R;

/**
 * Created by renjer on 2017-03-05.
 */

public class ListModelPassengerInfoDomestic {
    @SerializedName("code")
    private List<String> code = new ArrayList<>();

    @SerializedName("birthday")
    private List<String> birthday = new ArrayList<>();

    @SerializedName("meliat")
    private List<String> meliat = new ArrayList<>();

    @SerializedName("gender")
    private List<String> gender = new ArrayList<>();

    @SerializedName("englishFirstName")
    private List<String> englishFirstName = new ArrayList<>();

    @SerializedName("englishLastName")
    private List<String> englishLastName = new ArrayList<>();

    @SerializedName("persianFirstName")
    private List<String> persianFirstName = new ArrayList<>();

    @SerializedName("persianLastName")
    private List<String> persianLastName = new ArrayList<>();

    @SerializedName("expdate")
    private List<String> expdate = new ArrayList<>();

    @SerializedName("passport_number")
    private List<String> passportNumber = new ArrayList<>();

    @SerializedName("nationalitycode")
    private List<String> nationalityCode = new ArrayList<>();

    @SerializedName("passengerType")
    private List<String> passengerType = new ArrayList<>();

    //-----------------------------------------------
    public ListModelPassengerInfoDomestic(PassengerInfoDomestic passengerInfo) {
        code.add(passengerInfo.getCode());
        birthday.add(passengerInfo.getBirthday());
        meliat.add(passengerInfo.getMeliat());
        gender.add(passengerInfo.getGender());
        englishFirstName.add(passengerInfo.getEnglishFirstName());
        englishLastName.add(passengerInfo.getEnglishLastName());
        persianFirstName.add(passengerInfo.getPersianFirstName());
        persianLastName.add(passengerInfo.getPersianLastName());
        expdate.add(passengerInfo.getExpDate());
        passportNumber.add(passengerInfo.getPassportNumber());
        nationalityCode.add(passengerInfo.getNationalityCode());
        passengerType.add(passengerInfo.getPassengerType());
    }

    //-----------------------------------------------
    public ListModelPassengerInfoDomestic(List<PassengerInfoDomestic> passengerInfoList) {
        for (PassengerInfoDomestic passengerInfo : passengerInfoList) {
            code.add(passengerInfo.getCode());
            birthday.add(passengerInfo.getBirthday());
            meliat.add(passengerInfo.getMeliat());
            gender.add(passengerInfo.getGender());
            englishFirstName.add(passengerInfo.getEnglishFirstName());
            englishLastName.add(passengerInfo.getEnglishLastName());
            persianFirstName.add(passengerInfo.getPersianFirstName());
            persianLastName.add(passengerInfo.getPersianLastName());
            expdate.add(passengerInfo.getExpDate());
            passportNumber.add(passengerInfo.getPassportNumber());
            passportNumber.add(passengerInfo.getNationalityCode());
            passengerType.add(passengerInfo.getPassengerType());
        }
    }

    //-----------------------------------------------
    public String getPriceByPassenger(int priceAdults, int priceChild, int priceInfant) {
        switch (Integer.valueOf(passengerType.get(0))) {
            case FlightRules.TP_ADULTS:
                return String.valueOf(priceAdults);
            case FlightRules.TP_CHILDREN:
                return String.valueOf(priceChild);
            case FlightRules.TP_INFANT:
                return String.valueOf(priceInfant);
        }
        return String.valueOf(priceAdults);
    }
    //-----------------------------------------------
    public String getPriceByPassenger(String priceAdults, String priceChild, String priceInfant) {
        switch (Integer.valueOf(passengerType.get(0))) {
            case FlightRules.TP_ADULTS:
                return priceAdults;
            case FlightRules.TP_CHILDREN:
                return priceChild;
            case FlightRules.TP_INFANT:
                return priceInfant;
        }
        return priceAdults;
    }

    //-----------------------------------------------
    public int getTypePassengerResource(Context context) {
        switch (Integer.valueOf(passengerType.get(0))) {
            case FlightRules.TP_ADULTS:
                return R.string.adults;
            case FlightRules.TP_CHILDREN:
                return R.string.children;
            case FlightRules.TP_INFANT:
                return R.string.infant;
        }
        return R.string.adults;
    }
    //-----------------------------------------------

    public List<String> getCode() {
        return code;
    }

    public List<String> getBirthday() {
        return birthday;
    }

    public List<String> getMeliat() {
        return meliat;
    }

    public List<String> getGender() {
        return gender;
    }

    public List<String> getEnglishFirstName() {
        return englishFirstName;
    }

    public List<String> getEnglishLastName() {
        return englishLastName;
    }

    public List<String> getPersianFirstName() {
        return persianFirstName;
    }

    public List<String> getPersianLastName() {
        return persianLastName;
    }

    public List<String> getExpdate() {
        return expdate;
    }

    public List<String> getPassportNumber() {
        return passportNumber;
    }

    public List<String> getNationalityCode() {
        return nationalityCode;
    }

    public List<String> getPassengerType() {
        return passengerType;
    }
}
