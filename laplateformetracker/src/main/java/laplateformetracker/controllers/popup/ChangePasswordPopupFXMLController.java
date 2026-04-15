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
        
        Alert alert = new Alert(AlertType.NONE);

        if (newPassword.equals(confirmationPassword)){
            if (newPassword.length() > 15){
                if (matcher.matches()){
                    return true;
                } else{
                    alert.setAlertType(AlertType.WARNING);
                    alert.setContentText("Il manque au moins 1 maj, 1 min, 1 chiffre et 1 caractère spécial.");
                    alert.show();
                    return false;
                }
            } else{
                alert.setAlertType(AlertType.WARNING);
                alert.setContentText("Le mot de passe doit faire au moins 15 caractères.");
                alert.show();
                return false;
            }
        } else{
            alert.setAlertType(AlertType.WARNING);
            alert.setContentText("Les mots de passes ne correspondent pas.");
            alert.show();
            return false;
        }
    }

    @FXML
    private void handleConfirmButtonAction(ActionEvent event){
        if(onConfirmButtonCallback != null){
            String newPassword = passwordField.getText();
            String confirmationPassword = confirmationPasswordField.getText();
            if(this.validatePassword(newPassword, confirmationPassword)){    
                onConfirmButtonCallback.accept(newPassword);
            }
        }
    }
}
