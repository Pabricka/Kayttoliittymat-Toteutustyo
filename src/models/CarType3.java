package models;

public class CarType3 extends Car {
    private String name = "CarType3";
    private int seatAmount = 20;
    private boolean forTheAllergic = false;
    private boolean wheelChairAccess = true;
    private boolean petAllowed = true;
    private boolean familyCluster = false;

    public CarType3() {
        super("CarType3", 20, false, true, true, false);
    }
}