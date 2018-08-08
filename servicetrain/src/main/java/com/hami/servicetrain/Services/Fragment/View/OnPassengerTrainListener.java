package com.hami.servicetrain.Services.Fragment.View;

import com.hami.servicetrain.Services.Controller.Model.TrainPassengerInfo;


/**
 * Created by renjer on 2017-02-25.
 */

public interface OnPassengerTrainListener {
    public void onAddPassenger(TrainPassengerInfo trainPassengerInfo, Boolean hasForeign);
    public void onEditPassenger(TrainPassengerInfo trainPassengerInfo, int position);
}
