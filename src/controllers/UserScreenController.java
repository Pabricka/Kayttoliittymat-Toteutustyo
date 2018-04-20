package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Station;

import java.time.LocalDate;

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

    ObservableList<String> stations;

    public void initialize(){
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        stations = FXCollections.observableArrayList();

        for(Station station : Station.values()){
            stations.add(station.toString());
        }
        fromBox.setItems(stations);
        toBox.setItems(stations);
    }

}
