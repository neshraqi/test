package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 1/10/2017.
 */

public class FlightInternationalResponse implements Parcelable{

    @SerializedName("errorCode")
    private int errorCode;
    @SerializedName("searchId")
    private String searchId;
    @SerializedName("message")
    private String message;
    @SerializedName("flights")
    private FlightInternational flightInternational;


    //-----------------------------------------------


    public FlightInternationalResponse() {
    }
    //-----------------------------------------------

    protected FlightInternationalResponse(Parcel in) {
        errorCode = in.readInt();
        searchId = in.readString();
        message = in.readString();
    }

    public static final Creator<FlightInternationalResponse> CREATOR = new Creator<FlightInternationalResponse>() {
        @Override
        public FlightInternationalResponse createFromParcel(Parcel in) {
            return new FlightInternationalResponse(in);
        }

        @Override
        public FlightInternationalResponse[] newArray(int size) {
            return new FlightInternationalResponse[size];
        }
    };

    public int getErrorCode() {
        return errorCode;
    }

    public String getSearchId() {
        return searchId;
    }

    public String getMessage() {
        return message;
    }

    public FlightInternational getFlightInternational() {
        return flightInternational;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(errorCode);
        dest.writeString(searchId);
        dest.writeString(message);
    }
}
