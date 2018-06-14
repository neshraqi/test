package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import hami.hamibelit.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentPresenter;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Adapter.PassengerInfoListAdapter;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.RegisterPassengerResponse;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.ReserveRequest;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.TicketInternational;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.AllFlightInternationalIati;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.AllFlightInternationalParto;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.FinalFlightInternationalIati;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.PackageCompletedFlightInternational;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.ResultRegisterFlightInternationalResponse;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.ResultRegisterPassengerFlightInternationalResponse;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.RulesRequest;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.InternationalApi;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.ReserveInternationalPresenter;
import hami.hamibelit.BaseController.DividerItemDecoration;
import hami.hamibelit.BaseNetwork.BaseConfig;
import hami.hamibelit.Const.FlightRules;
import hami.hamibelit.R;
import hami.hamibelit.Util.CustomeChrome.CustomTabsPackages;
import hami.hamibelit.Util.Hashing;
import hami.hamibelit.Util.UtilFonts;
import hami.hamibelit.Util.UtilFragment;
import hami.hamibelit.View.ToastMessageBar;


public class FragmentFinalBookingFlightInternational extends Fragment {

    private View view;
    private RecyclerView rvResult;
    private LinearLayout layoutButtonPayment, layoutButtonGetTicket;
    private RegisterPassengerResponse registerPassengerResponse;
    private AppCompatButton btnGetTicket, btnBuy, btnEdit, btnExit;
    private TextView txtTitleFinalTicket, txtFinalPrice;
    private InternationalApi internationalApi;
    private ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    private Boolean hasReserve = false, hasPayment = false;
    private TicketInternational ticketInternational;
    private AllFlightInternationalParto allFlightInternationalParto;
    private FinalFlightInternationalIati finalFlightInternationalIati;
    private AllFlightInternationalIati allFlightInternationalIati;
    private PackageCompletedFlightInternational packageCompletedFlightInternational;
    private static final String TAG = "FragmentFinalBookingFlightInternational";

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            allFlightInternationalIati = getArguments().getParcelable(AllFlightInternationalIati.class.getName());
            allFlightInternationalParto = getArguments().getParcelable(AllFlightInternationalParto.class.getName());
            finalFlightInternationalIati = getArguments().getParcelable(FinalFlightInternationalIati.class.getName());
            packageCompletedFlightInternational = (PackageCompletedFlightInternational) getArguments().getSerializable(PackageCompletedFlightInternational.class.getName());
            registerPassengerResponse = getArguments().getParcelable(RegisterPassengerResponse.class.getName());
        } else {
            getActivity().onBackPressed();
            ToastMessageBar.show(getActivity(), R.string.msgErrorRegister);
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            allFlightInternationalIati = savedInstanceState.getParcelable(AllFlightInternationalIati.class.getName());
            allFlightInternationalParto = savedInstanceState.getParcelable(AllFlightInternationalParto.class.getName());
            finalFlightInternationalIati = savedInstanceState.getParcelable(FinalFlightInternationalIati.class.getName());
            packageCompletedFlightInternational = (PackageCompletedFlightInternational) savedInstanceState.getSerializable(PackageCompletedFlightInternational.class.getName());
            registerPassengerResponse = savedInstanceState.getParcelable(RegisterPassengerResponse.class.getName());
            hasReserve = savedInstanceState.getBoolean("hasReserve");
            hasPayment = savedInstanceState.getBoolean("hasPayment");

        }
    }

    //    //-----------------------------------------------
    public static FragmentFinalBookingFlightInternational newInstance(RegisterPassengerResponse registerPassengerResponse,
                                                                      AllFlightInternationalParto allFlightInternationalParto,
                                                                      PackageCompletedFlightInternational packageCompletedFlightInternational) {

        Bundle args = new Bundle();
        FragmentFinalBookingFlightInternational fragment = new FragmentFinalBookingFlightInternational();
        args.putParcelable(AllFlightInternationalParto.class.getName(), allFlightInternationalParto);
        args.putSerializable(PackageCompletedFlightInternational.class.getName(), packageCompletedFlightInternational);
        args.putParcelable(RegisterPassengerResponse.class.getName(), registerPassengerResponse);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    public static FragmentFinalBookingFlightInternational newInstance(RegisterPassengerResponse registerPassengerResponse) {

        Bundle args = new Bundle();
        FragmentFinalBookingFlightInternational fragment = new FragmentFinalBookingFlightInternational();
        args.putParcelable(RegisterPassengerResponse.class.getName(), registerPassengerResponse);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    public static FragmentFinalBookingFlightInternational newInstance(RegisterPassengerResponse registerPassengerResponse,
                                                                      AllFlightInternationalIati allFlightInternationalIati,
                                                                      PackageCompletedFlightInternational packageCompletedFlightInternational) {

        Bundle args = new Bundle();
        FragmentFinalBookingFlightInternational fragment = new FragmentFinalBookingFlightInternational();
        args.putParcelable(AllFlightInternationalIati.class.getName(), allFlightInternationalIati);
        args.putSerializable(PackageCompletedFlightInternational.class.getName(), packageCompletedFlightInternational);
        args.putParcelable(RegisterPassengerResponse.class.getName(), registerPassengerResponse);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    public static FragmentFinalBookingFlightInternational newInstance(RegisterPassengerResponse registerPassengerResponse,
                                                                      FinalFlightInternationalIati finalFlightInternationalIati,
                                                                      PackageCompletedFlightInternational packageCompletedFlightInternational) {

        Bundle args = new Bundle();
        FragmentFinalBookingFlightInternational fragment = new FragmentFinalBookingFlightInternational();
        args.putParcelable(FinalFlightInternationalIati.class.getName(), finalFlightInternationalIati);
        args.putSerializable(PackageCompletedFlightInternational.class.getName(), packageCompletedFlightInternational);
        args.putParcelable(RegisterPassengerResponse.class.getName(), registerPassengerResponse);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(AllFlightInternationalIati.class.getName(), allFlightInternationalIati);
            outState.putParcelable(AllFlightInternationalParto.class.getName(), allFlightInternationalParto);
            outState.putParcelable(FinalFlightInternationalIati.class.getName(), finalFlightInternationalIati);
            outState.putSerializable(PackageCompletedFlightInternational.class.getName(), packageCompletedFlightInternational);
            outState.putParcelable(RegisterPassengerResponse.class.getName(), registerPassengerResponse);
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
            view = inflater.inflate(R.layout.fragment_service_flight_international_pre_factor, container, false);
            initialComponentFragment();
        }
        return view;
    }
    //-----------------------------------------------

    @Override
    public void onResume() {
        super.onResume();

        if (hasReserve) {
            new InternationalApi(getActivity()).hasBuyTicket(registerPassengerResponse.getRegisterPassengerData().getTicketId(), paymentPresenter);
        }
    }

    //-----------------------------------------------
    private void initialComponentFragment() {
        try {


            UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_WEB);
            internationalApi = new InternationalApi(getActivity());
            layoutButtonGetTicket = (LinearLayout) view.findViewById(R.id.layoutButtonGetTicket);
            layoutButtonPayment = (LinearLayout) view.findViewById(R.id.layoutButtonPayment);
            txtFinalPrice = (TextView) view.findViewById(R.id.txtFinalPrice);
            txtTitleFinalTicket = (TextView) view.findViewById(R.id.titleFinalTicket);
            btnBuy = (AppCompatButton) view.findViewById(R.id.btnBuy);
            btnEdit = (AppCompatButton) view.findViewById(R.id.btnEditBuy);
            btnGetTicket = (AppCompatButton) view.findViewById(R.id.btnGetTicket);
            btnExit = (AppCompatButton) view.findViewById(R.id.btnExit);

            btnGetTicket.setOnClickListener(onClickShowDatailsListener);
            btnBuy.setOnClickListener(onClickShowDatailsListener);
            btnExit.setOnClickListener(onClickShowDatailsListener);
            btnEdit.setOnClickListener(onClickShowDatailsListener);

            setupTools();
            showListPassenger();
            CheckChangePrice();
        } catch (Exception e) {

            ToastMessageBar.show(getActivity(), R.string.msgErrorPayment);
            getActivity().onBackPressed();
        }
    }

    //-----------------------------------------------
    View.OnClickListener onClickShowDatailsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btnEditBuy:
                    getActivity().onBackPressed();
                    break;
                case R.id.btnBuy:
                    showDialogReserve();
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
    private void setupTools() {
        txtFinalPrice.setText(getFinalPrice());
        TextView txtWentFlightCity = (TextView) view.findViewById(R.id.txtWentFlightCity);
        TextView txtWentFlightDateTime = (TextView) view.findViewById(R.id.txtWentFlightDateTime);
        String timeTakeOff = "";
        String dateTakeOff = "";
        ResultRegisterFlightInternationalResponse outbound = registerPassengerResponse.getRegisterPassengerData().getOutbound_();

        dateTakeOff = outbound.getDate();
        timeTakeOff = outbound.getTdate();
        String departure = outbound.getFrom();
        String arrival = outbound.getTo();
        txtWentFlightCity.setText("پرواز رفت از" + departure);
        txtWentFlightDateTime.setText("تاریخ حرکت:" + dateTakeOff + " زمان پرواز:" + timeTakeOff);
        view.findViewById(R.id.cvWentFlight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlightInternationalParto != null) {
                    RulesRequest rulesRequest = new RulesRequest(packageCompletedFlightInternational.getSearchIdParto(), String.valueOf(allFlightInternationalParto.getId()));
                    showRulesPartoWent(rulesRequest, allFlightInternationalParto);
                } else if (allFlightInternationalIati != null) {
                    RulesRequest rulesRequest = new RulesRequest(packageCompletedFlightInternational.getSearchIdIati(), String.valueOf(allFlightInternationalIati.getId()));
                    showRulesIatiWent(rulesRequest, allFlightInternationalIati);
                } else if (finalFlightInternationalIati != null) {
                    RulesRequest rulesRequest = new RulesRequest(packageCompletedFlightInternational.getSearchIdIati(), String.valueOf(finalFlightInternationalIati.getAllFlightInternationalIatiWent().getId()));
                    showRulesIatiWent(rulesRequest, finalFlightInternationalIati.getAllFlightInternationalIatiWent());
                }
//                RulesRequest rulesRequest = new RulesRequest(packageCompletedFlightInternational.getSearchIdParto(), String.valueOf(allFlightInternationalParto.getId()));
//                showRulesPartoWent(rulesRequest, allFlightInternationalParto);
            }
        });
        ResultRegisterFlightInternationalResponse return_ = registerPassengerResponse.getRegisterPassengerData().getReturn_();
        if (return_ != null) {
            view.findViewById(R.id.cvReturnFlight).setVisibility(View.VISIBLE);
            TextView txtReturnFlightCity = (TextView) view.findViewById(R.id.txtReturnFlightCity);
            TextView txtReturnFlightDateTime = (TextView) view.findViewById(R.id.txtReturnFlightDateTime);
            String timeTakeOffReturn = "";
            String dateTakeOffReturn = "";
            timeTakeOffReturn = return_.getTdate();
            dateTakeOffReturn = return_.getDate();
            String departureReturn = return_.getFrom();
            String arrivalReturn = return_.getTo();
            txtReturnFlightCity.setText("پرواز برگشت از" + departureReturn);
            txtReturnFlightDateTime.setText("تاریخ حرکت:" + dateTakeOffReturn + " زمان پرواز:" + timeTakeOffReturn);
            view.findViewById(R.id.cvReturnFlight).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (allFlightInternationalParto != null) {
                        RulesRequest rulesRequest = new RulesRequest(packageCompletedFlightInternational.getSearchIdParto(), String.valueOf(allFlightInternationalParto.getId()));
                        showRulesPartoReturn(rulesRequest, allFlightInternationalParto);
                    } else if (allFlightInternationalIati != null) {
                        RulesRequest rulesRequest = new RulesRequest(packageCompletedFlightInternational.getSearchIdIati(), String.valueOf(allFlightInternationalIati.getId()));
                        showRulesIatiReturn(rulesRequest, allFlightInternationalIati);
                    } else if (finalFlightInternationalIati != null) {
                        RulesRequest rulesRequest = new RulesRequest(packageCompletedFlightInternational.getSearchIdIati(), String.valueOf(finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getId()));
                        showRulesIatiReturn(rulesRequest, finalFlightInternationalIati.getAllFlightInternationalIatiReturn());
                    }
                    RulesRequest rulesRequest = new RulesRequest(packageCompletedFlightInternational.getSearchIdParto(), String.valueOf(allFlightInternationalParto.getId()));
                    showRulesPartoReturn(rulesRequest, allFlightInternationalParto);
                }
            });
        } else {
            view.findViewById(R.id.cvReturnFlight).setVisibility(View.GONE);

        }

    }

    //-----------------------------------------------

    private String getFinalPrice() {
        String finalPrice = "";
        try {
            finalPrice = registerPassengerResponse.getRegisterPassengerData().getSumFinalPrice();
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice) / 10);
            finalPrice = "مبلغ نهایی پرداخت:" + finalPrice + " تومان";
            return finalPrice;
        } catch (Exception e) {
            finalPrice = "مبلغ نهایی پرداخت:" + registerPassengerResponse.getRegisterPassengerData().getSumFinalPrice();
            return finalPrice + " ریال";
        }

    }

    //-----------------------------------------------
    private void showListPassenger() {
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        PassengerInfoListAdapter mAdapter = new PassengerInfoListAdapter(getActivity(), registerPassengerResponse.getRegisterPassengerData().getPassengers(), "", "", "");
        rvResult.setAdapter(mAdapter);
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
        String ticketId = registerPassengerResponse.getRegisterPassengerData().getTicketId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.PARSIAN_BANK_FLIGHT + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);

    }

    //-----------------------------------------------
    public void getTicket() {
        String ticketId = registerPassengerResponse.getRegisterPassengerData().getTicketId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.BASE_URL_MASTER + "international/pdfticket/" + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);
    }

    //-----------------------------------------------
    private void showDialogReserve() {
        if (hasReserve) {
            showPayment();
        } else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_flight_international_reserve_final_layout, null);
            UtilFonts.overrideFonts(getActivity(), dialogView, UtilFonts.IRAN_SANS_NORMAL);

            Button btnAcceptRules = (Button) dialogView.findViewById(R.id.btnAcceptRules);
            Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            btnAcceptRules.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    ReserveRequest reserveRequest = new ReserveRequest(registerPassengerResponse.getRegisterPassengerData().getSearchId(), registerPassengerResponse.getRegisterPassengerData().getTicketId());
                    internationalApi.reserveFlight(reserveRequest.toString(), reserveInternationalPresenter);
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            dialogBuilder.setView(dialogView);
            alertDialog = dialogBuilder.create();
            alertDialog.show();
        }
    }

    //-----------------------------------------------
    ReserveInternationalPresenter reserveInternationalPresenter = new ReserveInternationalPresenter() {
        @Override
        public void onStart() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage(getString(R.string.reservering));
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }
                });
            }
        }

        @Override
        public void onErrorServer() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), R.string.msgErrorServer, Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getActivity(), R.string.msgErrorInternetConnection, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        @Override
        public void onSuccessReserve() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hasReserve = true;
                        alertDialog.cancel();
                        showPayment();
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
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
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
                        progressDialog.cancel();
                    }
                });
            }
        }
    };
    //-----------------------------------------------
    private PaymentPresenter paymentPresenter = new PaymentPresenter() {
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
    };

    //-----------------------------------------------
    private void showRulesPartoWent(RulesRequest rulesRequest, AllFlightInternationalParto allFlightInternationalParto) {
        UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(),
                FragmentInternationalRouting.newInstance(allFlightInternationalParto, false, packageCompletedFlightInternational, rulesRequest));
    }

    //-----------------------------------------------
    private void showRulesPartoReturn(RulesRequest rulesRequest, AllFlightInternationalParto allFlightInternationalParto) {
        UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(),
                FragmentInternationalRouting.newInstance(allFlightInternationalParto, true, packageCompletedFlightInternational, rulesRequest));
    }

    //-----------------------------------------------
    private void showRulesIatiWent(RulesRequest rulesRequest, AllFlightInternationalIati allFlightInternationalIati) {
        UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(),
                FragmentInternationalRouting.newInstance(allFlightInternationalIati, packageCompletedFlightInternational, rulesRequest));

    }
    //-----------------------------------------------

    private void showRulesIatiReturn(RulesRequest rulesRequest, AllFlightInternationalIati allFlightInternationalIati) {
        UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(),
                FragmentInternationalRouting.newInstance(allFlightInternationalIati, packageCompletedFlightInternational, rulesRequest));
    }

    //-----------------------------------------------
    public Boolean hasBuyTicket() {
        return hasPayment;
    }

    //-----------------------------------------------
    private void CheckChangePrice() {
        try {
            long adultPriceOld = 0;
            if (allFlightInternationalParto != null) {
                adultPriceOld = allFlightInternationalParto.getAdultPrice();
            } else if (allFlightInternationalIati != null) {
                adultPriceOld = allFlightInternationalIati.getAdultPrice();
            } else if (finalFlightInternationalIati != null) {
                adultPriceOld = finalFlightInternationalIati.getPriceAdults();
            }
            Boolean statusChangePrice = false;
            long adultPriceNew = 0;

            ResultRegisterPassengerFlightInternationalResponse passengers = registerPassengerResponse.getRegisterPassengerData().getPassengers();
            for (int index = 0; index < passengers.getTypep().length; index++) {
                int pType = Integer.parseInt(passengers.getTypep()[index]);
                if (pType == FlightRules.TP_ADULTS) {
                    adultPriceNew = Long.parseLong(passengers.getFinalprice()[index]);
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
                android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                String infoFlight = registerPassengerResponse.getRegisterPassengerData().getOutbound_().getFrom() + " به "
                        + registerPassengerResponse.getRegisterPassengerData().getOutbound_().getTo();
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

