package com.hami.servicetrain.Services.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-03-05.
 */

public class PassengerInfoTrain {
    @SerializedName("code")
    private String code;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("meliat")
    private String meliat;
    @SerializedName("pasport")
    private String passPort;
    @SerializedName("persianFirstName")
    private String persianFirstName;
    @SerializedName("persianLastName")
    private String persianLastName;
    @SerializedName("typeage")
    private String typeAge;
    @SerializedName("passengerType")
    private String passengerType;
    //-----------------------------------------------
    public PassengerInfoTrain(TrainPassengerInfo trainPassengerInfo){
        this.code = trainPassengerInfo.getNationalCode();
        this.birthday = trainPassengerInfo.getBirthDayPersian();
        this.meliat = String.valueOf(trainPassengerInfo.getNationalityType());
        this.passPort = trainPassengerInfo.getPassportNo();
        this.persianFirstName = trainPassengerInfo.getFirstNamePersian();
        this.persianLastName = trainPassengerInfo.getLastNamePersian();
        this.typeAge = String.valueOf(trainPassengerInfo.getTypePassenger());
        this.passengerType = String.valueOf("1");
    }
    //-----------------------------------------------

    public PassengerInfoTrain() {
    }

    //-----------------------------------------------


    public String getCode() {
        return code;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getMeliat() {
        return meliat;
    }

    public String getPassPort() {
        return passPort;
    }

    public String getPersianFirstName() {
        return persianFirstName;
    }

    public String getPersianLastName() {
        return persianLastName;
    }

    public String getTypeAge() {
        return typeAge;
    }

    public String getPassengerType() {
        return passengerType;
    }
}
