package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Trip implements Comparable<Trip>, Serializable {
    private Train train;
    private Connection connection;
    private LocalDateTime departureTime;

    public Trip(Train train, Connection connection, LocalDate date, LocalTime time) {
        this.train = train;
        this.connection = connection;
        this.departureTime = LocalDateTime.of(date, time);

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

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public int compareTo(Trip o) {
        return(departureTime.compareTo(o.departureTime));
    }

    public boolean hasSpace(int amount){
        int free = 0;
        List<Seat> seats;
        List<Car> cars = train.getCars();
        for(Car car : cars){
            seats = car.getSeats();
            for(Seat seat : seats){
                if(seat.isFree()){
                    free++;
                }
            }
        }
        System.out.println(free);
        if(free >= amount) return true;
        else return false;
    }

    public String toString(){
        return departureTime.toString() + " " + connection.getFrom().toString() + " " + connection.getTo().toString();
    }
}
