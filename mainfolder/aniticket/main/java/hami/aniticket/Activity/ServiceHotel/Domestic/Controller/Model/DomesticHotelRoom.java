package hami.hamibelit.Activity.ServiceHotel.Domestic.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by renjer on 2018-02-10.
 */

public class DomesticHotelRoom implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("nameFa")
    private String nameFa;
    @SerializedName("type")
    private String type;
    @SerializedName("persons")
    private String persons;
    @SerializedName("beds")
    private String beds;
    @SerializedName("singleBeds")
    private String singleBeds;
    @SerializedName("doubleBeds")
    private String doubleBeds;
    @SerializedName("sofa")
    private String sofa;
    @SerializedName("extraPersons")
    private int extraPersons;
    @SerializedName("onlineReservation")
    private String onlineReservation;
    @SerializedName("fullBoard")
    private String fullBoard;
    @SerializedName("isSpecial")
    private String isSpecial;
    @SerializedName("isSpecialDate")
    private String isSpecialDate;
    @SerializedName("sort")
    private String sort;
    @SerializedName("description")
    private String description;
    @SerializedName("image")
    private String image;
//    @SerializedName("hotelPrice")
//    private DomesticHotelRoomPrice hotelPrice;
    @SerializedName("price")
    private String price;
    @SerializedName("priceDiscount")
    private String priceDiscount;
    @SerializedName("show")
    private Boolean show;
    //@SerializedName("priceList")
    //private ArrayList<String> priceList;
    @SerializedName("days")
    private ArrayList<String> days;
    @SerializedName("extraPersonPrice")
    private ArrayList<String> extraPersonPrice;
    @SerializedName("priceBoard")
    private String priceBoard;
    //@SerializedName("boardList")
//private Object boardList;
    //-----------------------------------------------


    public DomesticHotelRoom() {
    }
    //-----------------------------------------------

    protected DomesticHotelRoom(Parcel in) {
        id = in.readString();
        nameFa = in.readString();
        type = in.readString();
        persons = in.readString();
        beds = in.readString();
        singleBeds = in.readString();
        doubleBeds = in.readString();
        sofa = in.readString();
        extraPersons = in.readInt();
        onlineReservation = in.readString();
        fullBoard = in.readString();

        isSpecial = in.readString();
        isSpecialDate = in.readString();
        sort = in.readString();
        description = in.readString();
        image = in.readString();
        //hotelPrice = in.readParcelable(DomesticHotelRoomPrice.class.getClassLoader());
        price = in.readString();
        //priceList = in.createStringArrayList();
        days = in.createStringArrayList();
        extraPersonPrice = in.createStringArrayList();
        priceBoard = in.readString();
        priceDiscount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nameFa);
        dest.writeString(type);
        dest.writeString(persons);
        dest.writeString(beds);
        dest.writeString(singleBeds);
        dest.writeString(doubleBeds);
        dest.writeString(sofa);
        dest.writeInt(extraPersons);
        dest.writeString(onlineReservation);
        dest.writeString(fullBoard);
        dest.writeString(isSpecial);
        dest.writeString(isSpecialDate);
        dest.writeString(sort);
        dest.writeString(description);
        dest.writeString(image);
        //dest.writeParcelable(hotelPrice, flags);
        dest.writeString(price);
        //dest.writeStringList(priceList);
        dest.writeStringList(days);
        dest.writeStringList(extraPersonPrice);
        dest.writeString(priceBoard);
        dest.writeString(priceDiscount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DomesticHotelRoom> CREATOR = new Creator<DomesticHotelRoom>() {
        @Override
        public DomesticHotelRoom createFromParcel(Parcel in) {
            return new DomesticHotelRoom(in);
        }

        @Override
        public DomesticHotelRoom[] newArray(int size) {
            return new DomesticHotelRoom[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getNameFa() {
        return nameFa;
    }

    public String getType() {
        return type;
    }

    public String getPersons() {
        return persons;
    }

    public String getBeds() {
        return beds;
    }

    public String getSingleBeds() {
        return singleBeds;
    }

    public String getDoubleBeds() {
        return doubleBeds;
    }

    public String getSofa() {
        return sofa;
    }

    public int getExtraPersons() {
        return extraPersons;
    }

    public String getOnlineReservation() {
        return onlineReservation;
    }

    public Boolean getFullBoard() {
        if (fullBoard != null)
            return Boolean.valueOf(fullBoard);
        return false;
    }

    public String getIsSpecial() {
        return isSpecial;
    }

    public String getIsSpecialDate() {
        return isSpecialDate;
    }

    public String getSort() {
        return sort;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

//    public DomesticHotelRoomPrice getHotelPrice() {
//        return hotelPrice;
//    }

    public String getPrice() {
        return price;
    }

    public String getPriceDiscount() {
        return priceDiscount;
    }

    public Boolean getShow() {
        return show;
    }

//    public ArrayList<String> getPriceList() {
//        return priceList;
//    }

    public ArrayList<String> getDays() {
        return days;
    }

//    public Object getBoardList() {
//        return boardList;
//    }

    public ArrayList<String> getExtraPersonPrice() {
        return extraPersonPrice;
    }

    public String getPriceBoard() {
        return priceBoard;
    }
}
