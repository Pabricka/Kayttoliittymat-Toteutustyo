package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.TableData;
import models.Trip;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DisplayTripsController {

    @FXML
    private TableView<TableData> listOfTrips;

    @FXML
    private TableColumn<TableData, String> depColumn;

    @FXML
    private TableColumn<TableData, String> arrColumn;

    @FXML
    private TableColumn<TableData, String> lengthColumn;

    @FXML
    private TableColumn<TableData, String> typeColumn;

    @FXML
    private TableColumn<TableData, String> priceColumn;

    @FXML
    private TableColumn<TableData, String> serviceColumn;

    @FXML
    private TableColumn<TableData, String> actionColumn;

    @FXML
    private Button settingsButton;

    @FXML
    private Button goBackBtn;

    List<Trip> foundTrips;
    ObservableList<TableData> data;

    public void initialize() {

        data = FXCollections.observableArrayList();
        //Get the found trips from session
        foundTrips = Client.session.getFoundTrips();
        //Populate the table and add interactivity
        populateTable(foundTrips);
        initEnterToGo();
        initSettingsButton();
        initGoBack();

    }

    /**
     * Populates the Table View with trips from the parameter list.
     * Uses the trips to construct TableData objects, which are then added to the table.
     * @param trips List of trips that are used to populate the table
     */
    public void populateTable(List<Trip> trips) {
        //Create the TableData objects from trips
        for (Trip trip : trips) {
            data.add(new TableData(trip));
        }
        //Set cell value factories
        depColumn.setCellValueFactory(new PropertyValueFactory<>("departure"));
        arrColumn.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        serviceColumn.setCellValueFactory(new PropertyValueFactory<>("services"));

        //Make the buttons in action column
        Callback<TableColumn<TableData, String>, TableCell<TableData, String>> cellFactory
                = new Callback<TableColumn<TableData, String>, TableCell<TableData, String>>() {
            @Override
            public TableCell call(final TableColumn<TableData, String> param) {
                final TableCell<TableData, String> cell = new TableCell<TableData, String>() {

                    final Button btn = new Button("Order");

                    //Add interactivity to the buttons
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(e -> {
                                //Check which button was pressed, add that trip as the selected trip and proceed to the order screen
                                int index = getIndex();
                                Client.session.setSelectedTrip(foundTrips.get(index));
                                try {
                                    controllers.Client.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/FXML/order_screen.fxml"))));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        actionColumn.setCellFactory(cellFactory);
        listOfTrips.setItems(data);

    }

    /**
     * Makes it so that the enter key can be used to proceed to the order screen instead of clicking the button
     */
    public void initEnterToGo(){
        listOfTrips.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                int index = listOfTrips.getSelectionModel().getFocusedIndex();
                Client.session.setSelectedTrip(foundTrips.get(index));
                try {
                    controllers.Client.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/FXML/order_screen.fxml"))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                ev.consume();
            }
        });
    }

    /**
     * Adds interactivity to the go back button
     */
    public void initGoBack() {
        goBackBtn.setOnAction(e -> {
            //Confirm selection
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Confirm that you want to go back");
            alert.setContentText("Are you sure you want to return to the search screen?");
            Optional<ButtonType> result = alert.showAndWait();

            //If okay, go back to user screen
            if (result.get() == ButtonType.OK) {
                try {
                    controllers.Client.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/FXML/user_screen.fxml"))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                e.consume();
            }
        });
    }

    /**
     * Make the settings button open up the settings window.
     */
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
}
