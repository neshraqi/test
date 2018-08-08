package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DomesticFlight implements Parcelable {

    @SerializedName("adultPrice")
    private String adultPrice;
    @SerializedName("adultPrice1")
    private String adultPrice1;
    @SerializedName("airelineName")
    private String aireLineName;
    @SerializedName("airelineNameF")
    private String aireLineNameF;
    @SerializedName("airlineCode")
    private int airlineCode;
    @SerializedName("arriveTime")
    private String arriveTime;
    @SerializedName("discountprice")
    private String discountprice;
    @SerializedName("childPrice")
    private String childPrice;
    @SerializedName("flightName")
    private String flightName;
    @SerializedName("flightNumber")
    private String flightNumber;
    @SerializedName("flightTime")
    private String flightTime;
    @SerializedName("international")
    private int international;
    @SerializedName("from")
    private String from;
    @SerializedName("id")
    private String id;
    @SerializedName("infantPrice")
    private String infantPrice;
    @SerializedName("legs")
    private String legs;
    @SerializedName("nexday")
    private String nexDay;
    @SerializedName("noe")
    private String noe;
    @SerializedName("noeF")
    private String noeF;
    @SerializedName("num")
    private String num;
    @SerializedName("preday")
    private String preDay;
    @SerializedName("price")
    private String price;
    @SerializedName("shownum")
    private String showNum;
    @SerializedName("status")
    private int status;
    @SerializedName("stops")
    private String stops;
    @SerializedName("takeoffTime")
    private String takeoffTime;
    @SerializedName("test")
    private List<TestFlight> testList;
    @SerializedName("time")
    private String time;
    @SerializedName("to")
    private String to;
    @SerializedName("type")
    private String type;
    @SerializedName("daytime")
    private String daytime;

    //-----------------------------------------------


    public DomesticFlight() {
    }
    //-----------------------------------------------

    protected DomesticFlight(Parcel in) {
        adultPrice = in.readString();
        adultPrice1 = in.readString();
        aireLineName = in.readString();
        aireLineNameF = in.readString();
        airlineCode = in.readInt();
        arriveTime = in.readString();
        childPrice = in.readString();
        flightName = in.readString();
        flightNumber = in.readString();
        flightTime = in.readString();
        from = in.readString();
        id = in.readString();
        infantPrice = in.readString();
        legs = in.readString();
        nexDay = in.readString();
        noe = in.readString();
        noeF = in.readString();
        num = in.readString();
        preDay = in.readString();
        price = in.readString();
        showNum = in.readString();
        status = in.readInt();
        stops = in.readString();
        takeoffTime = in.readString();
        time = in.readString();
        to = in.readString();
        type = in.readString();
        daytime = in.readString();
        discountprice = in.readString();
        international = in.readInt();
    }

    public static final Creator<DomesticFlight> CREATOR = new Creator<DomesticFlight>() {
        @Override
        public DomesticFlight createFromParcel(Parcel in) {
            return new DomesticFlight(in);
        }

        @Override
        public DomesticFlight[] newArray(int size) {
            return new DomesticFlight[size];
        }
    };

    public String getAdultPrice1() {
        return adultPrice1;
    }

    public String getDaytime() {
        return daytime;
    }

    public String getAdultPrice() {
        return adultPrice;
    }

    public String getAireLineName() {
        return aireLineName;
    }

    public String getAireLineNameF() {
        return aireLineNameF;
    }

    public int getAirlineCode() {
        return airlineCode;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public String getChildPrice() {
        return childPrice;
    }

    public int getInternational() {
        return international;
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

    public String getFrom() {
        return from;
    }

    public String getId() {
        return id;
    }

    public String getInfantPrice() {
        return infantPrice;
    }

    public String getLegs() {
        return legs;
    }

    public String getNexDay() {
        return nexDay;
    }

    public String getNoe() {
        return noe;
    }

    public String getNoeF() {
        return noeF;
    }

    public String getNum() {
        return num;
    }

    public String getPreDay() {
        return preDay;
    }

    public String getPrice() {
        return price;
    }

    public String getShowNum() {
        return showNum;
    }

    public int getStatus() {
        return status;
    }

    public String getStops() {
        return stops;
    }

    public String getTakeoffTime() {
        return takeoffTime;
    }

    public String getTime() {
        return time;
    }

    public String getTo() {
        return to;
    }

    public String getType() {
        return type;
    }

    public String getDiscountPrice() {
        return discountprice;
    }

    public void setDaytime(String daytime) {
        this.daytime = daytime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adultPrice);
        dest.writeString(adultPrice1);
        dest.writeString(aireLineName);
        dest.writeString(aireLineNameF);
        dest.writeInt(airlineCode);
        dest.writeString(arriveTime);
        dest.writeString(childPrice);
        dest.writeString(flightName);
        dest.writeString(flightNumber);
        dest.writeString(flightTime);
        dest.writeString(from);
        dest.writeString(id);
        dest.writeString(infantPrice);
        dest.writeString(legs);
        dest.writeString(nexDay);
        dest.writeString(noe);
        dest.writeString(noeF);
        dest.writeString(num);
        dest.writeString(preDay);
        dest.writeString(price);
        dest.writeString(showNum);
        dest.writeInt(status);
        dest.writeString(stops);
        dest.writeString(takeoffTime);
        dest.writeString(time);
        dest.writeString(to);
        dest.writeString(type);
        dest.writeString(daytime);
        dest.writeString(discountprice);
        dest.writeInt(international);
    }


    //-----------------------------------------------
}


