package hami.aniticket.Activity.ServiceHotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-01-17.
 */

public class InternationalHotelsResponse implements Parcelable {
    @SerializedName("errorCode")
    private int errorCode;
    @SerializedName("searchId")
    private String searchId;
    @SerializedName("message")
    private String message;
    @SerializedName("hotels")
    private ArrayList<InternationalHotel> hotels;
    private ToolsHotelFilter toolsHotelFilter;

    //-----------------------------------------------


    public InternationalHotelsResponse() {
    }
    //-----------------------------------------------


    protected InternationalHotelsResponse(Parcel in) {
        errorCode = in.readInt();
        searchId = in.readString();
        message = in.readString();
        hotels = in.createTypedArrayList(InternationalHotel.CREATOR);
        toolsHotelFilter = in.readParcelable(ToolsHotelFilter.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(errorCode);
        dest.writeString(searchId);
        dest.writeString(message);
        dest.writeTypedList(hotels);
        dest.writeParcelable(toolsHotelFilter, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InternationalHotelsResponse> CREATOR = new Creator<InternationalHotelsResponse>() {
        @Override
        public InternationalHotelsResponse createFromParcel(Parcel in) {
            return new InternationalHotelsResponse(in);
        }

        @Override
        public InternationalHotelsResponse[] newArray(int size) {
            return new InternationalHotelsResponse[size];
        }
    };

    public int getErrorCode() {
        return errorCode;
    }

    public String getSearchId() {
        return searchId;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<InternationalHotel> getHotels() {
        return hotels;
    }

    public ToolsHotelFilter getToolsHotelFilter() {
        return toolsHotelFilter;
    }

    public void setToolsHotelFilter(ToolsHotelFilter toolsHotelFilter) {
        this.toolsHotelFilter = toolsHotelFilter;
    }
}
