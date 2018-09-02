package com.hami.serviceflight.Services.International.Controller.Presenter;

import com.hami.serviceflight.Services.International.Controller.Model.OutBound;

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
