package hami.mainapp.train.Services.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hami.common.BaseNetwork.BaseConfig;
import com.hami.common.Util.TimeDate;
import com.hami.common.Util.UtilFonts;
import com.hami.common.Util.UtilImageLoader;

import hami.mainapp.R;
import hami.mainapp.train.NewDesignFilterTrainFragmentDialog;

import hami.mainapp.train.Services.Controller.Model.TrainResponse;
import hami.mainapp.train.Services.Controller.Presenter.SelectItemTrainSearchListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


/**
 * Created by renjer on 1/8/2017.
 */

public class TrainListAdapter extends RecyclerView.Adapter<TrainListAdapter.MyViewHolder> {

    private ArrayList<TrainResponse> listItem;
    private ArrayList<TrainResponse> listItemTemp;
    private Context context;
    private SelectItemTrainSearchListener selectItemTrainSearchListener;
    private Boolean hasSortReserveByTrainCapacity = false, hasSortReserveByTime = false, hasSortReserveByMoney = false;

    //-----------------------------------------------
    public TrainListAdapter(Context context, ArrayList<TrainResponse> list, SelectItemTrainSearchListener selectItemTrainSearchListener) {
        this.selectItemTrainSearchListener = selectItemTrainSearchListener;
        listItem = list;
        listItemTemp = new ArrayList<>();
        listItemTemp.addAll(listItem);
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public TrainListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_new_flight_domestic, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final TrainResponse trainResponse = listItem.get(position);
        String url = BaseConfig.FOLDER_IMAGE_TRAIN_URL + trainResponse.getOwner().toLowerCase() + ".png";
        UtilImageLoader.loadImage(context, viewHolder.imgLogoAirLine, url, R.drawable.train);
        if (!hasCapacity(trainResponse.getCapacity())) {
            viewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.main_color_grey_100));
        } else
            viewHolder.itemView.setBackgroundColor(Color.WHITE);
        viewHolder.txtTimeTakeOff.setText(trainResponse.getExitTime());
        viewHolder.txtTimeLanding.setText(trainResponse.getTimeOfArrival());
        long adultPrice = Long.valueOf(trainResponse.getPricefinal().replace(",", ""));
        long disCountAdultPrice = Long.valueOf(trainResponse.getDiscountprice().replace(",", ""));
        if (disCountAdultPrice != adultPrice) {
            viewHolder.txtPrice.setText(getFinalPrice(String.valueOf(disCountAdultPrice)));
            viewHolder.txtPrice2.setText(getFinalPrice(String.valueOf(adultPrice)));
            viewHolder.txtPrice2.setPaintFlags(viewHolder.txtPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.txtPrice2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.txtPrice.setText(getFinalPrice(String.valueOf(adultPrice)));
            viewHolder.txtPrice2.setVisibility(View.GONE);
        }
        //viewHolder.txtPrice.setText(getFinalPrice(trainResponse.getPrice()));
        viewHolder.txtFlyTime.setText(context.getText(R.string.capacityFlight) + ":" + trainResponse.getCapacity());
        String type = "";
        if (trainResponse.getIsCompartment().contentEquals("1")) {
            type = (context.getText(R.string.cope) + " " + trainResponse.getCompartmentCapicity() + " " + context.getText(R.string.unitCountTrain));
        } else {
            type = (context.getText(R.string.hall) + " " + trainResponse.getCompartmentCapicity() + " " + context.getText(R.string.unitCountTrain));
        }
        viewHolder.txtAirLineAndTypeClass.setText(trainResponse.getWagonName() + "(" + type + ")");
        viewHolder.imgService.setImageResource(R.mipmap.ic_train_gray);
    }

    //-----------------------------------------------
    private Boolean hasCapacity(String item) {
        try {
            int counter = Integer.valueOf(item);
            return counter > 0;
        } catch (Exception e) {
            return false;
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

    public ArrayList<TrainResponse> getFullItems() {
        return listItemTemp;
    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtPrice, txtPrice2;
        public TextView txtTimeTakeOff, txtTimeLanding;
        public TextView txtAirLineAndTypeClass;
        public TextView txtFlyTime;
        public ImageView imgLogoAirLine;

        public ImageView imgService;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            imgService = (ImageView) itemLayoutView.findViewById(R.id.imgService);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            txtPrice2 = (TextView) itemLayoutView.findViewById(R.id.tvPrice2);
            txtTimeLanding = (TextView) itemLayoutView.findViewById(R.id.txtTimeLanding);
            txtTimeTakeOff = (TextView) itemLayoutView.findViewById(R.id.txtTimeTakeOff);
            txtAirLineAndTypeClass = (TextView) itemLayoutView.findViewById(R.id.txtAirLineAndTypeClass);
            txtFlyTime = (TextView) itemLayoutView.findViewById(R.id.txtDetails);
            imgLogoAirLine = (ImageView) itemLayoutView.findViewById(R.id.imgLogoAirLine);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItemTrainSearchListener.onSelectItemTrain(listItem.get(getLayoutPosition()));
                }
            });

        }
    }

    //-----------------------------------------------
    public void clearList() {
        listItem = new ArrayList<>();
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    //-----------------------------------------------
    public ArrayList<TrainResponse> filterAll(ArrayMap<String, List<String>> applied_filters) {
        ArrayList<TrainResponse> listItemFiltered = new ArrayList<>();
        try {

            List<String> filterTimeTakeOff = applied_filters.get(NewDesignFilterTrainFragmentDialog.FILTER_CATEGORY_TIME_TAKE_OFF);
            List<String> filterTimeLanding = applied_filters.get(NewDesignFilterTrainFragmentDialog.FILTER_CATEGORY_TIME_LANDING);
            List<String> filterPrice = applied_filters.get(NewDesignFilterTrainFragmentDialog.FILTER_CATEGORY_PRICE);
            List<String> filterTrainType = applied_filters.get(NewDesignFilterTrainFragmentDialog.FILTER_CATEGORY_TRAIN_TYPE);
            List<String> filterTrainCompany = applied_filters.get(NewDesignFilterTrainFragmentDialog.FILTER_CATEGORY_TRAIN_COMPANY);
            List<String> filterSort = applied_filters.get(NewDesignFilterTrainFragmentDialog.FILTER_CATEGORY_SORT);
            ArrayList<TrainResponse> temp = new ArrayList<>();
            listItem.clear();
            listItem.addAll(listItemTemp);
            //****************************
            if (filterTimeTakeOff != null) {
                for (TrainResponse trainResponse : listItem) {
                    for (int index = 0; index < filterTimeTakeOff.size(); index++) {
                        int hour = Integer.valueOf(trainResponse.getDayTime());
                        int type = Integer.valueOf(filterTimeTakeOff.get(index));
                        if (hasTime(type, hour)) {
                            listItemFiltered.add(trainResponse);
                        }
                    }
                }
            } else {
                listItemFiltered.addAll(listItem);
            }
            //****************************
            if (filterTimeLanding != null) {
                for (TrainResponse trainResponse : listItemFiltered) {
                    for (int index = 0; index < filterTimeLanding.size(); index++) {
                        int hour = Integer.valueOf(trainResponse.getDayTime());
                        int type = Integer.valueOf(filterTimeLanding.get(index));
                        if (hasTime(type, hour)) {
                            temp.add(trainResponse);
                            //listItemFiltered.add(trainResponse);
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
                for (TrainResponse trainResponse : listItemFiltered) {
                    Long currentPrice = Long.valueOf(trainResponse.getFullPriceRial().replace(",", "")) / 10;
                    if (currentPrice <= filterPriceLong) {
                        temp.add(trainResponse);
                    }
                }
                listItemFiltered.clear();
                listItemFiltered.addAll(temp);
                temp.clear();
            }
            //****************************
            if (filterTrainType != null) {
                for (TrainResponse trainResponse : listItemFiltered) {
                    for (int index = 0; index < filterTrainType.size(); index++) {
                        if (trainResponse.getIsCompartment().contentEquals(filterTrainType.get(index))) {
                            temp.add(trainResponse);
                        }
                    }
                }
                listItemFiltered.clear();
                listItemFiltered.addAll(temp);
                temp.clear();
            }
            if (filterTrainCompany != null) {
                for (TrainResponse trainResponse : listItemFiltered) {
                    for (int index = 0; index < filterTrainCompany.size(); index++) {
                        if (trainResponse.getOwner().contentEquals(filterTrainCompany.get(index))) {
                            temp.add(trainResponse);
                        }
                    }
                }
                listItemFiltered.clear();
                listItemFiltered.addAll(temp);
                temp.clear();
            }
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
                        sortByTimeTakeOff();
                    } else if (filter.contentEquals("2")) {
                        sortByTimeLanding();
                    } else if (filter.contentEquals("3")) {
                        sortByTrainCapacity();
                    }
                }
            }
        } catch (Exception e) {
            resetFilter();
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
    public void resetFilter() {
        try {
            listItem.clear();
            listItem.addAll(listItemTemp);
            notifyDataSetChanged();
        } catch (Exception e) {

        }
    }

    //-----------------------------------------------
    public void sortByTrainCapacity() {

        Collections.sort(listItem, new Comparator<TrainResponse>() {
            @Override
            public int compare(TrainResponse trainResponse1, TrainResponse trainResponse2) {
                Integer value1 = Integer.valueOf(trainResponse1.getCapacity());
                Integer value2 = Integer.valueOf(trainResponse2.getCapacity());
                return value1.compareTo(value2);
            }
        });
        hasSortReserveByTrainCapacity = true;

        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public void sortByTimeTakeOff() {

        Collections.sort(listItem, new Comparator<TrainResponse>() {
            @Override
            public int compare(TrainResponse trainResponse1, TrainResponse trainResponse2) {
                Long value1 = TimeDate.getTime(trainResponse1.getExitTime());
                Long value2 = TimeDate.getTime(trainResponse2.getExitTime());
                return value1.compareTo(value2);
            }
        });
        hasSortReserveByTime = true;
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public void sortByTimeLanding() {

        Collections.sort(listItem, new Comparator<TrainResponse>() {
            @Override
            public int compare(TrainResponse trainResponse1, TrainResponse trainResponse2) {
                Long value1 = TimeDate.getTime(trainResponse1.getTimeOfArrival());
                Long value2 = TimeDate.getTime(trainResponse2.getTimeOfArrival());
                return value1.compareTo(value2);
            }
        });
        hasSortReserveByTime = true;
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public void sortByMoney() {

        Collections.sort(listItem, new Comparator<TrainResponse>() {
            @Override
            public int compare(TrainResponse trainResponse1, TrainResponse trainResponse2) {
                Integer value1 = Integer.valueOf(trainResponse1.getPrice().replace(",", ""));
                Integer value2 = Integer.valueOf(trainResponse2.getPrice().replace(",", ""));
                return value1.compareTo(value2);
            }
        });
        hasSortReserveByMoney = true;
        notifyDataSetChanged();
    }
}