package hami.mainapp.flight.Services.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-02-28.
 */

public class RegisterPassengerResponse implements Parcelable {
    @SerializedName("msg")
    private String msg;
    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private RegisterPassengerData RegisterPassengerData;
    //-----------------------------------------------

    public RegisterPassengerResponse() {
    }
    //-----------------------------------------------


    protected RegisterPassengerResponse(Parcel in) {
        msg = in.readString();
        code = in.readInt();
        RegisterPassengerData = in.readParcelable(RegisterPassengerData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
        dest.writeInt(code);
        dest.writeParcelable(RegisterPassengerData, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RegisterPassengerResponse> CREATOR = new Creator<RegisterPassengerResponse>() {
        @Override
        public RegisterPassengerResponse createFromParcel(Parcel in) {
            return new RegisterPassengerResponse(in);
        }

        @Override
        public RegisterPassengerResponse[] newArray(int size) {
            return new RegisterPassengerResponse[size];
        }
    };

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public RegisterPassengerData getRegisterPassengerData() {
        return RegisterPassengerData;
    }


    @Override
    public String toString() {
        try {
            Exclude ex = new Exclude();
            Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
            String jsonString = gson.toJson(this);
            return jsonString;
        } catch (Exception e) {
            e.getMessage();
        }
        return "";
    }

    class Exclude implements ExclusionStrategy {

        @Override
        public boolean shouldSkipClass(Class<?> arg0) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            SerializedName ns = field.getAnnotation(SerializedName.class);
            if (ns != null)
                return false;
            return true;
        }
    }
}


