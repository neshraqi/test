package hami.aniticket.Activity.PastPurchases.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hami.aniticket.Activity.PastPurchases.Model.PurchasesFlightInternational;
import hami.aniticket.BaseController.SelectItemList;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;
import hami.aniticket.Util.UtilImageLoader;


/**
 * Created by renjer on 1/8/2017.
 */

public class PastPurchasesListFlightInternationalAdapter extends
        RecyclerView.Adapter<PastPurchasesListFlightInternationalAdapter.MyViewHolder> {

    private ArrayList<PurchasesFlightInternational> listItem;
    private Context context;
    private SelectItemList<PurchasesFlightInternational> selectItemList;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private static final String TAG = "PastPurchasesListFlightInternationalAdapter";
    //-----------------------------------------------
    public PastPurchasesListFlightInternationalAdapter(Context context,
                                                       ArrayList<PurchasesFlightInternational> list,
                                                       SelectItemList<PurchasesFlightInternational> selectItemList) {
        this.selectItemList = selectItemList;
        listItem = list;
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ticket_service, null);
        return new MyViewHolder(view);
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final PurchasesFlightInternational outBound = listItem.get(position);
        UtilImageLoader.loadImage(context,viewHolder.imgLogoAirLine,outBound.getImg_airline(),R.drawable.flight);
        viewHolder.txtTimeTakeOff.setText("ساعت پرواز: " + outBound.getTtime());
        viewHolder.txtDateTakeOff.setText("تاریخ پرواز: " + outBound.getTdate_persian_show());
        viewHolder.txtAirLineAndTypeClass.setText(outBound.getAirline());
        viewHolder.txtDetails.setText("(" + context.getString(R.string.flightNumber) + " " + outBound.getFnumber() + ")");
        viewHolder.txtPrice.setText(getFinalPrice(outBound.getFprice()));
        viewHolder.txtFromPlace.setText(outBound.getFrom_persian());
        viewHolder.txtToPlace.setText(outBound.getTo_persian());
        if (listItem.get(position).getRefund() == 1) {
            viewHolder.txtStatusRefund.setVisibility(View.VISIBLE);
        } else {
            viewHolder.txtStatusRefund.setVisibility(View.GONE);
        }


    }

    //-----------------------------------------------
    public void addData(ArrayList<PurchasesFlightInternational> newData) {
        if (newData != null & newData.size() > 0) {
            listItem.addAll(newData);
            notifyDataSetChanged();
        }
    }

    //-----------------------------------------------
    public void clear() {
        listItem.clear();
        notifyDataSetChanged();
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
        public TextView txtTimeTakeOff, txtDateTakeOff;
        public TextView txtAirLineAndTypeClass;
        public ImageView imgLogoAirLine;
        public TextView txtFromPlace;
        public TextView txtToPlace;
        public ImageView imgService;
        public TextView txtDetails;
        public TextView txtStatusRefund;
        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_BOLD);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            txtDateTakeOff = (TextView) itemLayoutView.findViewById(R.id.txtDateTakeOff);
            txtTimeTakeOff = (TextView) itemLayoutView.findViewById(R.id.txtTimeTakeOff);
            txtAirLineAndTypeClass = (TextView) itemLayoutView.findViewById(R.id.txtAirLineAndTypeClass);
            //txtFlyTime = (TextView) itemLayoutView.findViewById(R.id.txtDetails);
            imgLogoAirLine = (ImageView) itemLayoutView.findViewById(R.id.imgLogoAirLine);
            txtFromPlace = (TextView) itemLayoutView.findViewById(R.id.txtFromPlace);
            txtToPlace = (TextView) itemLayoutView.findViewById(R.id.txtToPlace);
            txtDetails = (TextView) itemLayoutView.findViewById(R.id.txtDetails);
            imgService = (ImageView) itemLayoutView.findViewById(R.id.imgService);
            txtStatusRefund = (TextView) itemLayoutView.findViewById(R.id.txtStatusRefund);
            imgService.setImageResource(R.mipmap.ic_airplan_top);
            imgService.setRotation(-45);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItemList.onSelectItem(listItem.get(getLayoutPosition()),getLayoutPosition());
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