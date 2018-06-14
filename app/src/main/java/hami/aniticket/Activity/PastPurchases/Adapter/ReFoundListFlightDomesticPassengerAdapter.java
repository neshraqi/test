package hami.aniticket.Activity.PastPurchases.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;



import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import hami.aniticket.Activity.PastPurchases.Model.PassengerFlightDomestic;
import hami.aniticket.Activity.PastPurchases.Model.ReFoundPassenger;
import hami.aniticket.Activity.PastPurchases.Model.ReFoundResponseFlightDomestic;
import hami.aniticket.BaseController.SelectItemList;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;


/**
 * Created by renjer on 1/8/2017.
 */

public class ReFoundListFlightDomesticPassengerAdapter extends RecyclerView.Adapter<ReFoundListFlightDomesticPassengerAdapter.MyViewHolder> {

    //private ArrayList<PassengerFlightDomestic> listItem;
    private Context context;
    private SelectItemList<Integer> selectItemList;
    private ReFoundResponseFlightDomestic result;
    private static final String TAG = "ReFoundListFlightDomesticPassengerAdapter";
    //-----------------------------------------------
    public ReFoundListFlightDomesticPassengerAdapter(Context context,
                                                     ReFoundResponseFlightDomestic result,
                                                     SelectItemList<Integer> selectItemList) {
        this.selectItemList = selectItemList;
        this.result = result;
        //listItem = result.getReFoundResponseDataFlightDomestic().getPassengerFlightDomestic();
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public ReFoundListFlightDomesticPassengerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_flight_domestic_ticket_info_passenger_refound, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        PassengerFlightDomestic passengerInfo = result.getReFoundResponseDataFlightDomestic().getPassengerFlightDomestic().get(position);
        Long finalPrice = Long.valueOf(result.getReFoundResponseDataFlightDomestic().getPassengerFlightDomestic().get(position).getFinalprice().replace(",", ""));
        Long priceJarime = Long.valueOf(result.getReFoundResponseDataFlightDomestic().getReFoundPassengerResponse().getReFoundPassengers().get(position).getPricejarime());
        String priceJarimeFinal = String.valueOf((finalPrice - priceJarime));
        viewHolder.txtFullName.setText(passengerInfo.getName() + " " + passengerInfo.getFamily());
        viewHolder.txtPrice.setText("مبلغ پرداختی:" + getFinalPrice(String.valueOf(finalPrice)));
        viewHolder.txtReturnPrice.setText("مبلغ بازگشتی:" + getFinalPrice(priceJarimeFinal));
        viewHolder.txtFarePercent.setText(getFinePercent(passengerInfo.getId()) + "%");
        viewHolder.chkRowPassenger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                result.getReFoundResponseDataFlightDomestic().getPassengerFlightDomestic().get(position).setSelect(isChecked);
                int counterInt = getCountItemChecked();
                selectItemList.onSelectItem(counterInt,counterInt);
            }
        });

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
        public TextView txtStatusRefundMoney;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtFullName = (TextView) itemLayoutView.findViewById(R.id.txtFullname);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.txtPrice);
            txtReturnPrice = (TextView) itemLayoutView.findViewById(R.id.txtReturnPrice);
            txtFarePercent = (TextView) itemLayoutView.findViewById(R.id.txtFarePercent);
            chkRowPassenger = (CheckBox) itemLayoutView.findViewById(R.id.chkRowPassenger);
            if (getItemCount() == 1) {
                chkRowPassenger.setVisibility(View.GONE);
            }
            // txtTypePrice = itemLayoutView.findViewById(R.id.txtTypePrice);
        }

    }

    //-----------------------------------------------
    public String getCheckedPassenger() {
        ArrayList<String> resultId = new ArrayList<>();
        String resultString = "";
        try {
            if (result.getReFoundResponseDataFlightDomestic().getPassengerFlightDomestic().size() == 1) {
                PassengerFlightDomestic passengerInfo = result.getReFoundResponseDataFlightDomestic().getPassengerFlightDomestic().get(0);
                resultString = passengerInfo.getId();
            } else {
                for (PassengerFlightDomestic passengerInfo : result.getReFoundResponseDataFlightDomestic().getPassengerFlightDomestic()) {
                    if (passengerInfo.isSelected()) {
                        resultId.add(passengerInfo.getId());
                    }
                }
                resultString = Arrays.toString(resultId.toArray());
            }

            return resultString;
        } catch (Exception e) {

            return "";
        }
    }

    //-----------------------------------------------
    public int getCountItemChecked() {
        int counter = 0;
        try {
            for (PassengerFlightDomestic passengerInfo : result.getReFoundResponseDataFlightDomestic().getPassengerFlightDomestic()) {
                if (passengerInfo.isSelected()) {
                    counter++;
                }
            }
            return counter;
        } catch (Exception e) {

            return 0;
        }
    }

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

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return result.getReFoundResponseDataFlightDomestic().getPassengerFlightDomestic().size();
    }
}