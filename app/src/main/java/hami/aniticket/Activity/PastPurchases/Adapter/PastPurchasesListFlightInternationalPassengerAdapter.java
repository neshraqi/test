package hami.aniticket.Activity.PastPurchases.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hami.aniticket.Activity.PastPurchases.Model.PassengerFlightDomestic;
import hami.aniticket.BaseController.SelectItemList;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;


/**
 * Created by renjer on 1/8/2017.
 */

public class PastPurchasesListFlightInternationalPassengerAdapter extends RecyclerView.Adapter<PastPurchasesListFlightInternationalPassengerAdapter.MyViewHolder> {

    private ArrayList<PassengerFlightDomestic> listItem;
    private Context context;
    private SelectItemList<PassengerFlightDomestic> selectItemList;
    private static final String TAG = "PastPurchasesListFlightInternationalPassengerAdapter";
    //-----------------------------------------------
    public PastPurchasesListFlightInternationalPassengerAdapter(Context context,
                                                                ArrayList<PassengerFlightDomestic> list,
                                                                SelectItemList<PassengerFlightDomestic> selectItemList) {
        this.selectItemList = selectItemList;
        listItem = list;
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public PastPurchasesListFlightInternationalPassengerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_flight_domestic_ticket_info_passenger, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        PassengerFlightDomestic passengerInfo = listItem.get(position);
        viewHolder.txtFullName.setText(passengerInfo.getName() + " " + passengerInfo.getFamily());
        viewHolder.txtTypePassenger.setText("مسافر " + context.getString(passengerInfo.getTypeString(position)));
        String passport = "";
        passport = "شماره پاسپورت:" + passengerInfo.getPassportNumber();
        viewHolder.txtCoNational.setText(passport);
        viewHolder.txtPrice.setText(getFinalPrice(passengerInfo.getPrice()));
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
    public void clearList() {
        listItem = new ArrayList<>();
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView txtFullName;
        public TextView txtTypePassenger;
        public TextView txtCoNational;
        public TextView txtBirthDay;
        public TextView txtPrice;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtFullName = (TextView) itemLayoutView.findViewById(R.id.txtFullname);
            txtTypePassenger = (TextView) itemLayoutView.findViewById(R.id.txtTypePassenger);
            txtCoNational = (TextView) itemLayoutView.findViewById(R.id.txtCoNational);
            //txtCoPassport = (TextView) itemLayoutView.findViewById(R.id.txtCoPassport);
            txtBirthDay = (TextView) itemLayoutView.findViewById(R.id.txtBirthDay);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.txtPrice);
            // txtTypePrice = itemLayoutView.findViewById(R.id.txtTypePrice);
        }

    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }
}