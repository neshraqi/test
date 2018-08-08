package hami.mainapp.Activity.ServiceTour.Adapter;

import hami.mainapp.Activity.ServiceTour.Controller.Model.PassengerTour;

/**
 * Created by renjer on 1/11/2017.
 */

public interface OnSelectItemPassengerTourListener {
    public void onSelectItemFlightDomestic(PassengerTour passengerTour, int position);

    public void onRemoveItemFlightDomestic(PassengerTour passengerTour, int position);
}
