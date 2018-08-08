package hami.mainapp.Activity.ServiceSearch.ServiceTrain.Services.Controller.Presenter;

import hami.mainapp.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainPassengerInfo;

/**
 * Created by renjer on 1/11/2017.
 */

public interface OnSelectItemPassengerTrainListener {
    public void onSelectItemTrain(TrainPassengerInfo trainPassengerInfo, int position);
    public void onRemoveItemTrain(TrainPassengerInfo trainPassengerInfo, int position);
}
