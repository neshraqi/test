package hami.hamibelit.Activity.PastPurchases.Model;

import com.google.gson.annotations.SerializedName;

import hami.hamibelit.R;

/**
 * Created by renjer on 2017-11-26.
 */

public class PassengerFlightDomestic {


    @SerializedName("id")
    private String id;
    @SerializedName("gender")
    private String gender;
    @SerializedName("name")
    private String name;
    @SerializedName("family")
    private String family;
    @SerializedName("ename")
    private String eName;
    @SerializedName("efamily")
    private String eFamily;
    @SerializedName("nid")
    private String nid;
    @SerializedName("type")
    private String type;
    @SerializedName("price")
    private String price;
    @SerializedName("fprice")
    private String fprice;
    @SerializedName("passport_number")
    private String passportNumber;
    @SerializedName("refundprice")
    private String refundprice;
    @SerializedName("finalprice")
    private String finalprice;
    @SerializedName("ticketrefund")
    private int ticketrefund;

    private Boolean isSelect;

    //-----------------------------------------------

    public PassengerFlightDomestic() {
    }

    //-----------------------------------------------

    public String getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public String geteName() {
        return eName;
    }

    public String geteFamily() {
        return eFamily;
    }

    public String getRefundprice() {
        return refundprice;
    }

    public String getNid() {
        return nid;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public int getTypeString(int index) {
        int typeP = Integer.valueOf(type);
        if (typeP == 1) {
            return R.string.adults;
        } else if (typeP == 2) {
            return R.string.children;
        } else if (typeP == 3) {
            return R.string.infant;
        }
        return -1;
    }

    public Boolean isSelected() {
        if (isSelect == null)
            return false;
        return isSelect;
    }

    public String getFprice() {
        return fprice;
    }

    public String getFinalprice() {
        return finalprice;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    public int getTicketrefund() {
        return ticketrefund;
    }
}
