package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Fragment.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;


import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hami.mainapp.Activity.ServiceSearch.ConstService.ServiceID;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.SearchCountryActivity;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticPassengerInfo;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.BaseResult;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.Country;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.DataPassengerInfo;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.PassengerInfo;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.InternationalApi;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.NationalCodePresenter;
import hami.mainapp.BaseController.ResultSearchPresenter;
import hami.mainapp.Const.FlightRules;
import hami.mainapp.R;
import hami.mainapp.Util.Keyboard;
import hami.mainapp.Util.TimeDate;
import hami.mainapp.Util.UtilFonts;
import hami.mainapp.Util.ValidateNationalCode;
import hami.mainapp.View.HeaderBar;
import hami.mainapp.View.ToastMessageBar;


public class ActivityRegisterPassengerDomestic extends AppCompatActivity {

    private EditText edtNationalCode, edtGender, edtType, edtFirstNamePersian, edtLastNamePersian, edtFirstName, edtLastName, edtDatePassport, edtNoPassport, edtExportingCountry;
    private TabLayout tabsTypePassenger;
    private TextInputLayout tilNationalCode, tilCountryExporting;
    private int position, isForeign;
    private DomesticPassengerInfo domesticPassengerInfo;
    private Boolean hasShowAndEdit = false, hasFirstShowEdit = false, hasIranian = true;
    private ArrayList<DomesticPassengerInfo> listPassengers;
    private int accessTranslateCounter = 2;
    public final static int RESULT_OK_EDIT_PASSENGER = 100;
    public final static int RESULT_OK_ADD_PASSENGER = 101;
    private HeaderBar headerBar;
    private LinearLayout layoutForeignPassport;
    private InternationalApi internationalApi;
    private static final String TAG = "ActivityRegisterPassengerTour";

    //-----------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_flight_domestic_register_passenger);
        listPassengers = getIntent().getParcelableArrayListExtra("listPassengers");
        isForeign = getIntent().getExtras().getInt("isForeign");
        if (getIntent().hasExtra(DomesticPassengerInfo.class.getName())) {
            hasShowAndEdit = hasFirstShowEdit = true;
            domesticPassengerInfo = getIntent().getParcelableExtra(DomesticPassengerInfo.class.getName());
            position = getIntent().getExtras().getInt("index");
        } else {
            hasShowAndEdit = hasFirstShowEdit = false;
        }

        initialComponentActivity();
    }
    //-----------------------------------------------

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(DomesticPassengerInfo.class.getName(), domesticPassengerInfo);
            outState.putParcelableArrayList("listPassengers", listPassengers);
            outState.putBoolean("hasShowAndEdit", hasShowAndEdit);
            outState.putBoolean("hasFirstShowEdit", hasFirstShowEdit);
            outState.putBoolean("hasIranian", hasIranian);
            outState.putInt("position", position);
            outState.putInt("isForeign", isForeign);
            outState.putInt("accessTranslateCounter", accessTranslateCounter);

        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            domesticPassengerInfo = savedInstanceState.getParcelable(DomesticPassengerInfo.class.getName());
            listPassengers = savedInstanceState.getParcelableArrayList("listPassengers");
            hasShowAndEdit = savedInstanceState.getBoolean("hasShowAndEdit");
            hasFirstShowEdit = savedInstanceState.getBoolean("hasFirstShowEdit");
            hasIranian = savedInstanceState.getBoolean("hasIranian");
            position = savedInstanceState.getInt("position");
            isForeign = savedInstanceState.getInt("isForeign");
            accessTranslateCounter = savedInstanceState.getInt("accessTranslateCounter");

        }
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        UtilFonts.overrideFonts(this, findViewById(R.id.layoutMain), UtilFonts.IRAN_SANS_NORMAL);
        //setupToolbar(false);
        internationalApi = new InternationalApi(this);
        headerBar = (HeaderBar) findViewById(R.id.headerBar);
        headerBar.showMessageBar(R.string.msgErrorCheckInfoPassenger);
        AppCompatButton txtBtnAddPassenger = (AppCompatButton) findViewById(R.id.btnRegister);
        txtBtnAddPassenger.setText(hasFirstShowEdit ? R.string.editPassenger : R.string.registerPassenger);
        tabsTypePassenger = (TabLayout) findViewById(R.id.tabsTypePassenger);
        UtilFonts.applyFontTabPassenger(this, tabsTypePassenger);
        //-----------------------------------------------
        layoutForeignPassport = (LinearLayout) findViewById(R.id.layoutForeignPassport);
        edtNationalCode = (EditText) findViewById(R.id.edtNationalCode);
        edtNationalCode.addTextChangedListener(textWatcherNationalCode);
        edtGender = (EditText) findViewById(R.id.edtGender);
        edtFirstNamePersian = (EditText) findViewById(R.id.edtFnameFarsi);
        edtType = (EditText) findViewById(R.id.edtType);
        edtLastNamePersian = (EditText) findViewById(R.id.edtLNameFarsi);
        edtFirstName = (EditText) findViewById(R.id.edtFName);
        edtDatePassport = (EditText) findViewById(R.id.edtDatePassport);
        edtLastName = (EditText) findViewById(R.id.edtLName);
        edtNoPassport = (EditText) findViewById(R.id.edtNoPassport);
        edtExportingCountry = (EditText) findViewById(R.id.edtExportingCountry);
        tilNationalCode = (TextInputLayout) findViewById(R.id.tilNationalCode);
        tilCountryExporting = (TextInputLayout) findViewById(R.id.tilCountryExporting);
        //66 = next;
        edtFirstName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && edtFirstNamePersian.length() == 0 && accessTranslateCounter > 0)
                    translateWord(edtFirstName.getText().toString(), edtFirstNamePersian);
            }
        });
        edtLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && edtLastNamePersian.length() == 0 && accessTranslateCounter > 0)
                    translateWord(edtLastName.getText().toString(), edtLastNamePersian);
            }
        });
        edtFirstNamePersian.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && edtFirstName.length() == 0 && accessTranslateCounter > 0)
                    translateWord(edtFirstNamePersian.getText().toString(), edtFirstName);
            }
        });
        edtLastNamePersian.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && edtLastName.length() == 0 && accessTranslateCounter > 0)
                    translateWord(edtLastNamePersian.getText().toString(), edtLastName);
            }
        });
//        edtFirstName.setOnHoverListener(new View.OnHoverListener() {
//            @Override
//            public boolean onHover(View v, MotionEvent event) {
//                translateWord(edtFirstName.getText().toString(), edtFirstNamePersian);
//                return false;
//            }
//        });
//        callbackTextChange(edtFirstName, edtFirstNamePersian);
//        callbackTextChange(edtLastName, edtLastNamePersian);
//        callbackTextChange(edtFirstNamePersian, edtFirstName);
//        callbackTextChange(edtLastNamePersian, edtLastName);
        //-----------------------------------------------
        edtExportingCountry.setOnClickListener(onClickListener);
        edtExportingCountry.setFocusable(false);
        edtExportingCountry.setCursorVisible(false);
        //-----------------------------------------------
        edtDatePassport.setFocusable(false);
        edtDatePassport.setCursorVisible(false);
        edtDatePassport.setOnClickListener(onClickListener);
        //-----------------------------------------------
        edtGender.setText(R.string.male);
        edtGender.setTag(DomesticPassengerInfo.GENDER_MALE);
        edtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenuGender();
            }
        });
        edtGender.setFocusable(false);
        edtGender.setCursorVisible(false);
        //-----------------------------------------------
        edtType.setText(R.string.irani);
        layoutForeignPassport.setVisibility(View.GONE);
        edtType.setOnClickListener(onClickListener);
        edtType.setFocusable(false);
        edtType.setCursorVisible(false);
        setupStyleIran();
        txtBtnAddPassenger.setOnClickListener(onClickListener);
        if (hasShowAndEdit) {
            iniInfoDefault();
            edtFirstName.requestFocus();
        } else {
            domesticPassengerInfo = new DomesticPassengerInfo();
            edtNationalCode.requestFocus();
        }
    }

    //-----------------------------------------------
    private void setTypefaceToInputLayout(TextInputLayout inputLayout, String typeFace) {

        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/" + typeFace);

        inputLayout.getEditText().setTypeface(tf);
        try {
            // Retrieve the CollapsingTextHelper Field
            final Field collapsingTextHelperField = inputLayout.getClass().getDeclaredField("mCollapsingTextHelper");
            collapsingTextHelperField.setAccessible(true);

            // Retrieve an instance of CollapsingTextHelper and its TextPaint
            final Object collapsingTextHelper = collapsingTextHelperField.get(inputLayout);
            final Field tpf = collapsingTextHelper.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);

            // Apply your Typeface to the CollapsingTextHelper TextPaint
            ((TextPaint) tpf.get(collapsingTextHelper)).setTypeface(tf);
        } catch (Exception ignored) {
            // Nothing to do
        }
    }

    //-----------------------------------------------
    private void iniInfoDefault() {
        edtNationalCode.setText(domesticPassengerInfo.getNationalCode());
        edtFirstName.setText(domesticPassengerInfo.getFirstNameEng());
        edtLastName.setText(domesticPassengerInfo.getLastNameEng());
        edtFirstNamePersian.setText(domesticPassengerInfo.getFirstNamePersian());
        edtLastNamePersian.setText(domesticPassengerInfo.getLastNamePersian());

        int genderId = domesticPassengerInfo.getGender();
        int typeNationality = domesticPassengerInfo.getNationalityType();
        if (genderId == DomesticPassengerInfo.GENDER_MALE) {
            edtGender.setText(R.string.male);
        } else if (genderId == DomesticPassengerInfo.GENDER_FEMALE) {
            edtGender.setText(R.string.female);
        }
        if (isForeign == 0 && typeNationality == DomesticPassengerInfo.EXPORTING_COUNTRY_IRAN) {
            hasIranian = true;
            setupStyleIran();
            edtType.setText(R.string.irani);
        } else {
            hasIranian = false;
            setupStyleForeign();
            edtType.setText(R.string.foreign);
            edtExportingCountry.setText(domesticPassengerInfo.getExportingCountryName());
            edtNoPassport.setText(domesticPassengerInfo.getPassportCo());
            edtDatePassport.setText(domesticPassengerInfo.getPassportExpireDate());
        }
        hasFirstShowEdit = false;
    }

    //-----------------------------------------------
    private int getTypePassenger() {
        int index = tabsTypePassenger.getSelectedTabPosition();
        int typePassenger = -1;
        if (index == 0) {
            typePassenger = FlightRules.TP_INFANT;
            domesticPassengerInfo.setTypePassenger(FlightRules.TP_INFANT);
        } else if (index == 1) {
            typePassenger = FlightRules.TP_CHILDREN;
            domesticPassengerInfo.setTypePassenger(FlightRules.TP_CHILDREN);
        } else {
            typePassenger = FlightRules.TP_ADULTS;
            domesticPassengerInfo.setTypePassenger(FlightRules.TP_ADULTS);
        }
        return typePassenger;
    }

    //-----------------------------------------------
    private Boolean checkExistPassenger(String nationalCode) {
        try {
            int index = 0;
            for (DomesticPassengerInfo passengerInfo : listPassengers) {
                if (hasShowAndEdit) {
                    if (index != position && passengerInfo.getNationalCode().contentEquals(nationalCode)) {
                        return true;
                    }
                } else {
                    if (passengerInfo.getNationalCode().contentEquals(nationalCode)) {
                        return true;
                    }
                }
                index++;
            }
        } catch (Exception e) {

        }
        return false;
    }

    //-----------------------------------------------
    private TextWatcher textWatcherNationalCode = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 10 && !hasFirstShowEdit) {
                if (checkExistPassenger(s.toString())) {
                    ToastMessageBar.show(ActivityRegisterPassengerDomestic.this, R.string.msgErrorExistPassenger);
                } else {
                    getInfo(s.toString());
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //-----------------------------------------------
    private void callbackTextChange(EditText fromEditText, final EditText toEditText) {
        fromEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (accessTranslateCounter > 0) {
                    translateWord(s.toString(), toEditText);
                }
            }
        });
    }

    //-----------------------------------------------
    private void translateWord(String word, final EditText toEditText) {
        internationalApi.translateWord(word, new ResultSearchPresenter<BaseResult>() {
            @Override
            public void onStart() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onErrorServer(int type) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onErrorInternetConnection() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onSuccessResultSearch(final BaseResult result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toEditText.setText(result.getMsg());
                        accessTranslateCounter--;
                    }
                });
            }

            @Override
            public void noResult(int type) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onError(String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    //-----------------------------------------------
    private void getInfo(String nationalCode) {
        new InternationalApi(this).getInfoByNationalCode(nationalCode, new NationalCodePresenter() {
            @Override
            public void onStart() {

            }

            @Override
            public void onErrorServer() {

            }

            @Override
            public void onErrorInternetConnection() {

            }

            @Override
            public void onSuccessDataPassengerInfo(final DataPassengerInfo dataPassengerInfo) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (dataPassengerInfo.getPassengersType().contentEquals(String.valueOf(FlightRules.TP_ADULTS))) {
                                tabsTypePassenger.getTabAt(2).select();
                            } else if (dataPassengerInfo.getPassengersType().contentEquals(String.valueOf(FlightRules.TP_CHILDREN))) {
                                tabsTypePassenger.getTabAt(1).select();
                            } else if (dataPassengerInfo.getPassengersType().contentEquals(String.valueOf(FlightRules.TP_INFANT))) {
                                tabsTypePassenger.getTabAt(0).select();
                            }
                            edtFirstNamePersian.setText(dataPassengerInfo.getPassengerNamePersian());
                            edtLastNamePersian.setText(dataPassengerInfo.getPassengerFamilyPersian());
                            edtFirstName.setText(dataPassengerInfo.getPassengerNameEnglish());
                            edtLastName.setText(dataPassengerInfo.getPassengerFamilyEnglish());
                            if (Integer.valueOf(dataPassengerInfo.getGender()) == DomesticPassengerInfo.GENDER_MALE) {
                                domesticPassengerInfo.setGender(Integer.valueOf(dataPassengerInfo.getGender()));
                                edtGender.setText(R.string.male);

                            } else if (Integer.valueOf(dataPassengerInfo.getGender()) == DomesticPassengerInfo.GENDER_MALE) {
                                domesticPassengerInfo.setGender(Integer.valueOf(dataPassengerInfo.getGender()));
                                edtGender.setText(R.string.female);
                            } else {
                                domesticPassengerInfo.setGender(Integer.parseInt(PassengerInfo.MALE));
                                edtGender.setText(R.string.male);
                            }
                            domesticPassengerInfo.setPassportCo(dataPassengerInfo.getPassportNumber());
                            edtNoPassport.setText(dataPassengerInfo.getPassportNumber());
                            edtDatePassport.setText(dataPassengerInfo.getPassportExpiryDate());
                            if (Integer.valueOf(dataPassengerInfo.getNationality()) == DomesticPassengerInfo.EXPORTING_COUNTRY_IRAN) {
                                domesticPassengerInfo.setNationalityType(FlightRules.PN_IRANIAN);
                                edtType.setText(R.string.irani);
                                setupStyleIran();
                            } else if (Integer.valueOf(dataPassengerInfo.getNationality()) == DomesticPassengerInfo.EXPORTING_COUNTRY_FOREIGN) {
                                edtType.setText(R.string.foreign);
                                domesticPassengerInfo.setNationalityType(FlightRules.PN_FOREIGN);
                                setupStyleForeign();
                            } else {
                                edtType.setText(R.string.irani);
                                domesticPassengerInfo.setNationalityType(FlightRules.PN_IRANIAN);
                            }
                            Keyboard.closeKeyboard(ActivityRegisterPassengerDomestic.this);
                            accessTranslateCounter = 0;
                        } catch (Exception e) {


                        }

                    }
                });
            }


            @Override
            public void onError(String msg) {
            }

            @Override
            public void onFinish() {

            }
        });
    }

    //-----------------------------------------------
    private void showPopupMenuGender() {
        PopupMenu popupMenu = new PopupMenu(this, edtGender);
        popupMenu.inflate(R.menu.menu_gender);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                edtGender.setText(item.getTitle());
                if (item.getItemId() == R.id.menuMale)
                    domesticPassengerInfo.setGender(DomesticPassengerInfo.GENDER_MALE);
                else
                    domesticPassengerInfo.setGender(DomesticPassengerInfo.GENDER_FEMALE);
                return false;
            }
        });
        popupMenu.show();
    }

    //-----------------------------------------------
    private void showPopupMenuCoNationality() {
        PopupMenu popupMenu = new PopupMenu(this, edtType);
        popupMenu.inflate(R.menu.menu_co_nationality);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                edtType.setText(item.getTitle());
                if (item.getItemId() == R.id.menuIran) {
                    setupStyleIran();
                    domesticPassengerInfo.setNationalityType(DomesticPassengerInfo.EXPORTING_COUNTRY_IRAN);
                    hasIranian = true;
                } else {
                    setupStyleForeign();
                    domesticPassengerInfo.setNationalityType(DomesticPassengerInfo.EXPORTING_COUNTRY_FOREIGN);
                    hasIranian = false;
                }

                return false;
            }
        });
        popupMenu.show();
    }

    //-----------------------------------------------
    private void setupStyleIran() {
        headerBar.showMessageBar(R.string.msgErrorCheckInfoPassenger);
        layoutForeignPassport.setVisibility(View.GONE);
        tilCountryExporting.setVisibility(View.GONE);
        tilNationalCode.setVisibility(View.VISIBLE);
    }

    //-----------------------------------------------
    private void setupStyleForeign() {
        headerBar.showMessageBar(R.string.msgErrorCheckInfoPassportPassenger);
        layoutForeignPassport.setVisibility(View.VISIBLE);
        tilCountryExporting.setVisibility(View.VISIBLE);
        tilNationalCode.setVisibility(View.GONE);
    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.edtDatePassport:
                    getGregorianDate(edtDatePassport);
                    break;
                case R.id.edtGender:
                    showPopupMenuGender();
                    break;
                case R.id.edtType:
                    showPopupMenuCoNationality();
                    break;
                case R.id.btnRegister:
                    getDomesticPassengerInfoByView();
                    break;
                case R.id.edtExportingCountry:
                    Intent intent = new Intent(ActivityRegisterPassengerDomestic.this, SearchCountryActivity.class);
                    intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_COUNTRY);
                    startActivityForResult(intent, 0);
                    break;
            }
        }
    };

    //-----------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (data != null && data.getExtras() != null) {
                if (resultCode == ServiceID.SERVICE_COUNTRY) {
                    Country country = (Country) data.getExtras().getSerializable(Country.class.getName());
                    edtExportingCountry.setText(country.getPersian());
                    domesticPassengerInfo.setExportingCountry(country.getCode3());
                    domesticPassengerInfo.setNationalitycode(country.getCode3());
                    domesticPassengerInfo.setExportingCountryName(country.getPersian());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //-----------------------------------------------
    private void getGregorianDateForPassport2(final EditText editText) {
        CalendarDatePickerDialogFragment cdp2 = new CalendarDatePickerDialogFragment();
        cdp2.setDateRange(TimeDate.getCurrentDate(0), TimeDate.getCurrentDate(10));
        cdp2.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
            @Override
            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                String monthStr = "", dayOfMonthStr = "";
                monthOfYear++;
                if (monthOfYear < 10)
                    monthStr = "0" + monthOfYear;
                else
                    monthStr = String.valueOf(monthOfYear);
                if (dayOfMonth < 10)
                    dayOfMonthStr = "0" + dayOfMonth;
                else
                    dayOfMonthStr = String.valueOf(dayOfMonth);
                //String valueExpire = (year) + "/" + (monthStr) + "/" + dayOfMonthStr;
                String valueExpireDash = (year) + "-" + (monthStr) + "-" + dayOfMonthStr;
                //if (ValidationClass.validateExpPassportToast(activity, "", valueExpireDash, R.string.validatePassportExpDateMonth))
                editText.setText(Keyboard.convertPersianNumberToEngNumber(valueExpireDash));
            }
        });
        cdp2.show(getSupportFragmentManager(), "Material Calendar Example");
    }

    //-----------------------------------------------
    private void getGregorianDate(final EditText editText) {
        try {
            DatePickerDialog fromDatePickerDialog;

            final SimpleDateFormat dateFormatter;
            dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Calendar newCalendar = Calendar.getInstance();
            if (domesticPassengerInfo.getPassportExpireDate().length() > 0) {
                Date d = dateFormatter.parse(domesticPassengerInfo.getPassportExpireDate());
                newCalendar.setTime(d);
            }
            fromDatePickerDialog = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    //editText.setText(Keyboard.convertPersianNumberToEngNumber(dateFormatter.format(newDate.getTime())));
                    editText.setText(dateFormatter.format(newDate.getTime()));
                    editText.setError(null);
                    //passengerInfo.setExpDate(edtExpireDatePassport.getText().toString());
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            fromDatePickerDialog.show();
        } catch (ParseException e) {

            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    //-----------------------------------------------
    private void getDomesticPassengerInfoByView() {
        //edtNoPassport.setText(Keyboard.convertPersianNumberToEngNumber(edtNoPassport.getText().toString()));
        //edtNationalCode.setText(Keyboard.convertPersianNumberToEngNumber(edtNationalCode.getText().toString()));
        int type = domesticPassengerInfo.getNationalityType();
        if (type == DomesticPassengerInfo.EXPORTING_COUNTRY_IRAN && !ValidateNationalCode.isValidNationalCode(edtNationalCode.getText().toString())) {
            ToastMessageBar.show(ActivityRegisterPassengerDomestic.this, R.string.validateNationalCode);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtNationalCode.startAnimation(vibrateAnimation);
            edtNationalCode.requestFocus();
            return;
        }
        if (type == DomesticPassengerInfo.EXPORTING_COUNTRY_IRAN && checkExistPassenger(edtNationalCode.getText().toString())) {
            ToastMessageBar.show(ActivityRegisterPassengerDomestic.this, R.string.msgErrorExistPassenger);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtNationalCode.startAnimation(vibrateAnimation);
            edtNationalCode.requestFocus();
            return;
        }
        if (edtFirstName.length() == 0) {
            ToastMessageBar.show(ActivityRegisterPassengerDomestic.this, R.string.validateFirstName);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtFirstName.startAnimation(vibrateAnimation);
            return;
        }
        if (edtLastName.length() == 0) {
            ToastMessageBar.show(ActivityRegisterPassengerDomestic.this, R.string.validateLastName);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtLastName.startAnimation(vibrateAnimation);
            return;
        }
        if (edtFirstNamePersian.length() == 0) {
            ToastMessageBar.show(ActivityRegisterPassengerDomestic.this, R.string.validateFirstNamePersian);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtFirstNamePersian.startAnimation(vibrateAnimation);
            return;
        }
        if (edtLastNamePersian.length() == 0) {
            ToastMessageBar.show(ActivityRegisterPassengerDomestic.this, R.string.validateLastNamePersian);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtLastNamePersian.startAnimation(vibrateAnimation);
            return;
        }
        if (type == DomesticPassengerInfo.EXPORTING_COUNTRY_FOREIGN && edtNoPassport.length() == 0) {
            ToastMessageBar.show(ActivityRegisterPassengerDomestic.this, R.string.validatePassportCode);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtNoPassport.startAnimation(vibrateAnimation);
            return;
        }
        if (type == DomesticPassengerInfo.EXPORTING_COUNTRY_FOREIGN && edtDatePassport.length() == 0) {
            ToastMessageBar.show(ActivityRegisterPassengerDomestic.this, R.string.validatePassportExpDate);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtDatePassport.startAnimation(vibrateAnimation);
            return;
        }
        if (type == DomesticPassengerInfo.EXPORTING_COUNTRY_FOREIGN && edtExportingCountry.length() == 0) {
            ToastMessageBar.show(ActivityRegisterPassengerDomestic.this, R.string.validateErrorExportingCountry);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtExportingCountry.startAnimation(vibrateAnimation);
            return;
        }
        domesticPassengerInfo.setTypePassenger(getTypePassenger());
        domesticPassengerInfo.setFirstNameEng(edtFirstName.getText().toString());
        domesticPassengerInfo.setLastNameEng(edtLastName.getText().toString());
        domesticPassengerInfo.setFirstNamePersian(edtFirstNamePersian.getText().toString());
        domesticPassengerInfo.setLastNamePersian(edtLastNamePersian.getText().toString());
        domesticPassengerInfo.setNationalCode(edtNationalCode.getText().toString());
        if (hasShowAndEdit) {
            if (type == DomesticPassengerInfo.EXPORTING_COUNTRY_FOREIGN) {
                domesticPassengerInfo.setPassportCo(edtNoPassport.getText().toString());
                domesticPassengerInfo.setPassportExpireDate(edtDatePassport.getText().toString());
            }
            Intent intent = new Intent();
            intent.putExtra(DomesticPassengerInfo.class.getName(), domesticPassengerInfo);
            intent.putExtra("index", position);
            setResult(RESULT_OK_EDIT_PASSENGER, intent);
            finish();
        } else {
            if (type == DomesticPassengerInfo.EXPORTING_COUNTRY_FOREIGN) {
                domesticPassengerInfo.setPassportCo(edtNoPassport.getText().toString());
                domesticPassengerInfo.setPassportExpireDate(edtDatePassport.getText().toString());

            }
            Intent intent = new Intent();
            intent.putExtra(DomesticPassengerInfo.class.getName(), domesticPassengerInfo);
            setResult(RESULT_OK_ADD_PASSENGER, intent);
            finish();
        }
    }
    //-----------------------------------------------
}
