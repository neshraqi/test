package hami.nasimbehesht724.Activity.ServiceTour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-03-06.
 */

public class TourItemFilter implements Parcelable {
    @SerializedName("to")
    private ArrayList<IdName> toList;
    @SerializedName("by")
    private ArrayList<String> byList;
    @SerializedName("stay")
    private ArrayList<String> stayList;
    @SerializedName("kind")
    private ArrayList<String> kindList;
    //-----------------------------------------------

    public TourItemFilter() {
    }
    //-----------------------------------------------

    protected TourItemFilter(Parcel in) {
        toList = in.createTypedArrayList(IdName.CREATOR);
        byList = in.createStringArrayList();
        stayList = in.createStringArrayList();
        kindList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(toList);
        dest.writeStringList(byList);
        dest.writeStringList(stayList);
        dest.writeStringList(kindList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TourItemFilter> CREATOR = new Creator<TourItemFilter>() {
        @Override
        public TourItemFilter createFromParcel(Parcel in) {
            return new TourItemFilter(in);
        }

        @Override
        public TourItemFilter[] newArray(int size) {
            return new TourItemFilter[size];
        }
    };

    public ArrayList<IdName> getToList() {
        return toList;
    }

    public ArrayList<String> getByList() {
        return byList;
    }

    public ArrayList<String> getStayList() {
        return stayList;
    }

    public ArrayList<String> getKindList() {
        return kindList;
    }
}
