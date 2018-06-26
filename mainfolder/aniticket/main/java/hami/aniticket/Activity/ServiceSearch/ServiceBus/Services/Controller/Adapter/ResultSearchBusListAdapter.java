package hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Dialog.NewDesignFilterBusFragmentDialog;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.SearchBusResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.SelectItemBus;
import hami.nasimbehesht724.BaseNetwork.BaseConfig;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.TimeDate;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilImageLoader;

/**
 * Created by renjer on 1/8/2017.
 */

public class ResultSearchBusListAdapter extends RecyclerView.Adapter<ResultSearchBusListAdapter.MyViewHolder> {

    private ArrayList<SearchBusResponse> listItem;
    private ArrayList<SearchBusResponse> listItemTemp;
    private Context context;
    private SelectItemBus selectItemBus;
    private NumberFormat nf;
    private static final String TAG = "ResultSearchBusListAdapter";

    //-----------------------------------------------
    public ResultSearchBusListAdapter(Context context, ArrayList<SearchBusResponse> list, SelectItemBus selectItemBus) {
        this.selectItemBus = selectItemBus;
        listItem = list;
        listItemTemp = new ArrayList<>();
        listItemTemp.addAll(listItem);
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public ResultSearchBusListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_new_flight_domestic, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final SearchBusResponse searchBusResponse = listItem.get(position);
        String url = BaseConfig.BASE_URL_MASTER + BaseConfig.FOLDER_IMAGE_BUS_URL + searchBusResponse.getImg();
        UtilImageLoader.loadImage(context, viewHolder.imgLogoAirLine, url, R.drawable.bus);
        viewHolder.txtTimeTakeOff.setText(searchBusResponse.getDeparureTime());
        viewHolder.txtTimeLanding.setText("---");
        viewHolder.txtFlyTime.setText(searchBusResponse.getCapacity() + "نفر");
        long adultPrice = Long.valueOf(searchBusResponse.getPricefinal().replace(",", ""));
        long disCountAdultPrice = Long.valueOf(searchBusResponse.getDiscountprice().replace(",", ""));
        if (disCountAdultPrice != adultPrice) {
            viewHolder.txtPrice.setText(getFinalPrice(String.valueOf(disCountAdultPrice)));
            viewHolder.txtPrice2.setText(getFinalPrice(String.valueOf(adultPrice)));
            viewHolder.txtPrice2.setPaintFlags(viewHolder.txtPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.txtPrice2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.txtPrice.setText(getFinalPrice(String.valueOf(adultPrice)));
            viewHolder.txtPrice2.setVisibility(View.GONE);
        }
        viewHolder.txtPrice.setText(getFinalPrice(searchBusResponse.getPrice()));
        viewHolder.txtAirLineAndTypeClass.setText(searchBusResponse.getVip());
        viewHolder.txtTimeFlight.setText(searchBusResponse.getCompany());
        viewHolder.txtAirLine.setText("");
        viewHolder.imgService.setImageResource(R.mipmap.ic_bus_side_view);
        //viewHolder.txtFromPlace.setText(searchBusResponse.getSource());
        //viewHolder.txtToPlace.setText(searchBusResponse.getDestination());
        //viewHolder.txtToPlace.setText(searchBusResponse.getDestination());
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
    public ArrayList<SearchBusResponse> getFullItems() {
        return listItemTemp;
    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtPrice, txtPrice2;
        public TextView txtTimeTakeOff, txtTimeLanding;
        public TextView txtAirLineAndTypeClass, txtAirLine;
        public TextView txtFlyTime, txtTimeFlight;
        public ImageView imgLogoAirLine;
        public TextView txtFromPlace;
        public TextView txtToPlace;
        public ImageView imgService;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            txtPrice2 = (TextView) itemLayoutView.findViewById(R.id.tvPrice2);
            txtTimeLanding = (TextView) itemLayoutView.findViewById(R.id.txtTimeLanding);
            txtFromPlace = (TextView) itemLayoutView.findViewById(R.id.txtFromPlace);
            txtToPlace = (TextView) itemLayoutView.findViewById(R.id.txtToPlace);
            imgService = (ImageView) itemLayoutView.findViewById(R.id.imgService);
            //itemLayoutView.findViewById(R.id.txtSpaceComa).setVisibility(View.INVISIBLE);
            txtTimeTakeOff = (TextView) itemLayoutView.findViewById(R.id.txtTimeTakeOff);
            txtAirLineAndTypeClass = (TextView) itemLayoutView.findViewById(R.id.txtAirLineAndTypeClass);
            txtAirLine = (TextView) itemLayoutView.findViewById(R.id.txtAirLine);
            txtFlyTime = (TextView) itemLayoutView.findViewById(R.id.txtDetails);
            txtTimeFlight = (TextView) itemLayoutView.findViewById(R.id.txtTimeFlight);
            imgLogoAirLine = (ImageView) itemLayoutView.findViewById(R.id.imgLogoAirLine);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItemBus.onSelectItemBus(listItem.get(getLayoutPosition()));
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
    public ArrayList<SearchBusResponse> filterAll(ArrayMap<String, List<String>> applied_filters) {
        ArrayList<SearchBusResponse> listItemFiltered = new ArrayList<>();
        try {
            List<String> filterTime = applied_filters.get(NewDesignFilterBusFragmentDialog.FILTER_CATEGORY_TIME_TAKE_OFF);
            List<String> filterPrice = applied_filters.get(NewDesignFilterBusFragmentDialog.FILTER_CATEGORY_PRICE);
            List<String> filterBus_Type = applied_filters.get(NewDesignFilterBusFragmentDialog.FILTER_CATEGORY_Bus_TYPE);
            List<String> filterSort = applied_filters.get(NewDesignFilterBusFragmentDialog.FILTER_CATEGORY_SORT);
            ArrayList<SearchBusResponse> temp = new ArrayList<>();
            listItem.clear();
            listItem.addAll(listItemTemp);
            if (filterTime != null) {
                for (SearchBusResponse searchBusResponse : listItem) {
                    for (int index = 0; index < filterTime.size(); index++) {
                        int hour = Integer.valueOf(searchBusResponse.getDaytime());
                        int type = Integer.valueOf(filterTime.get(index));
                        if (hasTime(type, hour)) {
                            listItemFiltered.add(searchBusResponse);
                        }
                    }
                }
            } else {
                listItemFiltered.addAll(listItem);
            }

            if (filterBus_Type != null) {
                for (SearchBusResponse searchBusResponse : listItemFiltered) {
                    for (int index = 0; index < filterBus_Type.size(); index++) {
                        if (filterBus_Type.get(index).contains("0") && searchBusResponse.getVip().contentEquals(context.getString(R.string.bus_normal))) {
                            temp.add(searchBusResponse);
                            break;
                        } else if (filterBus_Type.get(index).contains("1") && searchBusResponse.getVip().contentEquals(context.getString(R.string.bus_vip))) {
                            temp.add(searchBusResponse);
                        }
                    }
                }
                listItemFiltered.clear();
                listItemFiltered.addAll(temp);
                temp.clear();
            }

            if (filterPrice != null) {
                if (filterPrice != null && filterPrice.size() > 0) {
                    Long filterPriceLong = Long.valueOf(filterPrice.get(0)) * NewDesignFilterBusFragmentDialog.STEP_SIZE * 1000;
                    for (SearchBusResponse searchBusResponse : listItemFiltered) {
                        Long currentPrice = Long.valueOf(searchBusResponse.getPrice().replace(",", "")) / 10;
                        if (currentPrice <= filterPriceLong) {
                            temp.add(searchBusResponse);
                        }
                    }
                }
                listItemFiltered.clear();
                listItemFiltered.addAll(temp);
                temp.clear();
            }
            listItem.clear();
            listItem.addAll(listItemFiltered);
            notifyDataSetChanged();
            if (filterSort != null) {
                if (filterSort != null && filterSort.size() > 0) {
                    String filter = filterSort.get(0);
                    if (filter.contentEquals("0")) {
                        sortByMoney();
                    } else if (filter.contentEquals("1")) {
                        sortByTime();
                    } else if (filter.contentEquals("2")) {
                        sortByBusType();
                    } else if (filter.contentEquals("3")) {
                        sortByCapacity();
                    }
                }
            }


        } catch (Exception e) {

        }
        return listItemFiltered;
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
    public void clearList() {
        listItem = new ArrayList<>();
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    private Boolean hasTime(int type, int hour) {
        try {

            if (type == 0 && (hour >= 7 && hour <= 10)) {
                return true;
            } else if (type == 1 && (hour >= 11 && hour <= 15)) {
                return true;
            } else if (type == 2 && (hour >= 16 && hour <= 19)) {
                return true;
            } else if (type == 3 && (hour >= 20 && hour <= 23)) {
                return true;
            } else if (type == 4 && ((hour >= 0 && hour <= 6))) {
                return true;
            }
            return false;
        } catch (Exception e) {

            return false;
        }
    }

    //-----------------------------------------------
    public void sortByCapacity() {
        //Collections.reverse(listItem);
        Collections.sort(listItem, new Comparator<SearchBusResponse>() {
            @Override
            public int compare(SearchBusResponse searchBusResponse1, SearchBusResponse searchBusResponse2) {
                Integer value1 = Integer.valueOf(searchBusResponse1.getCapacity());
                Integer value2 = Integer.valueOf(searchBusResponse2.getCapacity());
                return value1.compareTo(value2);
            }
        });
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public void sortByTime() {

        Collections.sort(listItem, new Comparator<SearchBusResponse>() {
            @Override
            public int compare(SearchBusResponse searchBusResponse1, SearchBusResponse searchBusResponse2) {
                Long value1 = TimeDate.getTime(searchBusResponse1.getDeparureTime());
                Long value2 = TimeDate.getTime(searchBusResponse2.getDeparureTime());
                return value1.compareTo(value2);
                //return domesticFlight1.getTakeoffTime().compareToIgnoreCase(domesticFlight2.getTakeoffTime());
            }
        });
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public void sortByMoney() {
        Collections.sort(listItem, new Comparator<SearchBusResponse>() {
            @Override
            public int compare(SearchBusResponse searchBusResponse1, SearchBusResponse searchBusResponse2) {
                Integer value1 = Integer.valueOf(searchBusResponse1.getPrice().replace(",", ""));
                Integer value2 = Integer.valueOf(searchBusResponse2.getPrice().replace(",", ""));
                return value1.compareTo(value2);
            }
        });
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public void sortByBusType() {
        Collections.sort(listItem, new Comparator<SearchBusResponse>() {
            @Override
            public int compare(SearchBusResponse searchBusResponse1, SearchBusResponse searchBusResponse2) {
                Integer value1 = searchBusResponse1.getVip().contentEquals("معمولی") ? 0 : 1;
                Integer value2 = searchBusResponse2.getVip().contentEquals("معمولی") ? 0 : 1;
                return value1.compareTo(value2);
            }
        });
        notifyDataSetChanged();
    }
}