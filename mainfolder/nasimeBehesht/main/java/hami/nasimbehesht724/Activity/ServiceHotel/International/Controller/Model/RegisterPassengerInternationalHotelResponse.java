package hami.mainapp.Activity.ServiceHotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-02-03.
 */

public class RegisterPassengerInternationalHotelResponse implements Parcelable {
    @SerializedName("msg")
    private String msg;
    @SerializedName("code")
    private int code;
    @SerializedName("params")
    private RegisterPassengerInternationalHotelParams registerPassengerInternationalHotelParams;
    //-----------------------------------------------

    public RegisterPassengerInternationalHotelResponse() {
    }

    //-----------------------------------------------


    protected RegisterPassengerInternationalHotelResponse(Parcel in) {
        msg = in.readString();
        code = in.readInt();
        registerPassengerInternationalHotelParams = in.readParcelable(RegisterPassengerInternationalHotelParams.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
        dest.writeInt(code);
        dest.writeParcelable(registerPassengerInternationalHotelParams, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RegisterPassengerInternationalHotelResponse> CREATOR = new Creator<RegisterPassengerInternationalHotelResponse>() {
        @Override
        public RegisterPassengerInternationalHotelResponse createFromParcel(Parcel in) {
            return new RegisterPassengerInternationalHotelResponse(in);
        }

        @Override
        public RegisterPassengerInternationalHotelResponse[] newArray(int size) {
            return new RegisterPassengerInternationalHotelResponse[size];
        }
    };

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public RegisterPassengerInternationalHotelParams getRegisterPassengerInternationalHotelParams() {
        return registerPassengerInternationalHotelParams;
    }


}
