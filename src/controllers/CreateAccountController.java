package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.User;

import java.util.List;

public class CreateAccountController {
    @FXML
    private TextField create_username_field;

    @FXML
    private TextField first_name_field;
    @FXML
    private TextField last_name_field;

    @FXML
    private TextField address_field;
    @FXML
    private Text create_account_info;

    @FXML
    private PasswordField create_password_field;

    @FXML
    private Button create_account2;

    @FXML
    private GridPane grid;

    public void initialize(){
        grid.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                create_account2.fire();
                ev.consume();
            }
        });
    }

    public void Create_account_button2Clicked(){


        if(first_name_field.getText().matches("[a-zåäöA-ZÅÄÖ0-9 ]*")){
            if(first_name_field.getText().length()<1){
                create_account_info.setText("First Name can't be" + System.getProperty("line.separator") +  "empty!");
                return;
            }
        }
        else {
            create_account_info.setText("First Name must contain" + System.getProperty("line.separator") +  "only letters!");
            return;
        }

        if(last_name_field.getText().matches("[a-zåäöA-ZÅÄÖ0-9 ]*")){
            if(last_name_field.getText().length()<1){
                create_account_info.setText("Last Name can't be" + System.getProperty("line.separator") +  "empty!");
                return;
            }
        }
        else {
            create_account_info.setText("Last Name must contain" + System.getProperty("line.separator") +  "only letters!");
            return;
        }

        if(address_field.getText().length()<1){
            create_account_info.setText("Address can't be" + System.getProperty("line.separator") +  "empty!");
            return;
        }
        if(create_username_field.getText().matches("[a-zåäöA-ZÅÄÖ0-9]*")){
            if(create_username_field.getText().length()<1){
                create_account_info.setText("Username can't be" + System.getProperty("line.separator") +  "empty!");
                return;
            }
            else if(create_password_field.getText().matches("[a-zåäöA-ZÅÄÖ0-9]*")) {
                if(create_password_field.getText().length()<1){
                    create_account_info.setText("Password can't be" + System.getProperty("line.separator") +  "empty!");
                    return;
                }
            }
            else {
                create_account_info.setText("Password must contain" + System.getProperty("line.separator") +  "only letters!");
                return;
            }
        }
        else {
            create_account_info.setText("Username must contain" + System.getProperty("line.separator") +  "only letters!");
            return;
        }

        try {
            List<User> users = Client.dummyData.getUsers();
            boolean success = true;
            for(User u : users){
                if(u.getUsername().equals(create_username_field.getText())){
                    success = false;
                }
            }
            if(success) {
                controllers.Client.dummyData.createNewUser(first_name_field.getText() + " " + last_name_field.getText(), address_field.getText(), create_username_field.getText(), create_password_field.getText(), false);
                controllers.Client.stage.setScene(controllers.Client.loginScreen);
                AdminController.items.add(create_username_field.getText());
            }
            else {
                create_account_info.setText("Username already in use");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
