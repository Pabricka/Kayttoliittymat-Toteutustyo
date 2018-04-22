package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Train implements Serializable {
    private String engine;
    private int seats;
    private List<Car> cars;

    public Train(String engine, List<Car> cars) {
        this.engine = engine;
        this.cars = cars;

    }
    public Train(String engine){
        this.engine = engine;
        cars = new ArrayList<>();
    }

    public Train(String engine) {
        this.engine = engine;
        cars = new ArrayList<>();
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getSeats() {
<<<<<<< HEAD
        int tmp = 0;
        for (Car car : cars) {
            tmp += car.getSeats().size();
        }
        return tmp;
||||||| merged common ancestors
        return seats;
=======
        int tmp = 0;
        for(Car car : cars){
            tmp+=car.getSeats().size();
        }
        return tmp;
>>>>>>> 70d7212d36260bd1bd7c6fd7ac9f8ce1307c0fee
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
<<<<<<< HEAD

    public void addCar(Car car) {
        cars.add(car);
    }
}
||||||| merged common ancestors
}
=======
    public  void addCar(Car car){
        cars.add(car);
    }
}
>>>>>>> 70d7212d36260bd1bd7c6fd7ac9f8ce1307c0fee
