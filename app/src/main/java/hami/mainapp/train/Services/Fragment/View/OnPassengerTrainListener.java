package hami.mainapp.train.Services.Fragment.View;

import hami.mainapp.train.Services.Controller.Model.TrainPassengerInfo;


/**
 * Created by renjer on 2017-02-25.
 */

public interface OnPassengerTrainListener {
    public void onAddPassenger(TrainPassengerInfo trainPassengerInfo, Boolean hasForeign);
    public void onEditPassenger(TrainPassengerInfo trainPassengerInfo, int position);
}
