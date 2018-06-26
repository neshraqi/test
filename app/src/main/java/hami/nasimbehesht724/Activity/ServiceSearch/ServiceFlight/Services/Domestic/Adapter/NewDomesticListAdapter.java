package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Adapter;

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

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticFlight;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Presenter.SelectItemFlightDomestic;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Dialog.NewDesignFilterFlightDomesticFragmentDialog;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.NewDesignFilterTrainFragmentDialog;
import hami.nasimbehesht724.BaseNetwork.BaseConfig;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.TimeDate;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilImageLoader;


public class NewDomesticListAdapter extends RecyclerView.Adapter<NewDomesticListAdapter.MyViewHolder> {

    private ArrayList<DomesticFlight> listItem;
    private ArrayList<DomesticFlight> listItemTemp;
    private Context context;
    private SelectItemFlightDomestic selectItemFlightDomestic;
    public final static String TYPE_FLIGHT_TCHARTER = "ticharter";
    public final static String TYPE_FLIGHT_TSYSTEM = "tisystem";
    private static final String TAG = "NewDomesticListAdapter";

    //-----------------------------------------------
    public NewDomesticListAdapter(Context context,
                                  ArrayList<DomesticFlight> list,
                                  SelectItemFlightDomestic selectItemFlightDomestic) {
        this.selectItemFlightDomestic = selectItemFlightDomestic;
        listItem = list;
        this.context = context;
        listItemTemp = new ArrayList<>();
        listItemTemp.addAll(listItem);
    }

    //-----------------------------------------------
    @Override
    public NewDomesticListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_new_flight_domestic, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final DomesticFlight outBound = listItem.get(position);
        String url = BaseConfig.FOLDER_IMAGE_DOMESTIC_URL + outBound.getAirlineCode() + ".png";
        UtilImageLoader.loadImage(context, viewHolder.imgLogoAirLine, url, R.mipmap.ic_airplan_top);
        viewHolder.txtTimeTakeOff.setText(outBound.getTakeoffTime());
        String duration = "";
        if (outBound.getArriveTime() != null && outBound.getArriveTime().length() != 0) {
            viewHolder.txtTimeLanding.setText(outBound.getArriveTime());
            duration = TimeDate.printDifferenceTime(outBound.getTakeoffTime(), outBound.getArriveTime(), outBound.getTime());
            viewHolder.txtTimeFlight.setText(duration);
        } else {
            viewHolder.txtTimeLanding.setText("--:--");
            duration = "---";
            viewHolder.txtTimeFlight.setText(duration);
        }
        viewHolder.txtDetails.setText("ظرفیت" + "(" + outBound.getShowNum() + " نفر " + ")");
        viewHolder.txtAirLineAndTypeClass.setText(outBound.getNoeF() + "(" + outBound.getFlightNumber() + ")");
        viewHolder.txtAirLine.setText(outBound.getAireLineNameF());
        long adultPrice = outBound.getAdultPrice1() == null ? Long.valueOf(outBound.getAdultPrice().replace(",", "")) : Long.valueOf(outBound.getAdultPrice1().replace(",", ""));
        long disCountAdultPrice = Long.valueOf(outBound.getDiscountPrice().replace(",", ""));
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
        public TextView txtTimeTakeOff, txtTimeLanding;
        public TextView txtAirLineAndTypeClass;
        public TextView txtAirLine;
        public TextView txtDetails;
        public TextView txtTimeFlight;
        public ImageView imgLogoAirLine;
        //public TextView txtFromPlace;
        //public TextView txtToPlace;
        public TextView txtCapacity;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_BOLD);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            txtPrice2 = (TextView) itemLayoutView.findViewById(R.id.tvPrice2);
            txtTimeLanding = (TextView) itemLayoutView.findViewById(R.id.txtTimeLanding);
            txtTimeTakeOff = (TextView) itemLayoutView.findViewById(R.id.txtTimeTakeOff);
            txtAirLineAndTypeClass = (TextView) itemLayoutView.findViewById(R.id.txtAirLineAndTypeClass);
            txtAirLine = (TextView) itemLayoutView.findViewById(R.id.txtAirLine);
            txtDetails = (TextView) itemLayoutView.findViewById(R.id.txtDetails);
            imgLogoAirLine = (ImageView) itemLayoutView.findViewById(R.id.imgLogoAirLine);
            txtTimeFlight = (TextView) itemLayoutView.findViewById(R.id.txtTimeFlight);
            //txtFromPlace = (TextView) itemLayoutView.findViewById(R.id.txtFromPlace);
            //txtToPlace = (TextView) itemLayoutView.findViewById(R.id.txtToPlace);
            txtCapacity = (TextView) itemLayoutView.findViewById(R.id.txtCapacity);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItemFlightDomestic.onSelectItemFlight(listItem.get(getLayoutPosition()));
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
    public ArrayList<DomesticFlight> filterAll(ArrayMap<String, List<String>> applied_filters) {
        ArrayList<DomesticFlight> listItemFiltered = new ArrayList<>();
        try {
            List<String> filterTime = applied_filters.get(NewDesignFilterFlightDomesticFragmentDialog.FILTER_CATEGORY_TIME_TAKE_OFF);
            List<String> filterPrice = applied_filters.get(NewDesignFilterFlightDomesticFragmentDialog.FILTER_CATEGORY_PRICE);
            List<String> filterAirLine = applied_filters.get(NewDesignFilterFlightDomesticFragmentDialog.FILTER_CATEGORY_AIRLINE);
            List<String> filterType = applied_filters.get(NewDesignFilterFlightDomesticFragmentDialog.FILTER_CATEGORY_FLIGHT_DOMESTIC_TYPE);
            List<String> filterSort = applied_filters.get(NewDesignFilterFlightDomesticFragmentDialog.FILTER_CATEGORY_SORT);
            ArrayList<DomesticFlight> temp = new ArrayList<>();
            listItem.clear();
            listItem.addAll(listItemTemp);
            //****************************
            if (filterTime != null) {
                for (DomesticFlight domesticFlight : listItem) {
                    for (int index = 0; index < filterTime.size(); index++) {
                        int hour = Integer.valueOf(domesticFlight.getDaytime());
                        int type = Integer.valueOf(filterTime.get(index));
                        if (hasTime(type, hour)) {
                            listItemFiltered.add(domesticFlight);
                        }
                    }
                }
            } else {
                listItemFiltered.addAll(listItem);
            }
            //****************************
            if (filterAirLine != null) {
                for (DomesticFlight domesticFlight : listItemFiltered) {
                    for (int index = 0; index < filterAirLine.size(); index++) {
                        if (domesticFlight.getAireLineName().contentEquals(filterAirLine.get(index))) {
                            temp.add(domesticFlight);
                            break;
                        }
                    }
                }
                listItemFiltered.clear();
                listItemFiltered.addAll(temp);
                temp.clear();
            }
            //****************************
            if (filterType != null) {
                for (DomesticFlight domesticFlight : listItemFiltered) {
                    for (int index = 0; index < filterType.size(); index++) {
                        if (filterType.get(index).contains("0") && domesticFlight.getNoe().contentEquals(TYPE_FLIGHT_TCHARTER)) {
                            temp.add(domesticFlight);
                            break;
                        } else if (filterType.get(index).contains("1") && domesticFlight.getNoe().contentEquals(TYPE_FLIGHT_TSYSTEM)) {
                            temp.add(domesticFlight);
                        }
                    }
                }
                listItemFiltered.clear();
                listItemFiltered.addAll(temp);
                temp.clear();
            }
            //****************************
            if (filterPrice != null) {
                Long filterPriceLong = Long.valueOf(filterPrice.get(0)) * NewDesignFilterTrainFragmentDialog.STEP_SIZE * 1000;
                for (DomesticFlight domesticFlight : listItemFiltered) {
                    if (filterPrice != null && filterPrice.size() > 0) {
                        Long priceLong = Long.valueOf(domesticFlight.getAdultPrice().replace(",", "")) / 10;
                        if (priceLong <= filterPriceLong) {
                            temp.add(domesticFlight);
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
                        sortByFlightAirline();
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
    public ArrayList<DomesticFlight> getFullItems() {
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
    public ArrayList<DomesticFlight> filterByTime(List<String> filter) {
        ArrayList<DomesticFlight> listItemFiltered = new ArrayList<>();
        for (DomesticFlight domesticFlight : listItem) {
            for (int index = 0; index < filter.size(); index++) {
                int hour = Integer.valueOf(domesticFlight.getDaytime());
                int type = Integer.valueOf(filter.get(index));
                if (hasTime(type, hour)) {
                    listItemFiltered.add(domesticFlight);
                }
            }
        }
        return listItemFiltered;
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
        try {
            Collections.sort(listItem, new Comparator<DomesticFlight>() {
                @Override
                public int compare(DomesticFlight domesticFlight1, DomesticFlight domesticFlight2) {
                    Integer value1 = Integer.valueOf(domesticFlight1.getShowNum());
                    Integer value2 = Integer.valueOf(domesticFlight2.getShowNum());
                    return value1.compareTo(value2);
                }
            });
            notifyDataSetChanged();
        } catch (Exception e) {


        }

    }

    //-----------------------------------------------
    public void sortByTime() {
        try {

            Collections.sort(listItem, new Comparator<DomesticFlight>() {
                @Override
                public int compare(DomesticFlight domesticFlight1, DomesticFlight domesticFlight2) {
                    Long value1 = TimeDate.getTime(domesticFlight1.getTakeoffTime());
                    Long value2 = TimeDate.getTime(domesticFlight2.getTakeoffTime());
                    return value1.compareTo(value2);
                    //return domesticFlight1.getTakeoffTime().compareToIgnoreCase(domesticFlight2.getTakeoffTime());
                }
            });
            notifyDataSetChanged();
        } catch (Exception e) {

        }
    }

    //-----------------------------------------------
    public void sortByMoney() {
        try {

            Collections.sort(listItem, new Comparator<DomesticFlight>() {
                @Override
                public int compare(DomesticFlight domesticFlight1, DomesticFlight domesticFlight2) {
                    Integer value1 = Integer.valueOf(domesticFlight1.getPrice().replace(",", ""));
                    Integer value2 = Integer.valueOf(domesticFlight2.getPrice().replace(",", ""));
                    return value1.compareTo(value2);
                }
            });
            notifyDataSetChanged();
        } catch (Exception e) {

        }
    }

    //-----------------------------------------------
    public void sortByFlightAirline() {
        try {
            Collections.sort(listItem, new Comparator<DomesticFlight>() {
                @Override
                public int compare(DomesticFlight domesticFlight1, DomesticFlight domesticFlight2) {
                    Integer value1 = domesticFlight1.getAirlineCode();
                    Integer value2 = domesticFlight2.getAirlineCode();
                    return value1.compareTo(value2);
                }
            });
            notifyDataSetChanged();
        } catch (Exception e) {

        }

    }
}