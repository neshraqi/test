package hami.aniticket.Activity.ServiceTour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2017-12-23.
 */

public class TourItemsResponse implements Parcelable {

    @SerializedName("code")
    private int code = 1;
    @SerializedName("msg")
    private String msg;
    @SerializedName("tour_list")
    private ArrayList<TourItem> tourItems;
    @SerializedName("filters")
    private TourItemFilter tourItemFilter;
    //-----------------------------------------------

    public TourItemsResponse() {
    }
    //-----------------------------------------------

    protected TourItemsResponse(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        tourItems = in.createTypedArrayList(TourItem.CREATOR);
        tourItemFilter = in.readParcelable(TourItemFilter.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeTypedList(tourItems);
        dest.writeParcelable(tourItemFilter, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TourItemsResponse> CREATOR = new Creator<TourItemsResponse>() {
        @Override
        public TourItemsResponse createFromParcel(Parcel in) {
            return new TourItemsResponse(in);
        }

        @Override
        public TourItemsResponse[] newArray(int size) {
            return new TourItemsResponse[size];
        }
    };

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public ArrayList<TourItem> getTourItems() {
        return tourItems;
    }

    public TourItemFilter getTourItemFilter() {
        return tourItemFilter;
    }
}
