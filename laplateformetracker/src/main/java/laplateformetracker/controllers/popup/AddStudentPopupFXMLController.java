package laplateformetracker.controllers.popup;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.net.URL;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddStudentPopupFXMLController implements Initializable{

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] letters = {"L", "J", "W", "C", "D"};
        List<String> combinations = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            for (String letter : letters) {
                combinations.add(i + letter);
            }
        }

        DegreePicker.setItems(FXCollections.observableArrayList(combinations));
        
        DegreePicker.getSelectionModel().selectFirst();
    }

    public void setOnAddButtonCallback(Consumer<ArrayList<String>> callback){
        this.onAddButtonCallback = callback;
    }

    @FXML
    private void handleAddButtonAction(ActionEvent event){
        if(onAddButtonCallback != null){

            String lastName = lastNameField.getText().trim();
            String firstName = firstNameField.getText().trim();
            LocalDate birthDate = birthDatePicker.getValue();
            String address = addressField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String degree = DegreePicker.getValue();
           
            String errorMessage = "";

            if (lastName.isEmpty() || firstName.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                errorMessage += "Tous les champs texte doivent être remplis.\n";
            }
            
            if (birthDate == null) {
                errorMessage += "La date de naissance est obligatoire.\n";
            }

            if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                errorMessage += "Le format de l'email est incorrect.\n";
            }

            if (!phone.isEmpty() && !phone.matches("^0[1-9][0-9]{8}$")) {
                errorMessage += "Le numéro de téléphone doit contenir 10 chiffres et commencer par 0.\n";
            }

            if (errorMessage.isEmpty()) {
                ArrayList<String> infoList = new ArrayList<>();
                infoList.add(lastName);
                infoList.add(firstName);
                infoList.add(birthDate.toString());
                infoList.add(address);
                infoList.add(email);
                infoList.add(phone);
                infoList.add(degree);

                onAddButtonCallback.accept(infoList);
                
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Données invalides");
                alert.setHeaderText("Veuillez corriger les erreurs suivantes :");
                alert.setContentText(errorMessage);
                alert.showAndWait();
            }
        }
    }
}
