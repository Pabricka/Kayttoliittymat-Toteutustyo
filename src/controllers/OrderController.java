package controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import models.Trip;

public class OrderController {

    @FXML
    private Canvas canvas;

    @FXML
    private Button nextCarBtnLeft;

    @FXML
    private Button nextCarBtnRight;

    @FXML
    private RadioButton allergicRadio;

    @FXML
    private RadioButton wheelRadio;

    @FXML
    private RadioButton petRadio;

    @FXML
    private RadioButton familyRadio;

    private Trip trip;

    public void initialize() {
        trip = Client.session.getSelectedTrip();
    }
}
