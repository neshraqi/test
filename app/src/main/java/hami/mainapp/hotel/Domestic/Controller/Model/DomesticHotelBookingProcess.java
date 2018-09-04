package hami.mainapp.hotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-02-10.
 */

public class DomesticHotelBookingProcess implements Parcelable {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("bookingProccess")
    private DomesticHotelBookingProcessData bookingProcess;
    @SerializedName("search_id")
    private String search_id;
    //-----------------------------------------------

    public DomesticHotelBookingProcess() {
    }

    //-----------------------------------------------

    protected DomesticHotelBookingProcess(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        search_id = in.readString();
        bookingProcess = in.readParcelable(DomesticHotelBookingProcessData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeString(search_id);
        dest.writeParcelable(bookingProcess, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DomesticHotelBookingProcess> CREATOR = new Creator<DomesticHotelBookingProcess>() {
        @Override
        public DomesticHotelBookingProcess createFromParcel(Parcel in) {
            return new DomesticHotelBookingProcess(in);
        }

        @Override
        public DomesticHotelBookingProcess[] newArray(int size) {
            return new DomesticHotelBookingProcess[size];
        }
    };

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getSearchId() {
        return search_id;
    }

    public DomesticHotelBookingProcessData getBookingProcess() {
        return bookingProcess;
    }
}
