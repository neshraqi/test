package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.PassengerInfo;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.SelectItemPassengerInternational;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;


public class ListPassengerInternationalListAdapter extends RecyclerView.Adapter<ListPassengerInternationalListAdapter.MyViewHolder> {

    private ArrayList<PassengerInfo> listItem;
    private Context context;
    private SelectItemPassengerInternational selectItemPassengerInternational;
    private ArrayList<String> listPrice;

    //-----------------------------------------------
    public ListPassengerInternationalListAdapter(Context context,
                                                 ArrayList<String> listPrice,
                                                 ArrayList<PassengerInfo> list,
                                                 SelectItemPassengerInternational selectItemPassengerInternational) {
        this.selectItemPassengerInternational = selectItemPassengerInternational;
        listItem = list;
        this.listPrice = listPrice;
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public ListPassengerInternationalListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_service_flight_passnger_pre_reserve, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        final PassengerInfo passengerInfo = listItem.get(position);
        viewHolder.txtFullName.setText(passengerInfo.getName() + " " + passengerInfo.getFamily());
        viewHolder.txtTypePassenger.setText(passengerInfo.getTypePassengerResource());

        String passport = "";
        if (passengerInfo.getPassportNumber() == null || passengerInfo.getPassportNumber().length() >= 1) {
            passport = "شماره پاسپورت:" + passengerInfo.getPassportNumber();
        } else {
            passport = "شماره پاسپورت:--------";
        }
        if (passengerInfo.getHasValidate() == 1) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
            viewHolder.itemView.startAnimation(vibrateAnimation);
        }
        int visible = passengerInfo.getHasError() ? View.VISIBLE : View.GONE;
        viewHolder.imgError.setVisibility(visible);
        viewHolder.txtCoNational.setText(passport);
        viewHolder.txtDateExpirePassport.setText(passengerInfo.getExpDate());
        viewHolder.txtBirthDay.setText(passengerInfo.getBirthday());
        viewHolder.txtPrice.setText(getFinalPrice(passengerInfo.getPriceByPassenger(listPrice)));
    }

    //-----------------------------------------------
    private String getFinalPrice(String price) {
        String finalPrice = "";
        try {

            finalPrice = price.replace(",", "");
            //finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice) / 10);
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice));
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
        public TextView txtDateExpirePassport;
        public TextView txtBirthDay;
        public TextView txtPrice;
        public ImageView imgError;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtFullName = (TextView) itemLayoutView.findViewById(R.id.txtFullname);
            txtTypePassenger = (TextView) itemLayoutView.findViewById(R.id.txtTypePassenger);
            txtCoNational = (TextView) itemLayoutView.findViewById(R.id.txtCoNational);
            itemLayoutView.findViewById(R.id.layoutDate).setVisibility(View.VISIBLE);
            txtBirthDay = (TextView) itemLayoutView.findViewById(R.id.txtBirthDay);
            txtDateExpirePassport = (TextView) itemLayoutView.findViewById(R.id.txtDateExpirePassport);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.txtPrice);
            imgError = (ImageView) itemLayoutView.findViewById(R.id.imgError);
            itemLayoutView.findViewById(R.id.rowLayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItemPassengerInternational.onSelectItem(listItem.get(getAdapterPosition()), getAdapterPosition());
                }
            });
            // txtTypePrice = itemLayoutView.findViewById(R.id.txtTypePrice);
        }
    }

    //-----------------------------------------------
    public void update(int index, PassengerInfo passengerInfo) {
        listItem.set(index, passengerInfo);
        notifyItemChanged(index);
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public ArrayList getListPassenger() {
        return listItem;
    }

    //-----------------------------------------------
    public Boolean validateInfoPassenger() {
        for (int index = 0; index < listItem.size(); index++) {
            if (listItem.get(index).getHasError()) {
                PassengerInfo passengerInfo = listItem.get(index);
                passengerInfo.setHasValidate(1);
                update(index, passengerInfo);
                return false;
            }
        }
        return true;
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }

}