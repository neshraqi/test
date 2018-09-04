package hami.mainapp.train.Services.Controller.Model;

import com.google.gson.annotations.SerializedName;
import com.hami.common.Const.TrainRules;


import java.util.ArrayList;
import java.util.List;

import hami.mainapp.R;


/**
 * Created by renjer on 2017-03-05.
 */

public class ListModelPassengerInfoTrain {
    @SerializedName("code")
    private List<String> code = new ArrayList<>();

    @SerializedName("birthday")
    private List<String> birthday = new ArrayList<>();

    @SerializedName("meliat")
    private List<String> meliat = new ArrayList<>();

    @SerializedName("typeage")
    private List<String> typeAge = new ArrayList<>();

    @SerializedName("persianFirstName")
    private List<String> persianFirstName = new ArrayList<>();

    @SerializedName("persianLastName")
    private List<String> persianLastName = new ArrayList<>();

    @SerializedName("pasport")
    private List<String> passportNumber = new ArrayList<>();

    @SerializedName("passengerType")
    private List<String> passengerType = new ArrayList<>();

    //-----------------------------------------------
    public ListModelPassengerInfoTrain(PassengerInfoTrain passengerInfo) {
        code.add(passengerInfo.getCode());
        birthday.add(passengerInfo.getBirthday());
        meliat.add(passengerInfo.getMeliat());
        typeAge.add(passengerInfo.getTypeAge());
        persianFirstName.add(passengerInfo.getPersianFirstName());
        persianLastName.add(passengerInfo.getPersianLastName());
        passportNumber.add(passengerInfo.getPassPort());
        passengerType.add(passengerInfo.getPassengerType());
    }

    //-----------------------------------------------
    public ListModelPassengerInfoTrain(List<PassengerInfoTrain> passengerInfoList) {
        for (PassengerInfoTrain passengerInfo : passengerInfoList) {
            code.add(passengerInfo.getCode());
            birthday.add(passengerInfo.getBirthday());
            meliat.add(passengerInfo.getMeliat());
            persianFirstName.add(passengerInfo.getPersianFirstName());
            typeAge.add(passengerInfo.getTypeAge());
            persianLastName.add(passengerInfo.getPersianLastName());
            passportNumber.add(passengerInfo.getPassPort());
            passengerType.add(passengerInfo.getPassengerType());
        }
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

    public List<String> getTypeAge() {
        return typeAge;
    }

    public List<String> getPersianFirstName() {
        return persianFirstName;
    }

    public int getPassengerTypeReSource() {
        int typePassengerApp = Integer.valueOf(passengerType.get(0));
        if (typePassengerApp == TrainRules.TP_ADULTS) {
            return R.string.adults;
        } else if (typePassengerApp == TrainRules.TP_CHILD) {
            return R.string.children;
        } else if (typePassengerApp == TrainRules.TP_SHAHED) {
            return R.string.shahed;
        } else {
            return R.string.veteran;
        }
    }

    public List<String> getPersianLastName() {
        return persianLastName;
    }

    public List<String> getPassportNumber() {
        return passportNumber;
    }

    public List<String> getPassengerType() {
        return passengerType;
    }
}
