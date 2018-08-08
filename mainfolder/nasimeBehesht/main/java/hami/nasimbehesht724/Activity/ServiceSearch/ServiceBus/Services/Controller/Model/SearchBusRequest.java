package hami.mainapp.Activity.ServiceSearch.ServiceBus.Services.Controller.Model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by renjer on 2017-02-11.
 */

public class SearchBusRequest implements Serializable {
    //
    private String sourceBus;
    private String destinationBus;
    private String departureGoBus;
    private String departurePersianGoBus;
    private String countPassengerBus = "1";
    private String fromCity;
    private String toCity;
    //-----------------------------------------------


    public SearchBusRequest(String fromCity, String toCity, String sourceBus, String destinationBus, String departureGoBus, String departurePersianGoBus, String countPassengerBus) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.sourceBus = sourceBus;
        this.destinationBus = destinationBus;
        this.departureGoBus = departureGoBus;
        this.countPassengerBus = countPassengerBus;
        this.departurePersianGoBus = departurePersianGoBus;
    }
    //-----------------------------------------------

    public SearchBusRequest() {
    }

    //-----------------------------------------------
    public void movementSourceWithDest() {
        String temp = getSourceBus();
        setSourceBus(getDestinationBus());
        setDestinationBus(temp);
    }

    public String getSourceBus() {
        return sourceBus;
    }

    public String getDestinationBus() {
        return destinationBus;
    }

    public String getDepartureGoBus() {
        return departureGoBus;
    }

    public String getCountPassengerBus() {
        return countPassengerBus;
    }

    public String getDeparturePersianGoBus() {
        return departurePersianGoBus;
    }

    public String getFromCity() {
        return fromCity;
    }

    public String getToCity() {
        return toCity;
    }
    //-----------------------------------------------

    public void setSourceBus(String sourceBus) {
        this.sourceBus = sourceBus;
    }

    public void setDestinationBus(String destinationBus) {
        this.destinationBus = destinationBus;
    }

    public void setDepartureGoBus(String departureGoBus) {
        this.departureGoBus = departureGoBus;
    }

    public void setDeparturePersianGoBus(String departurePersianGoBus) {
        this.departurePersianGoBus = departurePersianGoBus;
    }

    public void setCountPassengerBus(String countPassengerBus) {
        this.countPassengerBus = countPassengerBus;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    //-----------------------------------------------
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> postDataParams = new HashMap<>();
        postDataParams.put("sourcebus[]", sourceBus);
        postDataParams.put("destinationbus[]", destinationBus);
        postDataParams.put("DepartureGobus", departureGoBus);
        postDataParams.put("count-passengerbus", countPassengerBus);

        return postDataParams;
    }
    //-----------------------------------------------

}
