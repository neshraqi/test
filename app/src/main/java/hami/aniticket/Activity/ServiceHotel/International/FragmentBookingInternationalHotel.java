package hami.aniticket.Activity.ServiceHotel.International;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hami.aniticket.Activity.ServiceHotel.International.Adapter.ListPassengerInternationalHotelListAdapter;
import hami.aniticket.Activity.ServiceHotel.International.Controller.InternationalHotelApi;
import hami.aniticket.Activity.ServiceHotel.International.Controller.Model.HotelDetailResponse;
import hami.aniticket.Activity.ServiceHotel.International.Controller.Model.PassengerInfoInternationalHotel;
import hami.aniticket.Activity.ServiceHotel.International.Controller.Model.RegisterPassengerInternationalHotelResponse;
import hami.aniticket.Activity.ServiceHotel.International.Controller.Model.RowTypeHotelPassenger;
import hami.aniticket.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentPresenter;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.BaseResult;
import hami.aniticket.BaseController.ResultSearchPresenter;
import hami.aniticket.BaseNetwork.BaseConfig;
import hami.aniticket.R;
import hami.aniticket.Util.CustomeChrome.CustomTabsPackages;
import hami.aniticket.Util.UtilFonts;
import hami.aniticket.View.ToastMessageBar;


public class FragmentBookingInternationalHotel extends Fragment {

    private HotelDetailResponse hotelDetailResponse;
    private RegisterPassengerInternationalHotelResponse registerPassengerInternationalHotelResponse;
    private View view;
    private TextView txtTitleFinalTicket, txtFinalPrice;
    private LinearLayout layoutButtonPayment, layoutButtonGetTicket;
    private Boolean hasReserve = false, hasPayment = false;
    private AlertDialog alertDialog;
    private InternationalHotelApi hotelApi;
    private ProgressDialog progressDialog;
    private AppCompatButton btnGetTicket, btnBuy, btnEdit, btnExit;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            hotelDetailResponse = getArguments().getParcelable(HotelDetailResponse.class.getName());
            registerPassengerInternationalHotelResponse = getArguments().getParcelable(RegisterPassengerInternationalHotelResponse.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            hasReserve = savedInstanceState.getBoolean("hasReserve");
            hasPayment = savedInstanceState.getBoolean("hasPayment");
            hotelDetailResponse = savedInstanceState.getParcelable(HotelDetailResponse.class.getName());
            registerPassengerInternationalHotelResponse = savedInstanceState.getParcelable(RegisterPassengerInternationalHotelResponse.class.getName());
        }
    }

    //-----------------------------------------------
    public static FragmentBookingInternationalHotel newInstance(HotelDetailResponse hotelDetailResponse, RegisterPassengerInternationalHotelResponse registerPassengerInternationalHotelResponse) {

        Bundle args = new Bundle();
        FragmentBookingInternationalHotel fragment = new FragmentBookingInternationalHotel();
        args.putParcelable(HotelDetailResponse.class.getName(), hotelDetailResponse);
        args.putParcelable(RegisterPassengerInternationalHotelResponse.class.getName(), registerPassengerInternationalHotelResponse);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(HotelDetailResponse.class.getName(), hotelDetailResponse);
            outState.putParcelable(RegisterPassengerInternationalHotelResponse.class.getName(), registerPassengerInternationalHotelResponse);
            outState.putBoolean("hasReserve", hasReserve);
            outState.putBoolean("hasPayment", hasPayment);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_final_booking_payment_international_hotel_layout, container, false);
            initialComponentFragment(view);
        }
        return view;
    }

    //-----------------------------------------------
    public void onResume() {
        super.onResume();
        if (hasReserve) {
            String ticketId = registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getTicket_id();
            String hashId = registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getHashId();
            new InternationalHotelApi(getActivity()).hasBuyTicket(ticketId, hashId, paymentPresenter);
        }
    }

    //-----------------------------------------------
    private void initialComponentFragment(View view) {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.TAHOMA);
        hotelApi = new InternationalHotelApi(getActivity());
        btnBuy = (AppCompatButton) view.findViewById(R.id.btnBuy);
        btnEdit = (AppCompatButton) view.findViewById(R.id.btnEditBuy);
        btnGetTicket = (AppCompatButton) view.findViewById(R.id.btnGetTicket);
        btnExit = (AppCompatButton) view.findViewById(R.id.btnExit);
        btnGetTicket.setOnClickListener(onClickListener);
        btnBuy.setOnClickListener(onClickListener);
        btnExit.setOnClickListener(onClickListener);
        btnEdit.setOnClickListener(onClickListener);
        layoutButtonGetTicket = (LinearLayout) view.findViewById(R.id.layoutButtonGetTicket);
        layoutButtonPayment = (LinearLayout) view.findViewById(R.id.layoutButtonPayment);
        txtTitleFinalTicket = (TextView) view.findViewById(R.id.titleFinalTicket);
        txtFinalPrice = (TextView) view.findViewById(R.id.txtFinalPrice);
        txtFinalPrice.setText(getFinalPrice());
        setupHotelDetail();
        setupPassengerRequest();
        setupRecyclerViewPassenger();
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
    private String getFinalPrice() {
        String finalPrice = "";
        try {
            finalPrice = registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getSumFinalPrice();
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice));
            finalPrice = "Final payment:" + finalPrice + " IRR ";
            return finalPrice;
        } catch (Exception e) {
            finalPrice = registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getSumFinalPrice();
            finalPrice = "Final payment:" + finalPrice + " IRR ";
            return finalPrice;
        }

    }

    //-----------------------------------------------
    private void setupPassengerRequest() {
        TextView txtRoomCount = (TextView) view.findViewById(R.id.txtRoomCount);
        TextView txtPassengerCount = (TextView) view.findViewById(R.id.txtPassengerCount);
        TextView txtCheckIn = (TextView) view.findViewById(R.id.txtCheckIn);
        TextView txtCheckOut = (TextView) view.findViewById(R.id.txtCheckOut);
        String roomCount = hotelDetailResponse.getHotelDetailData().getHotelSearchRequest().getRooms();
        int adultCount = Integer.parseInt(hotelDetailResponse.getHotelDetailData().getHotelSearchRequest().getAdult());
        int childCount = Integer.parseInt(hotelDetailResponse.getHotelDetailData().getHotelSearchRequest().getChild());
        txtRoomCount.setText(getString(R.string.roomEng) + ":" + roomCount);
        txtPassengerCount.setText(getString(R.string.passengerEng) + "(" + getString(R.string.adultEng) + ":" + adultCount + "," + getString(R.string.childEng) + ":" + childCount + ")");
        txtCheckIn.setText(getString(R.string.checkInDateEng) + ":" + hotelDetailResponse.getHotelDetailData().getHotelSearchRequest().getCheckin());
        txtCheckOut.setText(getString(R.string.checkOutDateEng) + ":" + hotelDetailResponse.getHotelDetailData().getHotelSearchRequest().getCheckout());
    }

    //-----------------------------------------------
    private void setupHotelDetail() {
        TextView txtHotelName = (TextView) view.findViewById(R.id.txtHotelName);
        TextView txtHotelArea = (TextView) view.findViewById(R.id.txtHotelArea);
        txtHotelName.setText(hotelDetailResponse.getHotelDetailData().getHotels().getHotelInfo().getHotelName());
        txtHotelArea.setText(hotelDetailResponse.getHotelDetailData().getHotelSearchRequest().getCityName());
    }

    //-----------------------------------------------
    private void setupRecyclerViewPassenger() {
        RecyclerView rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        ListPassengerInternationalHotelListAdapter listPassengerInternationalHotelListAdapter = new ListPassengerInternationalHotelListAdapter(getActivity(),
                getPassengerInfo());
        rvResult.setAdapter(listPassengerInternationalHotelListAdapter);
    }

    //-----------------------------------------------
    private ArrayList<RowTypeHotelPassenger> getPassengerInfo() {
        try {
            ArrayList<RowTypeHotelPassenger> result = new ArrayList<>();
            for (int index = 0; index < registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getPassengers().getNameList().size(); index++) {
                String name = registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getPassengers().getNameList().get(index);
                String family = registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getPassengers().getFamilyList().get(index);
                if (registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getPassengers().getTypePassengerList().get(index).contentEquals("1")) {
                    result.add(PassengerInfoInternationalHotel.newInstanceAdults(name, family, index));
                } else {
                    result.add(PassengerInfoInternationalHotel.newInstanceChild(name, family, index));
                }
            }
            return result;
        } catch (Exception e) {
            getActivity().onBackPressed();
            ToastMessageBar.show(getActivity(), R.string.msgErrorReserveHotelEng);
            return null;
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
        String ticketId = registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getTicket_id();
        String hashId = registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getHashId();
        String url = BaseConfig.BANK_HOTEL + ticketId + "/" + hashId;
        new CustomTabsPackages(getActivity()).showUrl(url);

    }

    //-----------------------------------------------
    public void getTicket() {
        String ticketId = registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getTicket_id();
        String hashId = registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getHashId();
        String url = BaseConfig.BASE_URL_MASTER + "internationalhotel/pdfticket/" + ticketId + "/" + hashId;
        new CustomTabsPackages(getActivity()).showUrl(url);
    }

    //-----------------------------------------------
    private void showDialogReserve() {
        if (hasReserve) {
            showPayment();
        } else {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_international_hotel_booking_final_layout, null);
            UtilFonts.overrideFonts(getActivity(), dialogView, UtilFonts.TAHOMA);

            AppCompatButton btnAcceptRules = (AppCompatButton) dialogView.findViewById(R.id.btnAcceptRules);
            AppCompatButton btnCancel = (AppCompatButton) dialogView.findViewById(R.id.btnCancel);
            btnAcceptRules.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    String ticketId = registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getTicket_id();
                    String hashId = registerPassengerInternationalHotelResponse.getRegisterPassengerInternationalHotelParams().getHashId();
                    hotelApi.reserveHotel(ticketId, hashId, reserveInternationalPresenter);
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
    private ResultSearchPresenter<BaseResult> reserveInternationalPresenter = new ResultSearchPresenter<BaseResult>() {
        @Override
        public void onStart() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage(getString(R.string.bookingInProcessEng));
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }
                });
            }
        }

        @Override
        public void onErrorServer(int error) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastMessageBar.show(getActivity(), R.string.msgErrorServerEng);
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
                        ToastMessageBar.show(getActivity(), R.string.msgErrorInternetConnectionEng);
                    }
                });
            }
        }

        @Override
        public void onSuccessResultSearch(BaseResult result) {
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
        public void noResult(int type) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastMessageBar.show(getActivity(), R.string.msgErrorReserveHotelEng);
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
    public Boolean hasBuyTicket() {
        return hasPayment;
    }
}
