package hami.mainapp.Activity.ServiceHotel.International;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hami.mainapp.Activity.ServiceHotel.International.Controller.Model.HotelDetailResponse;
import hami.mainapp.Activity.ServiceHotel.International.Controller.Model.HotelInfo;
import hami.mainapp.Activity.ServiceHotel.International.Controller.Model.HotelMoreInfoFacility;
import hami.mainapp.R;
import hami.mainapp.Util.UtilFonts;
import hami.mainapp.Util.UtilImageLoader;
import hami.mainapp.Util.UtilMap;
import hami.mainapp.View.ReadMoreTextView;


public class FragmentFullDetailInternationalHotel extends Fragment {


    private RelativeLayout coordinator;
    private View view;
    private static ViewPager mPager;
    private HotelDetailResponse hotelDetailResponse;
    private static final String TAG = "FragmentDetailInternationalHotel";
    private ImageView imgMapStatic;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            hotelDetailResponse = getArguments().getParcelable(HotelDetailResponse.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            hotelDetailResponse = savedInstanceState.getParcelable(HotelDetailResponse.class.getName());
        }
    }

    //-----------------------------------------------
    public static FragmentFullDetailInternationalHotel newInstance(HotelDetailResponse hotelDetailResponse) {

        Bundle args = new Bundle();
        FragmentFullDetailInternationalHotel fragment = new FragmentFullDetailInternationalHotel();
        args.putParcelable(HotelDetailResponse.class.getName(), hotelDetailResponse);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(HotelDetailResponse.class.getName(), hotelDetailResponse);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_fulll_detail_hote_international_layout, container, false);
            initialComponentFragment();
        }
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment() {
        UtilFonts.overrideFonts(getActivity(), coordinator, UtilFonts.TAHOMA);
        setupMapStatic();
        setupHotelDetails();
    }

    //-----------------------------------------------
    private void setupMapStatic() {
        imgMapStatic = (ImageView) view.findViewById(R.id.imgMapStatic);
        final String lat = hotelDetailResponse.getHotelDetailData().getHotels().getHotelInfo().getLat();
        final String lng = hotelDetailResponse.getHotelDetailData().getHotels().getHotelInfo().getLong();
        String url = "https://maps.googleapis.com/maps/api/staticmap?center=" + lat + "," + lng + "&zoom=14&size=400x200&key=" + getString(R.string.google_play_id);
        UtilImageLoader.loadImage(getActivity(), imgMapStatic, url, R.drawable.no_image_hotel);
        imgMapStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hotelName = hotelDetailResponse.getHotelDetailData().getHotels().getHotelInfo().getHotelName();
                new UtilMap(getActivity()).showGoogleMap(lat, lng, hotelName);
            }
        });
    }

    //-----------------------------------------------
    private void setupHotelDetails() {
        try {
            ReadMoreTextView txtHotelDescription = (ReadMoreTextView) view.findViewById(R.id.txtHotelDescription);
            RatingBar rbRating = (RatingBar) view.findViewById(R.id.rbRating);
            TextView txtAddress = (TextView) view.findViewById(R.id.txtAddress);
            TextView txtHotelName = (TextView) view.findViewById(R.id.txtHotelName);
            AppCompatButton btnFullFeatures = (AppCompatButton) view.findViewById(R.id.btnFullFeaturesEng);
            LinearLayout layoutHotelFacilities = (LinearLayout) view.findViewById(R.id.layoutHotelFacilities);
            final HotelInfo hotelInfo = hotelDetailResponse.getHotelDetailData().getHotels().getHotelInfo();
            txtHotelDescription.setText(hotelDetailResponse.getHotelDetailData().getHotels().getHotelsMoreInfoData().getDescription());
            Integer rating = Integer.valueOf(hotelInfo.getStar());
            if (rating == 0) {
                rbRating.setVisibility(View.GONE);
            } else {
                rbRating.setVisibility(View.VISIBLE);
                rbRating.setNumStars(rating);
                rbRating.setRating(rating);
            }
            txtAddress.setText(hotelInfo.getAddress());
            txtHotelName.setText(hotelInfo.getHotelName());
            ArrayList<HotelMoreInfoFacility> hotelMoreInfoFacilities = hotelDetailResponse.getHotelDetailData().getHotels().getHotelsMoreInfoData().getHotelMoreInfoFacilities().getHotelMoreInfoFacilities();
            if (hotelMoreInfoFacilities != null) {
                view.findViewById(R.id.cardViewHotelFacilities).setVisibility(View.VISIBLE);
                for (int index = 0; index < hotelMoreInfoFacilities.size(); index++) {
                    HotelMoreInfoFacility hotelMoreInfoFacility = hotelMoreInfoFacilities.get(index);
                    layoutHotelFacilities.addView(getTextViewTitle(hotelMoreInfoFacility.getType()));
                    for (String value : hotelMoreInfoFacility.getHotelMoreInfoFacilityRecords().getRecordList()) {
                        layoutHotelFacilities.addView(getTextViewValue(value));
                    }
                }
            } else
                view.findViewById(R.id.cardViewHotelFacilities).setVisibility(View.GONE);


        } catch (Exception e) {

        }

    }

    //-----------------------------------------------
    private TextView getTextViewTitle(String title) {
        TextView textViewTitle = new TextView(getActivity());
        textViewTitle.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewTitle.setLayoutParams(textParam);
        textViewTitle.setPadding(5, 5, 5, 5);
        if (Build.VERSION.SDK_INT < 23) {
            textViewTitle.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
        } else {
            textViewTitle.setTextAppearance(android.R.style.TextAppearance_Medium);
        }
        textViewTitle.setTextColor(Color.WHITE);
        textViewTitle.setText(title);
        return textViewTitle;
    }

    //-----------------------------------------------
    private TextView getTextViewValue(String value) {
        TextView textViewTitle = new TextView(getActivity());
        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textParam.setMargins(10, 5, 10, 0);
        textViewTitle.setLayoutParams(textParam);
        textViewTitle.setTextColor(Color.BLACK);
        textViewTitle.setPadding(5, 5, 5, 5);
        textViewTitle.setText(" ■ " + value);
        return textViewTitle;
    }

}
