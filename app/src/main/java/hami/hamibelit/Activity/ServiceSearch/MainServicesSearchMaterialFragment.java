package hami.hamibelit.Activity.ServiceSearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import hami.hamibelit.Activity.SearchBarService;
import hami.hamibelit.Activity.ServiceHotel.Domestic.Controller.Model.HotelDomesticCity;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.SearchCity;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.SearchDestination;
import hami.hamibelit.Activity.ServiceHotel.SearchPlaceHotelActivity;
import hami.hamibelit.Activity.ServiceSearch.ConstService.ServiceID;
import hami.hamibelit.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.City;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.SearchCountryActivity;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.Country;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.SearchInternational;
import hami.hamibelit.Activity.ServiceSearch.ServiceTrain.SearchPlaceTrainActivity;
import hami.hamibelit.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.CityTrain;
import hami.hamibelit.BaseController.AccessStatusResponse;
import hami.hamibelit.MainActivityMaterial;
import hami.hamibelit.R;
import hami.hamibelit.Util.Keyboard;
import hami.hamibelit.Util.UtilFonts;

public class MainServicesSearchMaterialFragment extends Fragment {

    private AccessStatusResponse accessStatusResponse;
    private static final String TAG = "MainServicesSearchMaterialFragment";
    private View view;
    private SearchBarService searchBarService;
    //-----------------------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accessStatusResponse = (AccessStatusResponse) getArguments().getSerializable(AccessStatusResponse.class.getName());
        }
    }
    //-----------------------------------------------

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            accessStatusResponse = (AccessStatusResponse) savedInstanceState.getSerializable(AccessStatusResponse.class.getName());
        }
        super.onActivityCreated(savedInstanceState);
    }

    //-----------------------------------------------

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(AccessStatusResponse.class.getName(), accessStatusResponse);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    public static MainServicesSearchMaterialFragment newInstance(AccessStatusResponse accessStatusResponse) {
        Bundle args = new Bundle();
        MainServicesSearchMaterialFragment fragment = new MainServicesSearchMaterialFragment();
        args.putSerializable(AccessStatusResponse.class.getName(), accessStatusResponse);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_material, container, false);
        initialComponentFragment(view);
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment(View view) {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        setupService(view);
        Keyboard.closeKeyboard(getActivity());
    }


    //-----------------------------------------------
    private void setupService(final View view) {
        searchBarService = (SearchBarService) view.findViewById(R.id.searchBarService);
        MainActivityMaterial mainActivityMaterial = (MainActivityMaterial) getActivity();
        searchBarService.setActivity(mainActivityMaterial);
        LinearLayout layoutTour = (LinearLayout) view.findViewById(R.id.layoutTour);
        LinearLayout layoutHotel = (LinearLayout) view.findViewById(R.id.layoutHotel);
        LinearLayout layoutBus = (LinearLayout) view.findViewById(R.id.layoutBus);
        LinearLayout layoutTrain = (LinearLayout) view.findViewById(R.id.layoutTrain);
        LinearLayout layoutFlight = (LinearLayout) view.findViewById(R.id.layoutFlight);
        layoutTour.setOnClickListener(onClickServiceListener);
        layoutHotel.setOnClickListener(onClickServiceListener);
        layoutBus.setOnClickListener(onClickServiceListener);
        layoutTrain.setOnClickListener(onClickServiceListener);
        layoutFlight.setOnClickListener(onClickServiceListener);
        layoutHotel.setVisibility((accessStatusResponse.getDomesticHotel() && accessStatusResponse.getInternationalHotel()) ? View.VISIBLE : View.GONE);
        layoutTour.setVisibility((accessStatusResponse.getTour()) ? View.VISIBLE : View.GONE);
        layoutFlight.setVisibility((accessStatusResponse.getFlight() && accessStatusResponse.getInternational()) ? View.VISIBLE : View.GONE);
        layoutTrain.setVisibility((accessStatusResponse.getTrain()) ? View.VISIBLE : View.GONE);
        layoutBus.setVisibility((accessStatusResponse.getBus()) ? View.VISIBLE : View.GONE);
        setup();
    }

    //-----------------------------------------------
    private View.OnClickListener onClickServiceListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layoutTour:
                    break;
                case R.id.layoutHotel:
                    searchBarService.setupHotel();
                    break;
                case R.id.layoutTrain:
                    searchBarService.setupTrain();
                    break;
                case R.id.layoutBus:
                    searchBarService.setupBus();
                    break;
                case R.id.layoutFlight:
                    searchBarService.setupFlight();
                    break;
            }
        }
    };

    //-----------------------------------------------
    private void setup() {
        searchBarService.setupFlight();
        searchBarService.setCallBackFromPlace(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchPlaceMainActivity.class);
                intent.putExtra(SearchPlaceMainActivity.INTENT_HAS_TAKE_OFF, true);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, searchBarService.getServiceIdSelected());
                startActivityForResult(intent, 0);
            }
        });
        searchBarService.setCallBackToPlace(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchPlaceMainActivity.class);
                intent.putExtra(SearchPlaceMainActivity.INTENT_HAS_TAKE_OFF, false);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, searchBarService.getServiceIdSelected());
                startActivityForResult(intent, 0);
            }
        });
        searchBarService.setCallBackDestPlaceHotel(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (searchBarService.getServiceIdSelected() == ServiceID.SERVICE_ID_HOTEL_DESTINATION_DOMESTIC) {
                    intent = new Intent(getActivity(), SearchPlaceMainActivity.class);
                    intent.putExtra(SearchPlaceMainActivity.INTENT_HAS_TAKE_OFF, false);
                    intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_ID_HOTEL_DESTINATION_DOMESTIC);
                } else {
                    intent = new Intent(getActivity(), SearchPlaceHotelActivity.class);
                    intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_ID_HOTEL_DESTINATION);
                }
                startActivityForResult(intent, 0);
            }
        });
        searchBarService.setCallBackCityPlaceHotel(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBarService.setServiceIdSelected(ServiceID.SERVICE_ID_HOTEL_CITY);
                Intent intent = new Intent(getActivity(), SearchPlaceHotelActivity.class);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_ID_HOTEL_CITY);
                intent.putExtra(ServiceID.INTENT_DESTINATION_ID, searchBarService.getInternationalHotelSearchRequest().getCountryId());
                startActivityForResult(intent, 0);
            }
        });
        searchBarService.setCallBackCountry(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchCountryActivity.class);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_COUNTRY);
                startActivityForResult(intent, 0);

            }
        });

    }

    //-----------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (data != null && data.getExtras() != null) {
                if (resultCode == ServiceID.SERVICE_ID_BUS) {
                    City city = (City) data.getExtras().getSerializable(City.class.getName());
                    searchBarService.setupPlaceBus(data.getExtras().getBoolean(SearchPlaceMainActivity.INTENT_HAS_TAKE_OFF), city);
                } else if (resultCode == ServiceID.SERVICE_ID_TRAIN) {
                    CityTrain city = (CityTrain) data.getExtras().getSerializable(CityTrain.class.getName());
                    searchBarService.setupPlaceTrain(data.getExtras().getBoolean(SearchPlaceTrainActivity.HAS_TAKE_OFF), city);
                } else if (resultCode == ServiceID.SERVICE_ID_FLIGHT_DOMESTIC) {
                    SearchInternational city = (SearchInternational) data.getExtras().getSerializable(SearchInternational.class.getName());
                    Boolean hasTakeOff = data.getExtras().getBoolean(SearchPlaceTrainActivity.HAS_TAKE_OFF);
                    searchBarService.setPlaceFlightDomestic(city, hasTakeOff);
                } else if (resultCode == ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL) {
                    SearchInternational city = (SearchInternational) data.getExtras().getSerializable(SearchInternational.class.getName());
                    Boolean hasTakeOff = data.getExtras().getBoolean(SearchPlaceTrainActivity.HAS_TAKE_OFF);
                    searchBarService.setupPlaceFlightInternational(city, hasTakeOff);
                } else if (resultCode == ServiceID.SERVICE_ID_HOTEL_DESTINATION) {
                    SearchDestination searchDestination = (SearchDestination) data.getExtras().getSerializable(SearchDestination.class.getName());
                    searchBarService.setupPlaceDestHotelInternational(searchDestination);
                } else if (resultCode == ServiceID.SERVICE_ID_HOTEL_CITY) {
                    SearchCity searchCity = (SearchCity) data.getExtras().getSerializable(SearchCity.class.getName());
                    searchBarService.setupPlaceCityHotelInternational(searchCity);
                } else if (resultCode == ServiceID.SERVICE_COUNTRY) {
                    Country country = (Country) data.getExtras().getSerializable(Country.class.getName());
                    searchBarService.setupCountry(country);
                }
                if (resultCode == ServiceID.SERVICE_ID_HOTEL_DESTINATION_DOMESTIC) {
                    HotelDomesticCity searchDestination = (HotelDomesticCity) data.getExtras().getSerializable(HotelDomesticCity.class.getName());
                    searchBarService.setupPlaceCityHotelDomestic(searchDestination);
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //-----------------------------------------------
}
