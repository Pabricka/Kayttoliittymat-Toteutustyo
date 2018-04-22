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
        if(seatAmount > 20) seatAmount = 20;
        else if(seatAmount < 8) seatAmount = 8;
        this.name = name;
        this.seatAmount = seatAmount;
        this.seats = new ArrayList<>(seatAmount);
        this.forTheAllergic = forTheAllergic;
        this.wheelChairAccess = wheelChairAccess;
        this.petAllowed = petAllowed;
        this.familyCluster = familyCluster;

        this.populateCar();
    }


    public void populateCar(){
        for(int i = 0; i < seatAmount; i++){
            seats.add(new Seat(true, false, false, false, false));
        }
        if(familyCluster){
            seats.get(0).setFamilyCluster(true);
            seats.get(1).setFamilyCluster(true);
            seats.get(4).setFamilyCluster(true);
            seats.get(5).setFamilyCluster(true);

        }

        if(wheelChairAccess){
            seats.get(2).setWheelChairAccess(true);
            seats.get(3).setWheelChairAccess(true);
        }

        if(petAllowed && !forTheAllergic){
            seats.get(seats.size()-1).setPetAllowed(true);
            seats.get(seats.size()-2).setPetAllowed(true);
        }
        else if(forTheAllergic && !petAllowed){
            seats.get(seats.size()-1).setForTheAllergic(true);
            seats.get(seats.size()-2).setForTheAllergic(true);
        }
    }

    public ArrayList<Seat> getSeats(){
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
