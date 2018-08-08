package hami.mainapp.Activity.ServiceSearch.ServiceBus.Services.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import hami.mainapp.R;

/**
 * Created by renjer on 2017-02-21.
 */

public class BusTicketInformation implements Parcelable {

    //-----------------------------------------------
    @SerializedName("id")
    private String id;
    @SerializedName("regdate")
    private String regDate;
    @SerializedName("searchId")
    private String searchId;
    @SerializedName("orderId")
    private String orderId;
    @SerializedName("ip")
    private String ip;
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;
    @SerializedName("price")
    private String price;
    @SerializedName("fprice")
    private String fPrice;
    @SerializedName("finalprice")
    private String finalPrice;
    @SerializedName("tdate")
    private String tDate;
    @SerializedName("ttime")
    private String tTime1;
    @SerializedName("chairs")
    private String chairs;
    @SerializedName("status")
    private String status;
    @SerializedName("busnumber")
    private String busNumber;
    @SerializedName("bustype")
    private String busType;
    @SerializedName("company")
    private String company;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("email")
    private String email;
    @SerializedName("emailed")
    private String emailed;
    @SerializedName("pnr")
    private String pnr;
    @SerializedName("api")
    private String api;
    @SerializedName("numberp")
    private String numberP;
    @SerializedName("error")
    private String error;
    @SerializedName("code_error")
    private String codeError;
    @SerializedName("paymentID")
    private String paymentID;
    @SerializedName("payment_status")
    private String paymentStatus;
    @SerializedName("payment_rand")
    private String paymentRand;
    @SerializedName("webservice_error")
    private String webserviceError;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("tickets_paydescribe")
    private String ticketsPayDescribe;
    @SerializedName("reservationItems")
    private String ReservationItems;
    @SerializedName("name")
    private String name;
    @SerializedName("gender")
    private String gender;
    @SerializedName("nid")
    private String nid;
    @SerializedName("payment_type")
    private String paymentType;
    @SerializedName("discount")
    private String discount;
    @SerializedName("discount_id")
    private String discountId;
    @SerializedName("markup")
    private String markup;
    @SerializedName("credit")
    private String credit;
    @SerializedName("payment_kind")
    private String paymentKind;
    @SerializedName("pidtype")
    private String pidType;
    @SerializedName("refund")
    private String refund;
    //-----------------------------------------------

    public BusTicketInformation() {
    }
    //-----------------------------------------------


    protected BusTicketInformation(Parcel in) {
        id = in.readString();
        regDate = in.readString();
        searchId = in.readString();
        orderId = in.readString();
        ip = in.readString();
        from = in.readString();
        to = in.readString();
        price = in.readString();
        fPrice = in.readString();
        finalPrice = in.readString();
        tDate = in.readString();
        tTime1 = in.readString();
        chairs = in.readString();
        status = in.readString();
        busNumber = in.readString();
        busType = in.readString();
        company = in.readString();
        mobile = in.readString();
        email = in.readString();
        emailed = in.readString();
        pnr = in.readString();
        api = in.readString();
        numberP = in.readString();
        error = in.readString();
        codeError = in.readString();
        paymentID = in.readString();
        paymentStatus = in.readString();
        paymentRand = in.readString();
        webserviceError = in.readString();
        userId = in.readString();
        userName = in.readString();
        ticketsPayDescribe = in.readString();
        ReservationItems = in.readString();
        name = in.readString();
        gender = in.readString();
        nid = in.readString();
        paymentType = in.readString();
        discount = in.readString();
        discountId = in.readString();
        markup = in.readString();
        credit = in.readString();
        paymentKind = in.readString();
        pidType = in.readString();
        refund = in.readString();
    }

    public static final Creator<BusTicketInformation> CREATOR = new Creator<BusTicketInformation>() {
        @Override
        public BusTicketInformation createFromParcel(Parcel in) {
            return new BusTicketInformation(in);
        }

        @Override
        public BusTicketInformation[] newArray(int size) {
            return new BusTicketInformation[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getSearchId() {
        return searchId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getIp() {
        return ip;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getPrice() {
        return price;
    }

    public String getfPrice() {
        return fPrice;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public String gettDate() {
        return tDate;
    }

    public String gettTime1() {
        return tTime1;
    }

    public String getChairs() {
        return chairs;
    }

    public String getStatus() {
        return status;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public String getBusType() {
        return busType;
    }

    public String getCompany() {
        return company;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getEmailed() {
        return emailed;
    }

    public String getPnr() {
        return pnr;
    }

    public String getApi() {
        return api;
    }

    public String getNumberP() {
        return numberP;
    }

    public String getError() {
        return error;
    }

    public String getCodeError() {
        return codeError;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentRand() {
        return paymentRand;
    }

    public String getWebserviceError() {
        return webserviceError;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getTicketsPayDescribe() {
        return ticketsPayDescribe;
    }

    public String getReservationItems() {
        return ReservationItems;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        if(gender.contains("2"))
            return R.string.male;
        return R.string.female;
    }

    public String getNid() {
        return nid;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getDiscount() {
        return discount;
    }

    public String getDiscountId() {
        return discountId;
    }

    public String getMarkup() {
        return markup;
    }

    public String getCredit() {
        return credit;
    }

    public String getPaymentKind() {
        return paymentKind;
    }

    public String getPidType() {
        return pidType;
    }

    public String getRefund() {
        return refund;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(regDate);
        dest.writeString(searchId);
        dest.writeString(orderId);
        dest.writeString(ip);
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(price);
        dest.writeString(fPrice);
        dest.writeString(finalPrice);
        dest.writeString(tDate);
        dest.writeString(tTime1);
        dest.writeString(chairs);
        dest.writeString(status);
        dest.writeString(busNumber);
        dest.writeString(busType);
        dest.writeString(company);
        dest.writeString(mobile);
        dest.writeString(email);
        dest.writeString(emailed);
        dest.writeString(pnr);
        dest.writeString(api);
        dest.writeString(numberP);
        dest.writeString(error);
        dest.writeString(codeError);
        dest.writeString(paymentID);
        dest.writeString(paymentStatus);
        dest.writeString(paymentRand);
        dest.writeString(webserviceError);
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(ticketsPayDescribe);
        dest.writeString(ReservationItems);
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(nid);
        dest.writeString(paymentType);
        dest.writeString(discount);
        dest.writeString(discountId);
        dest.writeString(markup);
        dest.writeString(credit);
        dest.writeString(paymentKind);
        dest.writeString(pidType);
        dest.writeString(refund);
    }
}
