package hami.mainapp.flight.Services.Domestic.Fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
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

import com.hami.common.BaseController.PaymentPresenter;
import com.hami.common.BaseNetwork.BaseConfig;
import com.hami.common.Const.FlightRules;
import com.hami.common.Util.CustomeChrome.CustomTabsPackages;
import com.hami.common.Util.Hashing;
import com.hami.common.Util.UtilFonts;
import com.hami.common.Util.UtilImageLoader;
import com.hami.common.Util.UtilPrice;


import hami.mainapp.R;
import hami.mainapp.flight.Services.Domestic.Adapter.PassengerInfoLisDomesticAdapter;
import hami.mainapp.flight.Services.Domestic.Controller.Model.DomesticParams;
import hami.mainapp.flight.Services.Domestic.Controller.Model.RegisterFlightResponse;
import hami.mainapp.flight.Services.Domestic.Controller.Presenter.DomesticApi;

import java.text.NumberFormat;
import java.util.Locale;


public class FragmentFinalBookingFlightDomestic extends Fragment {
    //-----------------------------------------------
    private AppCompatButton btnGetTicket, btnBuy, btnEdit, btnExit;
    private Boolean hasReserve = false, hasPayment = false;
    private RecyclerView rvResult;
    private PassengerInfoLisDomesticAdapter mAdapter;
    private TextView txtTitleFinalTicket, txtFinalPrice, txtWarningCheckInfo;
    private LinearLayout layoutButtonPayment, layoutButtonGetTicket;
    private RegisterFlightResponse registerFlightResponse;
    private static final String TAG = "FragmentFinalBookingFlightDomestic";
    private String adultPrice;
    private AlertDialog alertDialogChangePrice;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            registerFlightResponse = getArguments().getParcelable(RegisterFlightResponse.class.getName());
            adultPrice = getArguments().getString("adultPrice");
        }

    }
    //-----------------------------------------------

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            hasReserve = savedInstanceState.getBoolean("hasReserve");
            hasPayment = savedInstanceState.getBoolean("hasPayment");
            hasPayment = savedInstanceState.getBoolean("adultPrice");

            registerFlightResponse = savedInstanceState.getParcelable(RegisterFlightResponse.class.getName());

        }
    }

    //------------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putBoolean("hasReserve", hasReserve);
            outState.putBoolean("hasPayment", hasPayment);
            outState.putString("adultPrice", adultPrice);
            outState.putParcelable(RegisterFlightResponse.class.getName(), registerFlightResponse);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    public static FragmentFinalBookingFlightDomestic newInstance(RegisterFlightResponse registerFlightResponse, String adultPrice) {
        Bundle args = new Bundle();
        FragmentFinalBookingFlightDomestic fragment = new FragmentFinalBookingFlightDomestic();
        args.putParcelable(RegisterFlightResponse.class.getName(), registerFlightResponse);
        args.putString("adultPrice", adultPrice);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //if (view == null) {
        View view = inflater.inflate(R.layout.fragment_service_flight_domestic_factor, container, false);
        initialComponentFragment(view);
        //}
        return view;
    }

    //-----------------------------------------------
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (alertDialogChangePrice != null) {
            alertDialogChangePrice.cancel();
        }
    }

    //-----------------------------------------------
    @Override
    public void onResume() {
        super.onResume();
        if (hasReserve) {
            new DomesticApi(getActivity()).hasBuyTicket(registerFlightResponse.getTicketId(), new PaymentPresenter() {
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
        layoutButtonGetTicket = view.findViewById(R.id.layoutButtonGetTicket);
        layoutButtonPayment = view.findViewById(R.id.layoutButtonPayment);
        txtFinalPrice = view.findViewById(R.id.txtFinalPrice);
        txtTitleFinalTicket = view.findViewById(R.id.titleFinalTicket);
        txtWarningCheckInfo = view.findViewById(R.id.txtWarningCheckInfo);
        txtWarningCheckInfo.setSelected(true);
        txtWarningCheckInfo.setVisibility(View.VISIBLE);
        btnBuy = view.findViewById(R.id.btnBuy);
        btnEdit = view.findViewById(R.id.btnEditBuy);
        btnGetTicket = view.findViewById(R.id.btnGetTicket);
        btnExit = view.findViewById(R.id.btnExit);

        btnGetTicket.setOnClickListener(onClickListener);
        btnBuy.setOnClickListener(onClickListener);
        btnExit.setOnClickListener(onClickListener);
        btnEdit.setOnClickListener(onClickListener);
        setupPlace(view);
        setupRecyclerView(view);
        CheckChangePrice();
    }

    //-----------------------------------------------
    private String getFinalPrice() {
        String finalPrice = "";
        try {
            finalPrice = registerFlightResponse.getViewParamsDomestic().getFinalPrice();
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice) / 10);
            finalPrice = "مبلغ نهایی پرداخت:" + finalPrice + " تومان";
            return finalPrice;
        } catch (Exception e) {

            finalPrice = "مبلغ نهایی پرداخت:" + registerFlightResponse.getViewParamsDomestic().getFinalPrice();
            return finalPrice + " ریال";
        }

    }
    //-----------------------------------------------
    private void setupPlace(View view) {
        DomesticParams domesticParams = registerFlightResponse.getViewParamsDomestic().getDomesticParams();
         txtFinalPrice.setText(getFinalPrice());
         TextView txtWarningCheckInfo = view.findViewById(R.id.txtWarningCheckInfo);
        TextView txtWentFlightCity = view.findViewById(R.id.txtWentFlightCity);
        TextView txtWentFlightDateTime = view.findViewById(R.id.txtWentFlightDateTime);
        ImageView imgLogoAirLine = view.findViewById(R.id.imgLogoAirLine);
        TextView txtAirLineAndTypeClass = view.findViewById(R.id.txtAirLineAndTypeClass);
        TextView txtFlightNumber = view.findViewById(R.id.txtFlightNumber);
        TextView txtFlightName = view.findViewById(R.id.txtFlightName);
        txtWarningCheckInfo.setSelected(true);
        txtFlightNumber.setText("شماره پرواز:" + domesticParams.getFlight_number_());
        txtFlightName.setText(domesticParams.getFlightName_().trim());
        String flightLocation = "پرواز از " + domesticParams.getFromfa() + " به " + domesticParams.getTofa();
        txtWentFlightCity.setText(flightLocation);
        txtWentFlightDateTime.setText(registerFlightResponse.getTakeOffDatePersian() + " ، " + domesticParams.getDeparture());
        txtAirLineAndTypeClass.setText(domesticParams.getAirline() + "(" + domesticParams.getFlightName_() + "-" + domesticParams.getFlight_number_() + ")");
        String url = BaseConfig.FOLDER_IMAGE_DOMESTIC_URL + registerFlightResponse.getAirlineCode() + ".png";
        UtilImageLoader.loadImage(getActivity(), imgLogoAirLine, url, R.mipmap.ic_launcher);
    }

    //-----------------------------------------------
    private void setupRecyclerView(View view) {
        rvResult = view.findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PassengerInfoLisDomesticAdapter(getActivity(), registerFlightResponse.getViewParamsDomestic().getDomesticParams());
        rvResult.setAdapter(mAdapter);
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
        String ticketId = registerFlightResponse.getTicketId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.MELLAT_BANK_FLIGHT_DOMESTIC + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);
    }

    //-----------------------------------------------
    public void getTicket() {
        String ticketId = registerFlightResponse.getTicketId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.BASE_URL_MASTER + "flight/pdfticket/" + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);
    }

    //-----------------------------------------------
    public Boolean hasBuyTicket() {
        return hasPayment;
    }

    //-----------------------------------------------
    private void CheckChangePrice() {
        try {
            Boolean statusChangePrice = false;
            double adultPriceNew = 0;
            double remainPrice = 0;
            double adultPriceOld = Double.parseDouble(adultPrice);
            String[] result = registerFlightResponse.getViewParamsDomestic().getDomesticParams().getPrice();
            DomesticParams domesticParams = registerFlightResponse.getViewParamsDomestic().getDomesticParams();
            String title3 = "";
            for (int index = 0; index < domesticParams.getTypep().length; index++) {
                int pType = Integer.parseInt(domesticParams.getTypep()[index]);
                if (pType == FlightRules.TP_ADULTS) {

                    adultPriceNew = Long.parseLong(domesticParams.getPrice()[index]);
                    if ((adultPriceOld < adultPriceNew) && (adultPriceNew - adultPriceOld) >= 1000) {
                        remainPrice = adultPriceNew - adultPriceOld;
                        String finalPrice = UtilPrice.convertToToman(remainPrice);
                        title3 = "مبلغ  " + finalPrice + " به قیمت هر مسافر(بزرگسال) افزوده شده است.";
                        statusChangePrice = true;
                    } else if (adultPriceOld > adultPriceNew) {
                        remainPrice = adultPriceOld - adultPriceNew;
                        String finalPrice = UtilPrice.convertToToman(remainPrice);
                        title3 = "مبلغ  " + finalPrice + " از قیمت هر مسافر(بزرگسال) کسر شده است.";
                        statusChangePrice = true;
                    } else {
                        statusChangePrice = false;
                    }
                    break;
                } else
                    continue;
            }
            if (statusChangePrice) {


                showDialogChangePrice(title3);
            }
        } catch (Exception e) {

        }

    }

    //-----------------------------------------------
    public void showDialogChangePrice(String title1) {
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.include_layout_change_price_message, null);
            UtilFonts.overrideFonts(getActivity(), dialogView, UtilFonts.IRAN_SANS_NORMAL);
            dialogBuilder.setView(dialogView);
            alertDialogChangePrice = dialogBuilder.create();
            alertDialogChangePrice.setCancelable(true);
            alertDialogChangePrice.setCanceledOnTouchOutside(true);
            TextView txtTitle1 = dialogView.findViewById(R.id.txtTitle1);
            txtTitle1.setText(title1);
            txtTitle1.setSelected(true);
            AppCompatButton btnAccept = dialogView.findViewById(R.id.btnAccept);
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogChangePrice.dismiss();
                }
            });
            alertDialogChangePrice.show();
        } catch (Exception e) {

        }

    }
    //-----------------------------------------------
}