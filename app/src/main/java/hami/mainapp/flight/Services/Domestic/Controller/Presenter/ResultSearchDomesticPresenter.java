package hami.mainapp.flight.Services.Domestic.Controller.Presenter;

import hami.mainapp.flight.Services.Domestic.Controller.Model.DomesticFlightResponse;


/**
 * Created by renjer on 1/10/2017.
 */

public interface ResultSearchDomesticPresenter {

    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessResultSearch(DomesticFlightResponse result);

    public void noFlight();

    public void onError(String msg);

    public void onFinish();

}
