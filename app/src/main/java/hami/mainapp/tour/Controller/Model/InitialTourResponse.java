package hami.mainapp.tour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2017-12-21.
 */

public class InitialTourResponse implements Parcelable {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("kind")
    private Object kindList;
    @SerializedName("from")
    private ArrayList<NameValue> fromList;
    @SerializedName("to")
    private ArrayList<NameValue> toList;
    @SerializedName("start_date")
    private ArrayList<DateTour> dateTourCalendar;
    //-----------------------------------------------

    public InitialTourResponse() {
    }
    //-----------------------------------------------

    protected InitialTourResponse(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        fromList = in.createTypedArrayList(NameValue.CREATOR);
        toList = in.createTypedArrayList(NameValue.CREATOR);
        dateTourCalendar = in.createTypedArrayList(DateTour.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeTypedList(fromList);
        dest.writeTypedList(toList);
        dest.writeTypedList(dateTourCalendar);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InitialTourResponse> CREATOR = new Creator<InitialTourResponse>() {
        @Override
        public InitialTourResponse createFromParcel(Parcel in) {
            return new InitialTourResponse(in);
        }

        @Override
        public InitialTourResponse[] newArray(int size) {
            return new InitialTourResponse[size];
        }
    };

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getKindList() {
        return kindList;
    }

    public ArrayList<NameValue> getFromList() {
        return fromList;
    }

    public ArrayList<NameValue> getToList() {
        return toList;
    }

    public ArrayList<DateTour> getDateTourCalendar() {
        return dateTourCalendar;
    }
}
