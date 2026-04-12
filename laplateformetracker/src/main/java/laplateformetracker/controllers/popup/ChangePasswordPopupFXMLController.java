package laplateformetracker.controllers.popup;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    private Consumer<String> onConfirmButtonCallback;

    public void setOnConfirmButtonCallback(Consumer<String> callback){
        this.onConfirmButtonCallback = callback;
    }

    private Boolean validatePassword(String newPassword, String confirmationPassword){
        String regex = "^(?=.*[a-z])(?=."
                       + "*[A-Z])(?=.*\\d)"
                       + "(?=.*[-+_!@#$%^&*., ?]).+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(newPassword);

        if (newPassword.equals(confirmationPassword) && 
            newPassword.length() > 15 && 
            matcher.matches()){
            return true;
        } else{
            return false;
        }
    }

    @FXML
    private void handleConfirmButtonAction(ActionEvent event){
        if(onConfirmButtonCallback != null){
            String newPassword = passwordField.getText();
            String confirmationPassword = confirmationPasswordField.getText();
            Alert alert = new Alert(AlertType.NONE);
            if(this.validatePassword(newPassword, confirmationPassword)){    
                alert.setAlertType(AlertType.CONFIRMATION);
                alert.setContentText("Mot de passe validé et enregistré.");
                onConfirmButtonCallback.accept(newPassword);
            }
            else {
                alert.setAlertType(AlertType.ERROR);
                alert.setContentText("Mots de passes invalides.");
            }
            alert.show();
        }
    }
}
