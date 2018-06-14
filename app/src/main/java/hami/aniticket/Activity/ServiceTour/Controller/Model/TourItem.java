package hami.aniticket.Activity.ServiceTour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-12-23.
 */

public class TourItem implements Parcelable{
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("go_by")
    private String go_by;
    @SerializedName("return_by")
    private String return_by;
    @SerializedName("start_date")
    private String start_date;
    @SerializedName("end_date")
    private String end_date;
    @SerializedName("price_inf")
    private String price_inf;
    @SerializedName("price_chd")
    private String price_chd;
    @SerializedName("price_adl")
    private String price_adl;
    @SerializedName("discount_price_adl")
    private String discount_price_adl;
    @SerializedName("tto")
    private String tto;
    @SerializedName("tto_code")
    private String tto_code;
    @SerializedName("tfrom")
    private String tfrom;
    @SerializedName("tfrom_code")
    private String tfrom_code;
    @SerializedName("stay_in")
    private String stay_in;
    @SerializedName("night_count")
    private String night_count;
    @SerializedName("day_count")
    private String day_count;
    @SerializedName("available")
    private String available;
    @SerializedName("img")
    private String img;
    @SerializedName("kind")
    private String kind;
    @SerializedName("contact_link")
    private String contact_link;
    @SerializedName("online_sell")
    private String online_sell;
    //-----------------------------------------------

    public TourItem() {
    }

    //-----------------------------------------------

    protected TourItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        go_by = in.readString();
        return_by = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        price_inf = in.readString();
        price_chd = in.readString();
        price_adl = in.readString();
        discount_price_adl = in.readString();
        tto = in.readString();
        tto_code = in.readString();
        tfrom = in.readString();
        tfrom_code = in.readString();
        stay_in = in.readString();
        night_count = in.readString();
        day_count = in.readString();
        available = in.readString();
        img = in.readString();
        kind = in.readString();
        contact_link = in.readString();
        online_sell = in.readString();
    }

    public static final Creator<TourItem> CREATOR = new Creator<TourItem>() {
        @Override
        public TourItem createFromParcel(Parcel in) {
            return new TourItem(in);
        }

        @Override
        public TourItem[] newArray(int size) {
            return new TourItem[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGo_by() {
        return go_by;
    }

    public String getReturn_by() {
        return return_by;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getPrice_inf() {
        return price_inf;
    }

    public String getPrice_chd() {
        return price_chd;
    }

    public String getPrice_adl() {
        return price_adl;
    }

    public String getTto() {
        return tto;
    }

    public String getTto_code() {
        return tto_code;
    }

    public String getTfrom() {
        return tfrom;
    }

    public String getTfrom_code() {
        return tfrom_code;
    }

    public String getStay_in() {
        return stay_in;
    }

    public String getNight_count() {
        return night_count;
    }

    public String getDay_count() {
        return day_count;
    }

    public String getAvailable() {
        return available;
    }

    public String getImg() {
        return img;
    }

    public String getKind() {
        return kind;
    }

    public String getDiscount_price_adl() {
        return discount_price_adl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(go_by);
        dest.writeString(return_by);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeString(price_inf);
        dest.writeString(price_chd);
        dest.writeString(price_adl);
        dest.writeString(discount_price_adl);
        dest.writeString(tto);
        dest.writeString(tto_code);
        dest.writeString(tfrom);
        dest.writeString(tfrom_code);
        dest.writeString(stay_in);
        dest.writeString(night_count);
        dest.writeString(day_count);
        dest.writeString(available);
        dest.writeString(img);
        dest.writeString(kind);
        dest.writeString(contact_link);
        dest.writeString(online_sell);
    }
}
