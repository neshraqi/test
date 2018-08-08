package com.hami.servicetrain.Services.Fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hami.common.BaseController.ResultSearchPresenter;
import com.hami.common.BaseNetwork.BaseConfig;
import com.hami.common.Util.BaseDatabase.DataSaver;
import com.hami.common.Util.Keyboard;
import com.hami.common.Util.UtilFonts;
import com.hami.common.Util.UtilFragment;
import com.hami.common.Util.UtilImageLoader;
import com.hami.common.View.CheckBox;
import com.hami.common.View.MessageBar;
import com.hami.common.View.Progressbar.ButtonWithProgress;
import com.hami.common.View.ToastMessageBar;
import com.hami.common.View.Validation.ValidationClass;
import com.hami.servicetrain.R;
import com.hami.servicetrain.Services.Adapter.PassengerTrainListAdapter;
import com.hami.servicetrain.Services.Controller.Model.ListModelPassengerInfoTrain;
import com.hami.servicetrain.Services.Controller.Model.PassengerInfoTrain;
import com.hami.servicetrain.Services.Controller.Model.RegisterTrainRequest;
import com.hami.servicetrain.Services.Controller.Model.RegisterTrainResponse;
import com.hami.servicetrain.Services.Controller.Model.TrainPassengerInfo;
import com.hami.servicetrain.Services.Controller.Model.TrainRequest;
import com.hami.servicetrain.Services.Controller.Model.TrainResponse;
import com.hami.servicetrain.Services.Controller.Presenter.OnSelectItemPassengerTrainListener;
import com.hami.servicetrain.Services.Controller.Presenter.TrainApi;
import com.hami.servicetrain.Services.Fragment.View.DialogTrainAddPassenger;
import com.hami.servicetrain.Services.Fragment.View.OnPassengerTrainListener;

public class FragmentTrainDetails extends Fragment {


    private RelativeLayout coordinator;
    private AppCompatButton btnRegister;
    private View view;
    private RecyclerView rvResult;
    private PassengerTrainListAdapter mAdapter;
    private TrainResponse trainResponse;
    private TrainRequest trainRequest;
    private TextView btnAddPassenger, tvCountPassenger;
    private MessageBar messageBar;
    private AlertDialog alertDialog;
    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            trainRequest = (TrainRequest) getArguments().getSerializable(TrainRequest.class.getName());
            trainResponse = (TrainResponse) getArguments().getParcelable(TrainResponse.class.getName());
        }

    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            trainRequest = (TrainRequest) savedInstanceState.getSerializable(TrainRequest.class.getName());
            trainResponse = (TrainResponse) savedInstanceState.getParcelable(TrainResponse.class.getName());
        }
    }

    //-----------------------------------------------
    public static FragmentTrainDetails newInstance(TrainResponse trainResponse, TrainRequest trainRequest) {
        Bundle args = new Bundle();
        FragmentTrainDetails fragment = new FragmentTrainDetails();
        args.putSerializable(TrainRequest.class.getName(), trainRequest);
        args.putParcelable(TrainResponse.class.getName(), trainResponse);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_service_train_details, container, false);
            initialComponentFragment();
        }
        return view;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(TrainRequest.class.getName(), trainRequest);
            outState.putParcelable(TrainResponse.class.getName(), trainResponse);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Override
    public void onResume() {
        super.onResume();
    }

    //-----------------------------------------------
    private void initialComponentFragment() {
        coordinator = (RelativeLayout) view.findViewById(R.id.coordinator);
        UtilFonts.overrideFonts(getActivity(), coordinator, UtilFonts.IRAN_SANS_NORMAL);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        messageBar.setMainBackground(R.color.main_color_grey_200);
        messageBar.setTitleButton(R.string.addPassenger);
        messageBar.showMessageBar(R.string.warningNoAddPassenger);
        messageBar.setCallbackButtonNewSearch(callbackMessageBar);
        tvCountPassenger = (TextView) view.findViewById(R.id.tvCountPassenger);
        btnRegister = (AppCompatButton) view.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(onClickListener);
        setupPlace();
        setupAddPassenger();
        setupRecyclerView();
    }

    //-----------------------------------------------
    private void setupPlace() {
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
        UtilImageLoader.loadImage(getActivity(), imgLogoAirLine, url, R.drawable.train);
    }

    //-----------------------------------------------
    View.OnClickListener callbackMessageBar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int capacityCope = Integer.valueOf(trainResponse.getCompartmentCapicity());
            int capacity = Integer.valueOf(trainResponse.getCapacity().replace("+", ""));
            if (mAdapter.getItemCount() == capacityCope) {
                ToastMessageBar.show(getActivity(), R.string.validateCopeFull);
            } else if (mAdapter.getItemCount() < capacity)
                new DialogTrainAddPassenger(getActivity(), onPassengerTrainListener);

            else {
                ToastMessageBar.show(getActivity(), R.string.validateCope);
            }
        }
    };

    //-----------------------------------------------
    private void setupAddPassenger() {
        btnAddPassenger = (TextView) view.findViewById(R.id.btnAddPassenger);
        btnAddPassenger.setPaintFlags(btnAddPassenger.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnAddPassenger.setOnClickListener(callbackMessageBar);
    }

    //-----------------------------------------------
    private void setupRecyclerView() {
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PassengerTrainListAdapter(getActivity(), onSelectItemPassengerTrainListener);
        rvResult.setAdapter(mAdapter);
    }

    //-----------------------------------------------
    private void updateCountPassenger() {
        int capacityCope = Integer.valueOf(trainResponse.getCompartmentCapicity());
        tvCountPassenger.setText("(" + mAdapter.getItemCount() + "/" + capacityCope + ")");
    }

    //-----------------------------------------------
    OnPassengerTrainListener onPassengerTrainListener = new OnPassengerTrainListener() {
        @Override
        public void onAddPassenger(TrainPassengerInfo trainPassengerInfo, Boolean hasForeign) {
            view.findViewById(R.id.layoutAddPassenger).setVisibility(View.VISIBLE);
            messageBar.setVisibility(View.GONE);
            mAdapter.addNewPassenger(trainPassengerInfo);
            updateCountPassenger();

        }

        @Override
        public void onEditPassenger(TrainPassengerInfo trainPassengerInfo, int position) {
            mAdapter.editPassenger(trainPassengerInfo, position);
        }
    };
    //-----------------------------------------------
    OnSelectItemPassengerTrainListener onSelectItemPassengerTrainListener = new OnSelectItemPassengerTrainListener() {
        @Override
        public void onSelectItemTrain(TrainPassengerInfo trainPassengerInfo, int position) {
            new DialogTrainAddPassenger(getActivity(), trainPassengerInfo, onPassengerTrainListener, position);
        }

        @Override
        public void onRemoveItemTrain(TrainPassengerInfo trainPassengerInfo, int position) {
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
            int i = v.getId();
            if (i == R.id.btnCancel) {
                getActivity().onBackPressed();

            } else if (i == R.id.btnRegister) {
                showDialog();

            }
        }
    };

    //-----------------------------------------------
    private void showDialog() {
        if (mAdapter.getItemCount() == 0) {
            ToastMessageBar.show(getActivity(), R.string.validateNoPassenger);
            return;
        }
        RegisterTrainRequest registerTrainRequest = new RegisterTrainRequest();
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            PassengerInfoTrain passengerInfoTrain = new PassengerInfoTrain(mAdapter.getListItem().get(i));
            ListModelPassengerInfoTrain listModelPassengerInfoTrain = new ListModelPassengerInfoTrain(passengerInfoTrain);
            registerTrainRequest.addListModelPassengerInfoTrain(listModelPassengerInfoTrain);
        }
        //new DialogRegisterTrainFinal(getActivity(), getActivity().getSupportFragmentManager()).showDialog(registerTrainRequest, trainResponse, trainRequest);
        showDialogFinalPreReserve(registerTrainRequest);
    }

    //-----------------------------------------------
    public void showDialogFinalPreReserve(final RegisterTrainRequest registerTrainRequest) {
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
//                new CustomTabsPackages(getActivity()).showUrl(RespinaConst.RULE_LINK_TRAIN);
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
                    final Boolean isCheckRoll = chkAcceptRule.hasCheck();
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
                    //RegisterTrainRequest registerTrainRequest = new RegisterTrainRequest();
                    registerTrainRequest.setPhoneNumber(mobile);
                    registerTrainRequest.setEmail(email);
                    registerTrainRequest.setCaptchaTrain("");
                    registerTrainRequest.setNumberP(String.valueOf(registerTrainRequest.getListModelPassengerInfoTrains().size()));
                    registerTrainRequest.setId(trainResponse.getId());
                    registerTrainRequest.setPrice(trainResponse.getPrice());
                    registerTrainRequest.setNumber(trainResponse.getNumber());
                    registerTrainRequest.setFullPrice(trainResponse.getFullPriceRial());
                    registerTrainRequest.setAirConditioning(trainResponse.getAirConditioning());
                    registerTrainRequest.setCapacity(trainResponse.getCapacity());
                    registerTrainRequest.setCircularNumberSerial(trainResponse.getCircularNumberSerial());
                    registerTrainRequest.setCompartmentCapicity(trainResponse.getCompartmentCapicity());
                    registerTrainRequest.setDaytime(trainResponse.getDayTime());
                    registerTrainRequest.setDaytimetext(trainResponse.getDayTimeText());
                    registerTrainRequest.setExitTime(trainResponse.getExitTime());
                    registerTrainRequest.setFromen(trainResponse.getFromEn());
                    registerTrainRequest.setFromfa(trainResponse.getFromFa());
                    registerTrainRequest.setDegree(trainResponse.getDegree());
                    registerTrainRequest.setToen(trainResponse.getToeN());
                    registerTrainRequest.setTofa(trainResponse.getToFa());
                    registerTrainRequest.setIsCompartment(trainResponse.getIsCompartment());
                    registerTrainRequest.setIscope(trainResponse.getIsCope());
                    registerTrainRequest.setMedia(trainResponse.getMedia());
                    registerTrainRequest.setMoveDate(trainResponse.getMoveDate());
                    registerTrainRequest.setWagonType(trainResponse.getWagonType());
                    registerTrainRequest.setNexday(trainResponse.getNexDay());
                    registerTrainRequest.setPreday(trainResponse.getPreDay());
                    registerTrainRequest.setTypeT(trainResponse.getTypeT());
                    registerTrainRequest.setTimeOfArrival(trainResponse.getTimeOfArrival());
                    registerTrainRequest.setSoldCount(trainResponse.getSoldCount());
                    registerTrainRequest.setRationCode(trainResponse.getRationCode());
                    registerTrainRequest.setRateCode(trainResponse.getRateCode());
                    registerTrainRequest.setPid(trainResponse.getPid());
                    registerTrainRequest.setOwner(trainResponse.getOwner());
                    registerTrainRequest.setOwnerfa(trainResponse.getOwnerFa());
                    registerTrainRequest.setPathCode(trainResponse.getPathCode());
                    registerTrainRequest.setWagonName(trainResponse.getWagonName());

                    new TrainApi(getActivity()).registerPassenger(registerTrainRequest, new ResultSearchPresenter<RegisterTrainResponse>() {
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
                                        ToastMessageBar.show(getActivity(), R.string.msgErrorReserveTrain);
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
                        public void onSuccessResultSearch(final RegisterTrainResponse result) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.cancel();
                                        UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentFinalBookingTrain.newInstance(result, trainResponse, trainRequest),R.id.frame_Layout);
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
                    ToastMessageBar.show(getActivity(), R.string.msgErrorReserveTrain);
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

