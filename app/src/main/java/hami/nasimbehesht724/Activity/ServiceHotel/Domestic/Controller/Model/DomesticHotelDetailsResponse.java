package hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class DomesticHotelDetailsResponse implements Parcelable {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("bookingProccess")
    private DomesticHotelBookingProcess domesticHotelBookingProcess;
    @SerializedName("detailInfo")
    private DomesticHotelDetailInfo domesticHotelDetailInfo;
    //-----------------------------------------------

    public DomesticHotelDetailsResponse() {
    }
    //-----------------------------------------------

    protected DomesticHotelDetailsResponse(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        domesticHotelBookingProcess = in.readParcelable(DomesticHotelBookingProcess.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeParcelable(domesticHotelBookingProcess, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DomesticHotelDetailsResponse> CREATOR = new Creator<DomesticHotelDetailsResponse>() {
        @Override
        public DomesticHotelDetailsResponse createFromParcel(Parcel in) {
            return new DomesticHotelDetailsResponse(in);
        }

        @Override
        public DomesticHotelDetailsResponse[] newArray(int size) {
            return new DomesticHotelDetailsResponse[size];
        }
    };

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public DomesticHotelBookingProcess getDomesticHotelBookingProcess() {
        return domesticHotelBookingProcess;
    }

    public DomesticHotelDetailInfo getDomesticHotelDetailInfo() {
        return domesticHotelDetailInfo;
    }
}
