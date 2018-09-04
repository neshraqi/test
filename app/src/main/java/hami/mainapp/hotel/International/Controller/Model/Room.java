package hami.mainapp.hotel.International.Controller.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-01-17.
 */

public class Room {

    @SerializedName("Name")
    private String Name;
    @SerializedName("AdultCount")
    private int AdultCount;
    @SerializedName("ChildCount")
    private int ChildCount;
    @SerializedName("ChildAges")
    private String[] ChildAges;
    @SerializedName("MealType")
    private String MealType;
    @SerializedName("SharingBedding")
    private Boolean SharingBedding;
    //-----------------------------------------------

    public Room() {
    }
    //-----------------------------------------------

    public String getName() {
        return Name;
    }

    public int getAdultCount() {
        return AdultCount;
    }

    public int getChildCount() {
        return ChildCount;
    }

    public String[] getChildAges() {
        return ChildAges;
    }

    public String getMealType() {
        return MealType;
    }

    public Boolean getSharingBedding() {
        return SharingBedding;
    }
}
