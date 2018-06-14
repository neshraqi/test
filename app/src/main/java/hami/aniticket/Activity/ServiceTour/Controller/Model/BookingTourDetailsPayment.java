package hami.aniticket.Activity.ServiceTour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-03-11.
 */

public class BookingTourDetailsPayment implements Parcelable{
    @SerializedName("bank")
    private String bank;
    @SerializedName("fbank")
    private String fbank;
    //-----------------------------------------------

    public BookingTourDetailsPayment() {
    }
    //-----------------------------------------------

    protected BookingTourDetailsPayment(Parcel in) {
        bank = in.readString();
        fbank = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bank);
        dest.writeString(fbank);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingTourDetailsPayment> CREATOR = new Creator<BookingTourDetailsPayment>() {
        @Override
        public BookingTourDetailsPayment createFromParcel(Parcel in) {
            return new BookingTourDetailsPayment(in);
        }

        @Override
        public BookingTourDetailsPayment[] newArray(int size) {
            return new BookingTourDetailsPayment[size];
        }
    };

    public String getBank() {
        return bank;
    }

    public String getFbank() {
        return fbank;
    }
}
