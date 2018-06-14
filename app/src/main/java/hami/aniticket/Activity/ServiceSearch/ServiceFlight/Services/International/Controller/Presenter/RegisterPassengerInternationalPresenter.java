package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter;

import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.RegisterPassengerResponse;

/**
 * Created by renjer on 1/10/2017.
 */

public interface RegisterPassengerInternationalPresenter {
    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessRegister(RegisterPassengerResponse registerPassengerResponse);

    public void onError(String msg);

    public void onFinish();

}
