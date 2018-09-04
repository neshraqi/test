package hami.mainapp.hotel.International;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.hami.common.BaseController.DividerItemDecoration;
import com.hami.common.BaseController.ResultSearchPresenter;
import com.hami.common.BaseController.SelectItemList;
import com.hami.common.Util.BaseDatabase.DataSaver;
import com.hami.common.Util.Keyboard;
import com.hami.common.Util.UtilFonts;
import com.hami.common.Util.UtilFragment;
import com.hami.common.View.CheckBoxLtr;
import com.hami.common.View.Progressbar.ButtonWithProgress;
import com.hami.common.View.ToastMessageBar;
import com.hami.common.View.Validation.ValidationClass;

import hami.mainapp.R;
import hami.mainapp.hotel.International.Adapter.ListPassengerInternationalHotelListAdapter;
import hami.mainapp.hotel.International.Controller.InternationalHotelApi;
import hami.mainapp.hotel.International.Controller.Model.HotelDetailResponse;
import hami.mainapp.hotel.International.Controller.Model.PassengerInfoInternationalHotel;
import hami.mainapp.hotel.International.Controller.Model.RegisterHotelRequest;
import hami.mainapp.hotel.International.Controller.Model.RegisterPassengerInternationalHotelResponse;
import hami.mainapp.hotel.International.Controller.Model.RoomInfo;
import hami.mainapp.hotel.International.Controller.Model.RoomPassengerInfoInternationalHotel;
import hami.mainapp.hotel.International.Controller.Model.RowTypeHotelPassenger;


import java.util.ArrayList;

public class FragmentPassengerInternationalHotel extends Fragment {

    //-----------------------------------------------
    private HotelDetailResponse hotelDetailResponse;
    private InternationalHotelApi hotelApi;
    private View view;
    private ListPassengerInternationalHotelListAdapter listPassengerInternationalHotelListAdapter;
    private static final String TAG = "FragmentPassengerInternationalHotel";
    private AppCompatButton btnBooking;
    private int selectedRoomIndex;


    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            hotelDetailResponse = getArguments().getParcelable(HotelDetailResponse.class.getName());
            selectedRoomIndex = getArguments().getInt("selectedRoomIndex");
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            hotelDetailResponse = savedInstanceState.getParcelable(HotelDetailResponse.class.getName());
            selectedRoomIndex = savedInstanceState.getInt("selectedRoomIndex");
        }
    }

    //-----------------------------------------------
    public static FragmentPassengerInternationalHotel newInstance(HotelDetailResponse hotelDetailResponse, int selectedRoomIndex) {

        Bundle args = new Bundle();
        FragmentPassengerInternationalHotel fragment = new FragmentPassengerInternationalHotel();
        args.putParcelable(HotelDetailResponse.class.getName(), hotelDetailResponse);
        args.putInt("selectedRoomIndex", selectedRoomIndex);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(HotelDetailResponse.class.getName(), hotelDetailResponse);
            outState.putInt("selectedRoomIndex", selectedRoomIndex);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_passenger_international_hotel_layout, container, false);
            initialComponentFragment(view);
        }
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment(View view) {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.TAHOMA);
        hotelApi = new InternationalHotelApi(getActivity());
        btnBooking = (AppCompatButton) view.findViewById(R.id.btnBooking);
        btnBooking.setOnClickListener(onClickListener);
        setupPassengerRequest();
        setupRecyclerViewPassenger();
    }

    //-----------------------------------------------
    private void setupPassengerRequest() {
        TextView txtRoomCount = (TextView) view.findViewById(R.id.txtRoomCount);
        TextView txtPassengerCount = (TextView) view.findViewById(R.id.txtPassengerCount);
        TextView txtCheckIn = (TextView) view.findViewById(R.id.txtCheckIn);
        TextView txtCheckOut = (TextView) view.findViewById(R.id.txtCheckOut);
        String roomCount = hotelDetailResponse.getHotelDetailData().getHotelSearchRequest().getRooms();
        int adultCount = Integer.parseInt(hotelDetailResponse.getHotelDetailData().getHotelSearchRequest().getAdult());
        int childCount = Integer.parseInt(hotelDetailResponse.getHotelDetailData().getHotelSearchRequest().getChild());
        String passengerCount = String.valueOf(adultCount + childCount);
        txtRoomCount.setText(getString(R.string.roomEng) + ":" + roomCount);
        txtPassengerCount.setText(getString(R.string.passengerEng) + "(" + getString(R.string.adultEng) + ":" + adultCount + "," + getString(R.string.childEng) + ":" + childCount + ")");
        txtCheckIn.setText(getString(R.string.checkInDateEng) + ":" + hotelDetailResponse.getHotelDetailData().getHotelSearchRequest().getCheckin());
        txtCheckOut.setText(getString(R.string.checkOutDateEng) + ":" + hotelDetailResponse.getHotelDetailData().getHotelSearchRequest().getCheckout());
    }

    //-----------------------------------------------
    private void setupRecyclerViewPassenger() {
        RecyclerView rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        rvResult.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        listPassengerInternationalHotelListAdapter = new ListPassengerInternationalHotelListAdapter(getActivity(),
                getPassengerInfo(),
                selectItemPassengerInternational);
        rvResult.setAdapter(listPassengerInternationalHotelListAdapter);
    }

    //-----------------------------------------------
    SelectItemList<PassengerInfoInternationalHotel> selectItemPassengerInternational = new SelectItemList<PassengerInfoInternationalHotel>() {
        @Override
        public void onSelectItem(PassengerInfoInternationalHotel object, int position) {
            showDialogAddPassenger(object, position);
        }
    };

    //-----------------------------------------------
    private ArrayList<RowTypeHotelPassenger> getPassengerInfo() {
        ArrayList<RowTypeHotelPassenger> result = new ArrayList<>();
        if (hotelDetailResponse.getHotelDetailData().getHotels().getRoomsInfo() != null) {
            ArrayList<RoomInfo> infoList = hotelDetailResponse.getHotelDetailData().getHotels().getRoomsInfo().get(selectedRoomIndex).getRooms().getRoomInfoList();

            for (int index = 0; index < infoList.size(); index++) {
                RoomInfo room = infoList.get(index);
                result.add(new RoomPassengerInfoInternationalHotel(room.getRoomName()));
                for (int indexPassenger = 0; indexPassenger < room.getCountAdult(); indexPassenger++)
                    result.add(PassengerInfoInternationalHotel.newInstanceAdults("", "", index));
                for (int indexPassenger = 0; indexPassenger < room.getCountChild(); indexPassenger++)
                    result.add(PassengerInfoInternationalHotel.newInstanceChild("", "", index));
            }
            return result;
        } else if (hotelDetailResponse.getHotelDetailData().getHotels().getRoomsInfoRoomObjectsList() != null) {
            RoomInfo room = hotelDetailResponse.getHotelDetailData().getHotels().getRoomsInfoRoomObjectsList().get(selectedRoomIndex).getRooms().getRoomInfo();
            result.add(new RoomPassengerInfoInternationalHotel(room.getRoomName()));
            for (int indexPassenger = 0; indexPassenger < room.getCountAdult(); indexPassenger++)
                result.add(PassengerInfoInternationalHotel.newInstanceAdults("", "", 0));
            for (int indexPassenger = 0; indexPassenger < room.getCountChild(); indexPassenger++)
                result.add(PassengerInfoInternationalHotel.newInstanceChild("", "", 0));

            return result;
        }
        return null;
    }

    //-----------------------------------------------
    private void showDialogAddPassenger(final PassengerInfoInternationalHotel object, final int position) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_service_international_hotel_register_passenger_layout, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        UtilFonts.overrideFonts(getActivity(), dialogView, UtilFonts.TAHOMA);
        AppCompatButton btnAddPassenger = (AppCompatButton) dialogView.findViewById(R.id.btnRegister);
        AppCompatButton btnCancel = (AppCompatButton) dialogView.findViewById(R.id.btnCancel);
        //-----------------------------------------------
        final EditText edtFName = (EditText) dialogView.findViewById(R.id.edtFname);
        final EditText edtLName = (EditText) dialogView.findViewById(R.id.edtLName);
        final EditText edtTypeGender = (EditText) dialogView.findViewById(R.id.edtTypeGender);
        edtFName.setText(object.getFirstName());
        edtLName.setText(object.getLastName());
        //-----------------------------------------------
        edtTypeGender.setText(R.string.mr);
        edtTypeGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), edtTypeGender);
                //popupMenu.inflate(R.menu.menu_type_passenger_hotel_dometsic);
                popupMenu.getMenu().add(Menu.NONE, 1, 1, getString(R.string.mr));
                popupMenu.getMenu().add(Menu.NONE, 2, 2, getString(R.string.mrs));
                popupMenu.getMenu().add(Menu.NONE, 3, 3, getString(R.string.miss));
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                         @Override
                                                         public boolean onMenuItemClick(MenuItem item) {
                                                             edtTypeGender.setText(item.getTitle());
                                                             return false;
                                                         }
                                                     }

                );
                popupMenu.show();
            }
        });
        edtTypeGender.setFocusable(false);
        edtTypeGender.setCursorVisible(false);
        edtTypeGender.setText(object.getTitle());
        //-----------------------------------------------

        btnAddPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtFName.length() == 0) {
                    ToastMessageBar.show(getActivity(), R.string.validateFirstNameEng);
                    return;
                }
                if (edtLName.length() == 0) {
                    ToastMessageBar.show(getActivity(), R.string.validateLastNameEng);
                    return;
                } else {
                    Keyboard.closeKeyboard(getActivity());
                    object.setFirstName(edtFName.getText().toString());
                    object.setLastName(edtLName.getText().toString());
                    object.setTitle(edtTypeGender.getText().toString());
                    object.setHasError(false);
                    listPassengerInternationalHotelListAdapter.update(position, object);
                    alertDialog.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }

    //-----------------------------------------------
    public void showDialogBookingHotel() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_service_register_final_layout_ltr, null);

        UtilFonts.overrideFonts(getActivity(), dialogView, UtilFonts.TAHOMA);
        final EditText edtMobile = (EditText) dialogView.findViewById(R.id.edtMobile);
        final EditText edtEmail = (EditText) dialogView.findViewById(R.id.edtEmail);
        final CheckBoxLtr chkAcceptRule = (CheckBoxLtr) dialogView.findViewById(R.id.chkAcceptRule);
        chkAcceptRule.setTitleWithUnderLine(R.string.rulesInternetBuyEng);
//        chkAcceptRule.setCallBackTitle(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new CustomTabsPackages(getActivity()).showUrl(RespinaConst.RULE_LINK_HOTEL);
//            }
//        });
        DataSaver dataSaver = new DataSaver(getActivity());
        edtEmail.setText(dataSaver.getEmail());
        edtMobile.setText(dataSaver.getMobile());
        final ButtonWithProgress btnReserve = (ButtonWithProgress) dialogView.findViewById(R.id.btnRegister);
        btnReserve.setConfig(R.string.reserveEng, R.string.bookingInProcessEng, R.string.reserveEng);
        btnReserve.setBackgroundButton(R.drawable.bg_button_orange);

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();

        btnReserve.setCallBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Keyboard.closeKeyboard(getActivity());
                    RegisterHotelRequest registerHotelRequest = new RegisterHotelRequest();
                    String mobile = Keyboard.convertPersianNumberToEngNumber(edtMobile.getText().toString());
                    String email = Keyboard.convertPersianNumberToEngNumber(edtEmail.getText().toString());
                    ArrayList<String> roomIdList = new ArrayList<>();
                    if (hotelDetailResponse.getHotelDetailData().getHotels().getRoomsInfoRoomObjectsList() != null) {
                        RoomInfo roomInfoArrayList = hotelDetailResponse.getHotelDetailData().getHotels().getRoomsInfoRoomObjectsList().get(selectedRoomIndex).getRooms().getRoomInfo();
                        String value = roomInfoArrayList.getRoomId();
                        roomIdList.add(value);
                        registerHotelRequest.setSuggestId(hotelDetailResponse.getHotelDetailData().getHotels().getRoomsInfoRoomObjectsList().get(selectedRoomIndex).getSuggestId());

                    } else {
                        ArrayList<RoomInfo> roomInfoArrayList = hotelDetailResponse.getHotelDetailData().getHotels().getRoomsInfo().get(selectedRoomIndex).getRooms().getRoomInfoList();
                        for (int i = 0; i < roomInfoArrayList.size(); i++) {
                            String value = roomInfoArrayList.get(i).getRoomId();
                            roomIdList.add(value);
                        }
                        registerHotelRequest.setSuggestId(hotelDetailResponse.getHotelDetailData().getHotels().getRoomsInfo().get(selectedRoomIndex).getSuggestId());
                    }
                    registerHotelRequest.setRoomId(roomIdList);
                    registerHotelRequest.setPassengers(listPassengerInternationalHotelListAdapter.getListPassenger());
                    registerHotelRequest.setSearchId(hotelDetailResponse.getHotelDetailData().getWsearchId());
                    registerHotelRequest.setReserveId(hotelDetailResponse.getHotelDetailData().getReserveId());
                    registerHotelRequest.setSessionId(hotelDetailResponse.getHotelDetailData().getSearchId());
                    registerHotelRequest.setUser_cellphone(mobile);
                    registerHotelRequest.setEmail(email);


                    Boolean isCheckRoll = chkAcceptRule.hasCheck();
                    if (mobile == null || mobile.length() == 0 || mobile.length() < 10) {
                        ToastMessageBar.show(getActivity(), R.string.validateMobileEng);
                        Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                        edtMobile.startAnimation(vibrateAnimation);
                        edtMobile.requestFocus();
                        return;
                    }
                    if (!ValidationClass.validateEmail(email)) {
                        ToastMessageBar.show(getActivity(), R.string.validateEmailEng);
                        Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                        edtEmail.startAnimation(vibrateAnimation);
                        edtEmail.requestFocus();
                        return;
                    }
                    if (!isCheckRoll) {
                        ToastMessageBar.show(getActivity(), R.string.validateAcceptRuleEng);
                        Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                        chkAcceptRule.startAnimation(vibrateAnimation);
                        chkAcceptRule.requestFocus();
                        return;
                    }
                    DataSaver dataSaver = new DataSaver(getActivity());
                    dataSaver.setEmailMobile(mobile, email);
                    hotelApi.registerHotel(registerHotelRequest, new ResultSearchPresenter<RegisterPassengerInternationalHotelResponse>() {
                        @Override
                        public void onStart() {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.setCancelable(false);
                                        alertDialog.setCanceledOnTouchOutside(false);
                                        edtEmail.setEnabled(false);
                                        edtMobile.setEnabled(false);
                                        btnReserve.setEnableButton(false);
                                        chkAcceptRule.setEnabled(false);
                                        btnReserve.startProgress();

                                    }
                                });
                            }
                        }

                        @Override
                        public void onErrorServer(int type) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastMessageBar.show(getActivity(), R.string.msgErrorReserveHotelEng);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onErrorInternetConnection() {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastMessageBar.show(getActivity(), R.string.msgErrorInternetConnectionEng);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onSuccessResultSearch(final RegisterPassengerInternationalHotelResponse result) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.cancel();
                                        UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentBookingInternationalHotel.newInstance(hotelDetailResponse, result), R.id.frame_Layout);
                                    }
                                });

                            }
                        }

                        @Override
                        public void noResult(int type) {

                        }

                        @Override
                        public void onError(final String msg) {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastMessageBar.show(getActivity(), msg);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFinish() {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        alertDialog.setCancelable(true);
                                        alertDialog.setCanceledOnTouchOutside(true);
                                        edtEmail.setEnabled(true);
                                        edtMobile.setEnabled(true);
                                        btnReserve.setEnableButton(true);
                                        chkAcceptRule.setEnabled(true);
                                        btnReserve.stopProgress();
                                    }
                                });
                            }
                        }
                    });
                } catch (Exception e) {
                    ToastMessageBar.show(getActivity(), R.string.msgErrorReserveHotelEng);
                }
            }
        });

        alertDialog.show();
    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnBooking) {
                bookingHotel();
            }

        }
    };

    //-----------------------------------------------
    private void bookingHotel() {
        if (listPassengerInternationalHotelListAdapter.validateInfoPassenger()) {
            showDialogBookingHotel();
        }
    }
    //-----------------------------------------------

}
