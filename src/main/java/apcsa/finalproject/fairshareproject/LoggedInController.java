package apcsa.finalproject.fairshareproject;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class LoggedInController implements Initializable {

    @FXML
    private VBox addTransactionBox;

    @FXML
    private ChoiceBox<String> currencySelection;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private TextField eventNameField;

    @FXML
    private VBox friendsList;

    @FXML
    private Button homeButton;

    @FXML
    private MenuItem logoutButton;

    @FXML
    private Label title;

    @FXML
    private TextField totalAmountField;

    @FXML
    private Label totalAmountLabel;

    @FXML
    private TextField transactionNameField;

    @FXML
    private MenuItem uploadReceipt;

    @FXML
    private MenuButton userMenu;

    @FXML
    private MenuItem viewFriendsButton;

    @FXML
    private MenuItem viewTransactions;

    private ObservableList<User> friends = FXCollections.observableArrayList();
    private SimpleDoubleProperty totalAmount = new SimpleDoubleProperty(0);
    private SimpleStringProperty currency = new SimpleStringProperty("");

    @FXML
    void doUploadReceipt(ActionEvent event) {
        DBUtils.changeSceneMenuItem(event, "ReceiptPage.fxml", "Coming Soon", uploadReceipt);
    }

    @FXML
    void doViewTransactions(ActionEvent event) {
        DBUtils.changeSceneMenuItem(event, "TransactionsPage.fxml", "Manage Transactions", viewTransactions);
    }

    @FXML
    void getCurrencySelection(ActionEvent event) {
        currency.set(currencySelection.getSelectionModel().getSelectedItem());
        updateDues();
    }

    @FXML
    void goHome(ActionEvent event) {
        DBUtils.changeScene(event, "LoggedIn.fxml", "Fair Share");
    }

    @FXML
    void logOut(ActionEvent event) {
        FairShare.user = null;
        DBUtils.changeScene(event, "MainPage.fxml", "Fair Share");
    }

    @FXML
    void saveTransaction(ActionEvent event) {
        Date date = Date.valueOf(eventDatePicker.getValue());
        String transactionName = transactionNameField.getText();
        String eventName = eventNameField.getText();
        if (!eventName.isEmpty()) {
            DBUtils.saveEvent(eventName, date, FairShare.user.getName());
        }
        for (User friend: friends) {
            double amount = friend.getDue();
            String currency = friend.getCurrency();
            DBUtils.saveTransaction(friend.getName(), eventName, transactionName, amount, currency, FairShare.user.getName());
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(transactionName +" saved");
        alert.show();
        DBUtils.changeScene(event, "LoggedIn.fxml", "Fair Share");
    }

    @FXML
    void viewFriends(ActionEvent event) {
        DBUtils.changeSceneMenuItem(event, "FriendsPage.fxml", "Friends", viewFriendsButton);
    }

    private int countCheckedBoxesNoRatio() {
        return (int) friends.stream().filter(x -> x.isIncluded() && !x.hasSpecialRatio()).count();
    }

    private double calcTotalRatio() {
        AtomicReference<Double> totalRatio = new AtomicReference<>((double) 0);
        friends.stream().filter(x -> x.isIncluded() && x.hasSpecialRatio()).forEach(x -> totalRatio.updateAndGet(v -> v + x.getPortion()));
        return totalRatio.get();
    }

    private double calculatePortion() {
        int includedCount = countCheckedBoxesNoRatio();
        return includedCount > 0 ? (1. - calcTotalRatio()) / (includedCount+1) : 0;
    }

    private void updateDues() {
        for (User friend: friends) {
            if (friend.isIncluded()) {
                if (!friend.hasSpecialRatio())
                    friend.setPortion(calculatePortion());
                double due = totalAmount.get()*friend.getPortion();
                if (!currency.get().isEmpty()&&!friend.getCurrency().equals(currency.get())) {
                    try {
                        due = CurrencyConverter.convertCurrency(due, currency.get(), friend.getCurrency());
                    } catch (IOException | JSONException e) {
                        System.out.println("Error fetching exchange rates: " + e.getMessage());
                    }
                }
                friend.setDue(due);
            } else {
                friend.setDue(0);
            }
        }
    }

    private void refreshFriendsList() {
        friendsList.getChildren().clear();
        for (User friend: friends) {
            CheckBox checkBox = new CheckBox(friend.getName());
            Label portionLabel = new Label();
            TextField ratioField = new TextField();
            ratioField.setPromptText("% or blank");
            ratioField.setPrefWidth(85);
            ratioField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty()) {
                    friend.setSpecialRatio(false);
                    friend.setPortion(calculatePortion());
                    updateDues();
                } else {
                    try {
                        double ratio = Double.parseDouble(newValue);
                        ratio /= 100;
                        friend.setSpecialRatio(true);
                        friend.setPortion(ratio);
                        updateDues();
                    } catch (NumberFormatException e) {
                        ratioField.setText(oldValue);
                    }
                }
            });

            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                friend.setIncluded(newValue);
                updateDues();
            });

            HBox row = new HBox(10, checkBox, ratioField, portionLabel);
            friendsList.getChildren().add(row);

            friend.dueProperty().addListener((observable, oldValue, newValue) -> {
                portionLabel.setText(friend.isIncluded() ? String.format("%.2f "+friend.getCurrency(), friend.getDue()) : "");
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currencySelection.getItems().addAll("USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "SEK", "MXN", "NZD",
                "SGD", "HKD", "NOK", "KRW", "TRY", "INR", "RUB", "BRL", "ZAR", "DKK", "PLN", "TWD", "THB", "MYR");
        userMenu.setText(FairShare.user.getName());
        friends.addAll(DBUtils.getFriendsAsUsers(FairShare.user.getName()));
        refreshFriendsList();
        if (friends.isEmpty()) {
            friendsList.getChildren().addAll(new Label("Added friends will appear hear"), new Label("Go to manage friends to add friends"));
        }
        totalAmountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                totalAmount.set(0.);
            } else {
                try {
                    totalAmount.set(Double.parseDouble(newValue));
                } catch (NumberFormatException e) {
                    totalAmountField.setText(oldValue);
                }
            }
            updateDues();
        });
        currencySelection.setOnAction(this::getCurrencySelection);
    }
}