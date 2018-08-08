package hami.mainapp.Activity.ServiceHotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-02-10.
 */

public class DomesticHotelRoomOption implements Parcelable {
    @SerializedName("option_key")
    private String option_key;
    @SerializedName("option_name")
    private String option_name;
    @SerializedName("option_description")
    private String option_description;
    //-----------------------------------------------

    public DomesticHotelRoomOption() {
    }
    //-----------------------------------------------

    protected DomesticHotelRoomOption(Parcel in) {
        option_key = in.readString();
        option_name = in.readString();
        option_description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(option_key);
        dest.writeString(option_name);
        dest.writeString(option_description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DomesticHotelRoomOption> CREATOR = new Creator<DomesticHotelRoomOption>() {
        @Override
        public DomesticHotelRoomOption createFromParcel(Parcel in) {
            return new DomesticHotelRoomOption(in);
        }

        @Override
        public DomesticHotelRoomOption[] newArray(int size) {
            return new DomesticHotelRoomOption[size];
        }
    };

    public String getOption_key() {
        return option_key;
    }

    public String getOption_name() {
        return option_name;
    }

    public String getOption_description() {
        return option_description;
    }
}
