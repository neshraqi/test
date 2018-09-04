package hami.mainapp.train.Services.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-05-25.
 */

public class PricePassengerTrainParam {
    @SerializedName("1")
    private String adults;
    @SerializedName("2")
    private String child;
    @SerializedName("4")
    private String shahed;
    @SerializedName("5")
    private String veteren;
    //-----------------------------------------------

    public PricePassengerTrainParam() {
    }

    //-----------------------------------------------


    public String getAdults() {
        return adults;
    }

    public String getChild() {
        return child;
    }

    public String getShahed() {
        return shahed;
    }

    public String getVeteren() {
        return veteren;
    }
}
