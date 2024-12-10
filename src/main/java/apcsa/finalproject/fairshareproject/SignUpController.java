package apcsa.finalproject.fairshareproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private ChoiceBox<String> currencyField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void completeSignUp(ActionEvent event) {
        if (currencyField.getSelectionModel().getSelectedItem().isBlank() || usernameField.getText().isBlank() || emailField.getText().isBlank() || passwordField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One or more fields are blank.");
            alert.show();
        }
        else {
            FairShare.user = new User(currencyField.getSelectionModel().getSelectedItem(), usernameField.getText(), emailField.getText(), passwordField.getText());
            DBUtils.signUpUser(event, FairShare.user);
        }
    }

    @FXML
    void goHome(ActionEvent event) {
        DBUtils.changeScene(event, "MainPage.fxml", "Fair Share");
    }

    @FXML
    void logIn(ActionEvent event) {
        DBUtils.changeScene(event, "LogInPage.fxml", "Log In");
    }

    @FXML
    void openMenu(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currencyField.getItems().addAll("USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "SEK", "MXN", "NZD",
                "SGD", "HKD", "NOK", "KRW", "TRY", "INR", "RUB", "BRL", "ZAR", "DKK", "PLN", "TWD", "THB", "MYR");
    }
}
