package hami.nasimbehesht724.Activity.ServiceHotel.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by renjer on 2018-01-10.
 */

public class SearchDestination implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("countryId")
    private String countryId;
    //-----------------------------------------------

    public SearchDestination() {
    }
    //-----------------------------------------------

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountryId() {
        return countryId;
    }
}
