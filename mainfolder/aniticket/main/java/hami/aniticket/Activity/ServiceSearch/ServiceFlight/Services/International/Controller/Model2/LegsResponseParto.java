package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2017-11-02.
 */

public class LegsResponseParto implements Parcelable {
    @SerializedName("legs")
    private ArrayList<LegsParto> listLegs;
    //-----------------------------------------------

    public LegsResponseParto() {
    }

    protected LegsResponseParto(Parcel in) {
        listLegs = in.createTypedArrayList(LegsParto.CREATOR);
    }
    //-----------------------------------------------
    public static final Creator<LegsResponseParto> CREATOR = new Creator<LegsResponseParto>() {
        @Override
        public LegsResponseParto createFromParcel(Parcel in) {
            return new LegsResponseParto(in);
        }

        @Override
        public LegsResponseParto[] newArray(int size) {
            return new LegsResponseParto[size];
        }
    };
    //-----------------------------------------------
    public ArrayList<LegsParto> getListLegs() {
        return listLegs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(listLegs);
    }
}
