package com.hami.serviceflight.Services.International.Controller.Model2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by renjer on 2017-11-02.
 */

public class FinalFlightInternationalIati implements Parcelable {

    public final static int TYPE_WENT_AND_RETURN = 4;
    private AllFlightInternationalIati allFlightInternationalIatiWent;
    private AllFlightInternationalIati allFlightInternationalIatiReturn;
    private long priceAdults, priceChild, priceInfant , disCountAdultPrice;

    //-----------------------------------------------


    public FinalFlightInternationalIati() {
    }
    //-----------------------------------------------

    public FinalFlightInternationalIati(AllFlightInternationalIati allFlightInternationalIatiWent, AllFlightInternationalIati allFlightInternationalIatiReturn, long priceAdults,long disCountAdultPrice, long priceChild, long priceInfant) {
        this.allFlightInternationalIatiWent = allFlightInternationalIatiWent;
        this.allFlightInternationalIatiReturn = allFlightInternationalIatiReturn;
        this.priceAdults = priceAdults;
        this.disCountAdultPrice = disCountAdultPrice;
        this.priceChild = priceChild;
        this.priceInfant = priceInfant;
    }
    //-----------------------------------------------

    public FinalFlightInternationalIati(AllFlightInternationalIati allFlightInternationalIatiWent) {
        this.allFlightInternationalIatiWent = allFlightInternationalIatiWent;
    }


    protected FinalFlightInternationalIati(Parcel in) {
        allFlightInternationalIatiWent = in.readParcelable(AllFlightInternationalIati.class.getClassLoader());
        allFlightInternationalIatiReturn = in.readParcelable(AllFlightInternationalIati.class.getClassLoader());
        priceAdults = in.readLong();
        disCountAdultPrice = in.readLong();
        priceChild = in.readLong();
        priceInfant = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(allFlightInternationalIatiWent, flags);
        dest.writeParcelable(allFlightInternationalIatiReturn, flags);
        dest.writeLong(priceAdults);
        dest.writeLong(disCountAdultPrice);
        dest.writeLong(priceChild);
        dest.writeLong(priceInfant);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FinalFlightInternationalIati> CREATOR = new Creator<FinalFlightInternationalIati>() {
        @Override
        public FinalFlightInternationalIati createFromParcel(Parcel in) {
            return new FinalFlightInternationalIati(in);
        }

        @Override
        public FinalFlightInternationalIati[] newArray(int size) {
            return new FinalFlightInternationalIati[size];
        }
    };

    public AllFlightInternationalIati getAllFlightInternationalIatiWent() {
        return allFlightInternationalIatiWent;
    }

    public long getPriceAdults() {
        return priceAdults;
    }

    public long getPriceChild() {
        return priceChild;
    }

    public long getPriceInfant() {
        return priceInfant;
    }

    public long getDisCountAdultPrice() {
        return disCountAdultPrice;
    }

    public void setAllFlightInternationalIatiWent(AllFlightInternationalIati allFlightInternationalIatiWent) {
        this.allFlightInternationalIatiWent = allFlightInternationalIatiWent;
    }

    public AllFlightInternationalIati getAllFlightInternationalIatiReturn() {
        return allFlightInternationalIatiReturn;
    }

    public void setAllFlightInternationalIatiReturn(AllFlightInternationalIati allFlightInternationalIatiReturn) {
        this.allFlightInternationalIatiReturn = allFlightInternationalIatiReturn;
    }

    public void setPriceAdults(long priceAdults) {
        this.priceAdults = priceAdults;
    }

    public void setPriceChild(long priceChild) {
        this.priceChild = priceChild;
    }

    public void setPriceInfant(long priceInfant) {
        this.priceInfant = priceInfant;
    }

    public void setDisCountAdultPrice(long disCountAdultPrice) {
        this.disCountAdultPrice = disCountAdultPrice;
    }
}
