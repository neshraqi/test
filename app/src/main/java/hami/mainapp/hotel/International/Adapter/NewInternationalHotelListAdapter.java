package hami.mainapp.hotel.International.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hami.common.BaseController.SelectItemList;
import com.hami.common.Util.UtilFonts;
import hami.mainapp.hotel.International.Controller.Model.InternationalHotel;
import hami.mainapp.hotel.International.Dialog.NewDesignFilterInternationalHotelFragmentDialog;
import hami.mainapp.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class NewInternationalHotelListAdapter extends RecyclerView.Adapter<NewInternationalHotelListAdapter.MyViewHolder> {

    private ArrayList<InternationalHotel> listItem;
    private ArrayList<InternationalHotel> listItemTemp;
    private Context context;
    private SelectItemList<InternationalHotel> hotelSelectItemList;
    private static final String TAG = "NewInternationalHotelListAdapter";

    //-----------------------------------------------
    public NewInternationalHotelListAdapter(Context context,
                                            ArrayList<InternationalHotel> list,
                                            SelectItemList<InternationalHotel> hotelSelectItemList) {
        this.hotelSelectItemList = hotelSelectItemList;
        listItem = list;
        this.context = context;
        listItemTemp = new ArrayList<>();
        listItemTemp.addAll(listItem);
    }

    //-----------------------------------------------
    @Override
    public NewInternationalHotelListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_hotel_layout_ltr, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final InternationalHotel hotel = listItem.get(position);
        Picasso.with(context)
                .load(hotel.getHotelImage())
                .placeholder(R.drawable.no_image_hotel)
                .error(R.drawable.no_image_hotel)
                .into(viewHolder.imgService);

        viewHolder.txtHotelName.setText(hotel.getHotelName());
        viewHolder.txtHotelAddress.setText(hotel.getAddress());
        Integer rating = Integer.valueOf(hotel.getHotelStar());
        if (rating == 0) {
            viewHolder.rbHotelRate.setVisibility(View.GONE);
        } else {
            viewHolder.rbHotelRate.setVisibility(View.VISIBLE);
            viewHolder.rbHotelRate.setNumStars(rating);
            viewHolder.rbHotelRate.setRating(rating);
        }
        long price = hotel.getPrice();//Long.valueOf(hotel.getPrice().replace(",", ""));
        long disCountPrice = hotel.getDiscountprice();//Long.valueOf(hotel.getDiscountprice().replace(",", ""));
        if (price != disCountPrice) {
            viewHolder.txtPrice.setText(getFinalPrice(String.valueOf(disCountPrice), "IRR"));
            viewHolder.txtPrice2.setText(getFinalPrice(String.valueOf(price), "IRR"));
            viewHolder.txtPrice2.setPaintFlags(viewHolder.txtPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.txtPrice2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.txtPrice.setText(getFinalPrice(String.valueOf(price), "IRR"));
            viewHolder.txtPrice2.setVisibility(View.GONE);
        }
    }

    //-----------------------------------------------
    private String getFinalPrice(String price, String currencyString) {
        String finalPrice = "";
        try {

            finalPrice = price.replace(",", "");
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice)) + " " + currencyString;
            return finalPrice;
        } catch (Exception e) {


            return price + " IRR";
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
        public TextView txtHotelName;
        public RatingBar rbHotelRate;
        public TextView txtStatusRefund;
        public TextView txtHotelAddress, txtRoomHotelStatus;
        public ImageView imgService;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.TAHOMA);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            txtPrice2 = (TextView) itemLayoutView.findViewById(R.id.tvPrice2);
            txtHotelName = (TextView) itemLayoutView.findViewById(R.id.txtHotelName);
            rbHotelRate = (RatingBar) itemLayoutView.findViewById(R.id.rbHotelRate);
            txtStatusRefund = (TextView) itemLayoutView.findViewById(R.id.txtStatusRefund);
            txtHotelAddress = (TextView) itemLayoutView.findViewById(R.id.txtHotelAddress);
            imgService = (ImageView) itemLayoutView.findViewById(R.id.imgService);
            txtRoomHotelStatus = (TextView) itemLayoutView.findViewById(R.id.txtRoomHotelStatus);
            txtRoomHotelStatus.setSelected(true);
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
    public ArrayList<InternationalHotel> filterAll(ArrayMap<String, List<String>> applied_filters) {
        ArrayList<InternationalHotel> listItemFiltered = new ArrayList<>();
        try {
            List<String> filterHotelClass = applied_filters.get(NewDesignFilterInternationalHotelFragmentDialog.FILTER_CATEGORY_INTERNATIONAL_HOTEL_TYPE);
            //List<String> filterOffer = applied_filters.get(NewDesignFilterInternationalHotelFragmentDialog.FILTER_CATEGORY_INTERNATIONAL_HOTEL_OFFER);
            List<String> filterSort = applied_filters.get(NewDesignFilterInternationalHotelFragmentDialog.FILTER_CATEGORY_SORT);
            ArrayList<InternationalHotel> temp = new ArrayList<>();
            listItem.clear();
            listItem.addAll(listItemTemp);
            //****************************
            if (filterHotelClass != null) {
                for (InternationalHotel hotel : listItem) {
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
//            //****************************
//            if (filterOffer != null) {
//                for (InternationalHotel hotel : listItemFiltered) {
//                    for (int index = 0; index < filterOffer.size(); index++) {
//                        if (hotel.getOffer() != null && filterOffer.get(index).contains(hotel.getOffer())) {
//                            temp.add(hotel);
//                            break;
//                        }
//                    }
//                }
//                listItemFiltered.clear();
//                listItemFiltered.addAll(temp);
//                temp.clear();
//            }
            //****************************


            listItem.clear();
            listItem.addAll(listItemFiltered);
            notifyDataSetChanged();
            if (filterSort != null) {
                if (filterSort != null && filterSort.size() > 0) {
                    String filter = filterSort.get(0);
                    if (filter.contentEquals("0")) {
                        sortByMoney();
                    } else if (filter.contentEquals("1")) {
                        sortByRate();
                    }
                }
            }

        } catch (Exception e) {


        }
        return listItemFiltered;
    }

    //-----------------------------------------------
    public ArrayList<InternationalHotel> getFullItems() {
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

    //    //-----------------------------------------------
//    public ArrayList<DomesticFlight> filterByTime(List<String> filter) {
//        ArrayList<DomesticFlight> listItemFiltered = new ArrayList<>();
//        for (DomesticFlight domesticFlight : listItem) {
//            for (int index = 0; index < filter.size(); index++) {
//                int hour = Integer.valueOf(domesticFlight.getDaytime());
//                int type = Integer.valueOf(filter.get(index));
//                if (hasTime(type, hour)) {
//                    listItemFiltered.add(domesticFlight);
//                }
//            }
//        }
//        return listItemFiltered;
//    }
//
//    //-----------------------------------------------
//    private Boolean hasTime(int type, int hour) {
//        try {
//            if (type == 0 && (hour >= 7 && hour <= 10)) {
//                return true;
//            } else if (type == 1 && (hour >= 11 && hour <= 15)) {
//                return true;
//            } else if (type == 2 && (hour >= 16 && hour <= 19)) {
//                return true;
//            } else if (type == 3 && (hour >= 20 && hour <= 23)) {
//                return true;
//            } else if (type == 4 && ((hour >= 0 && hour <= 6))) {
//                return true;
//            }
//            return false;
//        } catch (Exception e) {
//            FirebaseCrash.logcat(Log.ERROR, TAG, e.getMessage());
//            FirebaseCrash.report(e);
//            return false;
//        }
//    }
//
//    //-----------------------------------------------
//    public void sortByCapacity() {
//        //Collections.reverse(listItem);
//        try {
//            Collections.sort(listItem, new Comparator<DomesticFlight>() {
//                @Override
//                public int compare(DomesticFlight domesticFlight1, DomesticFlight domesticFlight2) {
//                    Integer value1 = Integer.valueOf(domesticFlight1.getShowNum());
//                    Integer value2 = Integer.valueOf(domesticFlight2.getShowNum());
//                    return value1.compareTo(value2);
//                }
//            });
//            notifyDataSetChanged();
//        } catch (Exception e) {
//            FirebaseCrash.logcat(Log.ERROR, TAG, e.getMessage());
//            FirebaseCrash.report(e);
//
//        }
//
//    }
//
//    //-----------------------------------------------
//    public void sortByTime() {
//        try {
//
//            Collections.sort(listItem, new Comparator<DomesticFlight>() {
//                @Override
//                public int compare(DomesticFlight domesticFlight1, DomesticFlight domesticFlight2) {
//                    Long value1 = TimeDate.getTime(domesticFlight1.getTakeoffTime());
//                    Long value2 = TimeDate.getTime(domesticFlight2.getTakeoffTime());
//                    return value1.compareTo(value2);
//                    //return domesticFlight1.getTakeoffTime().compareToIgnoreCase(domesticFlight2.getTakeoffTime());
//                }
//            });
//            notifyDataSetChanged();
//        } catch (Exception e) {
//            FirebaseCrash.logcat(Log.ERROR, TAG, e.getMessage());
//            FirebaseCrash.report(e);
//        }
//    }
//
//    //-----------------------------------------------
    public void sortByMoney() {
        try {

            Collections.sort(listItem, new Comparator<InternationalHotel>() {
                @Override
                public int compare(InternationalHotel hotel1, InternationalHotel hotel2) {
                    Integer value1 = hotel1.getPrice();//Integer.valueOf(+hotel1.getPrice().replace(",", ""));
                    Integer value2 = hotel2.getPrice();//Integer.valueOf(hotel2.getPrice().replace(",", ""));
                    return value1.compareTo(value2);
                }
            });
            notifyDataSetChanged();
        } catch (Exception e) {


        }
    }

    //    //-----------------------------------------------
    public void sortByRate() {
        try {
            Collections.sort(listItem, new Comparator<InternationalHotel>() {
                @Override
                public int compare(InternationalHotel hotel1, InternationalHotel hotel2) {
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