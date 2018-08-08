package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2017-11-02.
 */

public class AllFlightInternationalIati implements Parcelable {
    public final static int TYPE_WENT = 2;
    public final static String PRICE_TYPE_PACKAGED = "PACKAGED";
    public final static String PRICE_TYPE_FREE_FORM = "FREE_FORM";

    @SerializedName("id")
    private int id;
    @SerializedName("providerKey")
    private String providerKey;
    @SerializedName("pricingType")
    private String pricingType;
    @SerializedName("packageId")
    private int packageId;
    @SerializedName("legs")
    private ArrayList<LegsIati> legsList;
    @SerializedName("returnFlight")
    private Boolean returnFlight;
    @SerializedName("noe")
    private int noe;
    @SerializedName("stops")
    private int stops;
    @SerializedName("airline")
    private String airline;
    @SerializedName("Class")
    private String class_;
    @SerializedName("flightTime")
    private String flightTime;
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


    public int getId() {
        return id;
    }

    public String getProviderKey() {
        return providerKey;
    }

    public String getPricingType() {
        return pricingType;
    }

    public int getPackageId() {
        return packageId;
    }

    public ArrayList<LegsIati> getLegsList() {
        return legsList;
    }

    public Boolean getReturnFlight() {
        return returnFlight;
    }

    public int getNoe() {
        return noe;
    }

    public int getStops() {
        return stops;
    }

    public String getAirline() {
        return airline;
    }


    public String getClass_() {
        return class_;
    }

    public String getFlightTime() {
        return flightTime;
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

    public static Creator<AllFlightInternationalIati> getCREATOR() {
        return CREATOR;
    }

    public long getDiscountadultprice() {
        return discountadultprice;
    }

    //-----------------------------------------------
    protected AllFlightInternationalIati(Parcel in) {
        id = in.readInt();
        providerKey = in.readString();
        pricingType = in.readString();
        packageId = in.readInt();
        legsList = in.createTypedArrayList(LegsIati.CREATOR);
        noe = in.readInt();
        stops = in.readInt();
        airline = in.readString();
        class_ = in.readString();
        flightTime = in.readString();
        infantBaseFare = in.readString();
        infantPrice = in.readLong();
        childBaseFare = in.readString();
        childPrice = in.readLong();
        adultBaseFare = in.readString();
        adultPrice = in.readLong();
        discountadultprice = in.readLong();
    }

    public static final Creator<AllFlightInternationalIati> CREATOR = new Creator<AllFlightInternationalIati>() {
        @Override
        public AllFlightInternationalIati createFromParcel(Parcel in) {
            return new AllFlightInternationalIati(in);
        }

        @Override
        public AllFlightInternationalIati[] newArray(int size) {
            return new AllFlightInternationalIati[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(providerKey);
        dest.writeString(pricingType);
        dest.writeInt(packageId);
        dest.writeTypedList(legsList);
        dest.writeInt(noe);
        dest.writeInt(stops);
        dest.writeString(airline);
        dest.writeString(class_);
        dest.writeString(flightTime);
        dest.writeString(infantBaseFare);
        dest.writeLong(infantPrice);
        dest.writeString(childBaseFare);
        dest.writeLong(childPrice);
        dest.writeString(adultBaseFare);
        dest.writeLong(adultPrice);
        dest.writeLong(discountadultprice);
    }
}
