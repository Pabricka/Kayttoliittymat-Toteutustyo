package controllers;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.Purchase;
import models.Trip;
import models.Station;
import models.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AdminController {
    ArrayList<User> users;
    ArrayList<Purchase> journeys;

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

    private ObservableList<String> toStations;
    private ObservableList<String> fromStations;






    static ObservableList<String> items;
    static  ObservableList<String>  journey_items;


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
            if (sort_box.getSelectionModel().getSelectedItem() == "connection") {

                journeys.sort(Comparator.comparing(o -> o.getTrip().getConnection().getFrom().toString()));
                journey_items = FXCollections.observableArrayList();
                for (Purchase journey : journeys) {
                    journey_items.add(journey.getStrings());
                }

                journey_list.setItems(journey_items);
            } else if (sort_box.getSelectionModel().getSelectedItem() == "user") {


                journeys.sort(Comparator.comparing(o -> o.getBuyer().getName()));
                journey_items = FXCollections.observableArrayList();
                for (Purchase journey : journeys) {
                    journey_items.add(journey.getStrings());
                }

                journey_list.setItems(journey_items);

            } else {


                journeys.sort(Comparator.comparing(o -> o.getTrip().getDate()));
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
        } catch (Exception e) {
            e.printStackTrace();
        }


        items = FXCollections.observableArrayList();
        journey_items = FXCollections.observableArrayList();
        for (User user : users) {
            items.add(user.getUsername());
        }
        for (Purchase journey : journeys) {
            journey_items.add(journey.getStrings());
        }
        user_list.setItems(items);
        journey_list.setItems(journey_items);
        user_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("ListView selection changed from oldValue = "
                    + oldValue + " to newValue = " + newValue);
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
            System.out.println("ListView selection changed from oldValue = "
                    + oldValue + " to newValue = " + newValue);
            from_label.setVisible(true);
            to_label.setVisible(true);
            date_label.setVisible(true);
            edit_connection.setVisible(true);
            edit_date.setVisible(true);
            System.out.println(journey_list.getSelectionModel().getSelectedIndex());
            from_text.setText(journeys.get(journey_list.getSelectionModel().getSelectedIndex()).getTrip().getConnection().getFrom().toString());
            to_text.setText(journeys.get(journey_list.getSelectionModel().getSelectedIndex()).getTrip().getConnection().getTo().toString());
            date_text.setText(journeys.get(journey_list.getSelectionModel().getSelectedIndex()).getTrip().getDate().toString());
        });
        for (Purchase journey : journeys) {
            Station to = journey.getTrip().getConnection().getTo();
            Station from = journey.getTrip().getConnection().getFrom();
            if (!isStationInList(to, toStations)) toStations.add(to.toString());
            if (!isStationInList(from, fromStations)) fromStations.add(from.toString());

        }
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
                Client.dummyData.changeUsername(user_list.getSelectionModel().getSelectedIndex(), u_field.getText());
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
                Client.dummyData.changeName(user_list.getSelectionModel().getSelectedIndex(), n_field.getText());
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
                Client.dummyData.changePassword(user_list.getSelectionModel().getSelectedIndex(), p_field.getText());
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



}