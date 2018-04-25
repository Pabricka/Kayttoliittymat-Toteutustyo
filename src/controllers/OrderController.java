package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import models.Car;
import models.Seat;
import models.Trip;

import java.util.List;

public class OrderController {


    @FXML
    private Button nextCarBtn;

    @FXML
    private Button previousCarBtn;

    @FXML
    private RadioButton allergicRadio;

    @FXML
    private RadioButton wheelRadio;

    @FXML
    private RadioButton petRadio;

    @FXML
    private RadioButton familyRadio;

    @FXML
    private GridPane seats_grid;

    private Trip trip;
    private List<Car> cars;
    private int car;

    public void initialize() {
        trip = Client.session.getSelectedTrip();
        cars = trip.getTrain().getCars();
        car = 0;
        CarPreviewController.drawCar(seats_grid, cars.get(car), false);


        if(cars.size() == 1){
            nextCarBtn.setDisable(true);
        }

        nextCarBtn.setOnAction(e ->{
            car++;
            CarPreviewController.drawCar(seats_grid, cars.get(car), false);
            if(car >= cars.size()-1){
                nextCarBtn.setDisable(true);
            }
            previousCarBtn.setDisable(false);
        });

        previousCarBtn.setDisable(true);
        previousCarBtn.setOnAction(e->{
            car--;
            CarPreviewController.drawCar(seats_grid, cars.get(car), false);
            if(car == 0){
                previousCarBtn.setDisable(true);
            }
            nextCarBtn.setDisable(false);
        });

    }

    public void ResetSelectedSeats(){

        //clear temporal reservations
        for(Car c : cars){
            for(Seat s : c.getSeats()){
                s.setTemporalReservation(false);
            }
        }
        Client.session.getSelectedSeats().clear();
        Client.session.getSelectedCars().clear();
        CarPreviewController.drawCar(seats_grid, cars.get(car),false);
    }


}
