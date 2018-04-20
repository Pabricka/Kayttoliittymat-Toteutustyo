package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Journey;
import models.Station;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private Button searchButtom;

    private ObservableList<String> toStations;
    private ObservableList<String> fromStations;
    private ArrayList<Journey> journeys;

    public void initialize(){
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        toStations = FXCollections.observableArrayList();
        fromStations = FXCollections.observableArrayList();


        try {
            journeys = Client.dummyData.getJourneys();
        }
        catch(RemoteException e){
            e.printStackTrace();
        }
        for(Journey journey : journeys){
            Station to = journey.getConnection().getTo();
            Station from = journey.getConnection().getFrom();
            if(!isStationInList(to,toStations)) toStations.add(to.toString());
            if(!isStationInList(from,fromStations)) fromStations.add(from.toString());
        }
        fromBox.setItems(fromStations);
        toBox.setItems(toStations);

        fromBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<String> newToStations = FXCollections.observableArrayList();
                for(Journey journey : journeys){
                    Station to = journey.getConnection().getTo();
                    Station from = journey.getConnection().getFrom();
                    if(!isStationInList(to,newToStations) && from.toString().equals(newValue)) newToStations.add(to.toString());
                }

                toBox.setItems(newToStations);
            }
        });
    }
    public boolean isStationInList(Station station, List<String> stations){
        for(String s : stations){
            if(station.toString().equals(s)) return true;
        }
        return false;
    }

}
