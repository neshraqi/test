package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import hami.hamibelit.Const.FlightRules;
import hami.hamibelit.R;

/**
 * Created by renjer on 2018-01-07.
 */

public class ResultRegisterPassengerFlightInternationalResponse implements Parcelable {
    @SerializedName("name")
    private String[] name;
    @SerializedName("family")
    private String[] family;
    @SerializedName("nid")
    private String[] nid;
    @SerializedName("gender")
    private String[] gender;
    @SerializedName("typep")
    private String[] typep;
    @SerializedName("nationalitycode")
    private String[] nationalitycode;
    @SerializedName("expdate")
    private String[] expdate;
    @SerializedName("birthday")
    private String[] birthday;
    @SerializedName("passport_number")
    private String[] passport_number;
    @SerializedName("finalprice")
    private String[] finalprice;
    //-----------------------------------------------

    public ResultRegisterPassengerFlightInternationalResponse() {
    }


    protected ResultRegisterPassengerFlightInternationalResponse(Parcel in) {
        name = in.createStringArray();
        family = in.createStringArray();
        nid = in.createStringArray();
        gender = in.createStringArray();
        typep = in.createStringArray();
        nationalitycode = in.createStringArray();
        expdate = in.createStringArray();
        birthday = in.createStringArray();
        passport_number = in.createStringArray();
        finalprice = in.createStringArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(name);
        dest.writeStringArray(family);
        dest.writeStringArray(nid);
        dest.writeStringArray(gender);
        dest.writeStringArray(typep);
        dest.writeStringArray(nationalitycode);
        dest.writeStringArray(expdate);
        dest.writeStringArray(birthday);
        dest.writeStringArray(passport_number);
        dest.writeStringArray(finalprice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResultRegisterPassengerFlightInternationalResponse> CREATOR = new Creator<ResultRegisterPassengerFlightInternationalResponse>() {
        @Override
        public ResultRegisterPassengerFlightInternationalResponse createFromParcel(Parcel in) {
            return new ResultRegisterPassengerFlightInternationalResponse(in);
        }

        @Override
        public ResultRegisterPassengerFlightInternationalResponse[] newArray(int size) {
            return new ResultRegisterPassengerFlightInternationalResponse[size];
        }
    };

    //-----------------------------------------------
    public String[] getName() {
        return name;
    }

    public String[] getFamily() {
        return family;
    }

    public String[] getNid() {
        return nid;
    }

    public String[] getGender() {
        return gender;
    }

    public String[] getTypep() {
        return typep;
    }

    public String[] getNationalitycode() {
        return nationalitycode;
    }

    public String[] getExpdate() {
        return expdate;
    }

    public String[] getBirthday() {
        return birthday;
    }

    public String[] getFinalprice() {
        return finalprice;
    }

    public String[] getPassport_number() {
        return passport_number;
    }

    public int getTypePassengerResource(int index) {
        switch (Integer.valueOf(typep[index])) {
            case FlightRules.TP_ADULTS:
                return R.string.pAdults;
            case FlightRules.TP_CHILDREN:
                return R.string.pChildren;
            case FlightRules.TP_INFANT:
                return R.string.pInfant;
        }
        return 0;
    }


}
