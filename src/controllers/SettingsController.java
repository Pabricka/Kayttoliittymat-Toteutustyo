package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField addressField;

    @FXML
    private Button updateContactBtn;

    @FXML
    private Text paymentInfoText;

    @FXML
    private Button removePaymentBtn;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button changePasswordBtn;

    @FXML
    private Button closeBtn;

    @FXML
    private Text contactMsg;

    @FXML
    private Text pwMsg;

    private String firstName;
    private String lastName;
    private String address;
    private String paymentInfo;

    public void initialize(){
        initCloseBtn();
        initContactInfoBtn();
        initInfoFields();
        initRemovePaymentBtn();
        initChangePasswordBtn();
    }

    public void initCloseBtn(){
        closeBtn.setOnAction(event -> {
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
        });
    }

    public void initContactInfoBtn(){
        updateContactBtn.setOnAction(e -> {
            pwMsg.setText("");
            initPaymentInfoText();
            firstName = firstNameField.getText();
            lastName = lastNameField.getText();
            address = addressField.getText();

            if(!validContactInput(firstName, lastName, address)) return;
            else {
                Client.currentUser.setName(firstName + " " + lastName);
                Client.currentUser.setAddress(address);
                contactMsg.setFill(Color.GREEN);
                contactMsg.setText("Contact info updated!");
            }
        });
    }

    public void initInfoFields() {
        String[] parts = Client.currentUser.getName().split(" ");
        firstNameField.setText(parts[0]);
        lastNameField.setText(parts[1]);

        addressField.setText(Client.currentUser.getAddress());

        initPaymentInfoText();
    }

    public void initPaymentInfoText(){
        paymentInfoText.setFill(Color.BLACK);
        if(Client.currentUser.getCreditCard() != null){
            paymentInfoText.setText(Client.currentUser.getCreditCard().toString());
        } else {
            paymentInfoText.setText("No info saved");
        }

    }

    public boolean validContactInput(String firstName, String lastName, String address){

        if(firstName.length() < 1 || lastName.length() < 1 || address.length() < 1){
            contactMsg.setFill(Color.RED);
            contactMsg.setText("Fill out all contact info fields!");
            return false;
        }

        if (!firstName.matches("[a-zåäöA-ZÅÄÖ]*") || !lastName.matches("[a-zåäöA-ZÅÄÖ]*")){
            contactMsg.setFill(Color.RED);
            contactMsg.setText("Make sure that your name fields only contain letters!");
            return false;
        }
        return true;
    }

    public void initRemovePaymentBtn(){
        removePaymentBtn.setOnAction(e -> {
            contactMsg.setText("");
            initPaymentInfoText();

            if(Client.currentUser.getCreditCard() == null){
                paymentInfoText.setFill(Color.RED);
                paymentInfoText.setText("There if no info to remove!");
                return;
            }
            Client.currentUser.setCreditCard(null);
            paymentInfoText.setFill(Color.RED);
            paymentInfoText.setText("Info removed!");
        });
    }
    public void initChangePasswordBtn(){
        changePasswordBtn.setOnAction(e -> {

            contactMsg.setText("");
            initPaymentInfoText();

            String oldPw = oldPasswordField.getText();
            String newPw = newPasswordField.getText();
            String confPw = confirmPasswordField.getText();

            if(oldPw.length() < 1 || newPw.length() < 1 || confPw.length() < 1) {
                pwMsg.setFill(Color.RED);
                pwMsg.setText("Make sure that none of the password fields are empty!");
                return;
            }
            if(!oldPw.equals(Client.currentUser.getPassword()) || !(newPw.equals(confPw))){
                pwMsg.setFill(Color.RED);
                pwMsg.setText("The given password doesn't match your current\n password or the new passwords don't match!");
                return;
            }
            else {
                Client.currentUser.setPassword(newPw);
                pwMsg.setFill(Color.GREEN);
                pwMsg.setText("Password changed!");
            }
        });
    }
}
