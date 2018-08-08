package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-11-02.
 */

public class LegsParto implements Parcelable {
    @SerializedName("DepartureDateTime")
    private String DepartureDateTime;
    @SerializedName("ArrivalDateTime")
    private String ArrivalDateTime;
    @SerializedName("StopQuantity")
    private int StopQuantity;
    @SerializedName("FlightNumber")
    private String FlightNumber;
    @SerializedName("ResBookDesigCode")
    private String ResBookDesigCode;
    @SerializedName("JourneyDuration")
    private String JourneyDuration;
    @SerializedName("DepartureAirportLocationCode")
    private String DepartureAirportLocationCode;
    @SerializedName("ArrivalAirportLocationCode")
    private String ArrivalAirportLocationCode;
    @SerializedName("MarketingAirlineCode")
    private String MarketingAirlineCode;
    @SerializedName("CabinClassCode")
    private int CabinClassCode;
    @SerializedName("OperatingAirline")
    private OperatingAirlineParto OperatingAirline;
    @SerializedName("IsCharter")
    private Boolean IsCharter;
    @SerializedName("capacity")
    private String capacity;
    //-----------------------------------------------

    public LegsParto() {
    }

    //-----------------------------------------------

    protected LegsParto(Parcel in) {
        DepartureDateTime = in.readString();
        ArrivalDateTime = in.readString();
        StopQuantity = in.readInt();
        FlightNumber = in.readString();
        ResBookDesigCode = in.readString();
        JourneyDuration = in.readString();
        DepartureAirportLocationCode = in.readString();
        ArrivalAirportLocationCode = in.readString();
        MarketingAirlineCode = in.readString();
        CabinClassCode = in.readInt();
        OperatingAirline = in.readParcelable(OperatingAirlineParto.class.getClassLoader());
        capacity = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DepartureDateTime);
        dest.writeString(ArrivalDateTime);
        dest.writeInt(StopQuantity);
        dest.writeString(FlightNumber);
        dest.writeString(ResBookDesigCode);
        dest.writeString(JourneyDuration);
        dest.writeString(DepartureAirportLocationCode);
        dest.writeString(ArrivalAirportLocationCode);
        dest.writeString(MarketingAirlineCode);
        dest.writeInt(CabinClassCode);
        //dest.writeParcelable(OperatingAirline, flags);
        dest.writeString(capacity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LegsParto> CREATOR = new Creator<LegsParto>() {
        @Override
        public LegsParto createFromParcel(Parcel in) {
            return new LegsParto(in);
        }

        @Override
        public LegsParto[] newArray(int size) {
            return new LegsParto[size];
        }
    };

    public String getDepartureDateTime() {
        return DepartureDateTime;
    }

    public String getArrivalDateTime() {
        return ArrivalDateTime;
    }

    public int getStopQuantity() {
        return StopQuantity;
    }

    public String getFlightNumber() {
        return FlightNumber;
    }

    public String getResBookDesigCode() {
        return ResBookDesigCode;
    }

    public String getJourneyDuration() {
        return JourneyDuration;
    }

    public String getDepartureAirportLocationCode() {
        return DepartureAirportLocationCode;
    }

    public String getArrivalAirportLocationCode() {
        return ArrivalAirportLocationCode;
    }

    public String getMarketingAirlineCode() {
        return MarketingAirlineCode;
    }

    public int getCabinClassCode() {
        return CabinClassCode;
    }

    public OperatingAirlineParto getOperatingAirline() {
        return OperatingAirline;
    }

    public Boolean getCharter() {
        return (IsCharter == null || !IsCharter) ? false : true;
    }

    public String getCapacity() {
        return capacity;
    }
}
