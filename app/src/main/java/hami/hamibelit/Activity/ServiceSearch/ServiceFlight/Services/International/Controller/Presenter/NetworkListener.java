package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter;

/**
 * Created by renjer on 1/10/2017.
 */

public interface NetworkListener {

    public void onStart();

    public void onErrorInternetConnection();

    public void onErrorServer();

    public void onFinish(String result);
}
