package laplateformetracker.controllers.popup;

import java.util.ArrayList;
import java.util.function.Consumer;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AddStudentPopupFXMLController {
    
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
