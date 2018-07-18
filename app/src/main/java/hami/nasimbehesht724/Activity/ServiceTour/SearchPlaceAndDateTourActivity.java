package hami.nasimbehesht724.Activity.ServiceTour;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;



import hami.nasimbehesht724.Activity.ServiceSearch.ConstService.ServiceID;
import hami.nasimbehesht724.Activity.ServiceTour.Adapter.SearchDateTourAdapter;
import hami.nasimbehesht724.Activity.ServiceTour.Adapter.SearchPlaceTourAdapter;
import hami.nasimbehesht724.Activity.ServiceTour.Controller.Model.DateTour;
import hami.nasimbehesht724.Activity.ServiceTour.Controller.Model.InitialTourResponse;
import hami.nasimbehesht724.Activity.ServiceTour.Controller.Model.NameValue;
import hami.nasimbehesht724.BaseController.SelectItemList;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.Keyboard;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.View.HeaderBar;
import hami.nasimbehesht724.View.ToastMessageBar;


public class SearchPlaceAndDateTourActivity extends AppCompatActivity {

    private SearchDateTourAdapter searchDateTourAdapter;
    private SearchPlaceTourAdapter searchPlaceTourAdapter;
    private EditText autoCompleteFromPlace;
    private ImageView imgBtnBack;
    private InitialTourResponse initialTourResponse;
    private HeaderBar headerBar;
    private static final String TAG = "SearchPlaceAndDateTourActivity";
    private int serviceId;

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_search_place);
        try {
            serviceId = getIntent().getExtras().getInt(ServiceID.INTENT_SERVICE_ID);
            initialTourResponse = getIntent().getExtras().getParcelable(InitialTourResponse.class.getName());
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
            outState.putParcelable(InitialTourResponse.class.getName(), initialTourResponse);
        }
        super.onSaveInstanceState(outState);
    }
    //-----------------------------------------------

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            initialTourResponse = savedInstanceState.getParcelable(InitialTourResponse.class.getName());
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
        if (serviceId == ServiceID.SERVICE_ID_TOUR_PLACE) {
            headerBar.showMessageBar(R.string.validateSelectFromPlaceTour);
            autoCompleteFromPlace.setHint(R.string.fromPlace);
            setupResultPlace();
        } else {
            headerBar.showMessageBar(R.string.validateSelectFromDateTour);
            autoCompleteFromPlace.setHint(R.string.moveDate);
            setupResultDate();
        }
    }


    //-----------------------------------------------
    //-----------------------Date -------------------
    //-----------------------------------------------
    private void setupResultDate() {
        RecyclerView rvResult = (RecyclerView) findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);
        searchDateTourAdapter = new SearchDateTourAdapter(this, initialTourResponse.getDateTourCalendar(), searchDateSelectItemList);
        rvResult.setAdapter(searchDateTourAdapter);
    }

    //-----------------------------------------------
    SelectItemList<DateTour> searchDateSelectItemList = new SelectItemList<DateTour>() {
        @Override
        public void onSelectItem(DateTour object, int index) {
            if(object.getUnix().contains("allDates"))
                object.setJdate(object.getJday());
            Keyboard.closeKeyboard(SearchPlaceAndDateTourActivity.this);
            Intent intent = new Intent();
            intent.putExtra(DateTour.class.getName(), object);
            setResult(ServiceID.SERVICE_ID_TOUR_DATE, intent);
            finish();
        }
    };


    //-----------------------------------------------
    //----------------------- Place CITY----------------
    //-----------------------------------------------
    private void setupResultPlace() {
        RecyclerView rvResult = (RecyclerView) findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);
        searchPlaceTourAdapter = new SearchPlaceTourAdapter(this, initialTourResponse.getFromList(), searchPlaceSelectItemList);
        rvResult.setAdapter(searchPlaceTourAdapter);
    }

    //-----------------------------------------------
    SelectItemList<NameValue> searchPlaceSelectItemList = new SelectItemList<NameValue>() {
        @Override
        public void onSelectItem(NameValue object, int index) {
            Keyboard.closeKeyboard(SearchPlaceAndDateTourActivity.this);
            Intent intent = new Intent();
            intent.putExtra(NameValue.class.getName(), object);
            setResult(ServiceID.SERVICE_ID_TOUR_PLACE, intent);
            finish();
        }
    };


}