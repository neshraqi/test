package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter;

import java.util.List;

import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.FlightInternationalRequest;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.OutBound;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.PassengerInfo;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.RegisterPassengerResponse;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.SearchInternational;

/**
 * Created by renjer on 1/16/2017.
 */
public class InternationalWarehouse {

    private static List<OutBound> outBounds;
    private static List<OutBound> returns;
    private static OutBound outBoundReturn;
    private static OutBound outBoundWent;
    private static FlightInternationalRequest currentFlightRequest;
    private static String searchId;
    private static List<PassengerInfo> passengerInfoList;
    private static SearchInternational fromSearchInternational, toSearchInternational;
    private static RegisterPassengerResponse registerPassengerResponse;
    //-----------------------------------------------
    public static void movementPlace(){
        SearchInternational help = toSearchInternational;
        toSearchInternational = fromSearchInternational;
        fromSearchInternational = help;
    }
    //-----------------------------------------------

    public static String getSearchId() {
        return searchId;
    }
    //-----------------------------------------------

    public static void setSearchId(String searchId) {
        InternationalWarehouse.searchId = searchId;
    }

    //-----------------------------------------------
    public static List<OutBound> getLastOutBound() {
        return outBounds;
    }

    //-----------------------------------------------
    public static void setLastOutBound(List<OutBound> lastOutBound) {
        outBounds = lastOutBound;
    }

    //-----------------------------------------------
    public static List<OutBound> getListReturn() {
        return returns;
    }

    //-----------------------------------------------
    public static void setListReturn(List<OutBound> returns) {
        InternationalWarehouse.returns = returns;
    }

    //-----------------------------------------------
    public static OutBound getOutBoundWent() {
        return outBoundWent;
    }
    //-----------------------------------------------

    public static void setOutBoundWent(OutBound outBound) {
        InternationalWarehouse.outBoundWent = outBound;
    }

    //-----------------------------------------------
    public static OutBound getOutBoundReturn() {
        return outBoundReturn;
    }
    //-----------------------------------------------

    public static void setOutBoundReturn(OutBound outBound) {
        InternationalWarehouse.outBoundReturn = outBound;
    }

    //-----------------------------------------------
    public static void setPassengerInfoList(List<PassengerInfo> passengerInfoList) {
        InternationalWarehouse.passengerInfoList = passengerInfoList;
    }

    //-----------------------------------------------
    public static List<PassengerInfo> getPassengerInfoList() {
        return InternationalWarehouse.passengerInfoList;
    }

    //-----------------------------------------------
    public static FlightInternationalRequest getCurrentFlightRequest() {
        return currentFlightRequest;
    }

    public static void setCurrentFlightRequest(FlightInternationalRequest currentFlightRequest) {
        InternationalWarehouse.currentFlightRequest = currentFlightRequest;
    }
    //-----------------------------------------------

    public static RegisterPassengerResponse getRegisterPassengerResponse() {
        return registerPassengerResponse;
    }

    public static void setRegisterPassengerResponse(RegisterPassengerResponse registerPassengerResponse) {
        InternationalWarehouse.registerPassengerResponse = registerPassengerResponse;
    }


    //-----------------------------------------------

    public static SearchInternational getFromSearchInternational() {
        return fromSearchInternational;
    }

    //-----------------------------------------------
    public static SearchInternational getToSearchInternational() {
        return toSearchInternational;
    }
    //-----------------------------------------------

    public static void setFromSearchInternational(SearchInternational fromSearchInternational) {
        InternationalWarehouse.fromSearchInternational = fromSearchInternational;
    }

    public static void setToSearchInternational(SearchInternational toSearchInternational) {
        InternationalWarehouse.toSearchInternational = toSearchInternational;
    }

    //-----------------------------------------------
    public static void clearWareHouse() {
        try {
            outBounds = null;
            returns = null;
            outBoundReturn = null;
            outBoundWent = null;
            currentFlightRequest = null;
            searchId = null;
            passengerInfoList = null;
            registerPassengerResponse = null;
            fromSearchInternational = null;
            toSearchInternational = null;
            System.gc();
        }
        catch (Exception e){

        }
    }
    //-----------------------------------------------
}

