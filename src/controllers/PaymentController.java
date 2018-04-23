package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class PaymentController {

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField cvcText;

    @FXML
    private ChoiceBox<?> methodChoice;

    @FXML
    private ChoiceBox<?> providerChoice;

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
    private ChoiceBox<?> expirationBox2;

    @FXML
    private ChoiceBox<?> expirationBox1;

    @FXML
    private CheckBox savePaymentInfo;

    @FXML
    private Text errorMsg;

    public void initialize(){
        
    }

}
