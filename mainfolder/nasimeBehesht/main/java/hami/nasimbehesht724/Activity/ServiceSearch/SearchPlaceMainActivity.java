package hami.mainapp.Activity.ServiceSearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;



import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import hami.mainapp.Activity.ServiceHotel.Domestic.Adapter.SearchPlaceHotelDomesticAdapter;
import hami.mainapp.Activity.ServiceHotel.Domestic.Controller.Model.HotelDomesticCity;
import hami.mainapp.Activity.ServiceSearch.ConstService.ServiceID;
import hami.mainapp.Activity.ServiceSearch.ServiceBus.Adapter.SearchPlaceBusAdapter;
import hami.mainapp.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.City;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Adapter.SearchPlaceFlightAdapter;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.SearchInternational;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.InternationalApi;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.SearchInternationalPresenter;
import hami.mainapp.Activity.ServiceSearch.ServiceTrain.Adapter.SearchPlaceTrainAdapter;
import hami.mainapp.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.CityTrain;
import hami.mainapp.BaseController.SelectItemList;
import hami.mainapp.R;
import hami.mainapp.Util.Database.BusOffline;
import hami.mainapp.Util.Database.FlightDomesticOffline;
import hami.mainapp.Util.Database.FlightInternationalOffline;
import hami.mainapp.Util.Database.HotelDomesticOffline;
import hami.mainapp.Util.Database.TrainOffline;
import hami.mainapp.Util.Keyboard;
import hami.mainapp.Util.UtilFonts;
import hami.mainapp.View.HeaderBar;
import hami.mainapp.View.ToastMessageBar;

/**
 * Created by renjer on 2017-04-30.
 */

public class SearchPlaceMainActivity extends AppCompatActivity {

    private Boolean hasTakeOff;
    private int serviceId;
    public final static String INTENT_HAS_TAKE_OFF = "INTENT_HAS_TAKE_OFF";
    private SearchPlaceFlightAdapter searchPlaceFlightAdapter;
    private SearchPlaceTrainAdapter searchPlaceTrainAdapter;
    private SearchPlaceBusAdapter searchPlaceAdapter;
    private SearchPlaceHotelDomesticAdapter searchPlaceHotelDomesticAdapter;
    private EditText autoCompleteFromPlace;
    private ImageView imgBtnBack;
    private InternationalApi internationalApi;
    private HeaderBar headerBar;
    private static final String TAG = "SearchPlaceMainActivity";

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_search_place);
        try {
            hasTakeOff = getIntent().getExtras().getBoolean(INTENT_HAS_TAKE_OFF);
            serviceId = getIntent().getExtras().getInt(ServiceID.INTENT_SERVICE_ID);
        } catch (Exception e) {

            ToastMessageBar.show(this, R.string.msgErrorPayment);
            finish();
        }
        initialComponentActivity();
    }
    //-----------------------------------------------

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putBoolean(INTENT_HAS_TAKE_OFF, hasTakeOff);
            outState.putInt(ServiceID.INTENT_SERVICE_ID, serviceId);
        }
        super.onSaveInstanceState(outState);
    }
    //-----------------------------------------------

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            hasTakeOff = savedInstanceState.getBoolean(INTENT_HAS_TAKE_OFF);
            serviceId = savedInstanceState.getInt(ServiceID.INTENT_SERVICE_ID);
        }
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        LinearLayout layoutMain = (LinearLayout) findViewById(R.id.layoutMain);
        UtilFonts.overrideFonts(this, layoutMain, UtilFonts.IRAN_SANS_NORMAL);
        headerBar = (HeaderBar) findViewById(R.id.headerBar);
        setupPlace();
        imgBtnBack = (ImageView) findViewById(R.id.imgBtnBack);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoCompleteFromPlace.getWindowToken(), 0);
                finish();
            }
        });

    }

    //-----------------------------------------------
    private void setupPlace() {
        autoCompleteFromPlace = (EditText) findViewById(R.id.autoCompleteFromPlace);
        int hintString = (hasTakeOff) ? R.string.flyingFrom : R.string.flyingTo;
        autoCompleteFromPlace.setHint(hintString);
        switch (serviceId) {
            case ServiceID.SERVICE_ID_FLIGHT_DOMESTIC:
                headerBar.showMessageBar(R.string.typeCityAndAirport);
                autoCompleteFromPlace.addTextChangedListener(textWatcherFlightDomestic);
                searchPlaceFlightDomestic("");
                break;
            case ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL:
                headerBar.showMessageBar(R.string.typeCityAndAirport);
                internationalApi = new InternationalApi(this);
                setupResultPlaceFlightInternational(new FlightInternationalOffline(SearchPlaceMainActivity.this).getListPlace());
                autoCompleteFromPlace.addTextChangedListener(textWatcherFlightInternational);
                break;
            case ServiceID.SERVICE_ID_TRAIN:
                headerBar.showMessageBar(R.string.typeCity);
                autoCompleteFromPlace.addTextChangedListener(textWatcherTrain);
                searchPlaceTrain("");
                break;
            case ServiceID.SERVICE_ID_BUS:
                headerBar.showMessageBar(R.string.typeCity);
                autoCompleteFromPlace.addTextChangedListener(textWatcherBus);
                searchPlaceBus("");
                break;
            case ServiceID.SERVICE_ID_HOTEL_DESTINATION_DOMESTIC:
                headerBar.showMessageBar(R.string.typeCity);
                autoCompleteFromPlace.addTextChangedListener(textWatcherHotelDomestic);
                searchPlaceHotelDomestic("");
                break;

        }

    }

    //-----------------------------------------------
    //-----------------------Flight Domestic Place-----------------------
    //-----------------------------------------------
    public void searchPlaceFlightDomestic(@Nullable final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (title.length() > 0) {
                    setCityFlight(new FlightDomesticOffline(SearchPlaceMainActivity.this).getCityByName(title));
                } else {
                    setupResultPlaceFlightDomestic(new FlightDomesticOffline(SearchPlaceMainActivity.this).getCityListBusyAndAll());
                }
            }
        });
    }

    //-----------------------------------------------
    private void setCityFlight(ArrayList<SearchInternational> cities) {
        searchPlaceFlightAdapter.setFilter(cities);
    }

    //-----------------------------------------------
    TextWatcher textWatcherFlightDomestic = new TextWatcher() {
        private Timer timer = new Timer();
        private final long DELAY = 200;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {
            if (s.length() >= 1) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                searchPlaceFlightDomestic(s.toString());
                            }
                        },
                        DELAY
                );
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    //-----------------------------------------------
    private void setupResultPlaceFlightDomestic(List<SearchInternational> cities) {
        RecyclerView rvResult = (RecyclerView) findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);
        searchPlaceFlightAdapter = new SearchPlaceFlightAdapter(this, cities, selectItemSearchFlightDomestic);
        rvResult.setAdapter(searchPlaceFlightAdapter);
    }

    //-----------------------------------------------
    SelectItemList<SearchInternational> selectItemSearchFlightDomestic = new SelectItemList<SearchInternational>() {
        @Override
        public void onSelectItem(SearchInternational object, int index) {
            Keyboard.closeKeyboard(SearchPlaceMainActivity.this);
            Intent intent = new Intent();
            intent.putExtra(SearchInternational.class.getName(), object);
            intent.putExtra(INTENT_HAS_TAKE_OFF, hasTakeOff);
            setResult(ServiceID.SERVICE_ID_FLIGHT_DOMESTIC, intent);
            finish();
        }
    };

    //-----------------------------------------------
    //-----------------------Flight International Place-----------------------
    //-----------------------------------------------
    public void searchPlaceFlightInternational(final String title) {

        internationalApi.searchPlace(title, new SearchInternationalPresenter() {
            @Override
            public void onStart() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        headerBar.showProgress();
                    }
                });
            }

            @Override
            public void onErrorServer() {

            }

            @Override
            public void onErrorInternetConnection() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastMessageBar.show(SearchPlaceMainActivity.this, R.string.msgErrorInternetConnection);
                    }
                });
            }

            @Override
            public void onSuccessSearch(final List<SearchInternational> result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupResultPlaceFlightInternational(result);
                    }
                });

            }

            @Override
            public void onError(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastMessageBar.show(SearchPlaceMainActivity.this, R.string.msgErrorPayment);
                    }
                });
            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        headerBar.hideMessageBar();
                    }
                });
            }
        });
    }

    //-----------------------------------------------
    TextWatcher textWatcherFlightInternational = new TextWatcher() {
        private Timer timer = new Timer();
        private final long DELAY = 400;

        //-----------------------------------------------
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {
            if (s.length() >= 3) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                searchPlaceFlightInternational(s.toString());
                            }
                        },
                        DELAY
                );
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //-----------------------------------------------
    private void setupResultPlaceFlightInternational(List<SearchInternational> cities) {
        RecyclerView rvResult = (RecyclerView) findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);
        searchPlaceFlightAdapter = new SearchPlaceFlightAdapter(this, cities, selectItemSearchInternational);
        rvResult.setAdapter(searchPlaceFlightAdapter);
    }

    //-----------------------------------------------
    SelectItemList<SearchInternational> selectItemSearchInternational = new SelectItemList<SearchInternational>() {
        @Override
        public void onSelectItem(SearchInternational object, int index) {
            new FlightInternationalOffline(SearchPlaceMainActivity.this).savePlace(object.getYata(), object.toString());
            Keyboard.closeKeyboard(SearchPlaceMainActivity.this);
            Intent intent = new Intent();
            intent.putExtra(SearchInternational.class.getName(), object);
            intent.putExtra(INTENT_HAS_TAKE_OFF, hasTakeOff);
            setResult(ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL, intent);
            finish();
        }
    };
    //-----------------------------------------------
    //-----------------------Train Place-----------------------
    //-----------------------------------------------

    TextWatcher textWatcherTrain = new TextWatcher() {
        private Timer timer = new Timer();
        private final long DELAY = 300;

        //-----------------------------------------------
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {
            if (s.length() >= 1) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                // TODO: do what you need here (refresh list)
                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                                searchPlaceTrain(s.toString());
                            }
                        },
                        DELAY
                );
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //-----------------------------------------------
    private void setupResultPlaceTrain(List<CityTrain> cities) {
        RecyclerView rvResult = (RecyclerView) findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);
        searchPlaceTrainAdapter = new SearchPlaceTrainAdapter(this, cities, selectItemListTrain);
        rvResult.setAdapter(searchPlaceTrainAdapter);
    }

    //-----------------------------------------------
    private void setCityTrain(List<CityTrain> cities) {
        searchPlaceTrainAdapter.setFilter(cities);
    }

    //-----------------------------------------------
    private void searchPlaceTrain(final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (title.length() > 0) {
                    setCityTrain(new TrainOffline(SearchPlaceMainActivity.this).getCityByName(title));
                } else {
                    setupResultPlaceTrain(new TrainOffline(SearchPlaceMainActivity.this).getAllCityWithHeader());
                }
            }
        });

    }

    //-----------------------------------------------
    SelectItemList<CityTrain> selectItemListTrain = new SelectItemList<CityTrain>() {
        @Override
        public void onSelectItem(CityTrain object, int index) {
            Keyboard.closeKeyboard(SearchPlaceMainActivity.this);
            Intent intent = new Intent();
            intent.putExtra(CityTrain.class.getName(), object);
            intent.putExtra(INTENT_HAS_TAKE_OFF, hasTakeOff);
            setResult(ServiceID.SERVICE_ID_TRAIN, intent);
            finish();
        }
    };

    //-----------------------------------------------
    //-----------------------Bus Place-----------------------
    //-----------------------------------------------
    private void setupResultPlaceBus(List<City> cities) {
        RecyclerView rvResult = (RecyclerView) findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);
        searchPlaceAdapter = new SearchPlaceBusAdapter(this,
                cities,
                selectItemListBus);
        rvResult.setAdapter(searchPlaceAdapter);
    }

    //-----------------------------------------------
    TextWatcher textWatcherBus = new TextWatcher() {
        private Timer timer = new Timer();
        private final long DELAY = 300;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {
            if (s.length() >= 1) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                // TODO: do what you need here (refresh list)
                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                                searchPlaceBus(s.toString());
                            }
                        },
                        DELAY
                );
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //-----------------------------------------------
    private void setCityBus(List<City> cities) {
        searchPlaceAdapter.setFilter(cities);
    }

    //-----------------------------------------------
    private void searchPlaceBus(final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (title.length() > 0) {
                    setCityBus(new BusOffline(SearchPlaceMainActivity.this).getCityByName(title));
                } else {
                    setupResultPlaceBus(new BusOffline(SearchPlaceMainActivity.this).getCityListBusyAndAll());
                }
            }
        });

    }

    //-----------------------------------------------
    SelectItemList<City> selectItemListBus = new SelectItemList<City>() {
        @Override
        public void onSelectItem(City object, int index) {
            Keyboard.closeKeyboard(SearchPlaceMainActivity.this);
            Intent intent = new Intent();
            intent.putExtra(City.class.getName(), object);
            intent.putExtra(INTENT_HAS_TAKE_OFF, hasTakeOff);
            setResult(ServiceID.SERVICE_ID_BUS, intent);
            finish();
        }
    };

    //-----------------------------------------------
    //-----------------------InternationalHotel Domestic Place-----------------------
    //-----------------------------------------------
    private void setupResultPlaceHotelDomestic(List<HotelDomesticCity> cities) {
        RecyclerView rvResult = (RecyclerView) findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);
        searchPlaceHotelDomesticAdapter = new SearchPlaceHotelDomesticAdapter(this, cities, selectItemListHotelDomestic);
        rvResult.setAdapter(searchPlaceHotelDomesticAdapter);
    }

    //-----------------------------------------------
    TextWatcher textWatcherHotelDomestic = new TextWatcher() {
        private Timer timer = new Timer();
        private final long DELAY = 300;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {
            if (s.length() >= 1) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                searchPlaceHotelDomestic(s.toString());
                            }
                        },
                        DELAY
                );
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //-----------------------------------------------
    private void setCityHotelDomestic(List<HotelDomesticCity> cities) {
        searchPlaceHotelDomesticAdapter.setFilter(cities);
    }

    //-----------------------------------------------
    private void searchPlaceHotelDomestic(final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (title.length() > 0) {
                    setCityHotelDomestic(new HotelDomesticOffline(SearchPlaceMainActivity.this).getCityByName(title));
                } else {
                    setupResultPlaceHotelDomestic(new HotelDomesticOffline(SearchPlaceMainActivity.this).getAllCityWithHeader());
                }
            }
        });

    }

    //-----------------------------------------------
    SelectItemList<HotelDomesticCity> selectItemListHotelDomestic = new SelectItemList<HotelDomesticCity>() {
        @Override
        public void onSelectItem(HotelDomesticCity object, int index) {
            Keyboard.closeKeyboard(SearchPlaceMainActivity.this);
            Intent intent = new Intent();
            intent.putExtra(HotelDomesticCity.class.getName(), object);
            setResult(ServiceID.SERVICE_ID_HOTEL_DESTINATION_DOMESTIC, intent);
            finish();
        }
    };
    //-----------------------------------------------
}
