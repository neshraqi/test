package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Presenter;

import java.util.List;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.FlightInformationResponse;


public interface FlightInformationDomesticPresenter {

    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessResultSearch(FlightInformationResponse flightInformationResponse);

    public void onSuccessGetCity(List<String> result);

    public void onError(String msg);

    public void onFinish();

}
