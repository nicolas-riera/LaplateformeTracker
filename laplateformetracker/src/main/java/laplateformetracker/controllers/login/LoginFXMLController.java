package laplateformetracker.controllers.login;

import java.util.function.BiConsumer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginFXMLController {

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private BiConsumer<String, String> onLoginButtonCallback;

    public void setOnLoginButtonCallback(BiConsumer<String, String> callback) {
        this.onLoginButtonCallback = callback;
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event){
        if (onLoginButtonCallback != null) {

            String user = userField.getText();
            String pass = passwordField.getText();

            onLoginButtonCallback.accept(user, pass);
        }
    }

}
