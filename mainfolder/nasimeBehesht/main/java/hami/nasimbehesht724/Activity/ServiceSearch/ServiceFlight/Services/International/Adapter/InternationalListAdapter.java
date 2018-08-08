package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Adapter;

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

import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.OutBound;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.SelectItemFlightInternational;
import hami.mainapp.BaseNetwork.BaseConfig;
import hami.mainapp.R;
import hami.mainapp.Util.UtilFonts;
import hami.mainapp.Util.UtilImageLoader;

/**
 * Created by renjer on 1/8/2017.
 */

public class InternationalListAdapter extends RecyclerView.Adapter<InternationalListAdapter.MyViewHolder> {

    private ArrayList<OutBound> listItem;
    private ArrayList<OutBound> listItemTemp;
    private Context context;
    private SelectItemFlightInternational selectItemFlightInternational;

    //-----------------------------------------------
    public InternationalListAdapter(Context context, ArrayList<OutBound> list, SelectItemFlightInternational selectItemFlightInternational) {
        this.selectItemFlightInternational = selectItemFlightInternational;
        listItem = list;
        listItemTemp = new ArrayList<>();
        listItemTemp.addAll(listItem);
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public InternationalListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_new_flight_international_went, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final OutBound outBound = listItem.get(position);
        String url = BaseConfig.BASE_URL_MASTER + BaseConfig.FOLDER_IMAGE_INTERNATIONAL_URL + outBound.getFileName();
        UtilImageLoader.loadImage(context, viewHolder.imgLogoAirLine, url, R.drawable.flight);
        viewHolder.txtTimeTakeOff.setText(outBound.getTakeoffTime());
        viewHolder.txtTimeLanding.setText(outBound.getArriveTime());
        viewHolder.txtAirLineAndTypeClass.setText(outBound.getAireLineName() + "(" + outBound.getType() + ")");
        viewHolder.tvPrice.setText(getFinalPrice(outBound.getAdultPrice()));
        String[] time = outBound.getFlightTime().split(":");
        if (time != null && time.length == 2) {
            viewHolder.txtFlyTime.setText(time[0] + "  ساعت" + " و " + time[1] + " دقیقه" + " ( " + outBound.getStops() + " توقف" + " ) ");
        } else {
            viewHolder.txtFlyTime.setText(outBound.getFlightTime() + " ( " + outBound.getStops() + " توقف" + " ) ");
        }
        viewHolder.imgService.setImageResource(R.mipmap.ic_airplan_top);
        viewHolder.txtFromPlace.setText(outBound.getLegs().get(0).getDepartureCityName());
        viewHolder.txtToPlace.setText(outBound.getLegs().get(outBound.getLegs().size() - 1).getArrivalCityName());
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
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTimeTakeOff, txtTimeLanding;
        public TextView txtAirLineAndTypeClass;
        public TextView txtFlyTime;
        public ImageView imgLogoAirLine;
        public TextView tvPrice;
        public TextView txtFromPlace;
        public TextView txtToPlace;
        public ImageView imgService;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_BOLD);
            tvPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            txtTimeLanding = (TextView) itemLayoutView.findViewById(R.id.txtTimeLanding);
            txtTimeTakeOff = (TextView) itemLayoutView.findViewById(R.id.txtTimeTakeOff);
            txtAirLineAndTypeClass = (TextView) itemLayoutView.findViewById(R.id.txtAirLineAndTypeClass);
            txtFlyTime = (TextView) itemLayoutView.findViewById(R.id.txtDetails);
            imgLogoAirLine = (ImageView) itemLayoutView.findViewById(R.id.imgLogoAirLine);
            txtFromPlace = (TextView) itemLayoutView.findViewById(R.id.txtFromPlace);
            txtToPlace = (TextView) itemLayoutView.findViewById(R.id.txtToPlace);
            imgService = (ImageView) itemLayoutView.findViewById(R.id.imgService);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItemFlightInternational.onSelectItemFlight(listItem.get(getLayoutPosition()));
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
//    public ArrayList<OutBound> filterAll(ArrayMap<String, List<String>> applied_filters) {
//        ArrayList<OutBound> listItemFiltered = new ArrayList<>();
//        try {
//            List<String> filterTimeTakeOffWent = applied_filters.get(NewDesignFilterFlightInternationalFragmentDialog.FILTER_CATEGORY_TIME_TAKE_OFF);
//            List<String> filterTimeTakeOffReturn = applied_filters.get(NewDesignFilterFlightInternationalFragmentDialog.FILTER_CATEGORY_TIME_TAKE_OFF_RETURN);
//            List<String> filterPrice = applied_filters.get(NewDesignFilterFlightInternationalFragmentDialog.FILTER_CATEGORY_PRICE);
//            List<String> filterAirLine = applied_filters.get(NewDesignFilterFlightInternationalFragmentDialog.FILTER_CATEGORY_AIRLINE);
//            List<String> filterSort = applied_filters.get(NewDesignFilterFlightInternationalFragmentDialog.FILTER_CATEGORY_SORT);
//            ArrayList<OutBound> temp = new ArrayList<>();
//            listItem.clear();
//            listItem.addAll(listItemTemp);
//            if (filterTimeTakeOffWent != null) {
//                for (OutBound outBound : listItem) {
//                    for (int index = 0; index < filterTimeTakeOffWent.size(); index++) {
//                        String[] time = outBound.getTakeoffTime().split(":");
//                        int hour = Integer.valueOf(time[0]);
//                        int type = Integer.valueOf(filterTimeTakeOffWent.get(index));
//                        if (hasTime(type, hour)) {
//                            listItemFiltered.add(outBound);
//                        }
//                    }
//                }
//            } else {
//                listItemFiltered.addAll(listItem);
//            }
//            //-----------------------------------------------
//            if (filterTimeTakeOffReturn != null) {
//                for (OutBound outBound : listItem) {
//                    for (int index = 0; index < filterTimeTakeOffReturn.size(); index++) {
//                        String[] time = outBound.getTakeoffTime().split(":");
//                        int hour = Integer.valueOf(time[0]);
//                        int type = Integer.valueOf(filterTimeTakeOffReturn.get(index));
//                        if (hasTime(type, hour)) {
//                            listItemFiltered.add(outBound);
//                        }
//                    }
//                }
//                listItemFiltered.clear();
//                listItemFiltered.addAll(temp);
//                temp.clear();
//            }
//            //-----------------------------------------------
//            if (filterAirLine != null) {
//                for (OutBound outBound : listItemFiltered) {
//                    for (int index = 0; index < filterAirLine.size(); index++) {
//                        Legs legs = outBound.getLegs().get(0);
//                        if (legs.getCarrierCode() == filterAirLine.get(index)) {
//                            temp.add(outBound);
//                            break;
//                        }
//                    }
//                }
//                listItemFiltered.clear();
//                listItemFiltered.addAll(temp);
//                temp.clear();
//            }
//            //-----------------------------------------------
//            if (filterPrice != null) {
//                for (OutBound outBound : listItemFiltered) {
//                    if (filterPrice != null && filterPrice.size() > 0) {
//                        Long priceLong = Long.valueOf(outBound.getAdultPrice().replace(",", "")) / 10;
//                        Long filterPriceLong = Long.valueOf(filterPrice.get(0)) * NewDesignFilterFlightInternationalFragmentDialog.STEP_SIZE * 1000;
//                        if (priceLong <= filterPriceLong) {
//                            temp.add(outBound);
//                        }
//                    }
//                }
//                listItemFiltered.clear();
//                listItemFiltered.addAll(temp);
//                temp.clear();
//            }
//
//            listItem.clear();
//            listItem.addAll(listItemFiltered);
//            notifyDataSetChanged();
//            if (filterSort != null) {
//                if (filterSort != null && filterSort.size() > 0) {
//                    String filter = filterSort.get(0);
//                    if (filter.contentEquals("0")) {
//                        sortByMoney();
//                    } else if (filter.contentEquals("1")) {
//                        sortByTime();
//                    } else if (filter.contentEquals("2")) {
//                        sortByFlightType();
//                    } else if (filter.contentEquals("3")) {
//                        sortByCapacity();
//                    }
//                }
//            }
//        } catch (Exception e) {
//
//        }
//        return listItemFiltered;
//    }

    //-----------------------------------------------
    private Boolean hasTime(int type, int hour) {
        try {

            if (type == 10 && ((hour >= 0 && hour <= 6))) {
                return true;
            } else if (type == 12 && (hour >= 7 && hour <= 10)) {
                return true;
            } else if (type == 14 && (hour >= 11 && hour <= 15)) {
                return true;
            } else if (type == 16 && (hour >= 16 && hour <= 19)) {
                return true;
            } else if (type == 18 && (hour >= 20 && hour <= 23)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
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


}