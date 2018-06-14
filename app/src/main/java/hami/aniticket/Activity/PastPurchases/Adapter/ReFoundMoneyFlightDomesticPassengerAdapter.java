package hami.aniticket.Activity.PastPurchases.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;



import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import hami.aniticket.Activity.PastPurchases.Model.PassengerFlightDomestic;
import hami.aniticket.Activity.PastPurchases.Model.ReFoundPassenger;
import hami.aniticket.Activity.PastPurchases.Model.ReFoundResponseFlightDomestic;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;


/**
 * Created by renjer on 1/8/2017.
 */

public class ReFoundMoneyFlightDomesticPassengerAdapter extends RecyclerView.Adapter<ReFoundMoneyFlightDomesticPassengerAdapter.MyViewHolder> {

    //private ArrayList<PassengerFlightDomestic> listItem;
    private Context context;
    private ReFoundResponseFlightDomestic result;
    private ArrayList<PassengerFlightDomestic> lists;
    private static final String TAG = "ReFoundMoneyFlightDomesticPassengerAdapter";

    //-----------------------------------------------
    public ReFoundMoneyFlightDomesticPassengerAdapter(Context context,
                                                      ReFoundResponseFlightDomestic result) {
        this.result = result;
        lists = result.getReFoundResponseDataFlightDomestic().getPassengerFlightDomestic();
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public ReFoundMoneyFlightDomesticPassengerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_flight_domestic_ticket_info_passenger_refound, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        PassengerFlightDomestic passengerInfo = lists.get(position);
        Long finalPrice = Long.valueOf(passengerInfo.getFinalprice().replace(",", ""));
        Long priceJarime = Long.valueOf(result.getReFoundResponseDataFlightDomestic().getReFoundPassengerResponse().getReFoundPassengers().get(position).getPricejarime());
        String priceJarimeFinal = String.valueOf((finalPrice - priceJarime));
        viewHolder.txtFullName.setText(passengerInfo.getName() + " " + passengerInfo.getFamily());
        viewHolder.txtPrice.setText("مبلغ پرداختی:" + getFinalPrice(String.valueOf(finalPrice)));
        viewHolder.txtReturnPrice.setText("مبلغ بازگشتی:" + getFinalPrice(priceJarimeFinal));
        viewHolder.txtFarePercent.setText(getFinePercent(passengerInfo.getId()) + "%");
        Boolean hasCheckRefundMoney = (passengerInfo.getTicketrefund() == 1);
        if (hasCheckRefundMoney || (((result.getNextIdPassenger() == null || result.getNextIdPassenger().length() == 0) && position == 0) ||
                (result.getNextIdPassenger().contentEquals(passengerInfo.getId())))) {
            viewHolder.smoothProgressBar.setVisibility(View.GONE);
            viewHolder.smoothProgressBar.progressiveStop();
        } else {
            viewHolder.smoothProgressBar.setVisibility(View.VISIBLE);
            viewHolder.smoothProgressBar.progressiveStart();
        }
        if (hasCheckRefundMoney) {
            viewHolder.txtStatusRefundMoney.setVisibility(View.VISIBLE);
        } else {
            viewHolder.txtStatusRefundMoney.setVisibility(View.GONE);
        }

    }

    //-----------------------------------------------
    private String getFinePercent(String id) {
        String finalFarePercent = "";
        try {
            for (ReFoundPassenger reFoundPassenger : result.getReFoundResponseDataFlightDomestic().getReFoundPassengerResponse().getReFoundPassengers())
                if (reFoundPassenger.getPassengers_id().contentEquals(id)) {
                    return reFoundPassenger.getJarimerefund();
                }
            return finalFarePercent;
        } catch (Exception e) {

            return "0%";
        }
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
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtFullName;
        public TextView txtPrice, txtFarePercent, txtReturnPrice;
        public CheckBox chkRowPassenger;
        public SmoothProgressBar smoothProgressBar;
        public TextView txtStatusRefundMoney;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtFullName = (TextView) itemLayoutView.findViewById(R.id.txtFullname);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.txtPrice);
            txtReturnPrice = (TextView) itemLayoutView.findViewById(R.id.txtReturnPrice);
            txtFarePercent = (TextView) itemLayoutView.findViewById(R.id.txtFarePercent);
            chkRowPassenger = (CheckBox) itemLayoutView.findViewById(R.id.chkRowPassenger);
            txtStatusRefundMoney = (TextView) itemLayoutView.findViewById(R.id.txtStatusRefundMoney);
            smoothProgressBar = (SmoothProgressBar) itemLayoutView.findViewById(R.id.smoothProgressBar);
            chkRowPassenger.setVisibility(View.GONE);
        }

    }

    //-----------------------------------------------
    private void setProgress(int index) {
        lists.get(index).setSelect(true);
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return result.getReFoundResponseDataFlightDomestic().getPassengerFlightDomestic().size();
    }

    //-----------------------------------------------
    public ArrayList<String> getPassengerId() {
        ArrayList<String> arrayListId = new ArrayList<>();
        for (PassengerFlightDomestic passengerFlightDomestic : lists) {
            if (passengerFlightDomestic.getTicketrefund() == 0) {
                arrayListId.add(passengerFlightDomestic.getId());
            }
        }
        return arrayListId;
    }

    //-----------------------------------------------
    public void update(ReFoundResponseFlightDomestic reFoundResponseFlightDomestic) {
        result = reFoundResponseFlightDomestic;
        lists = result.getReFoundResponseDataFlightDomestic().getPassengerFlightDomestic();
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public Boolean hasTicketRefundPassenger() {
        Boolean status = true;
        try {
            for (PassengerFlightDomestic passengerInfo : result.getReFoundResponseDataFlightDomestic().getPassengerFlightDomestic()) {
                if (passengerInfo.getTicketrefund() == 0) {
                    return false;
                }
            }
            return status;
        } catch (Exception e) {

            return false;
        }
    }
}