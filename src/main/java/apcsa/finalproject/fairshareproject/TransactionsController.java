package apcsa.finalproject.fairshareproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {

    @FXML
    private Button homeButton;

    @FXML
    private MenuItem logoutButton;

    @FXML
    private Label title;

    @FXML
    private MenuItem uploadReceipt;

    @FXML
    private MenuButton userMenu;

    @FXML
    private MenuItem viewFriendsButton;

    @FXML
    private MenuItem viewTransactions;

    @FXML
    private TreeTableView<Transaction> owedTableView;

    @FXML
    private TreeTableColumn<Transaction, String> transactionColumn;

    @FXML
    private TreeTableColumn<Transaction, Double> amountColumn;

    @FXML
    private TreeTableColumn<Transaction, String> descriptionColumn;

    @FXML
    private TreeTableColumn<Transaction, String> statusColumn;

    @FXML
    void doUploadReceipt(ActionEvent event) {
        DBUtils.changeSceneMenuItem(event, FairShare.user != null ? "ReceiptPage.fxml" : "ReceiptPageLogOut.fxml", "Coming Soon", uploadReceipt);
    }

    @FXML
    void doViewTransactions(ActionEvent event) {
        DBUtils.changeSceneMenuItem(event, FairShare.user != null ? "TransactionsPage.fxml": "TransactionsPageLogOut.fxml", "Manage Transactions", viewTransactions);
    }

    @FXML
    void goHome(ActionEvent event) {
        DBUtils.changeScene(event, FairShare.user != null ? "LoggedIn.fxml" : "MainPage.fxml", "Fair Share");
    }

    @FXML
    void logOut(ActionEvent event) {
        FairShare.user = null;
        DBUtils.changeSceneMenuItem(event, "MainPage.fxml", "Fair Share", logoutButton);
    }

    @FXML
    void viewFriends(ActionEvent event) {
        DBUtils.changeSceneMenuItem(event, "FriendsPage.fxml", "Friends", viewFriendsButton);
    }

    @FXML
    void logIn(ActionEvent event) {
        DBUtils.changeScene(event, "LogInPage.fxml", "Log In");
    }

    @FXML
    void signUp(ActionEvent event) {
        DBUtils.changeScene(event, "SignUpPage.fxml", "Sign Up");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userMenu.setText(FairShare.user.getName());
        transactionColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        amountColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        descriptionColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        statusColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("status"));

        owedTableView.setRoot(loadTransactions());
        owedTableView.setShowRoot(false);
    }

    private TreeItem<Transaction> loadTransactions() {
        TreeItem<Transaction> root = new TreeItem<>();

        ObservableList<TreeItem<Transaction>> eventGroups = FXCollections.observableArrayList();
        HashMap<String, ArrayList<Transaction>> transactions = DBUtils.getTransactions(FairShare.user.getName());
        for (String event: transactions.keySet()) {
            eventGroups.add(createEventGroup(event, FXCollections.observableArrayList(transactions.get(event))));
        }

        root.getChildren().addAll(eventGroups);
        return root;
    }

    private TreeItem<Transaction> createEventGroup(String event, ObservableList<Transaction> transactions) {
        double sum = 0.0;
        for (Transaction t: transactions) {
            sum += t.getAmount();
        }
        TreeItem<Transaction> eventGroup = new TreeItem<>(new Transaction(event, sum, "", ""));
        eventGroup.setExpanded(true);

        for (Transaction t : transactions) {
            eventGroup.getChildren().add(new TreeItem<>(t));
        }

        return eventGroup;
    }
}
