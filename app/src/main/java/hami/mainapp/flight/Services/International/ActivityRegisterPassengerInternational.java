package hami.mainapp.flight.Services.International;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hami.common.BaseController.BaseResult;
import com.hami.common.BaseController.ResultSearchPresenter;
import com.hami.common.Const.ServiceID;
import com.hami.common.Util.UtilFonts;
import com.hami.common.Util.ValidateNationalCode;
import com.hami.common.View.HeaderBar;

import hami.mainapp.R;
import hami.mainapp.flight.SearchCountryActivity;
import hami.mainapp.flight.Services.International.Controller.Model.Country;
import hami.mainapp.flight.Services.International.Controller.Model.DataPassengerInfo;
import hami.mainapp.flight.Services.International.Controller.Model.PassengerInfo;
import hami.mainapp.flight.Services.International.Controller.Presenter.InternationalApi;
import hami.mainapp.flight.Services.International.Controller.Presenter.NationalCodePresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityRegisterPassengerInternational extends AppCompatActivity {
    public final static String TAG_FLIGHT_INTERNATIONAL = "TAG_FLIGHT_INTERNATIONAL ";
    public TextInputEditText edtFirstNameEng,
            edtLastNameEng,
    //            edtFirstNamePersian,
//            edtLastNamePersian,
    edtNationalCode,
            edtGender,
            edtBirthDay,
            edtCountry,
            edtExpireDatePassport,
            edtPassportNumber,
            edtDateOfIssueOfThePassport,
            edtExportingCountry;

    private AppCompatButton btnRegister;
    private PassengerInfo passengerInfo;
    private int index;
    private HeaderBar headerBar;
    private LinearLayout layoutFullNamePersian;
    private int accessTranslateCounter = 2;
    private InternationalApi internationalApi;
    private static final String TAG = "ActivityRegisterPassengerInternational";
    private TextInputLayout tilNationalCode;

    //-----------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_intenational_register_passenger);
        passengerInfo = getIntent().getParcelableExtra(PassengerInfo.class.getName());
        index = getIntent().getExtras().getInt("index");
        initialComponentActivity();
    }
    //-----------------------------------------------

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt("accessTranslateCounter", accessTranslateCounter);
            outState.putInt("index", index);
            outState.putParcelable(PassengerInfo.class.getName(), passengerInfo);
        }

        super.onSaveInstanceState(outState);
    }
    //-----------------------------------------------

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            passengerInfo = savedInstanceState.getParcelable(PassengerInfo.class.getName());
            index = savedInstanceState.getInt("index");
            accessTranslateCounter = savedInstanceState.getInt("accessTranslateCounter");
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        UtilFonts.overrideFonts(this, findViewById(R.id.layoutMain), UtilFonts.IRAN_SANS_NORMAL);
        internationalApi = new InternationalApi(this);
        headerBar = findViewById(R.id.headerBar);
        headerBar.showMessageBar(R.string.warningPassenger);
        tilNationalCode = findViewById(R.id.tilNationalCode);
        edtFirstNameEng = findViewById(R.id.tietFirstNameEng);
        edtLastNameEng = findViewById(R.id.tietLastNameEng);
//        edtFirstNamePersian = findViewById(R.id.tietFirstNamePersian);
//        edtLastNamePersian = findViewById(R.id.tietLastNamePersian);
        edtNationalCode = findViewById(R.id.tietNationalCode);
        edtGender = findViewById(R.id.tietGender);
        edtBirthDay = findViewById(R.id.tietBirthday);
        edtCountry = findViewById(R.id.tietCountry);
        edtExportingCountry = findViewById(R.id.tietCountryExporting);
        edtPassportNumber = findViewById(R.id.tietPassportNumber);
        edtExpireDatePassport = findViewById(R.id.tietExpireDatePassport);
        edtDateOfIssueOfThePassport = findViewById(R.id.tietDateOfIssueOfThePassport);
        btnRegister = findViewById(R.id.btnRegister);
//
//        edtFirstNameEng.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus && edtFirstNamePersian.length() == 0 && accessTranslateCounter > 0)
//                    translateWord(edtFirstNameEng.getText().toString(), edtFirstNamePersian);
//            }
//        });
//        edtLastNameEng.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus && edtLastNamePersian.length() == 0 && accessTranslateCounter > 0)
//                    translateWord(edtLastNameEng.getText().toString(), edtLastNamePersian);
//            }
//        });
//        edtFirstNamePersian.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus && edtFirstNameEng.length() == 0 && accessTranslateCounter > 0)
//                    translateWord(edtFirstNamePersian.getText().toString(), edtFirstNameEng);
//            }
//        });
//        edtLastNamePersian.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus && edtLastNameEng.length() == 0 && accessTranslateCounter > 0)
//                    translateWord(edtLastNamePersian.getText().toString(), edtLastNameEng);
//            }
//        });

        edtGender.setOnClickListener(onClickListener);
        edtGender.setFocusable(false);
        edtGender.setCursorVisible(false);

        edtBirthDay.setOnClickListener(onClickListener);
        edtBirthDay.setFocusable(false);
        edtBirthDay.setCursorVisible(false);

        edtCountry.setOnClickListener(onClickListener);
        edtCountry.setFocusable(false);
        edtCountry.setCursorVisible(false);

        edtExportingCountry.setOnClickListener(onClickListener);
        edtExportingCountry.setFocusable(false);
        edtExportingCountry.setCursorVisible(false);

        edtExpireDatePassport.setOnClickListener(onClickListener);
        edtExpireDatePassport.setFocusable(false);
        edtExpireDatePassport.setCursorVisible(false);

        edtDateOfIssueOfThePassport.setOnClickListener(onClickListener);
        edtDateOfIssueOfThePassport.setFocusable(false);
        edtDateOfIssueOfThePassport.setCursorVisible(false);

        edtNationalCode.requestFocus();
        btnRegister.setOnClickListener(onClickListener);
        iniInfo();
        edtNationalCode.addTextChangedListener(textWatcher);
        setTypeFace();
        setupToolbar();
    }

    //-----------------------------------------------
    private void setTypeFace() {
        TextInputLayout tilCountryExporting = findViewById(R.id.tilCountryExporting);

        TextInputLayout tilCountry = findViewById(R.id.tilCountry);
        TextInputLayout tilGender = findViewById(R.id.tilGender);
        TextInputLayout tilLastNameEng = findViewById(R.id.tilLastNameEng);
        TextInputLayout tilFirstNameEng = findViewById(R.id.tilFirstNameEng);
        TextInputLayout tilLastNamePersian = findViewById(R.id.tilLastNamePersian);
        TextInputLayout tilFirstNamePersian = findViewById(R.id.tilFirstNamePersian);
        TextInputLayout tilPassportNumber = findViewById(R.id.tilPassportNumber);
        TextInputLayout tilBirthday = findViewById(R.id.tilBirthday);
        TextInputLayout tilExpireDatePassport = findViewById(R.id.tilExpireDatePassport);
        TextInputLayout tilDateOfIssueOfThePassport = findViewById(R.id.tilDateOfIssueOfThePassport);


        tilNationalCode.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL));
        tilCountryExporting.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL));
        tilCountry.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL));
        tilGender.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL));
        tilLastNameEng.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL));
        tilFirstNameEng.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL));
        tilLastNamePersian.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL));
        tilFirstNamePersian.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL));
        tilPassportNumber.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL));
        tilBirthday.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL));
        tilExpireDatePassport.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL));
        tilDateOfIssueOfThePassport.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL));
    }

    //-----------------------------------------------
    private void iniInfo() {
        edtFirstNameEng.setText(passengerInfo.getName());
        edtLastNameEng.setText(passengerInfo.getFamily());
//        edtFirstNamePersian.setText(passengerInfo.getNamePersian());
//        edtLastNamePersian.setText(passengerInfo.getFamilyPersian());
//        edtFirstNamePersian.setText(passengerInfo.getName());
//        edtLastNamePersian.setText(passengerInfo.getFamily());
        if (passengerInfo.getGender().contentEquals(PassengerInfo.MALE))
            edtGender.setText(getString(R.string.male));
        else if (passengerInfo.getGender().contentEquals(PassengerInfo.FEMALE))
            edtGender.setText(getString(R.string.female));
        else {
            passengerInfo.setGender(PassengerInfo.MALE);
            edtGender.setText(getString(R.string.male));
        }

        if (passengerInfo.getNationalityCountryCode().contentEquals("IR")) {
            edtNationalCode.setVisibility(View.VISIBLE);
            edtNationalCode.setText(passengerInfo.getNid());
        } else {
            edtNationalCode.setVisibility(View.GONE);
        }

        edtBirthDay.setText(passengerInfo.getBirthday());
        edtExpireDatePassport.setText(passengerInfo.getExpDate());
        edtBirthDay.setText(passengerInfo.getBirthday());
        edtPassportNumber.setText(passengerInfo.getPassportNumber());
        edtExportingCountry.setText(passengerInfo.getExportingCountryPersian());
        edtDateOfIssueOfThePassport.setText(passengerInfo.getDateOfIssueOfThePassport());
        edtCountry.setText(passengerInfo.getNationalityCountryPersian());
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
    private void setupToolbar() {
        ImageView btnBack = findViewById(R.id.btnBack);
//        LinearLayout layoutTitleToolbar = findViewById(R.id.layoutTitleToolbar);
//        TextView txtTitle = findViewById(R.id.txtTitleMenu);
//        TextView txtSubTitleMenu = findViewById(R.id.txtSubTitleMenu);
//        String valueTitle = "ویرایش اطلاعات مسافر";
//        String valueSubTitle = passengerInfo.getName() + " " + passengerInfo.getFamily();
//        txtTitle.setText(valueTitle);
//        txtSubTitleMenu.setText(valueSubTitle);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    //-----------------------------------------------
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 10) {
                getInfo(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    //-----------------------------------------------

    @Override
    public void onBackPressed() {
        if (validate())
            setData(false);
        else
            setData(true);
        super.onBackPressed();

    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.tietGender) {
                showGender();

            } else if (i == R.id.tietBirthday) {
                showBirthDay();

            } else if (i == R.id.tietCountry) {
                Intent intent = new Intent(ActivityRegisterPassengerInternational.this, SearchCountryActivity.class);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_COUNTRY);
                startActivityForResult(intent, 0);

            } else if (i == R.id.tietCountryExporting) {
                Intent intent;
                intent = new Intent(ActivityRegisterPassengerInternational.this, SearchCountryActivity.class);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_EXPORTING_COUNTRY);
                startActivityForResult(intent, 0);

            } else if (i == R.id.tietExpireDatePassport) {
                showExpireDate();

            } else if (i == R.id.tietDateOfIssueOfThePassport) {
                showDateOfIssueOfThePassport();

            } else if (i == R.id.btnRegister) {
                if (validateView())
                    setData(false);

            }
        }
    };

    //-----------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (data != null && data.getExtras() != null) {
                if (resultCode == ServiceID.SERVICE_COUNTRY) {
                    Country country = (Country) data.getExtras().getSerializable(Country.class.getName());
                    if (country.getCode().contentEquals("IR")) {
                        tilNationalCode.setVisibility(View.VISIBLE);
                    } else {
                        tilNationalCode.setVisibility(View.GONE);
                    }
                    edtCountry.setText(country.getPersian());
                    passengerInfo.setNationalityCountryCode(country.getCode());
                    passengerInfo.setNationalityCountry(country.getFullName());
                } else if (resultCode == ServiceID.SERVICE_EXPORTING_COUNTRY) {
                    Country country = (Country) data.getExtras().getSerializable(Country.class.getName());
                    edtExportingCountry.setText(country.getPersian());
                    passengerInfo.setExportingCountry(country.getCode());
                    passengerInfo.setExportingCountryCode(country.getCode());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //-----------------------------------------------
    private Boolean validateView() {
        Boolean status = true;
        if (passengerInfo.getNationalityCountryCode().contentEquals("IR") && edtNationalCode.length() == 0 && !ValidateNationalCode.isValidNationalCode(edtNationalCode.getText().toString())) {
            edtNationalCode.setError("کد ملی را با دقت وارد کنید");
            status = false;
        }
        if (edtFirstNameEng.length() == 0) {
            edtFirstNameEng.setError("نام را انگیلسی وارد کنید");
            status = false;
        }
        if (edtLastNameEng.length() == 0) {
            edtLastNameEng.setError("نام خانوادگی را انگیلسی وارد کنید");
            status = false;
        }
//        if (getIntent().hasExtra("hasPersian")) {
//        if (edtFirstNamePersian.length() == 0) {
//            edtFirstNamePersian.setError("نام را فارسی وارد کنید");
//            status = false;
//        }
//        if (edtLastNamePersian.length() == 0) {
//            edtLastNamePersian.setError("نام خانوادگی را فارسی وارد کنید");
//            status = false;
//        }
//        }
        if (edtBirthDay.length() == 0) {
            edtBirthDay.setError("تاریخ تولد مطابق با پاسپورت");
            status = false;
        }
        if (edtPassportNumber.length() == 0) {
            edtPassportNumber.setError("شماره پاسپورت را با دقت وارد کنید");
            status = false;
        }
        if (edtDateOfIssueOfThePassport.length() == 0) {
            edtDateOfIssueOfThePassport.setError("تاریخ صدور پاسپورت را با دقت انتخاب کنید");
            status = false;
        }
        if (edtExpireDatePassport.length() == 0) {
            edtExpireDatePassport.setError("تاریخ انقضا پاسپورت را با دقت انتخاب کنید");
            status = false;
        }
        if (edtCountry.length() == 0) {
            edtCountry.setError("تابعیت کشور را انتخاب کنید");
            status = false;
        }
        if (edtExportingCountry.length() == 0) {
            edtExportingCountry.setError("کشور صادرکننده را انتخاب کنید");
            status = false;
        }
        return status;
    }

    //-----------------------------------------------
    private Boolean validate() {
        Boolean status = true;
        if (edtFirstNameEng.length() == 0) {
            status = false;
        }
        if (edtLastNameEng.length() == 0) {
            status = false;
        }
//        if (edtFirstNamePersian.length() == 0) {
//            status = false;
//        }
        if (getIntent().hasExtra("hasPersian")) {
//        if (edtLastNamePersian.length() == 0) {
//            status = false;
//        }
            if (edtNationalCode.length() == 0) {
                status = false;
            }
        }
        if (edtBirthDay.length() == 0) {
            status = false;
        }
        if (edtPassportNumber.length() == 0) {
            status = false;
        }
        if (edtExpireDatePassport.length() == 0) {
            status = false;
        }
        if (edtDateOfIssueOfThePassport.length() == 0) {
            status = false;
        }
        if (edtCountry.length() == 0) {
            status = false;
        }
        if (edtExportingCountry.length() == 0) {
            status = false;
        }
        return status;
    }

    //-----------------------------------------------
    private void setData(Boolean hasError) {
        passengerInfo.setHasError(hasError);
        passengerInfo.setName(edtFirstNameEng.getText().toString());
        passengerInfo.setFamily(edtLastNameEng.getText().toString());
//        passengerInfo.setNamePersian(edtFirstNamePersian.getText().toString());
//        passengerInfo.setFamilyPersian(edtLastNamePersian.getText().toString());
        passengerInfo.setNamePersian("علی");
        passengerInfo.setFamilyPersian("احمدی");
        passengerInfo.setBirthday(edtBirthDay.getText().toString());
        passengerInfo.setNid(edtNationalCode.getText().toString());
        passengerInfo.setExpDate(edtExpireDatePassport.getText().toString());
        passengerInfo.setPassportNumber(edtPassportNumber.getText().toString());
        passengerInfo.setDateOfIssueOfThePassport(edtDateOfIssueOfThePassport.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(PassengerInfo.class.getName(), passengerInfo);
        intent.putExtra("index", index);
        setResult(1, intent);
        finish();
    }

    //-----------------------------------------------
    private void showGender() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_tools_gender_dialog_layout_, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        final TextView txtMale = dialogView.findViewById(R.id.txtMale);
        TextView txtFemale = dialogView.findViewById(R.id.txtFemale);
        txtMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtGender.setText(R.string.male);
                passengerInfo.setGender(PassengerInfo.MALE);
                alertDialog.dismiss();
            }
        });
        txtFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtGender.setText(R.string.female);
                passengerInfo.setGender(PassengerInfo.FEMALE);
                alertDialog.dismiss();
            }
        });
        //UtilFonts.overrideFonts(this, dialogView, UtilFonts.IRAN_SANS_BOLD);
        alertDialog.show();
    }

    //-----------------------------------------------
    private void showBirthDay() {
        try {
            DatePickerDialog fromDatePickerDialog;
            final SimpleDateFormat dateFormatter;
            dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Calendar newCalendar = Calendar.getInstance();
            if (passengerInfo.getBirthday().length() > 0) {
                Date d = dateFormatter.parse(passengerInfo.getBirthday());
                //long milliseconds = d.getTime();
                newCalendar.setTime(d);
            }
            fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    edtBirthDay.setText(dateFormatter.format(newDate.getTime()));
                    edtBirthDay.setError(null);
                    passengerInfo.setBirthday(edtBirthDay.getText().toString());

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
    private void showExpireDate() {
        try {
            DatePickerDialog fromDatePickerDialog;

            final SimpleDateFormat dateFormatter;
            dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Calendar newCalendar = Calendar.getInstance();
            if (passengerInfo.getExpDate().length() > 0) {
                Date d = dateFormatter.parse(passengerInfo.getExpDate());
                newCalendar.setTime(d);
            }
            fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    edtExpireDatePassport.setText(dateFormatter.format(newDate.getTime()));
                    edtExpireDatePassport.setError(null);
                    passengerInfo.setExpDate(edtExpireDatePassport.getText().toString());
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
    private void showDateOfIssueOfThePassport() {
        try {
            DatePickerDialog fromDatePickerDialog;

            final SimpleDateFormat dateFormatter;
            dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Calendar newCalendar = Calendar.getInstance();
            if (passengerInfo.getExpDate().length() > 0) {
                Date d = dateFormatter.parse(passengerInfo.getExpDate());
                newCalendar.setTime(d);
            }
            fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    edtDateOfIssueOfThePassport.setText(dateFormatter.format(newDate.getTime()));
                    edtDateOfIssueOfThePassport.setError(null);
                    passengerInfo.setDateOfIssueOfThePassport(edtDateOfIssueOfThePassport.getText().toString());
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
                            passengerInfo.setName(dataPassengerInfo.getPassengerNameEnglish());
                            passengerInfo.setFamily(dataPassengerInfo.getPassengerFamilyEnglish());
                            passengerInfo.setBirthday(dataPassengerInfo.getDateOfBirth());
                            passengerInfo.setGender(dataPassengerInfo.getGender());
                            if (dataPassengerInfo.getGender().contentEquals(PassengerInfo.MALE))
                                edtGender.setText(R.string.male);
                            else if (dataPassengerInfo.getGender().contentEquals(PassengerInfo.MALE))
                                edtGender.setText(R.string.female);
                            edtFirstNameEng.setText(dataPassengerInfo.getPassengerNameEnglish());
                            edtLastNameEng.setText(dataPassengerInfo.getPassengerFamilyEnglish());
//                            edtFirstNamePersian.setText(dataPassengerInfo.getPassengerNamePersian());
//                            edtLastNamePersian.setText(dataPassengerInfo.getPassengerFamilyPersian());
                            edtBirthDay.setText(dataPassengerInfo.getDateOfBirth());
                            edtPassportNumber.requestFocus();
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

}
