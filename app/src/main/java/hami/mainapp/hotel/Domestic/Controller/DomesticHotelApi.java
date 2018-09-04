package hami.mainapp.hotel.Domestic.Controller;


import android.content.Context;

import com.google.gson.Gson;
import com.hami.common.BaseController.BaseAppKeyAndSecret;
import com.hami.common.BaseController.PaymentPresenter;
import com.hami.common.BaseController.PaymentResponse;
import com.hami.common.BaseController.ResultSearchPresenter;
import com.hami.common.BaseNetwork.BaseConfig;
import com.hami.common.BaseNetwork.NetworkListener;
import com.hami.common.BaseNetwork.WebServiceNetwork;
import com.hami.common.Const.KeyConst;
import com.hami.common.Util.Keyboard;
import hami.mainapp.flight.Services.International.Controller.Model.DataPassengerInfoResponse;
import hami.mainapp.flight.Services.International.Controller.Presenter.NationalCodePresenter;
import hami.mainapp.hotel.Domestic.Controller.Model.DomesticHotel;
import hami.mainapp.hotel.Domestic.Controller.Model.DomesticHotelDetailsResponse;
import hami.mainapp.hotel.Domestic.Controller.Model.DomesticHotelPreBookingRequest;
import hami.mainapp.hotel.Domestic.Controller.Model.DomesticHotelPreBookingResponse;
import hami.mainapp.hotel.Domestic.Controller.Model.DomesticHotelRegisterPassengerRequest;
import hami.mainapp.hotel.Domestic.Controller.Model.DomesticHotelRegisterPassengerResponse;
import hami.mainapp.hotel.Domestic.Controller.Model.DomesticHotelResponse;
import hami.mainapp.hotel.Domestic.Controller.Model.DomesticHotelSearchRequest;
import hami.mainapp.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by renjer on 1/10/2017.
 */

public class DomesticHotelApi {

    private Context context;
    private Thread thread;
    private static final String TAG = "InternationalHotelApi";
    private final static String CONTROLLER_HOTEL = "hotel/";
    //-----------------------------------------------

    public DomesticHotelApi(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void searchHotels(final String cityName, final ResultSearchPresenter<DomesticHotelResponse> resultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_MASTER + CONTROLLER_HOTEL + "hotelLoad/" + cityName;
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> getDataParams = new HashMap<>();
                new WebServiceNetwork(context).requestWebServiceByPost(url, getDataParams, new NetworkListener() {
                    @Override
                    public void onStart() {
                        resultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        resultSearchPresenter.onErrorInternetConnection();
                        resultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        resultSearchPresenter.onErrorServer(0);
                        resultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            DomesticHotelResponse domesticHotelResponse = gson.fromJson(result, DomesticHotelResponse.class);

                            if (domesticHotelResponse != null && domesticHotelResponse.getDomesticHotels() != null && domesticHotelResponse.getDomesticHotels().size() > 0) {
                                domesticHotelResponse.setFilterRate(getToolsFilterInfo(domesticHotelResponse.getDomesticHotels()));
                                resultSearchPresenter.onSuccessResultSearch(domesticHotelResponse);
                            } else {
                                resultSearchPresenter.noResult(0);
                            }

                        } catch (Exception e) {


                            resultSearchPresenter.onErrorServer(0);
                        } finally {
                            resultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void getHotelDetails(DomesticHotelSearchRequest domesticHotelSearchRequest, final ResultSearchPresenter<DomesticHotelDetailsResponse> resultSearchPresenter) {
        String cityName = domesticHotelSearchRequest.getCityNameEng() + "/";
        String checkIn = domesticHotelSearchRequest.getCheckInPersian() + "/";
        String checkOut = domesticHotelSearchRequest.getCheckOutPersian() + "/";
        String hotelId = domesticHotelSearchRequest.getHotelId() + "/";
        //String hotelName = domesticHotelSearchRequest.getHotelName() + "/";
        String apiType = domesticHotelSearchRequest.getApiType() + "/";
        String url = "";
        try {
            //hotelName = URLEncoder.encode(hotelName, "utf-8").toString();
            url = BaseConfig.BASE_URL_MASTER + CONTROLLER_HOTEL + "detail/" + cityName + hotelId + checkIn + checkOut + apiType;

        } catch (Exception e) {
            e.printStackTrace();
        }
        cancelRequest();
        final String finalUrl = url;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String urlEncode = "";

                String appKeyAndAppSecret = new BaseAppKeyAndSecret().toString();
                new WebServiceNetwork(context).requestWebServiceByPost(finalUrl, appKeyAndAppSecret, new NetworkListener() {
                    @Override
                    public void onStart() {
                        resultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        resultSearchPresenter.onErrorInternetConnection();
                        resultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        resultSearchPresenter.onErrorServer(0);
                        resultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            DomesticHotelDetailsResponse hotelsResponse = gson.fromJson(result, DomesticHotelDetailsResponse.class);
                            if (hotelsResponse != null && hotelsResponse.getCode() == 1 && hotelsResponse.getDomesticHotelBookingProcess() != null) {
                                resultSearchPresenter.onSuccessResultSearch(hotelsResponse);
                            } else
                                resultSearchPresenter.noResult(0);
                        } catch (Exception e) {


                            resultSearchPresenter.onErrorServer(0);

                        } finally {
                            resultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void preBooking(final DomesticHotelPreBookingRequest domesticHotelPreBooking, final ResultSearchPresenter<DomesticHotelPreBookingResponse> resultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_MASTER + CONTROLLER_HOTEL + "bookingProccess";
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, domesticHotelPreBooking.toString(), new NetworkListener() {
                    @Override
                    public void onStart() {
                        resultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        resultSearchPresenter.onErrorInternetConnection();
                        resultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        resultSearchPresenter.onErrorServer(0);
                        resultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            DomesticHotelPreBookingResponse hotelsResponse = gson.fromJson(result, DomesticHotelPreBookingResponse.class);
                            if (hotelsResponse != null && hotelsResponse.getCode() == 1 && hotelsResponse.getHotelInfoRoom() != null) {
                                resultSearchPresenter.onSuccessResultSearch(hotelsResponse);
                            } else
                                resultSearchPresenter.noResult(0);
                        } catch (Exception e) {


                            resultSearchPresenter.onErrorServer(0);

                        } finally {
                            resultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }
    //-----------------------------------------------

    public void registerHotel(final DomesticHotelRegisterPassengerRequest domesticHotelRegisterPassengerRequest, final ResultSearchPresenter<DomesticHotelRegisterPassengerResponse> registerPassengerInternationalPresenter) {
        final String url = BaseConfig.BASE_URL_MASTER + CONTROLLER_HOTEL + "hotelRegister";
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, domesticHotelRegisterPassengerRequest.toString(), new NetworkListener() {
                    @Override
                    public void onStart() {
                        registerPassengerInternationalPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        registerPassengerInternationalPresenter.onErrorInternetConnection();
                        registerPassengerInternationalPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        registerPassengerInternationalPresenter.onErrorServer(0);
                        registerPassengerInternationalPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            DomesticHotelRegisterPassengerResponse registerPassengerResponse = gson.fromJson(result, DomesticHotelRegisterPassengerResponse.class);
                            if (registerPassengerResponse != null && registerPassengerResponse.getCode() == 1)
                                registerPassengerInternationalPresenter.onSuccessResultSearch(registerPassengerResponse);
                            else
                                registerPassengerInternationalPresenter.onError(context.getString(R.string.msgErrorRegister));
                        } catch (Exception e) {


                            registerPassengerInternationalPresenter.onErrorServer(0);
                        } finally {
                            registerPassengerInternationalPresenter.onFinish();
                        }
                    }
                });

            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void hasBuyTicket(String ticketId, String hashId, final PaymentPresenter paymentBuyPresenter) {
        //String hash = Hashing.getHash(ticketId);
        final String url = BaseConfig.BASE_URL_MASTER + CONTROLLER_HOTEL + "getPaymentStatus/" + ticketId + "/" + hashId;
        final HashMap<String, String> getDataParams = new HashMap<>();
        getDataParams.put(KeyConst.APP_KEY, KeyConst.appKey);
        getDataParams.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getDataParams, new NetworkListener() {
                    @Override
                    public void onStart() {
                        paymentBuyPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        paymentBuyPresenter.onErrorInternetConnection();
                        paymentBuyPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        paymentBuyPresenter.onErrorServer();
                        paymentBuyPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            PaymentResponse paymentResponse = gson.fromJson(result, PaymentResponse.class);
                            if (paymentResponse != null && paymentResponse.getSuccess() && paymentResponse.getPaymentStatus() == 1 && paymentResponse.getStatus() == 3) {
                                paymentBuyPresenter.onSuccessBuy();
                            } else if (paymentResponse != null && paymentResponse.getSuccess() && paymentResponse.getPaymentStatus() == 1 && (paymentResponse.getStatus() == 2 || paymentResponse.getStatus() == 1)) {
                                paymentBuyPresenter.onReTryGetTicket();
                            } else
                                paymentBuyPresenter.onReTryGetPayment();
                        } catch (Exception e) {


                            paymentBuyPresenter.onErrorServer();
                        } finally {
                            paymentBuyPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public ArrayList<String> getToolsFilterInfo(final ArrayList<DomesticHotel> hotelInternationalList) {
        ArrayList<String> offerList = new ArrayList<>();
        ArrayList<String> rateList = new ArrayList<>();
        try {
            for (DomesticHotel hotel : hotelInternationalList) {
                Integer rateValue = (hotel.getHotelStar() != null) ? Integer.valueOf(hotel.getHotelStar()) : 0;
                if (rateValue >= 0 && !rateList.contains(String.valueOf(rateValue))) {
                    rateList.add(String.valueOf(rateValue));
                }
            }
            return rateList;

        } catch (Exception e) {


            return rateList;
        }
    }

    //-----------------------------------------------
    public synchronized void cancelRequest() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    //-----------------------------------------------
    public void getInfoByNationalCode(String nationalCode, final NationalCodePresenter nationalCodePresenter) {
        nationalCode = Keyboard.convertPersianNumberToEngNumber(nationalCode);
        final String url = BaseConfig.BASE_URL_MASTER + "flights/getPassengerInfo/" + nationalCode;
        final HashMap<String, String> getDataParams = new HashMap<>();
        getDataParams.put(KeyConst.APP_KEY, KeyConst.appKey);
        getDataParams.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getDataParams, new NetworkListener() {
                    @Override
                    public void onStart() {
                        nationalCodePresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        nationalCodePresenter.onErrorInternetConnection();
                    }

                    @Override
                    public void onErrorServer() {
                        nationalCodePresenter.onErrorServer();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            DataPassengerInfoResponse baseResult = gson.fromJson(result, DataPassengerInfoResponse.class);
                            if (baseResult != null && baseResult.getCode() == 1) {
                                nationalCodePresenter.onSuccessDataPassengerInfo(baseResult.getDataPassengerInfo());
                            } else
                                nationalCodePresenter.onError(baseResult.getMsg());

                        } catch (Exception e) {


                            nationalCodePresenter.onErrorServer();
                        } finally {
                            nationalCodePresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }
//----------------------------------------------------------
}
