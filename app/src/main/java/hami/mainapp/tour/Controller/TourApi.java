package hami.mainapp.tour.Controller;

import android.content.Context;

import com.google.gson.Gson;
import com.hami.common.BaseController.BaseResult;
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
import hami.mainapp.tour.Controller.Model.BookingTourDetails;
import hami.mainapp.tour.Controller.Model.BookingTourRequest;
import hami.mainapp.tour.Controller.Model.BookingTourResponse;
import hami.mainapp.tour.Controller.Model.InitialTourResponse;
import hami.mainapp.tour.Controller.Model.SearchTourRequest;
import hami.mainapp.tour.Controller.Model.TourDetailData;
import hami.mainapp.tour.Controller.Model.TourDetailResponse;
import hami.mainapp.tour.Controller.Model.TourItemsResponse;

import java.util.HashMap;


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
    public synchronized void cancelRequest() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    //-----------------------------------------------------
    public void translateWord(String word, final ResultSearchPresenter<BaseResult> resultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_MASTER + "flight/changeLanguage/";
        final HashMap<String, String> getDataParams = new HashMap<>();
        getDataParams.put("word", word);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getDataParams, new NetworkListener() {
                    @Override
                    public void onStart() {
                        resultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        resultSearchPresenter.onErrorInternetConnection();
                    }

                    @Override
                    public void onErrorServer() {
                        resultSearchPresenter.onErrorServer(0);
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            BaseResult baseResult = gson.fromJson(result, BaseResult.class);
                            if (baseResult != null && baseResult.getCode() == 1) {
                                resultSearchPresenter.onSuccessResultSearch(baseResult);
                            } else
                                resultSearchPresenter.onError(baseResult.getMsg());

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

    //-------------------------

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


}
