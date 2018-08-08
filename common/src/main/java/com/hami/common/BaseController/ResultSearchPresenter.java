package com.hami.common.BaseController;

/**
 * Created by renjer on 1/10/2017.
 */

public interface ResultSearchPresenter<T> {
    public void onStart();

    public void onErrorServer(int type);

    public void onErrorInternetConnection();

    public void onSuccessResultSearch(T result);

    public void noResult(int type);

    public void onError(String msg);

    public void onFinish();

}
