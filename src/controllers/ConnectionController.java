package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import models.Connection;
import models.Station;

import java.util.ArrayList;

public class ConnectionController {
    private ArrayList<Connection> connections;
    private Station[] stations;

    @FXML
    ComboBox<String> stations_box;
    @FXML
    TextField departures_field;
    @FXML
    TextField length_field;
    @FXML
    Button add_button;
    @FXML
    ListView<String> departures_list;
    @FXML
    Label check_label;
    @FXML
    Text error_text;
    @FXML
    Button cancel_button;
    @FXML
    Button confirm_button;

    ObservableList<String> departure_items = FXCollections.observableArrayList();
    ObservableList<String> station_items = FXCollections.observableArrayList();
    @FXML
    public void initialize(){

        try {
            connections = Client.dummyData.getConnections();
            stations = Client.dummyData.getStations();
        }catch (Exception e){
            e.printStackTrace();
        }
        for(Station station: stations){
            if(!(station.equals(stations[AdminController.getSelectedStation()]))){}
        }


        }

    }


