package com.hami.serviceflight.Services.Domestic.Controller.Model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by renjer on 1/30/2017.
 */

public class DomesticRequest implements Serializable {
    public String source;
    public String destination;
    public String sourcePersian;
    public String destinationPersian;
    public String departureGo;
    public String departureGoPersian;
    public String type;
    //-----------------------------------------------

    public DomesticRequest(String source, String destination, String departureGo, String departureGoPersian) {
        this.source = source;
        this.destination = destination;
        this.departureGo = departureGo;
        this.departureGoPersian = departureGoPersian;
    }
    //-----------------------------------------------

    public DomesticRequest() {
    }

    //-----------------------------------------------
    public void movementSourceWithDest() {
        String temp = getSource();
        String tempPersian = getSourcePersian();
        setSource(getDestination());
        setSourcePersian(getDestinationPersian());
        setDestination(temp);
        setDestinationPersian(tempPersian);
    }

    public String getSourcePersian() {
        return sourcePersian;
    }

    public String getDestinationPersian() {
        return destinationPersian;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureGo() {
        return departureGo;
    }

    public String getType() {
        return type;
    }

    public String getDepartureGoPersian() {
        return departureGoPersian;
    }
    //-----------------------------------------------

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepartureGo(String departureGo) {
        this.departureGo = departureGo;
    }

    public void setDepartureGoPersian(String departureGoPersian) {
        this.departureGoPersian = departureGoPersian;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSourcePersian(String sourcePersian) {
        this.sourcePersian = sourcePersian;
    }

    public void setDestinationPersian(String destinationPersian) {
        this.destinationPersian = destinationPersian;
    }

    //-----------------------------------------------
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("source[]", source);
        hashMap.put("destination[]", destination);
        hashMap.put("DepartureGo", departureGo);
        hashMap.put("type", "fast");
        return hashMap;
    }
    //-----------------------------------------------
//-----------------------------------------------
    public HashMap<String, String> toHashMapFastFlight() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("source[]", source);
        hashMap.put("destination[]", destination);
        hashMap.put("DepartureGo", departureGo);
        hashMap.put("type", "fast");
        return hashMap;
    }

    //-----------------------------------------------
    public HashMap<String, String> toHashMapFlight() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("source[]", source);
        hashMap.put("destination[]", destination);
        hashMap.put("DepartureGo", departureGo);
        hashMap.put("type", "");
        return hashMap;
    }
}
