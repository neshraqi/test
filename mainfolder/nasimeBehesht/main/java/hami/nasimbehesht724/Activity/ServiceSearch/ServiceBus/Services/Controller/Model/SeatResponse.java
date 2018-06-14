package hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Model;

/**
 * Created by renjer on 2017-02-11.
 */

public class SeatResponse {

    private String capacity;
    private String col;
    private String floor;
    private String row;
    private SeatData seatData;
    //-----------------------------------------------
    public SeatResponse() {
    }
    //-----------------------------------------------

    public SeatResponse(String capacity, String col, String floor, String row, SeatData seatData) {
        this.capacity = capacity;
        this.col = col;
        this.floor = floor;
        this.row = row;
        this.seatData = seatData;
    }
    //-----------------------------------------------
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public void setSeatData(SeatData seatData) {
        this.seatData = seatData;
    }

    //-----------------------------------------------
    public String getCapacity() {
        return capacity;
    }

    public String getCol() {
        return col;
    }

    public String getFloor() {
        return floor;
    }

    public String getRow() {
        return row;
    }

    public SeatData getSeatData() {
        return seatData;
    }
}
