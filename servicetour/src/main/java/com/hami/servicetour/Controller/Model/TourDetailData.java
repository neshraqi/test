package com.hami.servicetour.Controller.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by renjer on 2018-03-07.
 */

public class TourDetailData implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("share")
    private String share;
    @SerializedName("rereg")
    private String rereg;
    @SerializedName("tshow")
    private String tshow;
    @SerializedName("name")
    private String name;
    @SerializedName("leader")
    private String leader;
    @SerializedName("hardness")
    private String hardness;
    @SerializedName("last_rereg")
    private String last_rereg;
    @SerializedName("return_by")
    private String return_by;
    @SerializedName("go_by")
    private String go_by;
    @SerializedName("insurance")
    private String insurance;
    @SerializedName("end_time")
    private String end_time;
    @SerializedName("end_date")
    private String end_date;
    @SerializedName("start_time")
    private String start_time;
    @SerializedName("price_chd")
    private String price_chd;
    @SerializedName("start_date")
    private String start_date;
    @SerializedName("tto")
    private String tto;
    @SerializedName("tto_code")
    private String tto_code;
    @SerializedName("tfrom")
    private String tfrom;
    @SerializedName("tfrom_code")
    private String tfrom_code;
    @SerializedName("price_inf")
    private String price_inf;
    @SerializedName("price_adl")
    private String price_adl;
    @SerializedName("equipment")
    private String equipment;
    @SerializedName("stay_in")
    private String stay_in;
    @SerializedName("night_count")
    private String night_count;
    @SerializedName("day_count")
    private String day_count;
    @SerializedName("simcard")
    private String simcard;
    @SerializedName("transfer")
    private String transfer;
    @SerializedName("visa")
    private String visa;
    @SerializedName("hotel_tax")
    private String hotel_tax;
    @SerializedName("water")
    private String water;
    @SerializedName("cross_city_transfer")
    private String cross_city_transfer;
    @SerializedName("Catering")
    private String Catering;
    @SerializedName("meals")
    private String meals;
    @SerializedName("hami_markup")
    private String hami_markup;
    @SerializedName("description")
    private String description;
    @SerializedName("available")
    private String available;
    @SerializedName("city_tour")
    private String city_tour;
    @SerializedName("allowed_cargo")
    private String allowed_cargo;
    @SerializedName("places_tickets")
    private String places_tickets;
    @SerializedName("status")
    private String status;
    @SerializedName("img")
    private String img;
    @SerializedName("kind")
    private String kind;
    @SerializedName("CreatorHamiUserId")
    private String CreatorHamiUserId;
    @SerializedName("creatorUrl")
    private String creatorUrl;
    @SerializedName("accountApiHamiUser")
    private String accountApiHamiUser;
    @SerializedName("comment")
    private String comment;
    @SerializedName("price_single")
    private String price_single;
    @SerializedName("contact_link")
    private String contact_link;
    @SerializedName("inter_comment")
    private String inter_comment;
    @SerializedName("url")
    private String url;
    @SerializedName("online_sell")
    private String online_sell;
    @SerializedName("rate_count")
    private String rate_count;
    @SerializedName("rate_total")
    private String rate_total;
    @SerializedName("reg_time")
    private String reg_time;
    @SerializedName("reg_by")
    private String reg_by;
    @SerializedName("contact_mob")
    private String contact_mob;
    @SerializedName("go_company")
    private String go_company;
    @SerializedName("go_class")
    private String go_class;
    @SerializedName("go_number")
    private String go_number;
    @SerializedName("return_company")
    private String return_company;
    @SerializedName("return_class")
    private String return_class;
    @SerializedName("return_number")
    private String return_number;
    //-----------------------------------------------

    public TourDetailData() {
    }
    //-----------------------------------------------

    protected TourDetailData(Parcel in) {
        id = in.readString();
        share = in.readString();
        rereg = in.readString();
        tshow = in.readString();
        name = in.readString();
        leader = in.readString();
        hardness = in.readString();
        last_rereg = in.readString();
        return_by = in.readString();
        go_by = in.readString();
        insurance = in.readString();
        end_time = in.readString();
        end_date = in.readString();
        start_time = in.readString();
        price_chd = in.readString();
        start_date = in.readString();
        tto = in.readString();
        tto_code = in.readString();
        tfrom = in.readString();
        tfrom_code = in.readString();
        price_inf = in.readString();
        price_adl = in.readString();
        equipment = in.readString();
        stay_in = in.readString();
        night_count = in.readString();
        day_count = in.readString();
        simcard = in.readString();
        transfer = in.readString();
        visa = in.readString();
        hotel_tax = in.readString();
        water = in.readString();
        cross_city_transfer = in.readString();
        Catering = in.readString();
        meals = in.readString();
        hami_markup = in.readString();
        description = in.readString();
        available = in.readString();
        city_tour = in.readString();
        allowed_cargo = in.readString();
        places_tickets = in.readString();
        status = in.readString();
        img = in.readString();
        kind = in.readString();
        CreatorHamiUserId = in.readString();
        creatorUrl = in.readString();
        accountApiHamiUser = in.readString();
        comment = in.readString();
        price_single = in.readString();
        contact_link = in.readString();
        inter_comment = in.readString();
        url = in.readString();
        online_sell = in.readString();
        rate_count = in.readString();
        rate_total = in.readString();
        reg_time = in.readString();
        reg_by = in.readString();
        contact_mob = in.readString();
        go_company = in.readString();
        go_class = in.readString();
        go_number = in.readString();
        return_company = in.readString();
        return_class = in.readString();
        return_number = in.readString();
    }

    public static final Creator<TourDetailData> CREATOR = new Creator<TourDetailData>() {
        @Override
        public TourDetailData createFromParcel(Parcel in) {
            return new TourDetailData(in);
        }

        @Override
        public TourDetailData[] newArray(int size) {
            return new TourDetailData[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getShare() {
        return share;
    }

    public String getRereg() {
        return rereg;
    }

    public String getTshow() {
        return tshow;
    }

    public String getName() {
        return name;
    }

    public String getLeader() {
        if (leader == null)
            return "0";
        return leader;
    }

    public String getHardness() {
        return hardness;
    }

    public String getLast_rereg() {
        return last_rereg;
    }

    public String getReturn_by() {
        return return_by;
    }

    public String getGo_by() {
        return go_by;
    }

    public String getInsurance() {
        if (insurance == null)
            return "0";
        return insurance;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getPrice_chd() {
        return price_chd;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getTto() {
        return tto;
    }

    public String getTto_code() {
        return tto_code;
    }

    public String getTfrom() {
        return tfrom;
    }

    public String getTfrom_code() {
        return tfrom_code;
    }

    public String getPrice_inf() {
        return price_inf;
    }

    public String getPrice_adl() {
        return price_adl;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getStay_in() {
        return stay_in;
    }

    public String getNight_count() {
        return night_count;
    }

    public String getDay_count() {
        return day_count;
    }

    public String getSimcard() {
        if (simcard == null)
            return "0";
        return simcard;
    }

    public String getTransfer() {
        return transfer;
    }

    public String getVisa() {
        if (visa == null)
            return "0";
        return visa;
    }

    public String getHotel_tax() {
        return hotel_tax;
    }

    public String getWater() {
        return water;
    }

    public String getCross_city_transfer() {
        if (cross_city_transfer == null)
            return "0";
        return cross_city_transfer;
    }

    public String getCatering() {
        return Catering;
    }

    public String getMeals() {
        return meals;
    }

    public String getHami_markup() {
        return hami_markup;
    }

    public String getDescription() {
        return description;
    }

    public String getAvailable() {
        return available;
    }

    public String getCity_tour() {
        return city_tour;
    }

    public String getAllowed_cargo() {
        return allowed_cargo;
    }

    public String getPlaces_tickets() {
        return places_tickets;
    }

    public String getStatus() {
        return status;
    }

    public String getImg() {
        return img;
    }

    public String getKind() {
        return kind;
    }

    public String getCreatorHamiUserId() {
        return CreatorHamiUserId;
    }

    public String getCreatorUrl() {
        return creatorUrl;
    }

    public String getAccountApiHamiUser() {
        return accountApiHamiUser;
    }

    public String getComment() {
        return comment;
    }

    public String getPrice_single() {
        return price_single;
    }

    public String getContact_link() {
        return contact_link;
    }

    public String getInter_comment() {
        return inter_comment;
    }

    public String getUrl() {
        return url;
    }

    public String getOnline_sell() {
        return online_sell;
    }

    public String getRate_count() {
        return rate_count;
    }

    public String getRate_total() {
        return rate_total;
    }

    public String getReg_time() {
        return reg_time;
    }

    public String getReg_by() {
        return reg_by;
    }

    public String getContact_mob() {
        return contact_mob;
    }

    public String getGo_company() {
        return go_company;
    }

    public String getGo_class() {
        return go_class;
    }

    public String getGo_number() {
        return go_number;
    }

    public String getReturn_company() {
        return return_company;
    }

    public String getReturn_class() {
        return return_class;
    }

    public String getReturn_number() {
        return return_number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(share);
        dest.writeString(rereg);
        dest.writeString(tshow);
        dest.writeString(name);
        dest.writeString(leader);
        dest.writeString(hardness);
        dest.writeString(last_rereg);
        dest.writeString(return_by);
        dest.writeString(go_by);
        dest.writeString(insurance);
        dest.writeString(end_time);
        dest.writeString(end_date);
        dest.writeString(start_time);
        dest.writeString(price_chd);
        dest.writeString(start_date);
        dest.writeString(tto);
        dest.writeString(tto_code);
        dest.writeString(tfrom);
        dest.writeString(tfrom_code);
        dest.writeString(price_inf);
        dest.writeString(price_adl);
        dest.writeString(equipment);
        dest.writeString(stay_in);
        dest.writeString(night_count);
        dest.writeString(day_count);
        dest.writeString(simcard);
        dest.writeString(transfer);
        dest.writeString(visa);
        dest.writeString(hotel_tax);
        dest.writeString(water);
        dest.writeString(cross_city_transfer);
        dest.writeString(Catering);
        dest.writeString(meals);
        dest.writeString(hami_markup);
        dest.writeString(description);
        dest.writeString(available);
        dest.writeString(city_tour);
        dest.writeString(allowed_cargo);
        dest.writeString(places_tickets);
        dest.writeString(status);
        dest.writeString(img);
        dest.writeString(kind);
        dest.writeString(CreatorHamiUserId);
        dest.writeString(creatorUrl);
        dest.writeString(accountApiHamiUser);
        dest.writeString(comment);
        dest.writeString(price_single);
        dest.writeString(contact_link);
        dest.writeString(inter_comment);
        dest.writeString(url);
        dest.writeString(online_sell);
        dest.writeString(rate_count);
        dest.writeString(rate_total);
        dest.writeString(reg_time);
        dest.writeString(reg_by);
        dest.writeString(contact_mob);
        dest.writeString(go_company);
        dest.writeString(go_class);
        dest.writeString(go_number);
        dest.writeString(return_company);
        dest.writeString(return_class);
        dest.writeString(return_number);
    }
}
