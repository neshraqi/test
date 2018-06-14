package hami.hamibelit.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by renjer on 2017-03-08.
 */

public class TrainDataResponse {
    @SerializedName("data")
    private ArrayList<TrainResponse> trainResponseList;

    private HashMap<String, String> trainCompany;
    //-----------------------------------------------

    public TrainDataResponse() {
    }
    //-----------------------------------------------

    public ArrayList<TrainResponse> getTrainResponseList() {
        return trainResponseList;
    }

    public HashMap<String, String> getTrainCompany() {
        return trainCompany;
    }

    public void setTrainCompany(HashMap<String, String> trainCompany) {
        this.trainCompany = trainCompany;
    }
}
