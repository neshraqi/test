package hami.hamibelit.Activity.ServiceSearch.ServiceBus.Services.Controller.Model;

/**
 * Created by renjer on 2017-02-11.
 */

public class SeatRow {

    private String chairNumber;
    private int active;
    private int status;

    //-----------------------------------------------

    public SeatRow(String chairNumber, int active, int status) {
        this.chairNumber = chairNumber;
        this.active = active;
        this.status = status;
    }
    //-----------------------------------------------
    public SeatRow() {
    }
    //-----------------------------------------------

    public String getChairNumber() {
        return chairNumber;
    }

    public Boolean getActive() {
        if(active==1)
            return true;
        return false;
    }

    public int getStatus() {
        return status;
    }
}
