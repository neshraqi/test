package hami.hamibelit.Activity.Authentication.Controller;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import hami.hamibelit.Activity.PastPurchases.Model.PassengerFlightDomestic;
import hami.hamibelit.Activity.PastPurchases.Model.PassengerFlightDomesticResponse;
import hami.hamibelit.Activity.PastPurchases.Model.PurchasesBusResponse;
import hami.hamibelit.Activity.PastPurchases.Model.PurchasesFlightDomesticResponse;
import hami.hamibelit.Activity.PastPurchases.Model.PurchasesFlightInternationalResponse;
import hami.hamibelit.Activity.PastPurchases.Model.PurchasesTrainResponse;
import hami.hamibelit.Activity.PastPurchases.Model.ReFoundResponseFlightDomestic;
import hami.hamibelit.Activity.PastPurchases.Model.SplitResponse;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.BaseResult;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.NetworkListener;
import hami.hamibelit.BaseController.ResultSearchPresenter;
import hami.hamibelit.BaseNetwork.BaseConfig;
import hami.hamibelit.BaseNetwork.WebServiceNetwork;
import hami.hamibelit.Const.KeyConst;
import hami.hamibelit.Util.Database.DataSaver;
import hami.hamibelit.Util.Hashing;


/**
 * Created by renjer on 1/10/2017.
 */

public class UserApi {

    private Context context;
    public final static String TYPE_FLIGHT_DOMESTIC = "1";
    public final static String TYPE_FLIGHT_INTERNATIONAL = "2";
    public final static String TYPE_TRAIN = "3";
    public final static String TYPE_BUS = "4";
    private Thread thread;
    private static final String TAG = "UserApi";
    //-----------------------------------------------

    public UserApi(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void signIn(final String mobile, final String password, final ResultSearchPresenter<UserResponse> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/getInfo";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        getHashMap.put("user_cellphone", mobile);
        getHashMap.put("user_pass", password);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            UserResponse userResponse = gson.fromJson(result, UserResponse.class);
                            userResponse.getUser().setPassword(password);
                            if (userResponse != null && userResponse.getCode() == 1) {
                                new DataSaver(context).setLogin(userResponse.toString());
                                userResponseResultSearchPresenter.onSuccessResultSearch(userResponse);
                            } else
                                userResponseResultSearchPresenter.noResult(0);
                        } catch (Exception e) {

                            userResponseResultSearchPresenter.onErrorServer(0);
                        } finally {
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void requestSignInByMobile(final String mobile, final ResultSearchPresenter<Boolean> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/createPassword";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        getHashMap.put("user_cellphone", mobile);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            BaseResult userResponse = gson.fromJson(result, BaseResult.class);
                            if (userResponse != null && userResponse.getCode() == 1) {
                                new DataSaver(context).setMobileLogin(mobile);
                                userResponseResultSearchPresenter.onSuccessResultSearch(true);
                            } else
                                userResponseResultSearchPresenter.noResult(0);
                        } catch (Exception e) {

                            userResponseResultSearchPresenter.onErrorServer(0);
                        } finally {
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void confirmCode(String mobile, final String code, final ResultSearchPresenter<UserResponse> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/getInfoStep2";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        getHashMap.put("user_cellphone", mobile);
        getHashMap.put("user_pass", code);
        new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            UserResponse userResponse = gson.fromJson(result, UserResponse.class);
                            userResponse.getUser().setPassword(code);
                            if (userResponse != null && userResponse.getCode() == 1) {
                                new DataSaver(context).setLogin(userResponse.toString());
                                userResponseResultSearchPresenter.onSuccessResultSearch(userResponse);
                            } else
                                userResponseResultSearchPresenter.noResult(0);
                        } catch (Exception e) {

                            userResponseResultSearchPresenter.onErrorServer(0);
                        } finally {
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        }).start();
    }

    //-----------------------------------------------
    public void setTokenGcm(String token, final ResultSearchPresenter<BaseResult> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/setTokensGcm";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        UserResponse userResponse = new DataSaver(context).getUser();
        if (userResponse != null) {
            getHashMap.put("user_id", userResponse.getUserId());
            getHashMap.put("request_token_id", userResponse.getRequestTokenId());
        }
        getHashMap.put("tokengcm", token);
        new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            BaseResult userResponse = gson.fromJson(result, BaseResult.class);
                            if (userResponse != null && userResponse.getCode() == 1) {
                                userResponseResultSearchPresenter.onSuccessResultSearch(userResponse);
                            } else
                                userResponseResultSearchPresenter.noResult(0);
                        } catch (Exception e) {

                            userResponseResultSearchPresenter.onErrorServer(0);
                        } finally {
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        }).start();
    }

    //-----------------------------------------------
    public void reSignIn(final ResultSearchPresenter<UserResponse> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/getInfoStep2";
        final UserResult userResult = new DataSaver(context).getUser().getUser();
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        getHashMap.put("user_cellphone", userResult.getUserCellphone());
        getHashMap.put("user_pass", userResult.getPassword());
        new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            UserResponse userResponse = gson.fromJson(result, UserResponse.class);
                            userResponse.getUser().setPassword(userResult.getPassword());
                            if (userResponse != null && userResponse.getCode() == 1) {
                                new DataSaver(context).setLogin(userResponse.toString());
                                userResponseResultSearchPresenter.onSuccessResultSearch(userResponse);
                            } else
                                userResponseResultSearchPresenter.noResult(0);
                        } catch (Exception e) {

                            userResponseResultSearchPresenter.onErrorServer(0);
                        } finally {
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        }).start();
    }

    //-----------------------------------------------
    public void forgetPasswordByMobile(final String mobile, final ResultSearchPresenter<Boolean> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/forgetPassword";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        getHashMap.put("user_cellphone", mobile);
        getHashMap.put("type", "mobile");
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            BaseResult userResponse = gson.fromJson(result, BaseResult.class);
                            if (userResponse != null && userResponse.getCode() == 1) {
                                userResponseResultSearchPresenter.onSuccessResultSearch(true);
                            } else
                                userResponseResultSearchPresenter.noResult(0);
                        } catch (Exception e) {

                            userResponseResultSearchPresenter.onErrorServer(0);
                        } finally {
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void forgetPasswordByEmail(final String email, final ResultSearchPresenter<Boolean> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/forgetPassword";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        getHashMap.put("user_email", email);
        getHashMap.put("type", "email");
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            BaseResult userResponse = gson.fromJson(result, BaseResult.class);
                            if (userResponse != null && userResponse.getCode() == 1) {
                                //new DataSaver(context).setLogin(result);
                                userResponseResultSearchPresenter.onSuccessResultSearch(true);
                            } else
                                userResponseResultSearchPresenter.noResult(0);
                        } catch (Exception e) {

                            userResponseResultSearchPresenter.onErrorServer(0);
                        } finally {
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void checkReFoundTicket(String ticketId, final ResultSearchPresenter<ReFoundResponseFlightDomestic> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/checkrefunduser/" + ticketId;
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
//        final UserResponse userResponse = new DataSaver(context).getUser();
//        getHashMap.put("user_id", userResponse.getUserId());
//        getHashMap.put("request_token_id", userResponse.getRequestTokenId());
//        getHashMap.put("user_cellphone", userResponse.getUser().getUserCellphone());
//        getHashMap.put("type", TYPE_FLIGHT_DOMESTIC);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            ReFoundResponseFlightDomestic reFoundResponseFlightDomestic = gson.fromJson(result, ReFoundResponseFlightDomestic.class);
                            if (reFoundResponseFlightDomestic != null && (reFoundResponseFlightDomestic.getCode() == 1 || reFoundResponseFlightDomestic.getCode() == 100)) {
                                //new DataSaver(context).updateLogin(purchasesFlightDomesticResponse.getRequestTokenId(), purchasesFlightDomesticResponse.getUserId());
                                userResponseResultSearchPresenter.onSuccessResultSearch(reFoundResponseFlightDomestic);
                                userResponseResultSearchPresenter.onFinish();
                            } else if (reFoundResponseFlightDomestic != null && reFoundResponseFlightDomestic.getCode() == -100) {
                                userResponseResultSearchPresenter.noResult(-100);
                            } else {
                                userResponseResultSearchPresenter.noResult(0);
                                userResponseResultSearchPresenter.onFinish();
                            }


                        } catch (Exception e) {

                            userResponseResultSearchPresenter.onErrorServer(0);
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void splitPassenger(String ticketId, String passengerId, final ResultSearchPresenter<SplitResponse> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_MASTER + "flight/split";
        final HashMap<String, String> getHashMap = new HashMap<>();
        //getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        //getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        getHashMap.put("ticketid", ticketId);
        getHashMap.put("passengers[]", passengerId);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            SplitResponse splitResponse = gson.fromJson(result, SplitResponse.class);
                            if (splitResponse != null && splitResponse.getCode() == 1) {
                                userResponseResultSearchPresenter.onSuccessResultSearch(splitResponse);
                                userResponseResultSearchPresenter.onFinish();
                            } else if (splitResponse != null && splitResponse.getCode() == -100) {
                                userResponseResultSearchPresenter.noResult(-100);
                            } else {
                                userResponseResultSearchPresenter.noResult(0);
                                userResponseResultSearchPresenter.onFinish();
                            }


                        } catch (Exception e) {

                            userResponseResultSearchPresenter.onErrorServer(0);
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void refundUser(String id, String percent, String percentType, String passengerArray, String descRefund, final ResultSearchPresenter<BaseResult> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/refunduser/" + id;
        final UserResponse userResponse = new DataSaver(context).getUser();
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        getHashMap.put("percent", percent);
        getHashMap.put("percenttype", percentType);
        getHashMap.put("arid[]", passengerArray);
        getHashMap.put("descriptionrefund", descRefund);
        //getHashMap.put("user_id", userResponse.getUserId());
        //getHashMap.put("request_token_id", userResponse.getRequestTokenId());
        //getHashMap.put("user_cellphone", userResponse.getUser().getUserCellphone());

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            BaseResult baseResult = gson.fromJson(result, BaseResult.class);
                            if (baseResult != null && baseResult.getCode() == 1) {
                                userResponseResultSearchPresenter.onSuccessResultSearch(baseResult);
                                userResponseResultSearchPresenter.onFinish();
                            } else if (baseResult != null && baseResult.getCode() == -100) {
                                userResponseResultSearchPresenter.noResult(-100);
                            } else {
                                userResponseResultSearchPresenter.noResult(0);
                                userResponseResultSearchPresenter.onFinish();
                            }


                        } catch (Exception e) {
                            userResponseResultSearchPresenter.onErrorServer(0);
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void refundFinal(final ArrayList<String> idPassengerList,
                            final String ticketId,
                            final ResultSearchPresenter<ReFoundResponseFlightDomestic> userResponseResultSearchPresenter) {


        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int index = 0;
                for (index = 0; index < idPassengerList.size(); index++) {
                    final String idPassenger = idPassengerList.get(index);
                    final String url = BaseConfig.BASE_URL_API + "user/refundrfuser/" + idPassenger + "/" + ticketId;
                    final UserResponse userResponse = new DataSaver(context).getUser();
                    final HashMap<String, String> getHashMap = new HashMap<>();
                    getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
                    getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
                    getHashMap.put("user_id", userResponse.getUserId());
                    getHashMap.put("request_token_id", userResponse.getRequestTokenId());
                    getHashMap.put("user_cellphone", userResponse.getUser().getUserCellphone());
                    final int finalIndex = index;
                    new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                        @Override
                        public void onStart() {
                            userResponseResultSearchPresenter.onStart();
                        }

                        @Override
                        public void onErrorInternetConnection() {
                            userResponseResultSearchPresenter.onErrorInternetConnection();
                            userResponseResultSearchPresenter.onFinish();
                        }

                        @Override
                        public void onErrorServer() {
                            userResponseResultSearchPresenter.onErrorServer(0);
                            userResponseResultSearchPresenter.onFinish();
                        }

                        @Override
                        public void onFinish(String result) {
                            try {
                                Gson gson = new Gson();
                                ReFoundResponseFlightDomestic baseResult = gson.fromJson(result, ReFoundResponseFlightDomestic.class);
                                if (baseResult != null && baseResult.getCode() == 1) {
                                    String nextIdPassenger = ((idPassengerList.size() - finalIndex) == 1) ? "" : idPassengerList.get(finalIndex + 1);
                                    baseResult.setNextIdPassenger(nextIdPassenger);
                                    userResponseResultSearchPresenter.onSuccessResultSearch(baseResult);
                                    userResponseResultSearchPresenter.onFinish();
                                } else if (baseResult != null && baseResult.getCode() == -100) {
                                    userResponseResultSearchPresenter.noResult(-100);
                                } else {
                                    userResponseResultSearchPresenter.noResult(0);
                                    userResponseResultSearchPresenter.onFinish();
                                }


                            } catch (Exception e) {

                                userResponseResultSearchPresenter.onErrorServer(0);
                                userResponseResultSearchPresenter.onFinish();
                            }

                        }
                    });
                }
            }
        });
        //thread.start();
    }

    //-----------------------------------------------
    public void getListPurchasesFlightInternational(int pageNumber, int pageCounter, final ResultSearchPresenter<PurchasesFlightInternationalResponse> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/getTicketByType";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        final UserResponse userResponse = new DataSaver(context).getUser();
        getHashMap.put("user_id", userResponse.getUserId());
        getHashMap.put("request_token_id", userResponse.getRequestTokenId());
        getHashMap.put("user_cellphone", userResponse.getUser().getUserCellphone());
        getHashMap.put("page_number", String.valueOf(pageNumber));
        getHashMap.put("page_counter", String.valueOf(pageCounter));
        getHashMap.put("type", TYPE_FLIGHT_INTERNATIONAL);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            PurchasesFlightInternationalResponse purchasesFlightInternationalResponse = gson.fromJson(result, PurchasesFlightInternationalResponse.class);
                            if (purchasesFlightInternationalResponse != null && purchasesFlightInternationalResponse.getCode() == 1) {
                                new DataSaver(context).updateLogin(purchasesFlightInternationalResponse.getRequestTokenId(), purchasesFlightInternationalResponse.getUserId());
                                userResponseResultSearchPresenter.onSuccessResultSearch(purchasesFlightInternationalResponse);
                                userResponseResultSearchPresenter.onFinish();
                            } else if (purchasesFlightInternationalResponse != null && purchasesFlightInternationalResponse.getCode() == -100) {
                                userResponseResultSearchPresenter.noResult(-100);
                            } else {
                                userResponseResultSearchPresenter.noResult(0);
                                userResponseResultSearchPresenter.onFinish();
                            }


                        } catch (Exception e) {

                            userResponseResultSearchPresenter.onErrorServer(0);
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void getPassengerListTicketDomesticFlight(String ticketId, final ResultSearchPresenter<ArrayList<PassengerFlightDomestic>> passengerFlightDomesticResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/getPassengersByType";
        final HashMap<String, String> getHashMap = new HashMap<>();
        final UserResponse userResponse = new DataSaver(context).getUser();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        getHashMap.put("user_id", userResponse.getUserId());
        getHashMap.put("request_token_id", userResponse.getRequestTokenId());
        getHashMap.put("user_cellphone", userResponse.getUser().getUserCellphone());
        getHashMap.put("ticket_id", ticketId);
        getHashMap.put("ticket_id_hash", Hashing.getHash(ticketId));
        getHashMap.put("type", TYPE_FLIGHT_DOMESTIC);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        passengerFlightDomesticResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        passengerFlightDomesticResultSearchPresenter.onErrorInternetConnection();
                        passengerFlightDomesticResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        passengerFlightDomesticResultSearchPresenter.onErrorServer(0);
                        passengerFlightDomesticResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            PassengerFlightDomesticResponse passengerFlightDomesticResponse = gson.fromJson(result, PassengerFlightDomesticResponse.class);
                            if (passengerFlightDomesticResponse != null && passengerFlightDomesticResponse.getCode() == 1) {
                                new DataSaver(context).updateLogin(passengerFlightDomesticResponse.getRequestTokenId(), passengerFlightDomesticResponse.getUserId());
                                passengerFlightDomesticResultSearchPresenter.onSuccessResultSearch(passengerFlightDomesticResponse.getPassengerFlightDomestics());
                                passengerFlightDomesticResultSearchPresenter.onFinish();
                            } else if (passengerFlightDomesticResponse != null && passengerFlightDomesticResponse.getCode() == -100) {
                                passengerFlightDomesticResultSearchPresenter.noResult(-100);
                            } else {
                                passengerFlightDomesticResultSearchPresenter.noResult(0);
                                passengerFlightDomesticResultSearchPresenter.onFinish();
                            }


                        } catch (Exception e) {

                            passengerFlightDomesticResultSearchPresenter.onErrorServer(0);
                            passengerFlightDomesticResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void getPassengerListTicketTrain(String ticketId, final ResultSearchPresenter<ArrayList<PassengerFlightDomestic>> passengerFlightDomesticResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/getPassengersByType";
        final HashMap<String, String> getHashMap = new HashMap<>();
        final UserResponse userResponse = new DataSaver(context).getUser();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        getHashMap.put("user_id", userResponse.getUserId());
        getHashMap.put("request_token_id", userResponse.getRequestTokenId());
        getHashMap.put("user_cellphone", userResponse.getUser().getUserCellphone());
        getHashMap.put("ticket_id", ticketId);
        getHashMap.put("ticket_id_hash", Hashing.getHash(ticketId));
        getHashMap.put("type", TYPE_TRAIN);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        passengerFlightDomesticResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        passengerFlightDomesticResultSearchPresenter.onErrorInternetConnection();
                        passengerFlightDomesticResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        passengerFlightDomesticResultSearchPresenter.onErrorServer(0);
                        passengerFlightDomesticResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            PassengerFlightDomesticResponse passengerFlightDomesticResponse = gson.fromJson(result, PassengerFlightDomesticResponse.class);
                            if (passengerFlightDomesticResponse != null && passengerFlightDomesticResponse.getCode() == 1) {
                                new DataSaver(context).updateLogin(passengerFlightDomesticResponse.getRequestTokenId(), passengerFlightDomesticResponse.getUserId());
                                passengerFlightDomesticResultSearchPresenter.onSuccessResultSearch(passengerFlightDomesticResponse.getPassengerFlightDomestics());
                                passengerFlightDomesticResultSearchPresenter.onFinish();
                            } else if (passengerFlightDomesticResponse != null && passengerFlightDomesticResponse.getCode() == -100) {
                                passengerFlightDomesticResultSearchPresenter.noResult(-100);
                            } else {
                                passengerFlightDomesticResultSearchPresenter.noResult(0);
                                passengerFlightDomesticResultSearchPresenter.onFinish();
                            }


                        } catch (Exception e) {

                            passengerFlightDomesticResultSearchPresenter.onErrorServer(0);
                            passengerFlightDomesticResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void getPassengerListTicketInternational(String ticketId, final ResultSearchPresenter<ArrayList<PassengerFlightDomestic>> passengerFlightDomesticResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/getPassengersByType";
        final HashMap<String, String> getHashMap = new HashMap<>();
        final UserResponse userResponse = new DataSaver(context).getUser();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        getHashMap.put("user_id", userResponse.getUserId());
        getHashMap.put("request_token_id", userResponse.getRequestTokenId());
        getHashMap.put("user_cellphone", userResponse.getUser().getUserCellphone());
        getHashMap.put("ticket_id", ticketId);
        getHashMap.put("ticket_id_hash", Hashing.getHash(ticketId));
        getHashMap.put("type", TYPE_FLIGHT_INTERNATIONAL);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        passengerFlightDomesticResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        passengerFlightDomesticResultSearchPresenter.onErrorInternetConnection();
                        passengerFlightDomesticResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        passengerFlightDomesticResultSearchPresenter.onErrorServer(0);
                        passengerFlightDomesticResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            PassengerFlightDomesticResponse passengerFlightDomesticResponse = gson.fromJson(result, PassengerFlightDomesticResponse.class);
                            if (passengerFlightDomesticResponse != null && passengerFlightDomesticResponse.getCode() == 1) {
                                new DataSaver(context).updateLogin(passengerFlightDomesticResponse.getRequestTokenId(), passengerFlightDomesticResponse.getUserId());
                                passengerFlightDomesticResultSearchPresenter.onSuccessResultSearch(passengerFlightDomesticResponse.getPassengerFlightDomestics());
                                passengerFlightDomesticResultSearchPresenter.onFinish();
                            } else if (passengerFlightDomesticResponse != null && passengerFlightDomesticResponse.getCode() == -100) {
                                passengerFlightDomesticResultSearchPresenter.noResult(-100);
                            } else {
                                passengerFlightDomesticResultSearchPresenter.noResult(0);
                                passengerFlightDomesticResultSearchPresenter.onFinish();
                            }


                        } catch (Exception e) {

                            passengerFlightDomesticResultSearchPresenter.onErrorServer(0);
                            passengerFlightDomesticResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void getListPurchasesFlightDomestic(int pageNumber, int pageCounter, final ResultSearchPresenter<PurchasesFlightDomesticResponse> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/getTicketByType";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        final UserResponse userResponse = new DataSaver(context).getUser();
        getHashMap.put("page_number", String.valueOf(pageNumber));
        getHashMap.put("page_counter", String.valueOf(pageCounter));
        getHashMap.put("user_id", userResponse.getUserId());
        getHashMap.put("user_id", userResponse.getUserId());
        getHashMap.put("request_token_id", userResponse.getRequestTokenId());
        getHashMap.put("user_cellphone", userResponse.getUser().getUserCellphone());
        getHashMap.put("type", TYPE_FLIGHT_DOMESTIC);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            PurchasesFlightDomesticResponse purchasesFlightDomesticResponse = gson.fromJson(result, PurchasesFlightDomesticResponse.class);
                            if (purchasesFlightDomesticResponse != null && purchasesFlightDomesticResponse.getCode() == 1) {
                                new DataSaver(context).updateLogin(purchasesFlightDomesticResponse.getRequestTokenId(), purchasesFlightDomesticResponse.getUserId());
                                userResponseResultSearchPresenter.onSuccessResultSearch(purchasesFlightDomesticResponse);
                                userResponseResultSearchPresenter.onFinish();
                            } else if (purchasesFlightDomesticResponse != null && purchasesFlightDomesticResponse.getCode() == -100) {
                                userResponseResultSearchPresenter.noResult(-100);
                            } else {
                                userResponseResultSearchPresenter.noResult(0);
                                userResponseResultSearchPresenter.onFinish();
                            }


                        } catch (Exception e) {

                            userResponseResultSearchPresenter.onErrorServer(0);
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void getListPurchasesTrain(int pageNumber, int pageCounter, final ResultSearchPresenter<PurchasesTrainResponse> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/getTicketByType";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        final UserResponse userResponse = new DataSaver(context).getUser();
        getHashMap.put("page_number", String.valueOf(pageNumber));
        getHashMap.put("page_counter", String.valueOf(pageCounter));
        getHashMap.put("user_id", userResponse.getUserId());
        getHashMap.put("user_id", userResponse.getUserId());
        getHashMap.put("request_token_id", userResponse.getRequestTokenId());
        getHashMap.put("user_cellphone", userResponse.getUser().getUserCellphone());
        getHashMap.put("type", TYPE_TRAIN);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            PurchasesTrainResponse purchasesTrainResponse = gson.fromJson(result, PurchasesTrainResponse.class);
                            if (purchasesTrainResponse != null && purchasesTrainResponse.getCode() == 1) {
                                new DataSaver(context).updateLogin(purchasesTrainResponse.getRequestTokenId(), purchasesTrainResponse.getUserId());
                                userResponseResultSearchPresenter.onSuccessResultSearch(purchasesTrainResponse);
                                userResponseResultSearchPresenter.onFinish();
                            } else if (purchasesTrainResponse != null && purchasesTrainResponse.getCode() == -100) {
                                userResponseResultSearchPresenter.noResult(-100);
                            } else {
                                userResponseResultSearchPresenter.noResult(0);
                                userResponseResultSearchPresenter.onFinish();
                            }


                        } catch (Exception e) {

                            userResponseResultSearchPresenter.onErrorServer(0);
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void getListPurchasesBus(int pageNumber, int pageCounter, final ResultSearchPresenter<PurchasesBusResponse> userResponseResultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/getTicketByType";
        final HashMap<String, String> getHashMap = new HashMap<>();
        getHashMap.put(KeyConst.APP_KEY, KeyConst.appKey);
        getHashMap.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        final UserResponse userResponse = new DataSaver(context).getUser();
        getHashMap.put("page_number", String.valueOf(pageNumber));
        getHashMap.put("page_counter", String.valueOf(pageCounter));
        getHashMap.put("user_id", userResponse.getUserId());
        getHashMap.put("user_id", userResponse.getUserId());
        getHashMap.put("request_token_id", userResponse.getRequestTokenId());
        getHashMap.put("user_cellphone", userResponse.getUser().getUserCellphone());
        getHashMap.put("type", TYPE_BUS);
        cancelRequest();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        userResponseResultSearchPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        userResponseResultSearchPresenter.onErrorInternetConnection();
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        userResponseResultSearchPresenter.onErrorServer(0);
                        userResponseResultSearchPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            PurchasesBusResponse purchasesBusResponse = gson.fromJson(result, PurchasesBusResponse.class);
                            if (purchasesBusResponse != null && purchasesBusResponse.getCode() == 1) {
                                new DataSaver(context).updateLogin(purchasesBusResponse.getRequestTokenId(), purchasesBusResponse.getUserId());
                                userResponseResultSearchPresenter.onSuccessResultSearch(purchasesBusResponse);
                                userResponseResultSearchPresenter.onFinish();
                            } else if (purchasesBusResponse != null && purchasesBusResponse.getCode() == -100) {
                                userResponseResultSearchPresenter.noResult(-100);
                            } else {
                                userResponseResultSearchPresenter.noResult(0);
                                userResponseResultSearchPresenter.onFinish();
                            }


                        } catch (Exception e) {

                            userResponseResultSearchPresenter.onErrorServer(0);
                            userResponseResultSearchPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public void sentRating(String rate, String comment, final ResultSearchPresenter<BaseResult> resultSearchPresenter) {
        final String url = BaseConfig.BASE_URL_API + "user/setRateAndComment";
        final HashMap<String, String> getDataParams = new HashMap<>();
        getDataParams.put(KeyConst.APP_KEY, KeyConst.appKey);
        getDataParams.put(KeyConst.APP_SECRET, KeyConst.appSecret);
        getDataParams.put("rate", rate);
        getDataParams.put("comment", comment);
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
    public synchronized void cancelRequest() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }


}
