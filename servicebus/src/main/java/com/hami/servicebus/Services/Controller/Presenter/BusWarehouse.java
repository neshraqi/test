package com.hami.servicebus.Services.Controller.Presenter;

import com.hami.servicebus.Services.Controller.Model.BusTicketInformation;
import com.hami.servicebus.Services.Controller.Model.RegisterBusRequest;
import com.hami.servicebus.Services.Controller.Model.SearchBusRequest;
import com.hami.servicebus.Services.Controller.Model.SearchBusResponse;

/**
 * Created by renjer on 2017-02-11.
 */

public class BusWarehouse {
    private static SearchBusRequest searchBusRequest;
    private static SearchBusResponse currentSelectedSearchBusResponse;
    private static RegisterBusRequest registerBusRequest;
    private static BusTicketInformation busTicketInformation;
    //-----------------------------------------------

    public static BusTicketInformation getBusTicketInformation() {
        return busTicketInformation;
    }

    public static void setBusTicketInformation(BusTicketInformation busTicketInformation) {
        BusWarehouse.busTicketInformation = busTicketInformation;
    }

    //-----------------------------------------------
    public static void setSearchBusRequest(SearchBusRequest searchBusRequest ){
        BusWarehouse.searchBusRequest = searchBusRequest;
    }
    //-----------------------------------------------
    public static SearchBusRequest getSearchBusRequest(){
        return searchBusRequest;
    }
    //-----------------------------------------------

    public static void setCurrentSelectedSearchBusResponse(SearchBusResponse currentSelectedSearchBusResponse) {
        BusWarehouse.currentSelectedSearchBusResponse = currentSelectedSearchBusResponse;
    }
    //-----------------------------------------------

    public static SearchBusResponse getCurrentSelectedSearchBusResponse() {
        return currentSelectedSearchBusResponse;
    }
    //-----------------------------------------------

    public static RegisterBusRequest getRegisterBusRequest() {
        return registerBusRequest;
    }

    public static void setRegisterBusRequest(RegisterBusRequest registerBusRequest) {
        BusWarehouse.registerBusRequest = registerBusRequest;
    }
}