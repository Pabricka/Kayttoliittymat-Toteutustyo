package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Connection;
import models.Station;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConnectionController {
    private ArrayList<Connection> connections;

    private Station[] stations;

    private int selectedStation;
    private int selectedDestination;

    @FXML
    ComboBox<String> stations_box;
    @FXML
    TextField departures_field;
    @FXML
    TextField length_field;
    @FXML
    TextField price_field;
    @FXML
    Button add_button;
    @FXML
    ListView<String> departures_list;
    @FXML
    Label check_label;
    @FXML
    Label error_label;
    @FXML
    Button cancel_button;
    @FXML
    Button confirm_button;

    ObservableList<String> departure_items = FXCollections.observableArrayList();
    ObservableList<String> station_items = FXCollections.observableArrayList();
    @FXML
    public void initialize(){

        selectedDestination = -1;
        selectedStation = -1;

        try {
            connections = Client.dummyData.getConnections();
            stations = Client.dummyData.getStations();
        }catch (Exception e){
            e.printStackTrace();
        }
        selectedStation = AdminController.getSelectedStation();
        for(Station station: stations) {
            if (!(station.equals(stations[selectedStation]))) {
                for (Connection connection : connections) {
                    if ((connection.getFrom().equals(stations[selectedStation]))&&!(connection.getTo().equals(station))) {

                        station_items.add(station.toString());
                        break;
                    }
                }
            }
        }


        stations_box.setItems(station_items);
        stations_box.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedDestination = stations_box.getSelectionModel().getSelectedIndex();
        });


        }
        public void addClicked(){
        String time = departures_field.getText();
        if((time.charAt(2)==':')&&(time.length()==5)&&(isNumeric(time.substring(0,2)))&&(isNumeric(time.substring(3)))){
            String hours = time.substring(0,2);
            String minutes = time.substring(3);
            if((Integer.parseInt(hours)<24)&&(Integer.parseInt(minutes)<60)){
            departure_items.add(time);
            check_label.setVisible(false);
            departures_field.setText("");
        }
        }
        else{
            check_label.setVisible(true);
        }
        departures_list.setItems(departure_items);

        }
        public void confirmClicked(){
        ArrayList<LocalTime> departs = new ArrayList<>();
        String length = length_field.getText();
        String price = price_field.getText();
        System.out.println(selectedDestination);
        System.out.println(selectedStation);


        if((selectedDestination == -1)||(station_items.size()==0)||!(length.length()==5)||!(length.charAt(2)==':')||(Integer.parseInt(length.substring(3))>59)||!(isNumeric(price))){
            error_label.setVisible(true);
        }
        else{
            for(String string: departure_items){
                departs.add(LocalTime.parse(string));
                }
            Connection con = new Connection(stations[selectedStation],stations[searchStation()],Integer.parseInt(price),LocalTime.parse(length));
            try{
                con.setTimes(departs);
                Client.dummyData.addConnection(con);

            }catch (Exception e){
                e.printStackTrace();
            }
            AdminController.connection_items.add(con.getTo().toString());
            Stage stage = (Stage) confirm_button.getScene().getWindow();
            stage.close();
        }


        }
        public void cancelClicked(){
            Stage stage = (Stage) confirm_button.getScene().getWindow();
            stage.close();
        }
    public static boolean isNumeric(String str) {
        try {
            int i = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe) {
            System.out.println("false");
            return false;
        }
        System.out.println("true");
        return true;
    }
    private int searchStation(){
        int i = 0;
        for(Station station: stations){
            if(station.toString().equals(station_items.get(selectedDestination))){
                return i;
            }
            i++;
        }
        System.out.println("FAIL");
        return -1;
    }


    }


