package hami.nasimbehesht724.Activity.ServiceTour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-12-21.
 */

public class IdName implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    //-----------------------------------------------

    public IdName() {
    }
    //-----------------------------------------------

    protected IdName(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IdName> CREATOR = new Creator<IdName>() {
        @Override
        public IdName createFromParcel(Parcel in) {
            return new IdName(in);
        }

        @Override
        public IdName[] newArray(int size) {
            return new IdName[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
