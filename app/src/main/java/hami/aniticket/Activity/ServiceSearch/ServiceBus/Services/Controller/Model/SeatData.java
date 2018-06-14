package hami.aniticket.Activity.ServiceSearch.ServiceBus.Services.Controller.Model;

import java.util.List;



public class SeatData {
    private List<SeatRow> row1;
    private List<SeatRow> row2;
    private List<SeatRow> row3;
    private List<SeatRow> row4;
    //-----------------------------------------------

    public SeatData() {
        this.row1 = row1;
        this.row2 = row2;
        this.row3 = row3;
        this.row4 = row4;
    }

    //-----------------------------------------------

    public List<SeatRow> getRow1() {
        return row1;
    }

    public List<SeatRow> getRow2() {
        return row2;
    }

    public List<SeatRow> getRow3() {
        return row3;
    }

    public List<SeatRow> getRow4() {
        return row4;
    }
    //-----------------------------------------------

    public void setRow1(List<SeatRow> row1) {
        this.row1 = row1;
    }

    public void setRow2(List<SeatRow> row2) {
        this.row2 = row2;
    }

    public void setRow3(List<SeatRow> row3) {
        this.row3 = row3;
    }

    public void setRow4(List<SeatRow> row4) {
        this.row4 = row4;
    }
}
