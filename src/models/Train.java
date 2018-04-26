package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Train implements Serializable {
    private String engine;

    private List<Car> cars;

    public Train(String engine, List<Car> cars) {
        this.engine = engine;
        this.cars = cars;

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
        int tmp = 0;
        for (Car car : cars) {
            tmp += car.getSeats().size();
        }
        return tmp;
    }
    public int getAllergicSeats(){
        int tmp =0;
        for (Car car : cars){
            if(car.isForTheAllergic()){
                tmp += 2;
            }
        }
        return tmp;
    }
    public int getFamilySeats(){
        int tmp =0;
        for (Car car : cars){
            if(car.isFamilyCluster()){
                tmp += 4;
            }
        }
        return tmp;
    }
    public int getPetSeats(){
        int tmp =0;
        for (Car car : cars){
            if(car.isPetAllowed()){
                tmp += 2;
            }
        }
        return tmp;
    }
    public int getWheelchairSeats(){
        int tmp =0;
        for (Car car : cars){
            if(car.isWheelChairAccess()){
                tmp += 2;
            }
        }
        return tmp;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void addCar(Car car) {
        cars.add(new Car(car.getName(), car.getSeatAmount(), car.isForTheAllergic(), car.isWheelChairAccess(), car.isPetAllowed(), car.isFamilyCluster()));
    }

}

