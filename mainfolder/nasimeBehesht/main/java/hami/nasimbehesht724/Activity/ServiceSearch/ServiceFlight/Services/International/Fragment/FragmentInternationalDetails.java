package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Fragment;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.ActivityRegisterPassengerInternational;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Adapter.ListPassengerInternationalListAdapter;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.FlightInternationalRequest;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.PassengerInfo;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.RegisterPassengerRequest;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.RegisterPassengerResponse;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.AllFlightInternationalIati;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.AllFlightInternationalParto;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.FinalFlightInternationalIati;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.PackageCompletedFlightInternational;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.RulesRequest;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.InternationalApi;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.SelectItemPassengerInternational;
import hami.mainapp.BaseController.DividerItemDecoration;
import hami.mainapp.BaseController.ResultSearchPresenter;
import hami.mainapp.Const.FlightRules;
import hami.mainapp.R;
import hami.mainapp.Util.Database.DataSaver;
import hami.mainapp.Util.Keyboard;
import hami.mainapp.Util.TimeDate;
import hami.mainapp.Util.UtilFonts;
import hami.mainapp.Util.UtilFragment;
import hami.mainapp.View.CheckBox;
import hami.mainapp.View.Progressbar.ButtonWithProgress;
import hami.mainapp.View.ToastMessageBar;
import hami.mainapp.View.Validation.ValidationClass;


public class FragmentInternationalDetails extends Fragment {


    private RelativeLayout coordinator;
    private View view;
    private AppCompatButton btnRegister;
    private AllFlightInternationalParto allFlightInternationalParto;
    private FinalFlightInternationalIati finalFlightInternationalIati;
    private AllFlightInternationalIati allFlightInternationalIati;
    private ListPassengerInternationalListAdapter listPassengerInternationalListAdapter;
    private RecyclerView rvResult;
    private PackageCompletedFlightInternational packageCompletedFlightInternational;
    private Boolean hasPersian = false;
    private static final String TAG = "FragmentInternationalDetails";
    private AlertDialog alertDialog;
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
            hasPersian = savedInstanceState.getBoolean("hasPersian");
        }
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(AllFlightInternationalIati.class.getName(), allFlightInternationalIati);
            outState.putParcelable(AllFlightInternationalParto.class.getName(), allFlightInternationalParto);
            outState.putParcelable(FinalFlightInternationalIati.class.getName(), finalFlightInternationalIati);
            outState.putSerializable(PackageCompletedFlightInternational.class.getName(), packageCompletedFlightInternational);
            outState.putBoolean("hasPersian", hasPersian);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    public static FragmentInternationalDetails newInstance(
            AllFlightInternationalParto allFlightInternationalParto,
            PackageCompletedFlightInternational packageCompletedFlightInternational) {
        Bundle args = new Bundle();
        FragmentInternationalDetails fragment = new FragmentInternationalDetails();
        args.putParcelable(AllFlightInternationalParto.class.getName(), allFlightInternationalParto);
        args.putSerializable(PackageCompletedFlightInternational.class.getName(), packageCompletedFlightInternational);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    public static FragmentInternationalDetails newInstance(
            FinalFlightInternationalIati finalFlightInternationalIati,
            PackageCompletedFlightInternational packageCompletedFlightInternational) {
        Bundle args = new Bundle();
        FragmentInternationalDetails fragment = new FragmentInternationalDetails();
        args.putParcelable(FinalFlightInternationalIati.class.getName(), finalFlightInternationalIati);
        args.putSerializable(PackageCompletedFlightInternational.class.getName(), packageCompletedFlightInternational);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    public static FragmentInternationalDetails newInstance(
            AllFlightInternationalIati allFlightInternationalIati,
            PackageCompletedFlightInternational packageCompletedFlightInternational) {
        Bundle args = new Bundle();
        FragmentInternationalDetails fragment = new FragmentInternationalDetails();
        args.putParcelable(AllFlightInternationalIati.class.getName(), allFlightInternationalIati);
        args.putSerializable(PackageCompletedFlightInternational.class.getName(), packageCompletedFlightInternational);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_service_flight_international_details, container, false);
            initialComponentFragment();
        }
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment() {
        coordinator = (RelativeLayout) view.findViewById(R.id.coordinator);
        UtilFonts.overrideFonts(getActivity(), coordinator, UtilFonts.IRAN_SANS_WEB);
        btnRegister = (AppCompatButton) view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(onClickShowDatailsListener);
        try {
            setupRecyclerView();
            setupTools();
        } catch (Exception e) {

            ToastMessageBar.show(getActivity(), R.string.msgErrorPayment);
            getActivity().onBackPressed();
        }


    }

    //-----------------------------------------------
    private void setupTools() {
        TextView tvCountPassenger = (TextView) view.findViewById(R.id.tvCountPassenger);
        tvCountPassenger.setText(getString(R.string.registerContactInfoPassenger) + "(" + getPassengerInfo().size() + ")");
        TextView txtWentFlightCity = (TextView) view.findViewById(R.id.txtWentFlightCity);
        TextView txtWentFlightDateTime = (TextView) view.findViewById(R.id.txtWentFlightDateTime);
        String timeDateTakeOff = "";
        if (allFlightInternationalParto != null) {
            hasPersian = allFlightInternationalParto.getNoe() == 3 ? true : false;
            int lastIndexWent = allFlightInternationalParto.getLegs().get(0).getListLegs().size() - 1;
            timeDateTakeOff = allFlightInternationalParto.getLegs().get(0).getListLegs().get(0).getDepartureDateTime();
            String departure = allFlightInternationalParto.getLegs().get(0).getListLegs().get(0).getDepartureAirportLocationCode();
            String arrival = allFlightInternationalParto.getLegs().get(0).getListLegs().get(lastIndexWent).getArrivalAirportLocationCode();
            txtWentFlightCity.setText("پرواز رفت از" + departure + " به " + arrival);
            txtWentFlightDateTime.setText("تاریخ حرکت:" + TimeDate.getDate(null, timeDateTakeOff) + " ساعت حرکت:" + TimeDate.getTime(null, timeDateTakeOff));
            view.findViewById(R.id.cvWentFlight).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RulesRequest rulesRequest = new RulesRequest(packageCompletedFlightInternational.getSearchIdParto(), String.valueOf(allFlightInternationalParto.getId()));
                    showRulesPartoWent(rulesRequest, allFlightInternationalParto);
                }
            });
            if (allFlightInternationalParto.getLegs().size() == 2) {
                int lastIndexWentReturn = allFlightInternationalParto.getLegs().get(1).getListLegs().size() - 1;
                TextView txtReturnFlightCity = (TextView) view.findViewById(R.id.txtReturnFlightCity);
                TextView txtReturnFlightDateTime = (TextView) view.findViewById(R.id.txtReturnFlightDateTime);
                //ImageView btnMoreReturnFlight = (ImageView) view.findViewById(R.id.btnMoreReturnFlight);
                String timeDateTakeOffReturn = allFlightInternationalParto.getLegs().get(1).getListLegs().get(0).getDepartureDateTime();
                String departureReturn = allFlightInternationalParto.getLegs().get(1).getListLegs().get(0).getDepartureAirportLocationCode();
                String arrivalReturn = allFlightInternationalParto.getLegs().get(1).getListLegs().get(lastIndexWentReturn).getArrivalAirportLocationCode();
                txtReturnFlightCity.setText("پرواز برگشت از" + departureReturn + " به " + arrivalReturn);
                txtReturnFlightDateTime.setText("تاریخ حرکت:" + TimeDate.getDate(null, timeDateTakeOffReturn) + " ساعت حرکت:" + TimeDate.getTime(null, timeDateTakeOffReturn));
                view.findViewById(R.id.cvReturnFlight).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RulesRequest rulesRequest = new RulesRequest(packageCompletedFlightInternational.getSearchIdParto(), String.valueOf(allFlightInternationalParto.getId()));
                        showRulesPartoReturn(rulesRequest, allFlightInternationalParto);
                    }
                });
            } else {
                view.findViewById(R.id.cvReturnFlight).setVisibility(View.GONE);

            }
        } else if (allFlightInternationalIati != null) {
            hasPersian = allFlightInternationalIati.getNoe() == 3 ? true : false;
            int lastIndexWent = allFlightInternationalIati.getLegsList().size() - 1;
            timeDateTakeOff = allFlightInternationalIati.getLegsList().get(0).getDepartureTime();
            String departure = allFlightInternationalIati.getLegsList().get(0).getDepartureAirport();
            String arrival = allFlightInternationalIati.getLegsList().get(lastIndexWent).getArrivalAirport();
            txtWentFlightCity.setText("پرواز رفت از" + departure + " به " + arrival);
            txtWentFlightDateTime.setText("تاریخ حرکت:" + TimeDate.getDate(null, timeDateTakeOff) + " ساعت حرکت:" + TimeDate.getTime(null, timeDateTakeOff));
            view.findViewById(R.id.cvReturnFlight).setVisibility(View.GONE);
            view.findViewById(R.id.cvWentFlight).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RulesRequest rulesRequest = new RulesRequest(packageCompletedFlightInternational.getSearchIdIati(), String.valueOf(allFlightInternationalIati.getId()));
                    showRulesIatiWent(rulesRequest, allFlightInternationalIati);
                }
            });
        } else if (finalFlightInternationalIati != null) {
            hasPersian = (finalFlightInternationalIati.getAllFlightInternationalIatiWent().getNoe() == 3 || finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getNoe() == 3) ? true : false;
            int lastIndexWent = finalFlightInternationalIati.getAllFlightInternationalIatiWent().getLegsList().size() - 1;
            int lastIndexReturn = finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getLegsList().size() - 1;
            timeDateTakeOff = finalFlightInternationalIati.getAllFlightInternationalIatiWent().getLegsList().get(0).getDepartureTime();
            String departure = finalFlightInternationalIati.getAllFlightInternationalIatiWent().getLegsList().get(0).getDepartureAirport();
            String arrival = finalFlightInternationalIati.getAllFlightInternationalIatiWent().getLegsList().get(lastIndexWent).getArrivalAirport();
            txtWentFlightCity.setText("پرواز رفت از" + departure + " به " + arrival);
            txtWentFlightDateTime.setText("تاریخ حرکت:" + TimeDate.getDate(null, timeDateTakeOff) + " ساعت حرکت:" + TimeDate.getTime(null, timeDateTakeOff));
            TextView txtReturnFlightCity = (TextView) view.findViewById(R.id.txtReturnFlightCity);
            TextView txtReturnFlightDateTime = (TextView) view.findViewById(R.id.txtReturnFlightDateTime);
            //ImageView btnMoreReturnFlight = (ImageView) view.findViewById(R.id.btnMoreReturnFlight);
            //int lastIndexWentReturn = finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getLegsList().size() - 1;
            String timeDateTakeOffReturn = finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getLegsList().get(0).getDepartureTime();
            String departureReturn = finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getLegsList().get(0).getDepartureAirport();
            String arrivalReturn = finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getLegsList().get(lastIndexReturn).getArrivalAirport();
            txtReturnFlightCity.setText("پرواز برگشت از" + departureReturn + " به " + arrivalReturn);
            txtReturnFlightDateTime.setText("تاریخ حرکت:" + TimeDate.getDate(null, timeDateTakeOffReturn) + " ساعت حرکت:" + TimeDate.getTime(null, timeDateTakeOffReturn));
            view.findViewById(R.id.cvReturnFlight).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RulesRequest rulesRequest = new RulesRequest(packageCompletedFlightInternational.getSearchIdIati(), String.valueOf(finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getId()));
                    showRulesIatiReturn(rulesRequest, finalFlightInternationalIati.getAllFlightInternationalIatiReturn());
                }
            });
            view.findViewById(R.id.cvWentFlight).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RulesRequest rulesRequest = new RulesRequest(packageCompletedFlightInternational.getSearchIdIati(), String.valueOf(finalFlightInternationalIati.getAllFlightInternationalIatiWent().getId()));
                    showRulesIatiReturn(rulesRequest, finalFlightInternationalIati.getAllFlightInternationalIatiWent());
                }
            });


        }
    }

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
    View.OnClickListener onClickShowDatailsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnRegister:
                    registerPassenger();
                    break;
            }
        }
    };

    //-----------------------------------------------
    private void registerPassenger() {
        Keyboard.closeKeyboard(getActivity());
        if (listPassengerInternationalListAdapter.validateInfoPassenger()) {
            showPreFactor();
        }
    }

    //-----------------------------------------------
    private void setupRecyclerView() {
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        listPassengerInternationalListAdapter = new ListPassengerInternationalListAdapter(getActivity(),
                getPriceList(),
                getPassengerInfo(),
                selectItemPassengerInternational);
        rvResult.setAdapter(listPassengerInternationalListAdapter);

    }

    //-----------------------------------------------
    private ArrayList<String> getPriceList() {
        ArrayList<String> listPrice = new ArrayList<>();
        try {
            if (allFlightInternationalParto != null) {
                listPrice.add(String.valueOf(allFlightInternationalParto.getAdultPrice()));
                listPrice.add(String.valueOf(allFlightInternationalParto.getChildPrice()));
                listPrice.add(String.valueOf(allFlightInternationalParto.getInfantPrice()));
            } else if (allFlightInternationalIati != null) {
                listPrice.add(String.valueOf(allFlightInternationalIati.getAdultPrice()));
                listPrice.add(String.valueOf(allFlightInternationalIati.getChildPrice()));
                listPrice.add(String.valueOf(allFlightInternationalIati.getInfantPrice()));
            } else if (finalFlightInternationalIati != null) {
                listPrice.add(String.valueOf(finalFlightInternationalIati.getPriceAdults()));
                listPrice.add(String.valueOf(finalFlightInternationalIati.getPriceChild()));
                listPrice.add(String.valueOf(finalFlightInternationalIati.getPriceInfant()));
            }
            return listPrice;
        } catch (Exception e) {

            return listPrice;
        }
    }

    //-----------------------------------------------
    SelectItemPassengerInternational selectItemPassengerInternational = new SelectItemPassengerInternational() {
        @Override
        public void onSelectItem(PassengerInfo passengerInfo, int index) {
            Intent intent = new Intent(getActivity(), ActivityRegisterPassengerInternational.class);
            intent.putExtra(PassengerInfo.class.getName(), passengerInfo);
            intent.putExtra("index", index);
            if (hasPersian) {
                intent.putExtra("hasPersian", hasPersian);
            }
            startActivityForResult(intent, 1);
        }
    };


    //-----------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            PassengerInfo passengerInfo = (PassengerInfo) data.getParcelableExtra(PassengerInfo.class.getName());
            passengerInfo.setHasValidate(-1);
            int index = data.getExtras().getInt(("index"));
            listPassengerInternationalListAdapter.update(index, passengerInfo);
        } catch (Exception e) {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //-----------------------------------------------
    private ArrayList<PassengerInfo> getPassengerInfo() {
        FlightInternationalRequest flightRequest = packageCompletedFlightInternational.getFlightInternationalRequest();
        ArrayList<PassengerInfo> listPassengerInfo = new ArrayList<>();

        for (int i = 0; i < flightRequest.getAdultInt(); i++) {
            listPassengerInfo.add(new PassengerInfo(getActivity(), String.valueOf(FlightRules.TP_ADULTS)));
        }
        for (int i = 0; i < flightRequest.getChildInte(); i++) {
            listPassengerInfo.add(new PassengerInfo(getActivity(), String.valueOf(FlightRules.TP_CHILDREN)));
        }
        for (int i = 0; i < flightRequest.getInfantInt(); i++) {
            listPassengerInfo.add(new PassengerInfo(getActivity(), String.valueOf(FlightRules.TP_INFANT)));
        }
        return listPassengerInfo;
    }

    //-----------------------------------------------
    private void showPreFactor() {
        try {
            if (listPassengerInternationalListAdapter != null && listPassengerInternationalListAdapter.getItemCount() > 0) {
                showDialogFinalPreReserve();
            } else {
                ToastMessageBar.show(getActivity(), R.string.callSupport);
            }
        } catch (Exception e) {

            ToastMessageBar.show(getActivity(), R.string.callSupport);
        }

    }


    //-----------------------------------------------
    public void showDialogFinalPreReserve() {
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
//                new CustomTabsPackages(getActivity()).showUrl(RespinaConst.RULE_LINK_FLIGHT_INTERNATIONAL);
//            }
//        });
        DataSaver dataSaver = new DataSaver(getActivity());
        edtEmail.setText(dataSaver.getEmail());
        edtMobile.setText(dataSaver.getMobile());
        final ButtonWithProgress btnReserve = (ButtonWithProgress) dialogView.findViewById(R.id.btnRegister);
        btnReserve.setConfig(R.string.reserve, R.string.reserving, R.string.reserve);
        btnReserve.setBackgroundButton(R.drawable.bg_button_orange);
        final RegisterPassengerRequest registerPassengerRequest = new RegisterPassengerRequest(listPassengerInternationalListAdapter.getListPassenger());

        if (allFlightInternationalParto != null) {
            registerPassengerRequest.setIdSearch(packageCompletedFlightInternational.getSearchIdParto());
            registerPassengerRequest.setIdWent(String.valueOf(allFlightInternationalParto.getId()));
            registerPassengerRequest.setFlightNumber(allFlightInternationalParto.getLegs().get(0).getListLegs().get(0).getFlightNumber());
            registerPassengerRequest.setType(String.valueOf(allFlightInternationalParto.getNoe()));
            registerPassengerRequest.setClass_("undefined");
        } else if (allFlightInternationalIati != null) {
            registerPassengerRequest.setIdSearch(packageCompletedFlightInternational.getSearchIdIati());
            registerPassengerRequest.setIdWent(String.valueOf(allFlightInternationalIati.getId()));
            registerPassengerRequest.setFlightNumber(allFlightInternationalIati.getLegsList().get(0).getFlightNo());
            registerPassengerRequest.setType(String.valueOf(allFlightInternationalIati.getNoe()));
            registerPassengerRequest.setClass_("undefined");
        } else if (finalFlightInternationalIati != null) {
            registerPassengerRequest.setIdSearch(packageCompletedFlightInternational.getSearchIdIati());
            registerPassengerRequest.setIdWent(String.valueOf(finalFlightInternationalIati.getAllFlightInternationalIatiWent().getId()));
            registerPassengerRequest.setIdReturn(String.valueOf(finalFlightInternationalIati.getAllFlightInternationalIatiReturn().getId()));
            registerPassengerRequest.setFlightNumber(finalFlightInternationalIati.getAllFlightInternationalIatiWent().getLegsList().get(0).getFlightNo());
            registerPassengerRequest.setType(String.valueOf(finalFlightInternationalIati.getAllFlightInternationalIatiWent().getNoe()));
            registerPassengerRequest.setClass_("undefined");
        }


        registerPassengerRequest.setSecCode("noSecCode");
        registerPassengerRequest.setNumberP(String.valueOf(listPassengerInternationalListAdapter.getItemCount()));
        //registerPassengerRequest.setType("1");
        //registerPassengerRequest.setClass_(packageCompletedFlightInternational.getFlightInternationalRequest().getCabinType());
        registerPassengerRequest.setAirType("سیستمی");
        registerPassengerRequest.setFrom(packageCompletedFlightInternational.getFlightInternationalRequest().getOrigin());
        registerPassengerRequest.setTo(packageCompletedFlightInternational.getFlightInternationalRequest().getDestination());
        registerPassengerRequest.setDate(packageCompletedFlightInternational.getFlightInternationalRequest().getDate1());
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();

        btnReserve.setCallBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String mobile = Keyboard.convertPersianNumberToEngNumber(edtMobile.getText().toString());
                    String email = Keyboard.convertPersianNumberToEngNumber(edtEmail.getText().toString());
                    registerPassengerRequest.setCellPhone(mobile);
                    registerPassengerRequest.setEmail(email);
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


                    new InternationalApi(getActivity()).registerPassenger(registerPassengerRequest, new ResultSearchPresenter<RegisterPassengerResponse>() {
                        @Override
                        public void onStart() {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.setCancelable(false);
                                        alertDialog.setCanceledOnTouchOutside(false);
                                        edtEmail.setEnabled(false);
                                        edtMobile.setEnabled(false);
                                        btnReserve.setEnableButton(false);
                                        chkAcceptRule.setEnabled(false);
                                        btnReserve.startProgress();

                                    }
                                });
                            }
                        }

                        @Override
                        public void onErrorServer(int type) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.cancel();
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
                        public void onSuccessResultSearch(final RegisterPassengerResponse result) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.cancel();
                                        if (allFlightInternationalParto != null) {
                                            UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(),
                                                    FragmentFinalBookingFlightInternational.newInstance(
                                                            result, allFlightInternationalParto, packageCompletedFlightInternational)
                                            );
                                        } else if (allFlightInternationalIati != null) {
                                            UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(),
                                                    FragmentFinalBookingFlightInternational.newInstance(
                                                            result, allFlightInternationalIati, packageCompletedFlightInternational)
                                            );
                                        } else if (finalFlightInternationalIati != null) {
                                            UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(),
                                                    FragmentFinalBookingFlightInternational.newInstance(
                                                            result, finalFlightInternationalIati, packageCompletedFlightInternational)
                                            );
                                        }

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
                                        edtEmail.setEnabled(true);
                                        edtMobile.setEnabled(true);
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (alertDialog != null)
                alertDialog.cancel();
        } catch (Exception e) {

        }
    }
}

