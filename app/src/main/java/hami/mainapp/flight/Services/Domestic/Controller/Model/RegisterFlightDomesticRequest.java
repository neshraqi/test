package hami.mainapp.flight.Services.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renjer on 1/19/2017.
 */

public class RegisterFlightDomesticRequest implements Parcelable {

    @SerializedName("persons")
    private List<ListModelPassengerInfoDomestic> passengerInfoDomestics = new ArrayList<>();

    @SerializedName("phonnumber")
    private String phonNumber;

    @SerializedName("email")
    private String email;

    @SerializedName("captcha-flight")
    private String captchaFlight;

    @SerializedName("numberp")
    private String numberP;

    @SerializedName("id")
    private String id;

    @SerializedName("price")
    private String price;

    @SerializedName("num")
    private String num;

    @SerializedName("shownum")
    private String shownum;

    @SerializedName("adultPrice")
    private String adultPrice;

    @SerializedName("childPrice")
    private String childPrice;

    @SerializedName("infantPrice")
    private String infantPrice;

    @SerializedName("airelineName")
    private String airelineName;

    @SerializedName("legs")
    private String legs;

    @SerializedName("flightTime")
    private String flightTime;

    @SerializedName("from")
    private String from;

    @SerializedName("international")
    private int international;

    @SerializedName("to")
    private String to;

    @SerializedName("flightNumber")
    private String flightNumber;

    @SerializedName("flightName")
    private String flightName;

    @SerializedName("takeoffTime")
    private String takeoffTime;

    @SerializedName("arriveTime")
    private String arriveTime;

    @SerializedName("stops")
    private String stops;

    @SerializedName("type")
    private String type;

    @SerializedName("time")
    private String time;

    @SerializedName("noe")
    private String noe;

    @SerializedName("nexday")
    private String nexday;

    @SerializedName("preday")
    private String preday;

    @SerializedName("status")
    private String status;

    @SerializedName("airlineCode")
    private String airlineCode;

    @SerializedName("airelineNameF")
    private String airelineNameF;

    @SerializedName("noeF")
    private String noeF;

    @SerializedName("test")
    private String test;

    @SerializedName("daytime")
    private String daytime;

    @SerializedName("daytimetext")
    private String daytimetext;

    @SerializedName("count")
    private String count = "6";

    private String takeOffDatePersian;

    //-----------------------------------------------
    public RegisterFlightDomesticRequest() {
    }

    protected RegisterFlightDomesticRequest(Parcel in) {
        phonNumber = in.readString();
        email = in.readString();
        captchaFlight = in.readString();
        numberP = in.readString();
        id = in.readString();
        price = in.readString();
        num = in.readString();
        shownum = in.readString();
        adultPrice = in.readString();
        childPrice = in.readString();
        infantPrice = in.readString();
        airelineName = in.readString();
        legs = in.readString();
        flightTime = in.readString();
        from = in.readString();
        to = in.readString();
        flightNumber = in.readString();
        flightName = in.readString();
        takeoffTime = in.readString();
        arriveTime = in.readString();
        international = in.readInt();
        stops = in.readString();
        type = in.readString();
        time = in.readString();
        noe = in.readString();
        nexday = in.readString();
        preday = in.readString();
        status = in.readString();
        airlineCode = in.readString();
        airelineNameF = in.readString();
        noeF = in.readString();
        test = in.readString();
        daytime = in.readString();
        daytimetext = in.readString();
        count = in.readString();
        takeOffDatePersian = in.readString();
    }

    public static final Creator<RegisterFlightDomesticRequest> CREATOR = new Creator<RegisterFlightDomesticRequest>() {
        @Override
        public RegisterFlightDomesticRequest createFromParcel(Parcel in) {
            return new RegisterFlightDomesticRequest(in);
        }

        @Override
        public RegisterFlightDomesticRequest[] newArray(int size) {
            return new RegisterFlightDomesticRequest[size];
        }
    };

    //-----------------------------------------------
    public void addListModelPassengerInfoDomestic(ListModelPassengerInfoDomestic passengerInfoDomestics) {
        this.passengerInfoDomestics.add(passengerInfoDomestics);
    }

    ////-----------------------------------------------
    public void setPassengerInfoDomestics(List<ListModelPassengerInfoDomestic> passengerInfoDomestics) {
        this.passengerInfoDomestics = passengerInfoDomestics;
    }

    public List<ListModelPassengerInfoDomestic> getPassengerInfoDomestics() {
        return passengerInfoDomestics;
    }

    public void setPhonNumber(String phonNumber) {
        this.phonNumber = phonNumber;
    }

    public void setTakeOffDatePersian(String takeOffDatePersian) {
        this.takeOffDatePersian = takeOffDatePersian;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCaptchaFlight(String captchaFlight) {
        this.captchaFlight = captchaFlight;
    }

    public void setNumberP(String numberP) {
        this.numberP = numberP;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setShownum(String shownum) {
        this.shownum = shownum;
    }

    public void setAdultPrice(String adultPrice) {
        this.adultPrice = adultPrice;
    }

    public void setInternational(int international) {
        this.international = international;
    }

    public void setChildPrice(String childPrice) {
        this.childPrice = childPrice;
    }

    public void setInfantPrice(String infantPrice) {
        this.infantPrice = infantPrice;
    }

    public void setAirelineName(String airelineName) {
        this.airelineName = airelineName;
    }

    public void setLegs(String legs) {
        this.legs = legs;
    }

    public void setFlightTime(String flightTime) {
        this.flightTime = flightTime;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public void setTakeoffTime(String takeoffTime) {
        this.takeoffTime = takeoffTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public void setStops(String stops) {
        this.stops = stops;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNoe(String noe) {
        this.noe = noe;
    }

    public void setNexday(String nexday) {
        this.nexday = nexday;
    }

    public void setPreday(String preday) {
        this.preday = preday;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public void setAirelineNameF(String airelineNameF) {
        this.airelineNameF = airelineNameF;
    }

    public void setNoeF(String noeF) {
        this.noeF = noeF;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public void setDaytime(String daytime) {
        this.daytime = daytime;
    }

    public void setDaytimetext(String daytimetext) {
        this.daytimetext = daytimetext;
    }

    public String getTakeOffDatePersian() {
        return takeOffDatePersian;
    }

    public void setCount(String count) {
        //this.count = count;
        this.count = "6";
    }
    //-----------------------------------------------

    public String getPhonNumber() {
        return phonNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getCaptchaFlight() {
        return captchaFlight;
    }

    public String getNumberP() {
        return numberP;
    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String getNum() {
        return num;
    }

    public String getShownum() {
        return shownum;
    }

    public String getAdultPrice() {
        return adultPrice;
    }

    public String getChildPrice() {
        return childPrice;
    }

    public String getInfantPrice() {
        return infantPrice;
    }

    public String getAirelineName() {
        return airelineName;
    }

    public String getLegs() {
        return legs;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public int getInternational() {
        return international;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getFlightName() {
        return flightName;
    }

    public String getTakeoffTime() {
        return takeoffTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public String getStops() {
        return stops;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getNoe() {
        return noe;
    }

    public String getNexday() {
        return nexday;
    }

    public String getPreday() {
        return preday;
    }

    public String getStatus() {
        return status;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getAirelineNameF() {
        return airelineNameF;
    }

    public String getNoeF() {
        return noeF;
    }

    public String getTest() {
        return test;
    }

    public String getDaytime() {
        return daytime;
    }

    public String getDaytimetext() {
        return daytimetext;
    }

    public String getCount() {
        return count;
    }

    //-----------------------------------------------
    @Override
    public String toString() {
        try {
            Exclude ex = new Exclude();
            Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
            String jsonString = gson.toJson(this);
            return jsonString;
        } catch (Exception e) {
            e.getMessage();
        }
        return "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phonNumber);
        dest.writeString(email);
        dest.writeString(captchaFlight);
        dest.writeString(numberP);
        dest.writeString(id);
        dest.writeString(price);
        dest.writeString(num);
        dest.writeString(shownum);
        dest.writeString(adultPrice);
        dest.writeString(childPrice);
        dest.writeString(infantPrice);
        dest.writeString(airelineName);
        dest.writeString(legs);
        dest.writeString(flightTime);
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(flightNumber);
        dest.writeString(flightName);
        dest.writeString(takeoffTime);
        dest.writeString(arriveTime);
        dest.writeString(stops);
        dest.writeString(type);
        dest.writeString(time);
        dest.writeString(noe);
        dest.writeString(nexday);
        dest.writeString(preday);
        dest.writeString(status);
        dest.writeString(airlineCode);
        dest.writeString(airelineNameF);
        dest.writeString(noeF);
        dest.writeString(test);
        dest.writeString(daytime);
        dest.writeString(daytimetext);
        dest.writeString(count);
        dest.writeString(takeOffDatePersian);
    }
    //-----------------------------------------------

    class Exclude implements ExclusionStrategy {

        @Override
        public boolean shouldSkipClass(Class<?> arg0) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            SerializedName ns = field.getAnnotation(SerializedName.class);
            if (ns != null)
                return false;
            return true;
        }
    }
}
