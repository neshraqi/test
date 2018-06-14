package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import hami.nasimbehesht724.R;

/**
 * Created by renjer on 2017-05-24.
 */

public class DomesticParams implements Parcelable {
    @SerializedName("airline")
    private String airline;
    @SerializedName("date")
    private String date_;
    @SerializedName("class")
    private String class_;
    @SerializedName("numberp")
    private String numberp_;
    @SerializedName("flight_number")
    private String flight_number_;
    @SerializedName("flightName")
    private String flightName_;
    @SerializedName("cellphone")
    private String cellphone_;
    @SerializedName("email")
    private String email_;
    @SerializedName("from")
    private String from_;
    @SerializedName("to")
    private String to_;
    @SerializedName("melicode")
    private String[] melicode;
    @SerializedName("name")
    private String[] name;
    @SerializedName("namep")
    private String[] namep;
    @SerializedName("family")
    private String[] family;
    @SerializedName("familyp")
    private String[] familyp;
    @SerializedName("gender")
    private String[] gender;
    @SerializedName("typep")
    private String[] typep;
    @SerializedName("nationality")
    private String[] nationality;
    @SerializedName("nationalitycode")
    private String[] nationalitycode;
    @SerializedName("birthday")
    private String[] birthday;
    @SerializedName("expdate")
    private String[] expdate;
    @SerializedName("passport_number")
    private String[] passport_number;
    @SerializedName("price")
    private String[] price;
    @SerializedName("finalprice")
    private String[] finalprice;
    @SerializedName("departure")
    private String departure;
    @SerializedName("tdate")
    private String tdate;
    @SerializedName("discount")
    private String discount;
    @SerializedName("fprice")
    private String fprice;
    @SerializedName("allprice")
    private String allprice;
    @SerializedName("airlineen")
    private String airlineen;
    @SerializedName("fromfa")
    private String fromfa;
    @SerializedName("tofa")
    private String tofa;
    //-----------------------------------------------

    public DomesticParams() {
    }

    //-----------------------------------------------

    protected DomesticParams(Parcel in) {
        airline = in.readString();
        date_ = in.readString();
        class_ = in.readString();
        numberp_ = in.readString();
        flight_number_ = in.readString();
        flightName_ = in.readString();
        cellphone_ = in.readString();
        email_ = in.readString();
        from_ = in.readString();
        to_ = in.readString();
        melicode = in.createStringArray();
        name = in.createStringArray();
        namep = in.createStringArray();
        family = in.createStringArray();
        familyp = in.createStringArray();
        gender = in.createStringArray();
        typep = in.createStringArray();
        nationality = in.createStringArray();
        nationalitycode = in.createStringArray();
        birthday = in.createStringArray();
        expdate = in.createStringArray();
        passport_number = in.createStringArray();
        price = in.createStringArray();
        finalprice = in.createStringArray();
        departure = in.readString();
        tdate = in.readString();
        discount = in.readString();
        fprice = in.readString();
        allprice = in.readString();
        airlineen = in.readString();
        fromfa = in.readString();
        tofa = in.readString();
    }

    public static final Creator<DomesticParams> CREATOR = new Creator<DomesticParams>() {
        @Override
        public DomesticParams createFromParcel(Parcel in) {
            return new DomesticParams(in);
        }

        @Override
        public DomesticParams[] newArray(int size) {
            return new DomesticParams[size];
        }
    };

    public String getAirline() {
        return airline;
    }

    public String getDate_() {
        return date_;
    }

    public String getClass_() {
        return class_;
    }

    public String getNumberp_() {
        return numberp_;
    }

    public String getFlight_number_() {
        return flight_number_;
    }

    public String getFlightName_() {
        return flightName_;
    }

    public String getCellphone_() {
        return cellphone_;
    }

    public String getEmail_() {
        return email_;
    }

    public String getFrom_() {
        return from_;
    }

    public String getTo_() {
        return to_;
    }

    public String[] getName() {
        return name;
    }

    public String[] getNamep() {
        return namep;
    }

    public String[] getFamily() {
        return family;
    }

    public String[] getFamilyp() {
        return familyp;
    }

    public String[] getGender() {
        return gender;
    }

    public String[] getTypep() {
        return typep;
    }

    public String[] getMelicode() {
        return melicode;
    }

    public int getTypeString(int index) {
        int type = Integer.valueOf(typep[index]);
        if (type == 1) {
            return R.string.adults;
        } else if (type == 2) {
            return R.string.children;
        } else if (type == 3) {
            return R.string.infant;
        }
        return -1;
    }
    public String[] getNationality() {
        return nationality;
    }

    public String[] getNationalitycode() {
        return nationalitycode;
    }

    public String[] getBirthday() {
        return birthday;
    }

    public String[] getExpdate() {
        return expdate;
    }

    public String[] getPassport_number() {
        return passport_number;
    }

    public String[] getPrice() {
        return price;
    }

    public String[] getFinalprice() {
        return finalprice;
    }

    public String getDeparture() {
        return departure;
    }

    public String getTdate() {
        return tdate;
    }

    public String getDiscount() {
        return discount;
    }

    public String getFprice() {
        return fprice;
    }

    public String getAllprice() {
        return allprice;
    }

    public String getAirlineen() {
        return airlineen;
    }

    public String getFromfa() {
        return fromfa;
    }

    public String getTofa() {
        return tofa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(airline);
        dest.writeString(date_);
        dest.writeString(class_);
        dest.writeString(numberp_);
        dest.writeString(flight_number_);
        dest.writeString(flightName_);
        dest.writeString(cellphone_);
        dest.writeString(email_);
        dest.writeString(from_);
        dest.writeString(to_);
        dest.writeStringArray(melicode);
        dest.writeStringArray(name);
        dest.writeStringArray(namep);
        dest.writeStringArray(family);
        dest.writeStringArray(familyp);
        dest.writeStringArray(gender);
        dest.writeStringArray(typep);
        dest.writeStringArray(nationality);
        dest.writeStringArray(nationalitycode);
        dest.writeStringArray(birthday);
        dest.writeStringArray(expdate);
        dest.writeStringArray(passport_number);
        dest.writeStringArray(price);
        dest.writeStringArray(finalprice);
        dest.writeString(departure);
        dest.writeString(tdate);
        dest.writeString(discount);
        dest.writeString(fprice);
        dest.writeString(allprice);
        dest.writeString(airlineen);
        dest.writeString(fromfa);
        dest.writeString(tofa);
    }
}
