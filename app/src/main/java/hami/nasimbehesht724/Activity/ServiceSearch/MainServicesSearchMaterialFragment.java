package hami.nasimbehesht724.Activity.ServiceSearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import hami.nasimbehesht724.Activity.SearchBarService;
import hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model.HotelDomesticCity;
import hami.nasimbehesht724.Activity.ServiceHotel.International.Controller.Model.SearchCity;
import hami.nasimbehesht724.Activity.ServiceHotel.International.Controller.Model.SearchDestination;
import hami.nasimbehesht724.Activity.ServiceHotel.SearchPlaceHotelActivity;
import hami.nasimbehesht724.Activity.ServiceSearch.ConstService.ServiceID;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.City;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.SearchCountryActivity;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.Country;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.SearchInternational;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.SearchPlaceTrainActivity;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.CityTrain;
import hami.nasimbehesht724.BaseController.AccessStatusResponse;
import hami.nasimbehesht724.MainActivityMaterial;
import hami.nasimbehesht724.MainActivityMaterialTourService;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.Keyboard;
import hami.nasimbehesht724.Util.UtilFonts;

public class MainServicesSearchMaterialFragment extends Fragment {

    private AccessStatusResponse accessStatusResponse;
    private static final String TAG = "MainServicesSearchMaterialFragment";
    private View view;
    private SearchBarService searchBarService;
    private int serviceId;
    private ImageView iv_linePick1, iv_linePick2, iv_linePick3, iv_linePick4, iv_linePick5;
    Context context;
    LinearLayout layoutTour, layoutHotel, layoutBus, layoutTrain, layoutFlight;
    //-----------------------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accessStatusResponse = (AccessStatusResponse) getArguments().getSerializable(AccessStatusResponse.class.getName());
            serviceId = getArguments().getInt(ServiceID.INTENT_SERVICE_ID);
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
    public static MainServicesSearchMaterialFragment newInstance(AccessStatusResponse accessStatusResponse, int serviceId) {
        Bundle args = new Bundle();
        MainServicesSearchMaterialFragment fragment = new MainServicesSearchMaterialFragment();
        args.putSerializable(AccessStatusResponse.class.getName(), accessStatusResponse);
        args.putInt(ServiceID.INTENT_SERVICE_ID, serviceId);
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
        searchBarService = view.findViewById(R.id.searchBarService);
        MainActivityMaterial mainActivityMaterial = (MainActivityMaterial) getActivity();
        searchBarService.setActivity(mainActivityMaterial);
        iv_linePick1 = view.findViewById(R.id.iv_linePick1);
        iv_linePick2 = view.findViewById(R.id.iv_linePick2);
        iv_linePick3 = view.findViewById(R.id.iv_linePick3);
        iv_linePick4 = view.findViewById(R.id.iv_linePick4);
        iv_linePick5 = view.findViewById(R.id.iv_linePick5);
        layoutTour = view.findViewById(R.id.layoutTour);
        layoutHotel = view.findViewById(R.id.layoutHotel);
        layoutBus = view.findViewById(R.id.layoutBus);
        layoutTrain = view.findViewById(R.id.layoutTrain);
        layoutFlight = view.findViewById(R.id.layoutFlight);
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

    //----------------------------------------
    private void checkPermit() {

        if (serviceId == ServiceID.SERVICE_ID_HOTEL) {
            selectItemTab(layoutHotel);
            searchBarService.setupHotel();
        } else if (serviceId == ServiceID.SERVICE_ID_TRAIN) {
            selectItemTab(layoutTrain);
            searchBarService.setupTrain();
        } else if (serviceId == ServiceID.SERVICE_ID_FLIGHT_DOMESTIC) {
            selectItemTab(layoutFlight);
            searchBarService.setupFlight();
        } else if (serviceId == ServiceID.SERVICE_ID_BUS) {
            selectItemTab(layoutBus);
            searchBarService.setupBus();
        } else if (serviceId == ServiceID.SERVICE_ID_TOUR) {
            startActivity(new Intent(getActivity(), MainActivityMaterialTourService.class));
        }
    }

    //-----------------------------------------------
    private View.OnClickListener onClickServiceListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            selectItemTab(v);
        }
    };

    //-----------------------------------------------
    private void selectItemTab(View v) {

        switch (v.getId()) {
            case R.id.layoutTour:

                iv_linePick1.setVisibility(View.INVISIBLE);
                iv_linePick2.setVisibility(View.INVISIBLE);
                iv_linePick3.setVisibility(View.INVISIBLE);
                iv_linePick4.setVisibility(View.INVISIBLE);
                iv_linePick5.setVisibility(View.INVISIBLE);

                startActivity(new Intent(getActivity(), MainActivityMaterialTourService.class));
                break;

            case R.id.layoutHotel:

                searchBarService.setupHotel();

                iv_linePick1.setVisibility(View.INVISIBLE);
                iv_linePick2.setVisibility(View.VISIBLE);
                iv_linePick3.setVisibility(View.INVISIBLE);
                iv_linePick4.setVisibility(View.INVISIBLE);
                iv_linePick5.setVisibility(View.INVISIBLE);
                break;

            case R.id.layoutTrain:

                searchBarService.setupTrain();

                iv_linePick1.setVisibility(View.INVISIBLE);
                iv_linePick2.setVisibility(View.INVISIBLE);
                iv_linePick3.setVisibility(View.VISIBLE);
                iv_linePick4.setVisibility(View.INVISIBLE);
                iv_linePick5.setVisibility(View.INVISIBLE);
                break;

            case R.id.layoutBus:

                searchBarService.setupBus();

                iv_linePick1.setVisibility(View.INVISIBLE);
                iv_linePick2.setVisibility(View.INVISIBLE);
                iv_linePick3.setVisibility(View.INVISIBLE);
                iv_linePick4.setVisibility(View.VISIBLE);
                iv_linePick5.setVisibility(View.INVISIBLE);
                break;

            case R.id.layoutFlight:

                searchBarService.setupFlight();

                iv_linePick1.setVisibility(View.INVISIBLE);
                iv_linePick2.setVisibility(View.INVISIBLE);
                iv_linePick3.setVisibility(View.INVISIBLE);
                iv_linePick4.setVisibility(View.INVISIBLE);
                iv_linePick5.setVisibility(View.VISIBLE);
                break;
        }
    }


    //----------------------------------------
    private void setup() {
        checkPermit();
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
