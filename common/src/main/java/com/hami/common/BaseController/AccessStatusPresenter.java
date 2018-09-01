package com.hami.common.BaseController;

/**
 * Created by renjer on 1/10/2017.
 */

public interface AccessStatusPresenter {

    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessGetAccessStatus(AccessStatusResponse accessStatusResponse);

    public void onError(String msg);

    public void onFinish();

}
