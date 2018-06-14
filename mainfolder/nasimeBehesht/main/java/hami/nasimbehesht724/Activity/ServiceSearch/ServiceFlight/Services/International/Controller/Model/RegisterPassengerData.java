package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.ResultRegisterFlightInternationalResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.ResultRegisterPassengerFlightInternationalResponse;

/**
 * Created by renjer on 2017-02-28.
 */

public class RegisterPassengerData implements Parcelable {

    @SerializedName("passengers")
    private ResultRegisterPassengerFlightInternationalResponse passengers;
    @SerializedName("outbound")
    private ResultRegisterFlightInternationalResponse outbound_;
    @SerializedName("return")
    private ResultRegisterFlightInternationalResponse return_;
    @SerializedName("searchId")
    private String searchId;
    @SerializedName("ticket_id")
    private String ticket_id;
    @SerializedName("sumfinalprice")
    private String sumFinalPrice;
    @SerializedName("bank")
    private String bank;
    @SerializedName("etebar")
    private String credit;
    @SerializedName("wallet")
    private String wallet;
    @SerializedName("airTripType")
    private int airTripType;
    @SerializedName("numberp")
    private int numberp;
    @SerializedName("cellphone")
    private String cellphone;
    @SerializedName("email")
    private String email;


    //-----------------------------------------------


    public RegisterPassengerData() {
    }


    protected RegisterPassengerData(Parcel in) {
        passengers = in.readParcelable(ResultRegisterPassengerFlightInternationalResponse.class.getClassLoader());
        outbound_ = in.readParcelable(ResultRegisterFlightInternationalResponse.class.getClassLoader());
        return_ = in.readParcelable(ResultRegisterFlightInternationalResponse.class.getClassLoader());
        searchId = in.readString();
        ticket_id = in.readString();
        sumFinalPrice = in.readString();
        bank = in.readString();
        credit = in.readString();
        wallet = in.readString();
        airTripType = in.readInt();
        numberp = in.readInt();
        cellphone = in.readString();
        email = in.readString();
    }

    public static final Creator<RegisterPassengerData> CREATOR = new Creator<RegisterPassengerData>() {
        @Override
        public RegisterPassengerData createFromParcel(Parcel in) {
            return new RegisterPassengerData(in);
        }

        @Override
        public RegisterPassengerData[] newArray(int size) {
            return new RegisterPassengerData[size];
        }
    };

    public String getSearchId() {
        return searchId;
    }

    public String getTicketId() {
        return ticket_id;
    }

    public String getSumFinalPrice() {
        return sumFinalPrice;
    }

    public String[] getBank() {
        String[] res = bank.split("-");
        return res;
    }

    public String getCredit() {
        return credit;
    }

    public String getWallet() {
        return wallet;
    }

    public int getAirTripType() {
        return airTripType;
    }

    public int getNumberp() {
        return numberp;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getEmail() {
        return email;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public ResultRegisterFlightInternationalResponse getOutbound_() {
        return outbound_;
    }

    public ResultRegisterFlightInternationalResponse getReturn_() {
        return return_;
    }

    public ResultRegisterPassengerFlightInternationalResponse getPassengers() {
        return passengers;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(passengers, flags);
        dest.writeParcelable(outbound_, flags);
        dest.writeParcelable(return_, flags);
        dest.writeString(searchId);
        dest.writeString(ticket_id);
        dest.writeString(sumFinalPrice);
        dest.writeString(bank);
        dest.writeString(credit);
        dest.writeString(wallet);
        dest.writeInt(airTripType);
        dest.writeInt(numberp);
        dest.writeString(cellphone);
        dest.writeString(email);
    }
}
