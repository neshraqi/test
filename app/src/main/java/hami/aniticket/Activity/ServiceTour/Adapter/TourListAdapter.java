package hami.aniticket.Activity.ServiceTour.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hami.aniticket.Activity.ServiceTour.Controller.Model.TourItem;
import hami.aniticket.BaseController.SelectItemList;
import hami.aniticket.BaseNetwork.BaseConfig;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;
import hami.aniticket.Util.UtilImageLoader;


public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.MyViewHolder> {

    private ArrayList<TourItem> listItem;
    private ArrayList<TourItem> listItemTemp;
    private Context context;
    private SelectItemList<TourItem> selectItemList;

    //-----------------------------------------------
    public TourListAdapter(Context context,
                           ArrayList<TourItem> list,
                           SelectItemList<TourItem> selectItemList) {
        this.selectItemList = selectItemList;
        listItem = list;
        this.context = context;
        listItemTemp = new ArrayList<>();
        listItemTemp.addAll(listItem);
    }

    //-----------------------------------------------
    @Override
    public TourListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_tour_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final TourItem outBound = listItem.get(position);
        String url = BaseConfig.FOLDER_IMAGE_TOUR_URL + outBound.getImg() + ".jpg";
        UtilImageLoader.loadImage(context, viewHolder.imgService, url, R.drawable.tour);
        viewHolder.txtTourName.setText(outBound.getName());
        String dateCounter = outBound.getDay_count() + context.getString(R.string.day) + " و " + outBound.getNight_count() + context.getString(R.string.night);
        viewHolder.txtTourFromDate.setText("تاریخ حرکت: " + outBound.getStart_date() + "(" + dateCounter + ")");
        viewHolder.txtHotelType.setText(outBound.getStay_in());
        viewHolder.txtTourFromPlace.setText("مبدا از " + outBound.getTfrom());
        long adultPrice = Long.valueOf(outBound.getPrice_adl().replace(",", ""));
        long disCountAdultPrice = outBound.getDiscount_price_adl() == null || outBound.getDiscount_price_adl().length() == 0 ? Long.valueOf(outBound.getPrice_adl().replace(",", "")) : Long.valueOf(outBound.getDiscount_price_adl().replace(",", ""));
        if (disCountAdultPrice != adultPrice) {
            viewHolder.txtPrice.setText(getFinalPrice(String.valueOf(disCountAdultPrice)));
            viewHolder.txtPrice2.setText(getFinalPrice(String.valueOf(adultPrice)));
            viewHolder.txtPrice2.setPaintFlags(viewHolder.txtPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.txtPrice2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.txtPrice.setText(getFinalPrice(String.valueOf(adultPrice)));
            viewHolder.txtPrice2.setVisibility(View.GONE);
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

        public TextView txtPrice, txtPrice2;
        public TextView txtTourName, txtTourFromDate, txtHotelType, txtTourFromPlace;
        public ImageView imgService;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_BOLD);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            txtPrice2 = (TextView) itemLayoutView.findViewById(R.id.tvPrice2);
            txtTourName = (TextView) itemLayoutView.findViewById(R.id.txtTourName);
            txtHotelType = (TextView) itemLayoutView.findViewById(R.id.txtHotelType);
            txtTourFromDate = (TextView) itemLayoutView.findViewById(R.id.txtTourFromDate);
            txtTourFromPlace = (TextView) itemLayoutView.findViewById(R.id.txtTourFromPlace);
            imgService = (ImageView) itemLayoutView.findViewById(R.id.imgService);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItemList.onSelectItem(listItem.get(getAdapterPosition()), getAdapterPosition());
                }
            });

        }
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    //-----------------------------------------------

    public ArrayList<TourItem> getFullItems() {
        return listItemTemp;
    }

    //-----------------------------------------------
    public void resetFilter() {
        try {
            listItem.clear();
            listItem.addAll(listItemTemp);
            notifyDataSetChanged();
        } catch (Exception e) {

        }

    }


}