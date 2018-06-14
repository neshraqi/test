package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Presenter;

import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticFlight;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticRequest;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.RegisterFlightDomesticRequest;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.SearchInternational;

/**
 * Created by renjer on 1/16/2017.
 */
public class WarehouseDomestic {

    private static DomesticRequest domesticRequest;
    private static String searchId;
    private static DomesticFlight domesticFlight;
    private static SearchInternational fromSearchInternational;
    private static SearchInternational toSearchInternational;
    private static RegisterFlightDomesticRequest registerFlightDomesticRequest;
    //-----------------------------------------------

    public static RegisterFlightDomesticRequest getRegisterFlightDomesticRequest() {
        return registerFlightDomesticRequest;
    }

    public static void setRegisterFlightDomesticRequest(RegisterFlightDomesticRequest registerFlightDomesticRequest) {
        WarehouseDomestic.registerFlightDomesticRequest = registerFlightDomesticRequest;
    }

    //-----------------------------------------------
    public static DomesticFlight getDomesticFlight() {
        return domesticFlight;
    }

    public static void setDomesticFlight(DomesticFlight domesticFlight) {
        WarehouseDomestic.domesticFlight = domesticFlight;
    }
    //-----------------------------------------------

    public static SearchInternational getFromSearchInternational() {
        return fromSearchInternational;
    }

    public static void setFromSearchInternational(SearchInternational fromSearchInternational) {
        WarehouseDomestic.fromSearchInternational = fromSearchInternational;
    }

    public static SearchInternational getToSearchInternational() {
        return toSearchInternational;
    }

    public static void setToSearchInternational(SearchInternational toSearchInternational) {
        WarehouseDomestic.toSearchInternational = toSearchInternational;
    }

    //-----------------------------------------------
    public static String getSearchId() {
        return searchId;
    }

    public static void setSearchId(String searchId) {
        WarehouseDomestic.searchId = searchId;
    }

    //-----------------------------------------------
    public static DomesticRequest getDomesticRequest() {
        return domesticRequest;
    }

    public static void setDomesticRequest(DomesticRequest domesticRequest) {
        WarehouseDomestic.domesticRequest = domesticRequest;
    }

    //-----------------------------------------------
    public static void clear() {
        domesticRequest = null;
        domesticFlight = null;
        fromSearchInternational = null;
        toSearchInternational = null;
        registerFlightDomesticRequest = null;
        searchId = null;
    }
}

