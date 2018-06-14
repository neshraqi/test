package hami.hamibelit.Activity.ServiceHotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-01-17.
 */

public class HotelDetailResponse implements Parcelable {
    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private HotelDetailData hotelDetailData;
    //-----------------------------------------------


    public HotelDetailResponse() {
    }
    //-----------------------------------------------

    protected HotelDetailResponse(Parcel in) {
        code = in.readInt();
        hotelDetailData = in.readParcelable(HotelDetailData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeParcelable(hotelDetailData, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotelDetailResponse> CREATOR = new Creator<HotelDetailResponse>() {
        @Override
        public HotelDetailResponse createFromParcel(Parcel in) {
            return new HotelDetailResponse(in);
        }

        @Override
        public HotelDetailResponse[] newArray(int size) {
            return new HotelDetailResponse[size];
        }
    };

    public int getCode() {
        return code;
    }

    public HotelDetailData getHotelDetailData() {
        return hotelDetailData;
    }
}
