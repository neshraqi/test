package com.hami.servicebus.Services.Fragment;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hami.common.BaseNetwork.BaseConfig;
import com.hami.common.Util.CustomeChrome.CustomTabsPackages;
import com.hami.common.Util.Hashing;
import com.hami.common.Util.UtilFonts;
import com.hami.common.Util.UtilImageLoader;
import com.hami.servicebus.R;
import com.hami.servicebus.Services.Controller.Model.BusTicketInformation;
import com.hami.servicebus.Services.Controller.Model.SearchBusResponse;
import com.hami.servicebus.Services.Controller.Presenter.BusApi;
import com.hami.servicebus.Services.Controller.Presenter.PaymentPresenter;
import com.hami.servicebus.View.ToolsBusChair;

import java.text.NumberFormat;
import java.util.Locale;

public class FragmentReserveBus extends Fragment {
    //-----------------------------------------------
    private RelativeLayout coordinator;
    private View view;
    private TextView txtTitleFinalTicket, txtWarningCheckInfo;
    private AppCompatButton btnGetTicket, btnBuy, btnEdit, btnExit;
    private LinearLayout chairs;
    private Boolean hasReserve = false, hasPayment = false;
    private LinearLayout layoutButtonPayment, layoutButtonGetTicket;
    private BusTicketInformation busTicketInformation;
    private SearchBusResponse searchBusResponse;
    private TextView txtCountPassenger;
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
            busTicketInformation = savedInstanceState.getParcelable(BusTicketInformation.class.getName());
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
    private void initialComponentFragment() {
        //coordinator = (RelativeLayout) view.findViewById(R.id.coordinator);
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_BOLD);
      //  txtCountPassenger = view.findViewById(R.id.txtCountPassenger);
        chairs = view.findViewById(R.id.layoutChairs);
        layoutButtonGetTicket = view.findViewById(R.id.layoutButtonGetTicket);
        layoutButtonPayment = view.findViewById(R.id.layoutButtonPayment);
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
        try {
            setupInfo();
        } catch (Exception e) {


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
    private void setupInfo() {
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
        UtilImageLoader.loadImage(getActivity(), imgLogoBusCompany, url, R.mipmap.ic_bus);
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
        txtFinalPrice.setText(getFinalPrice(busTicketInformation.getFinalPrice()));
        setupChairs(busTicketInformation);
        //edtNationalCode.setText();
    }

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
    private String getPriceByPassenger(String price) {
        String finalPrice = "";
        try {

            finalPrice = price.replace(",", "");
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice) / 10);
            finalPrice = getString(R.string.pricePerPerson) + finalPrice + " تومان";
            return finalPrice;
        } catch (Exception e) {


            finalPrice = getString(R.string.finalPriceWithDiscount) + price;
            return finalPrice + " ریال";
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
        txtWarningCheckInfo.setVisibility(View.GONE);
        getActivity().findViewById(R.id.btnBack).setVisibility(View.INVISIBLE);
    }

    //-----------------------------------------------
    public void showPayment() {
        hasReserve = true;
        String ticketId = busTicketInformation.getId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.MELLAT_BANK_BUS + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);
    }

    //-----------------------------------------------
    public void getTicket() {
        String ticketId = busTicketInformation.getId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.BASE_URL_MASTER + "bus/pdfticket/" + ticketId + "/" + hash;
        new CustomTabsPackages(getActivity()).showUrl(url);
    }

    //-----------------------------------------------
    public Boolean hasBuyTicket() {
        return hasPayment;
    }
}
