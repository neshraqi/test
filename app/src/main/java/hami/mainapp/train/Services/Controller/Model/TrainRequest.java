package hami.mainapp.train.Services.Controller.Model;

import com.hami.common.Const.TrainRules;

import java.io.Serializable;
import java.util.HashMap;


/**
 * Created by renjer on 1/30/2017.
 */

public class TrainRequest implements Serializable {

    //-----------------------------------------------
    public String sourceTrain;
    public String destinationTrain;
    public String sourceTrainFa;
    public String destinationTrainFa;
    public String trainDepartureDate;
    public String departureGoTrainEng;
    public String departureGoTrainPersian;
    public String departureFaTrain;
    public String unixDateFTrain;
    public String countPassengerTrain = "1";
    public String typeTicketTrain = String.valueOf(TrainRules.TP_NORMAL);
    public String reverseDate;
    public Boolean isCope = false;

    //-----------------------------------------------
    public TrainRequest() {
    }

    //-----------------------------------------------
    public TrainRequest(String sourceTrain, String destinationTrain, String departureGoTrainEng, String departureGoTrainPersian, String countPassengerTrain, String typeTicketTrain, Boolean isCope) {
        this.sourceTrain = sourceTrain;
        this.destinationTrain = destinationTrain;
        this.departureGoTrainEng = departureGoTrainEng;
        this.countPassengerTrain = countPassengerTrain;
        this.typeTicketTrain = typeTicketTrain;
        this.departureGoTrainPersian = departureGoTrainPersian;
        this.isCope = isCope;
    }

    //-----------------------------------------------
    public void movementSourceWithDest() {
        String temp = getSourceTrain();
        String tempFa = getSourceTrainFa();
        setSourceTrain(getDestinationTrain());
        setSourceTrainFa(getDestinationTrainFa());
        setDestinationTrain(temp);
        setDestinationTrainFa(tempFa);
    }

    public void setSourceTrainFa(String sourceTrainFa) {
        this.sourceTrainFa = sourceTrainFa;
    }

    public void setDestinationTrainFa(String destinationTrainFa) {
        this.destinationTrainFa = destinationTrainFa;
    }

    public void setSourceTrain(String sourceTrain) {
        this.sourceTrain = sourceTrain;
    }

    public void setDestinationTrain(String destinationTrain) {
        this.destinationTrain = destinationTrain;
    }

    public void setDepartureGoTrainEng(String departureGoTrainEng) {
        this.departureGoTrainEng = departureGoTrainEng;
    }

    public void setDepartureGoTrainPersian(String departureGoTrainPersian) {
        this.departureGoTrainPersian = departureGoTrainPersian;
    }

    public void setCountPassengerTrain(String countPassengerTrain) {
        this.countPassengerTrain = countPassengerTrain;
    }

    public void setTypeTicketTrain(String typeTicketTrain) {
        this.typeTicketTrain = typeTicketTrain;
    }

    public void setCope(Boolean cope) {
        isCope = cope;
    }

    //-----------------------------------------------

    public String getSourceTrain() {
        return sourceTrain;
    }

    public String getDestinationTrain() {
        return destinationTrain;
    }

    public String getDepartureGoTrainEng() {
        return departureGoTrainEng;
    }

    public String getDepartureGoTrainPersian() {
        return departureGoTrainPersian;
    }

    public String getCountPassengerTrain() {
        return countPassengerTrain;
    }

    public String getTypeTicketTrain() {
        if (typeTicketTrain == null || typeTicketTrain.length() == 0)
            return String.valueOf(TrainRules.TP_NORMAL);
        return typeTicketTrain;
    }

    public Boolean isCope() {
        return isCope;
    }

    public String getCopeString() {
        if (isCope == true)
            return "1";
        return "0";
    }

    public String getSourceTrainFa() {
        return sourceTrainFa;
    }

    public String getDestinationTrainFa() {
        return destinationTrainFa;
    }
    //-----------------------------------------------

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sourcetrain", sourceTrain);
        hashMap.put("destinationtrain", destinationTrain);
        hashMap.put("trainDepartureDate", "");
        hashMap.put("DepartureGotrain", departureGoTrainEng);
        hashMap.put("DepartureFAtrain", "");
        hashMap.put("unixdateFtrain", "");
        hashMap.put("count-passengertrain", countPassengerTrain);
        hashMap.put("type-tickettrain", typeTicketTrain);
        hashMap.put("iscoupe", getCopeString());
        hashMap.put("reverseDate", "");
        return hashMap;
    }
    //-----------------------------------------------

}
