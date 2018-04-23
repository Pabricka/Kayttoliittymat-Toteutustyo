package models;

import java.io.Serializable;

public class Purchase implements Serializable {

    private User buyer;
    private Trip trip;
    private int carNumber;
    private int seatNumber;

    public Purchase(User buyer, Trip trip, int carNumber, int seatNumber) {
        this.buyer = buyer;
        this.trip = trip;
        this.carNumber = carNumber;
        this.seatNumber = seatNumber;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    //TODO aseta matkaa, penkkiä tai vaunua vaihdettaessa vanha paikka vapaaksi, uusi varatuksi

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStrings() {
        String information = buyer.getName() + "  " + trip.getDepartureTime().toLocalDate().toString() + " " + trip.getConnection().getFrom().toString() + "  " + trip.getConnection().getTo().toString();
        return information;
    }
}