package hami.mainapp.Activity.ServiceHotel.International.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HotelsMoreInfoImages implements Parcelable {
    @SerializedName("Image")
    private ArrayList<String> imageList;
    //-----------------------------------------------

    public HotelsMoreInfoImages() {
    }
    //-----------------------------------------------

    protected HotelsMoreInfoImages(Parcel in) {
        imageList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(imageList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HotelsMoreInfoImages> CREATOR = new Creator<HotelsMoreInfoImages>() {
        @Override
        public HotelsMoreInfoImages createFromParcel(Parcel in) {
            return new HotelsMoreInfoImages(in);
        }

        @Override
        public HotelsMoreInfoImages[] newArray(int size) {
            return new HotelsMoreInfoImages[size];
        }
    };

    public ArrayList<String> getImageList() {
        return imageList;
    }
}
