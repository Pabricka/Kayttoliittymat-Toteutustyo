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
    static  List<Car> carTypes;
    static private List<Train> trains;
    static private List<Car> cars;
    static private List<Connection> connections;
    static private Station[] stations;

    private int selectedTrain;
    private int selectedCar;
    private int selectedCarType;
    private static int selectedStation;
    private int selectedConnection;

    @FXML
    ListView<String> user_list;
    @FXML
    ListView<String> journey_list;
    @FXML
    ListView<String> trains_list;
    @FXML
    ListView<String> cars_list;
    @FXML
    ListView<String> stations_list;
    @FXML
    ListView<String> connections_list;


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

    @FXML
    Button editEngine;
    @FXML
    Button remove_car;
    @FXML
    Button add_car;

    @FXML
    Text engine;
    @FXML
    Text total_seats;
    @FXML
    Text allergic_seats;
    @FXML
    Text wheelchair_seats;
    @FXML
    Text pet_seats;
    @FXML
    Text family_clusters;

    @FXML
    TextField engine_field;

    @FXML
    Text station_text;

    @FXML
    Button add_connection;
    @FXML
    Button remove_connection;
    @FXML
    ChoiceBox<String> station_box;

    private ObservableList<String> toStations = FXCollections.observableArrayList();
    private ObservableList<String> fromStations = FXCollections.observableArrayList();



    static ObservableList<String> carTypes_list = FXCollections.observableArrayList();
    static ObservableList<String> train_items = FXCollections.observableArrayList();
    static ObservableList<String> car_items = FXCollections.observableArrayList();



    static ObservableList<String> items = FXCollections.observableArrayList();
    private static ObservableList<String>  journey_items = FXCollections.observableArrayList();

    static ObservableList<String> station_items = FXCollections.observableArrayList();
    static ObservableList<String> connection_items = FXCollections.observableArrayList();
    static ObservableList<String> connection_box_items = FXCollections.observableArrayList();



    @FXML
    public void initialize () {
        selectedTrain = -1;

        pick_date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });


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


        setlists();
        for(Train train : trains){
            train_items.add(train.getEngine());
        }
        for(Station station:stations){
            station_items.add(station.toString());
        }


        trains_list.setItems(train_items);
        user_list.setItems(items);
        journey_list.setItems(journey_items);
        stations_list.setItems(station_items);
        stations_list.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)-> {
            selectedStation = stations_list.getSelectionModel().getSelectedIndex();
            station_text.setText(stations[selectedStation].toString());
            connection_items.clear();
            for(Connection connection : connections){
                if(connection.getFrom().equals(stations[selectedStation])){
                    connection_items.add(connection.getTo().toString());
                }
            }
            connections_list.setItems(connection_items);


        });


        trains_list.setItems(train_items);
        trains_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedTrain = trains_list.getSelectionModel().getSelectedIndex();
            cars = trains.get(trains_list.getSelectionModel().getSelectedIndex()).getCars();
            car_items.clear();

            for(Car car:cars){
                car_items.add(car.getName());
            }

            cars_list.setItems(car_items);
            setSeatInfo();


            });
        cars_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                selectedCar = cars_list.getSelectionModel().getSelectedIndex();

                }
        );



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
            selectedCarType = carTypes_box.getSelectionModel().getSelectedIndex();
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
        setlists();
        items.clear();
        journey_items.clear();
        carTypes_list.clear();
        train_items.clear();
            for (User user : users) {
                items.add(user.getUsername());
            }
            for (Purchase journey : journeys) {
                journey_items.add(journey.getStrings());
            }
            for(Car car : carTypes){
                carTypes_list.add(car.getName());
            }
            for(Train train : trains){
                train_items.add(train.getEngine());
            }
            carTypes_list.add("New Type");

    }
    public void removeCarButtonClicked(){
        System.out.println(selectedTrain);
            System.out.println(selectedCar);
                    trains.get(selectedTrain).getCars().remove(selectedCar);

        cars = trains.get(selectedTrain).getCars();
        car_items.clear();

        for(Car car:cars){
            car_items.add(car.getName());
            System.out.println(car.getName());
        }
        setSeatInfo();
        try {
        Client.dummyData.setTrain(selectedTrain,trains.get(selectedTrain));
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public void addCarButtonClicked(){
        trains.get(selectedTrain).addCar(carTypes.get(selectedCarType));
        try {
            Client.dummyData.setTrain(selectedTrain,trains.get(selectedTrain));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cars = trains.get(selectedTrain).getCars();
        car_items.clear();

        for(Car car:cars){
            car_items.add(car.getName());
            System.out.println(car.getName());
        }
        setSeatInfo();

    }
    public void editEngineButtonClicked(){
        if(editEngine.getText().equals("Edit")){
            editEngine.setText("Ok");
            engine_field.setText(engine.getText());
            engine_field.setVisible(true);
        }

        else {
            engine_field.setVisible(false);
            editEngine.setText("Edit");
            trains.get(selectedTrain).setEngine(engine_field.getText());

            try {
                Client.dummyData.setTrain(selectedTrain, trains.get(selectedTrain));

            } catch (Exception e) {
                e.printStackTrace();
            }
            train_items.set(selectedTrain,engine_field.getText());
            setSeatInfo();


        }

    }
    public void setSeatInfo(){
        engine.setText(trains.get(selectedTrain).getEngine());
        int seats = trains.get(selectedTrain).getSeats();
        total_seats.setText(""+seats);
        int petSeats = trains.get(selectedTrain).getPetSeats();
        pet_seats.setText(""+petSeats);
        int familySeats = trains.get(selectedTrain).getFamilySeats();
        family_clusters.setText(""+familySeats);
        int allergicSeats = trains.get(selectedTrain).getAllergicSeats();
        allergic_seats.setText(""+allergicSeats);
        int wheelchairSeats = trains.get(selectedTrain).getWheelchairSeats();
        wheelchair_seats.setText(""+wheelchairSeats);

    }
    public void setlists(){
        try {
            users = Client.dummyData.getUsers();
            journeys = Client.dummyData.getPurchases();
            carTypes = Client.dummyData.getCarTypes();
            trains= Client.dummyData.getTrains();
            stations=Client.dummyData.getStations();
            connections = Client.dummyData.getConnections();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void addConnectionClicked(){



    }
    public static int getSelectedStation(){
        return selectedStation;
    }



}