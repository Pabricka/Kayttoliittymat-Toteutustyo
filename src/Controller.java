import Models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class Controller  {




    @FXML
    private TextField username_field;

    @FXML
    private TextField create_username_field;

    @FXML
    private TextField name_field;


    @FXML
    private Text info_text;
    @FXML
    private Text create_account_info;




    @FXML
    private PasswordField password_field;
    @FXML
    private PasswordField create_password_field;


    @FXML
    Button login_button;

    @FXML
    Button create_account_button;
    @FXML
    Button create_account_button2;

    public void Create_account_buttonClicked() throws IOException {
        info_text.setText("Account created!" + System.getProperty("line.separator") + "You can now log in.");

        Client.stage.setScene(Client.createAccountScreen);
    }
    public void Create_account_button2Clicked(){


        if(name_field.getText().matches("[a-zåäöA-ZÅÄÖ0-9 ]*")){
            if(name_field.getText().length()<1){
                create_account_info.setText("Name can't be" + System.getProperty("line.separator") +  "empty!");
                return;
            }
        }
        else {
            create_account_info.setText("Name must contain" + System.getProperty("line.separator") +  "only letters!");
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

        Client.stage.setScene(Client.loginScreen);
        Admin_controller.items.add(create_username_field.getText());
        try {
            Client.dummyData.createNewUser(name_field.getText(), create_username_field.getText(), create_password_field.getText(), false);
        }
        catch (Exception e){
            e.printStackTrace();
        }

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
                        ArrayList<User> users =  Client.dummyData.getUsers();
                    for(User user : users) {
                        if (user.getUsername().equals(username_field.getText()) && user.getPassword().equals(password_field.getText())) {
                            System.out.println(user.getName() + " logged in");
                            Client.currentUser = user;
                            if (user.isAdmin()) {

                                Client.stage.setTitle("Admin");
                                Client.stage.setScene(Client.adminScreen);

                            } else {
                                Client.stage.setScene(Client.userScreen);
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
