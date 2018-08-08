package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter;

import java.util.ArrayList;

import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.OutBound;

/**
 * Created by renjer on 1/10/2017.
 */

public interface ResultReturnListInternationalPresenter {
    public void onStart();

    public void onSuccessResult(ArrayList<OutBound> result);

    public void noFlight();

    public void onFinish();

}
