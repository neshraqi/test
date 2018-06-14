package hami.nasimbehesht724.Activity.PastPurchases.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.OutBound;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.RegisterPassengerResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.TicketInternational;
import hami.nasimbehesht724.BaseController.SelectItemList;
import hami.nasimbehesht724.BaseNetwork.BaseConfig;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilFonts;


/**
 * Created by renjer on 1/8/2017.
 */

public class PastPurchasesInternationalFlightListAdapter extends RecyclerView.Adapter<PastPurchasesInternationalFlightListAdapter.MyViewHolder> {

    private ArrayList<TicketInternational> listItem;
    private Context context;
    private SelectItemList<TicketInternational> registerFlightResponseSelectItemList;
    private static final String TAG = "PastPurchasesInternationalFlightListAdapter";
    //-----------------------------------------------
    public PastPurchasesInternationalFlightListAdapter(Context context,
                                                       ArrayList<TicketInternational> list,
                                                       SelectItemList<TicketInternational> registerFlightResponseSelectItemList) {
        this.registerFlightResponseSelectItemList = registerFlightResponseSelectItemList;
        listItem = list;
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public PastPurchasesInternationalFlightListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_new_flight_international_went, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final TicketInternational ticketInternational = listItem.get(position);
        OutBound outBound = ticketInternational.getOutBound();
        OutBound return_ = ticketInternational.getReturn_();
        RegisterPassengerResponse registerPassengerResponse = ticketInternational.getRegisterPassengerResponse();

        //viewHolder.tvPrice.setText(getFinalPrice(outBound.getAdultPrice()));
        Picasso.with(context)
                .load(BaseConfig.BASE_URL_MASTER + BaseConfig.FOLDER_IMAGE_INTERNATIONAL_URL + outBound.getFileName())
                .into(viewHolder.imgLogoAirLine);
        viewHolder.txtTimeTakeOffWent.setText("ساعت رفت:" + outBound.getTakeoffTime());
        viewHolder.txtAirLineAndTypeClass.setText(outBound.getAireLineName() + "(" + outBound.getType() + ")");

        viewHolder.imgService.setImageResource(R.mipmap.ic_airplan_top);
        viewHolder.txtFromPlace.setText(outBound.getLegs().get(0).getDepartureCityName());
        viewHolder.txtToPlace.setText(outBound.getLegs().get(outBound.getLegs().size() - 1).getArrivalCityName());
        if (return_ != null) {
            viewHolder.txtTimeTakeOffReturn.setText("ساعت برگشت:" + return_.getTakeoffTime());
            viewHolder.titleTypeFlight.setText(R.string.twoWay);
        } else {
            viewHolder.titleTypeFlight.setText(R.string.oneWay);
            viewHolder.txtTimeTakeOffReturn.setVisibility(View.GONE);
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
    public void clearList() {
        listItem = new ArrayList<>();
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtPrice;

        public TextView txtTimeTakeOffWent, txtTimeTakeOffReturn;
        public TextView txtAirLineAndTypeClass;
        public TextView txtFlyTime;
        public ImageView imgLogoAirLine;
        public TextView txtFromPlace;
        public TextView txtToPlace;
        public ImageView imgService;
        public TextView titleTypeFlight;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_BOLD);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            //txtTimeTakeOffWent = (TextView) itemLayoutView.findViewById(R.id.txtTimeTakeOffWent);
            txtTimeTakeOffReturn = (TextView) itemLayoutView.findViewById(R.id.txtTimeTakeOffReturn);
            txtAirLineAndTypeClass = (TextView) itemLayoutView.findViewById(R.id.txtAirLineAndTypeClass);
            imgLogoAirLine = (ImageView) itemLayoutView.findViewById(R.id.imgLogoAirLine);
            txtFromPlace = (TextView) itemLayoutView.findViewById(R.id.txtFromPlace);
            //titleTypeFlight = (TextView) itemLayoutView.findViewById(R.id.titleTypeFlight);
            txtToPlace = (TextView) itemLayoutView.findViewById(R.id.txtToPlace);
            imgService = (ImageView) itemLayoutView.findViewById(R.id.imgService);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    registerFlightResponseSelectItemList.onSelectItem(listItem.get(getLayoutPosition()),getLayoutPosition());
                }
            });

        }
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }
}