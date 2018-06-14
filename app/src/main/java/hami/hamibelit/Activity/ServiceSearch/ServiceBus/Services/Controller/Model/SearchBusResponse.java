package hami.hamibelit.Activity.ServiceSearch.ServiceBus.Services.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-02-11.
 */

public class SearchBusResponse implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("token")
    private String token;
    @SerializedName("deparureDate")
    private String deparureDate;
    @SerializedName("deparureTime")
    private String deparureTime;
    @SerializedName("busType")
    private String busType;
    @SerializedName("source")
    private String source;
    @SerializedName("destination")
    private String destination;
    @SerializedName("capacity")
    private String capacity;
    @SerializedName("price")
    private String price;
    @SerializedName("pricefinal")
    private String pricefinal;
    @SerializedName("discountprice")
    private String discountprice;
    @SerializedName("vip")
    private String vip;
    @SerializedName("searchId")
    private String searchId;
    @SerializedName("company")
    private String company;
    @SerializedName("img")
    private String img;
    @SerializedName("daytime")
    private String daytime;
    @SerializedName("daytimetext")
    private String daytimetext;
    //-----------------------------------------------

    public SearchBusResponse() {
    }
    //-----------------------------------------------

    protected SearchBusResponse(Parcel in) {
        id = in.readString();
        token = in.readString();
        deparureDate = in.readString();
        deparureTime = in.readString();
        busType = in.readString();
        source = in.readString();
        destination = in.readString();
        capacity = in.readString();
        price = in.readString();
        pricefinal = in.readString();
        discountprice = in.readString();
        vip = in.readString();
        searchId = in.readString();
        company = in.readString();
        img = in.readString();
        daytime = in.readString();
        daytimetext = in.readString();
    }

    public static final Creator<SearchBusResponse> CREATOR = new Creator<SearchBusResponse>() {
        @Override
        public SearchBusResponse createFromParcel(Parcel in) {
            return new SearchBusResponse(in);
        }

        @Override
        public SearchBusResponse[] newArray(int size) {
            return new SearchBusResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getDeparureDate() {
        return deparureDate;
    }

    public String getBusType() {
        return busType;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getPrice() {
        return price;
    }

    public String getVip() {
        return vip;
    }

    public String getSearchId() {
        return searchId;
    }

    public String getImg() {
        return img;
    }

    public String getDeparureTime() {
        return deparureTime;
    }

    public String getCompany() {
        return company;
    }

    public String getDaytime() {
        return daytime;
    }

    public String getDaytimetext() {
        return daytimetext;
    }

    public String getPricefinal() {
        return pricefinal;
    }

    public String getDiscountprice() {
        return discountprice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(token);
        dest.writeString(deparureDate);
        dest.writeString(deparureTime);
        dest.writeString(busType);
        dest.writeString(source);
        dest.writeString(destination);
        dest.writeString(capacity);
        dest.writeString(price);
        dest.writeString(pricefinal);
        dest.writeString(discountprice);
        dest.writeString(vip);
        dest.writeString(searchId);
        dest.writeString(company);
        dest.writeString(img);
        dest.writeString(daytime);
        dest.writeString(daytimetext);
    }
}
