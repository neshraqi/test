package hami.hamibelit.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renjer on 1/19/2017.
 */

public class RegisterTrainRequest {

    @SerializedName("persons")
    private List<ListModelPassengerInfoTrain> listModelPassengerInfoTrains = new ArrayList<>();

    @SerializedName("phonnumber")
    private String phoneNumber;

    @SerializedName("email")
    private String email;

    @SerializedName("captcha-train")
    private String captchaTrain;

    @SerializedName("numberp")
    private String numberP;

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
    private String SoldCount;

    @SerializedName("degree")
    private String degree;

    @SerializedName("CircularNumberSerial")
    private String CircularNumberSerial;

    @SerializedName("price")
    private String price;

    @SerializedName("fullPrice")
    private String fullPrice;


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
    private String TimeOfArrival;

    @SerializedName("PathCode")
    private String PathCode;

    @SerializedName("nexday")
    private String nexday;

    @SerializedName("preday")
    private String preday;

    @SerializedName("Owner")
    private String Owner;

    @SerializedName("Ownerfa")
    private String Ownerfa;

    @SerializedName("fromen")
    private String fromen;

    @SerializedName("toen")
    private String toen;

    @SerializedName("fromfa")
    private String fromfa;

    @SerializedName("tofa")
    private String tofa;

    @SerializedName("typeT")
    private String typeT;

    @SerializedName("iscope")
    private String iscope;

    @SerializedName("daytime")
    private String daytime;

    @SerializedName("daytimetext")
    private String daytimetext;

    @SerializedName("pid")
    private String pid;

    //-----------------------------------------------
    public RegisterTrainRequest() {
    }
    //-----------------------------------------------
    public void addListModelPassengerInfoTrain(ListModelPassengerInfoTrain listModelPassengerInfoTrain){
        this.listModelPassengerInfoTrains.add(listModelPassengerInfoTrain);
    }
    //-----------------------------------------------
    public List<ListModelPassengerInfoTrain> getListModelPassengerInfoTrains() {
        return listModelPassengerInfoTrains;
    }

    public void setListModelPassengerInfoTrains(List<ListModelPassengerInfoTrain> listModelPassengerInfoTrains) {
        this.listModelPassengerInfoTrains = listModelPassengerInfoTrains;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaptchaTrain() {
        return captchaTrain;
    }

    public void setCaptchaTrain(String captchaTrain) {
        this.captchaTrain = captchaTrain;
    }

    public String getNumberP() {
        return numberP;
    }

    public void setNumberP(String numberP) {
        this.numberP = numberP;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWagonType() {
        return wagonType;
    }

    public void setWagonType(String wagonType) {
        this.wagonType = wagonType;
    }

    public String getWagonName() {
        return wagonName;
    }

    public void setWagonName(String wagonName) {
        this.wagonName = wagonName;
    }

    public String getMoveDate() {
        return moveDate;
    }

    public void setMoveDate(String moveDate) {
        this.moveDate = moveDate;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getSoldCount() {
        return SoldCount;
    }

    public void setSoldCount(String soldCount) {
        SoldCount = soldCount;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCircularNumberSerial() {
        return CircularNumberSerial;
    }

    public void setCircularNumberSerial(String circularNumberSerial) {
        CircularNumberSerial = circularNumberSerial;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(String fullPrice) {
        this.fullPrice = fullPrice;
    }

    public String getCompartmentCapicity() {
        return compartmentCapicity;
    }

    public void setCompartmentCapicity(String compartmentCapicity) {
        this.compartmentCapicity = compartmentCapicity;
    }

    public String getIsCompartment() {
        return isCompartment;
    }

    public void setIsCompartment(String isCompartment) {
        this.isCompartment = isCompartment;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public String getRationCode() {
        return RationCode;
    }

    public void setRationCode(String rationCode) {
        RationCode = rationCode;
    }

    public String getAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(String airConditioning) {
        this.airConditioning = airConditioning;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getTimeOfArrival() {
        return TimeOfArrival;
    }

    public void setTimeOfArrival(String timeOfArrival) {
        TimeOfArrival = timeOfArrival;
    }

    public String getPathCode() {
        return PathCode;
    }

    public void setPathCode(String pathCode) {
        PathCode = pathCode;
    }

    public String getNexday() {
        return nexday;
    }

    public void setNexday(String nexday) {
        this.nexday = nexday;
    }

    public String getPreday() {
        return preday;
    }

    public void setPreday(String preday) {
        this.preday = preday;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getOwnerfa() {
        return Ownerfa;
    }

    public void setOwnerfa(String ownerfa) {
        Ownerfa = ownerfa;
    }

    public String getFromen() {
        return fromen;
    }

    public void setFromen(String fromen) {
        this.fromen = fromen;
    }

    public String getToen() {
        return toen;
    }

    public void setToen(String toen) {
        this.toen = toen;
    }

    public String getFromfa() {
        return fromfa;
    }

    public void setFromfa(String fromfa) {
        this.fromfa = fromfa;
    }

    public String getTofa() {
        return tofa;
    }

    public void setTofa(String tofa) {
        this.tofa = tofa;
    }

    public String getTypeT() {
        return typeT;
    }

    public void setTypeT(String typeT) {
        this.typeT = typeT;
    }

    public String getIscope() {
        return iscope;
    }

    public void setIscope(String iscope) {
        this.iscope = iscope;
    }

    public String getDaytime() {
        return daytime;
    }

    public void setDaytime(String daytime) {
        this.daytime = daytime;
    }

    public String getDaytimetext() {
        return daytimetext;
    }

    public void setDaytimetext(String daytimetext) {
        this.daytimetext = daytimetext;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    //-----------------------------------------------
    @Override
    public String toString() {
        try {
            Exclude ex = new Exclude();
            Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(ex).addSerializationExclusionStrategy(ex).create();
            String jsonString = gson.toJson(this);
            return jsonString;
        } catch (Exception e) {
            e.getMessage();
        }
        return "";
    }
    //-----------------------------------------------

    class Exclude implements ExclusionStrategy {

        @Override
        public boolean shouldSkipClass(Class<?> arg0) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            SerializedName ns = field.getAnnotation(SerializedName.class);
            if (ns != null)
                return false;
            return true;
        }
    }
}
