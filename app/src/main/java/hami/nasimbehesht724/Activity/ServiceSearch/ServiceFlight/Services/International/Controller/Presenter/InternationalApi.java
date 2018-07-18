package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter;


import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentPresenter;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Presenter.AirlineListener;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.BaseResult;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.Country;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.DataPassengerInfoResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.Legs;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.OutBound;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.RegisterPassengerRequest;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.RegisterPassengerResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.SearchInternational;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.TicketInternational;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.RulesRequest;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.RulesResponseIati;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.RulesResponseParto;
import hami.nasimbehesht724.BaseController.CallBackRequestSearch;
import hami.nasimbehesht724.BaseController.ResultSearchPresenter;
import hami.nasimbehesht724.BaseNetwork.BaseConfig;
import hami.nasimbehesht724.BaseNetwork.WebServiceNetwork;
import hami.nasimbehesht724.Const.KeyConst;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.Database.FlightDomesticOffline;
import hami.nasimbehesht724.Util.Hashing;
import hami.nasimbehesht724.Util.Keyboard;

/**
 * Created by renjer on 1/10/2017.
 */

public class InternationalApi {

    private Context context;
    public final static String TERM_PARAMETER = "term";
    public final static String SESSION_ID = "sessionId";
    public final static String SEARCH_ID = "searchId";
    private Thread thread;
    private static final String TAG = "InternationalApi";
    //-----------------------------------------------

    public InternationalApi(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void searchPlace(final String term, final SearchInternationalPresenter searchPresenter) {

        final String url = BaseConfig.BASE_URL_API + "international/datasearch/";
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> getDataParams = new HashMap<>();
                getDataParams.put(TERM_PARAMETER, term);
                getDataParams.put(KeyConst.APP_KEY, KeyConst.appKey);
                getDataParams.put(KeyConst.APP_SECRET, KeyConst.appSecret);
                new WebServiceNetwork(context).requestWebServiceByPost(url, getDataParams, new NetworkListener() {
                    @Override
                    public void onStart() {
                        searchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        searchPresenter.onErrorInternetConnection();
                        searchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        searchPresenter.onErrorServer();
                        searchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            SearchInternational[] searchInternationalArray = gson.fromJson(result, SearchInternational[].class);
                            List<SearchInternational> searchInternationalList = Arrays.asList(searchInternationalArray);
                            searchPresenter.onSuccessSearch(groupByCity(searchInternationalList));
                        } catch (Exception e) {


                        } finally {
                            searchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public List<SearchInternational> groupByCity(List<SearchInternational> searchInternationalList) {
        List<SearchInternational> result = new ArrayList<>();
        HashMap<String, List<SearchInternational>> hashMap = new HashMap<>();
        try {
            for (SearchInternational searchInternational : searchInternationalList) {
                if (!hashMap.containsKey(searchInternational.getCountry())) {
                    List<SearchInternational> list = new ArrayList<>();
                    list.add(searchInternational);
                    hashMap.put(searchInternational.getCountry(), list);
                    result.add(SearchInternational.newInstanceHeader(searchInternational.getCountry(),searchInternational.getCountryNameP(),searchInternational.getCountryNameE()));
                    result.add(searchInternational);
                } else {
                    hashMap.get(searchInternational.getCountry()).add(searchInternational);
                    result.add(searchInternational);
                }
            }
        } catch (Exception e) {
            return searchInternationalList;
        }
        return result;
    }

    //-----------------------------------------------
    public List<SearchInternational> searchPlace2(final String term) {
        try {
            final String url = BaseConfig.BASE_URL_MASTER + "international/datasearch/";
            HashMap<String, String> getDataParams = new HashMap<>();
            getDataParams.put(TERM_PARAMETER, term);
            getDataParams.put(KeyConst.APP_KEY, KeyConst.appKey);
            getDataParams.put(KeyConst.APP_SECRET, KeyConst.appSecret);
            String result = new WebServiceNetwork(context).requestWebServiceByGet(url, getDataParams);
            Gson gson = new Gson();
            SearchInternational[] searchInternationalArray = gson.fromJson(result, SearchInternational[].class);
            List<SearchInternational> searchInternationalList = Arrays.asList(searchInternationalArray);
            return searchInternationalList;
        } catch (Exception e) {


            return null;
        }
    }

    //-----------------------------------------------
    public List<Country> searchCountry(final String term) {
        try {
            final String url = BaseConfig.BASE_URL_API + "international/countries/";
            HashMap<String, String> getDataParams = new HashMap<>();
            getDataParams.put(TERM_PARAMETER, term);
            getDataParams.put(KeyConst.APP_KEY, KeyConst.appKey);
            getDataParams.put(KeyConst.APP_SECRET, KeyConst.appSecret);

            Gson gson = new Gson();
            String result = new WebServiceNetwork(context).requestWebServiceByPost(url, getDataParams);
            Country[] searchInternationalArray = gson.fromJson(result, Country[].class);
            List<Country> searchInternationalList = Arrays.asList(searchInternationalArray);
            return searchInternationalList;
        } catch (Exception e) {


            return null;
        }
    }

    //-----------------------------------------------
    public void getAirRules(final RulesRequest rulesRequest, final Boolean hasShowRules, final RulesInternationalPresenter rulesInternationalPresenter) {
        final String url = BaseConfig.BASE_URL_API + "international/airRules";
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> getDataParams = new HashMap<>();
                getDataParams.put("data", rulesRequest.toString());
                getDataParams.put(KeyConst.APP_KEY, KeyConst.appKey);
                getDataParams.put(KeyConst.APP_SECRET, KeyConst.appSecret);
                new WebServiceNetwork(context).requestWebServiceByPost(url, getDataParams, new NetworkListener() {
                    @Override
                    public void onStart() {
                        rulesInternationalPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        rulesInternationalPresenter.onErrorInternetConnection();
                        rulesInternationalPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        rulesInternationalPresenter.onErrorServer();
                        rulesInternationalPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            Gson gson = new Gson();
                            if (jsonObject.getInt("flag") == 1) {
                                RulesResponseParto rulesResponse = gson.fromJson(result, RulesResponseParto.class);
                                if (rulesResponse != null && rulesResponse.getErrorCode() == 100 && hasShowRules) {
                                    rulesInternationalPresenter.onSuccessGetRulesParto(rulesResponse, rulesResponse.getBaggage());
                                } else if (rulesResponse != null && rulesResponse.getErrorCode() == 100 && !hasShowRules) {
                                    rulesInternationalPresenter.onSuccessGetCapacity(rulesResponse.getBaggage());
                                } else if (hasShowRules) {
                                    rulesInternationalPresenter.onError(context.getString(R.string.msgErrorNoRules));
                                } else {
                                    rulesInternationalPresenter.onError(context.getString(R.string.msgErrorNoCapacity));
                                }
                            } else if (jsonObject.getInt("flag") == 2) {
                                RulesResponseIati rulesResponse = gson.fromJson(result, RulesResponseIati.class);
                                if (rulesResponse != null && rulesResponse.getErrorCode() == 100 && hasShowRules) {
                                    rulesInternationalPresenter.onSuccessGetRulesIati(rulesResponse, rulesResponse.getBaggage());
                                } else if (rulesResponse != null && rulesResponse.getErrorCode() == 100 && !hasShowRules) {
                                    rulesInternationalPresenter.onSuccessGetCapacity(rulesResponse.getBaggage());
                                } else if (hasShowRules) {
                                    rulesInternationalPresenter.onError(context.getString(R.string.msgErrorNoRules));
                                } else {
                                    rulesInternationalPresenter.onError(context.getString(R.string.msgErrorNoCapacity));
                                }
                            }

                        } catch (Exception e) {


                            if (hasShowRules) {
                                rulesInternationalPresenter.onError(context.getString(R.string.msgErrorNoRules));
                            } else {
                                rulesInternationalPresenter.onError(context.getString(R.string.msgErrorNoCapacity));
                            }
                        }
                        rulesInternationalPresenter.onFinish();


                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------

    public void registerPassenger(final RegisterPassengerRequest registerPassengerRequest, final ResultSearchPresenter<RegisterPassengerResponse> registerPassengerInternationalPresenter) {
        final String url = BaseConfig.BASE_URL_API + "international/registerflight1";
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> getDataParams = new HashMap<>();
                getDataParams.put("data", registerPassengerRequest.toString());
                getDataParams.put(KeyConst.APP_KEY, KeyConst.appKey);
                getDataParams.put(KeyConst.APP_SECRET, KeyConst.appSecret);
                new WebServiceNetwork(context).requestWebServiceByPost(url, getDataParams, new NetworkListener() {
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
                            RegisterPassengerResponse registerPassengerResponse = gson.fromJson(result, RegisterPassengerResponse.class);
                            if (registerPassengerResponse != null && registerPassengerResponse.getCode() == 1)
                                registerPassengerInternationalPresenter.onSuccessResultSearch(registerPassengerResponse);
                            else
                                registerPassengerInternationalPresenter.onError(registerPassengerResponse.getMsg());
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
    public void reserveFlight(final String jsonRequest, final ReserveInternationalPresenter reserveInternationalPresenter) {
        final String url = BaseConfig.BASE_URL_API + "international/reserve";
        final HashMap<String, String> getDataParams = new HashMap<>();
        getDataParams.put("data", jsonRequest);
        getDataParams.put(KeyConst.APP_KEY, KeyConst.appKey);
        getDataParams.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getDataParams, new NetworkListener() {
                    @Override
                    public void onStart() {
                        reserveInternationalPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        reserveInternationalPresenter.onErrorInternetConnection();
                    }

                    @Override
                    public void onErrorServer() {
                        reserveInternationalPresenter.onErrorServer();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            BaseResult baseResult = gson.fromJson(result, BaseResult.class);
                            if (baseResult != null && baseResult.getCode() == 1) {
                                reserveInternationalPresenter.onSuccessReserve();
                            } else
                                reserveInternationalPresenter.onError(baseResult.getMsg());

                        } catch (Exception e) {


                            reserveInternationalPresenter.onErrorServer();
                        } finally {
                            reserveInternationalPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
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

    //-----------------------------------------------
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

    //-----------------------------------------------
    public void getAirlineList(final ArrayList<OutBound> outBounds, final AirlineListener airlineListener) {
        final HashMap<String, String> airline = new HashMap<>();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //ArrayList<DomesticFlight> domesticFlights = domesticFlightResponse.getDomesticFlights();
                    for (OutBound flight : outBounds) {
                        Legs legs = flight.getLegs().get(0);
                        if (!airline.containsKey(legs.getCarrierCode()))
                            airline.put(legs.getCarrierCode(), legs.getCarrierName());
                    }
                    airlineListener.getAirlineList(airline);
                } catch (Exception e) {


                    airlineListener.noAirline();
                }

            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void hasBuyTicket(String ticketId, final PaymentPresenter paymentBuyPresenter) {
        String hash = Hashing.getHash(ticketId);
        final String url = BaseConfig.BASE_URL_API + "international/getPaymentStatus/" + ticketId + "/" + hash;
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
    public void getPastPurchases(final CallBackRequestSearch<ArrayList<TicketInternational>> registerFlightResponseCallBackRequestSearch) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<TicketInternational> responses = new FlightDomesticOffline(context).getFlightInternationalPastPurchases();
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
