package laplateformetracker.controllers.popup;

import java.util.function.BiConsumer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddGradePopupFXMLController{
    
    @FXML
    TextField skillField;

    @FXML
    TextField gradeField;

    @FXML
    Button confirmButton;

    private BiConsumer<String, Float> onConfirmButtonCallback;

    public void setOnConfirmButtonCallback(BiConsumer<String, Float> callback){
        this.onConfirmButtonCallback = callback;
    }

    @FXML
    private void handleConfirmButtonAction(ActionEvent event){
        if(onConfirmButtonCallback != null){
            Alert alert = new Alert(AlertType.WARNING);
            if (skillField.getText().isEmpty() || gradeField.getText().isEmpty()){
                alert.setContentText("Un des champs est vide.");
                alert.show();
            } else {
                String skill = skillField.getText();
                try {
                    Float grade = Float.parseFloat(gradeField.getText().replace(",","."));
                    if(0 <= grade && grade <= 20){
                        onConfirmButtonCallback.accept(skill, grade);
                    } else{
                        alert.setContentText("La note doit être contenue entre 0 et 20.");
                        alert.show();
                    }
                } catch (java.lang.RuntimeException e) {
                    alert.setContentText("La note doit être un chiffre entre 0 et 20.");
                    alert.show();
                }
            }
        }
    }
}
