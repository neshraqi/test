package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.text.NumberFormat;
import java.util.Locale;

import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticParams;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticPassengerInfo;
import hami.hamibelit.R;
import hami.hamibelit.Util.UtilFonts;

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
    public PassengerInfoLisDomesticAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        if (nationality == DomesticPassengerInfo.EXPORTING_COUNTRY_IRAN) {
            viewHolder.txtCoNational.setText("کد ملی:" + domesticParams.getMelicode()[position]);
        } else if (nationality == DomesticPassengerInfo.EXPORTING_COUNTRY_FOREIGN) {
            passport = "شماره پاسپورت:" + domesticParams.getPassport_number()[position];
            viewHolder.txtCoNational.setText(passport);
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
        //public TextView txtBirthDay;
        public TextView txtPrice;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtFullName = (TextView) itemLayoutView.findViewById(R.id.txtFullname);
            txtTypePassenger = (TextView) itemLayoutView.findViewById(R.id.txtTypePassenger);
            txtCoNational = (TextView) itemLayoutView.findViewById(R.id.txtCoNational);
            //txtCoPassport = (TextView) itemLayoutView.findViewById(R.id.txtCoPassport);
            //txtBirthDay = (TextView) itemLayoutView.findViewById(R.id.txtBirthDay);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.txtPrice);
            // txtTypePrice = itemLayoutView.findViewById(R.id.txtTypePrice);
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