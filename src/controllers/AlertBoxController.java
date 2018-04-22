package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AlertBoxController {

    @FXML
    private Text alertText;

    @FXML
    private Button closeBtn;

    public void initialize(){
        closeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) closeBtn.getScene().getWindow();
                stage.close();
            }
        });
    }
    public void setAlertText(String text){
        alertText.setText(text);
    }
}
