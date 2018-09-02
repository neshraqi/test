package com.hami.serviceflight;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.hami.common.BaseController.SelectItemList;

import com.hami.common.Const.ServiceID;
import com.hami.common.Util.UtilFonts;
import com.hami.common.View.HeaderBar;
import com.hami.serviceflight.Services.International.Adapter.SearchCountryAdapter;
import com.hami.serviceflight.Services.International.Controller.Model.Country;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

//import hami.nasimbehesht724.Activity.ServiceSearch.ConstService.ServiceID;
//import hami.nasimbehesht724.Util.Database.FlightDomesticOffline;


/**
 * Created by renjer on 2017-04-30.
 */

public class SearchCountryActivity extends AppCompatActivity {

    private int serviceId;
    private EditText autoCompleteFromPlace;
    private ImageView imgBtnBack;
    private HeaderBar headerBar;
    private SearchCountryAdapter countryAdapter;

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_search_place);
        if (getIntent().hasExtra(ServiceID.INTENT_SERVICE_ID)) {
            serviceId = getIntent().getExtras().getInt(ServiceID.INTENT_SERVICE_ID);
        } else {
            Toast.makeText(this, R.string.msgErrorServer, Toast.LENGTH_SHORT);
            finish();
        }
        initialComponentActivity();
    }
    //-----------------------------------------------

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(ServiceID.INTENT_SERVICE_ID, serviceId);
        }
        super.onSaveInstanceState(outState);
    }
    //-----------------------------------------------

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
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
        autoCompleteFromPlace.setHint(R.string.country);
        switch (serviceId) {
            case ServiceID.SERVICE_COUNTRY:
                headerBar.showMessageBar(R.string.validateErrorNationalityCountry);
                autoCompleteFromPlace.addTextChangedListener(textWatcherCountry);
                break;
            case ServiceID.SERVICE_EXPORTING_COUNTRY:
                headerBar.showMessageBar(R.string.validateErrorExportingCountry);
                autoCompleteFromPlace.addTextChangedListener(textWatcherCountry);
                break;

        }
        searchPlaceCountry("");
    }

    //-----------------------------------------------
    private void setupResultCountry(ArrayList<Country> countries) {
        RecyclerView rvResult = (RecyclerView) findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);
        countryAdapter = new SearchCountryAdapter(this, countries, countrySelectItemList);
        rvResult.setAdapter(countryAdapter);
    }

    //-----------------------------------------------
    SelectItemList<Country> countrySelectItemList = new SelectItemList<Country>() {
        @Override
        public void onSelectItem(Country object , int index) {
            Intent intent = new Intent();
            switch (serviceId) {
                case ServiceID.SERVICE_COUNTRY:
                    intent.putExtra(Country.class.getName(), object);
                    setResult(ServiceID.SERVICE_COUNTRY, intent);

                    break;
                case ServiceID.SERVICE_EXPORTING_COUNTRY:
                    intent.putExtra(Country.class.getName(), object);
                    setResult(ServiceID.SERVICE_EXPORTING_COUNTRY, intent);
                    break;

            }
            finish();
        }
    };
    //--------------------- --------------------------
    TextWatcher textWatcherCountry = new TextWatcher() {
        private Timer timer = new Timer();
        private final long DELAY = 100;

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
                                searchPlaceCountry(s.toString());
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
    private void searchPlaceCountry(@Nullable final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (title.length() > 0) {
                    setCountry(new FlightDomesticOffline(SearchCountryActivity.this).getCountry(title));
                } else {
                    setupResultCountry(new FlightDomesticOffline(SearchCountryActivity.this).getCountry());
                }
            }
        });
    }

    //-----------------------------------------------
    private void setCountry(ArrayList<Country> country) {
        countryAdapter.setFilter(country);
    }


}
