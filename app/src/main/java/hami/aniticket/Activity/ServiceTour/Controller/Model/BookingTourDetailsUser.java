package hami.aniticket.Activity.ServiceTour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-03-11.
 */

public class BookingTourDetailsUser implements Parcelable {
    @SerializedName("discount")
    private String discount;
    @SerializedName("wallet")
    private String wallet;
    @SerializedName("etebar")
    private String etebar;
    //-----------------------------------------------

    public BookingTourDetailsUser() {
    }
    //-----------------------------------------------

    protected BookingTourDetailsUser(Parcel in) {
        discount = in.readString();
        wallet = in.readString();
        etebar = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(discount);
        dest.writeString(wallet);
        dest.writeString(etebar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingTourDetailsUser> CREATOR = new Creator<BookingTourDetailsUser>() {
        @Override
        public BookingTourDetailsUser createFromParcel(Parcel in) {
            return new BookingTourDetailsUser(in);
        }

        @Override
        public BookingTourDetailsUser[] newArray(int size) {
            return new BookingTourDetailsUser[size];
        }
    };

    public String getDiscount() {
        return discount;
    }

    public String getWallet() {
        return wallet;
    }

    public String getEtebar() {
        return etebar;
    }
}
