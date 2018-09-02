package com.hami.servicehotel.Domestic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hami.common.Util.UtilFonts;
import com.hami.common.Util.UtilImageLoader;
import com.hami.common.View.ReadMoreTextView;
import com.hami.servicehotel.Domestic.Controller.Model.DomesticHotelBookingProcessData;
import com.hami.servicehotel.Domestic.Controller.Model.DomesticHotelDetailsResponse;
import com.hami.servicehotel.Domestic.Controller.Model.DomesticHotelMoreInfo;
import com.hami.servicehotel.R;

import java.text.NumberFormat;
import java.util.Locale;

public class FragmentFullDetailDomesticHotel extends Fragment {


    private RelativeLayout coordinator;
    private View view;
    private static ViewPager mPager;
    private DomesticHotelDetailsResponse domesticHotelDetailsResponse;
    private static final String TAG = "FragmentDetailInternationalHotel";
    private ImageView imgMapStatic;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            domesticHotelDetailsResponse = getArguments().getParcelable(DomesticHotelDetailsResponse.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            domesticHotelDetailsResponse = savedInstanceState.getParcelable(DomesticHotelDetailsResponse.class.getName());
        }
    }

    //-----------------------------------------------
    public static FragmentFullDetailDomesticHotel newInstance(DomesticHotelDetailsResponse hotelDetailResponse) {

        Bundle args = new Bundle();
        FragmentFullDetailDomesticHotel fragment = new FragmentFullDetailDomesticHotel();
        args.putParcelable(DomesticHotelDetailsResponse.class.getName(), hotelDetailResponse);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(DomesticHotelDetailsResponse.class.getName(), domesticHotelDetailsResponse);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_fulll_detail_hote_domesticl_layout, container, false);
            initialComponentFragment();
        }
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment() {
        setupMapStatic();
        setupHotelDetails();
    }

    //-----------------------------------------------
    private void setupMapStatic() {
        imgMapStatic = (ImageView) view.findViewById(R.id.imgMapStatic);
        String lat = String.valueOf(domesticHotelDetailsResponse.getDomesticHotelBookingProcess().getBookingProcess().getHotelLatitude());
        String lng = String.valueOf(domesticHotelDetailsResponse.getDomesticHotelBookingProcess().getBookingProcess().getHotelLongitude());
        String url = "https://maps.googleapis.com/maps/api/staticmap?center=" + lat + "," + lng + "&zoom=14&size=400x200&key=" + getString(R.string.google_play_id);
        UtilImageLoader.loadImage(getActivity(), imgMapStatic, url, R.drawable.no_image_hotel);
    }

    //-----------------------------------------------
    private void setupHotelDetails() {
        try {
            RatingBar rbRating = (RatingBar) view.findViewById(R.id.rbRating);
            TextView txtAddress = (TextView) view.findViewById(R.id.txtAddress);
            TextView txtHotelName = (TextView) view.findViewById(R.id.txtHotelName);
            ReadMoreTextView txtHotelDescription = (ReadMoreTextView) view.findViewById(R.id.txtHotelDescription);

            LinearLayout layoutHotelFacilities = (LinearLayout) view.findViewById(R.id.layoutHotelFacilities);

            UtilFonts.overrideFonts(getActivity(), txtHotelName, UtilFonts.IRAN_SANS_BOLD);
            UtilFonts.overrideFonts(getActivity(), txtAddress, UtilFonts.IRAN_SANS_NORMAL);
            UtilFonts.overrideFonts(getActivity(), txtHotelDescription, UtilFonts.IRAN_SANS_NORMAL);


            final DomesticHotelBookingProcessData hotelInfo = domesticHotelDetailsResponse.getDomesticHotelBookingProcess().getBookingProcess();
            Integer rating = hotelInfo.getHotelStar();
            if (rating == 0) {
                rbRating.setVisibility(View.GONE);
            } else {
                rbRating.setVisibility(View.VISIBLE);
                rbRating.setNumStars(rating);
                rbRating.setRating(rating);
            }
            txtAddress.setText(hotelInfo.getHotelAddress());
            txtHotelName.setText(hotelInfo.getHotelNameFa());
            txtHotelDescription.setText(hotelInfo.getHotelDescription());
            if (hotelInfo.getOptions() != null) {
                if (hotelInfo.getOptions().getHotelOptions() != null && hotelInfo.getOptions().getHotelOptions().size() > 0) {
                    layoutHotelFacilities.addView(getTextViewTitle(R.string.featuresHotel));
                    for (DomesticHotelMoreInfo value : hotelInfo.getOptions().getHotelOptions()) {
                        layoutHotelFacilities.addView(getTextViewValue(" ■ " + value.getName()));
                    }
                }

                if (hotelInfo.getOptions().getRoomOptions() != null && hotelInfo.getOptions().getRoomOptions().size() > 0) {
                    layoutHotelFacilities.addView(getLineView());
                    layoutHotelFacilities.addView(getTextViewTitle(R.string.facilitiesRoom));
                    for (DomesticHotelMoreInfo value : hotelInfo.getOptions().getRoomOptions()) {
                        layoutHotelFacilities.addView(getTextViewValue(" ■ " + value.getName()));
                    }
                }

            }
            if (hotelInfo.getRules() != null && hotelInfo.getRules().size() > 0) {
                layoutHotelFacilities.addView(getLineView());
                layoutHotelFacilities.addView(getTextViewTitle(R.string.rulesHotel));
                for (DomesticHotelMoreInfo value : hotelInfo.getRules()) {
                    layoutHotelFacilities.addView(getTextViewValue(" ■ " + value.getName()));
                }
            }
        } catch (Exception e) {

        }

    }

    //-----------------------------------------------
    private TextView getTextViewTitle(int title) {
        TextView textViewTitle = new TextView(getActivity());
        UtilFonts.overrideFonts(getActivity(), textViewTitle, UtilFonts.IRAN_SANS_BOLD);
        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewTitle.setLayoutParams(textParam);
        textViewTitle.setPadding(5, 5, 5, 5);
//        if (Build.VERSION.SDK_INT < 23) {
//            textViewTitle.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
//        } else {
//            textViewTitle.setTextAppearance(android.R.style.TextAppearance_Medium);
//        }
        textViewTitle.setTextSize(16);
        textViewTitle.setTextColor(Color.BLACK);
        textViewTitle.setText(title);
        return textViewTitle;
    }

    //-----------------------------------------------

    private TextView getTextViewValue(String value) {
        TextView textViewTitle = new TextView(getActivity());
        UtilFonts.overrideFonts(getActivity(), textViewTitle, UtilFonts.IRAN_SANS_NORMAL);
        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textParam.gravity = Gravity.RIGHT;
        textParam.setMargins(10, 5, 10, 0);
        textViewTitle.setLayoutParams(textParam);
        textViewTitle.setTextColor(Color.BLACK);
        textViewTitle.setPadding(5, 5, 5, 5);
        textViewTitle.setText(value);
        return textViewTitle;
    }

    //-----------------------------------------------
    private View getLineView() {
        View viewLine = new View(getActivity());
        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        textParam.setMargins(0, 10, 0, 10);
        viewLine.setLayoutParams(textParam);
        viewLine.setBackgroundResource(R.drawable.gradient_line_blue);
        return viewLine;
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
}
