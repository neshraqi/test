package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class TicketInternational implements Parcelable {
    @SerializedName("outBound")
    private OutBound outBound;
    @SerializedName("return_")
    private OutBound return_;
    @SerializedName("registerPassengerResponse")
    private RegisterPassengerResponse RegisterPassengerResponse;
    //-----------------------------------------------

    public TicketInternational() {
    }
    //-----------------------------------------------

    public TicketInternational(OutBound outBound, OutBound return_, RegisterPassengerResponse registerPassengerResponse) {
        this.outBound = outBound;
        this.return_ = return_;
        RegisterPassengerResponse = registerPassengerResponse;
    }

    //-----------------------------------------------

    protected TicketInternational(Parcel in) {
        outBound = in.readParcelable(OutBound.class.getClassLoader());
        return_ = in.readParcelable(OutBound.class.getClassLoader());
        RegisterPassengerResponse = in.readParcelable(hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.RegisterPassengerResponse.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(outBound, flags);
        dest.writeParcelable(return_, flags);
        dest.writeParcelable(RegisterPassengerResponse, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TicketInternational> CREATOR = new Creator<TicketInternational>() {
        @Override
        public TicketInternational createFromParcel(Parcel in) {
            return new TicketInternational(in);
        }

        @Override
        public TicketInternational[] newArray(int size) {
            return new TicketInternational[size];
        }
    };

    public OutBound getOutBound() {
        return outBound;
    }

    public OutBound getReturn_() {
        return return_;
    }

    public RegisterPassengerResponse getRegisterPassengerResponse() {
        return RegisterPassengerResponse;
    }

    //-----------------------------------------------
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
