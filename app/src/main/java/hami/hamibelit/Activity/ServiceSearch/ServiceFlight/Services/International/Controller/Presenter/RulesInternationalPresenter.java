package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter;

import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.BaggaegeResponse;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.RulesResponseIati;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.RulesResponseParto;

/**
 * Created by renjer on 1/10/2017.
 */

public interface RulesInternationalPresenter {
    public void onStart();

    public void onErrorServer();

    public void onErrorInternetConnection();

    public void onSuccessGetRulesParto(RulesResponseParto rulesResponse, BaggaegeResponse baggaegeResponse);

    public void onSuccessGetRulesIati(RulesResponseIati rulesResponse, BaggaegeResponse baggaegeResponse);

    public void onSuccessGetCapacity(BaggaegeResponse baggaegeResponse);

    public void onError(String msg);

    public void onFinish();

}
