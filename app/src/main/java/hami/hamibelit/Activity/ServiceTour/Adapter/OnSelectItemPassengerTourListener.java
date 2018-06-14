package hami.hamibelit.Activity.ServiceTour.Adapter;

import hami.hamibelit.Activity.ServiceTour.Controller.Model.PassengerTour;

/**
 * Created by renjer on 1/11/2017.
 */

public interface OnSelectItemPassengerTourListener {
    public void onSelectItemFlightDomestic(PassengerTour passengerTour, int position);

    public void onRemoveItemFlightDomestic(PassengerTour passengerTour, int position);
}
