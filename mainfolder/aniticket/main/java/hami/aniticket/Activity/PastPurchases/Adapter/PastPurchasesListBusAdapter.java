package hami.mainapp.Activity.PastPurchases.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hami.mainapp.Activity.PastPurchases.Model.PurchasesBus;
import hami.mainapp.BaseController.SelectItemList;
import hami.mainapp.R;
import hami.mainapp.Util.UtilFonts;
import hami.mainapp.Util.UtilImageLoader;


/**
 * Created by renjer on 1/8/2017.
 */

public class PastPurchasesListBusAdapter extends RecyclerView.Adapter<PastPurchasesListBusAdapter.MyViewHolder> {

    private ArrayList<PurchasesBus> listItem;
    private Context context;
    private SelectItemList<PurchasesBus> selectItemList;
    private static final String TAG = "PastPurchasesListBusAdapter";

    //-----------------------------------------------
    public PastPurchasesListBusAdapter(Context context,
                                       ArrayList<PurchasesBus> list,
                                       SelectItemList<PurchasesBus> selectItemList) {
        this.selectItemList = selectItemList;
        listItem = list;
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public PastPurchasesListBusAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ticket_service, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final PurchasesBus outBound = listItem.get(position);
        UtilImageLoader.loadImage(context, viewHolder.imgLogoAirLine, outBound.getImage(), R.drawable.bus);
        viewHolder.txtTimeTakeOff.setText("ساعت حرکت: " + outBound.getTtime());
        viewHolder.txtDateTakeOff.setText("تاریخ رسیدن: " + outBound.getTdate_persian_show());
        viewHolder.txtAirLineAndTypeClass.setText(outBound.getCompany());
        viewHolder.txtDetails.setText("(" + outBound.getId() + ")");
        viewHolder.txtPrice.setText(getFinalPrice(outBound.getFprice()));


        viewHolder.txtFromPlace.setText(outBound.getFrom());
        viewHolder.txtToPlace.setText(outBound.getTo());
        if (listItem.get(position).getRefund() == 1) {
            viewHolder.txtStatusRefund.setVisibility(View.VISIBLE);
        } else {
            viewHolder.txtStatusRefund.setVisibility(View.GONE);
        }
    }

    //-----------------------------------------------
    public void addData(ArrayList<PurchasesBus> newData) {
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
        public TextView txtFlyTime;
        public ImageView imgLogoAirLine;
        public TextView txtFromPlace;
        public TextView txtToPlace;
        public TextView txtDetails;
        public TextView txtServiceName;
        public ImageView imgService;
        public TextView txtStatusRefund;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_BOLD);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            txtServiceName = (TextView) itemLayoutView.findViewById(R.id.txtServiceName);
            txtDateTakeOff = (TextView) itemLayoutView.findViewById(R.id.txtDateTakeOff);
            txtTimeTakeOff = (TextView) itemLayoutView.findViewById(R.id.txtTimeTakeOff);
            txtAirLineAndTypeClass = (TextView) itemLayoutView.findViewById(R.id.txtAirLineAndTypeClass);
            txtFlyTime = (TextView) itemLayoutView.findViewById(R.id.txtDetails);
            imgLogoAirLine = (ImageView) itemLayoutView.findViewById(R.id.imgLogoAirLine);
            txtFromPlace = (TextView) itemLayoutView.findViewById(R.id.txtFromPlace);
            txtToPlace = (TextView) itemLayoutView.findViewById(R.id.txtToPlace);
            txtToPlace = (TextView) itemLayoutView.findViewById(R.id.txtToPlace);
            txtDetails = (TextView) itemLayoutView.findViewById(R.id.txtDetails);
            imgService = (ImageView) itemLayoutView.findViewById(R.id.imgService);
            txtStatusRefund = (TextView) itemLayoutView.findViewById(R.id.txtStatusRefund);
            imgService.setImageResource(R.mipmap.ic_bus_side_view);
            txtServiceName.setText(R.string.bus);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItemList.onSelectItem(listItem.get(getLayoutPosition()), getLayoutPosition());
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