package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class SuccessController {
    @FXML
    Button goStartBtn;

    public void initialize() {
        goStartBtn.setOnAction(e -> {
            try {
                controllers.Client.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/FXML/user_screen.fxml"))));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
