package hami.mainapp.hotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-01-10.
 */

public class InternationalHotelSearchRequest implements Parcelable {
    public final static String INTENT_HOTEL_SEARCH_REQUEST = "INTENT_HOTEL_SEARCH_REQUEST";
    private final static String TAG = "InternationalHotelSearchRequest";
    @SerializedName("checkin")
    private String checkin;
    @SerializedName("checkout")
    private String checkout;
    private String checkinLongDateString;
    private String checkoutLongDateString;
    @SerializedName("nationality")
    private String nationality;
    @SerializedName("rooms")
    private String rooms;
    @SerializedName("adult")
    private String adult;
    @SerializedName("adults")
    private ArrayList<String> adults = new ArrayList<>();
    @SerializedName("child")
    private String child;
    @SerializedName("childs")
    private ArrayList<String> childs = new ArrayList<>();
    @SerializedName("childage")
    private ArrayList<String> childage;
    @SerializedName("childages")
    private ArrayList<ArrayList<String>> childages;
    //    @SerializedName("childages")
//    private String childAgesJson;
    @SerializedName("lat")
    private String lat;
    @SerializedName("long")
    private String lng;
    @SerializedName("radius")
    private String radius;
    @SerializedName("hotelId")
    private String hotelId;
    @SerializedName("cityId")
    private String cityId;
    @SerializedName("cityName")
    private String cityName;
    @SerializedName("country")
    private String country;
    @SerializedName("countryId")
    private String countryId;

    //-----------------------------------------------


    public InternationalHotelSearchRequest() {
        lat = "";
        lng = "";
        radius = "0";
        checkin = "";
        checkout = "";
    }
    //-----------------------------------------------


    protected InternationalHotelSearchRequest(Parcel in) {
        checkin = in.readString();
        checkout = in.readString();
        checkinLongDateString = in.readString();
        checkoutLongDateString = in.readString();
        nationality = in.readString();
        rooms = in.readString();
        adult = in.readString();
        adults = in.createStringArrayList();
        child = in.readString();
        childs = in.createStringArrayList();
        childage = in.createStringArrayList();
        //childAgesJson = in.readString();
        lat = in.readString();
        lng = in.readString();
        radius = in.readString();
        hotelId = in.readString();
        cityId = in.readString();
        cityName = in.readString();
        country = in.readString();
        countryId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(checkin);
        dest.writeString(checkout);
        dest.writeString(checkinLongDateString);
        dest.writeString(checkoutLongDateString);
        dest.writeString(nationality);
        dest.writeString(rooms);
        dest.writeString(adult);
        dest.writeStringList(adults);
        dest.writeString(child);
        dest.writeStringList(childs);
        dest.writeStringList(childage);
        //dest.writeString(childAgesJson);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(radius);
        dest.writeString(hotelId);
        dest.writeString(cityId);
        dest.writeString(cityName);
        dest.writeString(country);
        dest.writeString(countryId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InternationalHotelSearchRequest> CREATOR = new Creator<InternationalHotelSearchRequest>() {
        @Override
        public InternationalHotelSearchRequest createFromParcel(Parcel in) {
            return new InternationalHotelSearchRequest(in);
        }

        @Override
        public InternationalHotelSearchRequest[] newArray(int size) {
            return new InternationalHotelSearchRequest[size];
        }
    };

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public String getNationality() {
        return nationality;
    }

    public String getRooms() {
        return rooms;
    }

    public String getAdult() {
        return adult;
    }

    public String getChild() {
        return child;
    }

    public ArrayList<String> getChildage() {
        return childage;
    }

//    public String getChildAgesJson() {
//        return childAgesJson;
//    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getRadius() {
        return radius;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCheckinLongDateString() {
        return checkinLongDateString;
    }

    public String getCheckoutLongDateString() {
        return checkoutLongDateString;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public ArrayList<ArrayList<String>> getChildages() {
        return childages;
    }

    public ArrayList<String> getAdults() {
        return adults;
    }

    public ArrayList<String> getChilds() {
        return childs;
    }

    public String getCountryId() {
        return countryId;
    }

    //-----------------------------------------------
    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public void setChild(String child) {
        this.child = child;
    }

//    public void setChildAgesJson(String childAgesJson) {
//        this.childAgesJson = childAgesJson;
//    }

    public void setChilds(ArrayList<String> childs) {
        this.childs = childs;
    }

    public void setChildages(ArrayList<ArrayList<String>> childages) {
        this.childages = childages;
    }

    public void setAdults(ArrayList<String> adults) {
        this.adults = adults;
    }

    public void setChildage(ArrayList<String> childage) {
        this.childage = childage;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCheckinLongDateString(String checkinLongDateString) {
        this.checkinLongDateString = checkinLongDateString;
    }

    public void setCheckoutLongDateString(String checkoutLongDateString) {
        this.checkoutLongDateString = checkoutLongDateString;
    }


    public void setCountryId(String countryId) {
        this.countryId = countryId;
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

    //-----------------------------------------------

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
    //-----------------------------------------------

}
