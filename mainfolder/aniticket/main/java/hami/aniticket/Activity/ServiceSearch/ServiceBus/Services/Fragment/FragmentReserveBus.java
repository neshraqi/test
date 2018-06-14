package hami.hamibelit.Activity.ServiceSearch.ServiceBus.Services.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import hami.hamibelit.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.BusTicketInformation;
import hami.hamibelit.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.SearchBusResponse;
import hami.hamibelit.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.BusApi;
import hami.hamibelit.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentPresenter;
import hami.hamibelit.Activity.ServiceSearch.ServiceBus.View.ToolsBusChair;
import hami.hamibelit.BaseNetwork.BaseConfig;
import hami.hamibelit.R;
import hami.hamibelit.Util.CustomeChrome.CustomTabsPackages;
import hami.hamibelit.Util.Hashing;
import hami.hamibelit.Util.UtilFonts;
import hami.hamibelit.Util.UtilImageLoader;
import hami.hamibelit.View.ToastMessageBar;


public class FragmentReserveBus extends Fragment {
    //-----------------------------------------------
    private RelativeLayout coordinator;
    private View view;
    private TextView txtTitleFinalTicket;
    private AppCompatButton btnGetTicket, btnBuy, btnEdit, btnExit;
    private LinearLayout chairs;
    private Boolean hasReserve = false, hasPayment = false;
    private LinearLayout layoutButtonPayment, layoutButtonGetTicket;
    private BusTicketInformation busTicketInformation;
    private SearchBusResponse searchBusResponse;
    private static final String TAG = "FragmentReserveBus";

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            busTicketInformation = getArguments().getParcelable(BusTicketInformation.class.getName());
            searchBusResponse = getArguments().getParcelable(SearchBusResponse.class.getName());

        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            hasReserve = savedInstanceState.getBoolean("hasReserve");
            hasPayment = savedInstanceState.getBoolean("hasPayment");
            busTicketInformation = (BusTicketInformation) savedInstanceState.getParcelable(BusTicketInformation.class.getName());
            searchBusResponse = savedInstanceState.getParcelable(SearchBusResponse.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putBoolean("hasReserve", hasReserve);
            outState.putBoolean("hasPayment", hasPayment);
            outState.putParcelable(BusTicketInformation.class.getName(), busTicketInformation);
            outState.putParcelable(SearchBusResponse.class.getName(), searchBusResponse);
        }
        super.onSaveInstanceState(outState);

    }

    //-----------------------------------------------
    public static FragmentReserveBus newInstance(BusTicketInformation busTicketInformation, SearchBusResponse searchBusResponse) {
        Bundle args = new Bundle();
        FragmentReserveBus fragment = new FragmentReserveBus();
        args.putParcelable(BusTicketInformation.class.getName(), busTicketInformation);
        args.putParcelable(SearchBusResponse.class.getName(), searchBusResponse);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_service_bus_pre_factor, container, false);
            initialComponentFragment();
        }
        return view;
    }
    //-----------------------------------------------

    @Override
    public void onResume() {
        super.onResume();
        if (hasReserve) {
            new BusApi(getActivity()).hasBuyTicket(busTicketInformation.getId(), new PaymentPresenter() {
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
        //coordinator = (RelativeLayout) view.findViewById(R.id.coordinator);
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_BOLD);
        chairs = (LinearLayout) view.findViewById(R.id.layoutChairs);
        layoutButtonGetTicket = (LinearLayout) view.findViewById(R.id.layoutButtonGetTicket);
        layoutButtonPayment = (LinearLayout) view.findViewById(R.id.layoutButtonPayment);
        txtTitleFinalTicket = (TextView) view.findViewById(R.id.titleFinalTicket);

        btnBuy = (AppCompatButton) view.findViewById(R.id.btnBuy);
        btnEdit = (AppCompatButton) view.findViewById(R.id.btnEditBuy);

        btnGetTicket = (AppCompatButton) view.findViewById(R.id.btnGetTicket);
        btnExit = (AppCompatButton) view.findViewById(R.id.btnExit);

        btnGetTicket.setOnClickListener(onClickListener);
        btnBuy.setOnClickListener(onClickListener);
        btnExit.setOnClickListener(onClickListener);
        btnEdit.setOnClickListener(onClickListener);
        try {
            setupInfo();
        } catch (Exception e) {
            ToastMessageBar.show(getActivity(), R.string.msgErrorPayment);
            getActivity().onBackPressed();

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
    private void setupInfo() {
        //BusTicketInformation busTicketInformation = BusWarehouse.getBusTicketInformation();

        TextView txtWentBusCity = (TextView) view.findViewById(R.id.txtWentBusCity);
        TextView txtWentBusDateTime = (TextView) view.findViewById(R.id.txtWentBusDateTime);
        TextView txtCompanyAndTypeClass = (TextView) view.findViewById(R.id.txtCompanyAndTypeClass);
        TextView txtWentBusBusType = (TextView) view.findViewById(R.id.txtWentBusBusType);
        ImageView imgLogoBusCompany = (ImageView) view.findViewById(R.id.imgLogoBusCompany);


        TextView txtFullName = (TextView) view.findViewById(R.id.txtFullname);
        TextView txtGender = (TextView) view.findViewById(R.id.txtGender);
        TextView txtNationalCode = (TextView) view.findViewById(R.id.txtCoNational);
        TextView txtPrice = (TextView) view.findViewById(R.id.txtPrice);

        TextView txtFinalPrice = (TextView) view.findViewById(R.id.txtFinalPrice);

        String url = BaseConfig.BASE_URL_MASTER + BaseConfig.FOLDER_IMAGE_BUS_URL + searchBusResponse.getImg();
        UtilImageLoader.loadImage(getActivity(), imgLogoBusCompany, url, R.drawable.bus);
        txtWentBusDateTime.setText(busTicketInformation.gettDate() + "," + busTicketInformation.gettTime1());
        txtWentBusCity.setText("سفر از " + busTicketInformation.getFrom() + " به " + busTicketInformation.getTo());
        txtCompanyAndTypeClass.setText(busTicketInformation.getCompany());
        txtPrice.setText(getFinalPrice(searchBusResponse.getPrice()));
        txtWentBusBusType.setText(busTicketInformation.getBusType());

        //txtCountPassenger.setText("تعداد مسافر:" + String.valueOf(countPassenger));
        //txtTypeBus.setText(busTicketInformation.getBusType());
        txtFullName.setText(busTicketInformation.getUserName());
        txtNationalCode.setText("کد ملی:" + busTicketInformation.getNid());
        txtGender.setText("جنسیت:" + getString(busTicketInformation.getGender()));
        txtFinalPrice.setText("قیمت نهایی:" + getFinalPrice(busTicketInformation.getFinalPrice()));
        setupChairs(busTicketInformation);
        //edtNationalCode.setText();
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
    private void setupChairs(BusTicketInformation busTicketInformation) {
        String[] chairsString = busTicketInformation.getChairs().split(",");
        for (int i = 0; i < chairsString.length; i++) {
            String number = chairsString[i].split("/")[0];
            ToolsBusChair toolsBusChair = new ToolsBusChair(getActivity(), ToolsBusChair.CHAIR_TYPE_CAN_SELECT, number);
            chairs.addView(toolsBusChair);
        }
        //chairs
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
        String ticketId = busTicketInformation.getId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.MELLAT_BANK_BUS + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//        CustomTabsIntent customTabsIntent = builder.build();
//        builder.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
//        customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
    }

    //-----------------------------------------------
    public void getTicket() {
        String ticketId = busTicketInformation.getId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.BASE_URL_MASTER + "bus/pdfticket/" + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//        CustomTabsIntent customTabsIntent = builder.build();
//        builder.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
//        customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
    }


    //-----------------------------------------------
    public Boolean hasBuyTicket() {
        return hasPayment;
    }
}
