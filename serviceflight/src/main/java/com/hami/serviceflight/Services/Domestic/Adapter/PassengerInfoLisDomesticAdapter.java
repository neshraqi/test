package com.hami.serviceflight.Services.Domestic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hami.common.Util.UtilFonts;
import com.hami.serviceflight.R;
import com.hami.serviceflight.Services.Domestic.Controller.Model.DomesticParams;
import com.hami.serviceflight.Services.Domestic.Controller.Model.DomesticPassengerInfo;

import java.text.NumberFormat;
import java.util.Locale;


/**
 * Created by renjer on 1/8/2017.
 */

public class PassengerInfoLisDomesticAdapter extends RecyclerView.Adapter<PassengerInfoLisDomesticAdapter.MyViewHolder> {

    private Context context;
    DomesticParams domesticParams;
    private static final String TAG = "PassengerInfoLisDomesticAdapter";

    //-----------------------------------------------
    public PassengerInfoLisDomesticAdapter(Context context, DomesticParams domesticParams) {
        this.context = context;
        this.domesticParams = domesticParams;
    }

    //-----------------------------------------------
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_flight_passnger_pre_reserve, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        viewHolder.txtFullName.setText(domesticParams.getName()[position] + " " + domesticParams.getFamily()[position]);
        viewHolder.txtTypePassenger.setText("مسافر " + context.getString(domesticParams.getTypeString(position)));
        String passport = "";
        Integer nationality = Integer.valueOf(domesticParams.getNationality()[position]);
        int international = domesticParams.getInternational();
        if (international == 1 || nationality == DomesticPassengerInfo.EXPORTING_COUNTRY_FOREIGN) {
            passport = "شماره پاسپورت:" + domesticParams.getPassport_number()[position];
            viewHolder.txtCoNational.setText(passport);
            viewHolder.layoutExpireDatePassport.setVisibility(View.VISIBLE);
            viewHolder.txtDateExpirePassport.setText(domesticParams.getExpdate()[position]);
        } else {
            viewHolder.txtCoNational.setText("کد ملی:" + domesticParams.getMelicode()[position]);
            viewHolder.layoutExpireDatePassport.setVisibility(View.GONE);
        }
        viewHolder.txtPrice.setText(getFinalPrice(domesticParams.getPrice()[position]));
    }

    //-----------------------------------------------
    private String getFinalPrice(String price) {
        String finalPrice = "";
        try {
            finalPrice = price;
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice) / 10);
            finalPrice += " تومان";

            return finalPrice;
        } catch (Exception e) {


            return price + " ریال";
        }

    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtFullName;
        public TextView txtTypePassenger;
        public TextView txtCoNational;
        public TextView txtPrice;
        public TextView txtDateExpirePassport;
        public RelativeLayout layoutBirthDay, layoutExpireDatePassport;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtFullName = itemLayoutView.findViewById(R.id.txtFullname);
            txtTypePassenger = itemLayoutView.findViewById(R.id.txtTypePassenger);
            txtCoNational = itemLayoutView.findViewById(R.id.txtCoNational);
            txtDateExpirePassport = itemLayoutView.findViewById(R.id.txtDateExpirePassport);
            layoutExpireDatePassport = itemLayoutView.findViewById(R.id.layoutExpireDatePassport);
            layoutExpireDatePassport.setVisibility(View.VISIBLE);
            layoutBirthDay = itemLayoutView.findViewById(R.id.layoutBirthDay);
            layoutBirthDay.setVisibility(View.GONE);
            txtPrice = itemLayoutView.findViewById(R.id.txtPrice);
        }
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        if (domesticParams != null) {
            return Integer.valueOf(domesticParams.getNumberp_()) > 0 ? Integer.valueOf(domesticParams.getNumberp_()) : 0;
        }
        return 0;
    }

}