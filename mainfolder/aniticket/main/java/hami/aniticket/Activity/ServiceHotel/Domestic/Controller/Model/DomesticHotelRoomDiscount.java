package hami.hamibelit.Activity.ServiceHotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-02-10.
 */

public class DomesticHotelRoomDiscount implements Parcelable{
    @SerializedName("sales")
    private String sales;
    @SerializedName("webservice")
    private String webservice;
    //-----------------------------------------------

    public DomesticHotelRoomDiscount() {
    }
    //-----------------------------------------------

    protected DomesticHotelRoomDiscount(Parcel in) {
        sales = in.readString();
        webservice = in.readString();
    }

    public static final Creator<DomesticHotelRoomDiscount> CREATOR = new Creator<DomesticHotelRoomDiscount>() {
        @Override
        public DomesticHotelRoomDiscount createFromParcel(Parcel in) {
            return new DomesticHotelRoomDiscount(in);
        }

        @Override
        public DomesticHotelRoomDiscount[] newArray(int size) {
            return new DomesticHotelRoomDiscount[size];
        }
    };

    public String getSales() {
        return sales;
    }

    public String getWebservice() {
        return webservice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sales);
        dest.writeString(webservice);
    }
}
