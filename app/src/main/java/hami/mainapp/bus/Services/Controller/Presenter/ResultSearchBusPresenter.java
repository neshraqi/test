package hami.mainapp.bus.Services.Controller.Presenter;

import hami.mainapp.bus.Services.Controller.Model.SearchBusDataResponse;

/**
 * Created by renjer on 1/10/2017.
 */

public interface ResultSearchBusPresenter {

    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessResultSearch(SearchBusDataResponse result);

    public void noBus();

    public void onError(String msg);

    public void onFinish();

}
