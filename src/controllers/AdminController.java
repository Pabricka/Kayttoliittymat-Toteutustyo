package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class AdminController {

    static private List<User> users;
    static private List<Purchase> journeys;
    static  List<Car> carTypes;
    static private List<Train> trains;
    static private List<Car> cars;
    static private ArrayList<Connection> connections;
    static private Station[] stations;

    private int selectedTrain;
    private int selectedCar;
    private int selectedCarType;
    private static int selectedStation;
    private int selectedUser;
    private int selectedJourney;
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
    Text address_text;

    @FXML
    Button edit_username_button;
    @FXML
    Button edit_name_button;
    @FXML
    Button edit_password_button;
    @FXML
    Button edit_address_button;

    @FXML
    TextField username_field;
    @FXML
    TextField name_field;
    @FXML
    TextField password_field;
    @FXML
    TextField address_field;

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
    Text date_text;


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
    Button delete_journey;
    @FXML
    Text train_capacity;
    @FXML
    Text engine_text;
    @FXML
    Text time_text;
    @FXML
    Button userview_button;




    static ObservableList<String> carTypes_list = FXCollections.observableArrayList();
    static ObservableList<String> train_items = FXCollections.observableArrayList();
    static ObservableList<String> car_items = FXCollections.observableArrayList();



    static ObservableList<String> items = FXCollections.observableArrayList();
    private static ObservableList<String>  journey_items = FXCollections.observableArrayList();

    static ObservableList<String> station_items = FXCollections.observableArrayList();
    static ObservableList<String> connection_items = FXCollections.observableArrayList();



    @FXML
    public void initialize () {
        selectedTrain = -1;
        selectedUser = -1;


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
            selectedUser = user_list.getSelectionModel().getSelectedIndex();
                    username_text.setText(users.get(selectedUser).getUsername());
                    name_text.setText(users.get(selectedUser).getName());
                    password_text.setText(users.get(selectedUser).getPassword());
                    address_text.setText(users.get(selectedUser).getAddress());
                    edit_username_button.setVisible(true);
                    edit_name_button.setVisible(true);
                    edit_password_button.setVisible(true);
                    edit_address_button.setVisible(true);

                }
        );
        journey_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedJourney = journey_list.getSelectionModel().getSelectedIndex();
            delete_journey.setVisible(true);
            from_text.setText(journeys.get(selectedJourney).getTrip().getConnection().getFrom().toString());
            to_text.setText(journeys.get(selectedJourney).getTrip().getConnection().getTo().toString());
            date_text.setText(journeys.get(selectedJourney).getTrip().getDepartureTime().toLocalDate().toString());
            train_capacity.setText(calculateSeats());
            time_text.setText(journeys.get(selectedJourney).getTrip().getDepartureTime().toLocalTime().toString());
            engine_text.setText(journeys.get(selectedJourney).getTrip().getTrain().getEngine());

        });

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
        connections_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedConnection = connections_list.getSelectionModel().getSelectedIndex();
        });

    }

    private boolean isStationInList(Station station, List<String> stations){
        for(String s : stations){
            if(station.toString().equals(s)) return true;
        }
        return false;
    }
    public void editUsernameButtonClicked(){

        if(edit_username_button.getText().equals("Edit")){
            edit_username_button.setText("Ok");
            username_field.setText(username_text.getText());
            username_field.setVisible(true);
        }

        else {
            edit_username_button.setText("Edit");

            //change the users username in database
            try {
                Client.dummyData.changeUsername(users.get(user_list.getSelectionModel().getSelectedIndex()).getUsername(), username_field.getText());
                users = Client.dummyData.getUsers();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            username_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getUsername());
            items.set(user_list.getSelectionModel().getSelectedIndex(), username_field.getText());
            username_field.setVisible(false);

        }



    }
    public void editNameButtonClicked(){
        if(edit_name_button.getText().equals("Edit")){
            edit_name_button.setText("Ok");
            name_field.setText(name_text.getText());
            name_field.setVisible(true);
        }

        else {

            edit_name_button.setText("Edit");

            //change the users name in database
            try {
                Client.dummyData.changeName(users.get(user_list.getSelectionModel().getSelectedIndex()).getUsername(), name_field.getText());
                users = Client.dummyData.getUsers();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            name_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getName());
            name_field.setVisible(false);

        }



    }
    public void editPasswordButtonClicked(){
        if(edit_password_button.getText().equals("Edit")){
            edit_password_button.setText("Ok");
            password_field.setText(password_text.getText());
            password_field.setVisible(true);
        }

        else {

            edit_password_button.setText("Edit");
            //change the users password in database
            try {
                Client.dummyData.changePassword(users.get(selectedUser).getUsername(), password_field.getText());
                users = Client.dummyData.getUsers();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            password_text.setText(users.get(user_list.getSelectionModel().getSelectedIndex()).getPassword());
            password_field.setVisible(false);

        }


    }
    public void editAddressButtonClicked(){
        if(edit_address_button.getText().equals("Edit")){
            edit_address_button.setText("Ok");
            address_field.setText(address_text.getText());
            address_field.setVisible(true);
        }

        else {

            edit_address_button.setText("Edit");
            //change the users password in database
            try {
                Client.dummyData.changeAddress(users.get(selectedUser).getUsername(), address_field.getText());
                users = Client.dummyData.getUsers();  
            }
            catch (Exception e){
                e.printStackTrace();
            }
            address_text.setText(users.get(selectedUser).getAddress());
            address_field.setVisible(false);

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
    public static void setlists(){
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
    public void addConnectionClicked() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/FXML/add_connection_screen.fxml"));
            Stage stage = new Stage();
            stage.setTitle("New Connection");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(Client.adminScreen.getWindow());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public void removeConnectionClicked(){

            String selection = connection_items.get(selectedConnection);
            int index = searchConnectionIndex(selection);

            try{
                Client.dummyData.removeConnection(index);
            }catch (Exception e) {
                e.printStackTrace();
            }
            connection_items.remove(selectedConnection);
            connections_list.setItems(connection_items);
            setlists();

        }


    public static int getSelectedStation(){
        return selectedStation;
    }
    private int searchConnectionIndex(String s){
        int i = 0;
        for(Connection connection : connections){
            if(connection.getFrom().equals(stations[selectedStation]) && connection.getTo().toString().equals(s)){
                return i;
            }
            i++;
        }
        return -1;

    }
    public void deleteJourneyClicked(){
        journey_items.remove(selectedJourney);
        journey_list.setItems(journey_items);
        try{
            Client.dummyData.removeJourney(selectedJourney);
        }catch (Exception e){
            e.printStackTrace();
        }
        setlists();

    }
    public String calculateSeats(){
        int totalSeats = 0;
        int reserved = 0;
        List<Car> cars = journeys.get(selectedJourney).getTrip().getTrain().getCars();
        ArrayList<Seat> seats = new ArrayList<>();
        for(Car car:cars){
            totalSeats += car.getSeatAmount();
            seats = car.getSeats();
            for(Seat seat : seats){
                if(!seat.isFree()){
                    reserved++;
                }
            }
        }
        return ""+ reserved + "/" + totalSeats;

    }
    public void goToUserScreen(){
        try{controllers.Client.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/FXML/user_screen.fxml"))));
        }catch (Exception e){
            e.printStackTrace();
        }


    }




}