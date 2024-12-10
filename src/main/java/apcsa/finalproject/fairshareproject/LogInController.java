package apcsa.finalproject.fairshareproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void completeLogIn(ActionEvent event) {
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("One or more fields are blank.");
            alert.show();
        }
        else {
            DBUtils.logInUser(event, usernameField.getText(), passwordField.getText());
        }
    }

    @FXML
    void goHome(ActionEvent event) {
        DBUtils.changeScene(event, "MainPage.fxml", "Fair Share");
    }

    @FXML
    void signUp(ActionEvent event) {
        DBUtils.changeScene(event, "SignUpPage.fxml", "Sign Up");
    }

}