package hami.mainapp.flight.Services.Domestic.Controller.Presenter;

import hami.mainapp.flight.Services.Domestic.Controller.Model.DomesticPassengerInfo;


/**
 * Created by renjer on 1/11/2017.
 */

public interface OnSelectItemPassengerDomesticListener {
    public void onSelectItemFlightDomestic(DomesticPassengerInfo domesticPassengerInfo, int position);
    public void onRemoveItemFlightDomestic(DomesticPassengerInfo domesticPassengerInfo, int position);
}
