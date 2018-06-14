package hami.nasimbehesht724.Activity.ServiceHotel.Domestic;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Adapter.DomesticHotelSlidingImageAdapter;
import hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.DomesticHotelApi;
import hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelBookingProcessData;
import hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelDetailsResponse;
import hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelPreBookingRequest;
import hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelPreBookingResponse;
import hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelRoom;
import hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelSearchRequest;
import hami.nasimbehesht724.BaseController.ResultSearchPresenter;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilActionCall;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilFragment;
import hami.nasimbehesht724.Util.UtilImageLoader;
import hami.nasimbehesht724.Util.UtilVibrator;
import hami.nasimbehesht724.View.HeaderBar;
import hami.nasimbehesht724.View.MessageBar;
import hami.nasimbehesht724.View.Progressbar.ButtonWithProgress;
import hami.nasimbehesht724.View.ToastMessageBar;


public class FragmentDetailDomesticHotel extends Fragment {


    private View view;
    private DomesticHotelSearchRequest hotelSearchRequest;
    private MessageBar messageBar;
    private DomesticHotelApi hotelApi;
    private static ViewPager mPager;
    private DomesticHotelDetailsResponse domesticHotelDetailsResponse;
    private static final String TAG = "FragmentDetailDomesticHotel";
    private HeaderBar headerBar;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            hotelSearchRequest = (DomesticHotelSearchRequest) getArguments().getSerializable(DomesticHotelSearchRequest.class.getName());
            domesticHotelDetailsResponse = getArguments().getParcelable(DomesticHotelDetailsResponse.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            hotelSearchRequest = (DomesticHotelSearchRequest) savedInstanceState.getSerializable(DomesticHotelSearchRequest.class.getName());
            domesticHotelDetailsResponse = savedInstanceState.getParcelable(DomesticHotelDetailsResponse.class.getName());
        }
    }

    //-----------------------------------------------
    public static FragmentDetailDomesticHotel newInstance(DomesticHotelSearchRequest hotelSearchRequest) {

        Bundle args = new Bundle();
        FragmentDetailDomesticHotel fragment = new FragmentDetailDomesticHotel();
        args.putSerializable(DomesticHotelSearchRequest.class.getName(), hotelSearchRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(DomesticHotelSearchRequest.class.getName(), hotelSearchRequest);
            outState.putParcelable(DomesticHotelDetailsResponse.class.getName(), domesticHotelDetailsResponse);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_domestic_hotel_detail_layout, container, false);
            initialComponentFragment();
        }
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment() {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        headerBar = (HeaderBar) view.findViewById(R.id.headerBar);
        hotelApi = new DomesticHotelApi(getActivity());
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
        hotelApi.getHotelDetails(hotelSearchRequest, searchPresenterDomestic);
    }

    //-----------------------------------------------
    private ResultSearchPresenter<DomesticHotelDetailsResponse> searchPresenterDomestic = new ResultSearchPresenter<DomesticHotelDetailsResponse>() {
        @Override
        public void onStart() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        headerBar.showMessageBar(R.string.gettingInfo);
                        headerBar.showProgress();
                        messageBar.showProgress(getString(R.string.gettingInfo));
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
                        headerBar.showMessageBar(R.string.error);
                        messageBar.showMessageBar(R.string.msgErrorServer);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        messageBar.setTitleButton(R.string.tryAgain);
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
                        headerBar.showMessageBar(R.string.error);
                        messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        messageBar.setTitleButton(R.string.tryAgain);
                    }
                });
            }
        }

        @Override
        public void onSuccessResultSearch(final DomesticHotelDetailsResponse result) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        headerBar.showMessageBar(R.string.validateSelectRoutingRoom);
                        messageBar.hideMessageBar();
                        domesticHotelDetailsResponse = result;
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
                        headerBar.showMessageBar(R.string.error);
                        messageBar.showMessageBar(R.string.msgErrorNoDomesticHotel);
                        messageBar.setTitleButton(R.string.tryAgainSearch);
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
                        headerBar.showMessageBar(R.string.error);
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
                        headerBar.hideProgress();
                        messageBar.hideProgress();
                        new UtilVibrator(getActivity()).vibrateBySound();
                    }
                });
            }
        }


    };

    //-----------------------------------------------
    private void setupHotelDetails() {
        try {
            RatingBar rbRating = (RatingBar) view.findViewById(R.id.rbRating);
            TextView txtAddress = (TextView) view.findViewById(R.id.txtAddress);
            TextView txtHotelName = (TextView) view.findViewById(R.id.txtHotelName);
            LinearLayout reviews = (LinearLayout) view.findViewById(R.id.reviews);
            final DomesticHotelBookingProcessData domesticHotelBookingProcess = domesticHotelDetailsResponse.getDomesticHotelBookingProcess().getBookingProcess();
            setupImageGallery();
            Integer rating = domesticHotelBookingProcess.getHotelStar();
            if (rating == 0) {
                rbRating.setVisibility(View.GONE);
            } else {
                rbRating.setVisibility(View.VISIBLE);
                rbRating.setNumStars(rating);
                rbRating.setRating(rating);
            }
            txtAddress.setText(domesticHotelBookingProcess.getHotelAddress());
            txtHotelName.setText(domesticHotelBookingProcess.getHotelNameFa());
            txtAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String source = domesticHotelBookingProcess.getHotelLatitude() + "," + domesticHotelBookingProcess.getHotelLongitude();
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=" + source));
                    startActivity(intent);
                }
            });
            for (int index = 0; index < domesticHotelBookingProcess.getRooms().size(); index++) {
                final DomesticHotelRoom domesticHotelRoom = domesticHotelBookingProcess.getRooms().get(index);

                View viewRoom = getActivity().getLayoutInflater().inflate(R.layout.row_domestic_hotel_room_layout, reviews, false);
                UtilFonts.overrideFonts(getActivity(), viewRoom, UtilFonts.IRAN_SANS_NORMAL);
                LinearLayout layoutRooms = (LinearLayout) viewRoom.findViewById(R.id.layoutRooms);
                AppCompatImageView imgRoom = (AppCompatImageView) viewRoom.findViewById(R.id.imgRoom);
                UtilImageLoader.loadImage(getActivity(), imgRoom, domesticHotelRoom.getImage(), R.drawable.no_image_hotel);
                AppCompatTextView tvTitleRoom = (AppCompatTextView) viewRoom.findViewById(R.id.tvTitleRoom);
                AppCompatTextView tvPrice = (AppCompatTextView) viewRoom.findViewById(R.id.tvPrice);
                AppCompatTextView tvPrice2 = (AppCompatTextView) viewRoom.findViewById(R.id.tvPrice2);
                final ButtonWithProgress btnSelectHotelRoom = (ButtonWithProgress) viewRoom.findViewById(R.id.btnSelectHotelRoom);
                AppCompatTextView titleExtraBed = (AppCompatTextView) viewRoom.findViewById(R.id.titleExtraBed);
                AppCompatTextView tvRoomCapacity = (AppCompatTextView) viewRoom.findViewById(R.id.tvRoomCapacity);
                RelativeLayout layoutExtraBed = (RelativeLayout) viewRoom.findViewById(R.id.layoutExtraBed);
                //btnSelectHotelRoom.setTag(hotel.getReserveId());
                tvRoomCapacity.setText(getString(R.string.capacityFlight) + " " + domesticHotelRoom.getPersons() + " " + getString(R.string.person));
                tvTitleRoom.setText(domesticHotelRoom.getNameFa());
                if (domesticHotelRoom.getShow()) {
                    btnSelectHotelRoom.setConfig(R.string.bookingOnline, R.string.preBooking, R.string.bookingOnline);
                    btnSelectHotelRoom.setBackgroundButtonColor(R.color.styleConfigMasterButtonBackground, R.color.styleConfigMasterButton2Background);
                    btnSelectHotelRoom.setVisibility(View.VISIBLE);
                    btnSelectHotelRoom.setCallBack(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DomesticHotelPreBookingRequest domesticHotelPreBooking = new DomesticHotelPreBookingRequest();
                            domesticHotelPreBooking.setCity(hotelSearchRequest.getCityNameEng());
                            domesticHotelPreBooking.setCityName(hotelSearchRequest.getCityNamePersian());
                            domesticHotelPreBooking.setHotel(hotelSearchRequest.getHotelId());
                            domesticHotelPreBooking.setId(hotelSearchRequest.getHotelId());
                            domesticHotelPreBooking.setNumberOfNights(String.valueOf(domesticHotelBookingProcess.getNumberOfNights()));
                            domesticHotelPreBooking.setRoomId(domesticHotelRoom.getId());
                            domesticHotelPreBooking.setFromDate(hotelSearchRequest.getCheckIn());
                            domesticHotelPreBooking.setInDate(hotelSearchRequest.getCheckInPersianShortYear());
                            domesticHotelPreBooking.setSearchId(domesticHotelDetailsResponse.getDomesticHotelBookingProcess().getSearchId());
                            preBookingHotel(domesticHotelPreBooking, btnSelectHotelRoom);

                            //UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentPassengerDomesticHotel.newInstance(hotelDetailResponse, reserveId));
                        }
                    });
                } else {
                    btnSelectHotelRoom.setVisibility(View.VISIBLE);
                    btnSelectHotelRoom.setConfig(R.string.bookingOffline, R.string.preBooking, R.string.bookingOffline);
                    btnSelectHotelRoom.setBackgroundButtonColor(R.color.styleConfigMasterButtonBackground, R.color.styleConfigMasterButton2Background);
                    btnSelectHotelRoom.setCallBack(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UtilActionCall.call(getActivity(), "05131402");
                        }
                    });
                }

                if (domesticHotelRoom.getExtraPersons() == 0) {
                    layoutExtraBed.setVisibility(View.GONE);
                } else {
                    String price = (domesticHotelRoom.getExtraPersonPrice() != null &&
                            domesticHotelRoom.getExtraPersonPrice().size() > 0) ? "(" + getFinalPrice(domesticHotelRoom.getExtraPersonPrice().get(0)) + ")" : "";
                    titleExtraBed.setText(getString(R.string.haveBedExtra) + price);
                    layoutExtraBed.setVisibility(View.VISIBLE);
                }

                long price = Long.valueOf(domesticHotelRoom.getPrice().replace(",", ""));
                long disCountPrice = Long.valueOf(domesticHotelRoom.getPriceDiscount().replace(",", ""));
                if (price != disCountPrice) {
                    tvPrice.setText(getFinalPrice(String.valueOf(disCountPrice)));
                    tvPrice2.setText(getFinalPrice(String.valueOf(price)));
                    tvPrice2.setPaintFlags(tvPrice2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    tvPrice2.setVisibility(View.VISIBLE);
                } else {
                    tvPrice.setText(getFinalPrice(String.valueOf(price)));
                    tvPrice2.setVisibility(View.GONE);
                }
                reviews.addView(viewRoom);
            }
            Button btnFullFeatures = (Button) view.findViewById(R.id.btnFullFeatures);
            btnFullFeatures.setOnClickListener(onClickListener);
        } catch (Exception e) {

        }

    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnFullFeatures)
                UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentFullDetailDomesticHotel.newInstance(domesticHotelDetailsResponse));
        }
    };

    //-----------------------------------------------
    private void setupImageGallery() {
        final TextView txtImageCounter = (TextView) view.findViewById(R.id.txtImageCounter);
        final ArrayList<String> imagesList = domesticHotelDetailsResponse.getDomesticHotelBookingProcess().getBookingProcess().getImages();
        txtImageCounter.setText("1/" + imagesList.size());
        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(new DomesticHotelSlidingImageAdapter(getActivity(), imagesList));
        final float density = getResources().getDisplayMetrics().density;
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                txtImageCounter.setText((position + 1) + "/" + imagesList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //-----------------------------------------------
    private String getFinalPrice(String price) {
        String finalPrice = "";
        try {

            finalPrice = price.replace(",", "");
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice) / 10);
            finalPrice += " تومان";

            return finalPrice;
        } catch (Exception e) {

            return price + " ریال";
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
    private void preBookingHotel(final DomesticHotelPreBookingRequest domesticHotelPreBooking, final ButtonWithProgress buttonWithProgress) {
        hotelApi.preBooking(domesticHotelPreBooking, new ResultSearchPresenter<DomesticHotelPreBookingResponse>() {
            @Override
            public void onStart() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.hideMessageBar();
                            buttonWithProgress.startProgress();
                            view.setEnabled(false);
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
                            ToastMessageBar.show(getActivity(), R.string.msgErrorServer);
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
                            ToastMessageBar.show(getActivity(), R.string.msgErrorInternetConnection);


                        }
                    });
                }
            }

            @Override
            public void onSuccessResultSearch(final DomesticHotelPreBookingResponse result) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentPassengerDomesticHotel.newInstance(result, domesticHotelPreBooking));
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
                            ToastMessageBar.show(getActivity(), R.string.msgErrorNoDomesticHotel);
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
                            buttonWithProgress.stopProgress();
                            view.setEnabled(true);
                        }
                    });
                }
            }

        });
    }
}
