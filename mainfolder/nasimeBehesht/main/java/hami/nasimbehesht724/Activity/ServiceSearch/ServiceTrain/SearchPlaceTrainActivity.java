package hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain;

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

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Adapter.SearchPlaceTrainAdapter;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.CityTrain;
import hami.nasimbehesht724.BaseController.SelectItemList;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.Database.TrainOffline;
import hami.nasimbehesht724.Util.Keyboard;
import hami.nasimbehesht724.Util.UtilFonts;

/**
 * Created by renjer on 2017-04-30.
 */

public class SearchPlaceTrainActivity extends AppCompatActivity {

    private Boolean hasTakeOff;
    public final static String HAS_TAKE_OFF = "INTENT_HAS_TAKE_OFF";
    private SearchPlaceTrainAdapter searchPlaceAdapter;
    private RecyclerView rvResult;
    private ImageView imgBtnBack;
    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_search_place);
        hasTakeOff = getIntent().getExtras().getBoolean(HAS_TAKE_OFF);
        initialComponentActivity();
    }
    //-----------------------------------------------

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("INTENT_HAS_TAKE_OFF", hasTakeOff);
    }
    //-----------------------------------------------

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            hasTakeOff = savedInstanceState.getBoolean("INTENT_HAS_TAKE_OFF");
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        LinearLayout layoutMain = (LinearLayout) findViewById(R.id.layoutMain);
        UtilFonts.overrideFonts(this, layoutMain, UtilFonts.IRAN_SANS_NORMAL);
        setupPlace();
        imgBtnBack = (ImageView) findViewById(R.id.imgBtnBack);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                finish();
            }
        });

    }
    private EditText editText;
    //-----------------------------------------------
    private void setupPlace() {
        editText = (EditText) findViewById(R.id.autoCompleteFromPlace);
        if (hasTakeOff) {
            editText.setHint(R.string.flyingFrom);
        } else {
            editText.setHint(R.string.flyingTo);
        }
        search("");
        editText.addTextChangedListener(textWatcher);
    }

    //-----------------------------------------------
    TextWatcher textWatcher = new TextWatcher() {
        private Timer timer = new Timer();
        private final long DELAY = 1000;

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
                                search(s.toString());
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
    private void setupFinishPlace(List<CityTrain> cities) {
        rvResult = (RecyclerView) findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvResult.setLayoutManager(layoutManager);

        searchPlaceAdapter = new SearchPlaceTrainAdapter(this,
                cities,
                selectItemList);
        rvResult.setAdapter(searchPlaceAdapter);
    }

    //-----------------------------------------------
    private void setCity(List<CityTrain> cities) {
        searchPlaceAdapter.setFilter(cities);
    }

    //-----------------------------------------------
    private void search(final String title) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (title.length() > 0) {
                    setCity(new TrainOffline(SearchPlaceTrainActivity.this).getCityByName(title));
                } else {
                    setupFinishPlace(new TrainOffline(SearchPlaceTrainActivity.this).getAllCity());
                }
            }
        });

    }

    SelectItemList<CityTrain> selectItemList = new SelectItemList<CityTrain>() {
        @Override
        public void onSelectItem(CityTrain object , int index) {
            Keyboard.closeKeyboard(SearchPlaceTrainActivity.this);
            Intent intent = new Intent();
            intent.putExtra(CityTrain.class.getName(), object);
            intent.putExtra(HAS_TAKE_OFF, hasTakeOff);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

}
