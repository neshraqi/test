package hami.nasimbehesht724.Activity.ServiceTour.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentPresenter;
import hami.nasimbehesht724.Activity.ServiceTour.Adapter.FinalPassengerTourListAdapter;
import hami.nasimbehesht724.Activity.ServiceTour.Controller.Model.BookingTourDetails;
import hami.nasimbehesht724.Activity.ServiceTour.Controller.TourApi;
import hami.nasimbehesht724.BaseNetwork.BaseConfig;
import hami.nasimbehesht724.Const.TourRules;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.CustomeChrome.CustomTabsPackages;
import hami.nasimbehesht724.Util.Hashing;
import hami.nasimbehesht724.Util.UtilFonts;


public class FragmentTourFinalBooking extends Fragment {


    private View view;
    private TourApi tourApi;
    private BookingTourDetails bookingTourDetails;
    private static final String TAG = "FragmentTourFinalBooking";
    private AppCompatButton btnRegister;
    private FinalPassengerTourListAdapter passengerTourListAdapter;
    private RecyclerView rvResult;
    private Boolean hasReserve = false, hasPayment = false;
    private LinearLayout layoutButtonPayment, layoutButtonGetTicket;
    private TextView txtTitleFinalTicket, txtWarningCheckInfo;
    private AppCompatButton btnGetTicket, btnBuy, btnEdit, btnExit;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            bookingTourDetails = getArguments().getParcelable(BookingTourDetails.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            bookingTourDetails = savedInstanceState.getParcelable(BookingTourDetails.class.getName());
            hasReserve = savedInstanceState.getBoolean("hasReserve");
            hasPayment = savedInstanceState.getBoolean("hasPayment");
        }
    }

    //-----------------------------------------------
    public static FragmentTourFinalBooking newInstance(BookingTourDetails bookingTourDetails) {

        Bundle args = new Bundle();
        FragmentTourFinalBooking fragment = new FragmentTourFinalBooking();
        args.putParcelable(BookingTourDetails.class.getName(), bookingTourDetails);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(BookingTourDetails.class.getName(), bookingTourDetails);
            outState.putBoolean("hasReserve", hasReserve);
            outState.putBoolean("hasPayment", hasPayment);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_tour_final_booking_layout, container, false);
            initialComponentFragment();
        }
        return view;
    }
    //-----------------------------------------------

    @Override
    public void onPause() {
        super.onPause();
        setupHeaderToolbar();
    }

    //-----------------------------------------------
    @Override
    public void onResume() {
        super.onResume();
        setupHeaderToolbar();
        if (hasReserve) {
            tourApi.hasBuyTicket(bookingTourDetails.getTicket().getId(), bookingTourDetails.getHashId(), new PaymentPresenter() {
                @Override
                public void onStart() {

                }

                @Override
                public void onErrorServer() {

                }

                @Override
                public void onErrorInternetConnection() {

                }

                @Override
                public void onSuccessBuy() {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hasPayment = true;
                                txtWarningCheckInfo.setVisibility(View.GONE);
                                setupGetTicket();
                            }
                        });
                    }
                }

                @Override
                public void onErrorBuy() {

                }

                @Override
                public void onReTryGetPayment() {

                }

                @Override
                public void onReTryGetTicket() {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setupPayment();
                            }
                        });
                    }
                }

                @Override
                public void onError(String msg) {

                }

                @Override
                public void onFinish() {

                }
            });
        }
    }


    //-----------------------------------------------
    private void initialComponentFragment() {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        tourApi = new TourApi(getActivity());
        layoutButtonGetTicket = (LinearLayout) view.findViewById(R.id.layoutButtonGetTicket);
        layoutButtonPayment = (LinearLayout) view.findViewById(R.id.layoutButtonPayment);
        txtTitleFinalTicket = (TextView) view.findViewById(R.id.titleFinalTicket);
      //  txtWarningCheckInfo = (TextView) view.findViewById(R.id.txtWarningCheckInfo);
        txtWarningCheckInfo.setSelected(true);
        txtWarningCheckInfo.setVisibility(View.VISIBLE);
        btnBuy = (AppCompatButton) view.findViewById(R.id.btnBuy);
        btnEdit = (AppCompatButton) view.findViewById(R.id.btnEditBuy);

        btnGetTicket = (AppCompatButton) view.findViewById(R.id.btnGetTicket);
        btnExit = (AppCompatButton) view.findViewById(R.id.btnExit);
        btnGetTicket.setOnClickListener(onClickListener);
        btnBuy.setOnClickListener(onClickListener);
        btnExit.setOnClickListener(onClickListener);
        btnEdit.setOnClickListener(onClickListener);
        setupTourDetails();
    }

    //-----------------------------------------------
    private void setupRecyclerView() {
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        Boolean hasInternationalTour = bookingTourDetails.getTicket().getTour_kind().contentEquals(TourRules.TOUR_INTERNATIONAL);
        passengerTourListAdapter = new FinalPassengerTourListAdapter(getActivity(), bookingTourDetails.getPassengers(), hasInternationalTour);
        rvResult.setAdapter(passengerTourListAdapter);
    }


    //-----------------------------------------------
    private void setupHeaderToolbar() {
        TextView txtSubTitleMenu = (TextView) getActivity().findViewById(R.id.txtSubTitleMenu);
        txtSubTitleMenu.setText("تایید نهایی و پرداخت");
    }

    //-----------------------------------------------
    private void setupTourDetails() {
        try {
            TextView txtTourName = (TextView) view.findViewById(R.id.txtTourName);
            TextView txtTourCountDayAndNight = (TextView) view.findViewById(R.id.txtTourCountDayAndNight);
            TextView txtFinalPrice = (TextView) view.findViewById(R.id.txtFinalPrice);
            TextView txtWentDate = (TextView) view.findViewById(R.id.txtWentDate);
            TextView txtWentPlace = (TextView) view.findViewById(R.id.txtWentPlace);
            //ImageView imgGoByFrom = (ImageView) view.findViewById(R.id.imgGoByFrom);
            //ImageView imgReturnByTo = (ImageView) view.findViewById(R.id.imgReturnByTo);
            TextView txtReturnDate = (TextView) view.findViewById(R.id.txtReturnDate);
            TextView txtReturnPlace = (TextView) view.findViewById(R.id.txtReturnPlace);

           // imgGoByFrom.setImageResource(getIconTour(bookingTourDetails.getTicket().getTour_go_by()));
           // imgReturnByTo.setImageResource(getIconTour(bookingTourDetails.getTicket().getTour_return_by()));

            txtTourName.setText(bookingTourDetails.getTicket().getTour_name());
            String dateCounter = "";
            if (bookingTourDetails.getTicket().getTour_day_count() == null || bookingTourDetails.getTicket().getTour_day_count().length() == 0 || bookingTourDetails.getTicket().getTour_night_count() == null || bookingTourDetails.getTicket().getTour_night_count().length() == 0)
                dateCounter = "---";
            else
                dateCounter = bookingTourDetails.getTicket().getTour_day_count() + getString(R.string.day) + " و " + bookingTourDetails.getTicket().getTour_night_count() + getString(R.string.night);
            txtTourCountDayAndNight.setText(dateCounter);
            txtWentDate.setText(getString(R.string.moveDate) + " " + getPersianDate(bookingTourDetails.getTicket().getTour_start_date()));
            txtReturnDate.setText(getString(R.string.date) + " " + getPersianDate(bookingTourDetails.getTicket().getTour_end_date()));
            txtWentPlace.setText(bookingTourDetails.getTicket().getTour_from() + " به " + bookingTourDetails.getTicket().getTour_to());
            txtReturnPlace.setText(bookingTourDetails.getTicket().getTour_to() + " به " + bookingTourDetails.getTicket().getTour_from());
            txtFinalPrice.setText(getFinalPrice(bookingTourDetails.getTicket().getFinalprice()));
            setupRecyclerView();
        } catch (Exception e) {


        }

    }

    //-----------------------------------------------
    private int getIconTour(String type) {
        if (type.contentEquals(TourRules.TOUR_BUS)) {
            return R.mipmap.ic_bus_side_view;
        } else if (type.contentEquals(TourRules.TOUR_AIRPLANE)) {
            return R.mipmap.ic_airplan_top;
        } else if (type.contentEquals(TourRules.TOUR_TRAIN)) {
            return R.mipmap.ic_train_gray;
        } else {
            return -1;
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
            switch (v.getId()) {
                case R.id.btnEditBuy:
                    getActivity().onBackPressed();
                    break;
                case R.id.btnBuy:
                    showPayment();
                    break;
                case R.id.btnGetTicket:
                    getTicket();
                    break;
                case R.id.btnExit:
                    getActivity().finish();
                    break;
            }

        }
    };

    //-----------------------------------------------
    private String getFinalPrice(String price) {
        String finalPrice = "";
        try {
            finalPrice = price.replace(",", "");
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice) / 10);
            finalPrice = getString(R.string.finalPriceWithDiscount) + finalPrice + " تومان";
            return finalPrice;
        } catch (Exception e) {


            finalPrice = getString(R.string.finalPriceWithDiscount) + price;
            return finalPrice + " ریال";
        }

    }

    //-----------------------------------------------
    private void setupPayment() {
        layoutButtonPayment.setVisibility(View.VISIBLE);
        layoutButtonGetTicket.setVisibility(View.GONE);
        txtTitleFinalTicket.setVisibility(View.GONE);
    }

    //-----------------------------------------------
    private void setupGetTicket() {
        layoutButtonPayment.setVisibility(View.GONE);
        layoutButtonGetTicket.setVisibility(View.VISIBLE);
        txtTitleFinalTicket.setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
    }

    //-----------------------------------------------
    public void showPayment() {
        hasReserve = true;
        String ticketId = bookingTourDetails.getTicket().getId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.BANK_TOUR + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);
    }

    //-----------------------------------------------
    public void getTicket() {
        String ticketId = bookingTourDetails.getTicket().getId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.BASE_URL_MASTER + "toor/pdfticket/" + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);
    }

    //-----------------------------------------------
    public Boolean hasBuyTicket() {
        return hasPayment;
    }

}
