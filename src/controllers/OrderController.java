package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Car;
import models.Seat;
import models.Trip;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class OrderController {


    @FXML
    private Button nextCarBtn;

    @FXML
    private Button previousCarBtn;

    @FXML
    private Button settings;


    private ToggleGroup group;

    @FXML
    private RadioButton allRadio;
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


        if(cars.size() == 1){
            nextCarBtn.setDisable(true);
        }


        //Initialize Next Car button
        nextCarBtn.setOnAction(e ->{
            car++;
            if(group.getSelectedToggle() != allRadio) {
                CarPreviewController.drawCar(seats_grid, cars.get(car), false, allergicRadio.isSelected(), wheelRadio.isSelected(), petRadio.isSelected(), familyRadio.isSelected());
            }
            else {
                CarPreviewController.drawCar(seats_grid,cars.get(car),false,true,true,true,true);
            }
            if(car >= cars.size()-1){
                nextCarBtn.setDisable(true);
            }
            previousCarBtn.setDisable(false);
        });

        //and Previous Car Button
        previousCarBtn.setDisable(true);
        previousCarBtn.setOnAction(e->{
            car--;
            if(group.getSelectedToggle() != allRadio) {
                CarPreviewController.drawCar(seats_grid, cars.get(car), false, allergicRadio.isSelected(), wheelRadio.isSelected(), petRadio.isSelected(), familyRadio.isSelected());
            }
            else {
                CarPreviewController.drawCar(seats_grid,cars.get(car),false,true,true,true,true);
            }
            if(car == 0){
                previousCarBtn.setDisable(true);
            }
            nextCarBtn.setDisable(false);
        });


        //add seat criteria buttons to same group
        group = new ToggleGroup();
        allRadio.setToggleGroup(group);
        allergicRadio.setToggleGroup(group);
        familyRadio.setToggleGroup(group);
        petRadio.setToggleGroup(group);
        wheelRadio.setToggleGroup(group);

        //all seats are enabled by default
        allRadio.fire();
    }

    /**
     * Checks that the user has selected seats for every passenger, and if so, proceeds to payment screen
     */
    public void ProceedToPayment(){
        if(Client.session.getPassengers() == Client.session.getSelectedSeats().size()){
            try{
                controllers.Client.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/FXML/payment_screen.fxml"))));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No seat selected for every passenger");
            alert.setHeaderText("You have selected " + Client.session.getSelectedSeats().size() + " seats, but have " + Client.session.getPassengers() + " passengers!" );
            alert.setContentText("Please select more seats");
            alert.showAndWait();
        }
    }

    /**
     * Resets the selected seats and redraw car
     */
    public void ResetSelectedSeats(){

        //clear temporal reservations
        for(Car c : cars){
            for(Seat s : c.getSeats()){
                s.setTemporalReservation(false);
            }
        }
        //clear selected seats from session and redraw car
        Client.session.getSelectedSeats().clear();
        Client.session.getSelectedCars().clear();
        if(group.getSelectedToggle() != allRadio) {
            CarPreviewController.drawCar(seats_grid, cars.get(car), false, allergicRadio.isSelected(), wheelRadio.isSelected(), petRadio.isSelected(), familyRadio.isSelected());
        }
        else {
            CarPreviewController.drawCar(seats_grid,cars.get(car),false,true,true,true,true);
        }

    }


    /**
     * Return to trips screen
     */
    public void GoBack(){

        //Confirm selection
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirm that you want to go back");
        alert.setContentText("Are you sure you want to return to the trips screen?");
        Optional<ButtonType> result = alert.showAndWait();

        //If okay, go back to trips screen
        if (result.get() == ButtonType.OK) {
            try {
                Client.session.getSelectedSeats().clear();
                Client.session.getSelectedCars().clear();
                controllers.Client.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/FXML/display_trips_screen.fxml"))));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Opens settings screen
     */
    public void SettingsBtnClicked(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/FXML/settings_screen.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(settings.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Selecting this will allow every free seat to be selected
     */
    public void AllRadioSelected(){
        CarPreviewController.drawCar(seats_grid, cars.get(car), false, true, true, true, true);
    }

    /**
     * disable all but free allergy-friendly seats
     **/
    public void AllergicRadioSelected(){
        boolean available = false;
        for (Car c : cars) {
            if (c.isForTheAllergic()) {
                for (Seat s : c.getSeats()) {
                    if (s.isFree() && s.isForTheAllergic()) {
                        available = true;
                    }
                }
            }
        }
        if (!available) {
            allRadio.fire();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not found");
            alert.setHeaderText("No seats fulfilling the selected criteria");
            alert.setContentText("No free allergy-friendly seats in this train!");
            alert.showAndWait();
        }
        else {
            CarPreviewController.drawCar(seats_grid, cars.get(car), false, true, false, false, false);
        }
    }

    /**
     * disable all but free wheelchair seats
     **/
    public void WheelchairRadioSelected() {
        boolean available = false;
        for (Car c : cars) {
            if (c.isWheelChairAccess()) {
                for (Seat s : c.getSeats()) {
                    if (s.isFree() && s.isWheelChairAccess()) {
                        available = true;
                    }
                }
            }
        }

        if (!available) {
            allRadio.fire();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not found");
            alert.setHeaderText("No seats fulfilling the selected criteria");
            alert.setContentText("No free seats with wheelchair access in this train!");
            alert.showAndWait();
        }else {
        CarPreviewController.drawCar(seats_grid, cars.get(car), false, false, true, false, false);
    }

    }

    /**
     * disable all but free pet seats
     **/
    public void PetRadioSelected(){

        boolean available = false;
        for(Car c : cars){
            if(c.isPetAllowed()){
                for(Seat s : c.getSeats()){
                    if(s.isFree() && s.isPetAllowed()){
                        available = true;
                    }
                }
            }
        }
        if(!available) {
            allRadio.fire();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not found");
            alert.setHeaderText("No seats fulfilling the selected criteria");
            alert.setContentText("No free seats with pet allowed in this train!");
            alert.showAndWait();
        }
        else {
            CarPreviewController.drawCar(seats_grid, cars.get(car), false, false, false, true, false);
        }
    }

    /**
     * disable all but free family seats
     **/
    public void FamilyRadioSelected() {

        boolean available = false;
        for (Car c : cars) {
            if (c.isFamilyCluster()) {
                for (Seat s : c.getSeats()) {
                    if (s.isFree() && s.isFamilyCluster()) {
                        available = true;
                    }
                }
            }
            if (!available) {
                allRadio.fire();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not found");
                alert.setHeaderText("No seats fulfilling the selected criteria");
                alert.setContentText("No free family seats in this train!");
                alert.showAndWait();
            }
            else {
                CarPreviewController.drawCar(seats_grid, cars.get(car), false, false, false, false, true);
            }

        }

    }

}
