package com.hami.servicebus.Services.Controller.Presenter;

import com.hami.servicebus.Services.Controller.Model.BusTicketInformation;


/**
 * Created by renjer on 1/10/2017.
 */

public interface RegisterBusPresenter {

    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessRegisterBus(BusTicketInformation busTicketInformation);

    public void noBus();

    public void onError(String msg);

    public void onFinish();

}
