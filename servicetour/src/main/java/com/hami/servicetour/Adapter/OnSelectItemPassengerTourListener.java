package com.hami.servicetour.Adapter;

import com.hami.servicetour.Controller.Model.PassengerTour;

/**
 * Created by renjer on 1/11/2017.
 */

public interface OnSelectItemPassengerTourListener {
    public void onSelectItemFlightDomestic(PassengerTour passengerTour, int position);

    public void onRemoveItemFlightDomestic(PassengerTour passengerTour, int position);
}
