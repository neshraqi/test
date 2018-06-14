package hami.aniticket.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model;

import hami.aniticket.Const.TrainRules;

/**
 * Created by renjer on 2017-02-25.
 */

public class TrainPassengerInfo {
    private int typePassenger;
    private int typePassengerApp;
    private String nationalCode;
    private String birthDayPersian;
    private String birthDayGregorian;
    private String firstNamePersian;
    private String lastNamePersian;
    private String passportNo;
    private int nationalityType;
    //-----------------------------------------------
    public final static int GENDER_MALE = 1;
    public final static int GENDER_FEMALE = 2;
    public final static int EXPORTING_COUNTRY_IRAN = 1;
    public final static int EXPORTING_COUNTRY_FOREIGN = 2;

    //-----------------------------------------------
    public static TrainPassengerInfo newInstanceIran(int typePassenger,int typePassengerApp, String birthDayPersian, String birthDayGregorian, String firstNamePersian, String lastNamePersian, String nationalCode) {
        TrainPassengerInfo domesticPassengerInfo = new TrainPassengerInfo();
        domesticPassengerInfo.birthDayPersian = birthDayPersian;
        domesticPassengerInfo.birthDayGregorian = birthDayGregorian;
        domesticPassengerInfo.firstNamePersian = firstNamePersian;
        domesticPassengerInfo.lastNamePersian = lastNamePersian;
        domesticPassengerInfo.nationalCode = nationalCode;
        domesticPassengerInfo.passportNo = "0";
        domesticPassengerInfo.nationalityType = TrainRules.PN_IRANIAN;
        domesticPassengerInfo.typePassenger = typePassenger;
        domesticPassengerInfo.typePassengerApp = typePassengerApp;
        return domesticPassengerInfo;
    }

    //-----------------------------------------------
    public static TrainPassengerInfo newInstanceForeign(int typePassenger,int typePassengerApp, String birthDayPersian, String birthDayGregorian, String firstNamePersian, String lastNamePersian, String passportNo) {
        TrainPassengerInfo domesticPassengerInfo = new TrainPassengerInfo();
        domesticPassengerInfo.birthDayPersian = birthDayPersian;
        domesticPassengerInfo.birthDayGregorian = birthDayGregorian;
        domesticPassengerInfo.firstNamePersian = firstNamePersian;
        domesticPassengerInfo.lastNamePersian = lastNamePersian;
        domesticPassengerInfo.nationalCode = "0";
        domesticPassengerInfo.passportNo = passportNo;
        domesticPassengerInfo.nationalityType = TrainRules.PN_FOREIGN;
        domesticPassengerInfo.typePassenger = typePassenger;
        domesticPassengerInfo.typePassengerApp = typePassengerApp;
        return domesticPassengerInfo;
    }
    //-----------------------------------------------

    public int getTypePassengerApp() {
        return typePassengerApp;
    }

    public int getTypePassenger() {
        return typePassenger;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public String getBirthDayPersian() {
        return birthDayPersian;
    }

    public String getBirthDayGregorian() {
        return birthDayGregorian;
    }

    public String getFirstNamePersian() {
        return firstNamePersian;
    }

    public String getLastNamePersian() {
        return lastNamePersian;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public int getNationalityType() {
        return nationalityType;
    }


    //-----------------------------------------------
}
