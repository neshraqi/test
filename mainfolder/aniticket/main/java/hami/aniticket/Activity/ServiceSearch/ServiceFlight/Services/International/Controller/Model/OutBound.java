package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 1/12/2017.
 */
public class OutBound implements Parcelable{

    public final static String KEY_OUTBOUND = "KEY_OUTBOUND";
    @SerializedName("id")
    private int id;
    @SerializedName("totalPrice")
    private String totalPrice;
    @SerializedName("adultPrice")
    private String adultPrice;
    @SerializedName("childPrice")
    private String childPrice;
    @SerializedName("infantPrice")
    private String infantPrice;
    @SerializedName("legs")
    private ArrayList<Legs> legs;
    @SerializedName("linkCode")
    private int[] linkCode;
    @SerializedName("arriveTime")
    private String arriveTime;
    @SerializedName("airelineName")
    private String aireLineName;
    @SerializedName("flightName")
    private String flightName;
    @SerializedName("flightNumber")
    private String flightNumber;
    @SerializedName("flightTime")
    private String flightTime;
    @SerializedName("waiting")
    private String[] waiting;
    @SerializedName("sumWaiting")
    private String sumWaiting;
    @SerializedName("noe")
    private int noe;
    @SerializedName("stops")
    private int stops;
    @SerializedName("type")
    private String type;
    @SerializedName("takeoffTime")
    private String takeoffTime;
    @SerializedName("fileName")
    private String fileName;
    //-----------------------------------------------

    public OutBound(int id, String totalPrice, String adultPrice, String childPrice, ArrayList<Legs> legs, String arriveTime, String aireLineName, String flightName, String flightNumber, String flightTime, String[] waiting, String sumWaiting, int noe, int stops, String type, String takeoffTime, String fileName) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.legs = legs;
        this.arriveTime = arriveTime;
        this.aireLineName = aireLineName;
        this.flightName = flightName;
        this.flightNumber = flightNumber;
        this.flightTime = flightTime;
        this.waiting = waiting;
        this.sumWaiting = sumWaiting;
        this.noe = noe;
        this.stops = stops;
        this.type = type;
        this.takeoffTime = takeoffTime;
        this.fileName = fileName;
    }

    //-----------------------------------------------
    public OutBound() {
    }
    //-----------------------------------------------


    protected OutBound(Parcel in) {
        id = in.readInt();
        totalPrice = in.readString();
        adultPrice = in.readString();
        childPrice = in.readString();
        infantPrice = in.readString();
        legs = in.createTypedArrayList(Legs.CREATOR);
        linkCode = in.createIntArray();
        arriveTime = in.readString();
        aireLineName = in.readString();
        flightName = in.readString();
        flightNumber = in.readString();
        flightTime = in.readString();
        waiting = in.createStringArray();
        sumWaiting = in.readString();
        noe = in.readInt();
        stops = in.readInt();
        type = in.readString();
        takeoffTime = in.readString();
        fileName = in.readString();
    }

    public static final Creator<OutBound> CREATOR = new Creator<OutBound>() {
        @Override
        public OutBound createFromParcel(Parcel in) {
            return new OutBound(in);
        }

        @Override
        public OutBound[] newArray(int size) {
            return new OutBound[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getAdultPrice() {
        return adultPrice;
    }

    public String getChildPrice() {
        return childPrice;
    }

    public ArrayList<Legs> getLegs() {
        return legs;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public String getAireLineName() {
        return aireLineName;
    }

    public String getFlightName() {
        return flightName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public String[] getWaiting() {
        return waiting;
    }

    public String getSumWaiting() {
        return sumWaiting;
    }

    public int getNoe() {
        return noe;
    }

    public int getStops() {
        return stops;
    }

    public String getType() {
        return type;
    }

    public String getTakeoffTime() {
        return takeoffTime;
    }

    public String getFileName() {
        return fileName;
    }

    public int[] getLinkCode() {
        return linkCode;
    }

    public String getInfantPrice() {
        return infantPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(totalPrice);
        dest.writeString(adultPrice);
        dest.writeString(childPrice);
        dest.writeString(infantPrice);
        dest.writeTypedList(legs);
        dest.writeIntArray(linkCode);
        dest.writeString(arriveTime);
        dest.writeString(aireLineName);
        dest.writeString(flightName);
        dest.writeString(flightNumber);
        dest.writeString(flightTime);
        dest.writeStringArray(waiting);
        dest.writeString(sumWaiting);
        dest.writeInt(noe);
        dest.writeInt(stops);
        dest.writeString(type);
        dest.writeString(takeoffTime);
        dest.writeString(fileName);
    }
}