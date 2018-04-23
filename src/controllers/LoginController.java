package controllers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {

    @FXML
    private TextField username_field;

    @FXML
    private Text info_text;

    @FXML
    private PasswordField password_field;

    @FXML
    Button create_account_button;

    @FXML
    private GridPane grid;

    @FXML Button log_in_button;


    public void initialize(){
        grid.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                log_in_button.fire();
                ev.consume();
            }
        });
    }

    public void Create_account_buttonClicked() throws IOException {
        info_text.setText("Account created!" + System.getProperty("line.separator") + "You can now log in.");

        controllers.Client.stage.setScene(controllers.Client.createAccountScreen);
    }

    public void Login_buttonClicked(){


        if(username_field.getText().matches("[a-zåäöA-ZÅÄÖ0-9]*")){
            if(username_field.getText().length()<1){
                info_text.setText("Username can't be" + System.getProperty("line.separator") +  "empty!");
            }
            else if(password_field.getText().matches("[a-zåäöA-ZÅÄÖ0-9]*")) {
                    if(password_field.getText().length()<1){
                        info_text.setText("Password can't be" + System.getProperty("line.separator") +  "empty!");
                    }

                    try{
                        ArrayList<User> users =  controllers.Client.dummyData.getUsers();
                    for(User user : users) {
                        if (user.getUsername().equals(username_field.getText()) && user.getPassword().equals(password_field.getText())) {
                            System.out.println(user.getName() + " logged in");
                            controllers.Client.currentUser = user;
                            if (user.isAdmin()) {

                                controllers.Client.stage.setTitle("Admin");
                                controllers.Client.stage.setScene(controllers.Client.adminScreen);

                            } else {
                                controllers.Client.stage.setScene(controllers.Client.userScreen);
                            }
                        }
                    }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    info_text.setText("Username and password" + System.getProperty("line.separator") +" do not match");
            }
            else {
                info_text.setText("Password must contain" + System.getProperty("line.separator") +  "only letters!");
            }
        }
        else {
            info_text.setText("Username must contain" + System.getProperty("line.separator") +  "only letters!");
            return;
        }

    }

}
