package controllers;

import javafx.fxml.FXML;
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

    public void initialize() {
        initCloseBtn();
        initContactInfoBtn();
        initContactInfoFields();
        initPaymentInfoText();
        initRemovePaymentBtn();
        initChangePasswordBtn();
    }

    /**
     * Adds the current user's name and address to the matching text boxes
     */
    public void initContactInfoFields() {
        String[] parts = Client.currentUser.getName().split(" ");
        firstNameField.setText(parts[0]);
        lastNameField.setText(parts[1]);

        addressField.setText(Client.currentUser.getAddress());
    }

    /**
     * Makes the close button close the window
     */
    public void initCloseBtn() {
        closeBtn.setOnAction(event -> {
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
        });
    }

    /**
     * Makes the update contact info button to validate user input
     * and update the user data if input is valid.
     */
    public void initContactInfoBtn() {
        updateContactBtn.setOnAction(e -> {
            //Reset messages
            pwMsg.setText("");
            initPaymentInfoText();

            firstName = firstNameField.getText();
            lastName = lastNameField.getText();
            address = addressField.getText();

            //If input is valid, update user data
            if (validContactInput(firstName, lastName, address)) {
                Client.currentUser.setName(firstName + " " + lastName);
                Client.currentUser.setAddress(address);

                //Update dummyData
                try {
                    Client.dummyData.changeName(Client.currentUser.getUsername(), firstName + " " + lastName);
                    Client.dummyData.changeAddress(Client.currentUser.getUsername(), address);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                //Display success message
                contactMsg.setFill(Color.GREEN);
                contactMsg.setText("Contact info updated!");
            }
        });
    }

    /**
     * Resets the payment info text according to the user's data
     */
    public void initPaymentInfoText() {
        paymentInfoText.setFill(Color.BLACK);
        if (Client.currentUser.getCreditCard() != null) {
            paymentInfoText.setText(Client.currentUser.getCreditCard().toString());
        } else {
            paymentInfoText.setText("No info saved");
        }

    }

    /**
     * Makes the payment button try to remove the user's saved payment info.
     * Displays a message signifying the outcome.
     */
    public void initRemovePaymentBtn() {
        removePaymentBtn.setOnAction(e -> {

            //Reset messages
            contactMsg.setText("");
            pwMsg.setText("");
            initPaymentInfoText();

            //Display an error message if the user has no saved payment data
            if (Client.currentUser.getCreditCard() == null) {
                paymentInfoText.setFill(Color.RED);
                paymentInfoText.setText("There if no info to remove!");
                return;
            }
            //If the user has saved data, set the user's credit card to null
            Client.currentUser.setCreditCard(null);
            paymentInfoText.setFill(Color.GREEN);
            paymentInfoText.setText("Info removed!");
        });
    }

    /**
     * Makes the change password button validate input and change the users password if the input is valid
     */
    public void initChangePasswordBtn() {
        changePasswordBtn.setOnAction(e -> {
            //Reset messages
            contactMsg.setText("");
            initPaymentInfoText();

            //Get user input
            String oldPw = oldPasswordField.getText();
            String newPw = newPasswordField.getText();
            String confPw = confirmPasswordField.getText();

            //Check some fields are empty
            if (oldPw.length() < 1 || newPw.length() < 1 || confPw.length() < 1) {
                pwMsg.setFill(Color.RED);
                pwMsg.setText("Make sure that none of the password fields are empty!");
                return;
            }

            //Check that the current password matches and that the new password and confirmation match.
            if (!oldPw.equals(Client.currentUser.getPassword()) || !(newPw.equals(confPw))) {
                pwMsg.setFill(Color.RED);
                pwMsg.setText("The given password doesn't match your current\n password or the new passwords don't match!");
                return;
            }
            //If they do match, change the user's password
            else {
                Client.currentUser.setPassword(newPw);
                try {
                    Client.dummyData.changePassword(Client.currentUser.getUsername(), newPw);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                pwMsg.setFill(Color.GREEN);
                pwMsg.setText("Password changed!");
            }
        });
    }

    /**
     * Checks if the given contact input is valid
     * @param firstName First name input
     * @param lastName  Last name input
     * @param address   Address input
     * @return  true if inputs are valid, false otherwise
     */
    public boolean validContactInput(String firstName, String lastName, String address) {

        //Check if some fields are empty
        if (firstName.length() < 1 || lastName.length() < 1 || address.length() < 1) {
            contactMsg.setFill(Color.RED);
            contactMsg.setText("Fill out all contact info fields!");
            return false;
        }

        //Check that the names only contain letters
        if (!firstName.matches("[a-zåäöA-ZÅÄÖ]*") || !lastName.matches("[a-zåäöA-ZÅÄÖ]*")) {
            contactMsg.setFill(Color.RED);
            contactMsg.setText("Make sure that your name fields only contain letters!");
            return false;
        }
        return true;
    }
}
