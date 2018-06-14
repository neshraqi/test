package hami.nasimbehesht724.Activity.PastPurchases.Adapter;

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

import hami.nasimbehesht724.Activity.PastPurchases.Model.PassengerFlightDomestic;
import hami.nasimbehesht724.BaseController.SelectItemList;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilFonts;


/**
 * Created by renjer on 1/8/2017.
 */

public class PastPurchasesListTrainPassengerAdapter extends RecyclerView.Adapter<PastPurchasesListTrainPassengerAdapter.MyViewHolder> {

    private ArrayList<PassengerFlightDomestic> listItem;
    private Context context;
    private SelectItemList<PassengerFlightDomestic> selectItemList;
    private static final String TAG = "ActivityRegisterPassengerInternational";
    //-----------------------------------------------
    public PastPurchasesListTrainPassengerAdapter(Context context,
                                                  ArrayList<PassengerFlightDomestic> list,
                                                  SelectItemList<PassengerFlightDomestic> selectItemList) {
        this.selectItemList = selectItemList;
        listItem = list;
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public PastPurchasesListTrainPassengerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        if (passengerInfo.getNid() != null && passengerInfo.getNid().length() > 0) {
            viewHolder.txtCoNational.setText("کد ملی:" + passengerInfo.getNid());
        } else {
            passport = "شماره پاسپورت:" + passengerInfo.getPassportNumber();
            viewHolder.txtCoNational.setText(passport);
        }
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