package com.hami.servicetour.Adapter;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hami.common.Util.UtilFonts;
import com.hami.common.View.ToolsTourOption;
import com.hami.servicetour.R;

import java.text.NumberFormat;
import java.util.Locale;


public class BottomSheetLayoutPriceTour extends BottomSheetDialogFragment {

    private String singlePrice, adultPrice, childPrice, infantPrice;
    private static final String TAG = "BottomSheetLayoutPriceTour";

    //-----------------------------------------------
    public static BottomSheetLayoutPriceTour newInstance() {
        Bundle args = new Bundle();
        BottomSheetLayoutPriceTour fragment = new BottomSheetLayoutPriceTour();
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tour_price_detail, container, false);
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        setupPriceView(view);
        return view;
    }

    //-----------------------------------------------
    public void setConfigPrice(String singlePrice, String adultPrice, String childPrice, String infantPrice) {
        this.singlePrice = singlePrice;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.infantPrice = infantPrice;
    }

    //-----------------------------------------------
    private void setupPriceView(View view) {
        try {
            ToolsTourOption toolsTourOption = (ToolsTourOption) view.findViewById(R.id.toolsTourOption);
            String adultPrice = getFinalPrice(this.adultPrice);
            String childPrice = getFinalPrice(this.childPrice);
            String infantPrice = getFinalPrice(this.infantPrice);
            String singlePrice = getFinalPrice(this.singlePrice);
            toolsTourOption.setPassengerPrice(adultPrice, childPrice, infantPrice, singlePrice);
        } catch (Exception e) {

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
}
