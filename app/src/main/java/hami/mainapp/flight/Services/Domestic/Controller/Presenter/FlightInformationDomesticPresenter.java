package hami.mainapp.flight.Services.Domestic.Controller.Presenter;

import hami.mainapp.flight.Services.Domestic.Controller.Model.FlightInformationResponse;

import java.util.List;

public interface FlightInformationDomesticPresenter {

    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessResultSearch(FlightInformationResponse flightInformationResponse);

    public void onSuccessGetCity(List<String> result);

    public void onError(String msg);

    public void onFinish();

}
