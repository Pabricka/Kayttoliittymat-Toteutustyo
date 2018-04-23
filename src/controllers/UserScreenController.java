package controllers;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Connection;
import models.Station;
import models.Trip;

import java.io.IOException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static controllers.Client.dummyData;

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

    @FXML
    private GridPane grid;

    private ObservableList<String> toStations;
    private ObservableList<String> fromStations;
    private ObservableList<String> depOrAr;
    private ObservableList<Integer> passengerAmount;

    private List<Trip> trips;
    private List<Trip> foundTrips;

    private String timeString;

    private int amountOfPassengers;

    public void initialize() {

        populateDepOrArr();
        populatePassengerAmontBox();
        initEnterToGo();
        initDatePicker();
        initToFromBoxes();
        initSettingsButton();
        initSearchButton();
    }

    public void initEnterToGo() {
        grid.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                searchButton.fire();
                ev.consume();
            }
        });
    }

    public void initDatePicker() {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }

    public void initToFromBoxes() {
        toStations = FXCollections.observableArrayList();
        fromStations = FXCollections.observableArrayList();

        //Populate the options in toBox and fromBox with stations in existing journeys
        try {
            trips = dummyData.getTrips();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (Trip trip : trips) {
            Station to = trip.getConnection().getTo();
            Station from = trip.getConnection().getFrom();
            if (!isStationInList(to, toStations)) toStations.add(to.toString());
            if (!isStationInList(from, fromStations)) fromStations.add(from.toString());
        }
        fromBox.setItems(fromStations);
        toBox.setItems(toStations);

        // Update the options in toBox based on the fromBox selection
        fromBox.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
            ObservableList<String> newToStations = FXCollections.observableArrayList();
            for (Trip trip : trips) {
                Station to = trip.getConnection().getTo();
                Station from = trip.getConnection().getFrom();
                if (!isStationInList(to, newToStations) && from.toString().equals(newValue))
                    newToStations.add(to.toString());
            }

            toBox.setItems(newToStations);
        });
    }

    public void initSettingsButton() {
        settingsButton.setOnAction(event -> {
            Parent root;
            System.out.println(Client.currentUser);
            try {
                root = FXMLLoader.load(getClass().getResource("/FXML/settings_screen.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Settings");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(
                        ((Node) event.getSource()).getScene().getWindow());
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void initSearchButton() {
        searchButton.setOnAction(event -> {
            if (!validateInput()) return;
            foundTrips = searchForTrips();

            if (foundTrips.size() != 0) {
                try {
                    Client.session.setFoundTrips(foundTrips);
                    Client.session.setPassengers(amountOfPassengers);
                    controllers.Client.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/FXML/display_trips_screen.fxml"))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Couldn't find any trips to match criteria!");
                alert.setContentText("Please try to change your criteria.");
                alert.showAndWait();
            }
        });

    }

    /**
     * Checks if a station is included in a list of stations
     *
     * @param station  station whose inclusion is checked
     * @param stations list of stations
     * @return true if station in stations, false otherwise
     */
    public boolean isStationInList(Station station, List<String> stations) {
        for (String s : stations) {
            if (station.toString().equals(s)) return true;
        }
        return false;
    }

    public void populatePassengerAmontBox() {
        passengerAmount = FXCollections.observableArrayList();
        for (int i = 1; i < 11; i++) {
            passengerAmount.add(i);
        }
        passAmount.setItems(passengerAmount);
    }

    public void populateDepOrArr() {
        depOrAr = FXCollections.observableArrayList();
        depOrAr.addAll("Departure", "Arrival");
        departureOrArrival.setItems(depOrAr);
    }

    public boolean validateInput() {

        if (!areAllFilled()) return false;
        if (!validateTime()) return false;
        return true;
    }

    public boolean areAllFilled() {
        if (fromBox.getValue() == null || toBox.getValue() == null || passAmount.getValue() == null ||
                datePicker.getValue() == null || timeBox.getText() == null || departureOrArrival.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("All fields not filled out!");
            alert.setContentText("Make sure to fill all the fields!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public boolean validateTime() {

        boolean valid;
        timeString = timeBox.getText().replaceAll("[^0-9]", "");
        if (timeString.length() < 3) {
            valid = false;
        } else {
            if (timeString.length() == 3) {
                timeString = "0" + timeString;
            }
            timeString = timeString.substring(0, 2) + ":" + timeString.substring(2, 4);
            if (timeString.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]")) {
                valid = true;
            } else {
                valid = false;
            }
        }

        if (!valid) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Error in reading time");
            alert.setContentText("Check your time input!");
            alert.showAndWait();
            return false;
        }
        return valid;
    }

    public List<Trip> searchForTrips() {

        List<Trip> foundTrips = new ArrayList<>();
        boolean departure;
        amountOfPassengers = Integer.parseInt(passAmount.getValue().toString());

        if (departureOrArrival.getValue().equals("Departure")) departure = true;
        else departure = false;

        try {
            List<Trip> trips = dummyData.getTrips();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate date = datePicker.getValue();
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        for (Trip trip : trips) {
            if (connectionMatchesSelection(trip.getConnection()) && isAfterTime(trip, dateTime, departure) && trip.hasSpace(amountOfPassengers)) {
                foundTrips.add(trip);
            }
        }

        Collections.sort(foundTrips);
        return foundTrips;
    }

    public boolean connectionMatchesSelection(Connection connection) {
        String from = fromBox.getValue().toString();
        String to = toBox.getValue().toString();

        if (connection.getFrom().toString().equals(from) && connection.getTo().toString().equals(to)) {
            return true;
        }
        return false;
    }

    public boolean isAfterTime(Trip trip, LocalDateTime dateTime, boolean departure) {
        LocalDateTime tripTime = trip.getDepartureTime();
        if (!departure) {
            tripTime = tripTime.plusHours(trip.getConnection().getLength().getHour()).plusMinutes(trip.getConnection().getLength().getMinute());
        }
        if (tripTime.toLocalDate().equals(dateTime.toLocalDate()) && tripTime.compareTo(dateTime) >= 0) {
            return true;
        }
        return false;
    }
}
