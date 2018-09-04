package hami.mainapp.flight.Services.International.Controller.Presenter;

import hami.mainapp.flight.Services.International.Controller.Model.DataPassengerInfo;

public interface NationalCodePresenter {

    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessDataPassengerInfo(DataPassengerInfo dataPassengerInfo);

    public void onError(String msg);

    public void onFinish();

}
