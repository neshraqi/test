package hami.nasimbehesht724.Activity.ServiceTour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-03-11.
 */

public class BookingTourDetails implements Parcelable {

    @SerializedName("ticket")
    private BookingTourDetailsTicket ticket;
    @SerializedName("passengers")
    private ArrayList<BookingTourDetailsPassenger> passengers;
    @SerializedName("user")
    private BookingTourDetailsUser user;
    @SerializedName("payment")
    private BookingTourDetailsPayment payment;
    @SerializedName("hashId")
    private String hashId;
    //-----------------------------------------------

    public BookingTourDetails() {
    }
    //-----------------------------------------------

    protected BookingTourDetails(Parcel in) {
        ticket = in.readParcelable(BookingTourDetailsTicket.class.getClassLoader());
        passengers = in.createTypedArrayList(BookingTourDetailsPassenger.CREATOR);
        user = in.readParcelable(BookingTourDetailsUser.class.getClassLoader());
        payment = in.readParcelable(BookingTourDetailsPayment.class.getClassLoader());
        hashId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(ticket, flags);
        dest.writeTypedList(passengers);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(payment, flags);
        dest.writeString(hashId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingTourDetails> CREATOR = new Creator<BookingTourDetails>() {
        @Override
        public BookingTourDetails createFromParcel(Parcel in) {
            return new BookingTourDetails(in);
        }

        @Override
        public BookingTourDetails[] newArray(int size) {
            return new BookingTourDetails[size];
        }
    };

    public BookingTourDetailsTicket getTicket() {
        return ticket;
    }

    public ArrayList<BookingTourDetailsPassenger> getPassengers() {
        return passengers;
    }

    public BookingTourDetailsUser getUser() {
        return user;
    }

    public BookingTourDetailsPayment getPayment() {
        return payment;
    }

    public String getHashId() {
        return hashId;
    }
}
