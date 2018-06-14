package hami.aniticket.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model;

import com.google.gson.annotations.SerializedName;

import hami.aniticket.Const.TrainRules;
import hami.aniticket.R;

/**
 * Created by renjer on 2017-05-25.
 */

public class ParamTrain {

    @SerializedName("date")
    private String date;
    @SerializedName("numberp")
    private String numberp;
    @SerializedName("train_number")
    private String train_number;
    @SerializedName("cellphone")
    private String cellphone;
    @SerializedName("email")
    private String email;
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;
    @SerializedName("namep")
    private String[] namep;
    @SerializedName("familyp")
    private String[] familyp;
    @SerializedName("typep")
    private String[] typep;
    @SerializedName("typeT")
    private String typeT;
    @SerializedName("melicode")
    private String[] melicode;
    @SerializedName("meliat")
    private String[] meliat;
    @SerializedName("passport_number")
    private String[] passport_number;
    @SerializedName("nationality")
    private String[] nationality;
    @SerializedName("birthday")
    private String[] birthday;
    @SerializedName("Price")
    private PricePassengerTrainParam Price;
    @SerializedName("time")
    private String time;
    @SerializedName("iscope")
    private String iscope;
    @SerializedName("wagon")
    private String wagon;
    @SerializedName("fprice")
    private String fprice;
    @SerializedName("discont")
    private String discont;
    @SerializedName("AllPrice")
    private String AllPrice;
    @SerializedName("wagonname")
    private String wagonname;
    //-----------------------------------------------

    public ParamTrain() {
    }

    //-----------------------------------------------

    public String getDate() {
        return date;
    }

    public String getNumberp() {
        return numberp;
    }

    public String getTrain_number() {
        return train_number;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getEmail() {
        return email;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String[] getNamep() {
        return namep;
    }

    public String[] getFamilyp() {
        return familyp;
    }

    public int getPassengerTypeReSource(int index) {
        int typePassengerApp = Integer.valueOf(typep[index]);
        if (typePassengerApp == TrainRules.TP_ADULTS) {
            return R.string.adults;
        } else if (typePassengerApp == TrainRules.TP_CHILD) {
            return R.string.children;
        } else if (typePassengerApp == TrainRules.TP_SHAHED) {
            return R.string.shahed;
        } else {
            return R.string.veteran;
        }
    }

    public String[] getTypep() {
        return typep;
    }

    public String getTypeT() {
        return typeT;
    }

    public String[] getMelicode() {
        return melicode;
    }

    public String[] getMeliat() {
        return meliat;
    }

    public String[] getPassport_number() {
        return passport_number;
    }

    public String[] getNationality() {
        return nationality;
    }

    public String[] getBirthday() {
        return birthday;
    }

    public PricePassengerTrainParam getPrice() {
        return Price;
    }

    public String getPriceByPassenger(int index) {
        if (typep[index].contentEquals("1")) {
            return Price.getAdults();
        } else if (typep[index].contentEquals("2")) {
            return Price.getChild();
        } else if (typep[index].contentEquals("4")) {
            return Price.getShahed();
        } else if (typep[index].contentEquals("5")) {
            return Price.getVeteren();
        } else {
            return "0";
        }
    }

    public String getTime() {
        return time;
    }

    public String getIscope() {
        return iscope;
    }

    public String getWagon() {
        return wagon;
    }

    public String getFprice() {
        return fprice;
    }

    public String getDiscont() {
        return discont;
    }

    public String getAllPrice() {
        return AllPrice;
    }

    public String getWagonname() {
        return wagonname;
    }
}
