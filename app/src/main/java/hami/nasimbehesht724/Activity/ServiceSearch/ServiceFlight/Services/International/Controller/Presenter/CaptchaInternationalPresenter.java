package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter;

/**
 * Created by renjer on 1/10/2017.
 */

public interface CaptchaInternationalPresenter {
    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessCaptcha(String url);

    public void onError(String msg);

    public void onFinish();

}
