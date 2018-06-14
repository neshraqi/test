package hami.aniticket.Activity.ServiceHotel.Domestic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;



import java.text.NumberFormat;
import java.util.Locale;

import hami.aniticket.Activity.ServiceHotel.Domestic.Controller.DomesticHotelApi;
import hami.aniticket.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelBookingProcessData;
import hami.aniticket.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelPreBookingRequest;
import hami.aniticket.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelPreBookingResponse;
import hami.aniticket.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelRegisterPassengerRequest;
import hami.aniticket.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelRegisterPassengerResponse;
import hami.aniticket.Activity.ServiceHotel.International.Adapter.ListPassengerInternationalHotelListAdapter;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.DataPassengerInfo;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.InternationalApi;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.NationalCodePresenter;
import hami.aniticket.BaseController.ResultSearchPresenter;
import hami.aniticket.R;
import hami.aniticket.Util.Database.DataSaver;
import hami.aniticket.Util.Keyboard;
import hami.aniticket.Util.UtilFonts;
import hami.aniticket.Util.UtilFragment;
import hami.aniticket.Util.ValidateNationalCode;
import hami.aniticket.View.CheckBox;
import hami.aniticket.View.Progressbar.ButtonWithProgress;
import hami.aniticket.View.ToastMessageBar;
import hami.aniticket.View.Validation.ValidationClass;


public class FragmentPassengerDomesticHotel extends Fragment {

    //-----------------------------------------------
    private DomesticHotelPreBookingResponse hotelDetailResponse;
    private DomesticHotelApi hotelApi;
    private View view;
    private ListPassengerInternationalHotelListAdapter listPassengerInternationalHotelListAdapter;
    private static final String TAG = "DomesticHotelPreBookingResponse";
    private ButtonWithProgress btnBooking;
    private String reserveId;
    private DomesticHotelPreBookingRequest domesticHotelPreBookingRequest;
    private EditText edtNationalCode, edtLName, edtFName, edtTelephone, edtMobile, edtDesc, edtEmail;
    private AppCompatSpinner spBedExtra;
    private CheckBox chkAcceptRule;
    private TextView txtFinalPrice;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            hotelDetailResponse = getArguments().getParcelable(DomesticHotelPreBookingResponse.class.getName());
            domesticHotelPreBookingRequest = (DomesticHotelPreBookingRequest) getArguments().getSerializable(DomesticHotelPreBookingRequest.class.getName());
//            reserveId = getArguments().getString("reserveId");
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            hotelDetailResponse = savedInstanceState.getParcelable(DomesticHotelPreBookingResponse.class.getName());
            domesticHotelPreBookingRequest = (DomesticHotelPreBookingRequest) savedInstanceState.getSerializable(DomesticHotelPreBookingRequest.class.getName());
//            reserveId = savedInstanceState.getString("reserveId");
        }
    }

    //-----------------------------------------------
    public static FragmentPassengerDomesticHotel newInstance(DomesticHotelPreBookingResponse hotelDetailResponse, DomesticHotelPreBookingRequest domesticHotelPreBookingRequest) {

        Bundle args = new Bundle();
        FragmentPassengerDomesticHotel fragment = new FragmentPassengerDomesticHotel();
        args.putParcelable(DomesticHotelPreBookingResponse.class.getName(), hotelDetailResponse);
        args.putSerializable(DomesticHotelPreBookingRequest.class.getName(), domesticHotelPreBookingRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(DomesticHotelPreBookingResponse.class.getName(), hotelDetailResponse);
            outState.putSerializable(DomesticHotelPreBookingRequest.class.getName(), domesticHotelPreBookingRequest);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_passenger_domestic_hotel_layout, container, false);
            initialComponentFragment(view);
        }
        return view;
    }


    //-----------------------------------------------
    private void initialComponentFragment(View view) {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        hotelApi = new DomesticHotelApi(getActivity());
        txtFinalPrice = (TextView) view.findViewById(R.id.txtFinalPrice);
        btnBooking = (ButtonWithProgress) view.findViewById(R.id.btnRegister);
        btnBooking.setConfig(R.string.reserve, R.string.reserving, R.string.reserve);
        btnBooking.setCallBack(onClickListener);
        setupPassengerRequest();
        setupRegisterPassenger();
    }

    //-----------------------------------------------
    private void setupPassengerRequest() {
        try {
            TextView txtHotelName = (TextView) view.findViewById(R.id.txtHotelName);
            TextView txtHotelRoom = (TextView) view.findViewById(R.id.txtHotelRoom);
            TextView txtHotelDateRequest = (TextView) view.findViewById(R.id.txtHotelDateRequest);
            TextView txtPriceRoom = (TextView) view.findViewById(R.id.txtPriceRoom);

            RatingBar rbRating = (android.widget.RatingBar) view.findViewById(R.id.rbRating);
            DomesticHotelBookingProcessData domesticHotelBookingProcessData = hotelDetailResponse.getHotelInfoRoom();
            txtHotelName.setText(domesticHotelBookingProcessData.getHotelNameFa());
            txtPriceRoom.setText(getFinalPrice(hotelDetailResponse.getRoomInfo().getPrice()));
            txtFinalPrice.setText("مبلغ قبل از رزرو:" + getFinalPrice(hotelDetailResponse.getRoomInfo().getPrice()));
            Integer rating = domesticHotelBookingProcessData.getHotelStar();
            if (rating == 0) {
                rbRating.setVisibility(View.GONE);
            } else {
                rbRating.setVisibility(View.VISIBLE);
                rbRating.setNumStars(rating);
                rbRating.setRating(rating);
            }
            txtHotelRoom.setText(hotelDetailResponse.getRoomInfo().getNameFa());
            txtHotelDateRequest.setText("از " + hotelDetailResponse.getRoomInfo().getDays().get(0) + " به مدت  " + domesticHotelPreBookingRequest.getNumberOfNights() + "شب ");
        } catch (Exception e) {
            ToastMessageBar.show(getActivity(), R.string.msgErrorPayment);
            getActivity().onBackPressed();
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
    public void setupRegisterPassenger() {
        spBedExtra = (AppCompatSpinner) view.findViewById(R.id.spBedExtra);

        if (hotelDetailResponse.getRoomInfo().getExtraPersons() == 0) {
            spBedExtra.setVisibility(View.GONE);
            view.findViewById(R.id.txtTitleBedsExtra).setVisibility(View.GONE);
        } else {
            spBedExtra.setVisibility(View.VISIBLE);
            view.findViewById(R.id.txtTitleBedsExtra).setVisibility(View.VISIBLE);
            String[] extraPerson = new String[hotelDetailResponse.getRoomInfo().getExtraPersons() + 1];
            for (int index = 0; index <= hotelDetailResponse.getRoomInfo().getExtraPersons(); index++) {
                long price = 0;
                if (hotelDetailResponse.getRoomInfo().getExtraPersonPrice() == null || hotelDetailResponse.getRoomInfo().getExtraPersonPrice().size() == 0)
                    price = 0;
                else
                    price = Long.valueOf(hotelDetailResponse.getRoomInfo().getExtraPersonPrice().get(0).replace(",", ""));
                long priceWithBed = price * index;
                String priceTitle = (index != 0) ? "(" + getFinalPrice(String.valueOf(priceWithBed)) + ")" : "";
                extraPerson[index] = (index != 0) ? String.valueOf(index) + getString(R.string.bedExtra) + priceTitle : getString(R.string.noBedExtra);
            }
            spBedExtra.setAdapter(new ArrayAdapter<CharSequence>(getActivity(), R.layout.row_item_spinner_train_white, extraPerson));
            spBedExtra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    long price = Long.valueOf(hotelDetailResponse.getRoomInfo().getPrice().replace(",", ""));
                    long priceBed = 0;
                    if (hotelDetailResponse.getRoomInfo().getExtraPersonPrice() == null || hotelDetailResponse.getRoomInfo().getExtraPersonPrice().size() == 0)
                        priceBed = 0;
                    else
                        priceBed = Long.valueOf(hotelDetailResponse.getRoomInfo().getExtraPersonPrice().get(0).replace(",", ""));
                    long priceWithBedExtra = priceBed * position;
                    long finalPrice = price + priceWithBedExtra;
                    txtFinalPrice.setText("مبلغ قبل از رزرو:" + getFinalPrice(String.valueOf(finalPrice)));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spBedExtra.setSelection(0);
        }

        edtNationalCode = (EditText) view.findViewById(R.id.edtNationalCode);
        edtLName = (EditText) view.findViewById(R.id.edtLName);
        edtFName = (EditText) view.findViewById(R.id.edtFName);
        edtTelephone = (EditText) view.findViewById(R.id.edtTelephone);
        edtMobile = (EditText) view.findViewById(R.id.edtMobile);
        edtDesc = (EditText) view.findViewById(R.id.edtDesc);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        chkAcceptRule = (CheckBox) view.findViewById(R.id.chkAcceptRule);
        chkAcceptRule.setTitle(R.string.rulesInternetBuy);
        DataSaver dataSaver = new DataSaver(getActivity());
        edtEmail.setText(dataSaver.getEmail());
        edtMobile.setText(dataSaver.getMobile());
        edtNationalCode.addTextChangedListener(textWatcherNationalCode);
    }


    //-----------------------------------------------
    private TextWatcher textWatcherNationalCode = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 10) {
                getInfo(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //-----------------------------------------------
    private void getInfo(String nationalCode) {
        new InternationalApi(getActivity()).getInfoByNationalCode(nationalCode, new NationalCodePresenter() {
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
            public void onSuccessDataPassengerInfo(final DataPassengerInfo dataPassengerInfo) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                edtFName.setText(dataPassengerInfo.getPassengerNamePersian());
                                edtLName.setText(dataPassengerInfo.getPassengerFamilyPersian());
                                Keyboard.closeKeyboard(getActivity());
                            } catch (Exception e) {


                            }

                        }
                    });
            }


            @Override
            public void onError(String msg) {
            }

            @Override
            public void onFinish() {

            }
        });
    }

    //-----------------------------------------------
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {
                Keyboard.closeKeyboard(getActivity());
                registerPassenger();

            } catch (Exception e) {


                ToastMessageBar.show(getActivity(), R.string.msgErrorReserveHotel);
            }

        }
    };

    //-----------------------------------------------
    private void registerPassenger() {
        if (!ValidateNationalCode.isValidNationalCode(edtNationalCode.getText().toString())) {
            ToastMessageBar.show(getActivity(), R.string.validateNationalCode);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtNationalCode.startAnimation(vibrateAnimation);
            edtNationalCode.requestFocus();
            return;
        }
        if (edtFName.length() == 0) {
            ToastMessageBar.show(getActivity(), R.string.validateFirstNamePersian);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtFName.startAnimation(vibrateAnimation);
            edtFName.requestFocus();
            return;
        }
        if (edtLName.length() == 0) {
            ToastMessageBar.show(getActivity(), R.string.validateLastNamePersian);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtLName.startAnimation(vibrateAnimation);
            edtLName.requestFocus();
            return;
        }
        if (edtMobile.length() == 0) {
            ToastMessageBar.show(getActivity(), R.string.validateMobile);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtMobile.startAnimation(vibrateAnimation);
            return;
        }
        if (edtTelephone.length() == 0) {
            ToastMessageBar.show(getActivity(), R.string.validateTelephone);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtTelephone.startAnimation(vibrateAnimation);
            edtTelephone.requestFocus();
            return;
        }
        if (!ValidationClass.validateEmail(edtEmail.getText().toString())) {
            ToastMessageBar.show(getActivity(), R.string.validateEmail);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtEmail.startAnimation(vibrateAnimation);
            edtEmail.requestFocus();
            return;
        }
        if (!chkAcceptRule.hasCheck()) {
            ToastMessageBar.show(getActivity(), R.string.validateAcceptRule);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            chkAcceptRule.startAnimation(vibrateAnimation);
            return;
        }
        DomesticHotelRegisterPassengerRequest domesticHotelRegisterPassengerRequest = new DomesticHotelRegisterPassengerRequest();
        domesticHotelRegisterPassengerRequest.setInternationalcode(edtNationalCode.getText().toString());
        domesticHotelRegisterPassengerRequest.setName(edtFName.getText().toString());
        domesticHotelRegisterPassengerRequest.setFamily(edtLName.getText().toString());
        domesticHotelRegisterPassengerRequest.setEmail(edtEmail.getText().toString());
        domesticHotelRegisterPassengerRequest.setMobile(edtMobile.getText().toString());
        domesticHotelRegisterPassengerRequest.setPhone(edtTelephone.getText().toString());
        domesticHotelRegisterPassengerRequest.setDescription(edtDesc.getText().toString());
        String extraPerson = hotelDetailResponse.getRoomInfo().getExtraPersons() <= 0 ? "0" : String.valueOf(spBedExtra.getSelectedItemPosition());
        domesticHotelRegisterPassengerRequest.setExtraPerson(extraPerson);
        domesticHotelRegisterPassengerRequest.setRoomId(domesticHotelPreBookingRequest.getRoomId());
        domesticHotelRegisterPassengerRequest.setSearchId(domesticHotelPreBookingRequest.getSearchId());
        hotelApi.registerHotel(domesticHotelRegisterPassengerRequest, new ResultSearchPresenter<DomesticHotelRegisterPassengerResponse>() {
            @Override
            public void onStart() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //messageBar.hideMessageBar();
                            btnBooking.startProgress();
                            view.setEnabled(false);
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
                            ToastMessageBar.show(getActivity(), R.string.msgErrorServer);
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
            public void onSuccessResultSearch(final DomesticHotelRegisterPassengerResponse result) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentFinalBookingDomesticHotel.newInstance(result));
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
                            ToastMessageBar.show(getActivity(), R.string.msgErrorNoDomesticHotel);
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
            public void onFinish() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnBooking.stopProgress();
                            view.setEnabled(true);
                        }
                    });
                }
            }


        });
    }

}
