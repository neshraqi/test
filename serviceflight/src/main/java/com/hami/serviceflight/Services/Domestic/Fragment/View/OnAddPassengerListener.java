package com.hami.serviceflight.Services.Domestic.Fragment.View;

import com.hami.serviceflight.Services.Domestic.Controller.Model.DomesticPassengerInfo;

/**
 * Created by renjer on 2017-02-25.
 */

public interface OnAddPassengerListener {
    public void onAddPassenger(DomesticPassengerInfo domesticPassengerInfo, Boolean hasForeign);
    public void onEditPassenger(DomesticPassengerInfo domesticPassengerInfo, int position);
}
