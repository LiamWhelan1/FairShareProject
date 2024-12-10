package apcsa.finalproject.fairshareproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ChoiceBox<String> currencyField;

    @FXML
    private Button logInButton;

    @FXML
    private MenuItem logoutButton;

    @FXML
    private TextField numPeopleField;

    @FXML
    private Label totalAmountLabel;

    @FXML
    private Label perPersonNotSUp;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField totalCostField;

    @FXML
    private VBox calculateBox;

    @FXML
    private MenuItem viewTransactions;

    @FXML
    private MenuItem uploadReceipt;

    @FXML
    private ChoiceBox<String> newCurrencyField;

    @FXML
    void goHome(ActionEvent event) {
        DBUtils.changeScene(event, "MainPage.fxml", "Fair Share");
    }

    @FXML
    void logIn(ActionEvent event) {
        DBUtils.changeScene(event, "LogInPage.fxml", "Log In");
    }

    @FXML
    void signUp(ActionEvent event) {
        DBUtils.changeScene(event, "SignUpPage.fxml", "Sign Up");
    }

    @FXML
    void calculateSplit(ActionEvent event) {
        double total = Double.parseDouble(totalCostField.getText());
        int numPeople = Integer.parseInt(numPeopleField.getText());
        String currency = currencyField.getSelectionModel().getSelectedItem();
        String newCurrency = newCurrencyField.getSelectionModel().getSelectedItem();
        double due = total / numPeople;
        if (newCurrency.isEmpty()||currency.equals(newCurrency)) {
            perPersonNotSUp.setText(String.format("Cost per person: %.2f "+currency, due));
        } else if (currency.isEmpty()) {
            perPersonNotSUp.setText(String.format("Cost per person: %.2f "+newCurrency, due));
        } else {
            try {
                due = CurrencyConverter.convertCurrency(due, currency, newCurrency);
                perPersonNotSUp.setText(String.format("Cost per person: %.2f "+newCurrency, due));
            } catch (IOException | JSONException e) {
                System.out.println("Error fetching exchange rates: " + e.getMessage());
            }
        }
        perPersonNotSUp.setVisible(true);
    }

    @FXML
    void doViewTransactions(ActionEvent event) {
        DBUtils.changeSceneMenuItem(event, "TransactionsPageLogOut.fxml", "Manage Transactions", viewTransactions);
    }

    @FXML
    void doUploadReceipt(ActionEvent event) {
        DBUtils.changeSceneMenuItem(event, "ReceiptPageLogOut.fxml", "Coming Soon", uploadReceipt);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currencyField.getItems().addAll("USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "SEK", "MXN", "NZD",
                "SGD", "HKD", "NOK", "KRW", "TRY", "INR", "RUB", "BRL", "ZAR", "DKK", "PLN", "TWD", "THB", "MYR");
        newCurrencyField.getItems().addAll("USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "SEK", "MXN", "NZD",
                "SGD", "HKD", "NOK", "KRW", "TRY", "INR", "RUB", "BRL", "ZAR", "DKK", "PLN", "TWD", "THB", "MYR");
        totalCostField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    Double.parseDouble(newValue);
                } catch (NumberFormatException e) {
                    totalCostField.setText(oldValue);
                }
            }
        });
        numPeopleField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    Integer.parseInt(newValue);
                } catch (NumberFormatException e) {
                    totalCostField.setText(oldValue);
                }
            }
        });
    }
}