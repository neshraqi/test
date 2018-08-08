package hami.mainapp.Activity.ServiceTour.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hami.mainapp.Activity.ServiceSearch.ConstService.ServiceID;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.SearchCountryActivity;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticPassengerInfo;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.BaseResult;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.Country;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.DataPassengerInfo;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.InternationalApi;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.NationalCodePresenter;
import hami.mainapp.Activity.ServiceTour.Controller.Model.PassengerTour;
import hami.mainapp.BaseController.ResultSearchPresenter;
import hami.mainapp.Const.FlightRules;
import hami.mainapp.Const.TourRules;
import hami.mainapp.R;
import hami.mainapp.Util.Keyboard;
import hami.mainapp.Util.ToolsPersianCalendar;
import hami.mainapp.Util.UtilFonts;
import hami.mainapp.Util.ValidateNationalCode;
import hami.mainapp.View.HeaderBar;
import hami.mainapp.View.ToastMessageBar;


public class ActivityRegisterPassengerTour extends AppCompatActivity {

    private EditText edtNationalCode, edtGender, edtBirthDay, edtFirstNamePersian, edtLastNamePersian, edtFirstName, edtLastName, edtDatePassport, edtNoPassport, edtExportingCountry;
    private TabLayout tabsTypePassenger;
    private int index;
    private String typeTour;
    private PassengerTour passengerTour;
    private Boolean hasShowAndEdit = false, hasFirstShowEdit = false;
    private int accessTranslateCounter = 2;
    public final static int RESULT_OK_EDIT_PASSENGER = 100;
    public final static int RESULT_OK_ADD_PASSENGER = 101;
    private HeaderBar headerBar;
    private LinearLayout layoutForeignPassport, layoutFullNameEng;
    private InternationalApi internationalApi;
    private static final String TAG = "ActivityRegisterPassengerTour";

    //-----------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_tour_register_passenger);
        if (getIntent().hasExtra(PassengerTour.class.getName())) {
            hasShowAndEdit = hasFirstShowEdit = true;
            passengerTour = getIntent().getParcelableExtra(PassengerTour.class.getName());

        } else {
            hasShowAndEdit = hasFirstShowEdit = false;
        }
        typeTour = getIntent().getExtras().getString("typeTour");
        index = getIntent().getExtras().getInt("index");

        initialComponentActivity();
    }
    //-----------------------------------------------

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(PassengerTour.class.getName(), passengerTour);
            outState.putBoolean("hasShowAndEdit", hasShowAndEdit);
            outState.putBoolean("hasFirstShowEdit", hasFirstShowEdit);
            outState.putInt("index", index);
            outState.putString("typeTour", typeTour);
            outState.putInt("accessTranslateCounter", accessTranslateCounter);

        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            passengerTour = savedInstanceState.getParcelable(PassengerTour.class.getName());
            hasShowAndEdit = savedInstanceState.getBoolean("hasShowAndEdit");
            hasFirstShowEdit = savedInstanceState.getBoolean("hasFirstShowEdit");
            index = savedInstanceState.getInt("index");
            typeTour = savedInstanceState.getString("typeTour");
            accessTranslateCounter = savedInstanceState.getInt("accessTranslateCounter");

        }
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        UtilFonts.overrideFonts(this, findViewById(R.id.layoutMain), UtilFonts.IRAN_SANS_NORMAL);
        setupToolbar(false);
        internationalApi = new InternationalApi(this);
        headerBar = (HeaderBar) findViewById(R.id.headerBar);
        headerBar.showMessageBar(R.string.msgErrorCheckInfoPassenger);
        AppCompatButton txtBtnAddPassenger = (AppCompatButton) findViewById(R.id.btnRegister);
        txtBtnAddPassenger.setText(hasFirstShowEdit ? R.string.editPassenger : R.string.registerPassenger);
        tabsTypePassenger = (TabLayout) findViewById(R.id.tabsTypePassenger);
        UtilFonts.applyFontTabPassenger(this, tabsTypePassenger);
        if (index == 0) {
            tabsTypePassenger.setVisibility(View.GONE);
            ToastMessageBar.show(ActivityRegisterPassengerTour.this, getString(R.string.validateAdultsFirstPassenger));
        } else {
            tabsTypePassenger.setVisibility(View.VISIBLE);
        }
        //-----------------------------------------------
        if (hasShowAndEdit) {
            setupToolbar(true);
        } else {
            passengerTour = new PassengerTour();
            setupToolbar(false);
        }
        setupMain();
        txtBtnAddPassenger.setOnClickListener(onClickListener);
    }

    //-----------------------------------------------
    private void setupToolbar(Boolean hasEdit) {
        TextView txtTitleMenu = (TextView) findViewById(R.id.txtTitleMenu);
        ImageView btnBack = (ImageView) findViewById(R.id.btnBack);
        if (hasEdit)
            txtTitleMenu.setText(R.string.editInfoPassenger);
        else
            txtTitleMenu.setText(R.string.registerInfoPassenger);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //-----------------------------------------------
    private void setupMain() {
        layoutForeignPassport = (LinearLayout) findViewById(R.id.layoutForeignPassport);
        layoutFullNameEng = (LinearLayout) findViewById(R.id.layoutFullNameEng);
        edtNationalCode = (EditText) findViewById(R.id.edtNationalCode);
        edtNationalCode.addTextChangedListener(textWatcherNationalCode);
        edtGender = (EditText) findViewById(R.id.edtGender);
        edtFirstNamePersian = (EditText) findViewById(R.id.edtFnameFarsi);
        edtLastNamePersian = (EditText) findViewById(R.id.edtLNameFarsi);
        edtFirstName = (EditText) findViewById(R.id.edtFName);
        edtLastName = (EditText) findViewById(R.id.edtLName);
        edtDatePassport = (EditText) findViewById(R.id.edtDatePassport);
        edtNoPassport = (EditText) findViewById(R.id.edtNoPassport);
        edtExportingCountry = (EditText) findViewById(R.id.edtExportingCountry);
        edtBirthDay = (EditText) findViewById(R.id.edtBirthDay);

        edtBirthDay.setOnClickListener(onClickListener);
        edtBirthDay.setFocusable(false);
        edtBirthDay.setCursorVisible(false);

        edtExportingCountry.setOnClickListener(onClickListener);
        edtExportingCountry.setFocusable(false);
        edtExportingCountry.setCursorVisible(false);
        //-----------------------------------------------
        edtDatePassport.setFocusable(false);
        edtDatePassport.setCursorVisible(false);
        edtDatePassport.setOnClickListener(onClickListener);
        //-----------------------------------------------
        edtGender.setFocusable(false);
        edtGender.setCursorVisible(false);
        edtGender.setOnClickListener(onClickListener);
        edtNationalCode.setText(passengerTour.getNationalCode());
        edtFirstNamePersian.setText(passengerTour.getFirstNamePersian());
        edtLastNamePersian.setText(passengerTour.getLastNamePersian());
        edtBirthDay.setText(passengerTour.getBirthDayPersian());
        edtExportingCountry.setText(passengerTour.getNationalityCountryName());
        int genderId = passengerTour.getGender();
        if (genderId == DomesticPassengerInfo.GENDER_MALE) {
            edtGender.setText(R.string.male);
        } else if (genderId == DomesticPassengerInfo.GENDER_FEMALE) {
            edtGender.setText(R.string.female);
        }
        hasFirstShowEdit = false;
        if (typeTour.contentEquals(TourRules.TOUR_ONE_DAY)) {
            setupTourOneDay();
        } else if (typeTour.contentEquals(TourRules.TOUR_DOMESTIC)) {
            setupTourDomestic();
        } else if (typeTour.contentEquals(TourRules.TOUR_INTERNATIONAL)) {
            setupTourInternational();
        } else {
            ToastMessageBar.show(this, R.string.msgErrorPayment);
            finish();
        }
    }

    //-----------------------------------------------
    private void setupTourOneDay() {
        layoutForeignPassport.setVisibility(View.GONE);
        layoutFullNameEng.setVisibility(View.GONE);

    }

    //-----------------------------------------------
    private void setupTourDomestic() {
        layoutForeignPassport.setVisibility(View.GONE);
        layoutFullNameEng.setVisibility(View.VISIBLE);
        edtFirstName.setText(passengerTour.getFirstNameEng());
        edtLastName.setText(passengerTour.getLastNameEng());
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
    }

    //-----------------------------------------------
    private void setupTourInternational() {
        layoutForeignPassport.setVisibility(View.VISIBLE);
        layoutFullNameEng.setVisibility(View.VISIBLE);
        edtFirstName.setText(passengerTour.getFirstNameEng());
        edtLastName.setText(passengerTour.getLastNameEng());
        edtNoPassport.setText(passengerTour.getPassportNumber());
        edtDatePassport.setText(passengerTour.getExpireDate());
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
    }

    //-----------------------------------------------
    private TextWatcher textWatcherNationalCode = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 10 && !hasFirstShowEdit) {
                getInfo(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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
                                passengerTour.setPassengerType(FlightRules.TP_ADULTS);
                            } else if (dataPassengerInfo.getPassengersType().contentEquals(String.valueOf(FlightRules.TP_CHILDREN))) {
                                tabsTypePassenger.getTabAt(1).select();
                                passengerTour.setPassengerType(FlightRules.TP_CHILDREN);
                            } else if (dataPassengerInfo.getPassengersType().contentEquals(String.valueOf(FlightRules.TP_INFANT))) {
                                tabsTypePassenger.getTabAt(0).select();
                                passengerTour.setPassengerType(FlightRules.TP_INFANT);
                            }

                            edtFirstNamePersian.setText(dataPassengerInfo.getPassengerNamePersian());
                            passengerTour.setFirstNamePersian(dataPassengerInfo.getPassengerNamePersian());

                            edtLastNamePersian.setText(dataPassengerInfo.getPassengerFamilyPersian());
                            passengerTour.setLastNamePersian(dataPassengerInfo.getPassengerFamilyPersian());

                            edtFirstName.setText(dataPassengerInfo.getPassengerNameEnglish());
                            passengerTour.setFirstNameEng(dataPassengerInfo.getPassengerNameEnglish());

                            edtLastName.setText(dataPassengerInfo.getPassengerFamilyEnglish());
                            passengerTour.setLastNameEng(dataPassengerInfo.getPassengerFamilyEnglish());

                            if (Integer.valueOf(dataPassengerInfo.getGender()) == PassengerTour.GENDER_MALE) {
                                passengerTour.setGender(Integer.valueOf(dataPassengerInfo.getGender()));
                                edtGender.setText(R.string.male);

                            } else if (Integer.valueOf(dataPassengerInfo.getGender()) == PassengerTour.GENDER_MALE) {
                                passengerTour.setGender(Integer.valueOf(dataPassengerInfo.getGender()));
                                edtGender.setText(R.string.female);
                            } else {
                                passengerTour.setGender(PassengerTour.GENDER_MALE);
                                edtGender.setText(R.string.male);
                            }

                            edtNoPassport.setText(dataPassengerInfo.getPassportNumber());
                            passengerTour.setPassportNumber(dataPassengerInfo.getPassportNumber());

                            //edtDatePassport.setText(dataPassengerInfo.getPassportExpiryDate());
                            //passengerTour.setExpireDate(dataPassengerInfo.getPassportExpiryDate());

                            edtBirthDay.setText(dataPassengerInfo.getDateOfBirthp());
                            passengerTour.setBirthDayPersian(dataPassengerInfo.getDateOfBirthp());
                            passengerTour.setBirthDayGreg(dataPassengerInfo.getDateOfBirth());
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            Calendar newCalendar = Calendar.getInstance();
                            if (passengerTour.getBirthDayGreg().length() > 0) {
                                try {
                                    Date d = dateFormatter.parse(passengerTour.getExpireDate());
                                    newCalendar.setTime(d);
                                    passengerTour.setBirthDayPersianTimeStamp(String.valueOf(newCalendar.getTimeInMillis()));
                                } catch (Exception e) {

                                }
                            }

                            Keyboard.closeKeyboard(ActivityRegisterPassengerTour.this);
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
                    passengerTour.setGender(DomesticPassengerInfo.GENDER_MALE);
                else
                    passengerTour.setGender(DomesticPassengerInfo.GENDER_FEMALE);
                return false;
            }
        });
        popupMenu.show();
    }

    //-----------------------------------------------
    @Override
    public void onBackPressed() {
        getDomesticPassengerInfoByView(hasError());
        super.onBackPressed();

    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.edtDatePassport:
                    getGregorianDateExpirePassport();
                    break;
                case R.id.edtBirthDay:
                    getDatePersian();
                    break;
                case R.id.edtGender:
                    showPopupMenuGender();
                    break;
                case R.id.btnRegister:
                    if (validate())
                        getDomesticPassengerInfoByView(false);
                    break;
                case R.id.edtExportingCountry:
                    Intent intent = new Intent(ActivityRegisterPassengerTour.this, SearchCountryActivity.class);
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
                    passengerTour.setNationalityCountryCode(country.getCode());
                    passengerTour.setNationalityCountryName(country.getPersian());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //-----------------------------------------------
    private void getDatePersian() {

        final PersianCalendar persianCalendar = new PersianCalendar();
        try {
            SimpleDateFormat sdfCurrent = new SimpleDateFormat("yyyy-MM-dd");
            String selectedDate = (passengerTour.getBirthDayGreg() != null && passengerTour.getBirthDayGreg().length() > 0) ? passengerTour.getBirthDayGreg() : "";
            Date dateCurrent = sdfCurrent.parse(selectedDate);
            Calendar calendarGreg = Calendar.getInstance();
            calendarGreg.setTime(dateCurrent);
            Calendar calendar = ToolsPersianCalendar.getPersianCalendar(calendarGreg);
            persianCalendar.setPersianDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            //persianCalendar.setTime(getDate(edtBirthDay.getTag()));
        } catch (Exception e) {

        }

        com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog datePickerDialog = com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog.newInstance(
                new com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        DateFormat df = new DateFormat();
                        Calendar calendar = ToolsPersianCalendar.getGregorianCalendar(year, monthOfYear + 1, dayOfMonth);
                        String dateGeorge = df.format("yyyy-MM-dd", calendar).toString();
                        String weekName = ToolsPersianCalendar.getDayOfWeek(calendar.getTime());
                        String monthName = ToolsPersianCalendar.getPersianMonthString(calendar.getTime());
                        String newDate = "";
                        if ((monthOfYear + 1) < 10)
                            newDate += year + "/0" + (monthOfYear + 1);
                        else
                            newDate += year + "/" + (monthOfYear + 1);

                        if (dayOfMonth < 10)
                            newDate += "/0" + (dayOfMonth);
                        else
                            newDate += "/" + (dayOfMonth);
                        edtBirthDay.setTag(dateGeorge);
                        edtBirthDay.setText(newDate);
                        passengerTour.setBirthDayPersian(newDate);
                        passengerTour.setBirthDayGreg(dateGeorge);
                        passengerTour.setBirthDayPersianTimeStamp(String.valueOf(calendar.getTimeInMillis()));
                        //edtBirthDay.setText(weekName + "," + dayOfMonth + monthName + "," + year);
                        //String datePersian = weekName + "," + dayOfMonth + monthName + "," + year;
                    }
                },
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
    }

    //-----------------------------------------------
    private void getGregorianDateExpirePassport() {
        try {
            DatePickerDialog fromDatePickerDialog;
            final SimpleDateFormat dateFormatter;
            dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Calendar newCalendar = Calendar.getInstance();
            if (passengerTour.getExpireDate().length() > 0) {
                Date d = dateFormatter.parse(passengerTour.getExpireDate());
                newCalendar.setTime(d);
            }
            fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    //editText.setText(Keyboard.convertPersianNumberToEngNumber(dateFormatter.format(newDate.getTime())));
                    passengerTour.setExpireDate(dateFormatter.format(newDate.getTime()));
                    passengerTour.setExpireDateTimeStamp(newDate.getTimeInMillis());
                    passengerTour.setPassStartDateTimeStamp(String.valueOf(newDate.getTimeInMillis()));
                    passengerTour.setPassStartDate(dateFormatter.format(newDate.getTime()));
                    edtDatePassport.setText(dateFormatter.format(newDate.getTime()));
                    edtDatePassport.setError(null);
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
    private void getDomesticPassengerInfoByView(Boolean hasError) {
        passengerTour.setFirstNamePersian(edtFirstNamePersian.getText().toString());
        passengerTour.setLastNamePersian(edtLastNamePersian.getText().toString());
        passengerTour.setFirstNameEng(edtFirstName.getText().toString());
        passengerTour.setLastNameEng(edtLastName.getText().toString());
        passengerTour.setNationalCode(edtNationalCode.getText().toString());
        passengerTour.setPassportNumber(edtNoPassport.getText().toString());
        int error = hasError ? 1 : 0;
        passengerTour.setHasError(error);
        if (hasShowAndEdit) {
            Intent intent = new Intent();
            intent.putExtra(PassengerTour.class.getName(), passengerTour);
            intent.putExtra("index", index);
            setResult(RESULT_OK_EDIT_PASSENGER, intent);
            finish();
        } else {
            Intent intent = new Intent();
            intent.putExtra(PassengerTour.class.getName(), passengerTour);
            setResult(RESULT_OK_ADD_PASSENGER, intent);
            finish();
        }
    }

    //-----------------------------------------------
    private Boolean validate() {
        passengerTour.setFirstNamePersian(edtFirstNamePersian.getText().toString());
        passengerTour.setLastNamePersian(edtLastNamePersian.getText().toString());
        passengerTour.setFirstNameEng(edtFirstName.getText().toString());
        passengerTour.setLastNameEng(edtLastName.getText().toString());
        passengerTour.setNationalCode(edtNationalCode.getText().toString());
        passengerTour.setPassportNumber(edtNoPassport.getText().toString());
        if (!ValidateNationalCode.isValidNationalCode(edtNationalCode.getText().toString())) {
            ToastMessageBar.show(ActivityRegisterPassengerTour.this, R.string.validateNationalCode);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtNationalCode.startAnimation(vibrateAnimation);
            edtNationalCode.requestFocus();
            return false;
        }
        if (edtFirstNamePersian.length() == 0) {
            ToastMessageBar.show(ActivityRegisterPassengerTour.this, R.string.validateFirstNamePersian);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtFirstNamePersian.startAnimation(vibrateAnimation);
            return false;
        }
        if (edtLastNamePersian.length() == 0) {
            ToastMessageBar.show(ActivityRegisterPassengerTour.this, R.string.validateLastNamePersian);
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtLastNamePersian.startAnimation(vibrateAnimation);
            return false;
        }
        if (!typeTour.contains(TourRules.TOUR_ONE_DAY)) {
            if (edtFirstName.length() == 0) {
                ToastMessageBar.show(ActivityRegisterPassengerTour.this, R.string.validateFirstName);
                Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
                edtFirstName.startAnimation(vibrateAnimation);
                return false;
            }
            if (edtLastName.length() == 0) {
                ToastMessageBar.show(ActivityRegisterPassengerTour.this, R.string.validateLastName);
                Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
                edtLastName.startAnimation(vibrateAnimation);
                return false;
            }
            if (typeTour.contains(TourRules.TOUR_INTERNATIONAL)) {
                if (passengerTour.getPassportNumber().length() == 0 || edtNoPassport.length() == 0) {
                    ToastMessageBar.show(ActivityRegisterPassengerTour.this, R.string.validatePassportCode);
                    Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
                    edtNoPassport.startAnimation(vibrateAnimation);
                    return false;
                }
                if (passengerTour.getExpireDate().length() == 0 || edtDatePassport.length() == 0) {
                    ToastMessageBar.show(ActivityRegisterPassengerTour.this, R.string.validatePassportExpDate);
                    Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
                    edtDatePassport.startAnimation(vibrateAnimation);
                    return false;
                }
            }
        }
        passengerTour.setHasValidate(1);
        return true;
    }

    //-----------------------------------------------
    private Boolean hasError() {
        passengerTour.setFirstNamePersian(edtFirstNamePersian.getText().toString());
        passengerTour.setLastNamePersian(edtLastNamePersian.getText().toString());
        passengerTour.setFirstNameEng(edtFirstName.getText().toString());
        passengerTour.setLastNameEng(edtLastName.getText().toString());
        passengerTour.setNationalCode(edtNationalCode.getText().toString());
        passengerTour.setPassportNumber(edtNoPassport.getText().toString());
        if (!ValidateNationalCode.isValidNationalCode(edtNationalCode.getText().toString())) {
            return true;
        }
        if (edtFirstNamePersian.length() == 0) {
            return true;
        }
        if (edtLastNamePersian.length() == 0) {
            return true;
        }
        if (!typeTour.contains(TourRules.TOUR_ONE_DAY)) {
            if (edtFirstName.length() == 0) {
                return true;
            }
            if (edtLastName.length() == 0) {
                return true;
            }
            if (typeTour.contains(TourRules.TOUR_INTERNATIONAL)) {
                if (passengerTour.getPassportNumber().length() == 0 || edtNoPassport.length() == 0) {
                    return true;
                }
                if (passengerTour.getExpireDate().length() == 0 || edtDatePassport.length() == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
