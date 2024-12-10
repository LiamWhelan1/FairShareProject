package apcsa.finalproject.fairshareproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class TransactionsController {

    @FXML
    private Button homeButton;

    @FXML
    private MenuItem logoutButton;

    @FXML
    private Label title;

    @FXML
    private VBox transactionsList;

    @FXML
    private MenuItem uploadReceipt;

    @FXML
    private MenuButton userMenu;

    @FXML
    private MenuItem viewFriendsButton;

    @FXML
    private MenuItem viewTransactions;

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
        DBUtils.changeScene(event, "MainPage.fxml", "Fair Share");
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

}
