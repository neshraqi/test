package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter;


import android.content.Context;
import android.util.Log;


import com.google.gson.Gson;

import java.util.HashMap;

import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.AllFlightInternationalResponse;
import hami.aniticket.BaseController.ResultSearchPresenter;
import hami.aniticket.BaseNetwork.BaseConfig;
import hami.aniticket.BaseNetwork.WebServiceNetwork;
import hami.aniticket.Const.KeyConst;


/**
 * Created by renjer on 1/10/2017.
 */

public class InternationalApi2 {

    private Context context;
    public final static String TERM_PARAMETER = "term";
    public final static String SESSION_ID = "sessionId";
    public final static String SEARCH_ID = "searchId";
    private Thread threadParto;
    private Thread threadIati;
    private static final String TAG = "InternationalApi2";
    //-----------------------------------------------

    public InternationalApi2(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void searchFlightParto(final String flightJson, final ResultSearchPresenter<AllFlightInternationalResponse> resultSearchInternationalPresenter) {
        final String url = BaseConfig.BASE_URL_API + "international/getFlightAjax2";
        final HashMap<String, String> getDataParams = new HashMap<>();
        getDataParams.put("data", flightJson);
        getDataParams.put(KeyConst.APP_KEY, KeyConst.appKey);
        getDataParams.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        cancelRequestParto();
        threadParto = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getDataParams, new NetworkListener() {
                    @Override
                    public void onStart() {
                        resultSearchInternationalPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        resultSearchInternationalPresenter.onErrorInternetConnection();
                        resultSearchInternationalPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        resultSearchInternationalPresenter.onErrorServer(AllFlightInternationalResponse.PARTO_FLIGHT);
                        resultSearchInternationalPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            AllFlightInternationalResponse allFlightInternationalResponse = gson.fromJson(result, AllFlightInternationalResponse.class);
                            if (allFlightInternationalResponse.getErrorCode() == 100 &&
                                    allFlightInternationalResponse.getAllFlightInternationalParto() != null &&
                                    allFlightInternationalResponse.getAllFlightInternationalParto().size() > 0) {
                                resultSearchInternationalPresenter.onSuccessResultSearch(allFlightInternationalResponse);
                            } else
                                resultSearchInternationalPresenter.noResult(AllFlightInternationalResponse.PARTO_FLIGHT);
                        } catch (Exception e) {


                            resultSearchInternationalPresenter.onErrorServer(AllFlightInternationalResponse.PARTO_FLIGHT);
                        } finally {
                            resultSearchInternationalPresenter.onFinish();
                        }

                    }
                });
            }
        });
        threadParto.start();
    }

    //-----------------------------------------------
    public void searchFlightIati(final String flightJson, final ResultSearchPresenter<AllFlightInternationalResponse> resultSearchInternationalPresenter) {
        final String url = BaseConfig.BASE_URL_API + "international/getFlightAjax2";
        final HashMap<String, String> getDataParams = new HashMap<>();
        getDataParams.put("data", flightJson);
        getDataParams.put(KeyConst.APP_KEY, KeyConst.appKey);
        getDataParams.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        cancelRequestIati();
        threadIati = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getDataParams, new NetworkListener() {
                    @Override
                    public void onStart() {
                        resultSearchInternationalPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        resultSearchInternationalPresenter.onErrorInternetConnection();
                        resultSearchInternationalPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        resultSearchInternationalPresenter.onErrorServer(AllFlightInternationalResponse.IATI_FLIGHT);
                        resultSearchInternationalPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            AllFlightInternationalResponse allFlightInternationalResponse = gson.fromJson(result, AllFlightInternationalResponse.class);
                            if (allFlightInternationalResponse.getErrorCode() == 100 &&
                                    allFlightInternationalResponse.getAllFlightInternationalIati() != null &&
                                    allFlightInternationalResponse.getAllFlightInternationalIati().size() > 0) {
                                resultSearchInternationalPresenter.onSuccessResultSearch(allFlightInternationalResponse);
                            } else
                                resultSearchInternationalPresenter.noResult(AllFlightInternationalResponse.IATI_FLIGHT);
                        } catch (Exception e) {


                            resultSearchInternationalPresenter.onErrorServer(AllFlightInternationalResponse.IATI_FLIGHT);
                        } finally {
                            resultSearchInternationalPresenter.onFinish();
                        }

                    }
                });
            }
        });
        threadIati.start();
    }

    //-----------------------------------------------
    public synchronized void cancelRequestParto() {
        if (threadParto != null) {
            threadParto.interrupt();
            threadParto = null;
        }
    }

    //-----------------------------------------------
    public synchronized void cancelRequestIati() {
        if (threadIati != null) {
            threadIati.interrupt();
            threadIati = null;
        }
    }
}
