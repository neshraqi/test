package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-01-07.
 */

public class ResultRegisterFlightInternationalResponse  implements Parcelable{
    @SerializedName("airline")
    private String airline;
    @SerializedName("date")
    private String date;
    @SerializedName("tdate")
    private String tdate;
    @SerializedName("class")
    private String _class;
    @SerializedName("flight_number")
    private String flight_number;
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;
    @SerializedName("finalprice")
    private String finalprice;
    //-----------------------------------------------

    public ResultRegisterFlightInternationalResponse() {
    }
    //-----------------------------------------------

    protected ResultRegisterFlightInternationalResponse(Parcel in) {
        airline = in.readString();
        date = in.readString();
        tdate = in.readString();
        _class = in.readString();
        flight_number = in.readString();
        from = in.readString();
        to = in.readString();
        finalprice = in.readString();
    }

    public static final Creator<ResultRegisterFlightInternationalResponse> CREATOR = new Creator<ResultRegisterFlightInternationalResponse>() {
        @Override
        public ResultRegisterFlightInternationalResponse createFromParcel(Parcel in) {
            return new ResultRegisterFlightInternationalResponse(in);
        }

        @Override
        public ResultRegisterFlightInternationalResponse[] newArray(int size) {
            return new ResultRegisterFlightInternationalResponse[size];
        }
    };

    public String getAirline() {
        return airline;
    }

    public String getDate() {
        return date;
    }

    public String getTdate() {
        return tdate;
    }

    public String get_class() {
        return _class;
    }

    public String getFlight_number() {
        return flight_number;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getFinalprice() {
        return finalprice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(airline);
        dest.writeString(date);
        dest.writeString(tdate);
        dest.writeString(_class);
        dest.writeString(flight_number);
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(finalprice);
    }
}
