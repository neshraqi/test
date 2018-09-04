package hami.mainapp.flight.Services.International.Controller.Presenter;

import hami.mainapp.flight.Services.International.Controller.Model.OutBound;

import java.util.ArrayList;

/**
 * Created by renjer on 1/10/2017.
 */

public interface ResultReturnListInternationalPresenter {
    public void onStart();

    public void onSuccessResult(ArrayList<OutBound> result);

    public void noFlight();

    public void onFinish();

}
