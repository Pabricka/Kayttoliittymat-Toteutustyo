package models;

import java.util.ArrayList;
import java.util.List;

public class Train {
    private String engine;
    private int seats;
    private List<Car> cars;

    public Train(String engine, List<Car> cars){
        this.engine = engine;
        this.cars = cars;

    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
