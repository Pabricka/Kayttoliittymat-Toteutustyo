package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Car implements Serializable {
    private String name;
    private int seatAmount;
    private ArrayList<Seat> seats;
    private boolean forTheAllergic;
    private boolean wheelChairAccess;
    private boolean petAllowed;
    private boolean familyCluster;


    public Car(String name, int seatAmount, boolean forTheAllergic, boolean wheelChairAccess, boolean petAllowed, boolean familyCluster) {
        if (seatAmount > 20) seatAmount = 20;
        else if (seatAmount < 8) seatAmount = 8;
        this.name = name;
        this.seatAmount = seatAmount;
        this.seats = new ArrayList<>(seatAmount);
        this.forTheAllergic = forTheAllergic;
        this.wheelChairAccess = wheelChairAccess;
        this.petAllowed = petAllowed;
        this.familyCluster = familyCluster;

        this.populateCar();
    }


    public void populateCar() {
        for (int i = 0; i < seatAmount; i++) {
            seats.add(new Seat(true, false, false, false, false));
        }
        if (familyCluster) {
            seats.get(0).setFamilyCluster(true);
            seats.get(1).setFamilyCluster(true);
            seats.get(4).setFamilyCluster(true);
            seats.get(5).setFamilyCluster(true);

        }

        if (wheelChairAccess) {
            seats.get(2).setWheelChairAccess(true);
            seats.get(3).setWheelChairAccess(true);
        }

        if (petAllowed && !forTheAllergic) {
            seats.get(seats.size() - 1).setPetAllowed(true);
            seats.get(seats.size() - 2).setPetAllowed(true);
        } else if (forTheAllergic && !petAllowed) {
            seats.get(seats.size() - 1).setForTheAllergic(true);
            seats.get(seats.size() - 2).setForTheAllergic(true);
        }
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setSeatAmount(int s) {

        this.seats.clear();

        if (s > 20) s = 20;
        else if (s < 8) s = 8;
        seatAmount = s;
        populateCar();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatAmount() {
        return seatAmount;
    }

    public boolean isForTheAllergic() {
        return forTheAllergic;
    }

    public boolean isWheelChairAccess() {
        return wheelChairAccess;
    }

    public boolean isPetAllowed() {
        return petAllowed;
    }

    public boolean isFamilyCluster() {
        return familyCluster;
    }

    public void setForTheAllergic(boolean forTheAllergic) {
        this.forTheAllergic = forTheAllergic;
        seats.get(seats.size() - 1).setForTheAllergic(forTheAllergic);
        seats.get(seats.size() - 2).setForTheAllergic(forTheAllergic);
    }

    public void setWheelChairAccess(boolean wheelChairAccess) {
        this.wheelChairAccess = wheelChairAccess;
        seats.get(2).setWheelChairAccess(wheelChairAccess);
        seats.get(3).setWheelChairAccess(wheelChairAccess);
    }

    public void setPetAllowed(boolean petAllowed) {
        this.petAllowed = petAllowed;
        seats.get(seats.size() - 1).setPetAllowed(petAllowed);
        seats.get(seats.size() - 2).setPetAllowed(petAllowed);
    }

    public void setFamilyCluster(boolean familyCluster) {
        this.familyCluster = familyCluster;
        seats.get(0).setFamilyCluster(familyCluster);
        seats.get(1).setFamilyCluster(familyCluster);
        seats.get(4).setFamilyCluster(familyCluster);
        seats.get(5).setFamilyCluster(familyCluster);
        }
}