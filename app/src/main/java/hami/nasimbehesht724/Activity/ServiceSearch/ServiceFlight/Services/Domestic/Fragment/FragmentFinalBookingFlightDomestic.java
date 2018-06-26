package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Fragment;

import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentPresenter;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Adapter.PassengerInfoLisDomesticAdapter;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticParams;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.RegisterFlightResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Presenter.DomesticApi;
import hami.nasimbehesht724.BaseController.DividerItemDecoration;
import hami.nasimbehesht724.BaseNetwork.BaseConfig;
import hami.nasimbehesht724.Const.FlightRules;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.CustomeChrome.CustomTabsPackages;
import hami.nasimbehesht724.Util.Hashing;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilImageLoader;


public class FragmentFinalBookingFlightDomestic extends Fragment {
    //-----------------------------------------------
    private RelativeLayout coordinator;
    private AppCompatButton btnGetTicket, btnBuy, btnEdit, btnExit;
    private Boolean hasReserve = false, hasPayment = false;
    private RecyclerView rvResult;
    private PassengerInfoLisDomesticAdapter mAdapter;
    private TextView txtTitleFinalTicket, txtFinalPrice;
    private LinearLayout layoutButtonPayment, layoutButtonGetTicket;
    private RegisterFlightResponse registerFlightResponse;
    private static final String TAG = "FragmentFinalBookingFlightDomestic";
    private String adultPrice;

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
                                hasPayment = true;
                                setupGetTicket();
                            }
                        });
                    }
                }

                @Override
                public void onErrorBuy() {

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
    private void initialComponentFragment(View view) {
        coordinator = (RelativeLayout) view.findViewById(R.id.coordinator);
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        layoutButtonGetTicket = (LinearLayout) view.findViewById(R.id.layoutButtonGetTicket);
        layoutButtonPayment = (LinearLayout) view.findViewById(R.id.layoutButtonPayment);
        txtFinalPrice = (TextView) view.findViewById(R.id.txtFinalPrice);
        txtTitleFinalTicket = (TextView) view.findViewById(R.id.titleFinalTicket);
        btnBuy = (AppCompatButton) view.findViewById(R.id.btnBuy);
        btnEdit = (AppCompatButton) view.findViewById(R.id.btnEditBuy);

        btnGetTicket = (AppCompatButton) view.findViewById(R.id.btnGetTicket);
        btnExit = (AppCompatButton) view.findViewById(R.id.btnExit);

        btnGetTicket.setOnClickListener(onClickListener);
        btnBuy.setOnClickListener(onClickListener);
        btnExit.setOnClickListener(onClickListener);
        btnEdit.setOnClickListener(onClickListener);
        setupPlace(view);
        setupRecyclerView(view);
        CheckChangePrice();
    }


    //-----------------------------------------------
    private void setupPlace(View view) {
        DomesticParams domesticParams = registerFlightResponse.getViewParamsDomestic().getDomesticParams();
        txtFinalPrice.setText(getFinalPrice());
        TextView txtWentFlightCity = (TextView) view.findViewById(R.id.txtWentFlightCity);
        TextView txtWentFlightDateTime = (TextView) view.findViewById(R.id.txtWentFlightDateTime);
        ImageView imgLogoAirLine = (ImageView) view.findViewById(R.id.imgLogoAirLine);
        TextView txtAirLineAndTypeClass = (TextView) view.findViewById(R.id.txtAirLineAndTypeClass);
        TextView txtFlightNumber = (TextView) view.findViewById(R.id.txtFlightNumber);
        TextView txtFlightName = (TextView) view.findViewById(R.id.txtFlightName);
        txtFlightNumber.setText("شماره پرواز:" + domesticParams.getFlight_number_());
        txtFlightName.setText(domesticParams.getFlightName_().trim());
        String flightLocation = "پرواز از " + domesticParams.getFromfa() + " به " + domesticParams.getTofa();
        txtWentFlightCity.setText(flightLocation);
        txtWentFlightDateTime.setText(registerFlightResponse.getTakeOffDatePersian() + " ، " + domesticParams.getDeparture());
        txtAirLineAndTypeClass.setText(domesticParams.getAirline() + "(" + domesticParams.getFlightName_() + "-" + domesticParams.getFlight_number_() + ")");
        String url = BaseConfig.FOLDER_IMAGE_DOMESTIC_URL + registerFlightResponse.getAirlineCode() + ".png";
        UtilImageLoader.loadImage(getActivity(), imgLogoAirLine, url, R.mipmap.ic_airplan_top);
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
    private void setupRecyclerView(View view) {
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new PassengerInfoLisDomesticAdapter(getActivity(), registerFlightResponse.getViewParamsDomestic().getDomesticParams());
        rvResult.setAdapter(mAdapter);
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
            long adultPriceNew = 0;
            long adultPriceOld = Long.parseLong(adultPrice);
            String[] result = registerFlightResponse.getViewParamsDomestic().getDomesticParams().getPrice();
            DomesticParams domesticParams = registerFlightResponse.getViewParamsDomestic().getDomesticParams();
            for (int index = 0; index < domesticParams.getTypep().length; index++) {
                int pType = Integer.parseInt(domesticParams.getTypep()[index]);
                if (pType == FlightRules.TP_ADULTS) {
                    adultPriceNew = Long.parseLong(domesticParams.getPrice()[index]);
                    if (adultPriceOld < adultPriceNew) {
                        statusChangePrice = true;
                    }
                    break;
                } else
                    continue;
            }
            if (statusChangePrice) {
                String finalPrice = NumberFormat.getNumberInstance(Locale.US).format(adultPriceNew / 10);
                finalPrice = finalPrice + " تومان";
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                String infoFlight = registerFlightResponse.getViewParamsDomestic().getDomesticParams().getFromfa() + " به "
                        + registerFlightResponse.getViewParamsDomestic().getDomesticParams().getTofa();
                dialogBuilder.setMessage("مسافر گرامی: نرخ کلاس پرواز " + infoFlight + " به مبلغ " + finalPrice + " تغییر کرده است. ");
                dialogBuilder.setPositiveButton(R.string.accept_, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogBuilder.show();
            }
        } catch (Exception e) {

        }

    }
}