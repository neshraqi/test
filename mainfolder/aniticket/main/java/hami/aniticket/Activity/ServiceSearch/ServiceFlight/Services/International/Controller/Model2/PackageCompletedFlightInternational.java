package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2;

import java.io.Serializable;

import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.FlightInternationalRequest;

/**
 * Created by renjer on 2017-11-14.
 */

public class PackageCompletedFlightInternational implements Serializable {
    FlightInternationalRequest flightInternationalRequest;
    private Object linkedTreeMapCityParto;
    private Object linkedTreeMapCityIati;
    private Object linkedTreeMapAirLineParto;
    private Object linkedTreeMapAirLineIati;
    private String searchIdParto = "", searchIdIati = "";
    //-----------------------------------------------

    public PackageCompletedFlightInternational() {
    }
    //-----------------------------------------------

    public FlightInternationalRequest getFlightInternationalRequest() {
        return flightInternationalRequest;
    }

    public void setFlightInternationalRequest(FlightInternationalRequest flightInternationalRequest) {
        this.flightInternationalRequest = flightInternationalRequest;
    }

    public Object getLinkedTreeMapCityParto() {
        return linkedTreeMapCityParto;
    }

    public void setLinkedTreeMapCityParto(Object linkedTreeMapCityParto) {
        this.linkedTreeMapCityParto = linkedTreeMapCityParto;
    }

    public Object getLinkedTreeMapCityIati() {
        return linkedTreeMapCityIati;
    }

    public void setLinkedTreeMapCityIati(Object linkedTreeMapCityIati) {
        this.linkedTreeMapCityIati = linkedTreeMapCityIati;
    }

    public Object getLinkedTreeMapAirLineParto() {
        return linkedTreeMapAirLineParto;
    }

    public void setLinkedTreeMapAirLineParto(Object linkedTreeMapAirLineParto) {
        this.linkedTreeMapAirLineParto = linkedTreeMapAirLineParto;
    }

    public Object getLinkedTreeMapAirLineIati() {
        return linkedTreeMapAirLineIati;
    }

    public void setLinkedTreeMapAirLineIati(Object linkedTreeMapAirLineIati) {
        this.linkedTreeMapAirLineIati = linkedTreeMapAirLineIati;
    }

    public String getSearchIdParto() {
        return searchIdParto;
    }

    public void setSearchIdParto(String searchIdParto) {
        this.searchIdParto = searchIdParto;
    }

    public String getSearchIdIati() {
        return searchIdIati;
    }

    public void setSearchIdIati(String searchIdIati) {
        this.searchIdIati = searchIdIati;
    }
}
