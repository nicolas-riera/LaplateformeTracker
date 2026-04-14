package laplateformetracker.controllers.popup;

import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddStudentPopupFXMLController {

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private ChoiceBox<String> DegreePicker;
    
    @FXML
    private Button addButton;

    private Consumer<ArrayList<String>> onAddButtonCallback;

    public void setOnConfirmButtonCallback(Consumer<ArrayList<String>> callback){
        this.onAddButtonCallback = callback;
    }

    @FXML
    private void handleAddButtonAction(ActionEvent event){
        if(onAddButtonCallback != null){
           
            // if(){    
            //     onAddButtonCallback.accept(infoList);
            // }
        }
    }
}
