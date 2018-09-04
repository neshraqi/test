package hami.mainapp.flight.Adapter;

public class PassengerCounterFlight {
    private int adultCount;
    private int childCount;
    private int infantCount;

    public PassengerCounterFlight(int adultCount, int childCount, int infantCount) {
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.infantCount = infantCount;
    }
    //-----------------------------------------------

    public PassengerCounterFlight() {
        this.adultCount = 0;
        this.childCount = 0;
        this.infantCount = 0;
    }
    //-----------------------------------------------

    public int getAdultCount() {
        return adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public int getInfantCount() {
        return infantCount;
    }
    //-----------------------------------------------

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public void setInfantCount(int infantCount) {
        this.infantCount = infantCount;
    }
}
