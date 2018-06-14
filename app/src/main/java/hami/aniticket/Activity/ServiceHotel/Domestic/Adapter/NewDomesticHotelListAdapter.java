package hami.aniticket.Activity.ServiceHotel.Domestic.Adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hami.aniticket.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotel;
import hami.aniticket.Activity.ServiceHotel.Domestic.dialog.NewDesignFilterDomesticHotelFragmentDialog;
import hami.aniticket.BaseController.SelectItemList;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;
import hami.aniticket.Util.UtilImageLoader;


public class NewDomesticHotelListAdapter extends RecyclerView.Adapter<NewDomesticHotelListAdapter.MyViewHolder> {

    private ArrayList<DomesticHotel> listItem;
    private ArrayList<DomesticHotel> listItemTemp;
    private Context context;
    private SelectItemList<DomesticHotel> hotelSelectItemList;
    private static final String TAG = "NewDomesticHotelListAdapter";

    //-----------------------------------------------
    public NewDomesticHotelListAdapter(Context context,
                                       ArrayList<DomesticHotel> list,
                                       SelectItemList<DomesticHotel> hotelSelectItemList) {
        this.hotelSelectItemList = hotelSelectItemList;
        listItem = list;
        this.context = context;
        listItemTemp = new ArrayList<>();
        listItemTemp.addAll(listItem);
    }

    //-----------------------------------------------
    @Override
    public NewDomesticHotelListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_hotel_layout_rtl, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final DomesticHotel hotel = listItem.get(position);
        String urlHotelImage = hotel.getHotelImage();
        UtilImageLoader.loadImage(context, viewHolder.imgService, urlHotelImage, R.drawable.no_image_hotel);
        viewHolder.txtHotelName.setText(hotel.getHotelNameFa());
        viewHolder.txtHotelAddress.setText(hotel.getHotelAddress());
        viewHolder.txtHotelCategory.setText(hotel.getHotelCategory());
        Integer rating = Integer.valueOf(hotel.getHotelStar());
        if (rating == 0) {
            viewHolder.rbHotelRate.setVisibility(View.GONE);
        } else {
            viewHolder.rbHotelRate.setVisibility(View.VISIBLE);
            viewHolder.rbHotelRate.setNumStars(rating);
            viewHolder.rbHotelRate.setRating(rating);
        }
    }

    //-----------------------------------------------
    public void clearList() {
        listItem = new ArrayList<>();
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView txtHotelName, txtHotelCategory;
        public RatingBar rbHotelRate;
        public TextView txtHotelAddress;
        public ImageView imgService;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtHotelName = (TextView) itemLayoutView.findViewById(R.id.txtHotelName);
            txtHotelCategory = (TextView) itemLayoutView.findViewById(R.id.txtHotelCategory);
            rbHotelRate = (RatingBar) itemLayoutView.findViewById(R.id.rbHotelRate);
            txtHotelAddress = (TextView) itemLayoutView.findViewById(R.id.txtHotelAddress);
            txtHotelAddress.setSelected(true);
            imgService = (ImageView) itemLayoutView.findViewById(R.id.imgService);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hotelSelectItemList.onSelectItem(listItem.get(getLayoutPosition()), getLayoutPosition());
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
    public ArrayList<DomesticHotel> filterAll(ArrayMap<String, List<String>> applied_filters) {
        ArrayList<DomesticHotel> listItemFiltered = new ArrayList<>();
        try {
            List<String> filterHotelClass = applied_filters.get(NewDesignFilterDomesticHotelFragmentDialog.FILTER_CATEGORY_INTERNATIONAL_HOTEL_TYPE);
            List<String> filterSort = applied_filters.get(NewDesignFilterDomesticHotelFragmentDialog.FILTER_CATEGORY_SORT);
            ArrayList<DomesticHotel> temp = new ArrayList<>();
            listItem.clear();
            listItem.addAll(listItemTemp);
            //****************************
            if (filterHotelClass != null) {
                for (DomesticHotel hotel : listItem) {
                    for (int index = 0; index < filterHotelClass.size(); index++) {
                        Integer rateValue = Integer.valueOf(hotel.getHotelStar());
                        Integer rateValueFilter = Integer.valueOf(filterHotelClass.get(index));
                        if (rateValue == rateValueFilter) {
                            listItemFiltered.add(hotel);
                        }
                    }
                }
            } else {
                listItemFiltered.addAll(listItem);
            }
            //****************************
            listItem.clear();
            listItem.addAll(listItemFiltered);
            notifyDataSetChanged();
            if (filterSort != null) {
                if (filterSort != null && filterSort.size() > 0) {
                    String filter = filterSort.get(0);
                    if (filter.contentEquals("0")) {
                        sortByRate();
                    }
                }
            }

        } catch (Exception e) {

        }
        return listItemFiltered;
    }

    //-----------------------------------------------
    public ArrayList<DomesticHotel> getFullItems() {
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

    //-----------------------------------------------
    public void sortByRate() {
        try {
            Collections.sort(listItem, new Comparator<DomesticHotel>() {
                @Override
                public int compare(DomesticHotel hotel1, DomesticHotel hotel2) {
                    Integer value1 = Integer.valueOf(hotel1.getHotelStar());
                    Integer value2 = Integer.valueOf(hotel2.getHotelStar());
                    return value1.compareTo(value2);
                }
            });
            notifyDataSetChanged();
        } catch (Exception e) {

        }

    }
}