package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Station;
import models.Trip;

import java.io.IOException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

public class UserScreenController {
    @FXML
    private ChoiceBox fromBox;

    @FXML
    private ChoiceBox toBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField timeBox;

    @FXML
    private ChoiceBox departureOrArrival;

    @FXML
    private ChoiceBox passAmount;

    @FXML
    private Button settingsButton;

    @FXML
    private Button searchButton;

    private ObservableList<String> toStations;
    private ObservableList<String> fromStations;
    private ObservableList<String> depOrAr;
    private ObservableList<Integer> passengerAmount;

    private List<Trip> trips;
    private List<Trip> foundTrips;

    public void initialize(){

        populateDepOrArr();
        populatePassengerAmontBox();
        initDatePicker();
        initToFromBoxes();
        initSettingsButton();
    }

    /**
     * Checks if a station is included in a list of stations
     * @param station station whose inclusion is checked
     * @param stations list of stations
     * @return true if station in stations, false otherwise
     */
    public boolean isStationInList(Station station, List<String> stations){
        for(String s : stations){
            if(station.toString().equals(s)) return true;
        }
        return false;
    }

    public void populatePassengerAmontBox(){
        passengerAmount = FXCollections.observableArrayList();
        for(int i = 1; i < 11; i++){
            passengerAmount.add(i);
        }
        passAmount.setItems(passengerAmount);
    }

    public void populateDepOrArr(){
        depOrAr = FXCollections.observableArrayList();
        depOrAr.addAll("Departure", "Arrival");
        departureOrArrival.setItems(depOrAr);
    }

    public void initDatePicker(){
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }
    public void initToFromBoxes(){
        toStations = FXCollections.observableArrayList();
        fromStations = FXCollections.observableArrayList();

        //Populate the options in toBox and fromBox with stations in existing journeys
        try {
            trips = Client.dummyData.getTrips();
        }
        catch(RemoteException e){
            e.printStackTrace();
        }
        for(Trip trip : trips){
            Station to = trip.getConnection().getTo();
            Station from = trip.getConnection().getFrom();
            if(!isStationInList(to,toStations)) toStations.add(to.toString());
            if(!isStationInList(from,fromStations)) fromStations.add(from.toString());
        }
        fromBox.setItems(fromStations);
        toBox.setItems(toStations);

        // Update the options in toBox based on the fromBox selection
        fromBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<String> newToStations = FXCollections.observableArrayList();
                for(Trip trip : trips){
                    Station to = trip.getConnection().getTo();
                    Station from = trip.getConnection().getFrom();
                    if(!isStationInList(to,newToStations) && from.toString().equals(newValue)) newToStations.add(to.toString());
                }

                toBox.setItems(newToStations);
            }
        });
    }
    public void initSettingsButton(){
        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("/FXML/settings_screen.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Settings");
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(
                            ((Node)event.getSource()).getScene().getWindow() );
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initSearchButton(){
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!validateInput()) return;
//                search();
            }
        });
    }
    public boolean validateInput(){
        return true;
    }

//    public
}
