package apcsa.finalproject.fairshareproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendsController implements Initializable {

    @FXML
    private VBox addFriendsList;

    @FXML
    private ScrollPane addFriendsPane;

    @FXML
    private TextField friendSearch;

    @FXML
    private VBox friendsList;

    @FXML
    private ScrollPane friendsPane;

    @FXML
    private Button homeButton;

    @FXML
    private MenuItem logoutButton;

    @FXML
    private MenuButton userMenu;

    private ObservableList<String> friends = FXCollections.observableArrayList();

    private ObservableList<String> nonFriends = FXCollections.observableArrayList();

    @FXML
    void goHome(ActionEvent event) {
        DBUtils.changeScene(event, "LoggedIn.fxml", "Fair Share");
    }

    @FXML
    void logOut(ActionEvent event) {
        FairShare.user = null;
        DBUtils.changeSceneMenuItem(event, "MainPage.fxml", "Fair Share", logoutButton);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userMenu.setText(FairShare.user.getName());
        friends.addAll(DBUtils.getFriends(FairShare.user.getName()));
        nonFriends.addAll(DBUtils.getNonFriends("", FairShare.user.getName()));
        refreshFriendsList();
        refreshSearchResults();
        friendSearch.textProperty().addListener((observable, oldValue, newValue) -> searchForFriends(newValue));
    }

    private void refreshFriendsList() {
        friendsList.getChildren().clear();
        for (String friend: friends) {
            Button removeButton = new Button("Remove");
            removeButton.setOnAction(event -> removeFriend(friend));
            HBox row = new HBox(10, new Label(friend), removeButton);
            row.setStyle("-fx-padding: 5;");
            friendsList.getChildren().add(row);
        }
    }

    private void removeFriend(String friend) {
        friends.remove(friend);
        nonFriends.add(friend);
        DBUtils.removeFriends(FairShare.user.getName(), friend);
        refreshFriendsList();
        refreshSearchResults();
    }

    private void addFriend(String friend) {
        friends.add(friend);
        nonFriends.remove(friend);
        DBUtils.addFriends(FairShare.user.getName(), friend);
        refreshFriendsList();
        refreshSearchResults();
    }

    private void refreshSearchResults() {
        addFriendsList.getChildren().clear();
        for (String nonFriend : nonFriends) {
            Button addButton = new Button("Add");
            addButton.setOnAction(event -> addFriend(nonFriend));

            HBox row = new HBox(10, new Label(nonFriend), addButton);
            row.setStyle("-fx-padding: 5;");
            addFriendsList.getChildren().add(row);
        }
    }

    private void searchForFriends(String query) {
        nonFriends.clear();
        nonFriends.addAll(DBUtils.getNonFriends(query, FairShare.user.getName()));
        refreshSearchResults();
    }
}