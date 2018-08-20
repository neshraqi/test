package hami.mainapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.hami.common.Const.ServiceID;
import com.hami.common.Util.Keyboard;
import com.hami.common.Util.TimeDate;
import com.hami.common.Util.ToolsPersianCalendar;
import com.hami.common.Util.UtilFonts;
import com.hami.common.View.ToastMessageBar;
import com.hami.serviceflight.SearchCountryActivity;
import com.hami.serviceflight.Services.International.Controller.Model.Country;
import com.hami.servicehotel.ActivityMainHotel;
import com.hami.servicehotel.Domestic.Controller.Model.DomesticHotelSearchRequest;
import com.hami.servicehotel.Domestic.Controller.Model.HotelDomesticCity;
import com.hami.servicehotel.International.Controller.Model.InternationalHotelSearchRequest;
import com.hami.servicehotel.International.Controller.Model.SearchCity;
import com.hami.servicehotel.International.Controller.Model.SearchDestination;
import com.hami.servicehotel.SearchPlaceHotelActivity;
import com.hami.servicehotel.ToolsHotelRoomCountOption;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class HotelServicesSearchMaterialFragment extends Fragment {

    private EditText edtFromPlaceDest, edtFromPlaceCity, edtCheckInDate, edtCheckOutDate;
    private AppCompatEditText edtNationality, edtSetRoom;
    private FloatingActionButton fabSearch;
    private InternationalHotelSearchRequest internationalHotelSearchRequest;
    private DomesticHotelSearchRequest domesticHotelSearchRequest;
    private AppCompatEditText edtFromPlaceDestDomestic;
    private EditText edtCheckInDateDomestic, edtCheckOutDateDomestic;
    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    private static final String TAG = "HotelServicesSearchMaterialFragment";
    public final static String INTENT_DESTINATION_ID = "idi";
    private View view;
    private TabLayout navigationService;

    //-----------------------------------------------
    public static HotelServicesSearchMaterialFragment newInstance() {
        Bundle args = new Bundle();
        HotelServicesSearchMaterialFragment fragment = new HotelServicesSearchMaterialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(com.hami.servicehotel.R.layout.fragment_hotel_main_material, container, false);
        initialComponentFragment();
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment() {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.TAHOMA);
        fabSearch = (FloatingActionButton) view.findViewById(com.hami.servicehotel.R.id.fabSearch);
        fabSearch.setOnClickListener(onClickListener);
        setupTab(view);
        navigationService.getTabAt(navigationService.getTabCount() - 1).select();
        Keyboard.closeKeyboard(getActivity());
    }

    //-----------------------------------------------
    private void setupTab(final View view) {
        navigationService = (TabLayout) view.findViewById(com.hami.servicehotel.R.id.tabsService);
        UtilFonts.applyFontTabServicesHotel(getActivity(), navigationService);
        navigationService.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setupHotelDomestic(View.GONE);
                        setupHotelInternational(View.VISIBLE);
                        break;
                    case 1:
                        setupHotelInternational(View.GONE);
                        setupHotelDomestic(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    //-----------------------------------------------
    private void setupHotelInternational(int visibility) {
        LinearLayout includeSearchHotelInternational = (LinearLayout) view.findViewById(com.hami.servicehotel.R.id.includeSearchHotelInternational);
        includeSearchHotelInternational.setVisibility(visibility);
        if (visibility == View.GONE) {
            includeSearchHotelInternational.setVisibility(View.GONE);
        } else {
            includeSearchHotelInternational.setVisibility(View.VISIBLE);
            ((CoordinatorLayout.LayoutParams) fabSearch.getLayoutParams()).anchorGravity = Gravity.RIGHT | Gravity.BOTTOM;
            internationalHotelSearchRequest = new InternationalHotelSearchRequest();
            edtFromPlaceDest = (EditText) view.findViewById(com.hami.servicehotel.R.id.edtFromPlaceDest);
            edtFromPlaceCity = (EditText) view.findViewById(com.hami.servicehotel.R.id.edtFromPlaceCity);
            edtCheckInDate = (EditText) view.findViewById(com.hami.servicehotel.R.id.edtCheckInDate);
            edtCheckOutDate = (EditText) view.findViewById(com.hami.servicehotel.R.id.edtCheckOutDate);
            edtNationality = (AppCompatEditText) view.findViewById(com.hami.servicehotel.R.id.edtNationality);
            edtSetRoom = (AppCompatEditText) view.findViewById(com.hami.servicehotel.R.id.edtSetRoom);

            edtFromPlaceDest.setFocusable(false);
            edtFromPlaceDest.setCursorVisible(false);
            edtFromPlaceDest.setText("");
            edtFromPlaceDest.setOnClickListener(onClickListenerInternational);

            edtFromPlaceCity.setFocusable(false);
            edtFromPlaceCity.setCursorVisible(false);
            edtFromPlaceCity.setText("");
            edtFromPlaceCity.setOnClickListener(onClickListenerInternational);

            edtCheckInDate.setFocusable(false);
            edtCheckInDate.setCursorVisible(false);
            edtCheckInDate.setText("");
            edtCheckInDate.setOnClickListener(onClickListenerInternational);

            edtCheckOutDate.setFocusable(false);
            edtCheckOutDate.setCursorVisible(false);
            edtCheckOutDate.setText("");
            edtCheckOutDate.setOnClickListener(onClickListenerInternational);

            edtNationality.setFocusable(false);
            edtNationality.setCursorVisible(false);
            edtNationality.setText("");
            edtNationality.setOnClickListener(onClickListenerInternational);

            edtSetRoom.setFocusable(false);
            edtSetRoom.setCursorVisible(false);
            edtSetRoom.setText("");
            edtSetRoom.setOnClickListener(onClickListenerInternational);
        }
    }

    //-----------------------------------------------
    private void setupHotelDomestic(int visibility) {
        LinearLayout includeSearchHotelDomestic = (LinearLayout) view.findViewById(com.hami.servicehotel.R.id.includeSearchHotelDomestic);
        if (visibility == View.GONE) {
            includeSearchHotelDomestic.setVisibility(View.GONE);
        } else {
            UtilFonts.overrideFonts(getActivity(), includeSearchHotelDomestic, UtilFonts.IRAN_SANS_NORMAL);
            includeSearchHotelDomestic.setVisibility(View.VISIBLE);
            ((CoordinatorLayout.LayoutParams) fabSearch.getLayoutParams()).anchorGravity = Gravity.LEFT | Gravity.BOTTOM;
            domesticHotelSearchRequest = new DomesticHotelSearchRequest();
            edtFromPlaceDestDomestic = (AppCompatEditText) includeSearchHotelDomestic.findViewById(com.hami.servicehotel.R.id.edtFromPlaceDestDomestic);
            edtCheckInDateDomestic = (EditText) includeSearchHotelDomestic.findViewById(com.hami.servicehotel.R.id.edtCheckInDateDomestic);
            edtCheckOutDateDomestic = (EditText) includeSearchHotelDomestic.findViewById(com.hami.servicehotel.R.id.edtCheckOutDateDomestic);

            edtFromPlaceDestDomestic.setFocusable(false);
            edtFromPlaceDestDomestic.setCursorVisible(false);
            edtFromPlaceDestDomestic.setText("");
            edtFromPlaceDestDomestic.setOnClickListener(onClickListenerDomestic);

            edtCheckInDateDomestic.setFocusable(false);
            edtCheckInDateDomestic.setCursorVisible(false);
            edtCheckInDateDomestic.setText("");
            edtCheckInDateDomestic.setOnClickListener(onClickListenerDomestic);

            edtCheckOutDateDomestic.setFocusable(false);
            edtCheckOutDateDomestic.setCursorVisible(false);
            edtCheckOutDateDomestic.setText("");
            edtCheckOutDateDomestic.setOnClickListener(onClickListenerDomestic);
        }
    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == com.hami.servicehotel.R.id.fabSearch) {
                searchHotel();

            }
        }
    };
    //-----------------------------------------------
    View.OnClickListener onClickListenerInternational = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            int i = v.getId();
            if (i == com.hami.servicehotel.R.id.edtFromPlaceDest) {
                intent = new Intent(getActivity(), SearchPlaceHotelActivity.class);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_ID_HOTEL_DESTINATION);
                startActivityForResult(intent, 0);

            } else if (i == com.hami.servicehotel.R.id.edtFromPlaceCity) {
                if (internationalHotelSearchRequest.getCountryId() != null && internationalHotelSearchRequest.getCountryId().length() > 0) {
                    intent = new Intent(getActivity(), SearchPlaceHotelActivity.class);
                    intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_ID_HOTEL_CITY);
                    intent.putExtra(INTENT_DESTINATION_ID, internationalHotelSearchRequest.getCountryId());
                    startActivityForResult(intent, 0);
                } else {
                    Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), com.hami.servicehotel.R.anim.shake);
                    edtFromPlaceDest.startAnimation(vibrateAnimation);
                    ToastMessageBar.showEngFont(getActivity(), com.hami.servicehotel.R.string.showCaseFromPlaceDestEng);
                }

            } else if (i == com.hami.servicehotel.R.id.edtCheckInDate) {
                getDateGreg(false, null);

            } else if (i == com.hami.servicehotel.R.id.edtCheckOutDate) {
                getDateGreg(true, internationalHotelSearchRequest.getCheckin());

            } else if (i == com.hami.servicehotel.R.id.edtNationality) {
                intent = new Intent(getActivity(), SearchCountryActivity.class);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_COUNTRY);
                startActivityForResult(intent, 0);

            } else if (i == com.hami.servicehotel.R.id.edtSetRoom) {
                setRooms();

            }
        }
    };
    //-----------------------------------------------
    View.OnClickListener onClickListenerDomestic = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            int i = v.getId();
            if (i == com.hami.servicehotel.R.id.edtFromPlaceDestDomestic) {
                intent = new Intent(getActivity(), SearchPlaceMainActivity.class);
                intent.putExtra(SearchPlaceMainActivity.INTENT_HAS_TAKE_OFF, false);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_ID_HOTEL_DESTINATION_DOMESTIC);
                startActivityForResult(intent, 0);

            } else if (i == com.hami.servicehotel.R.id.edtCheckInDateDomestic) {
                getDatePersian(false, null);

            } else if (i == com.hami.servicehotel.R.id.edtCheckOutDateDomestic) {
                getDatePersian(true, domesticHotelSearchRequest.getCheckIn());

            }
        }
    };

    //-----------------------------------------------
    private void searchHotel() {
        if (navigationService.getSelectedTabPosition() == 0) {
            searchHotelInternational();
        } else {
            searchHotelDomestic();
        }
    }

    //-----------------------------------------------
    private void searchHotelDomestic() {
        if (validateFormHotelDomestic()) {
            Intent intent = new Intent(getActivity(), ActivityMainHotel.class);
            intent.putExtra(DomesticHotelSearchRequest.class.getName(), domesticHotelSearchRequest);
            startActivity(intent);
        }
    }

    //-----------------------------------------------
    private void searchHotelInternational() {
        if (validateFormHotelInternational()) {
            Intent intent = new Intent(getActivity(), ActivityMainHotel.class);
            intent.putExtra(InternationalHotelSearchRequest.INTENT_HOTEL_SEARCH_REQUEST, internationalHotelSearchRequest.toString());
            intent.putExtra(InternationalHotelSearchRequest.class.getName(), internationalHotelSearchRequest);
            startActivity(intent);
        }
    }

    //-----------------------------------------------
    private void setRooms() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final ToolsHotelRoomCountOption toolsHotelRoomCountOption = new ToolsHotelRoomCountOption(getActivity());
        UtilFonts.overrideFonts(getActivity(), toolsHotelRoomCountOption, UtilFonts.IRAN_SANS_NORMAL);
        builder.setView(toolsHotelRoomCountOption);
        final AlertDialog alertDialog = builder.create();

        //toolsHotelRoomCountOption.setRoomIni(internationalHotelSearchRequest);
        toolsHotelRoomCountOption.setCallbackAcceptButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internationalHotelSearchRequest.setAdults(toolsHotelRoomCountOption.getAdultsList());
                internationalHotelSearchRequest.setAdult(toolsHotelRoomCountOption.getAllAdultCount());
                internationalHotelSearchRequest.setChilds(toolsHotelRoomCountOption.getChildList());
                internationalHotelSearchRequest.setChild(toolsHotelRoomCountOption.getAllChildCount());
                internationalHotelSearchRequest.setChildages(toolsHotelRoomCountOption.getChildAges());
                internationalHotelSearchRequest.setChildages(toolsHotelRoomCountOption.getChildAges());
                internationalHotelSearchRequest.setChildage(toolsHotelRoomCountOption.getChildAge());
                internationalHotelSearchRequest.setRooms(toolsHotelRoomCountOption.getRoomCount());
                edtSetRoom.setText(toolsHotelRoomCountOption.getRoomCount() + " " + getString(com.hami.servicehotel.R.string.roomEng));
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    //-----------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (data != null && data.getExtras() != null) {
                if (resultCode == ServiceID.SERVICE_ID_HOTEL_DESTINATION) {
                    SearchDestination searchDestination = (SearchDestination) data.getExtras().getSerializable(SearchDestination.class.getName());
                    edtFromPlaceDest.setText(searchDestination.getName());
                    internationalHotelSearchRequest.setCountry(searchDestination.getName());
                    internationalHotelSearchRequest.setCountryId(searchDestination.getId());
                } else if (resultCode == ServiceID.SERVICE_ID_HOTEL_CITY) {
                    SearchCity searchCity = (SearchCity) data.getExtras().getSerializable(SearchCity.class.getName());
                    edtFromPlaceCity.setText(searchCity.getName());
                    internationalHotelSearchRequest.setCityId(searchCity.getId());
                    internationalHotelSearchRequest.setCityName(searchCity.getName());
                } else if (resultCode == ServiceID.SERVICE_COUNTRY) {
                    Country country = (Country) data.getExtras().getSerializable(Country.class.getName());
                    edtNationality.setText(country.getFullName());
                    internationalHotelSearchRequest.setNationality(country.getCode());
                }
                if (resultCode == ServiceID.SERVICE_ID_HOTEL_DESTINATION_DOMESTIC) {
                    HotelDomesticCity searchDestination = (HotelDomesticCity) data.getExtras().getSerializable(HotelDomesticCity.class.getName());
                    edtFromPlaceDestDomestic.setText(searchDestination.getNameFa() + "," + searchDestination.getName());
                    domesticHotelSearchRequest.setCityNamePersian(searchDestination.getNameFa());
                    domesticHotelSearchRequest.setCityNameEng(searchDestination.getName());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //-----------------------------------------------
    private Boolean validateFormHotelInternational() {
        try {
            if (internationalHotelSearchRequest.getCountry() == null && internationalHotelSearchRequest.getCountry().length() == 0 && internationalHotelSearchRequest.getCountryId() == null && internationalHotelSearchRequest.getCountryId().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), com.hami.servicehotel.R.anim.shake);
                edtFromPlaceDest.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(getActivity(), com.hami.servicehotel.R.string.showCaseFromPlaceDestEng);
                return false;
            } else if (internationalHotelSearchRequest.getCityName() == null && internationalHotelSearchRequest.getCityName().length() == 0 && internationalHotelSearchRequest.getCityId() == null && internationalHotelSearchRequest.getCityId().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), com.hami.servicehotel.R.anim.shake);
                edtFromPlaceCity.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(getActivity(), com.hami.servicehotel.R.string.showCaseFromPlaceCityEng);
                return false;
            } else if (internationalHotelSearchRequest.getCheckin() == null && internationalHotelSearchRequest.getCheckin() == null) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), com.hami.servicehotel.R.anim.shake);
                edtCheckInDate.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(getActivity(), com.hami.servicehotel.R.string.showCaseCheckInEng);
                return false;
            } else if (internationalHotelSearchRequest.getCheckout() == null && internationalHotelSearchRequest.getCheckout() == null) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), com.hami.servicehotel.R.anim.shake);
                edtCheckOutDate.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(getActivity(), com.hami.servicehotel.R.string.showCaseCheckOutEng);
                return false;
            } else if (internationalHotelSearchRequest.getNationality() == null && internationalHotelSearchRequest.getNationality() == null) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), com.hami.servicehotel.R.anim.shake);
                edtNationality.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(getActivity(), com.hami.servicehotel.R.string.showCaseNationalityEng);
                return false;
            } else if (internationalHotelSearchRequest.getRooms() == null && internationalHotelSearchRequest.getRooms() == null) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), com.hami.servicehotel.R.anim.shake);
                edtSetRoom.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(getActivity(), com.hami.servicehotel.R.string.showCaseRoomEng);
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //-----------------------------------------------
    private Boolean validateFormHotelDomestic() {
        try {
            if (domesticHotelSearchRequest.getCityNameEng() == null &&
                    domesticHotelSearchRequest.getCityNameEng().length() == 0 &&
                    domesticHotelSearchRequest.getCityNamePersian() == null &&
                    domesticHotelSearchRequest.getCityNamePersian().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), com.hami.servicehotel.R.anim.shake);
                edtFromPlaceDestDomestic.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(getActivity(), com.hami.servicehotel.R.string.pleaseToPlace);
                return false;
            } else if (domesticHotelSearchRequest.getCheckIn() == null &&
                    domesticHotelSearchRequest.getCheckIn().length() == 0 &&
                    domesticHotelSearchRequest.getCheckInPersianShort() == null &&
                    domesticHotelSearchRequest.getCheckInPersianShort().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), com.hami.servicehotel.R.anim.shake);
                edtCheckInDateDomestic.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(getActivity(), com.hami.servicehotel.R.string.showCaseCheckInDate);
                return false;
            } else if (domesticHotelSearchRequest.getCheckOut() == null &&
                    domesticHotelSearchRequest.getCheckOut().length() == 0 &&
                    domesticHotelSearchRequest.getCheckOutPersianShort() == null &&
                    domesticHotelSearchRequest.getCheckOutPersianShort().length() == 0) {
                Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), com.hami.servicehotel.R.anim.shake);
                edtCheckOutDateDomestic.startAnimation(vibrateAnimation);
                ToastMessageBar.showEngFont(getActivity(), com.hami.servicehotel.R.string.showCaseCheckOutDate);
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
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
                calendarGreg.add(Calendar.DAY_OF_MONTH, 1);
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
                        Calendar calendar = ToolsPersianCalendar.getGregorianCalendar(year, monthOfYear + 1, dayOfMonth);
                        String dateGeorge = df.format("yyyy-MM-dd", calendar).toString();
                        persianCalendar.setGregorianChange(calendar.getTime());
                        String monthString = ((monthOfYear + 1) > 9) ? String.valueOf(monthOfYear + 1) : "0" + (monthOfYear + 1);
                        String dayString = ((dayOfMonth) > 9) ? String.valueOf(dayOfMonth) : "0" + (dayOfMonth);
                        String persianDateShortYear = String.valueOf(year).substring(2) + "-" + monthString + "-" + dayString;
                        String persianDateShort = year + "-" + monthString + "-" + dayString;
                        String weekName = ToolsPersianCalendar.getDayOfWeek(calendar.getTime());
                        String monthName = ToolsPersianCalendar.getPersianMonthString(calendar.getTime());
                        String datePersian = weekName + "," + dayOfMonth + monthName;
                        if (hasReturn) {
                            domesticHotelSearchRequest.setCheckOut(dateGeorge);
                            domesticHotelSearchRequest.setCheckOutPersianShort(datePersian);
                            domesticHotelSearchRequest.setCheckOutPersian(persianDateShort);
                            domesticHotelSearchRequest.setCheckInPersianShortYear(persianDateShortYear);
                            edtCheckOutDateDomestic.setText(datePersian);
                        } else {
                            domesticHotelSearchRequest.setCheckIn(dateGeorge);
                            domesticHotelSearchRequest.setCheckInPersian(persianDateShort);
                            domesticHotelSearchRequest.setCheckInPersianShortYear(persianDateShortYear);
                            domesticHotelSearchRequest.setCheckInPersianShort(datePersian);
                            edtCheckInDateDomestic.setText(datePersian);
                        }
                    }
                },
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );
        datePickerDialog.setMinDate(new PersianCalendar());
        datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    //-----------------------------------------------
    private void getDateGreg(final Boolean hasReturn, @Nullable String minDateString) {
        Calendar calendarNow = Calendar.getInstance();
        MonthAdapter.CalendarDay minDate = null;
        if (minDateString != null && minDateString.length() > 0) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(minDateString);
                calendarNow.setTime(date);
                minDate = new MonthAdapter.CalendarDay(calendarNow);
            } catch (Exception e) {
                calendarNow = Calendar.getInstance();
                minDate = new MonthAdapter.CalendarDay(calendarNow);
            }
        } else {
            if (hasReturn && internationalHotelSearchRequest.getCheckout() != null && internationalHotelSearchRequest.getCheckout().length() > 0)

                calendarNow.setTime(TimeDate.getDate(internationalHotelSearchRequest.getCheckout()).getTime());
            else if (internationalHotelSearchRequest.getCheckin() != null && internationalHotelSearchRequest.getCheckin().length() > 0) {
                minDate = new MonthAdapter.CalendarDay(calendarNow);
                calendarNow.setTime(TimeDate.getDate(internationalHotelSearchRequest.getCheckin()).getTime());
            }
        }
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                    @Override
                    public void onDateSet(CalendarDatePickerDialogFragment calendarDatePickerDialogFragment, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        DateFormat df = new DateFormat();
                        String dateGeorge = df.format("yyyy-MM-dd", calendar.getTime()).toString();
                        if (hasReturn) {
                            String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
                            String monthLongName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            int yearFinal = calendar.get(Calendar.YEAR);
                            String dateReturn = dayLongName + " , " + day + monthLongName + " , " + yearFinal;
                            edtCheckOutDate.setText(dateReturn);
                            internationalHotelSearchRequest.setCheckout(dateGeorge);
                            internationalHotelSearchRequest.setCheckoutLongDateString(dateReturn);
                        } else {
                            String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
                            String monthLongName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            int yearFinal = calendar.get(Calendar.YEAR);
                            String dateWent = dayLongName + " , " + day + monthLongName + " , " + yearFinal;
                            edtCheckInDate.setText(dateWent);
                            internationalHotelSearchRequest.setCheckin(dateGeorge);
                            internationalHotelSearchRequest.setCheckinLongDateString(dateWent);
                        }
                    }
                })
                .setFirstDayOfWeek(Calendar.SATURDAY)
                .setPreselectedDate(calendarNow.get(Calendar.YEAR), calendarNow.get(Calendar.MONTH), calendarNow.get(Calendar.DAY_OF_MONTH) + 1)
                .setDateRange(minDate, null)
                .setDoneText(getString(com.hami.servicehotel.R.string.acceptEng))
                .setThemeLight()
                .setCancelText(getString(com.hami.servicehotel.R.string.neverMindEng));

        cdp.show(getActivity().getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
    }
}
