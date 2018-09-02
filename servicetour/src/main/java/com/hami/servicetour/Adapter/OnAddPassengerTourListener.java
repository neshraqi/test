package com.hami.servicetour.Adapter;

import com.hami.serviceflight.Services.Domestic.Controller.Model.DomesticPassengerInfo;

/**
 * Created by renjer on 2017-02-25.
 */

public interface OnAddPassengerTourListener {
    public void onAddPassenger(DomesticPassengerInfo domesticPassengerInfo, Boolean hasForeign);
    public void onEditPassenger(DomesticPassengerInfo domesticPassengerInfo, int position);
}
