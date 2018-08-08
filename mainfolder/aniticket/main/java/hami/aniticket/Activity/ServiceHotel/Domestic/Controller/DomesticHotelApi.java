package hami.mainapp.Activity.ServiceHotel.Domestic.Controller;


import android.content.Context;
import android.util.Log;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import hami.mainapp.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotel;
import hami.mainapp.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelDetailsResponse;
import hami.mainapp.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelPreBookingRequest;
import hami.mainapp.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelPreBookingResponse;
import hami.mainapp.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelRegisterPassengerRequest;
import hami.mainapp.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelRegisterPassengerResponse;
import hami.mainapp.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelResponse;
import hami.mainapp.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelSearchRequest;
import hami.mainapp.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentPresenter;
import hami.mainapp.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentResponse;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.NetworkListener;
import hami.mainapp.BaseController.BaseAppKeyAndSecret;
import hami.mainapp.BaseController.ResultSearchPresenter;
import hami.mainapp.BaseNetwork.BaseConfig;
import hami.mainapp.BaseNetwork.WebServiceNetwork;
import hami.mainapp.Const.KeyConst;
import hami.mainapp.R;


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
        String apiType = domesticHotelSearchRequest.getApiType() + "/";
        String url = "";
        try {
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
                            } else
                                paymentBuyPresenter.onReTryGetTicket();
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
}
