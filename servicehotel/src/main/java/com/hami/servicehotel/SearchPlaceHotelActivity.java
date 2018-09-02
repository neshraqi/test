package com.hami.servicehotel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hami.common.BaseController.ResultSearchPresenter;
import com.hami.common.BaseController.SelectItemList;
import com.hami.common.Const.ServiceID;
import com.hami.common.Util.Keyboard;
import com.hami.common.Util.UtilFonts;
import com.hami.common.View.HeaderBarLtr;
import com.hami.common.View.MessageBar;
import com.hami.common.View.ToastMessageBar;
import com.hami.servicehotel.International.Adapter.SearchPlaceCityHotelInternationalAdapter;
import com.hami.servicehotel.International.Adapter.SearchPlaceHotelInternationalAdapter;
import com.hami.servicehotel.International.Controller.InternationalHotelApi;
import com.hami.servicehotel.International.Controller.Model.SearchCity;
import com.hami.servicehotel.International.Controller.Model.SearchDestination;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by renjer on 2017-04-30.
 */

public class   SearchPlaceHotelActivity extends AppCompatActivity {

    private Boolean hasDestination;
    public final static String INTENT_HAS_DESTINATION_INTERNATIONAL_HOTEL = "INTENT_HAS_DESTINATION_INTERNATIONAL_HOTEL ";
    public final static String INTENT_HAS_DESTINATION_DOMESTIC_HOTEL = "INTENT_HAS_DESTINATION_DOMESTIC_HOTEL ";
    private SearchPlaceHotelInternationalAdapter searchPlaceHotelInternationalAdapter;
    private SearchPlaceCityHotelInternationalAdapter searchPlaceCityHotelInternationalAdapter;
    private EditText autoCompleteFromPlace;
    private ImageView imgBtnBack;
    private InternationalHotelApi hotelApi;
    private HeaderBarLtr headerBar;
    private static final String TAG = "SearchPlaceHotelActivity";
    private String destinationId = "";
    private int serviceId;
    private ArrayList<SearchCity> resultCity;
    private MessageBar messageBar;

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_search_place_ltr);
        try {
            serviceId = getIntent().getExtras().getInt(ServiceID.INTENT_SERVICE_ID);
            if (serviceId == ServiceID.SERVICE_ID_HOTEL_CITY) {
                destinationId = getIntent().getExtras().getString(ServiceID.INTENT_DESTINATION_ID);
                hasDestination = false;
            } else {
                hasDestination = true;
            }
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
            outState.putBoolean(INTENT_HAS_DESTINATION_INTERNATIONAL_HOTEL, hasDestination);
        }
        super.onSaveInstanceState(outState);
    }
    //-----------------------------------------------

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            hasDestination = savedInstanceState.getBoolean(INTENT_HAS_DESTINATION_INTERNATIONAL_HOTEL);
        }
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        LinearLayout layoutMain = (LinearLayout) findViewById(R.id.layoutMain);
        UtilFonts.overrideFonts(this, layoutMain, UtilFonts.TAHOMA);
        messageBar = findViewById(R.id.messageBar);
        hotelApi = new InternationalHotelApi(this);
        headerBar = (HeaderBarLtr) findViewById(R.id.headerBar);
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
        int hintString = (hasDestination) ? R.string.countryEng : R.string.cityEng;
        autoCompleteFromPlace.setHint(hintString);
        if (serviceId == ServiceID.SERVICE_ID_HOTEL_DESTINATION) {
            headerBar.showMessageBar(R.string.pleaseEnterDestination);
            autoCompleteFromPlace.addTextChangedListener(textWatcherHotelInternational);
        } else {
            headerBar.showMessageBar(R.string.pleaseEnterCity);
            autoCompleteFromPlace.addTextChangedListener(textWatcherHotelCityInternational);
            searchCityHotelInternational();
        }
    }


    //-----------------------------------------------
    //-----------------------InternationalHotel Place Destination----------------
    //-----------------------------------------------
    public void searchDestinationHotelInternational(final String title) {
        hotelApi.searchDestination(title, new ResultSearchPresenter<ArrayList<SearchDestination>>() {
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
            public void onErrorServer(int type) {

            }

            @Override
            public void onErrorInternetConnection() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastMessageBar.show(SearchPlaceHotelActivity.this, R.string.msgErrorInternetConnection);
                    }
                });
            }

            @Override
            public void onSuccessResultSearch(final ArrayList<SearchDestination> result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupResultPlaceDestinationHotelInternational(result);
                    }
                });
            }

            @Override
            public void noResult(int type) {

            }

            @Override
            public void onError(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastMessageBar.show(SearchPlaceHotelActivity.this, R.string.msgErrorPayment);
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
    TextWatcher textWatcherHotelInternational = new TextWatcher() {
        private Timer timer = new Timer();
        private final long DELAY = 400;

        //-----------------------------------------------
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {
            if (s.length() >= 2) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                searchDestinationHotelInternational(s.toString());
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
    private void setupResultPlaceDestinationHotelInternational(List<SearchDestination> results) {
        RecyclerView rvResult = (RecyclerView) findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);
        searchPlaceHotelInternationalAdapter = new SearchPlaceHotelInternationalAdapter(this, results, selectItemSearchInternational);
        rvResult.setAdapter(searchPlaceHotelInternationalAdapter);
    }

    //-----------------------------------------------
    SelectItemList<SearchDestination> selectItemSearchInternational = new SelectItemList<SearchDestination>() {
        @Override
        public void onSelectItem(SearchDestination object, int index) {
            Keyboard.closeKeyboard(SearchPlaceHotelActivity.this);
            Intent intent = new Intent();
            intent.putExtra(SearchDestination.class.getName(), object);
            setResult(ServiceID.SERVICE_ID_HOTEL_DESTINATION, intent);
            finish();
        }
    };

    //-----------------------------------------------
    //-----------------------InternationalHotel Place CITY----------------
    //-----------------------------------------------
    public void searchCityHotelInternational() {
        hotelApi.searchCity(destinationId, new ResultSearchPresenter<ArrayList<SearchCity>>() {
            @Override
            public void onStart() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        autoCompleteFromPlace.setEnabled(false);
                        messageBar.hideMessageBar();
                        headerBar.showProgress();
                    }
                });
            }

            @Override
            public void onErrorServer(int type) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorServer);
                        messageBar.setTitleButton(R.string.tryAgain);
                        messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                searchCityHotelInternational();
                            }
                        });

                    }
                });
            }

            @Override
            public void onErrorInternetConnection() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                                messageBar.setTitleButton(R.string.tryAgain);
                                messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        searchCityHotelInternational();
                                    }
                                });

                            }
                        });
                    }
                });
            }

            @Override
            public void onSuccessResultSearch(final ArrayList<SearchCity> result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.hideMessageBar();
                        resultCity = result;
                        setupResultPlaceCityHotelInternational(result);
                    }
                });
            }

            @Override
            public void noResult(int type) {

            }

            @Override
            public void onError(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messageBar.showMessageBar(R.string.msgErrorPayment);
                                messageBar.setTitleButton(R.string.tryAgain);
                                messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        searchCityHotelInternational();
                                    }
                                });

                            }
                        });
                    }
                });
            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        autoCompleteFromPlace.setEnabled(true);
                        headerBar.hideMessageBar();
                    }
                });
            }
        });
    }

    //-----------------------------------------------
    TextWatcher textWatcherHotelCityInternational = new TextWatcher() {
        private Timer timer = new Timer();
        private final long DELAY = 200;

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
                                filterCity(s.toString());
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
    private void filterCity(String value) {
        if (value.length() == 0) {
            setupResultPlaceCityHotelInternational(resultCity);
        } else {
            FilterHotel<SearchCity, String> filter = new FilterHotel<SearchCity, String>() {
                public boolean isMatched(SearchCity object, String text) {
                    return object.getName().toLowerCase().startsWith(text.toLowerCase());
                }
            };
            ArrayList<SearchCity> list = new FilterList().filterList(resultCity, filter, value);
            setupResultPlaceCityHotelInternational(list);
        }
    }

    //-----------------------------------------------
    private void setupResultPlaceCityHotelInternational(final List<SearchCity> results) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView rvResult = (RecyclerView) findViewById(R.id.rvResult);
                try {
                    rvResult.setVisibility(View.VISIBLE);
                    rvResult.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(SearchPlaceHotelActivity.this);
                    rvResult.setLayoutManager(layoutManager);
                    searchPlaceCityHotelInternationalAdapter = new SearchPlaceCityHotelInternationalAdapter(SearchPlaceHotelActivity.this, results, searchCitySelectItemList);
                    rvResult.setAdapter(searchPlaceCityHotelInternationalAdapter);
                } catch (Exception e) {
                    rvResult.setVisibility(View.GONE);
                    messageBar.showMessageBar(R.string.msgErrorNoCityHotel);
                    messageBar.setTitleButton(R.string.tryAgain);
                    messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            searchCityHotelInternational();
                        }
                    });
                }
            }
        });


    }

    //-----------------------------------------------
    SelectItemList<SearchCity> searchCitySelectItemList = new SelectItemList<SearchCity>() {
        @Override
        public void onSelectItem(SearchCity object, int index) {
            Keyboard.closeKeyboard(SearchPlaceHotelActivity.this);
            Intent intent = new Intent();
            intent.putExtra(SearchCity.class.getName(), object);
            setResult(ServiceID.SERVICE_ID_HOTEL_CITY, intent);
            finish();
        }
    };


}

