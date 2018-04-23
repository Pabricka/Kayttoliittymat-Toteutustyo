package models;

import java.util.List;

public class Session {
    private User user;
    private int passengers;
    private List<Trip> foundTrips;
    private Trip selectedTrip;
    private List<Integer> selectedSeats;
    private List<Integer> selectedCars;

    public Session() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public List<Trip> getFoundTrips() {
        return foundTrips;
    }

    public void setFoundTrips(List<Trip> foundTrips) {
        this.foundTrips = foundTrips;
    }

    public Trip getSelectedTrip() {
        return selectedTrip;
    }

    public void setSelectedTrip(Trip selectedTrip) {
        this.selectedTrip = selectedTrip;
    }

    public List<Integer> getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(List<Integer> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public List<Integer> getSelectedCars() {
        return selectedCars;
    }

    public void setSelectedCars(List<Integer> selectedCars) {
        this.selectedCars = selectedCars;
    }
}
