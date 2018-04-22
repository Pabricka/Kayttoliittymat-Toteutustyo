package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Trip {
    private Train train;
    private Connection connection;
    private LocalDate date;
    private LocalTime departureTime;

    public Trip(Train train, Connection connection, LocalDate date, LocalTime departureTime) {
        this.train = train;
        this.connection = connection;
        this.date = date;
        this.departureTime = departureTime;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }
}
