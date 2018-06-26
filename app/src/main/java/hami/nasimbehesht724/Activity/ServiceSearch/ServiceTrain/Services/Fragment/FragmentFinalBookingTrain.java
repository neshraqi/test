package hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Fragment;

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

import java.text.NumberFormat;
import java.util.Locale;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentPresenter;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Adapter.PassengerInfoLisTrainAdapter;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.RegisterTrainResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainRequest;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Presenter.TrainApi;
import hami.nasimbehesht724.BaseNetwork.BaseConfig;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.CustomeChrome.CustomTabsPackages;
import hami.nasimbehesht724.Util.Hashing;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilImageLoader;


public class FragmentFinalBookingTrain extends Fragment {
    //-----------------------------------------------
    private View view;
    private AppCompatButton btnGetTicket, btnBuy, btnEdit, btnExit;
    private Boolean hasReserve = false, hasPayment = false;
    private RecyclerView rvResult;
    private PassengerInfoLisTrainAdapter mAdapter;
    private TextView txtTitleFinalTicket, txtFinalPrice;
    private LinearLayout layoutButtonPayment, layoutButtonGetTicket;
    private RegisterTrainResponse registerTrainResponse;
    private TrainResponse trainResponse;
    private TrainRequest trainRequest;

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            registerTrainResponse = savedInstanceState.getParcelable(RegisterTrainResponse.class.getName());
            trainResponse = savedInstanceState.getParcelable(TrainResponse.class.getName());
            hasReserve = savedInstanceState.getBoolean("hasReserve");
            hasPayment = savedInstanceState.getBoolean("hasPayment");
        }
    }

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            registerTrainResponse = getArguments().getParcelable(RegisterTrainResponse.class.getName());
            trainResponse = getArguments().getParcelable(TrainResponse.class.getName());
            trainRequest = (TrainRequest) getArguments().getSerializable(TrainRequest.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(RegisterTrainResponse.class.getName(), registerTrainResponse);
            outState.putParcelable(TrainResponse.class.getName(), trainResponse);
            outState.putSerializable(TrainRequest.class.getName(), trainRequest);
            outState.putBoolean("hasReserve", hasReserve);
            outState.putBoolean("hasPayment", hasPayment);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    public static FragmentFinalBookingTrain newInstance(RegisterTrainResponse registerTrainResponse, TrainResponse trainResponse, TrainRequest trainRequest) {
        Bundle args = new Bundle();
        FragmentFinalBookingTrain fragment = new FragmentFinalBookingTrain();
        args.putParcelable(RegisterTrainResponse.class.getName(), registerTrainResponse);
        args.putParcelable(TrainResponse.class.getName(), trainResponse);
        args.putSerializable(TrainRequest.class.getName(), trainRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_service_train_factor, container, false);
            initialComponentFragment();
        }
        return view;
    }

    //-----------------------------------------------
    @Override
    public void onResume() {
        super.onResume();
        if (hasReserve) {
            new TrainApi(getActivity()).hasBuyTicket(registerTrainResponse.getTicketId(), new PaymentPresenter() {
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
    private void initialComponentFragment() {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_BOLD);
        layoutButtonGetTicket = (LinearLayout) view.findViewById(R.id.layoutButtonGetTicket);
        layoutButtonPayment = (LinearLayout) view.findViewById(R.id.layoutButtonPayment);
        txtTitleFinalTicket = (TextView) view.findViewById(R.id.titleFinalTicket);
        txtFinalPrice = (TextView) view.findViewById(R.id.txtFinalPrice);
        btnBuy = (AppCompatButton) view.findViewById(R.id.btnBuy);
        btnEdit = (AppCompatButton) view.findViewById(R.id.btnEditBuy);

        btnGetTicket = (AppCompatButton) view.findViewById(R.id.btnGetTicket);
        btnExit = (AppCompatButton) view.findViewById(R.id.btnExit);

        btnGetTicket.setOnClickListener(onClickListener);
        btnBuy.setOnClickListener(onClickListener);
        btnExit.setOnClickListener(onClickListener);
        btnEdit.setOnClickListener(onClickListener);


        setupPlace();
        setupRecyclerView();
    }

    //-----------------------------------------------
    private void setupRecyclerView() {
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PassengerInfoLisTrainAdapter(getActivity(), registerTrainResponse);
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
    private void setupPlace() {
        txtFinalPrice.setText(getFinalPrice());
        TextView txtWentFlightCity = (TextView) view.findViewById(R.id.txtWentFlightCity);
        TextView txtWentFlightDateTime = (TextView) view.findViewById(R.id.txtWentFlightDateTime);
        ImageView imgLogoAirLine = (ImageView) view.findViewById(R.id.imgLogoAirLine);
        TextView txtAirLineAndTypeClass = (TextView) view.findViewById(R.id.txtAirLineAndTypeClass);
        txtWentFlightCity.setText("سفر به " + trainRequest.getDestinationTrain());
        txtWentFlightDateTime.setText(trainRequest.getDepartureGoTrainPersian() + " , " + trainResponse.getExitTime());
        String type = "";
        if (trainResponse.getIsCompartment().contentEquals("1")) {
            type = (getText(R.string.cope) + " " + trainResponse.getCompartmentCapicity() + " " + getText(R.string.unitCountTrain));
        } else {
            type = (getText(R.string.hall) + " " + trainResponse.getCompartmentCapicity() + " " + getText(R.string.unitCountTrain));
        }
        txtAirLineAndTypeClass.setText(trainResponse.getWagonName() + "(" + type + ")");
        String url = BaseConfig.FOLDER_IMAGE_TRAIN_URL + trainResponse.getOwner().toLowerCase() + ".png";
        UtilImageLoader.loadImage(getActivity(), imgLogoAirLine, url, R.mipmap.ic_train);
    }

    //-----------------------------------------------
    private String getFinalPrice() {
        String finalPrice = "";
        try {
            finalPrice = registerTrainResponse.getViewParamsTrain().getFinalPrice();
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice) / 10);
            finalPrice = "مبلغ نهایی پرداخت:" + finalPrice + " تومان";
            return finalPrice;
        } catch (Exception e) {
            finalPrice = "مبلغ نهایی پرداخت:" + registerTrainResponse.getViewParamsTrain().getFinalPrice();
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
    }

    //-----------------------------------------------
    public void showPayment() {
        hasReserve = true;
        String ticketId = registerTrainResponse.getTicketId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.MELLAT_BANK_TRAIN + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);
    }

    //-----------------------------------------------
    public void getTicket() {
        String ticketId = registerTrainResponse.getTicketId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.BASE_URL_MASTER + "train/pdfticket/" + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);
    }

    //-----------------------------------------------
    public Boolean hasBuyTicket() {
        return hasPayment;
    }
}
