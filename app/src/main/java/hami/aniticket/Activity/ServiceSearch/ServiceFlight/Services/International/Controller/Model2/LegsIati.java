package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-11-02.
 */

public class LegsIati implements Parcelable {

    @SerializedName("flightNo")
    private String flightNo;
    @SerializedName("aircraft")
    private String aircraft;
    @SerializedName("carrierCode")
    private String carrierCode;
    @SerializedName("carrierName")
    private String carrierName;
    @SerializedName("operatorCode")
    private String operatorCode;
    @SerializedName("operatorName")
    private String operatorName;
    @SerializedName("validatingCarrierCode")
    private String validatingCarrierCode;
    @SerializedName("departureAirport")
    private String departureAirport;
    @SerializedName("localDepartureDate")
    private String localDepartureDate;
    @SerializedName("departureTime")
    private String departureTime;
    @SerializedName("departureAirportName")
    private String departureAirportName;
    @SerializedName("departureCityName")
    private String departureCityName;
    @SerializedName("arrivalAirport")
    private String arrivalAirport;
    @SerializedName("localArrivalDate")
    private String localArrivalDate;
    @SerializedName("arrivalTime")
    private String arrivalTime;
    @SerializedName("arrivalAirportName")
    private String arrivalAirportName;
    @SerializedName("arrivalCityName")
    private String arrivalCityName;
    @SerializedName("arrivalTerminal")
    private String arrivalTerminal;
    @SerializedName("numStops")
    private String numStops;
    @SerializedName("legDurationMinute")
    private int legDurationMinute;
    @SerializedName("waitTimeBeforeNextLeg")
    private int waitTimeBeforeNextLeg;
    @SerializedName("capacity")
    private String capacity;
    //-----------------------------------------------


    public LegsIati() {
    }
    //-----------------------------------------------

    public String getFlightNo() {
        return flightNo;
    }

    public String getAircraft() {
        return aircraft;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getValidatingCarrierCode() {
        return validatingCarrierCode;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getLocalDepartureDate() {
        return localDepartureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public String getDepartureCityName() {
        return departureCityName;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public String getLocalArrivalDate() {
        return localArrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public String getArrivalCityName() {
        return arrivalCityName;
    }

    public String getArrivalTerminal() {
        return arrivalTerminal;
    }

    public String getNumStops() {
        return numStops;
    }

    public int getLegDurationMinute() {
        return legDurationMinute;
    }

    public int getWaitTimeBeforeNextLeg() {
        return waitTimeBeforeNextLeg;
    }

    public String getCapacity() {
        return capacity;
    }

    public static Creator<LegsIati> getCREATOR() {
        return CREATOR;
    }

    //-----------------------------------------------
    protected LegsIati(Parcel in) {
        flightNo = in.readString();
        aircraft = in.readString();
        carrierCode = in.readString();
        carrierName = in.readString();
        operatorCode = in.readString();
        operatorName = in.readString();
        validatingCarrierCode = in.readString();
        departureAirport = in.readString();
        localDepartureDate = in.readString();
        departureTime = in.readString();
        departureAirportName = in.readString();
        departureCityName = in.readString();
        arrivalAirport = in.readString();
        localArrivalDate = in.readString();
        arrivalTime = in.readString();
        arrivalAirportName = in.readString();
        arrivalCityName = in.readString();
        arrivalTerminal = in.readString();
        numStops = in.readString();
        legDurationMinute = in.readInt();
        waitTimeBeforeNextLeg = in.readInt();
        capacity = in.readString();
    }

    public static final Creator<LegsIati> CREATOR = new Creator<LegsIati>() {
        @Override
        public LegsIati createFromParcel(Parcel in) {
            return new LegsIati(in);
        }

        @Override
        public LegsIati[] newArray(int size) {
            return new LegsIati[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(flightNo);
        dest.writeString(aircraft);
        dest.writeString(carrierCode);
        dest.writeString(carrierName);
        dest.writeString(operatorCode);
        dest.writeString(operatorName);
        dest.writeString(validatingCarrierCode);
        dest.writeString(departureAirport);
        dest.writeString(localDepartureDate);
        dest.writeString(departureTime);
        dest.writeString(departureAirportName);
        dest.writeString(departureCityName);
        dest.writeString(arrivalAirport);
        dest.writeString(localArrivalDate);
        dest.writeString(arrivalTime);
        dest.writeString(arrivalAirportName);
        dest.writeString(arrivalCityName);
        dest.writeString(arrivalTerminal);
        dest.writeString(numStops);
        dest.writeInt(legDurationMinute);
        dest.writeInt(waitTimeBeforeNextLeg);
        dest.writeString(capacity);
    }
}