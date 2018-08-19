package com.hami.serviceflight.Services.Domestic.Controller.Presenter;

import com.hami.serviceflight.Services.Domestic.Controller.Model.RegisterFlightResponse;


/**
 * Created by renjer on 1/10/2017.
 */

public interface RegisterFlightDomesticPresenter {

    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessResultSearch(RegisterFlightResponse result);

    public void onError(String msg);

    public void onFinish();

}
