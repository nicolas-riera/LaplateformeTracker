package laplateformetracker.controllers.popup;

import java.util.function.BiConsumer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;

public class ChangePasswordPopupFXMLController {
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmationPasswordField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    private BiConsumer<String, String> onConfirmButtonCallback;

    public void setOnConfirmButtonCallback(BiConsumer<String, String> callback){
        this.onConfirmButtonCallback = callback;
    }

    @FXML
    private void handleConfirmButtonAction(ActionEvent event){
        if(onConfirmButtonCallback != null){
            String password = passwordField.getText();
            String confirmationPassword = confirmationPasswordField.getText();
            onConfirmButtonCallback.accept(password, confirmationPassword);
        }
    }
}
