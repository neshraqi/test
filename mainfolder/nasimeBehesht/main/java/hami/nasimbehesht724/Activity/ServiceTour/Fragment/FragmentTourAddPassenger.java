package hami.nasimbehesht724.Activity.ServiceTour.Fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import hami.nasimbehesht724.Activity.ServiceTour.Adapter.BottomSheetLayoutPriceTour;
import hami.nasimbehesht724.Activity.ServiceTour.Adapter.OnSelectItemPassengerTourListener;
import hami.nasimbehesht724.Activity.ServiceTour.Adapter.PassengerTourListAdapter;
import hami.nasimbehesht724.Activity.ServiceTour.Controller.Model.BookingTourDetails;
import hami.nasimbehesht724.Activity.ServiceTour.Controller.Model.BookingTourRequest;
import hami.nasimbehesht724.Activity.ServiceTour.Controller.Model.BookingTourUserData;
import hami.nasimbehesht724.Activity.ServiceTour.Controller.Model.PassengerTour;
import hami.nasimbehesht724.Activity.ServiceTour.Controller.Model.TourDetailData;
import hami.nasimbehesht724.Activity.ServiceTour.Controller.TourApi;
import hami.nasimbehesht724.BaseController.ResultSearchPresenter;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.Database.DataSaver;
import hami.nasimbehesht724.Util.Keyboard;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilFragment;
import hami.nasimbehesht724.View.CheckBox;
import hami.nasimbehesht724.View.Progressbar.ButtonWithProgress;
import hami.nasimbehesht724.View.ToastMessageBar;
import hami.nasimbehesht724.View.ToolsTourOption;
import hami.nasimbehesht724.View.Validation.ValidationClass;


public class FragmentTourAddPassenger extends Fragment {


    private View view;
    private TourApi tourApi;
    private TourDetailData tourDetailData;
    private static final String TAG = "FragmentTourAddPassenger";
    private AppCompatButton btnRegister;
    private TextView tvCountPassenger;
    private Button btnAddPassenger;
    private PassengerTourListAdapter passengerTourListAdapter;
    private RecyclerView rvResult;
    private LinearLayout layoutDetailPrice;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            tourDetailData = getArguments().getParcelable(TourDetailData.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            tourDetailData = savedInstanceState.getParcelable(TourDetailData.class.getName());
        }
    }

    //-----------------------------------------------
    public static FragmentTourAddPassenger newInstance(TourDetailData tourDetailData) {

        Bundle args = new Bundle();
        FragmentTourAddPassenger fragment = new FragmentTourAddPassenger();
        args.putParcelable(TourDetailData.class.getName(), tourDetailData);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(TourDetailData.class.getName(), tourDetailData);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_tour_passengerl_layout, container, false);
            initialComponentFragment();
        }
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment() {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        layoutDetailPrice = (LinearLayout) view.findViewById(R.id.layoutDetailPrice);
        btnRegister = (AppCompatButton) view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(onClickListener);
        layoutDetailPrice.setOnClickListener(onClickListener);
        tourApi = new TourApi(getActivity());
        setupRecyclerView();
        setupAddPassenger();
        setupTourDetails();
    }

    //-----------------------------------------------
    private void setupAddPassenger() {
        tvCountPassenger = (TextView) view.findViewById(R.id.tvCountPassenger);
        btnAddPassenger = (Button) view.findViewById(R.id.btnAddPassenger);
        btnAddPassenger.setPaintFlags(btnAddPassenger.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnAddPassenger.setOnClickListener(onClickListener);
        updateCountPassenger();
    }

    //-----------------------------------------------
    private void setupRecyclerView() {
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        passengerTourListAdapter = new PassengerTourListAdapter(getActivity(), onSelectItemPassengerDomesticListener);
        passengerTourListAdapter.setConfigPrice(tourDetailData.getPrice_adl(), tourDetailData.getPrice_chd(), tourDetailData.getPrice_inf(), tourDetailData.getPrice_single());
        rvResult.setAdapter(passengerTourListAdapter);
        passengerTourListAdapter.addNewPassenger(new PassengerTour());
    }

    //-----------------------------------------------
    OnSelectItemPassengerTourListener onSelectItemPassengerDomesticListener = new OnSelectItemPassengerTourListener() {
        @Override
        public void onSelectItemFlightDomestic(PassengerTour domesticPassengerInfo, int position) {
            Intent intent = new Intent(getActivity(), ActivityRegisterPassengerTour.class);
            intent.putExtra(PassengerTour.class.getName(), domesticPassengerInfo);
            intent.putExtra("typeTour", tourDetailData.getKind());
            intent.putExtra("index", position);
            startActivityForResult(intent, 0);
        }

        @Override
        public void onRemoveItemFlightDomestic(PassengerTour domesticPassengerInfo, int position) {
            passengerTourListAdapter.removePassenger(position);
            updateCountPassenger();

        }
    };

    //-----------------------------------------------
    private void updateCountPassenger() {
        tvCountPassenger.setText("(" + passengerTourListAdapter.getItemCount() + "/" + tourDetailData.getAvailable() + ")");
    }


    //-----------------------------------------------
    private void setupTourDetails() {
        try {
            TextView txtTourName = (TextView) view.findViewById(R.id.txtTourName);
            TextView txtWentDate = (TextView) view.findViewById(R.id.txtWentDate);
            TextView txtTourCountDayAndNight = (TextView) view.findViewById(R.id.txtTourCountDayAndNight);
            TextView txtReturnDate = (TextView) view.findViewById(R.id.txtReturnDate);

            txtTourName.setText(tourDetailData.getName());
            String dateCounter = tourDetailData.getDay_count() + getString(R.string.day) + " و " + tourDetailData.getNight_count() + getString(R.string.night);
            txtTourCountDayAndNight.setText(dateCounter);
            txtWentDate.setText(getString(R.string.moveDate) + " " + getPersianDate(tourDetailData.getStart_date()));

            txtReturnDate.setText(getString(R.string.date) + " " + getPersianDate(tourDetailData.getEnd_date()));

            setupPriceView();
        } catch (Exception e) {

        }

    }


    //-----------------------------------------------
    private void setupPriceView() {
        ToolsTourOption toolsTourOption = (ToolsTourOption) view.findViewById(R.id.toolsTourOption);
        String adultPrice = getFinalPrice(tourDetailData.getPrice_adl());
        String childPrice = getFinalPrice(tourDetailData.getPrice_chd());
        String infantPrice = getFinalPrice(tourDetailData.getPrice_inf());
        String singlePrice = getFinalPrice(tourDetailData.getPrice_single());
        toolsTourOption.setPassengerPrice(adultPrice, childPrice, infantPrice, singlePrice);
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
                if (passengerTourListAdapter.validateInfoPassenger())
                    showDialogFinalPreReserve();
            } else if (v.getId() == R.id.btnAddPassenger) {
                int capacity = Integer.parseInt(tourDetailData.getAvailable());
                if (capacity > passengerTourListAdapter.getItemCount()) {
                    int lastIndex = passengerTourListAdapter.getItemCount() == 0 ? 0 : passengerTourListAdapter.getItemCount();
                    Intent intent = new Intent(getActivity(), ActivityRegisterPassengerTour.class);
                    intent.putExtra("typeTour", tourDetailData.getKind());
                    intent.putExtra("index", lastIndex);
                    startActivityForResult(intent, 0);
                } else {
                    ToastMessageBar.show(getActivity(), R.string.msgErrorFullCapacityDomestic);
                }
            } else if (v.getId() == R.id.layoutDetailPrice) {
                getDetailPrice();
            }

        }
    };

    //-----------------------------------------------
    private void getDetailPrice() {
        BottomSheetLayoutPriceTour bottomSheetFragment = new BottomSheetLayoutPriceTour();
        bottomSheetFragment.setConfigPrice(tourDetailData.getPrice_single(), tourDetailData.getPrice_adl(), tourDetailData.getPrice_chd(), tourDetailData.getPrice_inf());
        bottomSheetFragment.show(getActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    //-----------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ActivityRegisterPassengerTour.RESULT_OK_EDIT_PASSENGER) {
                PassengerTour passengerTour = data.getParcelableExtra(PassengerTour.class.getName());
                int index = data.getExtras().getInt(("index"));
                passengerTourListAdapter.editPassenger(passengerTour, index);
            } else if (resultCode == ActivityRegisterPassengerTour.RESULT_OK_ADD_PASSENGER) {
                PassengerTour passengerTour = data.getParcelableExtra(PassengerTour.class.getName());
                //view.findViewById(R.id.layoutAddPassenger).setVisibility(View.VISIBLE);
                //messageBar.setVisibility(View.GONE);
                passengerTourListAdapter.addNewPassenger(passengerTour);
                updateCountPassenger();
            }


        } catch (Exception e) {

        }
        super.onActivityResult(requestCode, resultCode, data);
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
    public void showDialogFinalPreReserve() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_service_register_final_layout, null);

        UtilFonts.overrideFonts(getActivity(), dialogView, UtilFonts.IRAN_SANS_NORMAL);
        final EditText edtMobile = (EditText) dialogView.findViewById(R.id.edtMobile);
        final EditText edtEmail = (EditText) dialogView.findViewById(R.id.edtEmail);
        final CheckBox chkAcceptRule = (CheckBox) dialogView.findViewById(R.id.chkAcceptRule);
        chkAcceptRule.setTitle(R.string.rulesInternetBuy);
        DataSaver dataSaver = new DataSaver(getActivity());
        edtEmail.setText(dataSaver.getEmail());
        edtMobile.setText(dataSaver.getMobile());
        final ButtonWithProgress btnReserve = (ButtonWithProgress) dialogView.findViewById(R.id.btnRegister);
        btnReserve.setConfig(R.string.reserve, R.string.reserving, R.string.reserve);
        btnReserve.setBackgroundButton(R.drawable.bg_button_orange);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();

        btnReserve.setCallBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BookingTourRequest bookingTourRequest = new BookingTourRequest();
                    String mobile = Keyboard.convertPersianNumberToEngNumber(edtMobile.getText().toString());
                    String email = Keyboard.convertPersianNumberToEngNumber(edtEmail.getText().toString());
                    bookingTourRequest.setUserData(new BookingTourUserData(mobile, email));
                    Boolean isCheckRoll = chkAcceptRule.hasCheck();
                    if (mobile == null || mobile.length() == 0 || mobile.length() < 10) {
                        ToastMessageBar.show(getActivity(), R.string.validateMobile);
                        return;
                    }
                    if (!ValidationClass.validateEmail(email)) {
                        ToastMessageBar.show(getActivity(), R.string.validateEmail);
                        return;
                    }
                    if (!isCheckRoll) {
                        ToastMessageBar.show(getActivity(), R.string.validateAcceptRule);
                        return;
                    }
                    DataSaver dataSaver = new DataSaver(getActivity());
                    dataSaver.setEmailMobile(mobile, email);


                    bookingTourRequest.setPassengers(passengerTourListAdapter.getListItem());
                    bookingTourRequest.setTourID(tourDetailData.getId());
                    bookingTourRequest.setCaptcha("");


                    tourApi.registerPassenger(bookingTourRequest, new ResultSearchPresenter<BookingTourDetails>() {
                        @Override
                        public void onStart() {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.setCancelable(false);
                                        alertDialog.setCanceledOnTouchOutside(false);
                                        edtEmail.setEnabled(false);
                                        edtMobile.setEnabled(false);
                                        btnReserve.setEnableButton(false);
                                        chkAcceptRule.setEnabled(false);
                                        btnReserve.startProgress();

                                    }
                                });
                            }
                        }

                        @Override
                        public void onErrorServer(int type) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastMessageBar.show(getActivity(), R.string.msgErrorReserveTour);
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
                                        ToastMessageBar.show(getActivity(), R.string.msgErrorInternetConnection);
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
                                        ToastMessageBar.show(getActivity(), msg);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onSuccessResultSearch(final BookingTourDetails result) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.cancel();
                                        if (result != null) {
                                            UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(),
                                                    FragmentTourFinalBooking.newInstance(result));
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void noResult(int type) {

                        }

                        @Override
                        public void onFinish() {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.setCancelable(true);
                                        alertDialog.setCanceledOnTouchOutside(true);
                                        edtEmail.setEnabled(true);
                                        edtMobile.setEnabled(true);
                                        btnReserve.setEnableButton(true);
                                        chkAcceptRule.setEnabled(true);
                                        btnReserve.stopProgress();
                                    }
                                });
                            }
                        }
                    });
                } catch (Exception e) {


                    ToastMessageBar.show(getActivity(), R.string.msgErrorReserveTour);
                }
            }
        });

        alertDialog.show();
    }
}
