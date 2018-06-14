package hami.nasimbehesht724.Activity.PastPurchases.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by renjer on 2017-11-23.
 */

public class PurchasesFlightDomestic implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("ticketid")
    private String ticketId;
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;
    @SerializedName("price")
    private String price;
    @SerializedName("fprice")
    private String fprice;
    @SerializedName("tdate_persian_show")
    private String tdate_persian_show;
    @SerializedName("tdate")
    private String tdate;
    @SerializedName("ttime")
    private String ttime;
    @SerializedName("status")
    private String status;
    @SerializedName("fnumber")
    private String fnumber;
    @SerializedName("airline")
    private String airline;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("payment_type")
    private String payment_type;
    @SerializedName("code_error")
    private String code_error;
    @SerializedName("payment_status")
    private String payment_status;
    @SerializedName("api")
    private String api;
    @SerializedName("img_airline")
    private String img_airline;
    @SerializedName("from_persian")
    private String from_persian;
    @SerializedName("to_persian")
    private String to_persian;
    @SerializedName("tdate_persian")
    private String tdate_persian;
    @SerializedName("airline_persian")
    private String airline_persian;
    @SerializedName("refund")
    private int refund;

    public final static int TYPE = 1;
    //-----------------------------------------------

    public PurchasesFlightDomestic() {
    }

    //-----------------------------------------------
    public int getTypeItem() {
        return TYPE;
    }

    public String getId() {
        return id;
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

    public String getFprice() {
        return fprice;
    }

    public String getTdate() {
        return tdate;
    }

    public String getTtime() {
        return ttime;
    }

    public String getStatus() {
        return status;
    }

    public String getFnumber() {
        return fnumber;
    }

    public String getAirline() {
        return airline;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public String getCode_error() {
        return code_error;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public String getApi() {
        return api;
    }

    public String getImg_airline() {
        return img_airline;
    }

    public String getFrom_persian() {
        return from_persian;
    }

    public String getTo_persian() {
        return to_persian;
    }

    public String getTdate_persian() {
        return tdate_persian;
    }

    public String getAirline_persian() {
        return airline_persian;
    }

    public String getTdate_persian_show() {
        return tdate_persian_show;
    }

    public String getTicketId() {
        return ticketId;
    }

    public int getRefund() {
        return refund;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}
