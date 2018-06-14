package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Presenter;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import hami.aniticket.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentPresenter;
import hami.aniticket.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentResponse;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticFlight;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticFlightResponse;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticLocationResponse;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticRequest;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.RegisterFlightDomesticRequest;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.RegisterFlightResponse;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.NetworkListener;
import hami.aniticket.BaseController.CallBackRequestSearch;
import hami.aniticket.BaseController.ResultSearchPresenter;
import hami.aniticket.BaseNetwork.BaseConfig;
import hami.aniticket.BaseNetwork.WebServiceNetwork;
import hami.aniticket.Const.KeyConst;
import hami.aniticket.Util.Database.FlightDomesticOffline;
import hami.aniticket.Util.Hashing;


/**
 * Created by renjer on 1/10/2017.
 */

public class DomesticApi {

    private Context context;
    public final static String TERM_PARAMETER = "term";
    public final static String SESSION_ID = "sessionId";
    public final static String SEARCH_ID = "searchId";
    private Thread thread;
    private static final String TAG = "DomesticApi";
    //-----------------------------------------------

    public DomesticApi(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void getListLocation(final ResultSearchPresenter<DomesticLocationResponse> domesticLocationResponseResultSearchPresenter) {

    }

    //-----------------------------------------------
    public void searchFlight(final DomesticRequest domesticRequest, final ResultSearchDomesticPresenter resultSearchDomesticPresenter) {
        final String url = BaseConfig.BASE_URL_API + "flight/getFlightAjax";
        final HashMap<String, String> getHashMap = domesticRequest.toHashMap();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        resultSearchDomesticPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        resultSearchDomesticPresenter.onErrorInternetConnection();
                        resultSearchDomesticPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        resultSearchDomesticPresenter.onErrorServer();
                        resultSearchDomesticPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            DomesticFlightResponse domesticFlightResponse = gson.fromJson(result, DomesticFlightResponse.class);
                            if (domesticFlightResponse != null && domesticFlightResponse.getDomesticFlights() != null && domesticFlightResponse.getDomesticFlights().size() > 0) {
                                domesticFlightResponse.setAirlineList(getAirlineList(domesticFlightResponse.getDomesticFlights()));
                                resultSearchDomesticPresenter.onSuccessResultSearch(domesticFlightResponse);
                            } else
                                resultSearchDomesticPresenter.noFlight();
                        } catch (Exception e) {


                            resultSearchDomesticPresenter.onErrorServer();
                        } finally {
                            resultSearchDomesticPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void registerPassenger(final RegisterFlightDomesticRequest registerFlightDomesticRequest, final ResultSearchPresenter<RegisterFlightResponse> registerFlightDomesticPresenter) {
        final String url = BaseConfig.BASE_URL_API + "flight/registerFlight";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put("data", registerFlightDomesticRequest.toString());
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, 60000, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        registerFlightDomesticPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        registerFlightDomesticPresenter.onErrorInternetConnection();
                        registerFlightDomesticPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        registerFlightDomesticPresenter.onErrorServer(0);
                        registerFlightDomesticPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            RegisterFlightResponse registerFlightResponse = gson.fromJson(result, RegisterFlightResponse.class);
                            if (registerFlightResponse != null && registerFlightResponse.getCode().contentEquals("1230")) {
                                registerFlightDomesticPresenter.onErrorServer(1230);
                            } else if (registerFlightResponse != null && registerFlightResponse.getCode().contentEquals("1")) {
                                registerFlightResponse.setAirlineCode(registerFlightDomesticRequest.getAirlineCode());
                                registerFlightResponse.setTakeOffDatePersian(registerFlightDomesticRequest.getTakeOffDatePersian());
                                registerFlightDomesticPresenter.onSuccessResultSearch(registerFlightResponse);
                            } else if (registerFlightResponse != null && registerFlightResponse.getCode().contentEquals("0"))
                                registerFlightDomesticPresenter.onError(registerFlightResponse.getMsg());
                            else
                                registerFlightDomesticPresenter.onErrorServer(0);
                        } catch (Exception e) {


                            registerFlightDomesticPresenter.onErrorServer(0);
                        } finally {
                            registerFlightDomesticPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------

    public void hasBuyTicket(String ticketId, final PaymentPresenter paymentBuyPresenter) {
        String hash = Hashing.getHash(ticketId);
        final String url = BaseConfig.BASE_URL_API + "flight/getPaymentStatus/" + ticketId + "/" + hash;
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
    public HashMap<String, String> getAirlineList(final ArrayList<DomesticFlight> domesticFlights) {
        final HashMap<String, String> airline = new HashMap<>();
        try {
            for (DomesticFlight flight : domesticFlights) {
                if (!airline.containsKey(flight.getAirlineCode()))
                    airline.put(flight.getAireLineName(), flight.getAireLineNameF());
            }
            return airline;

        } catch (Exception e) {

            return airline;
        }
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
