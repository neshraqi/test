package hami.mainapp.Activity.ServiceSearch.ServiceTrain.Services.Controller.Presenter;


import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import hami.mainapp.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentPresenter;
import hami.mainapp.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentResponse;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.NetworkListener;
import hami.mainapp.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.RegisterTrainRequest;
import hami.mainapp.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.RegisterTrainResponse;
import hami.mainapp.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainDataResponse;
import hami.mainapp.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainRequest;
import hami.mainapp.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainResponse;
import hami.mainapp.BaseController.ResultSearchPresenter;
import hami.mainapp.BaseNetwork.BaseConfig;
import hami.mainapp.BaseNetwork.WebServiceNetwork;
import hami.mainapp.Const.KeyConst;
import hami.mainapp.R;
import hami.mainapp.Util.Hashing;


/**
 * Created by renjer on 1/10/2017.
 */

public class TrainApi {

    private Context context;
    public final static String TERM_PARAMETER = "term";
    public final static String SESSION_ID = "sessionId";
    public final static String SEARCH_ID = "searchId";
    private Thread thread;
    //-----------------------------------------------

    public TrainApi(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void searchTrain(final TrainRequest trainRequest, final ResultSearchPresenter<TrainDataResponse> trainDataResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "train/getTrainAjax";
        final HashMap<String, String> getHashMap = trainRequest.toHashMap();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        trainDataResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        trainDataResponseResultSearchPresenter.onErrorInternetConnection();
                        trainDataResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        trainDataResponseResultSearchPresenter.onErrorServer(0);
                        trainDataResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            TrainDataResponse trainDataResponse = gson.fromJson(result, TrainDataResponse.class);
                            if (trainDataResponse != null && trainDataResponse.getTrainResponseList() != null && trainDataResponse.getTrainResponseList().size() > 0) {
                                trainDataResponse.setTrainCompany(getTrainCompany(trainDataResponse.getTrainResponseList()));
                                trainDataResponseResultSearchPresenter.onSuccessResultSearch(trainDataResponse);
                            } else
                                trainDataResponseResultSearchPresenter.noResult(0);
                        } catch (Exception e) {
                            trainDataResponseResultSearchPresenter.onErrorServer(0);
                        } finally {
                            trainDataResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void registerPassenger(final RegisterTrainRequest registerTrainRequest, final ResultSearchPresenter<RegisterTrainResponse> registerTrainPresenter) {
        final String url = BaseConfig.BASE_URL_API + "train/registerTrain";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put("data", registerTrainRequest.toString());
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        registerTrainPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        registerTrainPresenter.onErrorInternetConnection();
                        registerTrainPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        registerTrainPresenter.onErrorServer(0);
                        registerTrainPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            RegisterTrainResponse registerTrainResponse = gson.fromJson(result, RegisterTrainResponse.class);
                            if (registerTrainResponse != null &&
                                    registerTrainResponse.getCode() == 1 &&
                                    registerTrainResponse.getTicketId() != null &&
                                    registerTrainResponse.getTicketId().length() > 0 && registerTrainResponse.getViewParamsTrain() != null) {
                                registerTrainPresenter.onSuccessResultSearch(registerTrainResponse);
                            } else if ((registerTrainResponse != null && registerTrainResponse.getCode() == 0) || registerTrainResponse.getTicketId() == null)
                                registerTrainPresenter.onError(context.getString(R.string.msgErrorServer));
                            else
                                registerTrainPresenter.onErrorServer(0);
                        } catch (Exception e) {
                            registerTrainPresenter.onErrorServer(0);
                        } finally {
                            registerTrainPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void hasBuyTicket(String ticketId, final PaymentPresenter paymentPresenter) {
        String hash = Hashing.getHash(ticketId);
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        final String url = BaseConfig.BASE_URL_API + "train/getPaymentStatus/" + ticketId + "/" + hash;
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        paymentPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        paymentPresenter.onErrorInternetConnection();
                        paymentPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        paymentPresenter.onErrorServer();
                        paymentPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            PaymentResponse paymentResponse = gson.fromJson(result, PaymentResponse.class);
                            if (paymentResponse != null && paymentResponse.getSuccess() && paymentResponse.getPaymentStatus() == 1 && paymentResponse.getStatus() == 3) {
                                paymentPresenter.onSuccessBuy();
                            } else
                                paymentPresenter.onReTryGetTicket();
                        } catch (Exception e) {
                            paymentPresenter.onErrorServer();
                        } finally {
                            paymentPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public HashMap<String, String> getTrainCompany(final ArrayList<TrainResponse> trainResponses) {
        final HashMap<String, String> airline = new HashMap<>();
        try {
            for (TrainResponse trainResponse : trainResponses) {
                airline.put(trainResponse.getOwner(), trainResponse.getOwnerFa());
            }
            return airline;

        } catch (Exception e) {
            return airline;
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
