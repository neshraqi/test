package hami.hamibelit.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2017-03-08.
 */

public class TrainResponse implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("number")
    private String number;
    @SerializedName("wagontype")
    private String wagonType;
    @SerializedName("wagonname")
    private String wagonName;
    @SerializedName("moveDate")
    private String moveDate;
    @SerializedName("exitTime")
    private String exitTime;
    @SerializedName("capacity")
    private String capacity;
    @SerializedName("SoldCount")
    private String soldCount;
    @SerializedName("degree")
    private String degree;
    @SerializedName("CircularNumberSerial")
    private String CircularNumberSerial;
    @SerializedName("price")
    private String price;
    @SerializedName("pricefinal")
    private String pricefinal;
    @SerializedName("price2")
    private long price2;
    @SerializedName("fullPrice")
    private String fullPrice;
    @SerializedName("discountprice")
    private String discountprice;
    @SerializedName("compartmentCapicity")
    private String compartmentCapicity;
    @SerializedName("isCompartment")
    private String isCompartment;
    @SerializedName("rateCode")
    private String rateCode;
    @SerializedName("RationCode")
    private String RationCode;
    @SerializedName("airConditioning")
    private String airConditioning;
    @SerializedName("media")
    private String media;
    @SerializedName("TimeOfArrival")
    private String timeOfArrival;
    @SerializedName("PathCode")
    private String pathCode;
    @SerializedName("nexday")
    private String nexDay;
    @SerializedName("preday")
    private String preDay;
    @SerializedName("Owner")
    private String Owner;
    @SerializedName("Ownerfa")
    private String OwnerFa;
    @SerializedName("fromen")
    private String fromEn;
    @SerializedName("toen")
    private String toeN;
    @SerializedName("fromfa")
    private String fromFa;
    @SerializedName("tofa")
    private String toFa;
    @SerializedName("typeT")
    private String typeT;
    @SerializedName("iscope")
    private String isCope;
    @SerializedName("daytime")
    private String dayTime;
    @SerializedName("daytimetext")
    private String dayTimeText;
    @SerializedName("pid")
    private String pid;
    //-----------------------------------------------

    public TrainResponse() {
    }

    //-----------------------------------------------

    protected TrainResponse(Parcel in) {
        id = in.readString();
        number = in.readString();
        wagonType = in.readString();
        wagonName = in.readString();
        moveDate = in.readString();
        exitTime = in.readString();
        capacity = in.readString();
        soldCount = in.readString();
        degree = in.readString();
        CircularNumberSerial = in.readString();
        price = in.readString();
        fullPrice = in.readString();
        price2 = in.readLong();
        pricefinal = in.readString();
        discountprice = in.readString();
        compartmentCapicity = in.readString();
        isCompartment = in.readString();
        rateCode = in.readString();
        RationCode = in.readString();
        airConditioning = in.readString();
        media = in.readString();
        timeOfArrival = in.readString();
        pathCode = in.readString();
        nexDay = in.readString();
        preDay = in.readString();
        Owner = in.readString();
        OwnerFa = in.readString();
        fromEn = in.readString();
        toeN = in.readString();
        fromFa = in.readString();
        toFa = in.readString();
        typeT = in.readString();
        isCope = in.readString();
        dayTime = in.readString();
        dayTimeText = in.readString();
        pid = in.readString();
    }

    public static final Creator<TrainResponse> CREATOR = new Creator<TrainResponse>() {
        @Override
        public TrainResponse createFromParcel(Parcel in) {
            return new TrainResponse(in);
        }

        @Override
        public TrainResponse[] newArray(int size) {
            return new TrainResponse[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getWagonType() {
        return wagonType;
    }

    public String getWagonName() {
        return wagonName;
    }

    public String getMoveDate() {
        return moveDate;
    }

    public String getExitTime() {
        return exitTime;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getSoldCount() {
        return soldCount;
    }

    public String getDegree() {
        return degree;
    }

    public String getCircularNumberSerial() {
        return CircularNumberSerial;
    }

    public String getPricefinal() {
        return pricefinal;
    }

    public String getPrice() {
        return price;
    }

    public long getPriceToman() {
        return price2;
    }

    public String getFullPriceRial() {
        return fullPrice;
    }

    public String getCompartmentCapicity() {
        return compartmentCapicity;
    }

    public String getCompartmentCapicityTitle() {
        return compartmentCapicity;
    }

    public String getIsCompartment() {
        return isCompartment;
    }

    public String getRateCode() {
        return rateCode;
    }

    public String getRationCode() {
        return RationCode;
    }

    public String getAirConditioning() {
        return airConditioning;
    }

    public String getMedia() {
        return media;
    }

    public String getTimeOfArrival() {
        return timeOfArrival;
    }

    public String getPathCode() {
        return pathCode;
    }

    public String getNexDay() {
        return nexDay;
    }

    public String getPreDay() {
        return preDay;
    }

    public String getOwner() {
        return Owner;
    }

    public String getOwnerFa() {
        return OwnerFa;
    }

    public String getFromEn() {
        return fromEn;
    }

    public String getToeN() {
        return toeN;
    }

    public String getFromFa() {
        return fromFa;
    }

    public String getToFa() {
        return toFa;
    }

    public String getTypeT() {
        return typeT;
    }

    public String getIsCope() {
        return isCope;
    }

    public String getDayTime() {
        return dayTime;
    }

    public String getDayTimeText() {
        return dayTimeText;
    }

    public String getPid() {
        return pid;
    }

    public String getDiscountprice() {
        return discountprice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(number);
        dest.writeString(wagonType);
        dest.writeString(wagonName);
        dest.writeString(moveDate);
        dest.writeString(exitTime);
        dest.writeString(capacity);
        dest.writeString(soldCount);
        dest.writeString(degree);
        dest.writeString(CircularNumberSerial);
        dest.writeString(price);
        dest.writeString(pricefinal);
        dest.writeString(discountprice);
        dest.writeLong(price2);
        dest.writeString(fullPrice);
        dest.writeString(compartmentCapicity);
        dest.writeString(isCompartment);
        dest.writeString(rateCode);
        dest.writeString(RationCode);
        dest.writeString(airConditioning);
        dest.writeString(media);
        dest.writeString(timeOfArrival);
        dest.writeString(pathCode);
        dest.writeString(nexDay);
        dest.writeString(preDay);
        dest.writeString(Owner);
        dest.writeString(OwnerFa);
        dest.writeString(fromEn);
        dest.writeString(toeN);
        dest.writeString(fromFa);
        dest.writeString(toFa);
        dest.writeString(typeT);
        dest.writeString(isCope);
        dest.writeString(dayTime);
        dest.writeString(dayTimeText);
        dest.writeString(pid);
    }
}
