package hami.nasimbehesht724.Activity.ServiceTour.Fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import hami.nasimbehesht724.Activity.ServiceTour.Adapter.TourSlidingImageAdapter;
import hami.nasimbehesht724.Activity.ServiceTour.Controller.Model.TourDetailData;
import hami.nasimbehesht724.Activity.ServiceTour.Controller.TourApi;
import hami.nasimbehesht724.BaseController.NameValueBoolean;
import hami.nasimbehesht724.BaseController.ResultSearchPresenter;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilFragment;
import hami.nasimbehesht724.Util.UtilVibrator;
import hami.nasimbehesht724.View.MessageBar;
import hami.nasimbehesht724.View.TextToggleView;
import hami.nasimbehesht724.View.ToastMessageBar;
import hami.nasimbehesht724.View.ToolsTourOption;
import io.supercharge.shimmerlayout.ShimmerLayout;


public class FragmentTourDetail extends Fragment {


    private View view;
    private MessageBar messageBar;
    private TourApi tourApi;
    private static ViewPager mPager;
    private TourDetailData tourDetailData;
    private static final String TAG = "FragmentTourDetail";
    private String tourId, tourName;
    private AppCompatButton btnRegister;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            tourId = getArguments().getString("tourId");
            tourName = getArguments().getString("tourName");
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            tourId = savedInstanceState.getString("tourId");
            tourName = savedInstanceState.getString("tourName");
            tourDetailData = savedInstanceState.getParcelable(TourDetailData.class.getName());
        }
    }

    //-----------------------------------------------
    public static FragmentTourDetail newInstance(String tourId, String tourName) {

        Bundle args = new Bundle();
        FragmentTourDetail fragment = new FragmentTourDetail();
        args.putString("tourId", tourId);
        args.putString("tourName", tourName);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putString("tourId", tourId);
            outState.putString("tourName", tourName);
            outState.putParcelable(TourDetailData.class.getName(), tourDetailData);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_tour_detail_layout, container, false);
            initialComponentFragment();
        }
        return view;
    }



    //-----------------------------------------------
    private void initialComponentFragment() {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        btnRegister = (AppCompatButton) view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(onClickListener);
        tourApi = new TourApi(getActivity());
        getTourDetails();
    }



    //-----------------------------------------------
    private View.OnClickListener callbackMessageBaClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getTourDetails();
        }
    };

    //-----------------------------------------------
    public void getTourDetails() {
        tourApi.getDetailTour(tourId, searchPresenterTour);
    }

    //-----------------------------------------------
    private ResultSearchPresenter<TourDetailData> searchPresenterTour = new ResultSearchPresenter<TourDetailData>() {
        @Override
        public void onStart() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showProgress(getString(R.string.gettingInfo));
                        messageBar.hideMessageBar();
                    }
                });
            }
        }


        @Override
        public void onErrorServer(final int type) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorServer);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        messageBar.setTitleButton(R.string.tryAgain);
                    }
                });
            }
        }

        @Override
        public void onErrorInternetConnection() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        messageBar.setTitleButton(R.string.tryAgain);
                    }
                });
            }
        }

        @Override
        public void onSuccessResultSearch(final TourDetailData result) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tourDetailData = result;
                        messageBar.hideMessageBar();
                        showTour();
                    }
                });
            }
        }

        @Override
        public void noResult(final int type) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorNoTour);
                        messageBar.setTitleButton(R.string.tryAgainSearch);
                        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
                        //setupFilterFab(false);

                    }
                });
            }
        }

        @Override
        public void onError(final String msg) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(msg);
                    }
                });
            }
        }

        @Override
        public void onFinish() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.hideProgress();
                        new UtilVibrator(getActivity()).vibrateBySound();
                    }
                });
            }
        }


    };

    //-----------------------------------------------
    private void showTour() {
        setupTourDetails();
        setupOtherDetailTour();
        setupPriceView();
        setupImageGallery();
    }

    //-----------------------------------------------
    private void setupTourDetails() {
        try {
            TextView txtTourName = (TextView) view.findViewById(R.id.txtTourName);
            TextView txtWentDate = (TextView) view.findViewById(R.id.txtWentDate);
            TextView txtWentTime = (TextView) view.findViewById(R.id.txtWentTime);
            TextView txtWentPlace = (TextView) view.findViewById(R.id.txtWentPlace);
            TextView txtTourCountDayAndNight = (TextView) view.findViewById(R.id.txtTourCountDayAndNight);

            TextView txtReturnDate = (TextView) view.findViewById(R.id.txtReturnDate);
            TextView txtReturnTime = (TextView) view.findViewById(R.id.txtReturnTime);
            TextView txtReturnPlace = (TextView) view.findViewById(R.id.txtReturnPlace);

            TextView txtHotelName = (TextView) view.findViewById(R.id.txtHotelName);
            TextView txtHotelRate = (TextView) view.findViewById(R.id.txtHotelRate);
            TextView txtHotelDetail1 = (TextView) view.findViewById(R.id.txtHotelDetail1);
            TextView txtHotelWaterRecreation = (TextView) view.findViewById(R.id.txtHotelWaterRecreation);
            txtHotelName.setSelected(true);
            txtTourName.setText(tourDetailData.getName());
            String dateCounter = tourDetailData.getDay_count() + getString(R.string.day) + " و " + tourDetailData.getNight_count() + getString(R.string.night);
            txtTourCountDayAndNight.setText(dateCounter);
            txtWentDate.setText(getString(R.string.moveDate) + " " + getPersianDate(tourDetailData.getStart_date()));
            txtWentTime.setText(getString(R.string.timeTakeOff) + " " + tourDetailData.getStart_time());
            txtWentPlace.setText(getString(R.string.from) + " " + tourDetailData.getTfrom() + " " + getString(R.string.to) + " " + tourDetailData.getTto());

            txtReturnDate.setText(getString(R.string.date) + " " + getPersianDate(tourDetailData.getEnd_date()));
            txtReturnTime.setText(getString(R.string.timeTakeOff) + " " + tourDetailData.getEnd_time());
            txtReturnPlace.setText(getString(R.string.from) + " " + tourDetailData.getTto() + " " + getString(R.string.to) + " " + tourDetailData.getTfrom());

            txtHotelName.setText(tourDetailData.getDescription());
            txtHotelRate.setText(tourDetailData.getStay_in());
            txtHotelDetail1.setText(tourDetailData.getMeals());
            if (tourDetailData.getWater().contentEquals("1")) {
                txtHotelWaterRecreation.setVisibility(View.VISIBLE);
                txtHotelWaterRecreation.setText(getString(R.string.hotelWaterRecreation));
            } else
                txtHotelWaterRecreation.setVisibility(View.GONE);

        } catch (Exception e) {

            ToastMessageBar.show(getActivity(), R.string.msgErrorPayment);
            getActivity().onBackPressed();
        }

    }

    //-----------------------------------------------
    private void setupOtherDetailTour() {
        try {
            LinearLayout layoutTourDetail = (LinearLayout) view.findViewById(R.id.layoutTourDetail);
            ArrayList<NameValueBoolean> nameValues = new ArrayList<>();

            nameValues.add(new NameValueBoolean(getString(R.string.visa), tourDetailData.getVisa().contentEquals("1")));
            nameValues.add(new NameValueBoolean(getString(R.string.simCard), tourDetailData.getSimcard().contentEquals("1")));
            nameValues.add(new NameValueBoolean(getString(R.string.insurance), tourDetailData.getInsurance().contentEquals("1")));
            nameValues.add(new NameValueBoolean(getString(R.string.leaderTranslate), tourDetailData.getLeader().contentEquals("1")));
            nameValues.add(new NameValueBoolean(getString(R.string.crossCityTransfer), tourDetailData.getCross_city_transfer().contentEquals("1")));
            nameValues.add(new NameValueBoolean(getString(R.string.welcomeHoney), true));
            for (NameValueBoolean item : nameValues) {
                TextToggleView textToggleView = new TextToggleView(getActivity());
                textToggleView.setConfig(item.getName(), item.getValue());
                layoutTourDetail.addView(textToggleView);
            }
        } catch (Exception e) {

        }

    }

    //-----------------------------------------------
    private void setupPriceView() {
        try {
            ToolsTourOption toolsTourOption = (ToolsTourOption) view.findViewById(R.id.toolsTourOption);
            String adultPrice = getFinalPrice(tourDetailData.getPrice_adl());
            String childPrice = getFinalPrice(tourDetailData.getPrice_chd());
            String infantPrice = getFinalPrice(tourDetailData.getPrice_inf());
            String singlePrice = getFinalPrice(tourDetailData.getPrice_single());
            toolsTourOption.setPassengerPrice(adultPrice, childPrice, infantPrice, singlePrice);
        } catch (Exception e) {

        }

    }

    //-----------------------------------------------
    private String getPersianDate(String longDate) {
        try {
            PersianCalendar persianCalendar = new PersianCalendar();
            persianCalendar.setTimeInMillis(Long.parseLong(longDate) * 1000L);
            persianCalendar.addPersianDate(Calendar.DAY_OF_MONTH, 1);
            String weekName = persianCalendar.getPersianWeekDayName();
            String monthName = persianCalendar.getPersianMonthName();
            int dayOfMonth = persianCalendar.getPersianDay();
            int year = persianCalendar.getPersianYear();
            String datePersian = weekName + "," + dayOfMonth + monthName + "," + year;
            return datePersian;
        } catch (Exception e) {
            return "";
        }
    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnRegister) {
                UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentTourAddPassenger.newInstance(tourDetailData));
            }

        }
    };

    //-----------------------------------------------
    private void setupImageGallery() {
        final TextView txtImageCounter = (TextView) view.findViewById(R.id.txtImageCounter);
        final ArrayList<String> imagesList = new ArrayList<>();
        String[] imageArray = tourDetailData.getImg().split(",");
        imagesList.addAll(Arrays.asList(imageArray));
        if (imagesList.get(imagesList.size() - 1).length() == 0) {
            imagesList.remove(imagesList.size() - 1);
        }
        txtImageCounter.setText("1/" + imagesList.size());
        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(new TourSlidingImageAdapter(getActivity(), imagesList));
        final float density = getResources().getDisplayMetrics().density;
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                txtImageCounter.setText((position + 1) + "/" + imagesList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
    View.OnClickListener callbackRetryMessageBarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            getTourDetails();
        }
    };




}
