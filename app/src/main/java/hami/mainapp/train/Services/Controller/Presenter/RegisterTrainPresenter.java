package hami.mainapp.train.Services.Controller.Presenter;

import hami.mainapp.train.Services.Controller.Model.RegisterTrainResponse;


/**
 * Created by renjer on 2017-03-11.
 */

public interface RegisterTrainPresenter {
    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessResultSearch(RegisterTrainResponse result);

    public void onError(String msg);

    public void onFinish();
}
