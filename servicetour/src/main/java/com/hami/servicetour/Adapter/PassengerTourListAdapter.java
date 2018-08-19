package com.hami.servicetour.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hami.common.Const.FlightRules;
import com.hami.common.Util.UtilFonts;
import com.hami.servicetour.Controller.Model.PassengerTour;
import com.hami.servicetour.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class PassengerTourListAdapter extends RecyclerView.Adapter<PassengerTourListAdapter.MyViewHolder> {

    private ArrayList<PassengerTour> listItem;
    private Context context;
    private OnSelectItemPassengerTourListener onSelectItemPassengerTourListener;
    private Typeface typeface;
    private int infantCount = 0, adultsCount = 0, childCount = 0;
    private static final String TAG = "PassengerTourListAdapter";
    private String adultPrice, childPrice, infantPrice, adultSinglePrice;

    //-----------------------------------------------
    public PassengerTourListAdapter(Context context, OnSelectItemPassengerTourListener onSelectItemPassengerTourListener) {
        this.onSelectItemPassengerTourListener = onSelectItemPassengerTourListener;
        listItem = new ArrayList<>();
        this.context = context;
    }

    //-----------------------------------------------
    public void setConfigPrice(String adultPrice, String childPrice, String infantPrice, String adultSinglePrice) {
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.infantPrice = infantPrice;
        this.adultSinglePrice = adultSinglePrice;
    }

    //-----------------------------------------------
    @Override
    public PassengerTourListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_flight_domestic_info_passenger, null);
        PassengerTourListAdapter.MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final PassengerTourListAdapter.MyViewHolder viewHolder, final int position) {
        final PassengerTour passengerTour = listItem.get(position);
        viewHolder.btnRemoveItem.setVisibility((position == 0) ? View.INVISIBLE : View.VISIBLE);
        if (passengerTour.getPassengerType() == FlightRules.TP_ADULTS) {
            viewHolder.txtTypePassenger.setText("ویرایش اطلاعات مسافر " + context.getString(R.string.adults));
            passengerTour.setPrice(adultPrice);
        } else if (passengerTour.getPassengerType() == FlightRules.TP_CHILDREN) {
            viewHolder.txtTypePassenger.setText("ویرایش اطلاعات مسافر " + context.getString(R.string.children));
            listItem.get(position).setPrice(childPrice);
            passengerTour.setPriceSingle(adultSinglePrice);
        } else if (passengerTour.getPassengerType() == FlightRules.TP_INFANT) {
            viewHolder.txtTypePassenger.setText("ویرایش اطلاعات مسافر " + context.getString(R.string.infant));
            listItem.get(position).setPrice(infantPrice);
        }
        passengerTour.setPriceSingle(adultSinglePrice);
        String finalPrice = listItem.size() == 1 ? passengerTour.getPriceSingle() : passengerTour.getPrice();
        viewHolder.txtPrice.setText(getFinalPrice(finalPrice));
        viewHolder.txtFullNameEng.setText("نام کامل: " + passengerTour.getFirstNamePersian() + " " + passengerTour.getLastNamePersian());
        if (passengerTour.getNationalCode() != null && passengerTour.getNationalCode().length() > 0) {
            viewHolder.txtNationalCode.setText("کد ملی:" + passengerTour.getNationalCode());
        } else if (passengerTour.getPassportNumber() != null && passengerTour.getPassportNumber().length() > 0) {
            viewHolder.txtNationalCode.setText("پاسپورت:" + passengerTour.getPassportNumber());
        } else {
            viewHolder.txtNationalCode.setText("کد ملی:");
        }
        if (passengerTour.getHasValidate() != 1) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
            viewHolder.itemView.startAnimation(vibrateAnimation);
        }
        int visible = passengerTour.getHasError() == 1 ? View.VISIBLE : View.GONE;
        viewHolder.imgError.setVisibility(visible);

    }

    //-----------------------------------------------
    private String getFinalPrice(String price) {
        String finalPrice = "";
        try {
            finalPrice = price.replace(",", "");
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice) / 10);
            finalPrice += " تومان";

            return finalPrice;
        } catch (Exception e) {

            return price + " ریال";
        }

    }

    //-----------------------------------------------
    public ArrayList<PassengerTour> getListItem() {
        return listItem;
    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtPrice;
        public TextView txtFullNameEng;
        public ImageView imgTypePassenger;
        public TextView txtTypePassenger;
        public TextView txtNationalCode;
        public ImageView btnRemoveItem;
        public ImageView imgError;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.txtPrice);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            //txtFullName = (TextView) itemLayoutView.findViewById(R.id.txtFullName);
            txtFullNameEng = (TextView) itemLayoutView.findViewById(R.id.txtFullNameEng);
            txtNationalCode = (TextView) itemLayoutView.findViewById(R.id.txtNationalCode);
            imgTypePassenger = (ImageView) itemLayoutView.findViewById(R.id.imgTypePassenger);
            txtTypePassenger = (TextView) itemLayoutView.findViewById(R.id.txtTypePassenger);
            btnRemoveItem = (ImageView) itemLayoutView.findViewById(R.id.btnImgRemove);
            imgError = (ImageView) itemLayoutView.findViewById(R.id.imgError);

            btnRemoveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectItemPassengerTourListener.onRemoveItemFlightDomestic(listItem.get(getAdapterPosition()), getAdapterPosition());
                }
            });
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectItemPassengerTourListener.onSelectItemFlightDomestic(listItem.get(getAdapterPosition()), getAdapterPosition());
                }
            });


        }
    }

    //-----------------------------------------------
    public void addNewPassenger(PassengerTour domesticPassengerInfo) {
        if (domesticPassengerInfo.getPassengerType() == FlightRules.TP_ADULTS)
            adultsCount++;
        else if (domesticPassengerInfo.getPassengerType() == FlightRules.TP_CHILDREN)
            childCount++;
        else if (domesticPassengerInfo.getPassengerType() == FlightRules.TP_INFANT)
            infantCount++;
        listItem.add(domesticPassengerInfo);
        notifyItemInserted(listItem.size() - 1);
        updatePricePassengers();
    }

    //-----------------------------------------------
    private void updatePricePassengers() {
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public void editPassenger(PassengerTour domesticPassengerInfo, int position) {
        listItem.set(position, domesticPassengerInfo);
        notifyItemChanged(position);
        notifyItemRangeChanged(0, listItem.size() - 1);
        updatePricePassengers();
    }

    //-----------------------------------------------
    public void removePassenger(int position) {
        if (listItem.get(position).getPassengerType() == FlightRules.TP_ADULTS)
            adultsCount--;
        else if (listItem.get(position).getPassengerType() == FlightRules.TP_CHILDREN)
            childCount--;
        else if (listItem.get(position).getPassengerType() == FlightRules.TP_INFANT)
            infantCount--;
        listItem.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listItem.size());
        updatePricePassengers();
    }

    //-----------------------------------------------
    public Boolean hasValidateChild() {
        if (adultsCount == 0 && childCount > 0) {
            return false;
        }
        return true;
    }

    //-----------------------------------------------
    public Boolean validateInfoPassenger() {
        for (int index = 0; index < listItem.size(); index++) {
            if (listItem.get(index).getHasError() == 1) {
                PassengerTour passengerInfo = listItem.get(index);
                passengerInfo.setHasValidate(1);
                editPassenger(passengerInfo, index);
                return false;
            }
        }
        return true;
    }

    //-----------------------------------------------
    public Boolean hasValidateInfant() {
        return (adultsCount >= infantCount);
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
