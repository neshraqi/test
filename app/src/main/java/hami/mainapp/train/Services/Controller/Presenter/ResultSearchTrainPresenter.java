package hami.mainapp.train.Services.Controller.Presenter;

import hami.mainapp.train.Services.Controller.Model.TrainDataResponse;


/**
 * Created by renjer on 1/10/2017.
 */

public interface ResultSearchTrainPresenter {

    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessResultSearch(TrainDataResponse result);

    public void noTrain();

    public void onError(String msg);

    public void onFinish();

}
