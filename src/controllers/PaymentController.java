package controllers;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.Car;
import models.CreditCard;
import models.Purchase;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class PaymentController {

    @FXML
    private GridPane grid;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField cvvText;

    @FXML
    private ChoiceBox methodChoice;

    @FXML
    private ChoiceBox providerChoice;

    @FXML
    private TextField lnameText;

    @FXML
    private TextField ccNumberText;

    @FXML
    private Button proceedButton;

    @FXML
    private TextField emailText;

    @FXML
    private TextField fnameText;

    @FXML
    private ChoiceBox expirationBox2;

    @FXML
    private ChoiceBox expirationBox1;

    @FXML
    private CheckBox savePaymentInfo;

    @FXML
    private Text errorMsg;

    private ObservableList<String> methodItems;
    private ObservableList<String> providerItems;
    private ObservableList<Integer> exp1Items;
    private ObservableList<Integer> exp2Items;


    public void initialize() {
        initializeInfoFields();
        initEnterToGo();
        initMethodChoiceUpdate();
        initProceedBtn();
        initCCfields();
    }

    /**
     * Adds the user's data to the info fields
     * and populates the choice box options.
     */
    private void initializeInfoFields() {

        methodItems = FXCollections.observableArrayList();
        providerItems = FXCollections.observableArrayList();
        exp1Items = FXCollections.observableArrayList();
        exp2Items = FXCollections.observableArrayList();

        String[] parts = Client.currentUser.getName().split(" ");
        fnameText.setText(parts[0]);
        lnameText.setText(parts[1]);
        addressTxt.setText(Client.currentUser.getAddress());


        methodItems.addAll("Credit Card", "Pay on the train");
        methodChoice.setItems(methodItems);


        for (CreditCard.Provider provider : CreditCard.Provider.values()) {
            providerItems.add(provider.toString());
        }
        providerChoice.setItems(providerItems);

        //Add months
        for (int i = 1; i < 13; i++) {
            exp1Items.add(i);
        }
        expirationBox1.setItems(exp1Items);

        //Add years
        for (int i = 18; i < 26; i++) {
            exp2Items.add(i);
        }
        expirationBox2.setItems(exp2Items);

    }

    /**
     * Inits the credit card fields with user's payment information
     */
    private void initCCfields() {
        CreditCard card = Client.currentUser.getCreditCard();

        if (card == null) return;

        methodChoice.getSelectionModel().selectFirst();
        providerChoice.getSelectionModel().select(card.getProvider().toString());
        ccNumberText.setText(card.getNumber());
        cvvText.setText(card.getCVV());
        expirationBox1.setValue(card.getExpirationDate().getMonthValue());
        expirationBox2.setValue(card.getExpirationDate().getYear() - 2000);

    }

    /**
     * Enter can be used to proceed with the purchase
     */
    private void initEnterToGo() {
        grid.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                proceedButton.fire();
                ev.consume();
            }
        });
    }

    /**
     * Make the credit card fields uneditable if user selects to pay on the train
     */
    private void initMethodChoiceUpdate() {
        methodChoice.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue, newValue) -> {
            if ((Integer) newValue == 1) {
                providerChoice.setDisable(true);
                ccNumberText.setDisable(true);
                cvvText.setDisable(true);
                expirationBox1.setDisable(true);
                expirationBox2.setDisable(true);
                savePaymentInfo.setDisable(true);
            }
            if ((Integer) newValue == 0) {
                providerChoice.setDisable(false);
                ccNumberText.setDisable(false);
                cvvText.setDisable(false);
                expirationBox1.setDisable(false);
                expirationBox2.setDisable(false);
                savePaymentInfo.setDisable(false);
            }
        });
    }

    /**
     * Validates user input and proceeds to make the purchase if the input is valid.
     */
    private void initProceedBtn() {
        proceedButton.setOnAction(e -> {
            errorMsg.setText("");

            //If no payment method is selected
            if (methodChoice.getSelectionModel().getSelectedIndex() == -1) {
                errorMsg.setText("Select a method of payment!");
                return;
            }
            //If credit card was selected as the method
            else if (methodChoice.getSelectionModel().getSelectedIndex() == 0) {
                if (!validateContactInput() || !validateCCinput()) return;
            }
            //If pay on the train was selected as the method
            else {
                if (!validateContactInput()) return;
            }
            for(int i =0; i< Client.session.getPassengers(); i++) {
                Purchase p = new Purchase(Client.currentUser, Client.session.getSelectedTrip(), Client.session.getSelectedCars().get(i), Client.session.getSelectedSeats().get(i));
                try {
                    Client.dummyData.newPurchase(p);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                //Move to the success screen
                try {
                    controllers.Client.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/FXML/success_screen.fxml"))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
    }

    private boolean validateContactInput() {
        String fname = fnameText.getText();
        String lname = lnameText.getText();
        String address = addressTxt.getText();
        String email = emailText.getText();

        if (fname.length() < 1 || lname.length() < 1 || address.length() < 1 || email.length() < 1) {
            errorMsg.setText("Fill out your contact information!");
            return false;
        }
        if (!fname.matches("[a-zåäöA-ZÅÄÖ]*") || !lname.matches("[a-zåäöA-ZÅÄÖ]*")) {
            errorMsg.setText("Your name can only contain letters!");
            return false;
        }
        return true;
    }


    /**
     * Validate credit card info
     *
     * @return true if info is valid, false otherwise
     */
    private boolean validateCCinput() {

        String ccNumber = ccNumberText.getText().replaceAll("[^\\d]", "");
        String cvvNumber = cvvText.getText();

        int cvvLength;

        //If provider is American Express, CVV length is 4, otherwise 3
        if (providerChoice.getSelectionModel().getSelectedIndex() == 2) cvvLength = 4;
        else cvvLength = 3;

        //Check if some fields are empty
        if (methodChoice.getSelectionModel().getSelectedIndex() == -1 || providerChoice.getSelectionModel().getSelectedIndex() == -1 ||
                ccNumberText.getText().length() < 1 || cvvNumber.length() < 1 || expirationBox1.getSelectionModel().getSelectedIndex() == -1 ||
                expirationBox2.getSelectionModel().getSelectedIndex() == -1) {
            errorMsg.setText("Fill out all fields!");
            return false;
        }

        //Get info from input fields
        String providerString = providerChoice.getSelectionModel().getSelectedItem().toString();
        int expMonth = (Integer) expirationBox1.getSelectionModel().getSelectedItem();
        int expYear = 2000 + (Integer) expirationBox2.getSelectionModel().getSelectedItem();
        String expMonthString = expirationBox1.getSelectionModel().getSelectedItem().toString();

        //Add 0 to month if month is a single digit number for formatting reasons
        if (expMonthString.length() == 1) expMonthString = "0" + expMonth;

        //Check that credit card number length is 16, CVV length is correct, CVV only has digits and that the expiration date is not in the past
        if (ccNumber.length() != 16 || cvvNumber.length() != cvvLength || !cvvNumber.matches("[0-9]+") ||
                expYear < Calendar.getInstance().get(Calendar.YEAR) || (expYear == Calendar.getInstance().get(Calendar.YEAR) && expMonth < Calendar.getInstance().get(Calendar.MONTH + 1))) {
            errorMsg.setText("Bad credit card information");
            return false;
        }
        //Parse YearMonth expiration date for the credit card's contructor
        YearMonth expDate = YearMonth.parse(expMonthString + " " + expYear, DateTimeFormatter.ofPattern("MM yyyy"));

        //If save info is selected, create a new credit card and assign it to the user
        if (savePaymentInfo.isSelected()) {
            CreditCard.Provider provid = CreditCard.Provider.VISA;
            for (CreditCard.Provider provider : CreditCard.Provider.values()) {
                if (provider.equalsName(providerString)) provid = provider;
            }
            ccNumber = ccNumber.substring(0, 4) + " " + ccNumber.substring(4, 8) + " " + ccNumber.substring(8, 12) + " " + ccNumber.substring(12, 16);
            Client.currentUser.setCreditCard(new CreditCard(ccNumber, cvvNumber, provid, expDate));
        }
        return true;
    }

}
