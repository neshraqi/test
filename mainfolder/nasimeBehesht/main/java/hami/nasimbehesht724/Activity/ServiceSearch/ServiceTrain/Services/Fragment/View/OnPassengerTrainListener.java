package hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Fragment.View;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainPassengerInfo;

/**
 * Created by renjer on 2017-02-25.
 */

public interface OnPassengerTrainListener {
    public void onAddPassenger(TrainPassengerInfo trainPassengerInfo, Boolean hasForeign);
    public void onEditPassenger(TrainPassengerInfo trainPassengerInfo, int position);
}
