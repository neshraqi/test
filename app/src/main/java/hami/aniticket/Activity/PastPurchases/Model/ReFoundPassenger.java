package hami.aniticket.Activity.PastPurchases.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-11-27.
 */

public class ReFoundPassenger {
    @SerializedName("apiid")
    private String apiid;
    @SerializedName("ticket_id")
    private String ticket_id;
    @SerializedName("cost")
    private String cost;
    @SerializedName("fare")
    private String fare;
    @SerializedName("tax")
    private String tax;
    @SerializedName("pricejarime")
    private String pricejarime;
    @SerializedName("jarimerefund")
    private String jarimerefund;
    @SerializedName("expnr")
    private String expnr;
    @SerializedName("dmmd")
    private String dmmd;

    @SerializedName("refundprice")
    private String refundprice;
    @SerializedName("ticketrefund")
    private String ticketrefund;
    @SerializedName("sacnumber")
    private String sacnumber;
    @SerializedName("TicketNumber")
    private String TicketNumber;
    @SerializedName("passengers_id")
    private String passengers_id;
    @SerializedName("refund")
    private String refund;
    @SerializedName("pnrrefund")
    private String pnrrefund;
    @SerializedName("daterefund")
    private String daterefund;
    @SerializedName("user_id_refund")
    private String user_id_refund;
    @SerializedName("user_type_refund")
    private String user_type_refund;

    private Boolean isSelect;
    //-----------------------------------------------

    public ReFoundPassenger() {
    }

    //-----------------------------------------------

    public Boolean IsSelected() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    public String getApiid() {
        return apiid;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public String getCost() {
        return cost;
    }

    public String getFare() {
        return fare;
    }

    public String getTax() {
        return tax;
    }

    public String getPricejarime() {
        return pricejarime;
    }

    public String getJarimerefund() {
        return jarimerefund;
    }

    public String getExpnr() {
        return expnr;
    }

    public String getDmmd() {
        return dmmd;
    }

    public String getTicketrefund() {
        return ticketrefund;
    }

    public String getSacnumber() {
        return sacnumber;
    }

    public String getTicketNumber() {
        return TicketNumber;
    }

    public String getPassengers_id() {
        return passengers_id;
    }

    public String getRefund() {
        return refund;
    }

    public String getRefundprice() {
        return refundprice;
    }

    public String getPnrrefund() {
        return pnrrefund;
    }

    public String getDaterefund() {
        return daterefund;
    }

    public String getUser_id_refund() {
        return user_id_refund;
    }

    public String getUser_type_refund() {
        return user_type_refund;
    }
}
