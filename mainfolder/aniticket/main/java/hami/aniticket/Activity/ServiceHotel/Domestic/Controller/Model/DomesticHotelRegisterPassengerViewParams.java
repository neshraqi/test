package hami.hamibelit.Activity.ServiceHotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-02-15.
 */

public class DomesticHotelRegisterPassengerViewParams implements Parcelable {
    @SerializedName("code")
    private String code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("bank")
    private String bank;
    @SerializedName("fbank")
    private String fbank;
    @SerializedName("etebar")
    private String etebar;
    @SerializedName("wallet")
    private String wallet;
    @SerializedName("hotelStar")
    private String hotelStar;
    @SerializedName("hotelAddress")
    private String hotelAddress;
    @SerializedName("roomNameFa")
    private String roomNameFa;
    @SerializedName("numberOfNights")
    private String numberOfNights;
    @SerializedName("inDate")
    private String inDate;
    @SerializedName("cityName")
    private String cityName;
    @SerializedName("hotelNameFa")
    private String hotelNameFa;
    @SerializedName("price")
    private String price;
    @SerializedName("id")
    private String id;
    @SerializedName("params")
    private DomesticHotelRegisterPassengerInfo domesticHotelRegisterPassengerInfo;
    @SerializedName("hashId")
    private String hashId;
    //-----------------------------------------------

    public DomesticHotelRegisterPassengerViewParams() {
    }
    //-----------------------------------------------

    protected DomesticHotelRegisterPassengerViewParams(Parcel in) {
        code = in.readString();
        msg = in.readString();
        bank = in.readString();
        fbank = in.readString();
        etebar = in.readString();
        wallet = in.readString();
        hotelStar = in.readString();
        hotelAddress = in.readString();
        roomNameFa = in.readString();
        numberOfNights = in.readString();
        inDate = in.readString();
        cityName = in.readString();
        hotelNameFa = in.readString();
        price = in.readString();
        id = in.readString();
        domesticHotelRegisterPassengerInfo = in.readParcelable(DomesticHotelRegisterPassengerInfo.class.getClassLoader());
        hashId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(msg);
        dest.writeString(bank);
        dest.writeString(fbank);
        dest.writeString(etebar);
        dest.writeString(wallet);
        dest.writeString(hotelStar);
        dest.writeString(hotelAddress);
        dest.writeString(roomNameFa);
        dest.writeString(numberOfNights);
        dest.writeString(inDate);
        dest.writeString(cityName);
        dest.writeString(hotelNameFa);
        dest.writeString(price);
        dest.writeString(id);
        dest.writeParcelable(domesticHotelRegisterPassengerInfo, flags);
        dest.writeString(hashId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DomesticHotelRegisterPassengerViewParams> CREATOR = new Creator<DomesticHotelRegisterPassengerViewParams>() {
        @Override
        public DomesticHotelRegisterPassengerViewParams createFromParcel(Parcel in) {
            return new DomesticHotelRegisterPassengerViewParams(in);
        }

        @Override
        public DomesticHotelRegisterPassengerViewParams[] newArray(int size) {
            return new DomesticHotelRegisterPassengerViewParams[size];
        }
    };
    //-----------------------------------------------

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getBank() {
        return bank;
    }

    public String getFbank() {
        return fbank;
    }

    public String getEtebar() {
        return etebar;
    }

    public String getWallet() {
        return wallet;
    }

    public String getHotelStar() {
        return hotelStar;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public String getRoomNameFa() {
        return roomNameFa;
    }

    public String getNumberOfNights() {
        return numberOfNights;
    }

    public String getInDate() {
        return inDate;
    }

    public String getCityName() {
        return cityName;
    }

    public String getHotelNameFa() {
        return hotelNameFa;
    }

    public String getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public DomesticHotelRegisterPassengerInfo getDomesticHotelRegisterPassengerInfo() {
        return domesticHotelRegisterPassengerInfo;
    }

    public String getHashId() {
        return hashId;
    }

    public static Creator<DomesticHotelRegisterPassengerViewParams> getCREATOR() {
        return CREATOR;
    }
}
