package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter;

import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.FlightInternationalResponse;

/**
 * Created by renjer on 1/10/2017.
 */

public interface ResultSearchInternationalPresenter {
    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessResultSearch(FlightInternationalResponse flightInternationalResponse);

    public void noFlight();

    public void onError(String msg);

    public void onFinish();

}
