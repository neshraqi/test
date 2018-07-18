package hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.SeatResponse;

/**
 * Created by renjer on 1/10/2017.
 */

public interface SeatChairBusPresenter {

    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessResultSearch(SeatResponse result);

    public void noBus();

    public void onError(String msg);

    public void onFinish();

}