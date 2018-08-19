package com.hami.servicebus.Services.Controller.Presenter;


import android.content.Context;

import com.google.gson.Gson;
import com.hami.common.BaseController.ResultSearchPresenter;
import com.hami.common.BaseNetwork.BaseConfig;
import com.hami.common.BaseNetwork.NetworkListener;
import com.hami.common.BaseNetwork.WebServiceNetwork;
import com.hami.common.Const.KeyConst;
import com.hami.common.Util.Hashing;
import com.hami.servicebus.R;
import com.hami.servicebus.Services.Controller.Model.BusTicketInformation;
import com.hami.servicebus.Services.Controller.Model.BusTicketInformationResponse;
import com.hami.servicebus.Services.Controller.Model.RegisterBusRequest;
import com.hami.servicebus.Services.Controller.Model.SearchBusDataResponse;
import com.hami.servicebus.Services.Controller.Model.SearchBusRequest;
import com.hami.servicebus.Services.Controller.Model.SeatData;
import com.hami.servicebus.Services.Controller.Model.SeatResponse;
import com.hami.servicebus.Services.Controller.Model.SeatRow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BusApi {

    private Context context;
    public final static String TERM_PARAMETER = "term";
    public final static String SESSION_ID = "sessionId";
    public final static String SEARCH_ID = "searchId";
    private Thread thread;
    public final static int BUS_TYPE_VIP = 1;
    public final static int BUS_TYPE_NORMAL = 2;
    private static final String TAG = "BusApi";

    //-----------------------------------------------
    public BusApi(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void searchBus(final SearchBusRequest searchBusRequest, final ResultSearchBusPresenter resultSearchBusPresenter) {
        final String url = BaseConfig.BASE_URL_API + "bus/getBusAjax";
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> getHashMap = searchBusRequest.toHashMap();
                getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
                getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        resultSearchBusPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        resultSearchBusPresenter.onErrorInternetConnection();
                        resultSearchBusPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        resultSearchBusPresenter.onErrorServer();
                        resultSearchBusPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            SearchBusDataResponse searchBusDataResponse = gson.fromJson(result, SearchBusDataResponse.class);
                            if (searchBusDataResponse != null && searchBusDataResponse.getListSearchBusResponse() != null && searchBusDataResponse.getListSearchBusResponse().size() > 0) {
                                resultSearchBusPresenter.onSuccessResultSearch(searchBusDataResponse);
                            } else
                                resultSearchBusPresenter.noBus();
                        } catch (Exception e) {


                            resultSearchBusPresenter.onErrorServer();
                        } finally {
                            resultSearchBusPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void getListSeat(final String busId, final String searchId, final SeatChairBusPresenter seatChairBusPresenter) {

        final String url = BaseConfig.BASE_URL_API + "bus/seatRequest";
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> postDataParams = new HashMap<>();
                postDataParams.put("busId", busId);
                postDataParams.put("searchId", searchId);
                postDataParams.put(KeyConst.APP_KEY, KeyConst.appKey);
                postDataParams.put(KeyConst.APP_SECRET, KeyConst.appSecret);
                new WebServiceNetwork(context).requestWebServiceByPost(url, postDataParams, new NetworkListener() {
                    @Override
                    public void onStart() {
                        seatChairBusPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        seatChairBusPresenter.onErrorInternetConnection();
                        seatChairBusPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        seatChairBusPresenter.onErrorServer();
                        seatChairBusPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            SeatResponse seatResponseData = stringToObject(result);
                            if (seatResponseData != null) {
                                seatChairBusPresenter.onSuccessResultSearch(seatResponseData);
                            } else
                                seatChairBusPresenter.onErrorServer();
                        } catch (Exception e) {


                            seatChairBusPresenter.onErrorServer();
                        } finally {
                            seatChairBusPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------

    public void registerPassenger(final RegisterBusRequest registerBusRequest, final ResultSearchPresenter<BusTicketInformation> registerBusPresenter) {
        final String url = BaseConfig.BASE_URL_API + "bus/registerBus";
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> getHashMap = registerBusRequest.toHashMap();
                getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
                getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
                new WebServiceNetwork(context).requestWebServiceByPost(url, 20000, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        registerBusPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        registerBusPresenter.onErrorInternetConnection();
                        registerBusPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        registerBusPresenter.onErrorServer(0);
                        registerBusPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            BusTicketInformationResponse busTicketInformationResponse = gson.fromJson(result, BusTicketInformationResponse.class);
                            if (busTicketInformationResponse != null && busTicketInformationResponse.getCode() == 1) {
                                registerBusPresenter.onSuccessResultSearch(busTicketInformationResponse.getBusTicketInformation());
                            } else
                                registerBusPresenter.onError(context.getString(R.string.msgErrorRegisterBus));
                        } catch (Exception e) {


                            registerBusPresenter.onErrorServer(0);
                        } finally {
                            registerBusPresenter.onFinish();
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
        final String url = BaseConfig.BASE_URL_API + "bus/getPaymentStatus/" + ticketId + "/" + hash;
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
    private SeatResponse stringToObject(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String col = jsonObject.optString("col");
            String row = jsonObject.optString("row");
            String floor = jsonObject.optString("floor");
            String capacity = jsonObject.optString("capacity");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            SeatData seatData = new SeatData();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray arrayObject = jsonArray.getJSONArray(i);
                List<SeatRow> resultSeatRow = new ArrayList<>();
                for (int j = 0; j < arrayObject.length(); j++) {
                    JSONObject object = (JSONObject) arrayObject.get(j);
                    SeatRow seatRow = new SeatRow(object.optString("chairNumber"), object.getInt("active"), object.getInt("status"));
                    resultSeatRow.add(seatRow);
                }
                if (i == 0) {
                    seatData.setRow1(resultSeatRow);
                } else if (i == 1) {
                    seatData.setRow2(resultSeatRow);
                } else if (i == 2) {
                    seatData.setRow3(resultSeatRow);
                } else if (jsonArray.length() - 1 == i && i == 3) {
                    seatData.setRow4(resultSeatRow);
                }
            }

            return new SeatResponse(capacity, col, floor, row, seatData);

        } catch (Exception e) {


            return null;
        }
    }

    //-----------------------------------------------
//    public void getTypeBus(final AirlineListener airlineListener) {
//        final HashMap<Integer, String> airline = new HashMap<>();
//        cancelRequest();
//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    airline.put(BUS_TYPE_VIP, context.getString(R.string.bus_vip));
//                    airline.put(BUS_TYPE_NORMAL, context.getString(R.string.bus_normal));
//                    airlineListener.getAirlineList(airline);
//                } catch (Exception e) {
//
//
//                    airlineListener.noAirline();
//                }
//
//            }
//        });
//        thread.start();
//    }

    //-----------------------------------------------
    public synchronized void cancelRequest() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    //-----------------------------------------------
//    public void getInfoByNationalCode(String nationalCode, final NationalCodePresenter nationalCodePresenter) {
//        nationalCode = Keyboard.convertPersianNumberToEngNumber(nationalCode);
//        final String url = BaseConfig.BASE_URL_MASTER + "flights/getPassengerInfo/" + nationalCode;
//        final HashMap<String, String> getDataParams = new HashMap<>();
//        getDataParams.put(KeyConst.APP_KEY, KeyConst.appKey);
//        getDataParams.put(KeyConst.APP_SECRET, KeyConst.appSecret);
//        cancelRequest();
//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new WebServiceNetwork(context).requestWebServiceByPost(url, getDataParams, new NetworkListener() {
//                    @Override
//                    public void onStart() {
//                        nationalCodePresenter.onStart();
//                    }
//
//                    @Override
//                    public void onErrorInternetConnection() {
//                        nationalCodePresenter.onErrorInternetConnection();
//                    }
//
//                    @Override
//                    public void onErrorServer() {
//                        nationalCodePresenter.onErrorServer();
//                    }
//
//                    @Override
//                    public void onFinish(String result) {
//                        try {
//                            Gson gson = new Gson();
//                            DataPassengerInfoResponse baseResult = gson.fromJson(result, DataPassengerInfoResponse.class);
//                            if (baseResult != null && baseResult.getCode() == 1) {
//                                nationalCodePresenter.onSuccessDataPassengerInfo(baseResult.getDataPassengerInfo());
//                            } else
//                                nationalCodePresenter.onError(baseResult.getMsg());
//
//                        } catch (Exception e) {
//
//
//                            nationalCodePresenter.onErrorServer();
//                        } finally {
//                            nationalCodePresenter.onFinish();
//                        }
//
//                    }
//                });
//            }
//        });
//        thread.start();
//    }

    //-----------------------------------------------
}
