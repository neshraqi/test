package com.hami.servicetrain.Services.Controller.Presenter;


import com.hami.servicetrain.Services.Controller.Model.DataPassengerInfo;

public interface NationalCodePresenter {

    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessDataPassengerInfo(DataPassengerInfo dataPassengerInfo);

    public void onError(String msg);

    public void onFinish();

}
