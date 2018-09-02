package com.hami.serviceflight.Services.International.Controller.Presenter;

import com.hami.serviceflight.Services.International.Controller.Model.SearchInternational;

import java.util.List;

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
