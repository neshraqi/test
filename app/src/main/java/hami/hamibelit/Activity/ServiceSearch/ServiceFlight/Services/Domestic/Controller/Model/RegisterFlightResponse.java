package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-03-05.
 */

public class RegisterFlightResponse implements Parcelable {
    @SerializedName("code")
    private String code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("ticket_id")
    private String ticketId;
    @SerializedName("viewparams")
    private ViewParamsDomestic viewParamsDomestic;
    private String airlineCode;
    private String takeOffDatePersian;
    //-----------------------------------------------

    public RegisterFlightResponse() {
    }
    //-----------------------------------------------


    protected RegisterFlightResponse(Parcel in) {
        code = in.readString();
        msg = in.readString();
        ticketId = in.readString();
        viewParamsDomestic = in.readParcelable(ViewParamsDomestic.class.getClassLoader());
        airlineCode = in.readString();
        takeOffDatePersian = in.readString();
    }

    //-----------------------------------------------
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(msg);
        dest.writeString(ticketId);
        dest.writeParcelable(viewParamsDomestic, flags);
        dest.writeString(airlineCode);
        dest.writeString(takeOffDatePersian);
    }

    //-----------------------------------------------
    public static final Creator<RegisterFlightResponse> CREATOR = new Creator<RegisterFlightResponse>() {
        @Override
        public RegisterFlightResponse createFromParcel(Parcel in) {
            return new RegisterFlightResponse(in);
        }

        @Override
        public RegisterFlightResponse[] newArray(int size) {
            return new RegisterFlightResponse[size];
        }
    };

    //-----------------------------------------------
    public String getTakeOffDatePersian() {
        return takeOffDatePersian;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getTicketId() {
        return ticketId;
    }

    public ViewParamsDomestic getViewParamsDomestic() {
        return viewParamsDomestic;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public void setTakeOffDatePersian(String takeOffDatePersian) {
        this.takeOffDatePersian = takeOffDatePersian;
    }

    //-----------------------------------------------
    @Override
    public int describeContents() {
        return 0;
    }

    //-----------------------------------------------
    @Override
    public String toString() {
        try {
            RegisterFlightResponse.Exclude ex = new RegisterFlightResponse.Exclude();
            Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
            String jsonString = gson.toJson(this);
            return jsonString;
        } catch (Exception e) {
            e.getMessage();
        }
        return "";
    }

    //-----------------------------------------------
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
