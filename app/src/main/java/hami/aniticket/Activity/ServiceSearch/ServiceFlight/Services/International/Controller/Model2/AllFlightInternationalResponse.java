package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2017-11-02.
 */

public class AllFlightInternationalResponse implements Parcelable {

    public final static int PARTO_FLIGHT = 1;
    public final static int IATI_FLIGHT = 2;
    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("searchId")
    private String searchId;

    @SerializedName("message")
    private String message;

    @SerializedName("flights1")
    private ArrayList<AllFlightInternationalParto> allFlightInternationalParto;

    @SerializedName("flights2")
    private ArrayList<AllFlightInternationalIati> allFlightInternationalIati;

    @SerializedName("flights3")
    private Object flight3;

    @SerializedName("Airlines")
    private Object jsonObjectAirlines;

    @SerializedName("Cities")
    private Object jsonObjectCities;
    //-----------------------------------------------

    public AllFlightInternationalResponse() {
    }

    //-----------------------------------------------

    //-----------------------------------------------

    protected AllFlightInternationalResponse(Parcel in) {
        errorCode = in.readInt();
        searchId = in.readString();
        message = in.readString();
        allFlightInternationalParto = in.createTypedArrayList(AllFlightInternationalParto.CREATOR);
        allFlightInternationalIati = in.createTypedArrayList(AllFlightInternationalIati.CREATOR);
    }

    public static final Creator<AllFlightInternationalResponse> CREATOR = new Creator<AllFlightInternationalResponse>() {
        @Override
        public AllFlightInternationalResponse createFromParcel(Parcel in) {
            return new AllFlightInternationalResponse(in);
        }

        @Override
        public AllFlightInternationalResponse[] newArray(int size) {
            return new AllFlightInternationalResponse[size];
        }
    };

    public Object getJsonObjectAirlines() {
        return jsonObjectAirlines;
    }

    public static int getPartoFlight() {
        return PARTO_FLIGHT;
    }

    public static int getIatiFlight() {
        return IATI_FLIGHT;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getSearchId() {
        return searchId;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<AllFlightInternationalIati> getAllFlightInternationalIati() {
        return allFlightInternationalIati;
    }

    public ArrayList<AllFlightInternationalParto> getAllFlightInternationalParto() {
        return allFlightInternationalParto;
    }

    public Object getJsonObjectCities() {
        return jsonObjectCities;
    }

    public Object getFlight3() {
        return flight3;
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
        dest.writeTypedList(allFlightInternationalParto);
        dest.writeTypedList(allFlightInternationalIati);
    }
}
