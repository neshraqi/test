package hami.nasimbehesht724.Activity.ServiceTour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-12-21.
 */

public class DateTour implements Parcelable {
    @SerializedName("jdate")
    private String jdate;
    @SerializedName("date")
    private String date;
    @SerializedName("jday")
    private String jday;
    @SerializedName("jmonth")
    private String jmonth;
    @SerializedName("unix")
    private String unix;

    //-----------------------------------------------\
    public DateTour() {
    }
    //-----------------------------------------------


    protected DateTour(Parcel in) {
        jdate = in.readString();
        date = in.readString();
        jday = in.readString();
        jmonth = in.readString();
        unix = in.readString();
    }

    public static final Creator<DateTour> CREATOR = new Creator<DateTour>() {
        @Override
        public DateTour createFromParcel(Parcel in) {
            return new DateTour(in);
        }

        @Override
        public DateTour[] newArray(int size) {
            return new DateTour[size];
        }
    };

    public String getJdate() {
        return jdate;
    }

    public String getDate() {
        return date;
    }

    public String getJday() {
        return jday;
    }

    public String getJmonth() {
        return jmonth;
    }

    public String getUnix() {
        return unix;
    }
    //-----------------------------------------------

    public void setJdate(String jdate) {
        this.jdate = jdate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setJday(String jday) {
        this.jday = jday;
    }

    public void setJmonth(String jmonth) {
        this.jmonth = jmonth;
    }

    public void setUnix(String unix) {
        this.unix = unix;
    }

    //-----------------------------------------------
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jdate);
        dest.writeString(date);
        dest.writeString(jday);
        dest.writeString(jmonth);
        dest.writeString(unix);
    }
}
