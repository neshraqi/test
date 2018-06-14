package hami.aniticket.Activity.ServiceTour.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hami.aniticket.Activity.ServiceTour.Controller.Model.BookingTourDetailsPassenger;
import hami.aniticket.Const.FlightRules;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;


public class FinalPassengerTourListAdapter extends RecyclerView.Adapter<FinalPassengerTourListAdapter.MyViewHolder> {

    private ArrayList<BookingTourDetailsPassenger> listItem;
    private Context context;
    private static final String TAG = "FinalPassengerTourListAdapter";
    private Boolean hasInternationalTour;

    //-----------------------------------------------
    public FinalPassengerTourListAdapter(Context context, ArrayList<BookingTourDetailsPassenger> items, Boolean hasInternationalTour) {
        this.listItem = items;
        this.context = context;
        this.hasInternationalTour = hasInternationalTour;
    }


    //-----------------------------------------------
    @Override
    public FinalPassengerTourListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_more_info_passenger, null);
        FinalPassengerTourListAdapter.MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final FinalPassengerTourListAdapter.MyViewHolder viewHolder, final int position) {
        final BookingTourDetailsPassenger passengerTour = listItem.get(position);
        if (Integer.valueOf(passengerTour.getAge_level()) == FlightRules.TP_ADULTS) {
            viewHolder.txtTypePassenger.setText(context.getString(R.string.passenger) + " " + context.getString(R.string.adults));
        } else if (Integer.valueOf(passengerTour.getAge_level()) == FlightRules.TP_CHILDREN) {
            viewHolder.txtTypePassenger.setText(context.getString(R.string.passenger) + " " + context.getString(R.string.children));
        } else if (Integer.valueOf(passengerTour.getAge_level()) == FlightRules.TP_INFANT) {
            viewHolder.txtTypePassenger.setText(context.getString(R.string.passenger) + " " + context.getString(R.string.infant));
        }
        viewHolder.txtFullNameEng.setText(passengerTour.getName() + " " + passengerTour.getLname());
        viewHolder.txtPrice.setText(getFinalPrice(passengerTour.getFinal_price()));
        viewHolder.txtNationalCode.setText("کد ملی:" + passengerTour.getMcode() + "(" + passengerTour.getB_day() + ")");
        viewHolder.txtMoreInfo2.setText(context.getString(R.string.expireDatePassport) + " " + passengerTour.getPassEndDate());
        viewHolder.txtMoreInfo.setText("پاسپورت:" + passengerTour.getPassID());
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
    public ArrayList<BookingTourDetailsPassenger> getListItem() {
        return listItem;
    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtPrice;
        public TextView txtFullNameEng;
        public ImageView imgTypePassenger;
        public TextView txtTypePassenger;
        public TextView txtNationalCode;
        //        public TextView txtBirthDay;
        public TextView txtMoreInfo;
        public TextView txtMoreInfo2;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.txtPrice);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtTypePassenger = (TextView) itemLayoutView.findViewById(R.id.txtTypePassenger);
            txtFullNameEng = (TextView) itemLayoutView.findViewById(R.id.txtFullNameEng);
            txtNationalCode = (TextView) itemLayoutView.findViewById(R.id.txtNationalCode);
            //txtBirthDay = (TextView) itemLayoutView.findViewById(R.id.txtBirthDay);
            txtMoreInfo = (TextView) itemLayoutView.findViewById(R.id.txtMoreInfo);
            txtMoreInfo2 = (TextView) itemLayoutView.findViewById(R.id.txtMoreInfo2);
            if (hasInternationalTour) {
                txtMoreInfo.setVisibility(View.VISIBLE);
                txtMoreInfo2.setVisibility(View.VISIBLE);

            } else {
                txtMoreInfo.setVisibility(View.GONE);
                txtMoreInfo2.setVisibility(View.GONE);
            }
            imgTypePassenger = (ImageView) itemLayoutView.findViewById(R.id.imgTypePassenger);
        }
    }


    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
