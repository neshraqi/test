package com.hami.servicehotel.Domestic;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hami.common.BaseController.PaymentPresenter;
import com.hami.common.BaseNetwork.BaseConfig;
import com.hami.common.Util.CustomeChrome.CustomTabsPackages;
import com.hami.common.Util.UtilFonts;
import com.hami.common.View.ToastMessageBar;
import com.hami.servicehotel.Domestic.Controller.DomesticHotelApi;
import com.hami.servicehotel.Domestic.Controller.Model.DomesticHotelRegisterPassengerInfo;
import com.hami.servicehotel.Domestic.Controller.Model.DomesticHotelRegisterPassengerResponse;
import com.hami.servicehotel.Domestic.Controller.Model.DomesticHotelRegisterPassengerViewParams;
import com.hami.servicehotel.R;

import java.text.NumberFormat;
import java.util.Locale;

public class FragmentFinalBookingDomesticHotel extends Fragment {

    //-----------------------------------------------
    private DomesticHotelApi hotelApi;
    private View view;
    private static final String TAG = "FragmentFinalBookingDomesticHotel";
    private DomesticHotelRegisterPassengerResponse domesticHotelRegisterPassengerResponse;
    private AppCompatButton btnGetTicket, btnBuy, btnEdit, btnExit;
    private TextView txtTitleFinalTicket, txtFinalPrice, txtWarningCheckInfo;
    private LinearLayout layoutButtonPayment, layoutButtonGetTicket;
    private Boolean hasReserve = false, hasPayment = false;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            domesticHotelRegisterPassengerResponse = getArguments().getParcelable(DomesticHotelRegisterPassengerResponse.class.getName());
//            reserveId = getArguments().getString("reserveId");
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            hasReserve = savedInstanceState.getBoolean("hasReserve");
            hasPayment = savedInstanceState.getBoolean("hasPayment");
            domesticHotelRegisterPassengerResponse = savedInstanceState.getParcelable(DomesticHotelRegisterPassengerResponse.class.getName());
        }
    }

    //-----------------------------------------------
    public static FragmentFinalBookingDomesticHotel newInstance(DomesticHotelRegisterPassengerResponse domesticHotelRegisterPassengerResponse) {

        Bundle args = new Bundle();
        FragmentFinalBookingDomesticHotel fragment = new FragmentFinalBookingDomesticHotel();
        args.putParcelable(DomesticHotelRegisterPassengerResponse.class.getName(), domesticHotelRegisterPassengerResponse);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(DomesticHotelRegisterPassengerResponse.class.getName(), domesticHotelRegisterPassengerResponse);
            outState.putBoolean("hasReserve", hasReserve);
            outState.putBoolean("hasPayment", hasPayment);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_final_booking_domestic_hotel_layout, container, false);
            initialComponentFragment(view);
        }
        return view;
    }

    //-----------------------------------------------
    @Override
    public void onPause() {
        super.onPause();

    }

    //-----------------------------------------------
    @Override
    public void onResume() {
        super.onResume();

        if (hasReserve) {
            String ticketId = domesticHotelRegisterPassengerResponse.getViewParams().getId();
            String hashId = domesticHotelRegisterPassengerResponse.getViewParams().getHashId();
            hotelApi.hasBuyTicket(ticketId, hashId, new PaymentPresenter() {
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
                                txtWarningCheckInfo.setVisibility(View.GONE);
                                hasPayment = true;
                                setupGetTicket();
                                txtTitleFinalTicket.setText(R.string.successGetTicket);
                                ViewCompat.setBackgroundTintList(btnGetTicket, ColorStateList.valueOf(getResources().getColor(R.color.greenSelectedChair)));
                                btnGetTicket.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getTicket();
                                    }
                                });
                            }
                        });
                    }
                }

                @Override
                public void onErrorBuy() {

                }

                @Override
                public void onReTryGetPayment() {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setupPayment();
                                btnGetTicket.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getTicket();
                                    }
                                });
                            }
                        });
                    }
                }

                @Override
                public void onReTryGetTicket() {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setupGetTicket();
                                txtTitleFinalTicket.setText(getString(R.string.msgErrorRunningGetTicket));
                                ViewCompat.setBackgroundTintList(btnGetTicket, ColorStateList.valueOf(Color.RED));
                                btnGetTicket.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showPayment();
                                    }
                                });
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
    private void initialComponentFragment(View view) {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        hotelApi = new DomesticHotelApi(getActivity());
        layoutButtonGetTicket = view.findViewById(R.id.layoutButtonGetTicket);
        layoutButtonPayment = view.findViewById(R.id.layoutButtonPayment);
        txtFinalPrice = view.findViewById(R.id.txtFinalPrice);
        txtTitleFinalTicket = view.findViewById(R.id.titleFinalTicket);
        txtWarningCheckInfo = view.findViewById(R.id.txtWarningCheckInfo);
        txtWarningCheckInfo.setVisibility(View.VISIBLE);
        txtWarningCheckInfo.setSelected(true);
        btnBuy = view.findViewById(R.id.btnBuy);
        btnEdit = view.findViewById(R.id.btnEditBuy);

        btnGetTicket = view.findViewById(R.id.btnGetTicket);
        btnExit = view.findViewById(R.id.btnExit);

        btnGetTicket.setOnClickListener(onClickListener);
        btnBuy.setOnClickListener(onClickListener);
        btnExit.setOnClickListener(onClickListener);
        btnEdit.setOnClickListener(onClickListener);
        setupPassengerRequest();
        setupRegisterPassenger();
    }

    //-----------------------------------------------
    private void setupPassengerRequest() {
        try {
            TextView txtHotelName = view.findViewById(R.id.txtHotelName);
            TextView txtHotelRoom = view.findViewById(R.id.txtHotelRoom);
            TextView txtHotelDateRequest = view.findViewById(R.id.txtHotelDateRequest);
            TextView txtPriceRoom = view.findViewById(R.id.txtPriceRoom);
            txtPriceRoom.setVisibility(View.GONE);
            RatingBar rbRating = view.findViewById(R.id.rbRating);
            DomesticHotelRegisterPassengerViewParams domesticHotelRegisterPassengerViewParams = domesticHotelRegisterPassengerResponse.getViewParams();
            txtHotelName.setText(domesticHotelRegisterPassengerViewParams.getHotelNameFa());
            Integer rating = Integer.valueOf(domesticHotelRegisterPassengerViewParams.getHotelStar());
            if (rating == 0) {
                rbRating.setVisibility(View.GONE);
            } else {
                rbRating.setVisibility(View.VISIBLE);
                rbRating.setNumStars(rating);
                rbRating.setRating(rating);
            }
            txtHotelRoom.setText(domesticHotelRegisterPassengerViewParams.getRoomNameFa());
            txtHotelDateRequest.setText("از " + domesticHotelRegisterPassengerViewParams.getInDate() + " به مدت  " + domesticHotelRegisterPassengerViewParams.getNumberOfNights() + "شب ");
            txtFinalPrice.setText(getFinalPrice(domesticHotelRegisterPassengerViewParams.getPrice()));
        } catch (Exception e) {
            ToastMessageBar.show(getActivity(), R.string.msgErrorPayment);
            getActivity().onBackPressed();
        }

    }




    //-----------------------------------------------
    public void setupRegisterPassenger() {
        try {
            TextView txtEmail = view.findViewById(R.id.txtEmail);
            TextView txtFullNamePerson = view.findViewById(R.id.txtFullNamePerson);
            TextView txtTelephone = view.findViewById(R.id.txtTelephone);
            TextView txtMobile = view.findViewById(R.id.txtMobile);
            DomesticHotelRegisterPassengerInfo infoParam = domesticHotelRegisterPassengerResponse.getViewParams().getDomesticHotelRegisterPassengerInfo();
            txtEmail.setText(getString(R.string.email) + ":" + infoParam.getEmail().get(0));
            txtFullNamePerson.setText(infoParam.getName().get(0) + " " + infoParam.getFamily().get(0));
            txtTelephone.setText(getString(R.string.telephone) + ":" + infoParam.getPhone().get(0));
            txtMobile.setText(getString(R.string.mobile) + ":" + infoParam.getMobile().get(0));
        } catch (Exception e) {
            ToastMessageBar.show(getActivity(), R.string.msgErrorPayment);
            getActivity().onBackPressed();
        }
    }


    //-----------------------------------------------
    private String getFinalPrice(String priceCurrent) {
        String finalPrice = "";
        try {
            finalPrice = priceCurrent;
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice) / 10);
            finalPrice = getString(R.string.finalPriceWithDiscount) + finalPrice + " تومان";
            return finalPrice;
        } catch (Exception e) {


            finalPrice = getString(R.string.finalPriceWithDiscount) + priceCurrent;
            return finalPrice + " ریال";
        }

    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.btnEditBuy) {
                getActivity().onBackPressed();

            } else if (i == R.id.btnBuy) {
                showPayment();

            } else if (i == R.id.btnGetTicket) {
                getTicket();

            } else if (i == R.id.btnExit) {
                getActivity().finish();

            }
        }
    };

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
        txtWarningCheckInfo.setVisibility(View.GONE);
        getActivity().findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
    }

    //-----------------------------------------------
    public void showPayment() {
        hasReserve = true;
        String ticketId = domesticHotelRegisterPassengerResponse.getViewParams().getId();
        String hash = domesticHotelRegisterPassengerResponse.getViewParams().getHashId();
        String url = BaseConfig.BANK_HOTEL_DOMESTIC + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);
    }

    //-----------------------------------------------
    public void getTicket() {
        String ticketId = domesticHotelRegisterPassengerResponse.getViewParams().getId();
        String hash = domesticHotelRegisterPassengerResponse.getViewParams().getHashId();
        String url = BaseConfig.BASE_URL_MASTER + "hotel/pdfticket/" + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);
    }

    //-----------------------------------------------
    public Boolean hasBuyTicket() {
        return hasPayment;
    }
}
