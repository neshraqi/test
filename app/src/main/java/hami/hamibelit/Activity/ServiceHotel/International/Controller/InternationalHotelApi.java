package hami.hamibelit.Activity.ServiceHotel.International.Controller;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.HotelDetailResponse;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.HotelMoreInfoRoom;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.HotelMoreInfoRoomObject;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.InternationalHotel;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.InternationalHotelsResponse;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.RegisterHotelRequest;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.RegisterPassengerInternationalHotelResponse;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.SearchCity;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.SearchDestination;
import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.ToolsHotelFilter;
import hami.hamibelit.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentPresenter;
import hami.hamibelit.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.PaymentResponse;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.BaseResult;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.NetworkListener;
import hami.hamibelit.BaseController.ResultSearchPresenter;
import hami.hamibelit.BaseNetwork.BaseConfig;
import hami.hamibelit.BaseNetwork.WebServiceNetwork;
import hami.hamibelit.Const.KeyConst;


/**
 * Created by renjer on 1/10/2017.
 */

public class InternationalHotelApi {

    private Context context;
    private Thread thread;
    private static final String TAG = "InternationalHotelApi";
    private final static String CONTROLLER_HOTEL = "internationalhotel/";
    //-----------------------------------------------

    public InternationalHotelApi(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void searchDestination(final String term, final ResultSearchPresenter<ArrayList<SearchDestination>> resultSearchPresenter) {
        //final String url = BaseConfig.BASE_URL_MASTER + CONTROLLER_HOTEL + "searchdestination?term=" + term;
        final String url = BaseConfig.BASE_URL_MASTER + CONTROLLER_HOTEL + "searchdestination?term=" + term;
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> getDataParams = new HashMap<>();
                getDataParams.put("term", term);
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
                            SearchDestination[] searchDestinationsArray = gson.fromJson(result, SearchDestination[].class);
                            ArrayList<SearchDestination> searchDestinationsLists = new ArrayList<SearchDestination>();
                            Collections.addAll(searchDestinationsLists, searchDestinationsArray);
                            resultSearchPresenter.onSuccessResultSearch(searchDestinationsLists);
                        } catch (Exception e) {


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
    public void searchCity(final String destinationId, final ResultSearchPresenter<ArrayList<SearchCity>> resultSearchPresenter) {

        final String url = BaseConfig.BASE_URL_MASTER + CONTROLLER_HOTEL + "searchcity?destinationId=" + destinationId;
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
                            SearchCity[] searchCities = gson.fromJson(result, SearchCity[].class);
                            ArrayList<SearchCity> searchCityArrayList = new ArrayList<SearchCity>();
                            Collections.addAll(searchCityArrayList, searchCities);
                            resultSearchPresenter.onSuccessResultSearch(searchCityArrayList);
                        } catch (Exception e) {


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
    public void searchHotels(final String hotelSearchRequest, final ResultSearchPresenter<InternationalHotelsResponse> resultSearchPresenter) {

        final String url = BaseConfig.BASE_URL_MASTER + CONTROLLER_HOTEL + "getHotelAjax";
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> getDataParams = new HashMap<>();
                new WebServiceNetwork(context).requestWebServiceByPost(url, hotelSearchRequest, new NetworkListener() {
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
                            InternationalHotelsResponse hotelsResponse = gson.fromJson(result, InternationalHotelsResponse.class);
                            if (hotelsResponse.getErrorCode() == 100 && hotelsResponse.getHotels() == null || hotelsResponse.getHotels().size() > 0) {
                                hotelsResponse.setToolsHotelFilter(getToolsFilterInfo(hotelsResponse.getHotels()));
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
    public void getHotelDetails(final String hotelId, final String searchId, final String reserveId, final String hotelSearchRequest, final ResultSearchPresenter<HotelDetailResponse> resultSearchPresenter) {

        final String url = BaseConfig.BASE_URL_MASTER + CONTROLLER_HOTEL + "detail/" + hotelId + "/" + searchId + "/" + reserveId + "/json";
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> getDataParams = new HashMap<>();
                getDataParams.put("data", hotelSearchRequest);
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
                            HotelDetailResponse hotelsResponse = gson.fromJson(result, HotelDetailResponse.class);
                            if (hotelsResponse != null && hotelsResponse.getCode() == 1 && hotelsResponse.getHotelDetailData().getHotels() != null) {
                                JSONObject jsonObject = new JSONObject(result);
                                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONObject("hotels").getJSONArray("RoomsInfo");
                                if (jsonArray.length() > 0) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0).getJSONObject("Rooms");
                                    Gson gson2 = new Gson();
                                    if (jsonObject1.get("Room") instanceof JSONObject) {
                                        ArrayList<HotelMoreInfoRoomObject> list = gson2.fromJson(jsonArray.toString(), new TypeToken<ArrayList<HotelMoreInfoRoomObject>>() {
                                        }.getType());
                                        hotelsResponse.getHotelDetailData().getHotels().setRoomsInfoRoomObjectsList(list);
                                        //ArrayList<HotelMoreInfoRoom> test = gson2.fromJson(jsonArray.toString() , jsonArrayList<HotelMoreInfoRoom>)
                                        //hotelsResponse.getHotelDetailData().getHotels().setRoomsInfoRoomObjectsList();
                                        jsonObject1.toString();

                                    } else if (jsonObject1.get("Room")  instanceof JSONArray) {
                                        ArrayList<HotelMoreInfoRoom> list = gson2.fromJson(jsonArray.toString(), new TypeToken<ArrayList<HotelMoreInfoRoom>>() {
                                        }.getType());
                                        hotelsResponse.getHotelDetailData().getHotels().setRoomsInfo(list);
                                        jsonObject1.toString();
                                    }
                                }
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

    public void registerHotel(final RegisterHotelRequest registerHotelRequest, final ResultSearchPresenter<RegisterPassengerInternationalHotelResponse> registerPassengerInternationalPresenter) {
        final String url = BaseConfig.BASE_URL_MASTER + CONTROLLER_HOTEL + "registerHotel/json";
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, registerHotelRequest.toString(), new NetworkListener() {
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
                            RegisterPassengerInternationalHotelResponse registerPassengerResponse = gson.fromJson(result, RegisterPassengerInternationalHotelResponse.class);
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
    public void reserveHotel(String ticketId, String hashId, final ResultSearchPresenter<BaseResult> reserveInternationalPresenter) {
        final String url = BaseConfig.BASE_URL_MASTER + "internationalhotel/reserve/" + ticketId + "/" + hashId;
        final HashMap<String, String> getDataParams = new HashMap<>();
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
                        reserveInternationalPresenter.onErrorServer(0);
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            BaseResult baseResult = gson.fromJson(result, BaseResult.class);
                            if (baseResult != null && baseResult.getCode() == 1) {
                                reserveInternationalPresenter.onSuccessResultSearch(baseResult);
                            } else
                                reserveInternationalPresenter.onError(baseResult.getMsg());

                        } catch (Exception e) {


                            reserveInternationalPresenter.onErrorServer(0);
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
    public void hasBuyTicket(String ticketId, String hashId, final PaymentPresenter paymentBuyPresenter) {
        //String hash = Hashing.getHash(ticketId);
        final String url = BaseConfig.BASE_URL_MASTER + "internationalhotel/getPaymentStatus/" + ticketId + "/" + hashId;
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
    public ToolsHotelFilter getToolsFilterInfo(final ArrayList<InternationalHotel> hotelInternationalList) {
        ArrayList<String> offerList = new ArrayList<>();
        ArrayList<String> rateList = new ArrayList<>();
        try {
            for (InternationalHotel hotel : hotelInternationalList) {

                Integer rateValue = (hotel != null) ? Integer.valueOf(hotel.getHotelStar()) : 0;
//                if (hotel.getOffer() != null && hotel.getOffer().length() > 0 && !offerList.contains(hotel.getOffer())) {
//                    offerList.add(hotel.getOffer());
//                }
                if (rateValue >= 0 && !rateList.contains(String.valueOf(rateValue))) {
                    rateList.add(String.valueOf(rateValue));
                }
            }
            ToolsHotelFilter toolsHotelFilter = new ToolsHotelFilter();
            toolsHotelFilter.setFilterOffer(offerList);
            toolsHotelFilter.setFilterRate(rateList);
            return toolsHotelFilter;

        } catch (Exception e) {

            return new ToolsHotelFilter();
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
