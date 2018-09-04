package hami.mainapp.tour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-03-07.
 */

public class TourDetailResponse implements Parcelable {
    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("tour")
    private TourDetailData tourDetailData;
    //-----------------------------------------------

    public TourDetailResponse() {
    }
    //-----------------------------------------------

    protected TourDetailResponse(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        tourDetailData = in.readParcelable(TourDetailData.class.getClassLoader());
    }

    public static final Creator<TourDetailResponse> CREATOR = new Creator<TourDetailResponse>() {
        @Override
        public TourDetailResponse createFromParcel(Parcel in) {
            return new TourDetailResponse(in);
        }

        @Override
        public TourDetailResponse[] newArray(int size) {
            return new TourDetailResponse[size];
        }
    };

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public TourDetailData getTourDetailData() {
        return tourDetailData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeParcelable(tourDetailData, flags);
    }
}
