package hami.hamibelit.Activity.ServiceTour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class BookingTourDetailsTicket implements Parcelable {
    @SerializedName("namep1")
    private String namep1;
    @SerializedName("id")
    private String id;
    @SerializedName("regdate")
    private String regdate;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("email")
    private String email;
    @SerializedName("numberp")
    private String numberp;
    @SerializedName("user_level")
    private String user_level;
    @SerializedName("finalprice")
    private String finalprice;
    @SerializedName("discount")
    private String discount;
    @SerializedName("tourID")
    private String tourID;
    @SerializedName("tour_name")
    private String tour_name;
    @SerializedName("tour_return_by")
    private String tour_return_by;
    @SerializedName("tour_go_by")
    private String tour_go_by;
    @SerializedName("tour_start_date")
    private String tour_start_date;
    @SerializedName("tour_end_date")
    private String tour_end_date;
    @SerializedName("tour_night_count")
    private String tour_night_count;
    @SerializedName("tour_day_count")
    private String tour_day_count;
    @SerializedName("tour_to")
    private String tour_to;
    @SerializedName("tour_from")
    private String tour_from;
    @SerializedName("tour_stay_in")
    private String tour_stay_in;
    @SerializedName("tour_kind")
    private String tour_kind;
    //-----------------------------------------------

    public BookingTourDetailsTicket() {
    }
    //-----------------------------------------------

    protected BookingTourDetailsTicket(Parcel in) {
        namep1 = in.readString();
        id = in.readString();
        regdate = in.readString();
        mobile = in.readString();
        email = in.readString();
        numberp = in.readString();
        user_level = in.readString();
        finalprice = in.readString();
        discount = in.readString();
        tourID = in.readString();
        tour_name = in.readString();
        tour_return_by = in.readString();
        tour_go_by = in.readString();
        tour_start_date = in.readString();
        tour_end_date = in.readString();
        tour_night_count = in.readString();
        tour_day_count = in.readString();
        tour_to = in.readString();
        tour_from = in.readString();
        tour_stay_in = in.readString();
        tour_kind = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(namep1);
        dest.writeString(id);
        dest.writeString(regdate);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(numberp);
        dest.writeString(user_level);
        dest.writeString(finalprice);
        dest.writeString(discount);
        dest.writeString(tourID);
        dest.writeString(tour_name);
        dest.writeString(tour_return_by);
        dest.writeString(tour_go_by);
        dest.writeString(tour_start_date);
        dest.writeString(tour_end_date);
        dest.writeString(tour_night_count);
        dest.writeString(tour_day_count);
        dest.writeString(tour_to);
        dest.writeString(tour_from);
        dest.writeString(tour_stay_in);
        dest.writeString(tour_kind);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingTourDetailsTicket> CREATOR = new Creator<BookingTourDetailsTicket>() {
        @Override
        public BookingTourDetailsTicket createFromParcel(Parcel in) {
            return new BookingTourDetailsTicket(in);
        }

        @Override
        public BookingTourDetailsTicket[] newArray(int size) {
            return new BookingTourDetailsTicket[size];
        }
    };

    public String getNamep1() {
        return namep1;
    }

    public String getId() {
        return id;
    }

    public String getRegdate() {
        return regdate;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getNumberp() {
        return numberp;
    }

    public String getUser_level() {
        return user_level;
    }

    public String getFinalprice() {
        return finalprice;
    }

    public String getDiscount() {
        return discount;
    }

    public String getTourID() {
        return tourID;
    }

    public String getTour_name() {
        return tour_name;
    }

    public String getTour_return_by() {
        return tour_return_by;
    }

    public String getTour_go_by() {
        return tour_go_by;
    }

    public String getTour_start_date() {
        return tour_start_date;
    }

    public String getTour_end_date() {
        return tour_end_date;
    }

    public String getTour_night_count() {
        return tour_night_count;
    }

    public String getTour_day_count() {
        return tour_day_count;
    }

    public String getTour_to() {
        return tour_to;
    }

    public String getTour_from() {
        return tour_from;
    }

    public String getTour_stay_in() {
        return tour_stay_in;
    }

    public String getTour_kind() {
        return tour_kind;
    }
}
