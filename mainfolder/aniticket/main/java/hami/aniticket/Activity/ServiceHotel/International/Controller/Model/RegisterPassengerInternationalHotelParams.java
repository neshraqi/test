package hami.hamibelit.Activity.ServiceHotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-02-03.
 */

public class RegisterPassengerInternationalHotelParams implements Parcelable {

    @SerializedName("hashId")
    private String hashId;
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
    @SerializedName("passengers")
    private ArrayListRegisterPassengerInternationalHotel passengers;

    //-----------------------------------------------


    public RegisterPassengerInternationalHotelParams() {
    }


    protected RegisterPassengerInternationalHotelParams(Parcel in) {
        hashId = in.readString();
        ticket_id = in.readString();
        sumFinalPrice = in.readString();
        bank = in.readString();
        credit = in.readString();
        wallet = in.readString();
        airTripType = in.readInt();
        numberp = in.readInt();
        cellphone = in.readString();
        email = in.readString();
        passengers = in.readParcelable(ArrayListRegisterPassengerInternationalHotel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hashId);
        dest.writeString(ticket_id);
        dest.writeString(sumFinalPrice);
        dest.writeString(bank);
        dest.writeString(credit);
        dest.writeString(wallet);
        dest.writeInt(airTripType);
        dest.writeInt(numberp);
        dest.writeString(cellphone);
        dest.writeString(email);
        dest.writeParcelable(passengers, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RegisterPassengerInternationalHotelParams> CREATOR = new Creator<RegisterPassengerInternationalHotelParams>() {
        @Override
        public RegisterPassengerInternationalHotelParams createFromParcel(Parcel in) {
            return new RegisterPassengerInternationalHotelParams(in);
        }

        @Override
        public RegisterPassengerInternationalHotelParams[] newArray(int size) {
            return new RegisterPassengerInternationalHotelParams[size];
        }
    };

    public String getHashId() {
        return hashId;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public String getSumFinalPrice() {
        return sumFinalPrice;
    }

    public String getBank() {
        return bank;
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

    public ArrayListRegisterPassengerInternationalHotel getPassengers() {
        return passengers;
    }


}
