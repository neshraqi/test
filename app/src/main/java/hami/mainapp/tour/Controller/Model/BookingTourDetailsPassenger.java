package hami.mainapp.tour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-03-11.
 */

public class BookingTourDetailsPassenger implements Parcelable {
    @SerializedName("gender")
    private String gender;
    @SerializedName("name")
    private String name;
    @SerializedName("Lname")
    private String Lname;
    @SerializedName("latinName")
    private String latinName;
    @SerializedName("latinLName")
    private String latinLName;
    @SerializedName("faName")
    private String faName;
    @SerializedName("b_day")
    private String b_day;
    @SerializedName("Mcode")
    private String Mcode;
    @SerializedName("passID")
    private String passID;
    @SerializedName("passStartDate")
    private String passStartDate;
    @SerializedName("passEndDate")
    private String passEndDate;
    @SerializedName("price")
    private String price;
    @SerializedName("fprice")
    private String fprice;
    @SerializedName("final_price")
    private String final_price;
    @SerializedName("userID")
    private String userID;
    @SerializedName("age_level")
    private String age_level;
    @SerializedName("nationality")
    private String nationality;
    //-----------------------------------------------

    public BookingTourDetailsPassenger() {
    }
    //-----------------------------------------------

    protected BookingTourDetailsPassenger(Parcel in) {
        gender = in.readString();
        name = in.readString();
        Lname = in.readString();
        latinName = in.readString();
        latinLName = in.readString();
        faName = in.readString();
        b_day = in.readString();
        Mcode = in.readString();
        passID = in.readString();
        passStartDate = in.readString();
        passEndDate = in.readString();
        price = in.readString();
        fprice = in.readString();
        final_price = in.readString();
        userID = in.readString();
        age_level = in.readString();
        nationality = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gender);
        dest.writeString(name);
        dest.writeString(Lname);
        dest.writeString(latinName);
        dest.writeString(latinLName);
        dest.writeString(faName);
        dest.writeString(b_day);
        dest.writeString(Mcode);
        dest.writeString(passID);
        dest.writeString(passStartDate);
        dest.writeString(passEndDate);
        dest.writeString(price);
        dest.writeString(fprice);
        dest.writeString(final_price);
        dest.writeString(userID);
        dest.writeString(age_level);
        dest.writeString(nationality);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingTourDetailsPassenger> CREATOR = new Creator<BookingTourDetailsPassenger>() {
        @Override
        public BookingTourDetailsPassenger createFromParcel(Parcel in) {
            return new BookingTourDetailsPassenger(in);
        }

        @Override
        public BookingTourDetailsPassenger[] newArray(int size) {
            return new BookingTourDetailsPassenger[size];
        }
    };

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getLname() {
        return Lname;
    }

    public String getLatinName() {
        return latinName;
    }

    public String getLatinLName() {
        return latinLName;
    }

    public String getFaName() {
        return faName;
    }

    public String getB_day() {
        return b_day;
    }

    public String getMcode() {
        return Mcode;
    }

    public String getPassID() {
        return passID;
    }

    public String getPassStartDate() {
        return passStartDate;
    }

    public String getPassEndDate() {
        return passEndDate;
    }

    public String getPrice() {
        return price;
    }

    public String getFprice() {
        return fprice;
    }

    public String getFinal_price() {
        return final_price;
    }

    public String getUserID() {
        return userID;
    }

    public String getAge_level() {
        return age_level;
    }

    public String getNationality() {
        return nationality;
    }
}
