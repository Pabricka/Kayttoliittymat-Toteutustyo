package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SettingsController {
    @FXML
    Button closeBtn;

    public void initialize(){
        initCloseBtn();
    }
    public void initCloseBtn(){
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) closeBtn.getScene().getWindow();
                stage.close();
            }
        });
    }
}
