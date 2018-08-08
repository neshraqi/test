package hami.mainapp.Activity.ServiceSearch;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hami.mainapp.Activity.ServiceSearch.ConstService.ServiceID;
import hami.mainapp.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.City;
import hami.mainapp.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.SearchBusRequest;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticRequest;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.FlightInternationalRequest;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.SearchInternational;
import hami.mainapp.Activity.ServiceSearch.ServiceTrain.SearchPlaceTrainActivity;
import hami.mainapp.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.CityTrain;
import hami.mainapp.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainRequest;
import hami.mainapp.BaseController.CallBackRequestSearch;
import hami.mainapp.R;
import hami.mainapp.Util.Keyboard;
import hami.mainapp.Util.TimeDate;
import hami.mainapp.Util.ToolsPersianCalendar;
import hami.mainapp.Util.UtilFonts;
import hami.mainapp.View.ToastMessageBar;
import hami.mainapp.View.ToolsFlightOption;
import hami.mainapp.View.ToolsTrainOption;


public class SearchTopSheetDialogFragment extends BottomSheetDialogFragment {

    private int serviceId;
    private EditText edtFromPlace, edtToPlace;
    private AppCompatEditText edtFromDate, edtToDate;
    private FloatingActionButton fabSearch;
    private SearchBusRequest searchBusRequest;
    private TrainRequest trainRequest;
    private FlightInternationalRequest flightRequest;
    private DomesticRequest domesticRequest;
    private LinearLayout layoutToolsSearchPassengerRespina;
    private ImageView imgMovement;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    private CallBackRequestSearch<DomesticRequest> domesticRequestCallBackRequestSearch;
    private CallBackRequestSearch<TrainRequest> trainRequestCallBackRequestSearch;
    private CallBackRequestSearch<FlightInternationalRequest> internationalFlightRequestCallBackRequestSearch;
    private CallBackRequestSearch<SearchBusRequest> searchBusRequestCallBackRequestSearch;
    private View layoutOutSide;
    private static final String TAG = "SearchTopSheetDialogFragment";

    //-----------------------------------------------
    public static SearchTopSheetDialogFragment newInstanceDomesticFlight(int serviceId, DomesticRequest domesticRequest) {
        Bundle args = new Bundle();
        SearchTopSheetDialogFragment fragment = new SearchTopSheetDialogFragment();
        args.putInt(ServiceID.INTENT_SERVICE_ID, serviceId);
        args.putSerializable(DomesticRequest.class.getName(), domesticRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    public static SearchTopSheetDialogFragment newInstanceInternationalFlight(int serviceId, FlightInternationalRequest flightRequest) {
        Bundle args = new Bundle();
        SearchTopSheetDialogFragment fragment = new SearchTopSheetDialogFragment();
        args.putInt(ServiceID.INTENT_SERVICE_ID, serviceId);
        args.putSerializable(FlightInternationalRequest.class.getName(), flightRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    public static SearchTopSheetDialogFragment newInstanceTrain(int serviceId, TrainRequest trainRequest) {
        Bundle args = new Bundle();
        SearchTopSheetDialogFragment fragment = new SearchTopSheetDialogFragment();
        args.putInt(ServiceID.INTENT_SERVICE_ID, serviceId);
        args.putSerializable(TrainRequest.class.getName(), trainRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    public static SearchTopSheetDialogFragment newInstanceBus(int serviceId, SearchBusRequest searchBusRequest) {
        Bundle args = new Bundle();
        SearchTopSheetDialogFragment fragment = new SearchTopSheetDialogFragment();
        args.putInt(ServiceID.INTENT_SERVICE_ID, serviceId);
        args.putSerializable(SearchBusRequest.class.getName(), searchBusRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            serviceId = getArguments().getInt(ServiceID.INTENT_SERVICE_ID);
            if (serviceId == ServiceID.SERVICE_ID_FLIGHT_DOMESTIC) {
                domesticRequest = (DomesticRequest) getArguments().getSerializable(DomesticRequest.class.getName());
            } else if (serviceId == ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL) {
                flightRequest = (FlightInternationalRequest) getArguments().getSerializable(FlightInternationalRequest.class.getName());
            } else if (serviceId == ServiceID.SERVICE_ID_TRAIN) {
                trainRequest = (TrainRequest) getArguments().getSerializable(TrainRequest.class.getName());
            } else if (serviceId == ServiceID.SERVICE_ID_BUS) {
                searchBusRequest = (SearchBusRequest) getArguments().getSerializable(SearchBusRequest.class.getName());
            }
        }
    }
    //-----------------------------------------------

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (outState != null) {
            outState.putSerializable(SearchBusRequest.class.getName(), searchBusRequest);
            outState.putSerializable(TrainRequest.class.getName(), trainRequest);
            outState.putSerializable(FlightInternationalRequest.class.getName(), flightRequest);
            outState.putSerializable(DomesticRequest.class.getName(), domesticRequest);
            outState.putInt("serviceId", serviceId);
        }
        super.onSaveInstanceState(outState);
    }
    //-----------------------------------------------

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            searchBusRequest = (SearchBusRequest) savedInstanceState.getSerializable(SearchBusRequest.class.getName());
            trainRequest = (TrainRequest) savedInstanceState.getSerializable(TrainRequest.class.getName());
            flightRequest = (FlightInternationalRequest) savedInstanceState.getSerializable(FlightInternationalRequest.class.getName());
            domesticRequest = (DomesticRequest) savedInstanceState.getSerializable(DomesticRequest.class.getName());
            serviceId = savedInstanceState.getInt("serviceId");
        }
    }

    //-----------------------------------------------
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    //-----------------------------------------------
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View inflatedView = View.inflate(getContext(), R.layout.include_service_flight_domestic_search, null);
        initialComponentFragment(inflatedView);
        dialog.setContentView(inflatedView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) inflatedView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        View parent = (View) inflatedView.getParent();
        parent.setFitsSystemWindows(true);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        inflatedView.measure(0, 0);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        bottomSheetBehavior.setPeekHeight(screenHeight);
        parent.setBackgroundColor(Color.TRANSPARENT);
        if (params.getBehavior() instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) params.getBehavior()).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        params.height = screenHeight;
        parent.setLayoutParams(params);
    }

    //-----------------------------------------------
    private void initialComponentFragment(View view) {
        initialComponentMain(view);
        if (serviceId == ServiceID.SERVICE_ID_FLIGHT_DOMESTIC) {
            resetFlightDomesticUi();
            setupServiceDomesticFlight();
        } else if (serviceId == ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL) {
            resetFlightInternationalUi();
            setupServiceInternationalFlight();
        } else if (serviceId == ServiceID.SERVICE_ID_BUS) {
            resetBusUi();
            setupServiceBus();
        } else if (serviceId == ServiceID.SERVICE_ID_TRAIN) {
            resetTrainUi();
            setupServiceTrain();
        }
    }

    //-----------------------------------------------
    private void initialComponentMain(View view) {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        layoutToolsSearchPassengerRespina = (LinearLayout) view.findViewById(R.id.layoutToolsSearchPassengerRespina);
        layoutOutSide = (View) view.findViewById(R.id.layoutOutSide);
        imgMovement = (ImageView) view.findViewById(R.id.imgMovementPlace);
        edtFromPlace = (EditText) view.findViewById(R.id.edtFromPlace);
        edtToPlace = (EditText) view.findViewById(R.id.edtToPlace);
        edtFromDate = (AppCompatEditText) view.findViewById(R.id.edtFromDate);
        edtToDate = (AppCompatEditText) view.findViewById(R.id.edtToDate);
        edtFromPlace.setFocusable(false);
        edtFromPlace.setCursorVisible(false);
        edtFromPlace.setOnClickListener(onClickListener);
        edtToPlace.setFocusable(false);
        edtToPlace.setCursorVisible(false);
        edtToPlace.setOnClickListener(onClickListener);
        edtFromDate.setFocusable(false);
        edtFromDate.setCursorVisible(false);
        edtFromDate.setOnClickListener(onClickListener);
        edtToDate.setFocusable(false);
        edtToDate.setCursorVisible(false);
        edtToDate.setOnClickListener(onClickListener);
        edtToDate.setVisibility(View.GONE);
        fabSearch = (FloatingActionButton) view.findViewById(R.id.fabSearch);
        fabSearch.setOnClickListener(onClickListener);
        imgMovement.setOnClickListener(onClickListener);
        Keyboard.closeKeyboard(getActivity());
        layoutOutSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //navigationService.setSelectedItemId(R.id.actionDomesticFlight);
    }

    //-----------------------------------------------
    private void resetBusUi() {
        edtToDate.setVisibility(View.GONE);
        layoutToolsSearchPassengerRespina.setVisibility(View.GONE);
    }

    //-----------------------------------------------
    private void resetTrainUi() {
        edtToDate.setVisibility(View.GONE);
        //layoutToolsSearchPassengerRespina.setVisibility(View.GONE);

        layoutToolsSearchPassengerRespina.findViewById(R.id.line).setVisibility(View.GONE);
        layoutToolsSearchPassengerRespina.setVisibility(View.VISIBLE);
        //layoutToolsSearchPassengerRespina.findViewById(R.id.toggleButtonType).setVisibility(View.GONE);
    }

    //-----------------------------------------------
    private void resetFlightInternationalUi() {

        //layoutToolsSearchPassengerRespina.findViewById(R.id.toggleButtonType).setVisibility(View.VISIBLE);
        layoutToolsSearchPassengerRespina.findViewById(R.id.line).setVisibility(View.VISIBLE);
        layoutToolsSearchPassengerRespina.setVisibility(View.VISIBLE);
    }

    //-----------------------------------------------
    private void resetFlightDomesticUi() {
        edtFromPlace.getText().clear();
        edtToPlace.getText().clear();
        edtFromDate.getText().clear();
        edtToDate.getText().clear();
        edtToDate.setVisibility(View.GONE);
        layoutToolsSearchPassengerRespina.setVisibility(View.GONE);
        layoutToolsSearchPassengerRespina.findViewById(R.id.line).setVisibility(View.GONE);


    }


    //-----------------------------------------------
    private void setupServiceDomesticFlight() {
        edtFromPlace.setText(domesticRequest.getSourcePersian());
        edtToPlace.setText(domesticRequest.getDestinationPersian());
        edtFromDate.setText(domesticRequest.getDepartureGoPersian());
//        ToggleButton toggleButtonType = (ToggleButton) layoutToolsSearchPassengerRespina.findViewById(R.id.toggleButtonType);
//        final LinearLayout layoutPassengerCount = (LinearLayout) layoutToolsSearchPassengerRespina.findViewById(R.id.layoutPassengerCount);
//        toggleButtonType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                edtToDate.setText("");
//            }
//        });
    }

    //-----------------------------------------------
    private void setupServiceInternationalFlight() {
        edtFromPlace.setText(flightRequest.getOriginPersian());
        edtToPlace.setText(flightRequest.getDestinationPersian());
        edtFromDate.setText(flightRequest.getWentDate());
//        RadioGroup toggleButtonType = (RadioGroup) layoutToolsSearchPassengerRespina.findViewById(R.id.toggleButtonType);
        final LinearLayout layoutPassengerCount = (LinearLayout) layoutToolsSearchPassengerRespina.findViewById(R.id.layoutPassengerCount);
        TextView txtCountPassenger = (TextView) layoutPassengerCount.findViewById(R.id.txtCountPassenger);
        txtCountPassenger.setText(getString(R.string.addCountPassenger) + "(1)");
        if (flightRequest.getDate2() != null && flightRequest.getDate2().length() > 0) {
//            toggleButtonType.check(R.id.rbWentAndReturnWays);
            edtToDate.setText(flightRequest.getReturnDate());
            edtToDate.setVisibility(View.VISIBLE);
        } else {
//            toggleButtonType.check(R.id.rbWentWays);
            edtToDate.setText("");
            edtToDate.setVisibility(View.GONE);
        }
//        toggleButtonType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                if (checkedId == R.id.rbWentAndReturnWays) {
//                    edtToDate.setText("");
//                    edtToDate.setVisibility(View.VISIBLE);
//                } else if (checkedId == R.id.rbWentWays) {
//                    edtToDate.setText("");
//                    edtToDate.setVisibility(View.GONE);
//                }
//            }
//        });


        layoutPassengerCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final ToolsFlightOption toolsFlightOption = new ToolsFlightOption(getActivity(), flightRequest.getAdult(), flightRequest.getChild(), flightRequest.getInfant());
                UtilFonts.overrideFonts(getActivity(), toolsFlightOption, UtilFonts.IRAN_SANS_NORMAL);
                builder.setView(toolsFlightOption);
                final AlertDialog alertDialog = builder.create();
                toolsFlightOption.setCallbackAcceptButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView txtCountPassenger = (TextView) layoutPassengerCount.findViewById(R.id.txtCountPassenger);
                        String count = String.valueOf(toolsFlightOption.getCountPassenger());
                        txtCountPassenger.setText(getString(R.string.addCountPassenger) + "(" + count + ")");
                        //txtCountPassenger.setText(String.valueOf(toolsFlightOption.getCountPassenger()));
                        flightRequest.setAdult(toolsFlightOption.getNumberAdults());
                        flightRequest.setChild(toolsFlightOption.getNumberChildren());
                        flightRequest.setInfant(toolsFlightOption.getNumberInfant());
                        alertDialog.dismiss();
                    }
                });
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        TextView txtCountPassenger = (TextView) layoutPassengerCount.findViewById(R.id.txtCountPassenger);
                        String count = String.valueOf(toolsFlightOption.getCountPassenger());
                        txtCountPassenger.setText(getString(R.string.addCountPassenger) + "(" + count + ")");
                        //txtCountPassenger.setText(String.valueOf(toolsFlightOption.getCountPassenger()));
                        flightRequest.setAdult(toolsFlightOption.getNumberAdults());
                        flightRequest.setChild(toolsFlightOption.getNumberChildren());
                        flightRequest.setInfant(toolsFlightOption.getNumberInfant());
                    }
                });
                alertDialog.show();
            }
        });
        layoutToolsSearchPassengerRespina.setVisibility(View.VISIBLE);
    }

    //-----------------------------------------------
    private void setupServiceTrain() {
        edtFromPlace.setText(trainRequest.getSourceTrainFa());
        edtToPlace.setText(trainRequest.getDestinationTrainFa());
        edtFromDate.setText(trainRequest.getDepartureGoTrainPersian());
        final LinearLayout layoutPassengerCount = (LinearLayout) layoutToolsSearchPassengerRespina.findViewById(R.id.layoutPassengerCount);
        TextView txtCountPassenger = (TextView) layoutPassengerCount.findViewById(R.id.txtCountPassenger);
        txtCountPassenger.setText(getString(R.string.addCountPassenger) + "(1)");

        View.OnClickListener onClickListenerShow = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final ToolsTrainOption toolsTrainOption = new ToolsTrainOption(getActivity(), Integer.valueOf(trainRequest.getCountPassengerTrain()), Integer.valueOf(trainRequest.getTypeTicketTrain()), trainRequest.isCope());
                UtilFonts.overrideFonts(getActivity(), toolsTrainOption, UtilFonts.IRAN_SANS_NORMAL);
                builder.setView(toolsTrainOption);
                final AlertDialog alertDialog = builder.create();
                toolsTrainOption.setCallbackAcceptButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView txtCountPassenger = (TextView) layoutToolsSearchPassengerRespina.findViewById(R.id.txtCountPassenger);
                        txtCountPassenger.setText(String.valueOf(toolsTrainOption.getCountPassenger()));
                        txtCountPassenger.setText(getString(R.string.addCountPassenger) + "(" + toolsTrainOption.getCountPassenger() + ")");
                        //txtTypePassenger.setText(toolsTrainOption.getStringTypePassenger());
                        trainRequest.setCountPassengerTrain(toolsTrainOption.getCountPassenger());
                        trainRequest.setCope(toolsTrainOption.getHasFullCope());
                        trainRequest.setTypeTicketTrain(toolsTrainOption.getTypePassenger());
                        alertDialog.dismiss();
                    }
                });
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        TextView txtCountPassenger = (TextView) layoutToolsSearchPassengerRespina.findViewById(R.id.txtCountPassenger);
//                        TextView txtTypePassenger = (TextView) layoutToolsSearchPassengerRespina.findViewById(R.id.btnTypePassenger);
                        txtCountPassenger.setText(String.valueOf(toolsTrainOption.getCountPassenger()));
//                        txtTypePassenger.setText(toolsTrainOption.getStringTypePassenger());
                        trainRequest.setCountPassengerTrain(toolsTrainOption.getCountPassenger());
                        trainRequest.setCope(toolsTrainOption.getHasFullCope());
                        trainRequest.setTypeTicketTrain(toolsTrainOption.getTypePassenger());
                    }
                });
                alertDialog.show();
            }
        };
        layoutToolsSearchPassengerRespina.findViewById(R.id.layoutPassengerCount).setOnClickListener(onClickListenerShow);
        layoutToolsSearchPassengerRespina.setOnClickListener(onClickListenerShow);
        layoutToolsSearchPassengerRespina.setVisibility(View.VISIBLE);
    }

    //-----------------------------------------------
    private void setupServiceBus() {
        edtFromPlace.setText(searchBusRequest.getFromCity());
        edtToPlace.setText(searchBusRequest.getToCity());
        edtFromDate.setText(searchBusRequest.getDeparturePersianGoBus());
    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.edtFromPlace:
                    intent = new Intent(getActivity(), SearchPlaceMainActivity.class);
                    intent.putExtra(SearchPlaceMainActivity.INTENT_HAS_TAKE_OFF, true);
                    intent.putExtra(ServiceID.INTENT_SERVICE_ID, serviceId);
                    startActivityForResult(intent, 0);
                    break;
                case R.id.edtToPlace:
                    intent = new Intent(getActivity(), SearchPlaceMainActivity.class);
                    intent.putExtra(SearchPlaceMainActivity.INTENT_HAS_TAKE_OFF, false);
                    intent.putExtra(ServiceID.INTENT_SERVICE_ID, serviceId);
                    startActivityForResult(intent, 0);
                    break;
                case R.id.edtFromDate:
                    showDateForService(false);
                    break;
                case R.id.edtToDate:
                    showDateForService(true);
                    break;
                case R.id.fabSearch:
                    search();
                    break;
                case R.id.imgMovementPlace:
                    movementService();
                    break;
            }
        }
    };

    //-----------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (data != null && data.getExtras() != null) {
                if (resultCode == ServiceID.SERVICE_ID_BUS) {
                    City city = (City) data.getExtras().getSerializable(City.class.getName());
                    setupPlaceBus(data.getExtras().getBoolean(SearchPlaceMainActivity.INTENT_HAS_TAKE_OFF), city);
                } else if (resultCode == ServiceID.SERVICE_ID_TRAIN) {
                    CityTrain city = (CityTrain) data.getExtras().getSerializable(CityTrain.class.getName());
                    setupPlaceTrain(data.getExtras().getBoolean(SearchPlaceTrainActivity.HAS_TAKE_OFF), city);
                } else if (resultCode == ServiceID.SERVICE_ID_FLIGHT_DOMESTIC) {
                    SearchInternational city = (SearchInternational) data.getExtras().getSerializable(SearchInternational.class.getName());
                    Boolean hasTakeOff = data.getExtras().getBoolean(SearchPlaceTrainActivity.HAS_TAKE_OFF);
                    setupPlaceFlightDomestic(hasTakeOff, city);
                } else if (resultCode == ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL) {
                    SearchInternational city = (SearchInternational) data.getExtras().getSerializable(SearchInternational.class.getName());
                    Boolean hasTakeOff = data.getExtras().getBoolean(SearchPlaceTrainActivity.HAS_TAKE_OFF);
                    setupPlaceFlightInternational(hasTakeOff, city);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //-----------------------------------------------
    private void setupPlaceBus(Boolean hasTakeOff, City city) {
        if (hasTakeOff) {
            if (searchBusRequest.getDestinationBus() != null && searchBusRequest.getDestinationBus().contentEquals(city.getCid())) {
                ToastMessageBar.show(getActivity(), R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                searchBusRequest.setFromCity(city.getCityName());
                searchBusRequest.setSourceBus(city.getCid());
                edtFromPlace.setText(city.getCityName());
            }
        } else {
            if (searchBusRequest.getSourceBus() != null && searchBusRequest.getSourceBus().contentEquals(city.getCid())) {
                ToastMessageBar.show(getActivity(), R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {

                searchBusRequest.setToCity(city.getCityName());
                searchBusRequest.setDestinationBus(city.getCid());
                edtToPlace.setText(city.getCityName());
            }
        }
    }

    //-----------------------------------------------
    private void setupPlaceTrain(Boolean hasTakeOff, CityTrain city) {
        if (hasTakeOff) {
            if (trainRequest.getDestinationTrain() != null && trainRequest.getDestinationTrain().contentEquals(city.getCityEng())) {
                ToastMessageBar.show(getActivity(), R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                trainRequest.setSourceTrain(city.getCityEng());
                trainRequest.setSourceTrainFa(city.getCityPersian());
                edtFromPlace.setText(city.getCityPersian());
            }
        } else {
            if (trainRequest.getSourceTrain() != null && trainRequest.getSourceTrain().contentEquals(city.getCityEng())) {
                ToastMessageBar.show(getActivity(), R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                trainRequest.setDestinationTrain(city.getCityEng());
                trainRequest.setDestinationTrainFa(city.getCityPersian());
                edtToPlace.setText(city.getCityPersian());
            }
        }
    }

    //-----------------------------------------------
    private void setupPlaceFlightDomestic(Boolean hasTakeOff, SearchInternational searchInternational) {
        if (hasTakeOff) {
            //fromSearchInternational = searchInternational;
            if (domesticRequest.getDestination() != null && domesticRequest.getDestination().contentEquals(searchInternational.getYata())) {
                ToastMessageBar.show(getActivity(), R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                edtFromPlace.setText(getTitle(searchInternational));
                domesticRequest.setSource(searchInternational.getYata());
                domesticRequest.setSourcePersian(searchInternational.getDataF());
            }
        } else {
            if (domesticRequest.getSource() != null && domesticRequest.getSource().contentEquals(searchInternational.getYata())) {
                ToastMessageBar.show(getActivity(), R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                //toSearchInternational = searchInternational;
                edtToPlace.setText(getTitle(searchInternational));
                domesticRequest.setDestination(searchInternational.getYata());
                domesticRequest.setDestinationPersian(searchInternational.getDataF());
            }
        }
    }

    //-----------------------------------------------
    private void setupPlaceFlightInternational(Boolean hasTakeOff, SearchInternational searchInternational) {
        if (hasTakeOff) {
            if (flightRequest.getDestination() != null && flightRequest.getDestination().contentEquals(searchInternational.getYata())) {
                ToastMessageBar.show(getActivity(), R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                flightRequest.setOrigin(searchInternational.getYata());
                flightRequest.setOriginFlag(searchInternational.getParent());
                flightRequest.setOriginPersian(searchInternational.getDataF());
                edtFromPlace.setText(getTitle(searchInternational));
            }
        } else {
            if (flightRequest.getOrigin() != null && flightRequest.getOrigin().contentEquals(searchInternational.getYata())) {
                ToastMessageBar.show(getActivity(), R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                flightRequest.setDestination(searchInternational.getYata());
                flightRequest.setDestinationFlag(searchInternational.getParent());
                flightRequest.setDestinationPersian(searchInternational.getDataF());
                edtToPlace.setText(getTitle(searchInternational));
            }
        }
    }

    //-----------------------------------------------
    private String getTitle(SearchInternational searchInternational) {
        String title = "";
        title = searchInternational.getDataF();
        return title;
    }

    //-----------------------------------------------
    private Boolean validateFormDomestic() {
        try {
            if (domesticRequest.getSource() == null || domesticRequest.getSource().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseFromPlace);
                return false;
            } else if (domesticRequest.getDestination() == null || domesticRequest.getDestination().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseToPlace);
                return false;
            } else if (domesticRequest.getDepartureGo() == null ||
                    domesticRequest.getDepartureGoPersian() == null ||
                    domesticRequest.getDepartureGo().length() == 0 ||
                    domesticRequest.getDepartureGoPersian().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtFromDate.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.validateErrorDate);
                return false;
            }

        } catch (Exception e) {

            return false;
        }
        return true;
    }

    //-----------------------------------------------
    private Boolean validateFormBus() {
        try {
            if (searchBusRequest.getSourceBus() == null || searchBusRequest.getSourceBus().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseFromPlace);
                return false;
            } else if (searchBusRequest.getDestinationBus() == null || searchBusRequest.getDestinationBus().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseToPlace);
                return false;
            } else if (searchBusRequest.getDepartureGoBus() == null ||
                    searchBusRequest.getDeparturePersianGoBus() == null ||
                    searchBusRequest.getDepartureGoBus().length() == 0 ||
                    searchBusRequest.getDeparturePersianGoBus().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtFromDate.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.validateErrorDate);
                return false;
            }

        } catch (Exception e) {

            return false;
        }
        return true;
    }

    //-----------------------------------------------
    private Boolean validateFormTrain() {
        try {
            if (trainRequest.getSourceTrain() == null || trainRequest.getSourceTrain().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseFromPlace);
                return false;
            } else if (trainRequest.getDestinationTrain() == null || trainRequest.getDestinationTrain().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseToPlace);
                return false;
            } else if (trainRequest.getDepartureGoTrainEng() == null ||
                    trainRequest.getDepartureGoTrainPersian() == null ||
                    trainRequest.getDepartureGoTrainEng().length() == 0 ||
                    trainRequest.getDepartureGoTrainPersian().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtFromDate.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.validateErrorDate);
                return false;
            }

        } catch (Exception e) {

            return false;
        }
        return true;
    }

    //-----------------------------------------------
    private Boolean validateFormInternational() {
        try {
//            RadioGroup toggleButtonType = (RadioGroup) layoutToolsSearchPassengerRespina.findViewById(R.id.toggleButtonType);
            if (flightRequest.getOrigin() == null || flightRequest.getOrigin().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseFromPlace);
                return false;
            } else if (flightRequest.getDestination() == null || flightRequest.getDestination().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseToPlace);
                return false;
            } else if (flightRequest.getDate1() == null ||
                    flightRequest.getDate1().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtFromDate.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.validateErrorDate);
                return false;
            }
//            else if (toggleButtonType.getCheckedRadioButtonId() == R.id.rbWentAndReturnWays && (flightRequest.getDate2() == null ||
//                    flightRequest.getDate2().length() == 0)) {
//                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
//                edtToDate.startAnimation(vibrateAnimation);
//                ToastMessageBar.show(getActivity(), R.string.validateErrorToDate);
//                return false;
//            }

        } catch (Exception e) {

            return false;
        }
        return true;
    }

    //-----------------------------------------------
    private int getServiceId() {
        return serviceId;
    }


    //-----------------------------------------------
    private void showDateForService(Boolean hasReturn) {
        if (getServiceId() == ServiceID.SERVICE_ID_FLIGHT_DOMESTIC) {
            if (domesticRequest.getSource() == null || domesticRequest.getSource().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseFromPlace);
            } else if (domesticRequest.getDestination() == null || domesticRequest.getDestination().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseToPlace);
            } else
                getDatePersian(false, domesticRequest.getDepartureGo());
        } else if (getServiceId() == ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL) {
            if (flightRequest.getOrigin() == null || flightRequest.getOrigin().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseFromPlace);
            } else if (flightRequest.getDestination() == null || flightRequest.getDestination().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseToPlace);
            } else {
                String minDateIni = hasReturn ? flightRequest.getDate1() : "";
                getDateGreg(hasReturn, minDateIni);
            }
        } else if (getServiceId() == ServiceID.SERVICE_ID_TRAIN) {
            if (trainRequest.getSourceTrain() == null || trainRequest.getSourceTrain().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseFromPlace);
            } else if (trainRequest.getDestinationTrain() == null || trainRequest.getDestinationTrain().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseToPlace);
            } else
                getDatePersian(false, trainRequest.getDepartureGoTrainEng());
        } else if (getServiceId() == ServiceID.SERVICE_ID_BUS) {
            if (searchBusRequest.getSourceBus() == null || searchBusRequest.getSourceBus().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseFromPlace);
            } else if (searchBusRequest.getDestinationBus() == null || searchBusRequest.getDestinationBus().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(getActivity(), R.string.showCaseToPlace);
            } else {
                getDatePersian(false, searchBusRequest.getDepartureGoBus());
            }
        }
    }

    //-----------------------------------------------
    private void getDatePersian(final Boolean hasReturn, @Nullable String selectedDate) {

        final PersianCalendar persianCalendar = new PersianCalendar();
        try {
            if (selectedDate != null && selectedDate.length() > 0) {
                SimpleDateFormat sdfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                Date dateCurrent = sdfCurrent.parse(selectedDate);
                Calendar calendarGreg = Calendar.getInstance();
                calendarGreg.setTime(dateCurrent);
                Calendar calendar = ToolsPersianCalendar.getPersianCalendar(calendarGreg);
                persianCalendar.setPersianDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
        } catch (Exception e) {

        }
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        DateFormat df = new DateFormat();
                        Calendar calendar = ToolsPersianCalendar.getGregorianCalendar(year, monthOfYear + 1, dayOfMonth);
                        String dateGeorge = df.format("yyyy-MM-dd", calendar).toString();
                        String weekName = ToolsPersianCalendar.getDayOfWeek(calendar.getTime());
                        String monthName = ToolsPersianCalendar.getPersianMonthString(calendar.getTime());
                        edtFromDate.setText(weekName + "," + dayOfMonth + monthName + "," + year);
                        //String datePersian = weekName + "," + dayOfMonth + monthName + "," + year;
                        String datePersian = weekName + "," + dayOfMonth + monthName;
                        switch (getServiceId()) {
                            case ServiceID.SERVICE_ID_BUS:
                                searchBusRequest.setDepartureGoBus(dateGeorge);
                                searchBusRequest.setDeparturePersianGoBus(datePersian);
                                edtFromDate.setText(datePersian);
                                break;
                            case ServiceID.SERVICE_ID_TRAIN:
                                trainRequest.setDepartureGoTrainEng(dateGeorge);
                                trainRequest.setDepartureGoTrainPersian(datePersian);
                                edtFromDate.setText(datePersian);
                                break;
                            case ServiceID.SERVICE_ID_FLIGHT_DOMESTIC:
                                domesticRequest.setDepartureGo(dateGeorge);
                                domesticRequest.setDepartureGoPersian(datePersian);
                                if (hasReturn)
                                    edtToDate.setText(datePersian);
                                else
                                    edtFromDate.setText(datePersian);
                                break;
                        }
                    }
                },
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );
//        if (getServiceId() == ServiceID.SERVICE_ID_FLIGHT_DOMESTIC) {
//            datePickerDialog.setPlaceFlight("", "");
//        }
        datePickerDialog.setMinDate(new PersianCalendar());
        datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    //-----------------------------------------------
    private void getDateGreg(final Boolean hasReturn, @Nullable String minDateString) {
        //Calendar calendar = Calendar.getInstance();
        Calendar calendarNow = Calendar.getInstance();
        MonthAdapter.CalendarDay minDate = null;
        if (minDateString != null && minDateString.length() > 0) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(minDateString);
                calendarNow.setTime(date);
                minDate = new MonthAdapter.CalendarDay(calendarNow);
            } catch (Exception e) {


                calendarNow = Calendar.getInstance();
                minDate = new MonthAdapter.CalendarDay(calendarNow);
            }
        } else {
            if (hasReturn)
                calendarNow.setTime(TimeDate.getDate(flightRequest.getDate2()).getTime());
            else {
                minDate = new MonthAdapter.CalendarDay(calendarNow);
                calendarNow.setTime(TimeDate.getDate(flightRequest.getDate1()).getTime());
            }
            //minDate = new MonthAdapter.CalendarDay(calendarNow);
        }

        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment calendarDatePickerDialogFragment, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        DateFormat df = new DateFormat();
                        String dateGeorge = df.format("yyyy-MM-dd", calendar.getTime()).toString();
                        if (hasReturn) {

                            String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
                            String monthLongName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            int yearFinal = calendar.get(Calendar.YEAR);
                            String dateReturn = dayLongName + "," + monthLongName + " " + day + "," + yearFinal;
                            flightRequest.setReturnDate(dateReturn);
                            edtToDate.setText(dateReturn);
                            flightRequest.setDate2(dateGeorge);
                        } else {
                            String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
                            String monthLongName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            int yearFinal = calendar.get(Calendar.YEAR);
                            String dateWent = dayLongName + "," + monthLongName + " " + day + "," + yearFinal;
                            edtFromDate.setText(dateWent);
                            flightRequest.setWentDate(dateWent);
                            flightRequest.setDate1(dateGeorge);
                            flightRequest.setReturnDate("");
                            flightRequest.setDate2("");
                            edtToDate.setText("");
                        }
                    }
                })
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(calendarNow.get(Calendar.YEAR), calendarNow.get(Calendar.MONTH), calendarNow.get(Calendar.DAY_OF_MONTH))
                .setDateRange(minDate, null)
                .setDoneText(getString(R.string.accept_))
                .setThemeLight()
                .setCancelText(getString(R.string.nevermind));

        cdp.show(getActivity().getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
    }

    //-----------------------------------------------
    public void setCallBackFlightDomestic(CallBackRequestSearch<DomesticRequest> domesticRequestCallBackRequestSearch) {
        this.domesticRequestCallBackRequestSearch = domesticRequestCallBackRequestSearch;
    }

    //-----------------------------------------------
    public void setCallBackFlightInternational(CallBackRequestSearch<FlightInternationalRequest> internationalFlightRequestCallBackRequestSearch) {
        this.internationalFlightRequestCallBackRequestSearch = internationalFlightRequestCallBackRequestSearch;
    }

    //-----------------------------------------------
    public void setCallBackTrain(CallBackRequestSearch<TrainRequest> trainRequestCallBackRequestSearch) {
        this.trainRequestCallBackRequestSearch = trainRequestCallBackRequestSearch;
    }

    //-----------------------------------------------
    public void setCallBackBus(CallBackRequestSearch<SearchBusRequest> searchBusRequestCallBackRequestSearch) {
        this.searchBusRequestCallBackRequestSearch = searchBusRequestCallBackRequestSearch;
    }

    //-----------------------------------------------
    private void search() {
        if (getServiceId() == ServiceID.SERVICE_ID_FLIGHT_DOMESTIC && validateFormDomestic()) {
            domesticRequestCallBackRequestSearch.getResponse(domesticRequest);
        } else if (getServiceId() == ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL && validateFormInternational()) {
            internationalFlightRequestCallBackRequestSearch.getResponse(flightRequest);
        } else if (getServiceId() == ServiceID.SERVICE_ID_BUS && validateFormBus()) {
            searchBusRequestCallBackRequestSearch.getResponse(searchBusRequest);
        } else if (getServiceId() == ServiceID.SERVICE_ID_TRAIN && validateFormTrain()) {
            trainRequestCallBackRequestSearch.getResponse(trainRequest);

        }
    }

    //-----------------------------------------------
    private void movementService() {
        if (getServiceId() == ServiceID.SERVICE_ID_FLIGHT_DOMESTIC) {
            movementFlightDomestic();
        } else if (getServiceId() == ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL) {
            movementFlightInternational();
        } else if (getServiceId() == ServiceID.SERVICE_ID_TRAIN) {
            movementTrain();
        } else if (getServiceId() == ServiceID.SERVICE_ID_BUS) {
            movementBus();
        }
    }

    //-----------------------------------------------
    private void movementFlightDomestic() {
        if (domesticRequest.getSource() == null || domesticRequest.getSource().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtFromPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(getActivity(), R.string.showCaseFromPlace);
            return;
        } else if (domesticRequest.getDestination() == null || domesticRequest.getDestination().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtToPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(getActivity(), R.string.showCaseToPlace);
            return;
        }
        domesticRequest.movementSourceWithDest();
        edtFromPlace.setText(domesticRequest.getSourcePersian());
        edtToPlace.setText(domesticRequest.getDestinationPersian());
    }

    //-----------------------------------------------
    private void movementFlightInternational() {
        if (flightRequest.getOrigin() == null || flightRequest.getOrigin().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtFromPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(getActivity(), R.string.showCaseFromPlace);
            return;
        } else if (flightRequest.getDestination() == null || flightRequest.getDestination().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtToPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(getActivity(), R.string.showCaseToPlace);
            return;
        }
        flightRequest.movementSourceWithDest();
        edtFromPlace.setText(flightRequest.getOriginPersian());
        edtToPlace.setText(flightRequest.getDestinationPersian());
    }

    //-----------------------------------------------
    private void movementTrain() {
        if (trainRequest.getSourceTrain() == null || trainRequest.getSourceTrain().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtFromPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(getActivity(), R.string.showCaseFromPlace);
            return;
        } else if (trainRequest.getDestinationTrain() == null || trainRequest.getDestinationTrain().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtToPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(getActivity(), R.string.showCaseToPlace);
            return;
        }
        trainRequest.movementSourceWithDest();
        edtFromPlace.setText(trainRequest.getSourceTrainFa());
        edtToPlace.setText(trainRequest.getDestinationTrainFa());
    }

    //-----------------------------------------------
    private void movementBus() {
        if (searchBusRequest.getSourceBus() == null || searchBusRequest.getSourceBus().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtFromPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(getActivity(), R.string.showCaseFromPlace);
            return;
        } else if (searchBusRequest.getDestinationBus() == null || searchBusRequest.getDestinationBus().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtToPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(getActivity(), R.string.showCaseToPlace);
            return;
        }
        searchBusRequest.movementSourceWithDest();
        edtFromPlace.setText(searchBusRequest.getSourceBus());
        edtToPlace.setText(searchBusRequest.getDestinationBus());
    }


}
