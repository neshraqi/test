package hami.nasimbehesht724.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hami.nasimbehesht724.Activity.ServiceHotel.ActivityMainHotel;
import hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelSearchRequest;
import hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model.HotelDomesticCity;
import hami.nasimbehesht724.Activity.ServiceHotel.International.Controller.Model.InternationalHotelSearchRequest;
import hami.nasimbehesht724.Activity.ServiceHotel.International.Controller.Model.SearchCity;
import hami.nasimbehesht724.Activity.ServiceHotel.International.Controller.Model.SearchDestination;
import hami.nasimbehesht724.Activity.ServiceSearch.ConstService.ServiceID;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.ActivityMainBus;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.City;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.SearchBusRequest;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.ActivityMainFlight;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticRequest;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.Country;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.FlightInternationalRequest;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.SearchInternational;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.ActivityMainTrain;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.CityTrain;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainRequest;
import hami.nasimbehesht724.MainActivityMaterial;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.TimeDate;
import hami.nasimbehesht724.Util.ToolsPersianCalendar;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.View.ToastMessageBar;
import hami.nasimbehesht724.View.ToolsFlightOption;
import hami.nasimbehesht724.View.ToolsHotelRoomCountOption;

/**
 * Created by renjer on 2018-04-18.
 */

public class SearchBarService extends LinearLayout {
    private AppCompatCheckBox checkBox;
    private View view;
    private RatingBar ratingBar;
    private TextView txtTitle;
    private EditText edtFromPlaceDest, edtFromPlaceCity, edtCheckInDate, edtCheckOutDate;
    private AppCompatEditText edtNationality, edtSetRoom;
    private EditText edtFromPlace, edtToPlace;
    private AppCompatEditText edtFromDate, edtToDate;
    private ImageView imgMovement;
    private TabLayout tabLayout;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    //-----------------------------------------------
    private SearchBusRequest searchBusRequest;
    private TrainRequest trainRequest;
    private FlightInternationalRequest flightRequest;
    private DomesticRequest domesticRequest;
    private InternationalHotelSearchRequest internationalHotelSearchRequest;
    private DomesticHotelSearchRequest domesticHotelSearchRequest;
    private int serviceIdSelected = ServiceID.SERVICE_ID_FLIGHT_DOMESTIC;
    private LinearLayout searchBarMaster;
    private LinearLayout searchBarHotel;
    //-----------------------------------------------
    private MainActivityMaterial mainActivityMaterial;
    private Context context;

    //-----------------------------------------------
    public SearchBarService(Context context) {
        super(context);
        this.context = context;
        ini(context);
    }

    public SearchBarService(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        ini(context);
    }

    //-----------------------------------------------
    public void setActivity(MainActivityMaterial mainActivityMaterial) {
        this.mainActivityMaterial = mainActivityMaterial;
    }
    //-----------------------------------------------

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, serviceIdSelected, searchBusRequest, trainRequest, flightRequest, domesticRequest, internationalHotelSearchRequest, domesticHotelSearchRequest);
    }

    //-----------------------------------------------
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        flightRequest = savedState.getFlightRequest();
        domesticRequest = savedState.getDomesticRequest();
        trainRequest = savedState.getTrainRequest();
        searchBusRequest = savedState.getSearchBusRequest();
        domesticHotelSearchRequest = savedState.getDomesticHotelSearchRequest();
        internationalHotelSearchRequest = savedState.getInternationalHotelSearchRequest();
    }

    //-----------------------------------------------
    private void ini(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.include_search_bar_service, this);
        UtilFonts.overrideFonts(context, view, UtilFonts.IRAN_SANS_NORMAL);
        searchBarMaster = view.findViewById(R.id.searchBarMaster);
        searchBarHotel = view.findViewById(R.id.layoutHotel);
        setCallBackSearch();
    }

    //-----------------------------------------------
    public int getServiceIdSelected() {
        return serviceIdSelected;
    }
    //-----------------------------------------------

    public void setServiceIdSelected(int serviceIdSelected) {
        this.serviceIdSelected = serviceIdSelected;
    }

    //-----------------------------------------------
    public void setCallBackFromPlace(OnClickListener callback) {
        edtFromPlace.setOnClickListener(callback);
    }

    //-----------------------------------------------
    public void setCallBackDestPlaceHotel(OnClickListener callback) {
        edtFromPlaceDest.setOnClickListener(callback);
    }

    //-----------------------------------------------
    public void setCallBackCityPlaceHotel(OnClickListener callback) {
        edtFromPlaceCity.setOnClickListener(callback);
    }

    //-----------------------------------------------
    private void setCallBackSearch() {
        AppCompatButton btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(onClickListener);
    }

    //-----------------------------------------------
    public void setCallBackToPlace(OnClickListener callback) {
        edtToPlace.setOnClickListener(callback);
    }

    //-----------------------------------------------
    public void setCallBackCountry(OnClickListener callback) {
        edtNationality.setOnClickListener(callback);
    }

    //-----------------------------------------------
    public void setupPlaceBus(Boolean hasTakeOff, City city) {
        if (hasTakeOff) {
            if (searchBusRequest.getDestinationBus() != null && searchBusRequest.getDestinationBus().contentEquals(city.getCid())) {
                ToastMessageBar.show(context, R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                searchBusRequest.setFromCity(city.getCityName());
                searchBusRequest.setSourceBus(city.getCid());
                edtFromPlace.setText(city.getCityName());
            }
        } else {
            if (searchBusRequest.getSourceBus() != null && searchBusRequest.getSourceBus().contentEquals(city.getCid())) {
                ToastMessageBar.show(context, R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {

                searchBusRequest.setToCity(city.getCityName());
                searchBusRequest.setDestinationBus(city.getCid());
                edtToPlace.setText(city.getCityName());
            }
        }
    }

    //-----------------------------------------------
    public void setupPlaceTrain(Boolean hasTakeOff, CityTrain city) {
        if (hasTakeOff) {
            if (trainRequest.getDestinationTrain() != null && trainRequest.getDestinationTrain().contentEquals(city.getCityEng())) {
                ToastMessageBar.show(context, R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                trainRequest.setSourceTrain(city.getCityEng());
                trainRequest.setSourceTrainFa(city.getCityPersian());
                edtFromPlace.setText(city.getCityPersian());
            }
        } else {
            if (trainRequest.getSourceTrain() != null && trainRequest.getSourceTrain().contentEquals(city.getCityEng())) {
                ToastMessageBar.show(context, R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                trainRequest.setDestinationTrain(city.getCityEng());
                trainRequest.setDestinationTrainFa(city.getCityPersian());
                edtToPlace.setText(city.getCityPersian());
            }
        }
    }

    //-----------------------------------------------
    public void setPlaceFlightDomestic(SearchInternational fromPlaceFlight, Boolean hasTakeOff) {
        if (domesticRequest == null) {
            domesticRequest = new DomesticRequest();
        }
        if (hasTakeOff) {
            if (domesticRequest.getDestination() != null && domesticRequest.getDestination().contentEquals(fromPlaceFlight.getYata())) {
                ToastMessageBar.show(context, R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                edtFromPlace.setText(fromPlaceFlight.getDataF());
                domesticRequest.setSource(fromPlaceFlight.getYata());
                domesticRequest.setSourcePersian(fromPlaceFlight.getDataF());
            }
        } else {
            if (domesticRequest.getSource() != null && domesticRequest.getSource().contentEquals(fromPlaceFlight.getYata())) {
                ToastMessageBar.show(context, R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                edtToPlace.setText(fromPlaceFlight.getDataF());
                domesticRequest.setDestination(fromPlaceFlight.getYata());
                domesticRequest.setDestinationPersian(fromPlaceFlight.getDataF());
            }
        }
    }

    //-----------------------------------------------
    public void setupPlaceFlightInternational(SearchInternational searchInternational, Boolean hasTakeOff) {
        if (hasTakeOff) {
            if (flightRequest.getDestination() != null && flightRequest.getDestination().contentEquals(searchInternational.getYata())) {
                ToastMessageBar.show(context, R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                flightRequest.setOrigin(searchInternational.getYata());
                flightRequest.setOriginFlag(searchInternational.getParent());
                flightRequest.setOriginPersian(searchInternational.getDataF());
                edtFromPlace.setText(searchInternational.getDataF());
            }
        } else {
            if (flightRequest.getOrigin() != null && flightRequest.getOrigin().contentEquals(searchInternational.getYata())) {
                ToastMessageBar.show(context, R.string.msgErrorEqualFromPlaceWithToPlace);
            } else {
                flightRequest.setDestination(searchInternational.getYata());
                flightRequest.setDestinationFlag(searchInternational.getParent());
                flightRequest.setDestinationPersian(searchInternational.getDataF());
                edtToPlace.setText(searchInternational.getDataF());
            }
        }
    }

    //-----------------------------------------------
    public void setupPlaceDestHotelInternational(SearchDestination searchDestination) {
        edtFromPlaceDest.setText(searchDestination.getName());
        internationalHotelSearchRequest.setCountry(searchDestination.getName());
        internationalHotelSearchRequest.setCountryId(searchDestination.getId());
    }

    //-----------------------------------------------
    public void setupPlaceCityHotelInternational(SearchCity searchCity) {
        edtFromPlaceCity.setText(searchCity.getName());
        internationalHotelSearchRequest.setCityId(searchCity.getCode());
        internationalHotelSearchRequest.setCityName(searchCity.getName());
    }

    //-----------------------------------------------
    public void setupCountry(Country country) {
        edtNationality.setText(country.getFullName());
        internationalHotelSearchRequest.setNationality(country.getCode());
    }

    //-----------------------------------------------
    public void setupPlaceCityHotelDomestic(HotelDomesticCity searchDestination) {
        edtFromPlaceDest.setText(searchDestination.getNameFa() + "," + searchDestination.getName());
        domesticHotelSearchRequest.setCityNamePersian(searchDestination.getNameFa());
        domesticHotelSearchRequest.setCityNameEng(searchDestination.getName());
    }

    //-----------------------------------------------
    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edtFromDate:
                    showDateForService(false);
                    break;
                case R.id.edtToDate:
                    showDateForService(true);
                    break;
                case R.id.btnSearch:
                    search();
                    break;
                case R.id.imgMovementPlace:
                    movementService();
                    break;
            }
        }
    };

    //-----------------------------------------------
    private void movementService() {
        if (serviceIdSelected == ServiceID.SERVICE_ID_FLIGHT_DOMESTIC) {
            movementFlightDomestic();
        } else if (serviceIdSelected == ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL) {
            movementFlightInternational();
        } else if (serviceIdSelected == ServiceID.SERVICE_ID_TRAIN) {
            movementTrain();
        } else if (serviceIdSelected == ServiceID.SERVICE_ID_BUS) {
            movementBus();
        }
    }

    //-----------------------------------------------
    private void movementFlightDomestic() {
        if (domesticRequest.getSource() == null || domesticRequest.getSource().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
            edtFromPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(context, R.string.showCaseFromPlace);
            return;
        } else if (domesticRequest.getDestination() == null || domesticRequest.getDestination().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
            edtToPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(context, R.string.showCaseToPlace);
            return;
        }
        domesticRequest.movementSourceWithDest();
        edtFromPlace.setText(domesticRequest.getSourcePersian());
        edtToPlace.setText(domesticRequest.getDestinationPersian());
    }

    //-----------------------------------------------
    private void movementFlightInternational() {
        if (flightRequest.getOrigin() == null || flightRequest.getOrigin().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
            edtFromPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(context, R.string.showCaseFromPlace);
            return;
        } else if (flightRequest.getDestination() == null || flightRequest.getDestination().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
            edtToPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(context, R.string.showCaseToPlace);
            return;
        }
        flightRequest.movementSourceWithDest();
        edtFromPlace.setText(flightRequest.getOriginPersian());
        edtToPlace.setText(flightRequest.getDestinationPersian());
    }

    //-----------------------------------------------
    private void movementTrain() {
        if (trainRequest.getSourceTrain() == null || trainRequest.getSourceTrain().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
            edtFromPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(context, R.string.showCaseFromPlace);
            return;
        } else if (trainRequest.getDestinationTrain() == null || trainRequest.getDestinationTrain().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
            edtToPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(context, R.string.showCaseToPlace);
            return;
        }
        trainRequest.movementSourceWithDest();
        edtFromPlace.setText(trainRequest.getSourceTrainFa());
        edtToPlace.setText(trainRequest.getDestinationTrainFa());
    }

    //-----------------------------------------------
    private void movementBus() {
        if (searchBusRequest.getSourceBus() == null || searchBusRequest.getSourceBus().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
            edtFromPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(context, R.string.showCaseFromPlace);
            return;
        } else if (searchBusRequest.getDestinationBus() == null || searchBusRequest.getDestinationBus().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
            edtToPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(context, R.string.showCaseToPlace);
            return;
        }
        searchBusRequest.movementSourceWithDest();
        edtFromPlace.setText(searchBusRequest.getSourceBus());
        edtToPlace.setText(searchBusRequest.getDestinationBus());
    }

    //-----------------------------------------------
    private void search() {
        Intent intent = null;
        if (serviceIdSelected == ServiceID.SERVICE_ID_FLIGHT_DOMESTIC && validateFormDomestic()) {
            intent = new Intent(context, ActivityMainFlight.class);
            intent.putExtra(ActivityMainFlight.TAG_FLIGHT_INTERNATIONAL, false);
            intent.putExtra(DomesticRequest.class.getName(), domesticRequest);
            context.startActivity(intent);
        } else if (serviceIdSelected == ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL && validateFormInternational()) {
            intent = new Intent(context, ActivityMainFlight.class);
            intent.putExtra(ActivityMainFlight.TAG_FLIGHT_INTERNATIONAL, true);
            intent.putExtra(FlightInternationalRequest.class.getName(), flightRequest);
            context.startActivity(intent);
        } else if (serviceIdSelected == ServiceID.SERVICE_ID_BUS && validateFormBus()) {
            intent = new Intent(context, ActivityMainBus.class);
            intent.putExtra(SearchBusRequest.class.getName(), searchBusRequest);
            context.startActivity(intent);
        } else if (serviceIdSelected == ServiceID.SERVICE_ID_TRAIN && validateFormTrain()) {
            intent = new Intent(context, ActivityMainTrain.class);
            intent.putExtra(TrainRequest.class.getName(), trainRequest);
            context.startActivity(intent);

        } else if ((serviceIdSelected == ServiceID.SERVICE_ID_HOTEL_DESTINATION || serviceIdSelected == ServiceID.SERVICE_ID_HOTEL_CITY) && validateFormHotelInternational()) {
            searchHotelInternational();

        } else if (serviceIdSelected == ServiceID.SERVICE_ID_HOTEL_DESTINATION_DOMESTIC && validateFormHotelDomestic()) {
            searchHotelDomestic();

        }


    }

    //-----------------------------------------------
    private void searchHotelDomestic() {
        if (validateFormHotelDomestic()) {
            Intent intent = new Intent(context, ActivityMainHotel.class);
            intent.putExtra(DomesticHotelSearchRequest.class.getName(), domesticHotelSearchRequest);
            context.startActivity(intent);
        }
    }

    //-----------------------------------------------
    private void searchHotelInternational() {
        if (validateFormHotelInternational()) {
            Intent intent = new Intent(context, ActivityMainHotel.class);
            intent.putExtra(InternationalHotelSearchRequest.INTENT_HOTEL_SEARCH_REQUEST, internationalHotelSearchRequest.toString());
            intent.putExtra(InternationalHotelSearchRequest.class.getName(), internationalHotelSearchRequest);
            context.startActivity(intent);
        }
    }

    //-----------------------------------------------
    private Boolean validateFormHotelInternational() {
        try {
            if (internationalHotelSearchRequest.getCountry() == null || internationalHotelSearchRequest.getCountry().length() == 0 && internationalHotelSearchRequest.getCountryId() == null || internationalHotelSearchRequest.getCountryId().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromPlaceDest.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(context, R.string.showCaseFromPlaceDestEng);
                return false;
            } else if (internationalHotelSearchRequest.getCityName() == null || internationalHotelSearchRequest.getCityName().length() == 0 || internationalHotelSearchRequest.getCityId() == null || internationalHotelSearchRequest.getCityId().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromPlaceCity.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(context, R.string.showCaseFromPlaceCityEng);
                return false;
            } else if (internationalHotelSearchRequest.getCheckin() == null || internationalHotelSearchRequest.getCheckin().length() == 0 || internationalHotelSearchRequest.getCheckinLongDateString() == null) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtCheckInDate.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(context, R.string.showCaseCheckInEng);
                return false;
            } else if (internationalHotelSearchRequest.getCheckout() == null || internationalHotelSearchRequest.getCheckout().length() == 0 || internationalHotelSearchRequest.getCheckoutLongDateString() == null) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtCheckOutDate.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(context, R.string.showCaseCheckOutEng);
                return false;
            } else if (internationalHotelSearchRequest.getNationality() == null || internationalHotelSearchRequest.getNationality().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtNationality.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(context, R.string.showCaseNationalityEng);
                return false;
            } else if (internationalHotelSearchRequest.getRooms() == null || internationalHotelSearchRequest.getRooms().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtSetRoom.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(context, R.string.showCaseRoomEng);
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //-----------------------------------------------
    private Boolean validateFormHotelDomestic() {
        try {
            if (domesticHotelSearchRequest.getCityNameEng() == null ||
                    domesticHotelSearchRequest.getCityNameEng().length() == 0 ||
                    domesticHotelSearchRequest.getCityNamePersian() == null ||
                    domesticHotelSearchRequest.getCityNamePersian().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromPlaceDest.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.pleaseToPlace);
                return false;
            } else if (domesticHotelSearchRequest.getCheckIn() == null ||
                    domesticHotelSearchRequest.getCheckIn().length() == 0 ||
                    domesticHotelSearchRequest.getCheckInPersianShort() == null ||
                    domesticHotelSearchRequest.getCheckInPersianShort().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtCheckInDate.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseCheckInDate);
                return false;
            } else if (domesticHotelSearchRequest.getCheckOut() == null ||
                    domesticHotelSearchRequest.getCheckOut().length() == 0 ||
                    domesticHotelSearchRequest.getCheckOutPersianShort() == null ||
                    domesticHotelSearchRequest.getCheckOutPersianShort().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtCheckOutDate.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseCheckOutDate);
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //-----------------------------------------------
    private Boolean validateFormDomestic() {
        try {
            if (domesticRequest.getSource() == null || domesticRequest.getSource().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseFromPlace);
                return false;
            } else if (domesticRequest.getDestination() == null || domesticRequest.getDestination().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseToPlace);
                return false;
            } else if (domesticRequest.getDepartureGo() == null ||
                    domesticRequest.getDepartureGoPersian() == null ||
                    domesticRequest.getDepartureGo().length() == 0 ||
                    domesticRequest.getDepartureGoPersian().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromDate.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.validateErrorDate);
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
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseFromPlace);
                return false;
            } else if (searchBusRequest.getDestinationBus() == null || searchBusRequest.getDestinationBus().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseToPlace);
                return false;
            } else if (searchBusRequest.getDepartureGoBus() == null ||
                    searchBusRequest.getDeparturePersianGoBus() == null ||
                    searchBusRequest.getDepartureGoBus().length() == 0 ||
                    searchBusRequest.getDeparturePersianGoBus().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromDate.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.validateErrorDate);
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
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseFromPlace);
                return false;
            } else if (trainRequest.getDestinationTrain() == null || trainRequest.getDestinationTrain().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseToPlace);
                return false;
            } else if (trainRequest.getDepartureGoTrainEng() == null ||
                    trainRequest.getDepartureGoTrainPersian() == null ||
                    trainRequest.getDepartureGoTrainEng().length() == 0 ||
                    trainRequest.getDepartureGoTrainPersian().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromDate.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.validateErrorDate);
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
            final EditText edtTypeWentAndReturn = view.findViewById(R.id.edtTypeWentAndReturn);
            if (flightRequest.getOrigin() == null || flightRequest.getOrigin().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseFromPlace);
                return false;
            } else if (flightRequest.getDestination() == null || flightRequest.getDestination().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseToPlace);
                return false;
            } else if (flightRequest.getDate1() == null ||
                    flightRequest.getDate1().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromDate.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.validateErrorDate);
                return false;
            } else if (edtTypeWentAndReturn.getTag() != null && (Boolean) edtTypeWentAndReturn.getTag() && (flightRequest.getDate2() == null ||
                    flightRequest.getDate2().length() == 0)) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtToDate.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.validateErrorToDate);
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //-----------------------------------------------
    public void setupFlight() {
        setupSearchMaster(VISIBLE);
        setupHotelMaster(GONE);
        tabLayout = view.findViewById(R.id.tabsService);
        UtilFonts.applyFontTabServicesFlight(mainActivityMaterial, tabLayout);

        setDrawableRight(edtFromPlace, R.mipmap.ic_airplane_to_right);
        setDrawableRight(edtToPlace, R.mipmap.ic_airplane_to_left);
        setDrawableRight(edtFromDate, R.mipmap.ic_went_date_master);
        setDrawableRight(edtToDate, R.mipmap.ic_went_date_master);
        tabLayout.setVisibility(VISIBLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    setupFlightInternational();
                } else {
                    setupFlightDomestic();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setupFlightDomestic();
    }

    //-----------------------------------------------
    public void setupFlightDomestic() {
        setupSearchMaster(VISIBLE);
        setupHotelMaster(GONE);
        edtFromPlace.getText().clear();
        edtToPlace.getText().clear();
        edtFromDate.getText().clear();
        edtToDate.getText().clear();
        domesticRequest = new DomesticRequest();
        serviceIdSelected = ServiceID.SERVICE_ID_FLIGHT_DOMESTIC;
        RelativeLayout layoutToolsSearchPassengerRespina = view.findViewById(R.id.layoutToolsSearchPassengerRespina);
        edtToDate.setVisibility(GONE);
        layoutToolsSearchPassengerRespina.setVisibility(GONE);
    }

    //-----------------------------------------------
    public void setupFlightInternational() {
        setupSearchMaster(VISIBLE);
        setupHotelMaster(GONE);
        flightRequest = new FlightInternationalRequest();
        domesticRequest = null;
        trainRequest = null;
        searchBusRequest = null;
        domesticHotelSearchRequest = null;
        internationalHotelSearchRequest = null;
        serviceIdSelected = ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL;
        edtFromPlace.getText().clear();
        edtToPlace.getText().clear();
        edtFromDate.getText().clear();
        edtToDate.getText().clear();
        RelativeLayout layoutToolsSearchPassengerRespina = view.findViewById(R.id.layoutToolsSearchPassengerRespina);
        edtToDate.setVisibility(VISIBLE);
        final EditText edtTypeWentAndReturn = view.findViewById(R.id.edtTypeWentAndReturn);
        edtTypeWentAndReturn.setFocusable(false);
        edtTypeWentAndReturn.setCursorVisible(false);
        edtTypeWentAndReturn.setText(R.string.twoWay);
        edtTypeWentAndReturn.setTag(true);
        edtTypeWentAndReturn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean hasReturn = (edtTypeWentAndReturn.getTag() == null) ? true : (Boolean) edtTypeWentAndReturn.getTag();
                hasReturn = !hasReturn;
                if (!hasReturn) {
                    flightRequest.setDate2("");
                    flightRequest.setReturnDate("");
                    edtToDate.setText("");
                    edtToDate.setVisibility(GONE);
                    edtTypeWentAndReturn.setText(R.string.oneWay);
                    edtTypeWentAndReturn.setTag(false);
                } else {
                    flightRequest.setDate2("");
                    flightRequest.setReturnDate("");
                    edtToDate.setText("");
                    edtToDate.setVisibility(VISIBLE);
                    edtTypeWentAndReturn.setText(R.string.twoWay);
                    edtTypeWentAndReturn.setTag(true);
                }
            }
        });
        final EditText edtCountPassenger = view.findViewById(R.id.edtCountPassenger);
        edtCountPassenger.setFocusable(false);
        edtCountPassenger.setCursorVisible(false);
        layoutToolsSearchPassengerRespina.setVisibility(VISIBLE);
        edtCountPassenger.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final ToolsFlightOption toolsFlightOption = new ToolsFlightOption(context, flightRequest.getAdult(), flightRequest.getChild(), flightRequest.getInfant());
                UtilFonts.overrideFonts(context, toolsFlightOption, UtilFonts.IRAN_SANS_NORMAL);
                builder.setView(toolsFlightOption);
                final AlertDialog alertDialog = builder.create();
                toolsFlightOption.setCallbackAcceptButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String count = String.valueOf(toolsFlightOption.getCountPassenger());
                        edtCountPassenger.setText(context.getString(R.string.addCountPassenger) + "(" + count + ")");
                        flightRequest.setAdult(toolsFlightOption.getNumberAdults());
                        flightRequest.setChild(toolsFlightOption.getNumberChildren());
                        flightRequest.setInfant(toolsFlightOption.getNumberInfant());
                        alertDialog.dismiss();
                    }
                });
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        String count = String.valueOf(toolsFlightOption.getCountPassenger());
                        edtCountPassenger.setText(context.getString(R.string.addCountPassenger) + "(" + count + ")");
                        flightRequest.setAdult(toolsFlightOption.getNumberAdults());
                        flightRequest.setChild(toolsFlightOption.getNumberChildren());
                        flightRequest.setInfant(toolsFlightOption.getNumberInfant());
                    }
                });
                alertDialog.show();
            }
        });


    }

    //-----------------------------------------------
    public void setupBus() {

        searchBusRequest = new SearchBusRequest();
        domesticRequest = null;
        domesticHotelSearchRequest = null;
        internationalHotelSearchRequest = null;
        trainRequest = null;
        serviceIdSelected = ServiceID.SERVICE_ID_BUS;
        setupSearchMaster(VISIBLE);
        setupHotelMaster(GONE);
        setDrawableRight(edtFromPlace, R.mipmap.ic_bus_went);
        setDrawableRight(edtToPlace, R.mipmap.ic_bus_return);
        setDrawableRight(edtFromDate, R.mipmap.ic_went_date_master);
    }

    //-----------------------------------------------
    public void setupTrain() {
        trainRequest = new TrainRequest();
        domesticRequest = null;
        searchBusRequest = null;
        domesticHotelSearchRequest = null;
        internationalHotelSearchRequest = null;
        serviceIdSelected = ServiceID.SERVICE_ID_TRAIN;
        setupSearchMaster(VISIBLE);
        setupHotelMaster(GONE);
        edtToDate.setVisibility(GONE);
        setDrawableRight(edtFromPlace, R.mipmap.ic_from_train);
        setDrawableRight(edtToPlace, R.mipmap.ic_to_train);
        setDrawableRight(edtFromDate, R.mipmap.ic_went_date_master);


    }


    //-----------------------------------------------
    private void setupSearchMaster(int visibility) {

        tabLayout = view.findViewById(R.id.tabsService);
        imgMovement = view.findViewById(R.id.imgMovementPlace);
        edtFromPlace = view.findViewById(R.id.edtFromPlace);
        edtToPlace = view.findViewById(R.id.edtToPlace);
        edtFromDate = view.findViewById(R.id.edtFromDate);
        edtToDate = view.findViewById(R.id.edtToDate);
        edtFromPlace.getText().clear();
        edtFromPlace.setFocusable(false);
        edtFromPlace.setCursorVisible(false);
        //edtFromPlace.setOnClickListener(onClickListener);
        edtToPlace.getText().clear();
        edtToPlace.setFocusable(false);
        edtToPlace.setCursorVisible(false);

        //edtToPlace.setOnClickListener(onClickListener);
        edtFromDate.getText().clear();
        edtFromDate.setFocusable(false);
        edtFromDate.setCursorVisible(false);
        edtFromDate.setOnClickListener(onClickListener);

        edtToDate.getText().clear();
        edtToDate.setFocusable(false);
        edtToDate.setCursorVisible(false);
        edtToDate.setOnClickListener(onClickListener);
        edtToDate.setVisibility(View.GONE);
        searchBarMaster.setVisibility(visibility);
        RelativeLayout layoutToolsSearchPassengerRespina = view.findViewById(R.id.layoutToolsSearchPassengerRespina);
        layoutToolsSearchPassengerRespina.setVisibility(GONE);
        tabLayout.setVisibility(GONE);
        imgMovement.setOnClickListener(onClickListener);

    }

    //-----------------------------------------------
    private void setupHotelMaster(int visibility) {
        searchBarHotel.setVisibility(visibility);
        edtFromPlaceDest = view.findViewById(R.id.edtFromPlaceDest);
        edtFromPlaceCity = view.findViewById(R.id.edtFromPlaceCity);
        edtCheckInDate = view.findViewById(R.id.edtCheckInDate);
        edtCheckOutDate = view.findViewById(R.id.edtCheckOutDate);
        edtNationality = view.findViewById(R.id.edtNationality);
        edtSetRoom = view.findViewById(R.id.edtSetRoom);

        edtFromPlaceDest.setFocusable(false);
        edtFromPlaceDest.setCursorVisible(false);
        edtFromPlaceDest.setText("");
        //edtFromPlaceDest.setOnClickListener(onClickListenerInternational);

        edtFromPlaceCity.setFocusable(false);
        edtFromPlaceCity.setCursorVisible(false);
        edtFromPlaceCity.setText("");
        //edtFromPlaceCity.setOnClickListener(onClickListenerInternational);

        edtCheckInDate.setFocusable(false);
        edtCheckInDate.setCursorVisible(false);
        edtCheckInDate.setText("");
        edtCheckInDate.setOnClickListener(onClickListenerInternational);

        edtCheckOutDate.setFocusable(false);
        edtCheckOutDate.setCursorVisible(false);
        edtCheckOutDate.setText("");
        edtCheckOutDate.setOnClickListener(onClickListenerInternational);

        edtNationality.setFocusable(false);
        edtNationality.setCursorVisible(false);
        edtNationality.setText("");

        edtSetRoom.setFocusable(false);
        edtSetRoom.setCursorVisible(false);
        edtSetRoom.setText("");
        edtSetRoom.setOnClickListener(onClickListenerInternational);
    }

    //-----------------------------------------------
    public void setupHotel() {
        setupSearchMaster(GONE);
        setupHotelMaster(VISIBLE);
        tabLayout = view.findViewById(R.id.tabsServiceHotel);
        UtilFonts.applyFontTabServicesHotel(mainActivityMaterial, tabLayout);
        setDrawableRight(edtFromPlaceDest, R.mipmap.ic_hotel_domestic);
        setDrawableRight(edtCheckInDate, R.mipmap.ic_went_date_master);
        setDrawableRight(edtCheckOutDate, R.mipmap.ic_went_date_master);
        tabLayout.setVisibility(VISIBLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    setupHotelInternational();
                } else {
                    setupHotelDomestic();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setupHotelDomestic();
    }

    //-----------------------------------------------
    private void setupHotelInternational() {
        trainRequest = null;
        domesticRequest = null;
        searchBusRequest = null;
        domesticHotelSearchRequest = null;
        internationalHotelSearchRequest = new InternationalHotelSearchRequest();
        serviceIdSelected = ServiceID.SERVICE_ID_HOTEL_DESTINATION;
        setDrawableRight(edtFromPlaceCity, R.mipmap.ic_city);
        setDrawableRight(edtNationality, R.mipmap.ic_nationality);
        setDrawableRight(edtSetRoom, R.mipmap.ic_bed);
        edtFromPlaceCity.setVisibility(VISIBLE);
        edtNationality.setVisibility(VISIBLE);
        edtSetRoom.setVisibility(VISIBLE);
        edtFromPlaceDest.setHint(context.getString(R.string.country));
        edtFromPlaceDest.getText().clear();
        edtFromPlaceCity.getText().clear();
        edtCheckInDate.getText().clear();
        edtCheckOutDate.getText().clear();
        edtNationality.getText().clear();
        edtSetRoom.getText().clear();
    }

    //-----------------------------------------------
    private void setupHotelDomestic() {
        trainRequest = null;
        domesticRequest = null;
        searchBusRequest = null;
        domesticHotelSearchRequest = new DomesticHotelSearchRequest();
        internationalHotelSearchRequest = null;
        serviceIdSelected = ServiceID.SERVICE_ID_HOTEL_DESTINATION_DOMESTIC;
        edtFromPlaceCity.setVisibility(GONE);
        edtNationality.setVisibility(GONE);
        edtSetRoom.setVisibility(GONE);
        edtFromPlaceDest.setHint(context.getString(R.string.toPlace));
        edtFromPlaceDest.getText().clear();
        edtCheckInDate.getText().clear();
        edtCheckOutDate.getText().clear();
    }

    //-----------------------------------------------
    View.OnClickListener onClickListenerInternational = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {

                case R.id.edtCheckInDate:
                    if (serviceIdSelected == ServiceID.SERVICE_ID_HOTEL_DESTINATION_DOMESTIC)
                        getDatePersian(false, domesticHotelSearchRequest.getCheckIn());
                    else
                        getDateGregHotel(false, null);
                    break;

                case R.id.edtCheckOutDate:
                    if (serviceIdSelected == ServiceID.SERVICE_ID_HOTEL_DESTINATION_DOMESTIC)
                        getDatePersian(true, domesticHotelSearchRequest.getCheckOut());
                    else
                        getDateGregHotel(true, internationalHotelSearchRequest.getCheckin());
                    break;

                case R.id.edtSetRoom:
                    setRooms();
                    break;
            }
        }
    };

    //-----------------------------------------------
    private void showDateForService(Boolean hasReturn) {
        if (serviceIdSelected == ServiceID.SERVICE_ID_FLIGHT_DOMESTIC) {
            if (domesticRequest.getSource() == null || domesticRequest.getSource().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseFromPlace);
            } else if (domesticRequest.getDestination() == null || domesticRequest.getDestination().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseToPlace);
            } else
                getDatePersian(false, domesticRequest.getDepartureGo());
        } else if (serviceIdSelected == ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL) {
            if (flightRequest.getOrigin() == null || flightRequest.getOrigin().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseFromPlace);
            } else if (flightRequest.getDestination() == null || flightRequest.getDestination().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseToPlace);
            } else {
                String minDateIni = hasReturn ? flightRequest.getDate1() : "";
                getDateGreg(hasReturn, minDateIni);
            }
        } else if (serviceIdSelected == ServiceID.SERVICE_ID_TRAIN) {
            if (trainRequest.getSourceTrain() == null || trainRequest.getSourceTrain().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseFromPlace);
            } else if (trainRequest.getDestinationTrain() == null || trainRequest.getDestinationTrain().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseToPlace);
            } else
                getDatePersian(false, trainRequest.getDepartureGoTrainEng());
        } else if (serviceIdSelected == ServiceID.SERVICE_ID_BUS) {
            if (searchBusRequest.getSourceBus() == null || searchBusRequest.getSourceBus().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtFromPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseFromPlace);
            } else if (searchBusRequest.getDestinationBus() == null || searchBusRequest.getDestinationBus().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                edtToPlace.startAnimation(vibrateAnimation);
                ToastMessageBar.show(context, R.string.showCaseToPlace);
            } else
                getDatePersian(false, searchBusRequest.getDepartureGoBus());
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
                        String dateGeorge = DateFormat.format("yyyy-MM-dd", calendar).toString();
                        persianCalendar.setGregorianChange(calendar.getTime());
                        String weekName = ToolsPersianCalendar.getDayOfWeek(calendar.getTime());
                        String monthName = ToolsPersianCalendar.getPersianMonthString(calendar.getTime());
                        String monthString = ((monthOfYear + 1) > 9) ? String.valueOf(monthOfYear + 1) : "0" + (monthOfYear + 1);
                        String dayString = ((dayOfMonth) > 9) ? String.valueOf(dayOfMonth) : "0" + (dayOfMonth);
                        String persianDateShortYear = String.valueOf(year).substring(2) + "-" + monthString + "-" + dayString;
                        String persianDateShort = year + "-" + monthString + "-" + dayString;
                        edtFromDate.setText(weekName + "," + dayOfMonth + monthName + "," + year);
                        String datePersian = weekName + "," + dayOfMonth + monthName;
                        switch (serviceIdSelected) {
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
                            case ServiceID.SERVICE_ID_HOTEL_DESTINATION_DOMESTIC:
                                if (hasReturn) {
                                    domesticHotelSearchRequest.setCheckOut(dateGeorge);
                                    domesticHotelSearchRequest.setCheckOutPersianShort(datePersian);
                                    domesticHotelSearchRequest.setCheckOutPersian(persianDateShort);
                                    domesticHotelSearchRequest.setCheckInPersianShortYear(persianDateShortYear);
                                    edtCheckOutDate.setText(datePersian);
                                } else {
                                    domesticHotelSearchRequest.setCheckIn(dateGeorge);
                                    domesticHotelSearchRequest.setCheckInPersian(persianDateShort);
                                    domesticHotelSearchRequest.setCheckInPersianShortYear(persianDateShortYear);
                                    domesticHotelSearchRequest.setCheckInPersianShort(datePersian);
                                    edtCheckInDate.setText(datePersian);
                                }

                                break;
                        }
                    }
                },
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );
        datePickerDialog.setMinDate(new PersianCalendar());
        datePickerDialog.show(mainActivityMaterial.getFragmentManager(), "Datepickerdialog");
    }

    //-----------------------------------------------
    private void getDateGreg(final Boolean hasReturn, @Nullable String minDateString) {
        Calendar calendarNow = Calendar.getInstance();
        MonthAdapter.CalendarDay minDate = null;
        if (minDateString != null || minDateString.length() > 0) {
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
        }
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment calendarDatePickerDialogFragment, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        DateFormat df = new DateFormat();
                        String dateGeorge = DateFormat.format("yyyy-MM-dd", calendar.getTime()).toString();
                        if (hasReturn) {
                            String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
                            String monthLongName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            int yearFinal = calendar.get(Calendar.YEAR);
                            String dateReturn = dayLongName + " , " + day + monthLongName + " , " + yearFinal;
                            edtToDate.setText(dateReturn);
                            flightRequest.setReturnDate(dateReturn);
                            flightRequest.setDate2(dateGeorge);
                        } else {
                            String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
                            String monthLongName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            int yearFinal = calendar.get(Calendar.YEAR);
                            String dateWent = dayLongName + " , " + day + monthLongName + " , " + yearFinal;
                            edtFromDate.setText(dateWent);
                            flightRequest.setWentDate(dateWent);
                            flightRequest.setDate1(dateGeorge);
//                            RadioGroup toggleButtonType = (RadioGroup) layoutToolsSearchPassengerRespina.findViewById(R.id.toggleButtonType);
//                            if (toggleButtonType.getCheckedRadioButtonId() == R.id.rbWentAndReturnWays) {
//                                flightRequest.setReturnDate("");
//                                flightRequest.setDate2("");
//                                edtToDate.setText("");
//                            }
                        }
                    }
                })
                .setFirstDayOfWeek(Calendar.SATURDAY)
                .setPreselectedDate(calendarNow.get(Calendar.YEAR), calendarNow.get(Calendar.MONTH), calendarNow.get(Calendar.DAY_OF_MONTH) + 1)
                .setDateRange(minDate, null)
                .setDoneText(context.getString(R.string.accept_))
                .setThemeLight()
                .setCancelText(context.getString(R.string.nevermind));
        cdp.show(mainActivityMaterial.getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
    }

    //-----------------------------------------------
    private void getDateGregHotel(final Boolean hasReturn, @Nullable String minDateString) {
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
            if (hasReturn && internationalHotelSearchRequest.getCheckout() != null && internationalHotelSearchRequest.getCheckout().length() > 0)

                calendarNow.setTime(TimeDate.getDate(internationalHotelSearchRequest.getCheckout()).getTime());
            else if (internationalHotelSearchRequest.getCheckin() != null && internationalHotelSearchRequest.getCheckin().length() > 0) {
                minDate = new MonthAdapter.CalendarDay(calendarNow);
                calendarNow.setTime(TimeDate.getDate(internationalHotelSearchRequest.getCheckin()).getTime());
            }
        }
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment calendarDatePickerDialogFragment, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        DateFormat df = new DateFormat();
                        String dateGeorge = DateFormat.format("yyyy-MM-dd", calendar.getTime()).toString();
                        if (hasReturn) {
                            String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
                            String monthLongName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            int yearFinal = calendar.get(Calendar.YEAR);
                            String dateReturn = dayLongName + " , " + day + monthLongName + " , " + yearFinal;
                            edtCheckOutDate.setText(dateReturn);
                            internationalHotelSearchRequest.setCheckout(dateGeorge);
                            internationalHotelSearchRequest.setCheckoutLongDateString(dateReturn);
                        } else {
                            String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
                            String monthLongName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            int yearFinal = calendar.get(Calendar.YEAR);
                            String dateWent = dayLongName + " , " + day + monthLongName + " , " + yearFinal;
                            edtCheckInDate.setText(dateWent);
                            internationalHotelSearchRequest.setCheckin(dateGeorge);
                            internationalHotelSearchRequest.setCheckinLongDateString(dateWent);
                        }
                    }
                })
                .setFirstDayOfWeek(Calendar.SATURDAY)
                .setPreselectedDate(calendarNow.get(Calendar.YEAR), calendarNow.get(Calendar.MONTH), calendarNow.get(Calendar.DAY_OF_MONTH) + 1)
                .setDateRange(minDate, null)
                .setDoneText(context.getString(R.string.acceptEng))
                .setThemeLight()
                .setCancelText(context.getString(R.string.neverMindEng));

        cdp.show(mainActivityMaterial.getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
    }

    //-----------------------------------------------
    public DomesticHotelSearchRequest getDomesticHotelSearchRequest() {
        return domesticHotelSearchRequest;
    }

    //-----------------------------------------------
    public DomesticRequest getDomesticRequest() {
        return domesticRequest;
    }

    //-----------------------------------------------
    public FlightInternationalRequest getFlightRequest() {
        return flightRequest;
    }

    //-----------------------------------------------
    public TrainRequest getTrainRequest() {
        return trainRequest;
    }

    //-----------------------------------------------
    public SearchBusRequest getSearchBusRequest() {
        return searchBusRequest;
    }

    //-----------------------------------------------
    public InternationalHotelSearchRequest getInternationalHotelSearchRequest() {
        return internationalHotelSearchRequest;
    }

    //-----------------------------------------------
    private void setDrawableRight(EditText editText, int iconResource) {
        Drawable img = getContext().getResources().getDrawable(iconResource);
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
    }

    //-----------------------------------------------
    private void setRooms() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final ToolsHotelRoomCountOption toolsHotelRoomCountOption = new ToolsHotelRoomCountOption(context);
        UtilFonts.overrideFonts(context, toolsHotelRoomCountOption, UtilFonts.IRAN_SANS_NORMAL);
        builder.setView(toolsHotelRoomCountOption);
        final AlertDialog alertDialog = builder.create();
        //toolsHotelRoomCountOption.setRoomIni(internationalHotelSearchRequest);
        toolsHotelRoomCountOption.setCallbackAcceptButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internationalHotelSearchRequest.setAdults(toolsHotelRoomCountOption.getAdultsList());
                internationalHotelSearchRequest.setAdult(toolsHotelRoomCountOption.getAllAdultCount());
                internationalHotelSearchRequest.setChilds(toolsHotelRoomCountOption.getChildList());
                internationalHotelSearchRequest.setChild(toolsHotelRoomCountOption.getAllChildCount());
                internationalHotelSearchRequest.setChildages(toolsHotelRoomCountOption.getChildAges());
                internationalHotelSearchRequest.setChildages(toolsHotelRoomCountOption.getChildAges());
                internationalHotelSearchRequest.setChildage(toolsHotelRoomCountOption.getChildAge());
                internationalHotelSearchRequest.setRooms(toolsHotelRoomCountOption.getRoomCount());
                edtSetRoom.setText(toolsHotelRoomCountOption.getRoomCount() + " " + context.getString(R.string.roomEng));
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    //-----------------------------------------------
    protected static class SavedState extends BaseSavedState {


        private SearchBusRequest searchBusRequest;
        private TrainRequest trainRequest;
        private FlightInternationalRequest flightRequest;
        private DomesticRequest domesticRequest;
        private InternationalHotelSearchRequest internationalHotelSearchRequest;
        private DomesticHotelSearchRequest domesticHotelSearchRequest;
        private int serviceIdSelected = -1;

        //-----------------------------------------------
        private SavedState(Parcelable superState,
                           int serviceIdSelected,
                           SearchBusRequest searchBusRequest,
                           TrainRequest trainRequest,
                           FlightInternationalRequest flightRequest,
                           DomesticRequest domesticRequest,
                           InternationalHotelSearchRequest internationalHotelSearchRequest,
                           DomesticHotelSearchRequest domesticHotelSearchRequest) {
            super(superState);
            this.serviceIdSelected = serviceIdSelected;
            this.searchBusRequest = searchBusRequest;
            this.trainRequest = trainRequest;
            this.flightRequest = flightRequest;
            this.domesticRequest = domesticRequest;
            this.internationalHotelSearchRequest = internationalHotelSearchRequest;
            this.domesticHotelSearchRequest = domesticHotelSearchRequest;
        }

        private SavedState(Parcel in) {
            super(in);
            serviceIdSelected = in.readInt();
            searchBusRequest = (SearchBusRequest) in.readSerializable();
            trainRequest = (TrainRequest) in.readSerializable();
            flightRequest = in.readParcelable(getClass().getClassLoader());
            internationalHotelSearchRequest = in.readParcelable(getClass().getClassLoader());
            domesticHotelSearchRequest = in.readParcelable(getClass().getClassLoader());
        }

        public DomesticHotelSearchRequest getDomesticHotelSearchRequest() {
            return domesticHotelSearchRequest;
        }

        public DomesticRequest getDomesticRequest() {
            return domesticRequest;
        }

        public FlightInternationalRequest getFlightRequest() {
            return flightRequest;
        }

        public int getServiceIdSelected() {
            return serviceIdSelected;
        }

        public InternationalHotelSearchRequest getInternationalHotelSearchRequest() {
            return internationalHotelSearchRequest;
        }

        public SearchBusRequest getSearchBusRequest() {
            return searchBusRequest;
        }

        public TrainRequest getTrainRequest() {
            return trainRequest;
        }

        @Override
        public void writeToParcel(Parcel destination, int flags) {
            super.writeToParcel(destination, flags);
            destination.writeInt(serviceIdSelected);
            destination.writeSerializable(flightRequest);
            destination.writeSerializable(trainRequest);
            destination.writeSerializable(searchBusRequest);
            destination.writeSerializable(domesticRequest);
            destination.writeParcelable(internationalHotelSearchRequest, flags);
            destination.writeSerializable(domesticHotelSearchRequest);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }

        };

    }
}
