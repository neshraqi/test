package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Presenter;

import java.util.HashMap;

/**
 * Created by renjer on 2017-08-22.
 */

public interface AirlineListener<T, S> {
    public void getAirlineList(HashMap<T, S> airlines);

    public void noAirline();
}
