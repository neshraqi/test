package hami.hamibelit.Activity.ServiceTour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import hami.hamibelit.BaseController.ToStringClass;

/**
 * Created by renjer on 2018-03-06.
 */

public class SearchTourRequest extends ToStringClass implements Parcelable{

    @SerializedName("kind")
    private String kind;

    @SerializedName("kindfa")
    private String kindFa;

    @SerializedName("from")
    private String from;

    @SerializedName("fromfa")
    private String fromFa;

    @SerializedName("date")
    private String date;

    @SerializedName("datefa")
    private String dateFa;
    //-----------------------------------------------


    public SearchTourRequest() {
    }

    public SearchTourRequest(String kind, String kindFa, String from, String fromFa, String date, String dateFa) {
        this.kind = kind;
        this.kindFa = kindFa;
        this.from = from;
        this.fromFa = fromFa;
        this.date = date;
        this.dateFa = dateFa;
    }
    //-----------------------------------------------

    protected SearchTourRequest(Parcel in) {
        kind = in.readString();
        kindFa = in.readString();
        from = in.readString();
        fromFa = in.readString();
        date = in.readString();
        dateFa = in.readString();
    }

    public static final Creator<SearchTourRequest> CREATOR = new Creator<SearchTourRequest>() {
        @Override
        public SearchTourRequest createFromParcel(Parcel in) {
            return new SearchTourRequest(in);
        }

        @Override
        public SearchTourRequest[] newArray(int size) {
            return new SearchTourRequest[size];
        }
    };

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getKindFa() {
        return kindFa;
    }

    public void setKindFa(String kindFa) {
        this.kindFa = kindFa;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromFa() {
        return fromFa;
    }

    public void setFromFa(String fromFa) {
        this.fromFa = fromFa;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateFa() {
        return dateFa;
    }

    public void setDateFa(String dateFa) {
        this.dateFa = dateFa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(kindFa);
        dest.writeString(from);
        dest.writeString(fromFa);
        dest.writeString(date);
        dest.writeString(dateFa);
    }
}
