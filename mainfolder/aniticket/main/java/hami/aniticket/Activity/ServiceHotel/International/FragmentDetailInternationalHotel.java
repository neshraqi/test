package hami.hamibelit.Activity.ServiceHotel.International;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hami.hamibelit.Activity.ServiceHotel.International.Adapter.SlidingImageAdapter;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.InternationalHotelApi;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.HotelDetailResponse;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.HotelInfo;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.HotelMoreInfoRoom;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.HotelMoreInfoRoomObject;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.InternationalHotel;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.InternationalHotelSearchRequest;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.RoomInfo;
import hami.hamibelit.BaseController.ResultSearchPresenter;
import hami.hamibelit.R;
import hami.hamibelit.Util.UtilFonts;
import hami.hamibelit.Util.UtilFragment;
import hami.hamibelit.Util.UtilMap;
import hami.hamibelit.Util.UtilVibrator;
import hami.hamibelit.View.HeaderBarLtr;
import hami.hamibelit.View.MessageBar;
import hami.hamibelit.View.ToastMessageBar;


public class FragmentDetailInternationalHotel extends Fragment {


    private View view;
    private InternationalHotelSearchRequest hotelSearchRequest;
    private String hotelSearchRequestJson;
    private MessageBar messageBar;
    private HeaderBarLtr headerBar;
    private InternationalHotelApi hotelApi;
    private static ViewPager mPager;
    private InternationalHotel hotel;
    private HotelDetailResponse hotelDetailResponse;
    private static final String TAG = "FragmentDetailInternationalHotel";

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            hotelSearchRequest = getArguments().getParcelable(InternationalHotelSearchRequest.class.getName());
            hotelSearchRequestJson = getArguments().getString(InternationalHotelSearchRequest.INTENT_HOTEL_SEARCH_REQUEST);
            hotel = getArguments().getParcelable(InternationalHotel.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            hotelSearchRequest = savedInstanceState.getParcelable(InternationalHotelSearchRequest.class.getName());
            hotelSearchRequestJson = savedInstanceState.getParcelable(InternationalHotelSearchRequest.INTENT_HOTEL_SEARCH_REQUEST);
            hotel = savedInstanceState.getParcelable(InternationalHotel.class.getName());
            hotelDetailResponse = savedInstanceState.getParcelable(HotelDetailResponse.class.getName());
        }
    }

    //-----------------------------------------------
    public static FragmentDetailInternationalHotel newInstance(String hotelSearchRequestJson, InternationalHotelSearchRequest hotelSearchRequest, InternationalHotel hotel) {

        Bundle args = new Bundle();
        FragmentDetailInternationalHotel fragment = new FragmentDetailInternationalHotel();
        args.putParcelable(InternationalHotelSearchRequest.class.getName(), hotelSearchRequest);
        args.putString(InternationalHotelSearchRequest.INTENT_HOTEL_SEARCH_REQUEST, hotelSearchRequestJson);
        args.putParcelable(InternationalHotel.class.getName(), hotel);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(InternationalHotelSearchRequest.class.getName(), hotelSearchRequest);
            outState.putString(InternationalHotelSearchRequest.INTENT_HOTEL_SEARCH_REQUEST, hotelSearchRequestJson);
            outState.putParcelable(InternationalHotel.class.getName(), hotel);
            outState.putParcelable(HotelDetailResponse.class.getName(), hotelDetailResponse);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_international_hotel_detail_layout, container, false);
            initialComponentFragment();
        }
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment() {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.TAHOMA);
        headerBar = (HeaderBarLtr) view.findViewById(R.id.headerBar);
        hotelApi = new InternationalHotelApi(getActivity());
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        getHotelDetails();
    }

    //-----------------------------------------------
    private View.OnClickListener callbackMessageBaClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getHotelDetails();
        }
    };

    //-----------------------------------------------
    public void getHotelDetails() {
        hotelApi.getHotelDetails(hotel.getHotelId(), hotel.getSearchID(), String.valueOf(hotel.getReserveId()), hotelSearchRequestJson, searchPresenterInternational);
    }

    //-----------------------------------------------
    private ResultSearchPresenter<HotelDetailResponse> searchPresenterInternational = new ResultSearchPresenter<HotelDetailResponse>() {
        @Override
        public void onStart() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        headerBar.showMessageBar(R.string.gettingDetailHotelEng);
                        messageBar.showProgress(getString(R.string.gettingDetailHotelEng));
                        headerBar.showProgress();
                    }
                });
            }
        }


        @Override
        public void onErrorServer(final int type) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorServerEng);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        headerBar.showMessageBar(R.string.errorEng);
                        messageBar.setTitleButton(R.string.tryAgainEng);
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
                        messageBar.showMessageBar(R.string.msgErrorInternetConnectionEng);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        headerBar.showMessageBar(R.string.errorEng);
                        messageBar.setTitleButton(R.string.tryAgainEng);
                    }
                });
            }
        }

        @Override
        public void onSuccessResultSearch(final HotelDetailResponse result) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.hideMessageBar();
                        hotelDetailResponse = result;
                        setupHotelDetails();
                    }
                });
            }
        }

        @Override
        public void noResult(final int type) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorNoHotelInternationalEng);
                        messageBar.setTitleButton(R.string.tryAgainSearchEng);
                        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
                        //setupFilterFab(false);

                    }
                });
            }
        }

        @Override
        public void onError(final String msg) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(msg);
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
                        new UtilVibrator(getActivity()).vibrateBySound();
                        messageBar.hideProgress();
                        headerBar.hideProgress();
                    }
                });
            }
        }


    };

    //-----------------------------------------------
    private void setupHotelDetails() {
        try {
            headerBar.showMessageBar(R.string.showCaseRoomEng);
            RatingBar rbRating = (RatingBar) view.findViewById(R.id.rbRating);
            TextView txtAddress = (TextView) view.findViewById(R.id.txtAddress);
            TextView txtHotelName = (TextView) view.findViewById(R.id.txtHotelName);
            AppCompatButton btnFullFeatures = (AppCompatButton) view.findViewById(R.id.btnFullFeatures);
            LinearLayout reviews = (LinearLayout) view.findViewById(R.id.reviews);
            final HotelInfo hotelInfo = hotelDetailResponse.getHotelDetailData().getHotels().getHotelInfo();
            setupImageGallery();
            Integer rating = Integer.valueOf(hotelInfo.getStar());
            if (rating == 0) {
                rbRating.setVisibility(View.GONE);
            } else {
                rbRating.setVisibility(View.VISIBLE);
                rbRating.setNumStars(rating);
                rbRating.setRating(rating);
            }
            txtAddress.setText(hotelInfo.getAddress());
            txtHotelName.setText(hotelInfo.getHotelName());
            if (hotelDetailResponse.getHotelDetailData().getHotels().getRoomsInfo() != null) {
                ArrayList<HotelMoreInfoRoom> rooms = hotelDetailResponse.getHotelDetailData().getHotels().getRoomsInfo();
                if (rooms == null) {

                } else {
                    for (int index = 0; index < rooms.size(); index++) {
                        View view = getActivity().getLayoutInflater().inflate(R.layout.row_international_hotel_room_layout, reviews, false);
                        LinearLayout layoutRooms = (LinearLayout) view.findViewById(R.id.layoutRooms);
                        TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);
                        TextView tvPrice2 = (TextView) view.findViewById(R.id.tvPrice2);
                        ArrayList<RoomInfo> roomsPerson = rooms.get(index).getRooms().getRoomInfoList();
                        HotelMoreInfoRoom hotelMoreInfoRoom = rooms.get(index);

                        AppCompatButton btnSelectHotelRoom = (AppCompatButton) view.findViewById(R.id.btnSelectHotelRoom);
                        btnSelectHotelRoom.setTag(index);
                        btnSelectHotelRoom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int indexSelected = (int) v.getTag();
                                UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentPassengerInternationalHotel.newInstance(hotelDetailResponse, indexSelected));
                            }
                        });
                        Integer price = rooms.get(index).getPrice();
                        Integer disCountPrice = rooms.get(index).getPrice();
                        //Long price = Long.valueOf(rooms.get(index).getPrice().replace(",", ""));
                        //Long disCountPrice = Long.valueOf(rooms.get(index).getPrice().replace(",", ""));
                        if (price != disCountPrice) {
                            tvPrice.setText(getFinalPrice(String.valueOf(disCountPrice), "IRR"));
                            tvPrice2.setText(getFinalPrice(String.valueOf(price), "IRR"));
                            tvPrice2.setPaintFlags(tvPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            tvPrice2.setVisibility(View.VISIBLE);
                        } else {
                            tvPrice.setText(getFinalPrice(String.valueOf(price), "IRR"));
                            tvPrice2.setVisibility(View.GONE);
                        }

                        txtAddress.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new UtilMap(getActivity()).showGoogleMap(hotelInfo.getLat(), hotelInfo.getLong(), hotelInfo.getHotelName());
                            }
                        });
                        for (int indexRoom = 0; rooms != null && indexRoom < roomsPerson.size(); indexRoom++) {
                            UtilFonts.overrideFonts(getActivity(), view, UtilFonts.TAHOMA);
                            LinearLayout linearLayout = new LinearLayout(getActivity());
                            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            linearLayout.setGravity(Gravity.LEFT);
                            linearLayout.setHorizontalGravity(Gravity.LEFT);
                            layoutRooms.addView(linearLayout);
                            layoutRooms.addView(getTextViewValue(roomsPerson.get(indexRoom).getRoomName(), R.color.colorPrimaryDark, 10));
                            if (indexRoom % 2 == 0) {
                                View viewLine = new View(getActivity());
                                LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                textParam.setMargins(10, 10, 10, 0);
                                viewLine.setLayoutParams(textParam);
                                viewLine.setBackgroundColor(Color.GRAY);
                                //viewLine.setBackgroundResource(R.drawable.gradient_line_blue);
                                layoutRooms.addView(viewLine);
                            }

                        }
                        reviews.addView(view);
                    }
                }
            } else if (hotelDetailResponse.getHotelDetailData().getHotels().getRoomsInfoRoomObjectsList() != null) {
                ArrayList<HotelMoreInfoRoomObject> rooms = hotelDetailResponse.getHotelDetailData().getHotels().getRoomsInfoRoomObjectsList();
                if (rooms == null) {

                } else {
                    for (int index = 0; index < rooms.size(); index++) {
                        View view = getActivity().getLayoutInflater().inflate(R.layout.row_international_hotel_room_layout, reviews, false);
                        LinearLayout layoutRooms = (LinearLayout) view.findViewById(R.id.layoutRooms);
                        TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);
                        TextView tvPrice2 = (TextView) view.findViewById(R.id.tvPrice2);
                        RoomInfo roomsPerson = rooms.get(index).getRooms().getRoomInfo();
                        AppCompatButton btnSelectHotelRoom = (AppCompatButton) view.findViewById(R.id.btnSelectHotelRoom);
                        btnSelectHotelRoom.setTag(index);
                        btnSelectHotelRoom.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int indexSelected = (int) v.getTag();
                                UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentPassengerInternationalHotel.newInstance(hotelDetailResponse, indexSelected));
                            }
                        });
                        //Long price = Long.valueOf(rooms.get(index).getPrice().replace(",", ""));
                        //Long disCountPrice = Long.valueOf(rooms.get(index).getPrice().replace(",", ""));
                        Integer price = rooms.get(index).getPrice();
                        Integer disCountPrice = rooms.get(index).getPrice();
                        if (price != disCountPrice) {
                            tvPrice.setText(getFinalPrice(String.valueOf(disCountPrice), "IRR"));
                            tvPrice2.setText(getFinalPrice(String.valueOf(price), "IRR"));
                            tvPrice2.setPaintFlags(tvPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            tvPrice2.setVisibility(View.VISIBLE);
                        } else {
                            tvPrice.setText(getFinalPrice(String.valueOf(price), "IRR"));
                            tvPrice2.setVisibility(View.GONE);
                        }

                        txtAddress.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new UtilMap(getActivity()).showGoogleMap(hotelInfo.getLat(), hotelInfo.getLong(), hotelInfo.getHotelName());
                            }
                        });
                        //for (int indexRoom = 0; rooms != null && indexRoom < roomsPerson.size(); indexRoom++) {
                        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.TAHOMA);
                        LinearLayout linearLayout = new LinearLayout(getActivity());
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout.setGravity(Gravity.LEFT);
                        linearLayout.setHorizontalGravity(Gravity.LEFT);
                        layoutRooms.addView(linearLayout);
                        layoutRooms.addView(getTextViewValue(roomsPerson.getRoomName(), R.color.colorPrimaryDark, 10));
//                            if (indexRoom % 2 == 0) {
                        View viewLine = new View(getActivity());
                        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        textParam.setMargins(10, 10, 10, 0);
                        viewLine.setLayoutParams(textParam);
                        viewLine.setBackgroundColor(Color.GRAY);
                        //viewLine.setBackgroundResource(R.drawable.gradient_line_blue);
                        layoutRooms.addView(viewLine);
//                            }

                        //}
                        reviews.addView(view);
                    }
                }
            }
            btnFullFeatures.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentFullDetailInternationalHotel.newInstance(hotelDetailResponse));
                }
            });

        } catch (Exception e) {
            e.getMessage();
            ToastMessageBar.show(getActivity(), R.string.msgErrorServerEng);
            getActivity().onBackPressed();

        }

    }

    //-----------------------------------------------
    private TextView getTextViewValue(String value, int color, int marginTop) {
        TextView textViewTitle = new TextView(getActivity());
        LinearLayout.LayoutParams textParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textParam.setMargins(10, marginTop, 10, 0);
        textViewTitle.setLayoutParams(textParam);
        textViewTitle.setTextColor(getResources().getColor(color));
        textViewTitle.setPadding(5, 5, 5, 5);
        textViewTitle.setText(value);
        return textViewTitle;
    }

    //-----------------------------------------------
    private void setupImageGallery() {
        final TextView txtImageCounter = (TextView) view.findViewById(R.id.txtImageCounter);
        ArrayList<String> imagesList = hotelDetailResponse.getHotelDetailData().getHotels().getHotelsMoreInfoData().getHotelsMoreInfoImages().getImageList();
        final int sizeImage = (imagesList == null) ? 0 : imagesList.size();
        String initialImage = sizeImage == 0 ? "0/0" : "1/" + sizeImage;
        txtImageCounter.setText(initialImage);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        String hotelId = hotel.getHotelId();
        mPager.setAdapter(new SlidingImageAdapter(getActivity(), hotelId, imagesList));
        final float density = getResources().getDisplayMetrics().density;
        //indicator.setRadius(5 * density);
        //NUM_PAGES = IMAGES.length;
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                txtImageCounter.setText((position + 1) + "/" + sizeImage);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == NUM_PAGES) {
//                    currentPage = 0;
//                }
//                mPager.setCurrentItem(currentPage++, true);
//            }
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 3000, 3000);
    }

    //-----------------------------------------------
    private String getFinalPrice(String price, String currencyString) {
        String finalPrice = "";
        try {

            finalPrice = price.replace(",", "");
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice)) + " " + currencyString;
            return finalPrice;
        } catch (Exception e) {

            return price + " IRR";
        }

    }

    //-----------------------------------------------
    View.OnClickListener callbackRetryMessageBarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getHotelDetails();
        }
    };
    //-----------------------------------------------
}
