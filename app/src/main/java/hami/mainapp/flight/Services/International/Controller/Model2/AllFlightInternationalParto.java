package hami.mainapp.flight.Services.International.Controller.Model2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2017-11-02.
 */

public class AllFlightInternationalParto implements Parcelable {

    @SerializedName("id")
    private long id;
    @SerializedName("noe")
    private long noe;
    @SerializedName("infantBaseFare")
    private String infantBaseFare;
    @SerializedName("infantPrice")
    private long infantPrice;
    @SerializedName("childBaseFare")
    private String childBaseFare;
    @SerializedName("childPrice")
    private long childPrice;
    @SerializedName("adultBaseFare")
    private String adultBaseFare;
    @SerializedName("adultPrice")
    private long adultPrice;
    @SerializedName("discountadultprice")
    private long discountadultprice;
    @SerializedName("masir")
    private ArrayList<LegsResponseParto> legs;
    @SerializedName("outboundStops")
    private long outboundStops;
    @SerializedName("outboundAirline")
    private String outboundAirline;
    @SerializedName("returnStops")
    private long returnStops;
    @SerializedName("returnAirline")
    private String returnAirline;
    public final static int TYPE_WENT = 1;
    public final static int TYPE_WENT_AND_RETURN = 3;
    //-----------------------------------------------

    public AllFlightInternationalParto() {
    }
    //-----------------------------------------------

    public long getId() {
        return id;
    }

    public long getNoe() {
        return noe;
    }

    public String getInfantBaseFare() {
        return infantBaseFare;
    }

    public long getInfantPrice() {
        return infantPrice;
    }

    public String getChildBaseFare() {
        return childBaseFare;
    }

    public long getChildPrice() {
        return childPrice;
    }

    public String getAdultBaseFare() {
        return adultBaseFare;
    }

    public long getAdultPrice() {
        return adultPrice;
    }

    public ArrayList<LegsResponseParto> getLegs() {
        return legs;
    }

    public long getOutboundStops() {
        return outboundStops;
    }

    public String getOutboundAirline() {
        return outboundAirline;
    }

    public long getReturnStops() {
        return returnStops;
    }

    public String getReturnAirline() {
        return returnAirline;
    }

    public static Creator<AllFlightInternationalParto> getCREATOR() {
        return CREATOR;
    }

    public long getDiscountadultprice() {
        return discountadultprice;
    }

    //-----------------------------------------------
    protected AllFlightInternationalParto(Parcel in) {
        id = in.readLong();
        noe = in.readLong();
        infantBaseFare = in.readString();
        infantPrice = in.readLong();
        childBaseFare = in.readString();
        childPrice = in.readLong();
        adultBaseFare = in.readString();
        adultPrice = in.readLong();
        discountadultprice = in.readLong();
        legs = in.createTypedArrayList(LegsResponseParto.CREATOR);
        outboundStops = in.readLong();
        outboundAirline = in.readString();
        returnStops = in.readLong();
        returnAirline = in.readString();
    }

    public static final Creator<AllFlightInternationalParto> CREATOR = new Creator<AllFlightInternationalParto>() {
        @Override
        public AllFlightInternationalParto createFromParcel(Parcel in) {
            return new AllFlightInternationalParto(in);
        }

        @Override
        public AllFlightInternationalParto[] newArray(int size) {
            return new AllFlightInternationalParto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(noe);
        dest.writeString(infantBaseFare);
        dest.writeLong(infantPrice);
        dest.writeString(childBaseFare);
        dest.writeLong(childPrice);
        dest.writeString(adultBaseFare);
        dest.writeLong(adultPrice);
        dest.writeTypedList(legs);
        dest.writeLong(outboundStops);
        dest.writeLong(discountadultprice);
        dest.writeString(outboundAirline);
        dest.writeLong(returnStops);
        dest.writeString(returnAirline);
    }

    //-----------------------------------------------


}
