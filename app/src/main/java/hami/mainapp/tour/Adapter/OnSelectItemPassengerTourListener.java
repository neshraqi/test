package hami.mainapp.tour.Adapter;

import hami.mainapp.tour.Controller.Model.PassengerTour;

/**
 * Created by renjer on 1/11/2017.
 */

public interface OnSelectItemPassengerTourListener {
    public void onSelectItemFlightDomestic(PassengerTour passengerTour, int position);

    public void onRemoveItemFlightDomestic(PassengerTour passengerTour, int position);
}