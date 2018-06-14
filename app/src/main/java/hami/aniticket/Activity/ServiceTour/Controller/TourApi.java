package hami.aniticket.Activity.ServiceTour.Controller;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import hami.aniticket.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentPresenter;
import hami.aniticket.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentResponse;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.RegisterFlightResponse;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.NetworkListener;
import hami.aniticket.Activity.ServiceTour.Controller.Model.BookingTourDetails;
import hami.aniticket.Activity.ServiceTour.Controller.Model.BookingTourRequest;
import hami.aniticket.Activity.ServiceTour.Controller.Model.BookingTourResponse;
import hami.aniticket.Activity.ServiceTour.Controller.Model.InitialTourResponse;
import hami.aniticket.Activity.ServiceTour.Controller.Model.SearchTourRequest;
import hami.aniticket.Activity.ServiceTour.Controller.Model.TourDetailData;
import hami.aniticket.Activity.ServiceTour.Controller.Model.TourDetailResponse;
import hami.aniticket.Activity.ServiceTour.Controller.Model.TourItemsResponse;
import hami.aniticket.BaseController.CallBackRequestSearch;
import hami.aniticket.BaseController.ResultSearchPresenter;
import hami.aniticket.BaseNetwork.BaseConfig;
import hami.aniticket.BaseNetwork.WebServiceNetwork;
import hami.aniticket.Const.KeyConst;
import hami.aniticket.Util.Database.FlightDomesticOffline;

/**
 * Created by renjer on 1/10/2017.
 */

public class TourApi {

    private Context context;
    public final static String TERM_PARAMETER = "term";
    public final static String SESSION_ID = "sessionId";
    public final static String SEARCH_ID = "searchId";
    private Thread thread;
    //-----------------------------------------------

    public TourApi(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void initialTour(final ResultSearchPresenter<InitialTourResponse> resultIntialTourPresenter) {
        final String url = BaseConfig.BASE_URL_API + "toor/initLoad";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        resultIntialTourPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        resultIntialTourPresenter.onErrorInternetConnection();
                        resultIntialTourPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        resultIntialTourPresenter.onErrorServer(0);
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            InitialTourResponse initialTourResponse = gson.fromJson(result, InitialTourResponse.class);
                            if (initialTourResponse != null && initialTourResponse.getCode() == 1) {
                                resultIntialTourPresenter.onSuccessResultSearch(initialTourResponse);
                            } else
                                resultIntialTourPresenter.noResult(0);
                        } catch (Exception e) {
                            resultIntialTourPresenter.onErrorServer(0);
                        } finally {
                            resultIntialTourPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void searchTour(SearchTourRequest searchTourRequest, final ResultSearchPresenter<TourItemsResponse> responseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "toor/search_tour";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        getHashMap.put("data", searchTourRequest.toString());
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        responseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        responseResultSearchPresenter.onErrorInternetConnection();
                        responseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        responseResultSearchPresenter.onErrorServer(0);
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            TourItemsResponse tourItemsResponse = gson.fromJson(result, TourItemsResponse.class);
                            if (tourItemsResponse != null && tourItemsResponse.getCode() == 1) {
                                responseResultSearchPresenter.onSuccessResultSearch(tourItemsResponse);
                            } else
                                responseResultSearchPresenter.noResult(0);
                        } catch (Exception e) {
                            responseResultSearchPresenter.onErrorServer(0);
                        } finally {
                            responseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void getDetailTour(String tourId, final ResultSearchPresenter<TourDetailData> responseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "toor/detail/" + tourId;
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        responseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        responseResultSearchPresenter.onErrorInternetConnection();
                        responseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        responseResultSearchPresenter.onErrorServer(0);
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            TourDetailResponse tourItemsResponse = gson.fromJson(result, TourDetailResponse.class);
                            if (tourItemsResponse != null && tourItemsResponse.getCode() == 1) {
                                responseResultSearchPresenter.onSuccessResultSearch(tourItemsResponse.getTourDetailData());
                            } else
                                responseResultSearchPresenter.noResult(0);
                        } catch (Exception e) {
                            responseResultSearchPresenter.onErrorServer(0);
                        } finally {
                            responseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void registerPassenger(final BookingTourRequest bookingTourRequest, final ResultSearchPresenter<BookingTourDetails> bookingTourDetailsResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "toor/reserve/";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put("data", bookingTourRequest.toString());
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, 20000, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        bookingTourDetailsResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        bookingTourDetailsResultSearchPresenter.onErrorInternetConnection();
                        bookingTourDetailsResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        bookingTourDetailsResultSearchPresenter.onErrorServer(0);
                        bookingTourDetailsResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            BookingTourResponse response = gson.fromJson(result, BookingTourResponse.class);
                            if (response != null && response.getCode() == 1) {
                                bookingTourDetailsResultSearchPresenter.onSuccessResultSearch(response.getDetails());
                            } else
                                bookingTourDetailsResultSearchPresenter.onError(response.getMsg());
                        } catch (Exception e) {
                            bookingTourDetailsResultSearchPresenter.onErrorServer(0);
                        } finally {
                            bookingTourDetailsResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------

    public void hasBuyTicket(String ticketId, String hashId, final PaymentPresenter paymentBuyPresenter) {
        final String url = BaseConfig.BASE_URL_API + "toor/getPaymentStatus/" + ticketId + "/" + hashId;
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
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
    public void getPastPurchases(final CallBackRequestSearch<ArrayList<RegisterFlightResponse>> registerFlightResponseCallBackRequestSearch) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<RegisterFlightResponse> responses = new FlightDomesticOffline(context).getFlightDomesticPastPurchases();
                    registerFlightResponseCallBackRequestSearch.getResponse(responses);
                } catch (Exception e) {
                    registerFlightResponseCallBackRequestSearch.getResponse(null);
                }

            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public synchronized void cancelRequest() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }


}
