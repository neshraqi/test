package hami.mainapp.bus.Services.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import com.hami.common.BaseController.ResultSearchPresenter;
import com.hami.common.Util.BaseDatabase.DataSaver;
import com.hami.common.Util.Keyboard;
import com.hami.common.Util.UtilFonts;
import com.hami.common.Util.UtilFragment;
import com.hami.common.Util.ValidateNationalCode;
import com.hami.common.View.CheckBox;
import com.hami.common.View.HeaderBar;
import com.hami.common.View.MessageBar;
import com.hami.common.View.Progressbar.ButtonWithProgress;
import com.hami.common.View.ToastMessageBar;
import com.hami.common.View.Validation.ValidationClass;


import hami.mainapp.R;
import hami.mainapp.bus.Services.Controller.Model.BusTicketInformation;
import hami.mainapp.bus.Services.Controller.Model.RegisterBusRequest;
import hami.mainapp.bus.Services.Controller.Model.SearchBusRequest;
import hami.mainapp.bus.Services.Controller.Model.SearchBusResponse;
import hami.mainapp.bus.Services.Controller.Model.SeatResponse;
import hami.mainapp.bus.Services.Controller.Model.SeatRow;
import hami.mainapp.bus.Services.Controller.Presenter.BusApi;
import hami.mainapp.bus.Services.Controller.Presenter.SeatChairBusPresenter;
import hami.mainapp.bus.View.SelectChairListener;
import hami.mainapp.bus.View.ToolsBusChair;
import hami.mainapp.flight.Services.International.Controller.Model.DataPassengerInfo;
import hami.mainapp.flight.Services.International.Controller.Presenter.InternationalApi;
import hami.mainapp.flight.Services.International.Controller.Presenter.NationalCodePresenter;


import java.util.ArrayList;


public class FragmentListChairBus extends Fragment {


    private RelativeLayout coordinator;
    private View view;
    private ProgressDialog progressDialog;
    private ArrayList<String> chairsSelected;
    private AppCompatButton btnContinue;
    private SearchBusResponse searchBusResponse;
    private SearchBusRequest searchBusRequest;
    private MessageBar messageBar;
    private HeaderBar headerBar;
    private int gender = RegisterBusRequest.TYPE_MALE;
    private static final String TAG = "FragmentListChairBus";
    private AlertDialog alertDialog;
    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            searchBusRequest = (SearchBusRequest) getArguments().getSerializable(SearchBusRequest.class.getName());
            searchBusResponse = getArguments().getParcelable(SearchBusResponse.class.getName());

        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            searchBusRequest = (SearchBusRequest) savedInstanceState.getSerializable(SearchBusRequest.class.getName());
            searchBusResponse = savedInstanceState.getParcelable(SearchBusResponse.class.getName());
            chairsSelected = savedInstanceState.getStringArrayList("chairs");
        }
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(SearchBusRequest.class.getName(), searchBusRequest);
            outState.putParcelable(SearchBusResponse.class.getName(), searchBusResponse);
            outState.putStringArrayList("chairs", chairsSelected);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    public static FragmentListChairBus newInstance(SearchBusResponse searchBusResponse, SearchBusRequest searchBusRequest) {
        Bundle args = new Bundle();
        FragmentListChairBus fragment = new FragmentListChairBus();
        args.putParcelable(SearchBusResponse.class.getName(), searchBusResponse);
        args.putSerializable(SearchBusRequest.class.getName(), searchBusRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_service_bus_select_chair, container, false);
        initialComponentFragment();
        return view;
    }




    //-----------------------------------------------
    private void initialComponentFragment() {
        coordinator = (RelativeLayout) view.findViewById(R.id.coordinator);
        UtilFonts.overrideFonts(getActivity(), coordinator, UtilFonts.IRAN_SANS_NORMAL);
        headerBar = (HeaderBar) view.findViewById(R.id.headerBar);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
        btnContinue = (AppCompatButton) view.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(onClickListener);
        getChairList();

    }


    //-----------------------------------------------
    View.OnClickListener callbackMessageBaClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().finish();
        }
    };
    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.btnContinue) {
                reservingPassenger();

            } else if (i == R.id.btnCancel) {
                getActivity().onBackPressed();

            }
        }
    };

    //-----------------------------------------------
    public void getChairList() {
        chairsSelected = new ArrayList<>();
        new BusApi(getActivity()).getListSeat(searchBusResponse.getId(), searchBusResponse.getSearchId(), seatChairBusPresenter);
    }

    //-----------------------------------------------
    SeatChairBusPresenter seatChairBusPresenter = new SeatChairBusPresenter() {
        @Override
        public void onStart() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        headerBar.showMessageBar(R.string.gettingInfo);
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage(getString(R.string.registeringInfo));
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
                        messageBar.showMessageBar(R.string.msgErrorServer);
                        headerBar.showMessageBar(R.string.msgErrorServer);
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
                        messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                        headerBar.showMessageBar(R.string.msgErrorInternetConnection);
                    }
                });
            }
        }

        @Override
        public void onSuccessResultSearch(final SeatResponse result) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupChairList(result);
                    }
                });
            }
        }

        @Override
        public void noBus() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorServer);
                        headerBar.showMessageBar(R.string.msgErrorServer);
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
                        messageBar.showMessageBar(msg);
                        headerBar.showMessageBar(msg);
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
                        progressDialog.dismiss();
                    }
                });
            }
        }
    };

    //-----------------------------------------------
    private void setupChairList(SeatResponse seatResponse) {
        headerBar.showMessageBar(R.string.msgErrorNoSelectChairBus);
        LinearLayout row1, row2, row3, row4;
        row1 = (LinearLayout) getView().findViewById(R.id.layoutBusRow1);
        row2 = (LinearLayout) getView().findViewById(R.id.layoutBusRow2);
        row3 = (LinearLayout) getView().findViewById(R.id.layoutBusRow3);
        row4 = (LinearLayout) getView().findViewById(R.id.layoutBusRow4);
        //-----------------------------------------------
        for (int i = 0; i < seatResponse.getSeatData().getRow1().size(); i++) {
            SeatRow seatRow = seatResponse.getSeatData().getRow1().get(i);
            ToolsBusChair toolsBusChair = new ToolsBusChair(getActivity(), seatRow.getStatus(), seatRow.getChairNumber());
            toolsBusChair.setSelectChairCallBack(selectChairListener);
            row1.addView(toolsBusChair);
            if (seatRow.getChairNumber().contentEquals("0")) {
                toolsBusChair.setVisibility(View.INVISIBLE);
            } else {
                toolsBusChair.setVisibility(View.VISIBLE);
            }
        }
        for (int i = 0; i < seatResponse.getSeatData().getRow2().size(); i++) {
            SeatRow seatRow = seatResponse.getSeatData().getRow2().get(i);
            ToolsBusChair toolsBusChair = new ToolsBusChair(getActivity(), seatRow.getStatus(), seatRow.getChairNumber());
            toolsBusChair.setSelectChairCallBack(selectChairListener);
            row2.addView(toolsBusChair);
            if (seatRow.getChairNumber().contentEquals("0")) {
                toolsBusChair.setVisibility(View.INVISIBLE);
            }
        }
        for (int i = 0; i < seatResponse.getSeatData().getRow3().size(); i++) {
            SeatRow seatRow = seatResponse.getSeatData().getRow3().get(i);
            ToolsBusChair toolsBusChair = new ToolsBusChair(getActivity(), seatRow.getStatus(), seatRow.getChairNumber());
            toolsBusChair.setSelectChairCallBack(selectChairListener);
            row3.addView(toolsBusChair);

            if (seatRow.getChairNumber().contentEquals("0")) {
                toolsBusChair.setVisibility(View.INVISIBLE);
            }
        }
        if (seatResponse.getCol() != null && Integer.valueOf(seatResponse.getCol()) == 4) {

            row4.setVisibility(View.VISIBLE);
            for (int i = 0; i < seatResponse.getSeatData().getRow4().size(); i++) {
                SeatRow seatRow = seatResponse.getSeatData().getRow4().get(i);
                ToolsBusChair toolsBusChair = new ToolsBusChair(getActivity(), seatRow.getStatus(), seatRow.getChairNumber());
                toolsBusChair.setSelectChairCallBack(selectChairListener);
                row4.addView(toolsBusChair);

                if (seatRow.getChairNumber().contentEquals("0")) {
                    toolsBusChair.setVisibility(View.INVISIBLE);
                }
            }
        } else {
            row4.setVisibility(View.GONE);
        }


    }

    //-----------------------------------------------
    SelectChairListener selectChairListener = new SelectChairListener() {
        @Override
        public void onSelectChair(String chairNumber) {
            chairsSelected.add(chairNumber);
        }

        @Override
        public void onDeSelectChair(String chairNumber) {
            chairsSelected.remove(chairNumber);
        }
    };

    //-----------------------------------------------
    private void reservingPassenger() {
        if (chairsSelected == null || chairsSelected.size() == 0) {
            ToastMessageBar.show(getActivity(), R.string.msgErrorNoSelectChairBus);
            return;
        }
        showDialogFinalPreReserve();
    }

    //-----------------------------------------------
    public void showDialogFinalPreReserve() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_service_bus_register_passenger_layout, null);
        UtilFonts.overrideFonts(getActivity(), dialogView, UtilFonts.IRAN_SANS_NORMAL);
        final LinearLayout layoutMainInput = (LinearLayout) dialogView.findViewById(R.id.layoutMainInput);
        final EditText edtMobile = (EditText) dialogView.findViewById(R.id.edtMobile);
        final EditText edtEmail = (EditText) dialogView.findViewById(R.id.edtEmail);
        final EditText edtGender = (EditText) dialogView.findViewById(R.id.edtGender);
        final EditText edtFirstName = (EditText) dialogView.findViewById(R.id.edtFName);
        final EditText edtLastName = (EditText) dialogView.findViewById(R.id.edtLName);
        final EditText edtNationalCode = (EditText) dialogView.findViewById(R.id.edtNationalCode);
        final CheckBox chkAcceptRule = (CheckBox) dialogView.findViewById(R.id.chkAcceptRule);
        edtGender.setText(R.string.male);
        edtGender.setFocusable(false);
        edtGender.setCursorVisible(false);
        edtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), edtGender);
                //popupMenu.inflate(R.menu.menu_gender);
                popupMenu.getMenu().add(Menu.NONE, 1, 1, getString(R.string.male));
                popupMenu.getMenu().add(Menu.NONE, 2, 2, getString(R.string.female));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        edtGender.setText(item.getTitle());
                        if (item.getItemId() == 1)
                            gender = RegisterBusRequest.TYPE_MALE;
                        else
                            gender = RegisterBusRequest.TYPE_FEMALE;
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        chkAcceptRule.setTitle(R.string.rulesInternetBuy);
//        chkAcceptRule.setCallBackTitle(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new CustomTabsPackages(getActivity()).showUrl(RespinaConst.RULE_LINK_BUS);
//            }
//        });
        DataSaver dataSaver = new DataSaver(getActivity());
        edtEmail.setText(dataSaver.getEmail());
        edtMobile.setText(dataSaver.getMobile());
        edtNationalCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 10) {
                    new InternationalApi(getActivity()).getInfoByNationalCode(s.toString(), new NationalCodePresenter() {
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
                        public void onSuccessDataPassengerInfo(final DataPassengerInfo dataPassengerInfo) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            edtFirstName.setText(dataPassengerInfo.getPassengerNamePersian());
                                            edtLastName.setText(dataPassengerInfo.getPassengerFamilyPersian());
                                            if (dataPassengerInfo.getGender().contentEquals("1")) {
                                                gender = RegisterBusRequest.TYPE_MALE;
                                                edtGender.setText(R.string.male);
                                            } else {
                                                gender = RegisterBusRequest.TYPE_FEMALE;
                                                edtGender.setText(R.string.female);
                                            }
                                        } catch (Exception e) {


                                        }


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

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final ButtonWithProgress btnReserve = (ButtonWithProgress) dialogView.findViewById(R.id.btnRegister);
        btnReserve.setConfig(R.string.reserve, R.string.reserving, R.string.reserve);
        btnReserve.setBackgroundButton(R.drawable.bg_button_orange);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        btnReserve.setCallBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    edtMobile.setText(Keyboard.convertPersianNumberToEngNumber(edtMobile.getText().toString()));
                    edtEmail.setText(Keyboard.convertPersianNumberToEngNumber(edtEmail.getText().toString()));
                    if (!ValidateNationalCode.isValidNationalCode(edtNationalCode.getText().toString())) {
                        ToastMessageBar.show(getActivity(), R.string.validateNationalCode);
                        Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                        edtNationalCode.startAnimation(vibrateAnimation);
                        edtNationalCode.requestFocus();
                        return;
                    }
                    if (edtFirstName.length() == 0) {
                        ToastMessageBar.show(getActivity(), R.string.validateFirstNamePersian);
                        Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                        edtFirstName.startAnimation(vibrateAnimation);
                        return;
                    }
                    if (edtLastName.length() == 0) {
                        ToastMessageBar.show(getActivity(), R.string.validateLastNamePersian);
                        Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                        edtLastName.startAnimation(vibrateAnimation);
                        return;
                    }
                    if (edtMobile.length() >= 0 && edtMobile.length() < 10) {
                        ToastMessageBar.show(getActivity(), R.string.validateMobile);
                        Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                        edtMobile.startAnimation(vibrateAnimation);
                        return;
                    }
//                    if (edtEmail.length() >= 0 && edtEmail.length() == 0) {
//                        ToastMessageBar.show(getActivity(), R.string.validateEmail);
//                        Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
//                        edtEmail.startAnimation(vibrateAnimation);
//                        return;
//                    }
                    if (!ValidationClass.validateEmailEditTextToast(getActivity(), edtEmail, R.string.validateEmail)) {
                        Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                        edtEmail.startAnimation(vibrateAnimation);
                        return;
                    }
                    if (!chkAcceptRule.hasCheck()) {
                        Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                        chkAcceptRule.startAnimation(vibrateAnimation);
                        ToastMessageBar.show(getActivity(), R.string.validateAcceptRule);
                        return;
                    }
                    DataSaver dataSaver = new DataSaver(getActivity());
                    dataSaver.setEmailMobile(edtMobile.getText().toString(), edtEmail.getText().toString());
                    RegisterBusRequest registerBusRequest = new RegisterBusRequest(chairsSelected,
                            edtNationalCode.getText().toString(),
                            edtFirstName.getText().toString(),
                            edtLastName.getText().toString(),
                            String.valueOf(gender),
                            edtMobile.getText().toString(),
                            edtEmail.getText().toString(),
                            "",
                            String.valueOf(chairsSelected.size()),
                            searchBusResponse.getId(),
                            searchBusResponse.getCompany(),
                            searchBusResponse.getToken(),
                            searchBusResponse.getDeparureDate(),
                            searchBusResponse.getDeparureTime(),
                            searchBusResponse.getBusType(),
                            searchBusResponse.getSource(),
                            searchBusResponse.getDestination(),
                            searchBusResponse.getCapacity(),
                            searchBusResponse.getPrice(),
                            searchBusResponse.getVip(),
                            searchBusResponse.getSearchId(),
                            searchBusResponse.getImg());

                    new BusApi(getActivity()).registerPassenger(registerBusRequest, new ResultSearchPresenter<BusTicketInformation>() {
                        @Override
                        public void onStart() {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.setCancelable(false);
                                        alertDialog.setCanceledOnTouchOutside(false);
                                        btnReserve.setEnableButton(false);
                                        layoutMainInput.setEnabled(false);
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
                                        ToastMessageBar.show(getActivity(), R.string.msgErrorReserveBus);
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
                                        ToastMessageBar.show(getActivity(), R.string.msgErrorInternetConnection);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onSuccessResultSearch(final BusTicketInformation result) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.cancel();
                                        UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentReserveBus.newInstance(result, searchBusResponse), R.id.frame_Layout);

                                    }
                                });
                            }
                        }

                        @Override
                        public void noResult(int type) {

                        }

                        @Override
                        public void onError(final String msg) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastMessageBar.show(getActivity(), msg);
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
                                        alertDialog.setCancelable(true);
                                        alertDialog.setCanceledOnTouchOutside(true);
                                        layoutMainInput.setEnabled(true);
                                        btnReserve.setEnableButton(true);
                                        chkAcceptRule.setEnabled(true);
                                        btnReserve.stopProgress();
                                    }
                                });
                            }
                        }
                    });
                } catch (Exception e) {


                    ToastMessageBar.show(getActivity(), R.string.msgErrorReserveBus);
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
            if (alertDialog != null) {
                alertDialog.cancel();
            }
        } catch (Exception e) {

        }
    }
}
