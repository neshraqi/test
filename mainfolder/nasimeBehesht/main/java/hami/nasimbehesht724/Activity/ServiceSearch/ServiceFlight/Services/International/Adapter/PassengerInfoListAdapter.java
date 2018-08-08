package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.PassengerInfo;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.ResultRegisterPassengerFlightInternationalResponse;
import hami.mainapp.R;
import hami.mainapp.Util.UtilFonts;

/**
 * Created by renjer on 1/8/2017.
 */

public class PassengerInfoListAdapter extends RecyclerView.Adapter<PassengerInfoListAdapter.MyViewHolder> {

    //private List<PassengerInfo> list_item;
    private ResultRegisterPassengerFlightInternationalResponse passengers;
    private Context context;
    private String priceAdults, priceChild, priceInfant;

    //-----------------------------------------------
    public PassengerInfoListAdapter(Context context, List<PassengerInfo> item, String priceAdults, String priceChild, String priceInfant) {
        //this.list_item = item;
        this.context = context;
        this.priceAdults = priceAdults;
        this.priceChild = priceChild;
        this.priceInfant = priceInfant;
    }

    //-----------------------------------------------
    public PassengerInfoListAdapter(Context context, ResultRegisterPassengerFlightInternationalResponse item, String priceAdults, String priceChild, String priceInfant) {
        this.passengers = item;
        this.context = context;
        this.priceAdults = priceAdults;
        this.priceChild = priceChild;
        this.priceInfant = priceInfant;
    }

    //-----------------------------------------------
    @Override
    public PassengerInfoListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_flight_international_passnger_pre_factor, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        //PassengerInfo passengerInfo = list_item.get(position);
        viewHolder.txtFullName.setText(passengers.getName()[position] + " " + passengers.getFamily()[position]);
        viewHolder.txtTypePassenger.setText(context.getString(passengers.getTypePassengerResource(position)));
        viewHolder.txtCoNational.setText("شماره پاسپورت:" + passengers.getPassport_number()[position]);
        viewHolder.txtBirthDay.setText(passengers.getBirthday()[position]);
        viewHolder.txtExpireDate.setText(passengers.getExpdate()[position]);
    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtFullName;
        public TextView txtTypePassenger;
        public TextView txtCoNational;
        public TextView txtPrice;
        public TextView txtExpireDate;
        public TextView txtTypePrice;
        public TextView txtDateExpirePassport;
        public TextView txtBirthDay;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtFullName = (TextView) itemLayoutView.findViewById(R.id.txtFullname);
            txtTypePassenger = (TextView) itemLayoutView.findViewById(R.id.txtTypePassenger);
            txtCoNational = (TextView) itemLayoutView.findViewById(R.id.txtCoNational);
            txtBirthDay = (TextView) itemLayoutView.findViewById(R.id.txtBirthDay);
            txtExpireDate = (TextView) itemLayoutView.findViewById(R.id.txtDateExpirePassport);

        }
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        if (passengers != null) {
            return passengers.getFamily().length > 0 ? passengers.getFamily().length : 0;
        }
        return 0;
    }

}