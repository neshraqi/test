package com.hami.servicebus.Services.Controller.Presenter;

/**
 * Created by renjer on 1/10/2017.
 */

public interface PaymentPresenter {

    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessBuy();

    public void onErrorBuy();

    public void onReTryGetTicket();

    public void onReTryGetPayment();

    public void onError(String msg);

    public void onFinish();

}
