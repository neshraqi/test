package hami.aniticket.Activity.ServiceSearch.ServiceTrain.Services.Fragment.View;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.DataPassengerInfo;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.InternationalApi;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.NationalCodePresenter;
import hami.aniticket.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainPassengerInfo;
import hami.aniticket.Const.TrainRules;
import hami.aniticket.R;
import hami.aniticket.Util.Keyboard;
import hami.aniticket.Util.ToolsPersianCalendar;
import hami.aniticket.Util.UtilFonts;
import hami.aniticket.View.Validation.ValidationClass;


public class DialogTrainAddPassenger {

    private FragmentActivity activity;
    private AlertDialog alertDialog;
    private EditText edtNationalCodeOrPassport, edtBirthDay, edtTypePassenger, edtFirstNamePersian, edtLastNamePersian, edtType;
    private TextInputLayout tilNationalCodeOrPassport;
    private TextView txtTypePassenger;
    private OnPassengerTrainListener onPassengerTrainListener;
    private int passengerTypeNationality;
    private int position;
    private TrainPassengerInfo trainPassengerInfo;
    private Boolean hasShowAndEdit = false, hasFirstShowEdit = false;
    private Boolean hasIranian = true;
    private static final String TAG = "DialogTrainAddPassenger";

    //-----------------------------------------------
    public DialogTrainAddPassenger(FragmentActivity activity, OnPassengerTrainListener onPassengerTrainListener) {
        this.onPassengerTrainListener = onPassengerTrainListener;
        this.activity = activity;
        this.passengerTypeNationality = TrainRules.PN_IRANIAN;
        hasShowAndEdit = hasFirstShowEdit = false;
        ini();
    }

    //-----------------------------------------------
    public DialogTrainAddPassenger(FragmentActivity activity, TrainPassengerInfo trainPassengerInfo, OnPassengerTrainListener onPassengerTrainListener, int position) {
        this.onPassengerTrainListener = onPassengerTrainListener;
        this.activity = activity;
        this.position = position;
        this.trainPassengerInfo = trainPassengerInfo;
        this.passengerTypeNationality = trainPassengerInfo.getNationalityType();
        hasShowAndEdit = hasFirstShowEdit = true;
        ini();
    }

    //-----------------------------------------------
    private void ini() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_service_train_register_passenger_layout, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        UtilFonts.overrideFonts(activity, dialogView, UtilFonts.IRAN_SANS_BOLD);
        TextView txtBtnAddPassenger = (TextView) dialogView.findViewById(R.id.btnRegister);
        TextView txtBtnCancel = (TextView) dialogView.findViewById(R.id.btnCancel);
        //-----------------------------------------------
        tilNationalCodeOrPassport = (TextInputLayout) dialogView.findViewById(R.id.tilNationalCodeOrPassport);
        txtTypePassenger = (TextView) dialogView.findViewById(R.id.txtTypePassenger);
        edtNationalCodeOrPassport = (EditText) dialogView.findViewById(R.id.edtNationalCodeOrPassport);
        edtBirthDay = (EditText) dialogView.findViewById(R.id.edtBirthDay);
        edtFirstNamePersian = (EditText) dialogView.findViewById(R.id.edtFnameFarsi);
        edtLastNamePersian = (EditText) dialogView.findViewById(R.id.edtLNameFarsi);
        edtTypePassenger = (EditText) dialogView.findViewById(R.id.edtTypePassenger);
        edtType = (EditText) dialogView.findViewById(R.id.edtType);
        edtNationalCodeOrPassport.addTextChangedListener(textWatcher);
        if (passengerTypeNationality == TrainRules.PN_IRANIAN) {
            edtType.setText(R.string.irani);
            tilNationalCodeOrPassport.setHint(activity.getString(R.string.nationalCode));
            edtNationalCodeOrPassport.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            edtType.setText(R.string.foreign);
            tilNationalCodeOrPassport.setHint(activity.getString(R.string.noPassport));
            edtNationalCodeOrPassport.setInputType(InputType.TYPE_CLASS_TEXT);
            //txtTypePassenger.setText(R.string.foreign);
        }
        //-----------------------------------------------
        edtBirthDay.setFocusable(false);
        edtBirthDay.setCursorVisible(false);
        edtBirthDay.setOnClickListener(onClickListener);
        //-----------------------------------------------
        edtType.setFocusable(false);
        edtType.setCursorVisible(false);
        edtType.setOnClickListener(onClickListener);
        //-----------------------------------------------
        edtTypePassenger.setText(R.string.adults);
        edtTypePassenger.setTag(R.string.objTag1, TrainRules.TP_ADULTS);
        edtTypePassenger.setTag(R.string.objTag2, TrainRules.TP_ADULTS);
        edtTypePassenger.setOnClickListener(onClickListener);
        edtTypePassenger.setFocusable(false);
        edtTypePassenger.setCursorVisible(false);
        //-----------------------------------------------
//        txtBtnAddPassenger.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DomesticPassengerInfo domesticPassengerInfo = DomesticPassengerInfo.newInstanceIran(passengerTypeNationality,edtBirthDay.getText().toString(),edtBirthDay.getTag().toString(),(int)edtGender.getTag(),edtFirstNamePersian.getText().toString(),edtLastNamePersian.getText().toString(),edtFirstName.getText().toString(),edtLastName.getText().toString(),edtNationalCode.getText().toString());
//                onAddPassengerListener.onAddPassenger(domesticPassengerInfo,false);
//                alertDialog.dismiss();
//                //mAdapter.addNewPassenger(domesticPassengerInfo);
//            }
//        });
        txtBtnAddPassenger.setOnClickListener(onClickListener);
        if (hasShowAndEdit)

        {
            iniInfoDefault();
        }
        //-----------------------------------------------
        txtBtnCancel.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    //-----------------------------------------------
    private void iniInfoDefault() {
        edtFirstNamePersian.setText(trainPassengerInfo.getFirstNamePersian());
        edtLastNamePersian.setText(trainPassengerInfo.getLastNamePersian());
        int nationalType = trainPassengerInfo.getNationalityType();
        if (nationalType == TrainRules.PN_IRANIAN) {
            edtNationalCodeOrPassport.setText(trainPassengerInfo.getNationalCode());
            edtBirthDay.setText(trainPassengerInfo.getBirthDayPersian());
            edtBirthDay.setTag(trainPassengerInfo.getBirthDayGregorian());
        } else {
            edtNationalCodeOrPassport.setText(trainPassengerInfo.getPassportNo());
            edtBirthDay.setText(trainPassengerInfo.getBirthDayGregorian());
            edtBirthDay.setTag(trainPassengerInfo.getBirthDayPersian());

        }
        edtFirstNamePersian.requestFocus();
        int typePassengerApp = trainPassengerInfo.getTypePassengerApp();
        int typePassenger = trainPassengerInfo.getTypePassenger();

        if (typePassengerApp == TrainRules.TP_ADULTS) {
            edtTypePassenger.setText(R.string.adults);
        } else if (typePassengerApp == TrainRules.TP_CHILD) {
            edtTypePassenger.setText(R.string.children);
        } else if (typePassengerApp == TrainRules.TP_SHAHED) {
            edtTypePassenger.setText(R.string.shahed);
        } else {
            edtTypePassenger.setText(R.string.veteran);
        }
        edtTypePassenger.setTag(R.string.objTag1, typePassenger);
        edtTypePassenger.setTag(R.string.objTag2, typePassengerApp);
        hasFirstShowEdit = false;
    }

    //-----------------------------------------------
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (passengerTypeNationality != TrainRules.PN_FOREIGN && s.length() == 10 && !hasFirstShowEdit) {
                getInfo(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //-----------------------------------------------
    private void getInfo(String nationalCode) {
        new InternationalApi(activity).getInfoByNationalCode(nationalCode, new NationalCodePresenter() {
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
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                edtFirstNamePersian.setText(dataPassengerInfo.getPassengerNamePersian());
                                edtLastNamePersian.setText(dataPassengerInfo.getPassengerFamilyPersian());
                                edtBirthDay.setText(dataPassengerInfo.getDateOfBirthp());
                                edtBirthDay.setTag(dataPassengerInfo.getDateOfBirth());
                            } catch (Exception e) {


                            }

                        }
                    });
                }
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
    private void showPopupMenuTypePassenger() {
        PopupMenu popupMenu = new PopupMenu(activity, edtTypePassenger);
        popupMenu.inflate(R.menu.menu_type_passenger_train);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                 @Override
                                                 public boolean onMenuItemClick(MenuItem item) {
                                                     edtTypePassenger.setText(item.getTitle());
                                                     if (item.getItemId() == R.id.menuAdults) {
                                                         edtTypePassenger.setTag(R.string.objTag1, TrainRules.TP_ADULTS);
                                                         edtTypePassenger.setTag(R.string.objTag2, TrainRules.TP_ADULTS);
                                                     } else if (item.getItemId() == R.id.menuChild) {
                                                         edtTypePassenger.setTag(R.string.objTag1, TrainRules.TP_CHILD);
                                                         edtTypePassenger.setTag(R.string.objTag2, TrainRules.TP_CHILD);
                                                     } else if (item.getItemId() == R.id.menuShahed) {
                                                         edtTypePassenger.setTag(R.string.objTag1, TrainRules.TP_SHAHED);
                                                         edtTypePassenger.setTag(R.string.objTag2, TrainRules.TP_SHAHED);
                                                     } else {
                                                         edtTypePassenger.setTag(R.string.objTag1, TrainRules.TP_VETERAN);
                                                         edtTypePassenger.setTag(R.string.objTag2, TrainRules.TP_VETERAN_APP);
                                                     }
                                                     return false;
                                                 }
                                             }

        );
        popupMenu.show();
    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edtBirthDay:
                    if (passengerTypeNationality == TrainRules.PN_IRANIAN) {
                        getDatePersian();
//                        if (edtBirthDay.getTag() != null) {
//                            getDatePersian(false, edtBirthDay.getTag().toString());
//                        } else
//                            getDatePersian(false, "");
                    } else {
                        getGregorianDateForBirthDay(edtBirthDay);
                    }
                    break;
                case R.id.edtTypePassenger:
                    showPopupMenuTypePassenger();
                    break;
                case R.id.btnRegister:
                    getDomesticPassengerInfoByView();
                    break;
                case R.id.edtType:
                    showPopupMenuType();
                    break;
            }
        }
    };

    //-----------------------------------------------
    private void showPopupMenuType() {
        PopupMenu popupMenu = new PopupMenu(activity, edtType);
        popupMenu.inflate(R.menu.menu_type_nationality);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                edtType.setText(item.getTitle());
                if (item.getItemId() == R.id.menuIranian) {
                    edtNationalCodeOrPassport.setInputType(InputType.TYPE_CLASS_NUMBER);
                    passengerTypeNationality = TrainRules.PN_IRANIAN;
                    tilNationalCodeOrPassport.setHint(activity.getString(R.string.nationalCode));
                } else {
                    edtNationalCodeOrPassport.setInputType(InputType.TYPE_CLASS_TEXT);
                    passengerTypeNationality = TrainRules.PN_FOREIGN;
                    tilNationalCodeOrPassport.setHint(activity.getString(R.string.noPassport));
                }
                return false;
            }
        });
        popupMenu.show();
    }

    //-----------------------------------------------
    private Date getDate(Object dateString) {
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            Date d = f.parse(dateString.toString());
            return d;
        } catch (Exception e) {
            return new Date();
        }
    }

    //-----------------------------------------------
    private void getDatePersian() {

        final PersianCalendar persianCalendar = new PersianCalendar();
        try {
            SimpleDateFormat sdfCurrent = new SimpleDateFormat("yyyy-MM-dd");
            String selectedDate = edtBirthDay.getTag() != null ? edtBirthDay.getTag().toString() : "";
            Date dateCurrent = sdfCurrent.parse(selectedDate);
            Calendar calendarGreg = Calendar.getInstance();
            calendarGreg.setTime(dateCurrent);
            Calendar calendar = ToolsPersianCalendar.getPersianCalendar(calendarGreg);
            persianCalendar.setPersianDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            //persianCalendar.setTime(getDate(edtBirthDay.getTag()));
        } catch (Exception e) {

        }

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
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

                        //edtBirthDay.setText(weekName + "," + dayOfMonth + monthName + "," + year);
                        //String datePersian = weekName + "," + dayOfMonth + monthName + "," + year;
                    }
                },
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );
        datePickerDialog.show(activity.getFragmentManager(), "Datepickerdialog");
    }

    //-----------------------------------------------
    private void getDatePersian(final Boolean hasReturn, @Nullable String selectedDate) {

        final PersianCalendar persianCalendar = new PersianCalendar();
        try {
            if (selectedDate != null && selectedDate.length() > 0) {
                SimpleDateFormat sdfCurrent = new SimpleDateFormat("yyyy-MM-dd");
                Date dateCurrent = sdfCurrent.parse(selectedDate);
                Calendar calendarGreg = Calendar.getInstance();
                calendarGreg.setTime(dateCurrent);
                Calendar calendar = ToolsPersianCalendar.getPersianCalendar(calendarGreg);
                persianCalendar.setPersianDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            }
        } catch (Exception e) {

        }
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        DateFormat df = new DateFormat();
                        PersianCalendar persianCalendarSelected = new PersianCalendar();
                        persianCalendarSelected.setPersianDate(year, monthOfYear, dayOfMonth);
                        Calendar calendar = ToolsPersianCalendar.getGregorianCalendar(year, monthOfYear + 1, dayOfMonth);
                        String dateGeorge = df.format("yyyy-MM-dd", calendar).toString();
                        //String persianCalendarString = df.format("yyyy/MM/dd", persianCalendarSelected).toString();
                        String persianCalendarString = ToolsPersianCalendar.getPersianDate(persianCalendarSelected.getTime());
                        //persianCalendar.setGregorianChange(calendar.getTime());
                        edtBirthDay.setTag(dateGeorge);
                        edtBirthDay.setText(persianCalendarString);

                    }
                },
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );
//        if (navigationService.getSelectedTabPosition() == ServiceID.SERVICE_ID_FLIGHT_DOMESTIC) {
//            datePickerDialog.setPlaceFlight("", "");
//        }
        //datePickerDialog.setMaxDate(new PersianCalendar());
        datePickerDialog.show(activity.getFragmentManager(), "Datepickerdialog");
    }

    //-----------------------------------------------
    private void getGregorianDateForBirthDay(final EditText editText) {
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment();
        cdp.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
            @Override
            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                String persianDate = ToolsPersianCalendar.getPersianDate(year, monthOfYear, dayOfMonth);
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

                String newDate = Keyboard.convertPersianNumberToEngNumber((year) + "-" + (monthStr) + "-" + dayOfMonthStr);
                editText.setText(newDate);
                editText.setTag(persianDate);

            }
        });
        cdp.show(activity.getSupportFragmentManager(), "Material Calendar Example");
    }

    //-----------------------------------------------
    private void getDomesticPassengerInfoByView() {
        edtNationalCodeOrPassport.setText(Keyboard.convertPersianNumberToEngNumber(edtNationalCodeOrPassport.getText().toString()));
        if (passengerTypeNationality == TrainRules.PN_IRANIAN) {

            if (!ValidationClass.validateNationalCodeToast(activity, edtNationalCodeOrPassport.getText().toString())) {
                edtNationalCodeOrPassport.requestFocus();
                return;
            }
        } else {
            if (!ValidationClass.validateEditTextToast(activity, edtNationalCodeOrPassport, R.string.validatePassportCode, 10)) {
                edtNationalCodeOrPassport.requestFocus();
                return;
            }
        }
        if (!ValidationClass.validateEditTextToast(activity, edtFirstNamePersian, R.string.validateFirstNamePersian)) {
            edtFirstNamePersian.requestFocus();
            return;
        }
        if (!ValidationClass.validateEditTextToast(activity, edtLastNamePersian, R.string.validateLastNamePersian)) {
            edtLastNamePersian.requestFocus();
            return;
        }
        if (!ValidationClass.validateEditTextToast(activity, edtBirthDay, R.string.validateBirthDay)) {
            edtBirthDay.requestFocus();
            return;
        }
        trainPassengerInfo = new TrainPassengerInfo();
        int typePassenger = (int) edtTypePassenger.getTag(R.string.objTag1);
        int typePassengerApp = (int) edtTypePassenger.getTag(R.string.objTag2);
        if (hasShowAndEdit) {
            if (passengerTypeNationality == TrainRules.PN_IRANIAN) {
                trainPassengerInfo = trainPassengerInfo.newInstanceIran(
                        typePassenger,
                        typePassengerApp,
                        edtBirthDay.getText().toString(),
                        edtBirthDay.getTag().toString(),
                        edtFirstNamePersian.getText().toString(),
                        edtLastNamePersian.getText().toString(),
                        edtNationalCodeOrPassport.getText().toString());
                onPassengerTrainListener.onEditPassenger(trainPassengerInfo, position);
            } else {
                trainPassengerInfo = trainPassengerInfo.newInstanceForeign(
                        typePassenger,
                        typePassengerApp,
                        edtBirthDay.getText().toString(),
                        edtBirthDay.getTag().toString(),
                        edtFirstNamePersian.getText().toString(),
                        edtLastNamePersian.getText().toString(),
                        edtNationalCodeOrPassport.getText().toString());
                onPassengerTrainListener.onEditPassenger(trainPassengerInfo, position);
            }
        } else {
            if (passengerTypeNationality == TrainRules.PN_IRANIAN) {
                trainPassengerInfo = trainPassengerInfo.newInstanceIran(
                        typePassenger,
                        typePassengerApp,
                        edtBirthDay.getText().toString(),
                        edtBirthDay.getTag().toString(),
                        edtFirstNamePersian.getText().toString(),
                        edtLastNamePersian.getText().toString(),
                        edtNationalCodeOrPassport.getText().toString());
                onPassengerTrainListener.onAddPassenger(trainPassengerInfo, false);
            } else {
                trainPassengerInfo = trainPassengerInfo.newInstanceForeign(
                        typePassenger,
                        typePassengerApp,
                        edtBirthDay.getText().toString(),
                        edtBirthDay.getTag().toString(),
                        edtFirstNamePersian.getText().toString(),
                        edtLastNamePersian.getText().toString(),
                        edtNationalCodeOrPassport.getText().toString());
                onPassengerTrainListener.onAddPassenger(trainPassengerInfo, true);
            }
        }
        alertDialog.dismiss();
    }
    //-----------------------------------------------
}
