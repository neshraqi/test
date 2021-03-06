package com.hami.common.BaseController;

import android.content.Context;

import com.google.gson.Gson;
import com.hami.common.BaseNetwork.BaseConfig;
import com.hami.common.BaseNetwork.NetworkListener;
import com.hami.common.BaseNetwork.WebServiceNetwork;
import java.util.HashMap;


/**
 * Created by renjer on 2018-03-27.
 */

public class AccessApi {
    private Context context;
    //-----------------------------------------------

    public AccessApi(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void getAccessStatus(final AccessStatusPresenter accessStatusPresenter) {
        //final String url = "http://asaparvaz.com/main/getAccess";
        final String url = BaseConfig.BASE_URL_MASTER + "main/getAccess";
        final HashMap<String, String> getHashMap = new HashMap<>();
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {
                        accessStatusPresenter.onStart();
                    }

                    @Override
                    public void onErrorInternetConnection() {
                        accessStatusPresenter.onErrorInternetConnection();
                        accessStatusPresenter.onFinish();
                    }

                    @Override
                    public void onErrorServer() {
                        accessStatusPresenter.onErrorServer();
                        accessStatusPresenter.onFinish();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            AccessStatusResponse accessStatusResponse = gson.fromJson(result, AccessStatusResponse.class);
                            if (accessStatusResponse != null && accessStatusResponse.getSuccess()) {
                                accessStatusPresenter.onSuccessGetAccessStatus(accessStatusResponse);
                                accessStatusPresenter.onFinish();
                            } else {
                                accessStatusPresenter.onErrorServer();
                                accessStatusPresenter.onFinish();
                            }
                        } catch (Exception e) {
                            accessStatusPresenter.onErrorServer();
                            accessStatusPresenter.onFinish();
                        }

                    }
                });
            }
        });
        thread.start();
    }
    //-----------------------------------------------
}
