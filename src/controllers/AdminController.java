package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AdminController {

    static private List<User> users;
    static private List<Purchase> journeys;
    static List<Car> carTypes;

    @FXML
    ListView<String> user_list;
    @FXML
    ListView<String> journey_list;

    @FXML
    Text username_text;

    @FXML
    Text name_text;

    @FXML
    Text password_text;

    @FXML
    Text u_text;

    @FXML
    Text n_text;

    @FXML
    Text p_text;

    @FXML
    Button u_button;
    @FXML
    Button n_button;
    @FXML
    Button p_button;

    @FXML
    TextField u_field;
    @FXML
    TextField n_field;
    @FXML
    TextField p_field;

    @FXML
    ComboBox<String> sort_box;
    @FXML
    ChoiceBox<String> carTypes_box;

    @FXML
    Label from_label;
    @FXML
    Label to_label;
    @FXML
    Text from_text;
    @FXML
    Text to_text;
    @FXML
    ChoiceBox from_choice;
    @FXML
    ChoiceBox to_choice;
    @FXML
    Button edit_connection;
    @FXML
    Text date_text;
    @FXML
    DatePicker pick_date;
    @FXML
    Button edit_date;
    @FXML
    Label date_label;

    private ObservableList<String> toStations = FXCollections.observableArrayList();
    private ObservableList<String> fromStations = FXCollections.observableArrayList();



    static ObservableList<String> carTypes_list = FXCollections.observableArrayList();



    static ObservableList<String> items = FXCollections.observableArrayList();
    private static ObservableList<String>  journey_items = FXCollections.observableArrayList();



    @FXML
    public void initialize () {

        pick_date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        toStations = FXCollections.observableArrayList();
        fromStations = FXCollections.observableArrayList();


        sort_box.getItems().removeAll(sort_box.getItems());
        sort_box.getItems().addAll("user", "date", "connection");
        sort_box.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (sort_box.getSelectionModel().getSelectedItem().equals("connection")) {

                journeys.sort(Comparator.comparing(o -> o.getTrip().getConnection().getFrom().toString()));
                journey_items = FXCollections.observableArrayList();
                for (Purchase journey : journeys) {
                    journey_items.add(journey.getStrings());
                }

                journey_list.setItems(journey_items);
            } else if (sort_box.getSelectionModel().getSelectedItem().equals("user")) {


                journeys.sort(Comparator.comparing(o -> o.getBuyer().getName()));
                journey_items = FXCollections.observableArrayList();
                for (Purchase journey : journeys) {
                    journey_items.add(journey.getStrings());
                }

                journey_list.setItems(journey_items);

            } else {


                journeys.sort(Comparator.comparing(o -> o.getTrip().getDepartureTime()));
                journey_items = FXCollections.observableArrayList();
                for (Purchase journey : journeys) {
                    journey_items.add(journey.getStrings());
                }

                journey_list.setItems(journey_items);

            }
        });


        try {
            users = Client.dummyData.getUsers();
            journeys = Client.dummyData.getPurchases();
            carTypes = Client.dummyData.getCarTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        user_list.setItems(items);
        journey_list.setItems(journey_items);
        user_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    u_text.setText("Username: ");
                    n_text.setText("Name: ");
                    p_text.setText("Password: ");
                    username_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getUsername());
                    name_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getName());
                    password_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getPassword());
                    u_button.setVisible(true);
                    n_button.setVisible(true);
                    p_button.setVisible(true);

                }
        );
        journey_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            from_label.setVisible(true);
            to_label.setVisible(true);
            date_label.setVisible(true);
            edit_connection.setVisible(true);
            edit_date.setVisible(true);
            from_text.setText(journeys.get(journey_list.getSelectionModel().getSelectedIndex()).getTrip().getConnection().getFrom().toString());
            to_text.setText(journeys.get(journey_list.getSelectionModel().getSelectedIndex()).getTrip().getConnection().getTo().toString());
            date_text.setText(journeys.get(journey_list.getSelectionModel().getSelectedIndex()).getTrip().getDepartureTime().toLocalDate().toString());
        });
        for (Purchase journey : journeys) {
            Station to = journey.getTrip().getConnection().getTo();
            Station from = journey.getTrip().getConnection().getFrom();
            if (!isStationInList(to, toStations)) toStations.add(to.toString());
            if (!isStationInList(from, fromStations)) fromStations.add(from.toString());

        }
        carTypes_box.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue.equals("New Type")){
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource("/FXML/car_preview.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("New Car Type Preview");
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(Client.adminScreen.getWindow());
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        carTypes_box.setItems(carTypes_list);
        from_choice.setItems(fromStations);
        to_choice.setItems(toStations);

    }

    private boolean isStationInList(Station station, List<String> stations){
        for(String s : stations){
            if(station.toString().equals(s)) return true;
        }
        return false;
    }
    public void u_buttonClicked(){

        if(u_button.getText().equals("Edit")){
            u_button.setText("Ok");
            u_field.setText(username_text.getText());
            u_field.setVisible(true);
        }

        else {
            u_button.setText("Edit");

            //change the users username in database
            try {
                Client.dummyData.changeUsername(users.get(user_list.getSelectionModel().getSelectedIndex()).getUsername(), u_field.getText());
                users = Client.dummyData.getUsers();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            username_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getUsername());
            items.set(user_list.getSelectionModel().getSelectedIndex(),u_field.getText());
            u_field.setVisible(false);

        }



    }
    public void n_buttonClicked(){
        if(n_button.getText().equals("Edit")){
            n_button.setText("Ok");
            n_field.setText(name_text.getText());
            n_field.setVisible(true);
        }

        else {

            n_button.setText("Edit");

            //change the users name in database
            try {
                Client.dummyData.changeName(users.get(user_list.getSelectionModel().getSelectedIndex()).getUsername(), n_field.getText());
                users = Client.dummyData.getUsers();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            name_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getName());
            n_field.setVisible(false);

        }



    }
    public void p_buttonClicked(){
        if(p_button.getText().equals("Edit")){
            p_button.setText("Ok");
            p_field.setText(password_text.getText());
            p_field.setVisible(true);
        }

        else {

            p_button.setText("Edit");
            //change the users password in database
            try {
                Client.dummyData.changePassword(users.get(user_list.getSelectionModel().getSelectedIndex()).getUsername(), p_field.getText());
                users = Client.dummyData.getUsers();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            password_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getPassword());
            p_field.setVisible(false);

        }


    }
    public void edit_connection_buttonClicked(){
        if (edit_connection.getText().equals("Edit")){
            edit_connection.setText("Ok");
            from_choice.setVisible(true);
            to_choice.setVisible(true);
        }
        else {
            edit_connection.setText("Edit");
            from_choice.setVisible(false);
            to_choice.setVisible(false);

        }

    }
    public void edit_date_buttonClicked(){
        if(edit_date.getText().equals("Edit")){
            edit_date.setText("Ok");
            pick_date.setVisible(true);
        }
        else{
            edit_date.setText("Edit");
            pick_date.setVisible(false);
        }

    }

    public void onTabChange(){
        try {
            users = Client.dummyData.getUsers();
            journeys = Client.dummyData.getPurchases();
            carTypes = Client.dummyData.getCarTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        items.clear();
        journey_items.clear();
        carTypes_list.clear();
            for (User user : users) {
                items.add(user.getUsername());
            }
            for (Purchase journey : journeys) {
                journey_items.add(journey.getStrings());
            }
            for(Car car : carTypes){
                carTypes_list.add(car.getName());
            }
            carTypes_list.add("New Type");

    }



}