package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter;

import java.util.List;

import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.SearchInternational;

/**
 * Created by renjer on 1/10/2017.
 */

public interface SearchInternationalPresenter {
    public void onStart();
    public void onErrorServer();
    public void onErrorInternetConnection();
    public void onSuccessSearch(List<SearchInternational> result);
    public void onError(String msg);
    public void onFinish();

}
