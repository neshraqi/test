package hami.hamibelit.Activity.ServiceHotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-02-15.
 */

public class DomesticHotelRegisterPassengerResponse implements Parcelable{
    @SerializedName("viewparams")
    private DomesticHotelRegisterPassengerViewParams viewParams;
    @SerializedName("code")
    private int code;
    //-----------------------------------------------

    public DomesticHotelRegisterPassengerResponse() {
    }
    //-----------------------------------------------

    protected DomesticHotelRegisterPassengerResponse(Parcel in) {
        viewParams = in.readParcelable(DomesticHotelRegisterPassengerViewParams.class.getClassLoader());
        code = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(viewParams, flags);
        dest.writeInt(code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DomesticHotelRegisterPassengerResponse> CREATOR = new Creator<DomesticHotelRegisterPassengerResponse>() {
        @Override
        public DomesticHotelRegisterPassengerResponse createFromParcel(Parcel in) {
            return new DomesticHotelRegisterPassengerResponse(in);
        }

        @Override
        public DomesticHotelRegisterPassengerResponse[] newArray(int size) {
            return new DomesticHotelRegisterPassengerResponse[size];
        }
    };

    public DomesticHotelRegisterPassengerViewParams getViewParams() {
        return viewParams;
    }

    public int getCode() {
        return code;
    }
}
