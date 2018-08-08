package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Fragment;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Adapter.PassengerDomesticListAdapter;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticFlight;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticPassengerInfo;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticRequest;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.ListModelPassengerInfoDomestic;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.PassengerInfoDomestic;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.RegisterFlightDomesticRequest;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.RegisterFlightResponse;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Presenter.DomesticApi;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Presenter.OnSelectItemPassengerDomesticListener;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Fragment.View.ActivityRegisterPassengerDomestic;
import hami.mainapp.BaseController.DividerItemDecoration;
import hami.mainapp.BaseController.ResultSearchPresenter;
import hami.mainapp.BaseNetwork.BaseConfig;
import hami.mainapp.R;
import hami.mainapp.Util.Database.DataSaver;
import hami.mainapp.Util.Keyboard;
import hami.mainapp.Util.UtilFonts;
import hami.mainapp.Util.UtilFragment;
import hami.mainapp.Util.UtilImageLoader;
import hami.mainapp.View.CheckBox;
import hami.mainapp.View.MessageBar;
import hami.mainapp.View.Progressbar.ButtonWithProgress;
import hami.mainapp.View.ToastMessageBar;
import hami.mainapp.View.Validation.ValidationClass;


public class FragmentDomesticDetails extends Fragment {

    private RelativeLayout coordinator;
    private AppCompatButton btnRegister;
    private RecyclerView rvResult;
    private PassengerDomesticListAdapter mAdapter;
    private View view;
    private DomesticRequest domesticRequest;
    private DomesticFlight domesticFlight;
    private TextView tvCountPassenger;
    private Button btnAddPassenger;
    private MessageBar messageBar;
    private static final String TAG = "FragmentDomesticDetails";
    private AlertDialog alertDialog;
    private AlertDialog alertDialogError;
    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            domesticRequest = (DomesticRequest) getArguments().getSerializable(DomesticRequest.class.getName());
            domesticFlight = (DomesticFlight) getArguments().getParcelable(DomesticFlight.class.getName());
        }

    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            domesticRequest = (DomesticRequest) savedInstanceState.getSerializable(DomesticRequest.class.getName());
            domesticFlight = savedInstanceState.getParcelable(DomesticFlight.class.getName());
        }
    }
//-----------------------------------------------

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(DomesticRequest.class.getName(), domesticRequest);
            outState.putParcelable(DomesticFlight.class.getName(), domesticFlight);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    public static FragmentDomesticDetails newInstance(DomesticFlight domesticFlight, DomesticRequest domesticRequest) {
        Bundle args = new Bundle();
        FragmentDomesticDetails fragment = new FragmentDomesticDetails();
        args.putSerializable(DomesticRequest.class.getName(), domesticRequest);
        args.putParcelable(DomesticFlight.class.getName(), domesticFlight);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_service_flight_domestic_details, container, false);
            initialComponentFragment();
        }
        return view;
    }

    //-----------------------------------------------
    @Override
    public void onResume() {
        super.onResume();
    }

    //-----------------------------------------------
    private void initialComponentFragment() {
        coordinator = (RelativeLayout) view.findViewById(R.id.coordinator);
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_BOLD);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        messageBar.setMainBackground(R.color.main_color_grey_200);
        messageBar.setTitleButton(" + " + getString(R.string.addPassenger));
        messageBar.showMessageBar(R.string.warningNoAddPassenger);
        messageBar.setCallbackButtonNewSearch(callbackMessageBar);
        tvCountPassenger = (TextView) view.findViewById(R.id.tvCountPassenger);
        btnRegister = (AppCompatButton) view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(onClickListener);
        try {
            setupPlace();
            setupAddPassenger();
            setupRecyclerView();
            updateCountPassenger();
        } catch (Exception e) {
            ToastMessageBar.show(getActivity(), R.string.msgErrorPayment);
            getActivity().onBackPressed();
        }
    }

    //-----------------------------------------------
    View.OnClickListener callbackMessageBar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int capacity = Integer.valueOf(domesticFlight.getNum());
            if (capacity > mAdapter.getItemCount()) {
                Intent intent = new Intent(getActivity(), ActivityRegisterPassengerDomestic.class);
                intent.putExtra("listPassengers", mAdapter.getListItem());
                intent.putExtra("isForeign", (domesticFlight.getInternational() == 1));
                startActivityForResult(intent, 0);
            } else {
                ToastMessageBar.show(getActivity(), R.string.msgErrorFullCapacityDomestic);
            }
        }
    };

    //-----------------------------------------------
    private void setupAddPassenger() {
        btnAddPassenger = (Button) view.findViewById(R.id.btnAddPassenger);
        btnAddPassenger.setPaintFlags(btnAddPassenger.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnAddPassenger.setOnClickListener(onClickListener);
    }

    //-----------------------------------------------
    private void setupRecyclerView() {
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new PassengerDomesticListAdapter(getActivity(), onSelectItemPassengerDomesticListener);
        String adultPrice = domesticFlight.getAdultPrice1() == null ? domesticFlight.getAdultPrice() : domesticFlight.getAdultPrice1();
        mAdapter.setConfigPrice(adultPrice, domesticFlight.getChildPrice(), domesticFlight.getInfantPrice());
        rvResult.setAdapter(mAdapter);
    }

    //-----------------------------------------------
    private void setupPlace() {
        TextView txtWentFlightCity = (TextView) view.findViewById(R.id.txtWentFlightCity);
        TextView txtWentFlightDateTime = (TextView) view.findViewById(R.id.txtWentFlightDateTime);
        ImageView imgLogoAirLine = (ImageView) view.findViewById(R.id.imgLogoAirLine);
        TextView txtAirLineAndTypeClass = (TextView) view.findViewById(R.id.txtAirLineAndTypeClass);
        TextView txtFlightNumber = (TextView) view.findViewById(R.id.txtFlightNumber);
        TextView txtFlightName = (TextView) view.findViewById(R.id.txtFlightName);
        String flightLocation = "پرواز از " + domesticRequest.getSourcePersian() + " به " + domesticRequest.getDestinationPersian();
        txtWentFlightCity.setText(flightLocation);
        txtWentFlightDateTime.setText(domesticRequest.getDepartureGoPersian() + " , " + domesticFlight.getTakeoffTime());
        txtAirLineAndTypeClass.setText(domesticFlight.getAireLineNameF() + "," + domesticFlight.getNoeF());
        txtFlightNumber.setText("شماره پرواز:" + domesticFlight.getFlightNumber());
        txtFlightName.setText(domesticFlight.getFlightName().trim());
        String url = BaseConfig.FOLDER_IMAGE_DOMESTIC_URL + domesticFlight.getAirlineCode() + ".png";
        UtilImageLoader.loadImage(getActivity(), imgLogoAirLine, url, R.drawable.flight);
    }

    //-----------------------------------------------
    private void updateCountPassenger() {
        int capacity = Integer.valueOf(domesticFlight.getNum());
        tvCountPassenger.setText("(" + mAdapter.getItemCount() + "/" + capacity + ")");
    }

    //-----------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == ActivityRegisterPassengerDomestic.RESULT_OK_EDIT_PASSENGER) {
                DomesticPassengerInfo domesticPassengerInfo = data.getParcelableExtra(DomesticPassengerInfo.class.getName());
                int index = data.getExtras().getInt(("index"));
                mAdapter.editPassenger(domesticPassengerInfo, index);
            } else if (resultCode == ActivityRegisterPassengerDomestic.RESULT_OK_ADD_PASSENGER) {
                DomesticPassengerInfo domesticPassengerInfo = data.getParcelableExtra(DomesticPassengerInfo.class.getName());
                view.findViewById(R.id.layoutAddPassenger).setVisibility(View.VISIBLE);
                messageBar.setVisibility(View.GONE);
                mAdapter.addNewPassenger(domesticPassengerInfo);
                updateCountPassenger();
            }


        } catch (Exception e) {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //-----------------------------------------------
    OnSelectItemPassengerDomesticListener onSelectItemPassengerDomesticListener = new OnSelectItemPassengerDomesticListener() {
        @Override
        public void onSelectItemFlightDomestic(DomesticPassengerInfo domesticPassengerInfo, int position) {
            Intent intent = new Intent(getActivity(), ActivityRegisterPassengerDomestic.class);
            intent.putExtra("listPassengers", mAdapter.getListItem());
            intent.putExtra(DomesticPassengerInfo.class.getName(), domesticPassengerInfo);
            intent.putExtra("index", position);
            startActivityForResult(intent, 0);
        }

        @Override
        public void onRemoveItemFlightDomestic(DomesticPassengerInfo domesticPassengerInfo, int position) {
            mAdapter.removePassenger(position);
            updateCountPassenger();
            if (mAdapter.getItemCount() == 0) {
                view.findViewById(R.id.layoutAddPassenger).setVisibility(View.GONE);
                messageBar.setVisibility(View.VISIBLE);
            }
        }
    };
    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnCancel:
                    getActivity().onBackPressed();
                    break;
                case R.id.btnRegister:
                    showDialog();
                    break;
                case R.id.btnAddPassenger:
                    int capacity = Integer.valueOf(domesticFlight.getNum());
                    if (capacity > mAdapter.getItemCount()) {
                        Intent intent = new Intent(getActivity(), ActivityRegisterPassengerDomestic.class);
                        intent.putExtra("listPassengers", mAdapter.getListItem());
                        startActivityForResult(intent, 0);
                    } else {
                        ToastMessageBar.show(getActivity(), R.string.msgErrorFullCapacityDomestic);
                    }
                    break;

            }
        }
    };

    //-----------------------------------------------
    private void showDialog() {
        if (mAdapter.getItemCount() == 0) {
            ToastMessageBar.show(getActivity(), R.string.validateNoPassenger);
            messageBar.setVibrator();
            return;
        }
        if (mAdapter.getItemCount() > 0 && !mAdapter.hasValidateChild()) {
            ToastMessageBar.show(getActivity(), R.string.validateChild);
            return;
        }
        if (mAdapter.getItemCount() > 0 && !mAdapter.hasValidateInfant()) {
            ToastMessageBar.show(getActivity(), R.string.validateInfant);
            return;
        }
        RegisterFlightDomesticRequest registerFlightDomesticRequest = new RegisterFlightDomesticRequest();
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            PassengerInfoDomestic passengerInfoDomestic = new PassengerInfoDomestic(mAdapter.getListItem().get(i));
            ListModelPassengerInfoDomestic listModelPassengerInfoDomestic = new ListModelPassengerInfoDomestic(passengerInfoDomestic);
            registerFlightDomesticRequest.addListModelPassengerInfoDomestic(listModelPassengerInfoDomestic);
        }
        registerFlightDomesticRequest.setTakeOffDatePersian(domesticRequest.getDepartureGoPersian());
        showDialogFinalPreReserve(registerFlightDomesticRequest);
    }


    //-----------------------------------------------
    public void showDialogFinalPreReserve(final RegisterFlightDomesticRequest registerFlightDomesticRequest) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_service_register_final_layout, null);

        UtilFonts.overrideFonts(getActivity(), dialogView, UtilFonts.IRAN_SANS_NORMAL);
        final EditText edtMobile = (EditText) dialogView.findViewById(R.id.edtMobile);
        final EditText edtEmail = (EditText) dialogView.findViewById(R.id.edtEmail);
        final CheckBox chkAcceptRule = (CheckBox) dialogView.findViewById(R.id.chkAcceptRule);
        chkAcceptRule.setTitle(R.string.rulesInternetBuy);
//        chkAcceptRule.setCallBackTitle(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new CustomTabsPackages(getActivity()).showUrl(RespinaConst.RULE_LINK_FLIGHT_DOMESTIC);
//            }
//        });
        DataSaver dataSaver = new DataSaver(getActivity());
        edtEmail.setText(dataSaver.getEmail());
        edtMobile.setText(dataSaver.getMobile());
        final ButtonWithProgress btnReserve = (ButtonWithProgress) dialogView.findViewById(R.id.btnRegister);
        btnReserve.setConfig(R.string.reserve, R.string.reserving, R.string.reserve);
        btnReserve.setBackgroundButton(R.drawable.bg_button_orange);

        //Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
        //btnCancel.setOnClickListener(onClickListener);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        btnReserve.setCallBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String mobile = Keyboard.convertPersianNumberToEngNumber(edtMobile.getText().toString());
                    String email = Keyboard.convertPersianNumberToEngNumber(edtEmail.getText().toString());
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
                    registerFlightDomesticRequest.setPhonNumber(mobile);
                    registerFlightDomesticRequest.setEmail(email);
                    registerFlightDomesticRequest.setCaptchaFlight("");
                    registerFlightDomesticRequest.setNumberP(String.valueOf(registerFlightDomesticRequest.getPassengerInfoDomestics().size()));
                    registerFlightDomesticRequest.setId(domesticFlight.getId());
                    registerFlightDomesticRequest.setPrice(domesticFlight.getPrice());
                    registerFlightDomesticRequest.setNum(domesticFlight.getNum());
                    registerFlightDomesticRequest.setShownum(domesticFlight.getShowNum());
                    registerFlightDomesticRequest.setAdultPrice(domesticFlight.getAdultPrice());
                    registerFlightDomesticRequest.setChildPrice(domesticFlight.getChildPrice());
                    registerFlightDomesticRequest.setInfantPrice(domesticFlight.getInfantPrice());
                    registerFlightDomesticRequest.setAirelineName(domesticFlight.getAireLineName());
                    registerFlightDomesticRequest.setLegs(domesticFlight.getLegs());
                    registerFlightDomesticRequest.setFlightTime(domesticFlight.getFlightTime());
                    registerFlightDomesticRequest.setFrom(domesticFlight.getFrom());
                    registerFlightDomesticRequest.setTo(domesticFlight.getTo());
                    registerFlightDomesticRequest.setFlightNumber(domesticFlight.getFlightNumber());
                    registerFlightDomesticRequest.setFlightName(domesticFlight.getFlightName());
                    registerFlightDomesticRequest.setTakeoffTime(domesticFlight.getTakeoffTime());
                    registerFlightDomesticRequest.setArriveTime(domesticFlight.getArriveTime());
                    registerFlightDomesticRequest.setStops(domesticFlight.getStops());
                    registerFlightDomesticRequest.setType(domesticFlight.getType());
                    registerFlightDomesticRequest.setTime(domesticFlight.getTime());
                    registerFlightDomesticRequest.setNoe(domesticFlight.getNoe());
                    registerFlightDomesticRequest.setNexday(domesticFlight.getNexDay());
                    registerFlightDomesticRequest.setPreday(domesticFlight.getPreDay());
                    registerFlightDomesticRequest.setStatus(String.valueOf(domesticFlight.getStatus()));
                    registerFlightDomesticRequest.setAirlineCode(String.valueOf(domesticFlight.getAirlineCode()));
                    registerFlightDomesticRequest.setAirelineNameF(domesticFlight.getAireLineNameF());
                    registerFlightDomesticRequest.setNoeF(domesticFlight.getNoeF());
                    registerFlightDomesticRequest.setTest("");
                    registerFlightDomesticRequest.setDaytime("");
                    registerFlightDomesticRequest.setDaytimetext("");
                    registerFlightDomesticRequest.setCount("");

                    new DomesticApi(getActivity()).registerPassenger(registerFlightDomesticRequest, new ResultSearchPresenter<RegisterFlightResponse>() {
                        @Override
                        public void onStart() {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.setCancelable(false);
                                        alertDialog.setCanceledOnTouchOutside(false);
                                        edtMobile.setEnabled(false);
                                        edtEmail.setEnabled(false);
                                        btnReserve.setEnableButton(false);
                                        chkAcceptRule.setEnabled(false);
                                        btnReserve.startProgress();

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
                                        alertDialog.cancel();
                                        if (type == 1230) {
                                            showDialogError1030();
                                        } else
                                            ToastMessageBar.show(getActivity(), R.string.msgErrorReserveFlight);
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
                                        alertDialog.cancel();
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
                                        alertDialog.cancel();
                                        ToastMessageBar.show(getActivity(), msg);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onSuccessResultSearch(final RegisterFlightResponse result) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.cancel();
                                        UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentFinalBookingFlightDomestic.newInstance(result, domesticFlight.getAdultPrice()));
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
                                        edtMobile.setEnabled(true);
                                        edtEmail.setEnabled(true);
                                        btnReserve.setEnableButton(true);
                                        chkAcceptRule.setEnabled(true);
                                        btnReserve.stopProgress();
                                    }
                                });
                            }
                        }
                    });
                } catch (Exception e) {


                    ToastMessageBar.show(getActivity(), R.string.msgErrorReserveFlight);
                }
            }
        });

        alertDialog.show();
    }

    //-----------------------------------------------
    public void showDialogError1030() {
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.include_layout_expire_time_message, null);
            dialogBuilder.setView(dialogView);
            alertDialogError = dialogBuilder.create();
            alertDialogError.setCancelable(false);
            alertDialogError.setCanceledOnTouchOutside(false);
            UtilFonts.overrideFonts(getActivity(), dialogView, UtilFonts.IRAN_SANS_BOLD);
            final TextView tvTitleCenter = (TextView) dialogView.findViewById(R.id.tvTitleCenter);
            AppCompatButton tvButtonRetry = (AppCompatButton) dialogView.findViewById(R.id.tvButtonRetry);
            AppCompatButton btnBack = (AppCompatButton) dialogView.findViewById(R.id.btnBack);
            tvTitleCenter.setText(R.string.msgErrorFullCapacityFlight);
            tvButtonRetry.setText("جستجو مجدد");
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogError.cancel();
                    getActivity().finish();
                }
            });
            tvButtonRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogError.dismiss();
                    getActivity().onBackPressed();
                    UtilFragment.changeFragment(getActivity().getSupportFragmentManager(), FragmentListWentDomestic.newInstance(domesticRequest));
                }
            });
            alertDialogError.show();
        } catch (Exception e) {

        }

    }

}

