package com.hami.serviceflight.Services.International.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;
import com.hami.common.BaseController.SelectItemList;
import com.hami.common.BaseNetwork.BaseConfig;
import com.hami.common.Util.TimeDate;
import com.hami.common.Util.UtilFonts;
import com.hami.serviceflight.R;
import com.hami.serviceflight.Services.International.Controller.Model2.AllFlightInternationalIati;
import com.hami.serviceflight.Services.International.Controller.Model2.AllFlightInternationalParto;
import com.hami.serviceflight.Services.International.Controller.Model2.FinalFlightInternationalIati;
import com.hami.serviceflight.Services.International.Controller.Model2.LegsIati;
import com.hami.serviceflight.Services.International.Controller.Model2.LegsParto;
import com.hami.serviceflight.Services.International.Dialog.NewDesignFilterFlightInternationalFragmentDialog;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by renjer on 1/8/2017.
 */

public class NewInternationalListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Object> listItem;
    private ArrayList<Object> listItemTemp;
    private LinkedTreeMap<String, String> airlineListParto;
    private LinkedTreeMap<String, String> airlineListIatis;
    private Context context;
    private SelectItemList<Object> selectItemFlightInternational;
    private static final String TAG = "NewInternationalListAdapter";

    //-----------------------------------------------
    public NewInternationalListAdapter(Context context, SelectItemList<Object> selectItemFlightInternational) {
        this.selectItemFlightInternational = selectItemFlightInternational;
        listItem = new ArrayList<>();
        listItemTemp = new ArrayList<>();
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public int getItemViewType(int position) {
        if (listItem.get(position) instanceof AllFlightInternationalParto) {
            return ((AllFlightInternationalParto) listItem.get(position)).getLegs().size() == 2 ? AllFlightInternationalParto.TYPE_WENT_AND_RETURN : AllFlightInternationalParto.TYPE_WENT;
        } else if (listItem.get(position) instanceof FinalFlightInternationalIati) {
            return FinalFlightInternationalIati.TYPE_WENT_AND_RETURN;
        } else if (listItem.get(position) instanceof AllFlightInternationalIati) {
            return AllFlightInternationalIati.TYPE_WENT;
        }
        return -1;
    }

    //-----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == AllFlightInternationalParto.TYPE_WENT || viewType == AllFlightInternationalIati.TYPE_WENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_new_flight_international_went, null);
            MyViewHolderWent myViewHolder = new MyViewHolderWent(view);
            return myViewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_new_flight_international_went_and_return, null);
            MyViewHolderWentAndReturn myViewHolder = new MyViewHolderWentAndReturn(view);
            return myViewHolder;
        }
    }


    //-----------------------------------------------
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder.getItemViewType() == AllFlightInternationalParto.TYPE_WENT) {
            MyViewHolderWent myViewHolderWent = (MyViewHolderWent) viewHolder;
            configureDefaultViewHolderPartoWent(myViewHolderWent, position);
        } else if (viewHolder.getItemViewType() == AllFlightInternationalParto.TYPE_WENT_AND_RETURN) {
            MyViewHolderWentAndReturn myViewHolder = (MyViewHolderWentAndReturn) viewHolder;
            configureDefaultViewHolderPartoWentAndReturn(myViewHolder, position);
        } else if (viewHolder.getItemViewType() == AllFlightInternationalIati.TYPE_WENT) {
            MyViewHolderWent myViewHolderWent = (MyViewHolderWent) viewHolder;
            configureDefaultViewHolderIatiWent(myViewHolderWent, position);
        } else if (viewHolder.getItemViewType() == FinalFlightInternationalIati.TYPE_WENT_AND_RETURN) {
            MyViewHolderWentAndReturn myViewHolder = (MyViewHolderWentAndReturn) viewHolder;
            configureDefaultViewHolderIatiWentAndReturn(myViewHolder, position);

        }

    }

    //-----------------------------------------------
    public void addParto(ArrayList<AllFlightInternationalParto> allFlightInternationalPartos, Object airlineParto) {
        listItem.addAll(allFlightInternationalPartos);
        listItemTemp.addAll(allFlightInternationalPartos);
        airlineListParto = (LinkedTreeMap<String, String>) airlineParto;
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public void addIati(ArrayList<AllFlightInternationalIati> allFlightInternationalIatis, Object airlineIatiObject, Boolean hasReturn) {
        this.airlineListIatis = (LinkedTreeMap<String, String>) airlineIatiObject;
        if (hasReturn) {
            addIatiWentAndReturn(allFlightInternationalIatis);
        } else {
            addIatiWent(allFlightInternationalIatis);
        }

    }

    //-----------------------------------------------
    public void addIatiWentAndReturn(ArrayList<AllFlightInternationalIati> allFlightInternationalIatis) {
        listItem.addAll(getFlightIatisWentAndReturn(allFlightInternationalIatis));
        listItemTemp.addAll(getFlightIatisWentAndReturn(allFlightInternationalIatis));
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public void addIatiWent(ArrayList<AllFlightInternationalIati> allFlightInternationalIatis) {
        listItem.addAll(allFlightInternationalIatis);
        listItemTemp.addAll(allFlightInternationalIatis);
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    private ArrayList<FinalFlightInternationalIati> getFlightIatisWentAndReturn(ArrayList<AllFlightInternationalIati> allFlightInternationalIatis) {
        ArrayList<FinalFlightInternationalIati> finalListIatis = new ArrayList<>();
        ArrayList<AllFlightInternationalIati> allFlightInternationalIatisWent = new ArrayList<>();
        ArrayList<AllFlightInternationalIati> allFlightInternationalIatisReturn = new ArrayList<>();

        for (int index = 0; index < allFlightInternationalIatis.size(); index++) {
            AllFlightInternationalIati currentObject = allFlightInternationalIatis.get(index);
            if (currentObject.getReturnFlight()) {
                allFlightInternationalIatisReturn.add(currentObject);
            } else {
                allFlightInternationalIatisWent.add(currentObject);
            }
        }
        for (int indexWent = 0; indexWent < allFlightInternationalIatisWent.size(); indexWent++) {
            AllFlightInternationalIati currentObjectWent = allFlightInternationalIatisWent.get(indexWent);
            for (int indexReturn = 0; indexReturn < allFlightInternationalIatisReturn.size(); indexReturn++) {
                AllFlightInternationalIati currentObjectReturn = allFlightInternationalIatisReturn.get(indexReturn);
                if (currentObjectWent.getPricingType().contentEquals(AllFlightInternationalIati.PRICE_TYPE_PACKAGED) &&
                        currentObjectReturn.getPricingType().contentEquals(AllFlightInternationalIati.PRICE_TYPE_PACKAGED) &&
                        currentObjectWent.getPackageId() == currentObjectReturn.getPackageId() &&
                        currentObjectWent.getAdultPrice() == currentObjectReturn.getAdultPrice() &&
                        currentObjectWent.getProviderKey().contentEquals(currentObjectReturn.getProviderKey())
                        ) {
                    finalListIatis.add(new FinalFlightInternationalIati(currentObjectWent, currentObjectReturn, currentObjectWent.getAdultPrice(), currentObjectWent.getDiscountadultprice(), currentObjectWent.getChildPrice(), currentObjectWent.getInfantPrice()));

                } else if (currentObjectWent.getPricingType().contentEquals(AllFlightInternationalIati.PRICE_TYPE_FREE_FORM) &&
                        currentObjectReturn.getPricingType().contentEquals(AllFlightInternationalIati.PRICE_TYPE_FREE_FORM)) {
                    long finalPriceAdults = currentObjectWent.getAdultPrice() + currentObjectReturn.getAdultPrice();
                    long finalPriceChild = currentObjectWent.getChildPrice() + currentObjectReturn.getChildPrice();
                    long finalPriceInfant = currentObjectWent.getInfantPrice() + currentObjectReturn.getInfantPrice();
                    long finalPriceAdultsDisCount = currentObjectWent.getDiscountadultprice() + currentObjectReturn.getDiscountadultprice();
                    finalListIatis.add(new FinalFlightInternationalIati(currentObjectWent, currentObjectReturn, finalPriceAdults, finalPriceAdultsDisCount, finalPriceChild, finalPriceInfant));

                }
            }
        }
        return finalListIatis;
    }

    //-----------------------------------------------
    private void configureDefaultViewHolderPartoWent(MyViewHolderWent viewHolder, int position) {
        AllFlightInternationalParto allFlightInternationalParto = (AllFlightInternationalParto) listItem.get(position);
        Picasso.with(context)
                .load(BaseConfig.BASE_URL_MASTER +
                        BaseConfig.FOLDER_IMAGE_INTERNATIONAL_URL + allFlightInternationalParto.getOutboundAirline() + ".png")
                .into(viewHolder.imgLogoAirLine);
        ArrayList<LegsParto> listLegs = allFlightInternationalParto.getLegs().get(0).getListLegs();
        String departureDateTime = TimeDate.getTime(null, listLegs.get(0).getDepartureDateTime());
        String arrivalDateTime = TimeDate.getTime(null, listLegs.get(listLegs.size() - 1).getArrivalDateTime());
        viewHolder.txtTimeTakeOff.setText(departureDateTime);
        viewHolder.txtTimeLanding.setText(arrivalDateTime);
        String airlineName = airlineListParto.get(allFlightInternationalParto.getOutboundAirline());
        viewHolder.txtAirLine.setText(airlineName);
        TimeDate.ResultDateDiff finalResult = new TimeDate.ResultDateDiff();
        for (int index = 0; index < listLegs.size(); index++) {
            if ((index + 1) <= listLegs.size() - 1) {
                TimeDate.ResultDateDiff resultDateDiff = TimeDate.dateDiff(listLegs.get(index).getArrivalDateTime(), listLegs.get(index + 1).getDepartureDateTime());
                finalResult.setElapsedHours(finalResult.getElapsedHours() + resultDateDiff.getElapsedHours());
                finalResult.setElapsedMinutes(finalResult.getElapsedMinutes() + resultDateDiff.getElapsedMinutes());
            }
        }
        viewHolder.txtTimeStops.setText(context.getString(R.string.flightStops) + ":" + finalResult.getElapsedHours() + "  ساعت" + " و " + finalResult.getElapsedMinutes() + " دقیقه");
        if (allFlightInternationalParto.getDiscountadultprice() != allFlightInternationalParto.getAdultPrice()) {
            viewHolder.tvPrice.setText(getFinalPrice(String.valueOf(allFlightInternationalParto.getDiscountadultprice())));
            viewHolder.tvPrice2.setText(getFinalPrice(String.valueOf(allFlightInternationalParto.getAdultPrice())));
            viewHolder.tvPrice2.setPaintFlags(viewHolder.tvPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tvPrice2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvPrice.setText(getFinalPrice(String.valueOf(allFlightInternationalParto.getAdultPrice())));
            viewHolder.tvPrice2.setVisibility(View.GONE);
        }
        TimeDate.ResultDateDiff resultDateDiff = TimeDate.dateDiff(listLegs.get(0).getDepartureDateTime(), listLegs.get(listLegs.size() - 1).getArrivalDateTime());


        if (resultDateDiff != null) {
            viewHolder.txtFlyTime.setText(context.getString(R.string.flightDuration) + ":" + resultDateDiff.getElapsedHours() + "  ساعت" + " و " + resultDateDiff.getElapsedMinutes() + " دقیقه");
        }
        //viewHolder.txtFromPlace.setText(listLegs.get(0).getDepartureAirportLocationCode());
        //viewHolder.txtToPlace.setText(listLegs.get(listLegs.size() - 1).getArrivalAirportLocationCode());
        String stops = allFlightInternationalParto.getOutboundStops() == 0 ? "بدون" : String.valueOf(allFlightInternationalParto.getOutboundStops());
        viewHolder.txtStops.setText("(" + stops + " " + context.getString(R.string.stops) + ")");
    }

    //-----------------------------------------------
    private void configureDefaultViewHolderPartoWentAndReturn(MyViewHolderWentAndReturn viewHolder, int position) {
        AllFlightInternationalParto allFlightInternationalParto = (AllFlightInternationalParto) listItem.get(position);
        //==============Went Flight
        Picasso.with(context)
                .load(BaseConfig.BASE_URL_MASTER +
                        BaseConfig.FOLDER_IMAGE_INTERNATIONAL_URL + allFlightInternationalParto.getOutboundAirline() + ".png")
                .into(viewHolder.imgLogoAirLine);
        ArrayList<LegsParto> listLegs = allFlightInternationalParto.getLegs().get(0).getListLegs();
        String departureDateTime = TimeDate.getTime(null, listLegs.get(0).getDepartureDateTime());
        String arrivalDateTime = TimeDate.getTime(null, listLegs.get(listLegs.size() - 1).getArrivalDateTime());
        viewHolder.txtTimeTakeOff.setText(departureDateTime);
        viewHolder.txtTimeLanding.setText(arrivalDateTime);
        String airlineName = airlineListParto.get(allFlightInternationalParto.getOutboundAirline());
        viewHolder.txtAirLine.setText(airlineName);
        TimeDate.ResultDateDiff finalResult = new TimeDate.ResultDateDiff();
        for (int index = 0; index < listLegs.size(); index++) {
            if ((index + 1) <= listLegs.size() - 1) {
                TimeDate.ResultDateDiff resultDateDiff = TimeDate.dateDiff(listLegs.get(index).getArrivalDateTime(), listLegs.get(index + 1).getDepartureDateTime());
                finalResult.setElapsedHours(finalResult.getElapsedHours() + resultDateDiff.getElapsedHours());
                finalResult.setElapsedMinutes(finalResult.getElapsedMinutes() + resultDateDiff.getElapsedMinutes());
            }
        }
        viewHolder.txtTimeStops.setText(context.getString(R.string.flightStops) + ":" + finalResult.getElapsedHours() + "  ساعت" + " و " + finalResult.getElapsedMinutes() + " دقیقه");
        if (allFlightInternationalParto.getDiscountadultprice() != allFlightInternationalParto.getAdultPrice()) {
            viewHolder.tvPrice.setText(getFinalPrice(String.valueOf(allFlightInternationalParto.getDiscountadultprice())));
            viewHolder.tvPrice2.setText(getFinalPrice(String.valueOf(allFlightInternationalParto.getAdultPrice())));
            viewHolder.tvPrice2.setPaintFlags(viewHolder.tvPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tvPrice2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvPrice.setText(getFinalPrice(String.valueOf(allFlightInternationalParto.getAdultPrice())));
            viewHolder.tvPrice2.setVisibility(View.GONE);
        }
        TimeDate.ResultDateDiff resultDateDiff = TimeDate.dateDiff(listLegs.get(0).getDepartureDateTime(), listLegs.get(listLegs.size() - 1).getArrivalDateTime());
        if (resultDateDiff != null) {
            viewHolder.txtFlyTime.setText(context.getString(R.string.flightDuration) + ":" + resultDateDiff.getElapsedHours() + "  ساعت" + " و " + resultDateDiff.getElapsedMinutes() + " دقیقه");
        }
        //viewHolder.txtFromPlace.setText(listLegs.get(0).getDepartureAirportLocationCode());
        //viewHolder.txtToPlace.setText(listLegs.get(listLegs.size() - 1).getArrivalAirportLocationCode());
        String stops = allFlightInternationalParto.getOutboundStops() == 0 ? "بدون" : String.valueOf(allFlightInternationalParto.getOutboundStops());
        viewHolder.txtStops.setText("(" + stops + " " + context.getString(R.string.stops) + ")");
        //==============Return Flight
        ArrayList<LegsParto> listLegsReturn = allFlightInternationalParto.getLegs().get(1).getListLegs();
        Picasso.with(context)
                .load(BaseConfig.BASE_URL_MASTER +
                        BaseConfig.FOLDER_IMAGE_INTERNATIONAL_URL + allFlightInternationalParto.getReturnAirline() + ".png")
                .into(viewHolder.imgLogoAirLineReturn);

        String departureDateTimeReturn = TimeDate.getTime(null, listLegsReturn.get(0).getDepartureDateTime());
        String arrivalDateTimeReturn = TimeDate.getTime(null, listLegsReturn.get(listLegsReturn.size() - 1).getArrivalDateTime());
        viewHolder.txtTimeTakeOffReturn.setText(departureDateTimeReturn);
        viewHolder.txtTimeLandingReturn.setText(arrivalDateTimeReturn);
        String airlineNameReturn = airlineListParto.get(allFlightInternationalParto.getReturnAirline());
        viewHolder.txtAirLineReturn.setText(airlineNameReturn);
        TimeDate.ResultDateDiff resultDateDiffReturn = TimeDate.dateDiff(listLegsReturn.get(0).getDepartureDateTime(), listLegsReturn.get(listLegsReturn.size() - 1).getArrivalDateTime());
        TimeDate.ResultDateDiff finalResultReturn = new TimeDate.ResultDateDiff();
        for (int index = 0; index < listLegs.size(); index++) {
            if ((index + 1) <= listLegsReturn.size() - 1) {
                TimeDate.ResultDateDiff resultDateDiffReturnX = TimeDate.dateDiff(listLegsReturn.get(index).getArrivalDateTime(), listLegsReturn.get(index + 1).getDepartureDateTime());
                finalResultReturn.setElapsedHours(finalResultReturn.getElapsedHours() + resultDateDiffReturnX.getElapsedHours());
                finalResultReturn.setElapsedMinutes(finalResultReturn.getElapsedMinutes() + resultDateDiffReturnX.getElapsedMinutes());
            }
        }
        viewHolder.txtTimeStopsReturn.setText(context.getString(R.string.flightStops) + ":" + finalResultReturn.getElapsedHours() + "  ساعت" + " و " + finalResultReturn.getElapsedMinutes() + " دقیقه");

        if (resultDateDiffReturn != null) {
            viewHolder.txtFlyTimeReturn.setText(context.getString(R.string.flightDuration) + ":" + resultDateDiffReturn.getElapsedHours() + "  ساعت" + " و " + resultDateDiffReturn.getElapsedMinutes() + " دقیقه");
        }
        //viewHolder.txtFromPlaceReturn.setText(listLegsReturn.get(0).getDepartureAirportLocationCode());
        //viewHolder.txtToPlaceReturn.setText(listLegsReturn.get(listLegsReturn.size() - 1).getArrivalAirportLocationCode());
        String stopsReturn = allFlightInternationalParto.getReturnStops() == 0 ? "بدون" : String.valueOf(allFlightInternationalParto.getReturnStops());
        viewHolder.txtStopsReturn.setText("(" + stopsReturn + " " + context.getString(R.string.stops) + ")");

    }

    //-----------------------------------------------
    private void configureDefaultViewHolderIatiWent(MyViewHolderWent viewHolder, int position) {
        AllFlightInternationalIati allFlightInternationalIati = (AllFlightInternationalIati) listItem.get(position);
        Picasso.with(context)
                .load(BaseConfig.BASE_URL_MASTER +
                        BaseConfig.FOLDER_IMAGE_INTERNATIONAL_URL +
                        allFlightInternationalIati.getAirline() + ".png")
                .into(viewHolder.imgLogoAirLine);
        ArrayList<LegsIati> listLegs = allFlightInternationalIati.getLegsList();
        String departureDateTime = TimeDate.getTime(null, listLegs.get(0).getDepartureTime());
        String arrivalDateTime = TimeDate.getTime(null, listLegs.get(listLegs.size() - 1).getArrivalTime());
        viewHolder.txtTimeTakeOff.setText(departureDateTime);
        viewHolder.txtTimeLanding.setText(arrivalDateTime);
        String airlineName = airlineListIatis.get(allFlightInternationalIati.getAirline());
        viewHolder.txtAirLine.setText(airlineName);
        int waitTimeBeforeNextLeg = 0;
        for (LegsIati legsIati : allFlightInternationalIati.getLegsList()) {
            waitTimeBeforeNextLeg += legsIati.getWaitTimeBeforeNextLeg();
        }
        TimeDate.ResultDateDiff resultDateDiffWating = TimeDate.convertToHourMin(waitTimeBeforeNextLeg);
        viewHolder.txtTimeStops.setText(context.getString(R.string.flightStops) + ":" + resultDateDiffWating.getElapsedHours() + "  ساعت" + " و " + resultDateDiffWating.getElapsedMinutes() + " دقیقه");
        if (allFlightInternationalIati.getDiscountadultprice() != allFlightInternationalIati.getAdultPrice()) {
            viewHolder.tvPrice.setText(getFinalPrice(String.valueOf(allFlightInternationalIati.getDiscountadultprice())));
            viewHolder.tvPrice2.setText(getFinalPrice(String.valueOf(allFlightInternationalIati.getAdultPrice())));
            viewHolder.tvPrice2.setPaintFlags(viewHolder.tvPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tvPrice2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvPrice.setText(getFinalPrice(String.valueOf(allFlightInternationalIati.getAdultPrice())));
            viewHolder.tvPrice2.setVisibility(View.GONE);
        }
        TimeDate.ResultDateDiff resultDateDiff = TimeDate.dateDiff(listLegs.get(0).getDepartureTime(), listLegs.get(listLegs.size() - 1).getArrivalTime());
        if (resultDateDiff != null) {
            viewHolder.txtFlyTime.setText(context.getString(R.string.flightDuration) + ":" + resultDateDiff.getElapsedHours() + "  ساعت" + " و " + resultDateDiff.getElapsedMinutes() + " دقیقه");
        }
        //viewHolder.txtFromPlace.setText(listLegs.get(0).getDepartureAirport());
        //viewHolder.txtToPlace.setText(listLegs.get(listLegs.size() - 1).getArrivalAirport());
        String stopsReturn = allFlightInternationalIati.getStops() == 0 ? "بدون" : String.valueOf(allFlightInternationalIati.getStops());
        viewHolder.txtStops.setText("(" + stopsReturn + " " + context.getString(R.string.stops) + ")");
    }

    //-----------------------------------------------
    private void configureDefaultViewHolderIatiWentAndReturn(MyViewHolderWentAndReturn viewHolder, int position) {
        FinalFlightInternationalIati finalFlightInternationalIati = (FinalFlightInternationalIati) listItem.get(position);
        Picasso.with(context)
                .load(BaseConfig.BASE_URL_MASTER +
                        BaseConfig.FOLDER_IMAGE_INTERNATIONAL_URL +
                        finalFlightInternationalIati.getAllFlightInternationalIatiWent().getAirline() + ".png")
                .into(viewHolder.imgLogoAirLine);
        ArrayList<LegsIati> listLegs = finalFlightInternationalIati.getAllFlightInternationalIatiWent().getLegsList();
        String departureDateTime = TimeDate.getTime(null, listLegs.get(0).getDepartureTime());
        String arrivalDateTime = TimeDate.getTime(null, listLegs.get(listLegs.size() - 1).getArrivalTime());
        viewHolder.txtTimeTakeOff.setText(departureDateTime);
        viewHolder.txtTimeLanding.setText(arrivalDateTime);
        int waitTimeBeforeNextLeg = 0;
        for (LegsIati legsIati : finalFlightInternationalIati.getAllFlightInternationalIatiWent().getLegsList()) {
            waitTimeBeforeNextLeg += legsIati.getWaitTimeBeforeNextLeg();
        }
        TimeDate.ResultDateDiff resultDateDiffWating = TimeDate.convertToHourMin(waitTimeBeforeNextLeg);
        viewHolder.txtTimeStops.setText(context.getString(R.string.flightStops) + ":" + resultDateDiffWating.getElapsedHours() + "  ساعت" + " و " + resultDateDiffWating.getElapsedMinutes() + " دقیقه");
        String airlineName = airlineListIatis.get(finalFlightInternationalIati.getAllFlightInternationalIatiWent().getAirline());
        viewHolder.txtAirLine.setText(airlineName);
        if (finalFlightInternationalIati.getDisCountAdultPrice() != finalFlightInternationalIati.getPriceAdults()) {
            viewHolder.tvPrice.setText(getFinalPrice(String.valueOf(finalFlightInternationalIati.getDisCountAdultPrice())));
            viewHolder.tvPrice2.setText(getFinalPrice(String.valueOf(finalFlightInternationalIati.getPriceAdults())));
            viewHolder.tvPrice2.setPaintFlags(viewHolder.tvPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tvPrice2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvPrice.setText(getFinalPrice(String.valueOf(finalFlightInternationalIati.getPriceAdults())));
            viewHolder.tvPrice2.setVisibility(View.GONE);
        }
        TimeDate.ResultDateDiff resultDateDiff = TimeDate.dateDiff(listLegs.get(0).getDepartureTime(), listLegs.get(listLegs.size() - 1).getArrivalTime());


        if (resultDateDiff != null) {
            viewHolder.txtFlyTime.setText(context.getString(R.string.flightDuration) + ":" + resultDateDiff.getElapsedHours() + "  ساعت" + " و " + resultDateDiff.getElapsedMinutes() + " دقیقه");
        }
        //viewHolder.txtFromPlace.setText(listLegs.get(0).getDepartureAirport());
        //viewHolder.txtToPlace.setText(listLegs.get(listLegs.size() - 1).getArrivalAirport());
        String stops = finalFlightInternationalIati.getAllFlightInternationalIatiWent().getStops() == 0 ? "بدون" : String.valueOf(finalFlightInternationalIati.getAllFlightInternationalIatiWent().getStops());
        viewHolder.txtStops.setText("(" + stops + " " + context.getString(R.string.stops) + ")");
        //==============Return Flight
        ArrayList<LegsIati> listLegsReturn = finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getLegsList();
        Picasso.with(context)
                .load(BaseConfig.BASE_URL_MASTER +
                        BaseConfig.FOLDER_IMAGE_INTERNATIONAL_URL +
                        finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getAirline() + ".png")
                .into(viewHolder.imgLogoAirLineReturn);
        int waitTimeBeforeNextLegReturn = 0;
        for (LegsIati legsIati : finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getLegsList()) {
            waitTimeBeforeNextLegReturn += legsIati.getWaitTimeBeforeNextLeg();
        }
        TimeDate.ResultDateDiff resultDateDiffWatingReturn = TimeDate.convertToHourMin(waitTimeBeforeNextLegReturn);
        viewHolder.txtTimeStopsReturn.setText(context.getString(R.string.flightStops) + ":" + resultDateDiffWatingReturn.getElapsedHours() + "  ساعت" + " و " + resultDateDiffWatingReturn.getElapsedMinutes() + " دقیقه");
        String departureDateTimeReturn = TimeDate.getTime(null, listLegsReturn.get(0).getDepartureTime());
        String arrivalDateTimeReturn = TimeDate.getTime(null, listLegsReturn.get(listLegsReturn.size() - 1).getArrivalTime());
        viewHolder.txtTimeTakeOffReturn.setText(departureDateTimeReturn);
        viewHolder.txtTimeLandingReturn.setText(arrivalDateTimeReturn);
        String airlineNameReturn = airlineListIatis.get(finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getAirline());
        viewHolder.txtAirLineReturn.setText(airlineNameReturn);
        TimeDate.ResultDateDiff resultDateDiffReturn = TimeDate.dateDiff(listLegsReturn.get(0).getDepartureTime(), listLegsReturn.get(listLegsReturn.size() - 1).getArrivalTime());
        if (resultDateDiffReturn != null) {
            viewHolder.txtFlyTimeReturn.setText(context.getString(R.string.flightDuration) + ":" + resultDateDiffReturn.getElapsedHours() + "  ساعت" + " و " + resultDateDiffReturn.getElapsedMinutes() + " دقیقه");
        }
        //viewHolder.txtFromPlaceReturn.setText(listLegsReturn.get(0).getDepartureAirport());
        //viewHolder.txtToPlaceReturn.setText(listLegsReturn.get(listLegsReturn.size() - 1).getArrivalAirport());
        String stopsReturn = finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getStops() == 0 ? "بدون" : String.valueOf(finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getStops());
        viewHolder.txtStopsReturn.setText("(" + stopsReturn + " " + context.getString(R.string.stops) + ")");
    }

    //-----------------------------------------------
    private String getFinalPrice(String price) {
        String finalPrice = "";
        try {
            finalPrice = price.replace(",", "");
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice));
            finalPrice += " تومان";

            return finalPrice;
        } catch (Exception e) {
            return price + " تومان";
        }

    }

    //-----------------------------------------------
    public class MyViewHolderWent extends RecyclerView.ViewHolder {


        public TextView txtTimeTakeOff, txtTimeLanding;
        //public TextView txtAirLineAndTypeClass;
        public TextView txtAirLine;
        public TextView txtFlyTime;
        public TextView txtStops;
        public TextView txtTimeStops;
        public ImageView imgLogoAirLine;
        public TextView tvPrice, tvPrice2;
        //public TextView txtFromPlace;
        //public TextView txtToPlace;
        public ImageView imgService;

        public MyViewHolderWent(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            tvPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            tvPrice2 = (TextView) itemLayoutView.findViewById(R.id.tvPrice2);
            txtTimeLanding = (TextView) itemLayoutView.findViewById(R.id.txtTimeLanding);
            txtTimeTakeOff = (TextView) itemLayoutView.findViewById(R.id.txtTimeTakeOff);
            txtTimeStops = (TextView) itemLayoutView.findViewById(R.id.txtTimeStops);
            //txtAirLineAndTypeClass = (TextView) itemLayoutView.findViewById(R.id.txtAirLineAndTypeClass);
            txtAirLine = (TextView) itemLayoutView.findViewById(R.id.txtAirLine);
            txtFlyTime = (TextView) itemLayoutView.findViewById(R.id.txtFlyTime);
            imgLogoAirLine = (ImageView) itemLayoutView.findViewById(R.id.imgLogoAirLine);
            //txtFromPlace = (TextView) itemLayoutView.findViewById(R.id.txtFromPlace);
            //txtToPlace = (TextView) itemLayoutView.findViewById(R.id.txtToPlace);
            txtStops = (TextView) itemLayoutView.findViewById(R.id.txtStops);
            imgService = (ImageView) itemLayoutView.findViewById(R.id.imgService);
            UtilFonts.overrideFonts(context, tvPrice, UtilFonts.IRAN_SANS_BOLD);
            UtilFonts.overrideFonts(context, txtTimeLanding, UtilFonts.IRAN_SANS_BOLD);
            UtilFonts.overrideFonts(context, txtTimeTakeOff, UtilFonts.IRAN_SANS_BOLD);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //selectItemFlightInternational.onSelectItemFlight(listItem.get(getLayoutPosition()));
                    selectItemFlightInternational.onSelectItem(listItem.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    //-----------------------------------------------
    public class MyViewHolderWentAndReturn extends RecyclerView.ViewHolder {
        public TextView txtTimeTakeOff, txtTimeLanding, txtTimeTakeOffReturn, txtTimeLandingReturn;
        public TextView txtAirLine, txtAirLineReturn;
        public TextView txtFlyTime, txtFlyTimeReturn;
        public TextView txtStops, txtStopsReturn;
        public TextView txtTimeStops, txtTimeStopsReturn;
        public ImageView imgLogoAirLine, imgLogoAirLineReturn;
        public TextView tvPrice, tvPrice2;
        //public TextView txtFromPlace, txtFromPlaceReturn;
        //public TextView txtToPlace, txtToPlaceReturn;
        public ImageView imgService, imgServiceReturn;


        public MyViewHolderWentAndReturn(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            itemLayoutView.findViewById(R.id.layoutReturn).setVisibility(View.VISIBLE);
            tvPrice = (TextView) itemLayoutView.findViewById(R.id.tvPrice);
            tvPrice2 = (TextView) itemLayoutView.findViewById(R.id.tvPrice2);
            txtTimeLanding = (TextView) itemLayoutView.findViewById(R.id.txtTimeLanding);
            txtTimeTakeOff = (TextView) itemLayoutView.findViewById(R.id.txtTimeTakeOff);
            txtTimeStops = (TextView) itemLayoutView.findViewById(R.id.txtTimeStops);
            txtTimeStopsReturn = (TextView) itemLayoutView.findViewById(R.id.txtTimeStopsReturn);
            txtAirLine = (TextView) itemLayoutView.findViewById(R.id.txtAirLine);
            txtFlyTime = (TextView) itemLayoutView.findViewById(R.id.txtFlyTime);
            imgLogoAirLine = (ImageView) itemLayoutView.findViewById(R.id.imgLogoAirLine);
            //txtFromPlace = (TextView) itemLayoutView.findViewById(R.id.txtFromPlace);
            //txtToPlace = (TextView) itemLayoutView.findViewById(R.id.txtToPlace);
            txtStops = (TextView) itemLayoutView.findViewById(R.id.txtStops);
            imgService = (ImageView) itemLayoutView.findViewById(R.id.imgService);

            txtTimeLandingReturn = (TextView) itemLayoutView.findViewById(R.id.txtTimeLandingReturn);
            txtTimeTakeOffReturn = (TextView) itemLayoutView.findViewById(R.id.txtTimeTakeOffReturn);
            txtAirLineReturn = (TextView) itemLayoutView.findViewById(R.id.txtAirLineReturn);
            txtFlyTimeReturn = (TextView) itemLayoutView.findViewById(R.id.txtFlyTimeReturn);
            imgLogoAirLineReturn = (ImageView) itemLayoutView.findViewById(R.id.imgLogoAirLineReturn);
            //txtFromPlaceReturn = (TextView) itemLayoutView.findViewById(R.id.txtFromPlaceReturn);
            //txtToPlaceReturn = (TextView) itemLayoutView.findViewById(R.id.txtToPlaceReturn);
            txtStopsReturn = (TextView) itemLayoutView.findViewById(R.id.txtStopsReturn);
            imgServiceReturn = (ImageView) itemLayoutView.findViewById(R.id.imgServiceReturn);
            UtilFonts.overrideFonts(context, tvPrice, UtilFonts.IRAN_SANS_BOLD);
            UtilFonts.overrideFonts(context, txtTimeLanding, UtilFonts.IRAN_SANS_BOLD);
            UtilFonts.overrideFonts(context, txtTimeTakeOff, UtilFonts.IRAN_SANS_BOLD);
            UtilFonts.overrideFonts(context, tvPrice, UtilFonts.IRAN_SANS_BOLD);
            UtilFonts.overrideFonts(context, txtTimeLandingReturn, UtilFonts.IRAN_SANS_BOLD);
            UtilFonts.overrideFonts(context, txtTimeTakeOffReturn, UtilFonts.IRAN_SANS_BOLD);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItemFlightInternational.onSelectItem(listItem.get(getLayoutPosition()), getAdapterPosition());
                    //selectItemFlightInternational.onSelectItemFlight(listItem.get(getLayoutPosition()));
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
    public ArrayList<Object> filterAll(ArrayMap<String, List<String>> applied_filters) {
        ArrayList<Object> listItemFiltered = new ArrayList<>();
        try {
            List<String> filterTimeTakeOffWent = applied_filters.get(NewDesignFilterFlightInternationalFragmentDialog.FILTER_CATEGORY_TIME_TAKE_OFF);
            List<String> filterTimeTakeOffReturn = applied_filters.get(NewDesignFilterFlightInternationalFragmentDialog.FILTER_CATEGORY_TIME_TAKE_OFF_RETURN);
            List<String> filterStops = applied_filters.get(NewDesignFilterFlightInternationalFragmentDialog.FILTER_CATEGORY_STOPS);
            List<String> filterAirLine = applied_filters.get(NewDesignFilterFlightInternationalFragmentDialog.FILTER_CATEGORY_AIRLINE);
            List<String> filterSort = applied_filters.get(NewDesignFilterFlightInternationalFragmentDialog.FILTER_CATEGORY_SORT);
            ArrayList<Object> temp = new ArrayList<>();
            listItem.clear();
            listItem.addAll(listItemTemp);
            if (filterTimeTakeOffWent != null) {
                for (Object outBound : listItem) {
                    for (int index = 0; index < filterTimeTakeOffWent.size(); index++) {
                        if (outBound instanceof AllFlightInternationalParto) {
                            AllFlightInternationalParto item = (AllFlightInternationalParto) outBound;
                            String departureDateTime = TimeDate.getTime(null, item.getLegs().get(0).getListLegs().get(0).getDepartureDateTime());
                            String[] time = departureDateTime.split(":");
                            int hour = Integer.valueOf(time[0]);
                            int type = Integer.valueOf(filterTimeTakeOffWent.get(index));
                            if (hasTime(type, hour)) {
                                listItemFiltered.add(outBound);
                            }
                        } else if (outBound instanceof FinalFlightInternationalIati) {
                            FinalFlightInternationalIati item = (FinalFlightInternationalIati) outBound;
                            String departureDateTime = TimeDate.getTime(null, item.getAllFlightInternationalIatiWent().getLegsList().get(0).getDepartureTime());
                            String[] time = departureDateTime.split(":");
                            int hour = Integer.valueOf(time[0]);
                            int type = Integer.valueOf(filterTimeTakeOffWent.get(index));
                            if (hasTime(type, hour)) {
                                listItemFiltered.add(outBound);
                            }
                        } else if (outBound instanceof AllFlightInternationalIati) {
                            AllFlightInternationalIati item = (AllFlightInternationalIati) outBound;
                            String departureDateTime = TimeDate.getTime(null, item.getLegsList().get(0).getDepartureTime());
                            String[] time = departureDateTime.split(":");
                            int hour = Integer.valueOf(time[0]);
                            int type = Integer.valueOf(filterTimeTakeOffWent.get(index));
                            if (hasTime(type, hour)) {
                                listItemFiltered.add(outBound);
                            }
                        }

                    }
                }
            } else {
                listItemFiltered.addAll(listItem);
            }
            //-----------------------------------------------
            if (filterTimeTakeOffReturn != null) {
                for (Object outBound : listItemFiltered) {
                    for (int index = 0; index < filterTimeTakeOffReturn.size(); index++) {
                        if (outBound instanceof AllFlightInternationalParto) {
                            AllFlightInternationalParto item = (AllFlightInternationalParto) outBound;
                            String departureDateTime = TimeDate.getTime(null, item.getLegs().get(1).getListLegs().get(0).getDepartureDateTime());
                            String[] time = departureDateTime.split(":");
                            int hour = Integer.valueOf(time[0]);
                            int type = Integer.valueOf(filterTimeTakeOffReturn.get(index));
                            if (hasTime(type, hour)) {
                                temp.add(outBound);
                            }
                        } else if (outBound instanceof FinalFlightInternationalIati) {
                            FinalFlightInternationalIati item = (FinalFlightInternationalIati) outBound;
                            String departureDateTime = TimeDate.getTime(null, item.getAllFlightInternationalIatiReturn().getLegsList().get(0).getDepartureTime());
                            String[] time = departureDateTime.split(":");
                            int hour = Integer.valueOf(time[0]);
                            int type = Integer.valueOf(filterTimeTakeOffReturn.get(index));
                            if (hasTime(type, hour)) {
                                temp.add(outBound);
                            }
                        }

                    }
                }
                listItemFiltered.clear();
                listItemFiltered.addAll(temp);
                temp.clear();
            }
            //-----------------------------------------------
            if (filterAirLine != null) {
                for (Object outBound : listItemFiltered) {
                    for (int index = 0; index < filterAirLine.size(); index++) {
                        if (outBound instanceof AllFlightInternationalParto) {
                            AllFlightInternationalParto item = (AllFlightInternationalParto) outBound;
                            if (item.getOutboundAirline().contentEquals(filterAirLine.get(index))) {
                                temp.add(outBound);
                            }
                        } else if (outBound instanceof FinalFlightInternationalIati) {
                            FinalFlightInternationalIati item = (FinalFlightInternationalIati) outBound;
                            String airlineName = item.getAllFlightInternationalIatiWent().getLegsList().get(0).getCarrierName();
                            if (airlineName.contentEquals(filterAirLine.get(index))) {
                                temp.add(outBound);
                            }
                        } else if (outBound instanceof AllFlightInternationalIati) {
                            AllFlightInternationalIati item = (AllFlightInternationalIati) outBound;
                            String airlineName = item.getAirline();
                            if (airlineName.contentEquals(filterAirLine.get(index))) {
                                temp.add(outBound);
                            }
                        }
                    }
                }
                listItemFiltered.clear();
                listItemFiltered.addAll(temp);
                temp.clear();
            }
            //-----------------------------------------------
            if (filterStops != null) {
                for (Object outBound : listItemFiltered) {
                    for (int index = 0; index < filterStops.size(); index++) {
                        if (outBound instanceof AllFlightInternationalParto) {
                            AllFlightInternationalParto item = (AllFlightInternationalParto) outBound;
                            if (item.getOutboundStops() == Integer.valueOf(filterStops.get(index))) {
                                temp.add(outBound);
                            }
                        } else if (outBound instanceof FinalFlightInternationalIati) {
                            FinalFlightInternationalIati item = (FinalFlightInternationalIati) outBound;
                            if (item.getAllFlightInternationalIatiWent().getStops() == Integer.valueOf(filterStops.get(index))) {
                                temp.add(outBound);
                            }
                        } else if (outBound instanceof AllFlightInternationalIati) {
                            AllFlightInternationalIati item = (AllFlightInternationalIati) outBound;
                            if (item.getStops() == Integer.valueOf(filterStops.get(index))) {
                                temp.add(outBound);
                            }
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
                        sortByTimeTakeOff();
                    }
                }
            }
        } catch (Exception e) {

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
    public ArrayList<Object> getFullItems() {
        return listItem;
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
    public void sortByTimeTakeOff() {

        Collections.sort(listItem, new Comparator<Object>() {
            @Override
            public int compare(Object item1, Object item2) {
                Long valueTime1 = Long.MIN_VALUE;
                Long valueTime2 = Long.MIN_VALUE;
                if (item1 instanceof AllFlightInternationalParto) {
                    AllFlightInternationalParto item = (AllFlightInternationalParto) item1;
                    String departureDateTime = TimeDate.getTime(null, item.getLegs().get(0).getListLegs().get(0).getDepartureDateTime());
                    valueTime1 = TimeDate.getTime(departureDateTime);

                } else if (item1 instanceof FinalFlightInternationalIati) {
                    FinalFlightInternationalIati item = (FinalFlightInternationalIati) item1;
                    String departureDateTime = TimeDate.getTime(null, item.getAllFlightInternationalIatiWent().getLegsList().get(0).getDepartureTime());
                    valueTime1 = TimeDate.getTime(departureDateTime);
                } else if (item1 instanceof AllFlightInternationalIati) {
                    AllFlightInternationalIati item = (AllFlightInternationalIati) item1;
                    String departureDateTime = TimeDate.getTime(null, item.getLegsList().get(0).getDepartureTime());
                    valueTime1 = TimeDate.getTime(departureDateTime);
                }

                if (item2 instanceof AllFlightInternationalParto) {
                    AllFlightInternationalParto item = (AllFlightInternationalParto) item2;
                    String departureDateTime = TimeDate.getTime(null, item.getLegs().get(0).getListLegs().get(0).getDepartureDateTime());
                    valueTime2 = TimeDate.getTime(departureDateTime);

                } else if (item2 instanceof FinalFlightInternationalIati) {
                    FinalFlightInternationalIati item = (FinalFlightInternationalIati) item2;
                    String departureDateTime = TimeDate.getTime(null, item.getAllFlightInternationalIatiWent().getLegsList().get(0).getDepartureTime());
                    valueTime2 = TimeDate.getTime(departureDateTime);
                } else if (item2 instanceof AllFlightInternationalIati) {
                    AllFlightInternationalIati item = (AllFlightInternationalIati) item2;
                    String departureDateTime = TimeDate.getTime(null, item.getLegsList().get(0).getDepartureTime());
                    valueTime2 = TimeDate.getTime(departureDateTime);
                }
                return valueTime1.compareTo(valueTime2);
            }
        });
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public void sortByMoney() {
        Collections.sort(listItem, new Comparator<Object>() {
            @Override
            public int compare(Object item1, Object item2) {
                Long value1 = Long.MIN_VALUE;
                Long value2 = Long.MIN_VALUE;
                if (item1 instanceof AllFlightInternationalParto) {
                    AllFlightInternationalParto item = (AllFlightInternationalParto) item1;
                    value1 = item.getAdultPrice();
                } else if (item1 instanceof FinalFlightInternationalIati) {
                    FinalFlightInternationalIati item = (FinalFlightInternationalIati) item1;
                    value1 = item.getPriceAdults();
                } else if (item1 instanceof AllFlightInternationalIati) {
                    AllFlightInternationalIati item = (AllFlightInternationalIati) item1;
                    value1 = item.getAdultPrice();
                }

                if (item2 instanceof AllFlightInternationalParto) {
                    AllFlightInternationalParto item = (AllFlightInternationalParto) item2;
                    value2 = item.getAdultPrice();
                } else if (item2 instanceof FinalFlightInternationalIati) {
                    FinalFlightInternationalIati item = (FinalFlightInternationalIati) item2;
                    value2 = item.getPriceAdults();
                } else if (item2 instanceof AllFlightInternationalIati) {
                    AllFlightInternationalIati item = (AllFlightInternationalIati) item2;
                    value2 = item.getAdultPrice();
                }
                return value1.compareTo(value2);
            }
        });
        notifyDataSetChanged();
    }


}