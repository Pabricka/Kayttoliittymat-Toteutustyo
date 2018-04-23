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
import javafx.scene.text.Text;
import models.CreditCard;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class PaymentController {

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
        initMethodChoiceUpdate();
        initProceedBtn();
    }

    public void initializeInfoFields() {

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

        for (int i = 1; i < 13; i++) {
            exp1Items.add(i);
        }
        expirationBox1.setItems(exp1Items);

        for (int i = 18; i < 26; i++) {
            exp2Items.add(i);
        }
        expirationBox2.setItems(exp2Items);

    }

    public void initMethodChoiceUpdate() {
        methodChoice.getSelectionModel().selectedIndexProperty().addListener((ChangeListener<Number>) (observableValue, oldValue, newValue) -> {
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

    public void initProceedBtn() {
        proceedButton.setOnAction(e -> {
            errorMsg.setText("");
            if (methodChoice.getSelectionModel().getSelectedIndex() == -1) {
                errorMsg.setText("Select a method of payment!");
                return;
            } else if (methodChoice.getSelectionModel().getSelectedIndex() == 0) {
                if (!validateContactInput() || !validateCCinput()) return;
            } else {
                if (!validateContactInput()) return;
            }
//            FOR VALITTU PENKKI IN VALITUT PENKIT
//            Purchase purchase = new Purchase(Client.currentUser, SELECTED_TRIP, SELECTED_PENKKI, SELECTED_VAUNU);
//
//            try{
//                Client.dummyData.getPurchases().add(purchase);
//            }
//            catch(Exception exc){
//                exc.printStackTrace();
//            }
            try {
                controllers.Client.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/FXML/success_screen.fxml"))));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
    }

    public boolean validateContactInput() {
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

    public boolean validateCCinput() {
        String ccNumber = ccNumberText.getText().replaceAll("[^\\d]", "");
        String cvvNumber = cvvText.getText();

        int cvvLength;

        if (providerChoice.getSelectionModel().getSelectedIndex() == 2) cvvLength = 4;
        else cvvLength = 3;

        if (methodChoice.getSelectionModel().getSelectedIndex() == -1 || providerChoice.getSelectionModel().getSelectedIndex() == -1 ||
                ccNumberText.getText().length() < 1 || cvvNumber.length() < 1 || expirationBox1.getSelectionModel().getSelectedIndex() == -1 ||
                expirationBox2.getSelectionModel().getSelectedIndex() == -1) {
            errorMsg.setText("Fill out all fields!");
            return false;
        }

        String providerString = providerChoice.getSelectionModel().getSelectedItem().toString();
        int expMonth = (Integer) expirationBox1.getSelectionModel().getSelectedItem();
        int expYear = 2000 + (Integer) expirationBox2.getSelectionModel().getSelectedItem();
        String expMonthString = expirationBox1.getSelectionModel().getSelectedItem().toString();

        if (expMonthString.length() == 1) expMonthString = "0" + expMonth;


        if (ccNumber.length() != 16 || cvvNumber.length() != cvvLength || !cvvNumber.matches("[0-9]+") ||
                expYear < Calendar.getInstance().get(Calendar.YEAR) || (expYear == Calendar.getInstance().get(Calendar.YEAR) && expMonth < Calendar.getInstance().get(Calendar.MONTH + 1))) {
            errorMsg.setText("Bad credit card information");
            return false;
        }
        YearMonth expDate = YearMonth.parse(expMonthString + " " + expYear, DateTimeFormatter.ofPattern("MM yyyy"));

        if (savePaymentInfo.isSelected()) {
            CreditCard.Provider provid = CreditCard.Provider.VISA;
            for (CreditCard.Provider provider : CreditCard.Provider.values()) {
                if (provider.equalsName(providerString)) provid = provider;
            }
            Client.currentUser.setCreditCard(new CreditCard(ccNumber, cvvNumber, provid, expDate));
        }
        return true;
    }

}
