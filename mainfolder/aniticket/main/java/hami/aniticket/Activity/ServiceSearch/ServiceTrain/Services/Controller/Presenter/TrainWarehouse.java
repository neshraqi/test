package hami.hamibelit.Activity.ServiceSearch.ServiceTrain.Services.Controller.Presenter;

import hami.hamibelit.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.CityTrain;
import hami.hamibelit.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.RegisterTrainRequest;
import hami.hamibelit.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainRequest;
import hami.hamibelit.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainResponse;

/**
 * Created by renjer on 2017-02-11.
 */

public class TrainWarehouse {
    private static TrainRequest trainRequest;
    private static TrainResponse trainResponse;
    private static CityTrain fromSearchTrain;
    private static CityTrain toSearchTrain;
    private static RegisterTrainRequest registerTrainRequest;
    //-----------------------------------------------

    public static RegisterTrainRequest getRegisterTrainRequest() {
        return registerTrainRequest;
    }

    public static void setRegisterTrainRequest(RegisterTrainRequest registerTrainRequest) {
        TrainWarehouse.registerTrainRequest = registerTrainRequest;
    }

    //-----------------------------------------------

    public static CityTrain getFromSearchTrain() {
        return fromSearchTrain;
    }

    public static void setFromSearchTrain(CityTrain fromSearchTrain) {
        TrainWarehouse.fromSearchTrain = fromSearchTrain;
    }

    public static CityTrain getToSearchTrain() {
        return toSearchTrain;
    }

    public static void setToSearchTrain(CityTrain toSearchTrain) {
        TrainWarehouse.toSearchTrain = toSearchTrain;
    }

    //-----------------------------------------------

    public static TrainResponse getTrainResponse() {
        return trainResponse;
    }

    public static void setTrainResponse(TrainResponse trainResponse) {
        TrainWarehouse.trainResponse = trainResponse;
    }

    //-----------------------------------------------
    public static TrainRequest getTrainRequest() {
        return trainRequest;
    }

    public static void setTrainRequest(TrainRequest trainRequest) {
        TrainWarehouse.trainRequest = trainRequest;
    }

    //-----------------------------------------------
    public static void clearWareHouse() {
        try {
            trainRequest = null;
            trainResponse = null;
            fromSearchTrain = null;
            toSearchTrain = null;
            System.gc();
        } catch (Exception e) {

        }
    }
}
