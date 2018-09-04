package hami.mainapp.hotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-02-06.
 */

public class ToolsHotelFilter implements Parcelable {
    private ArrayList<String> filterRate;
    private ArrayList<String> filterOffer;
    //-----------------------------------------------

    public ToolsHotelFilter() {
        filterOffer = new ArrayList<>();
        filterRate = new ArrayList<>();
    }

    //-----------------------------------------------

    protected ToolsHotelFilter(Parcel in) {
        filterRate = in.createStringArrayList();
        filterOffer = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(filterRate);
        dest.writeStringList(filterOffer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ToolsHotelFilter> CREATOR = new Creator<ToolsHotelFilter>() {
        @Override
        public ToolsHotelFilter createFromParcel(Parcel in) {
            return new ToolsHotelFilter(in);
        }

        @Override
        public ToolsHotelFilter[] newArray(int size) {
            return new ToolsHotelFilter[size];
        }
    };

    public void setFilterRate(ArrayList<String> filterRate) {
        this.filterRate = filterRate;
    }

    public void setFilterOffer(ArrayList<String> filterOffer) {
        this.filterOffer = filterOffer;
    }
    //-----------------------------------------------

    public ArrayList<String> getFilterRate() {
        return filterRate;
    }

    public ArrayList<String> getFilterOffer() {
        return filterOffer;
    }
}
