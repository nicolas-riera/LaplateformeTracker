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
import laplateformetracker.models.DataBase;
import laplateformetracker.models.StudentModel;

public class ModifyStudentPopupFXMLController implements Initializable{

    private ArrayList<ArrayList<String>> studentInfos;

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
    private Button modifyButton;

    private Consumer<ArrayList<Object>> onModifyButtonCallback;

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

    public void pullStudentInfos(int userId, DataBase database){
        this.studentInfos = StudentModel.getInfos(userId, database);
        emailField.setPromptText(studentInfos.get(0).get(2));
        firstNameField.setPromptText(studentInfos.get(0).get(4));
        lastNameField.setPromptText(studentInfos.get(0).get(5));
        birthDatePicker.setPromptText(studentInfos.get(0).get(6));
        addressField.setPromptText(studentInfos.get(0).get(7));
        phoneField.setPromptText(studentInfos.get(0).get(8));
    }

    public void setOnModifyButtonCallback(Consumer<ArrayList<Object>> callback){
        this.onModifyButtonCallback = callback;
    }

    @FXML
    private void handleModifyButtonAction(ActionEvent event){
        if(onModifyButtonCallback != null){

            String lastName = lastNameField.getText().trim();
            String firstName = firstNameField.getText().trim();
            LocalDate birthDate = birthDatePicker.getValue();
            String address = addressField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String degree = DegreePicker.getValue();
           
            String errorMessage = "";

            if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                errorMessage += "Le format de l'email est incorrect.\n";
            }

            if (!phone.isEmpty() && !phone.matches("^0[1-9][0-9]{8}$")) {
                errorMessage += "Le numéro de téléphone doit contenir 10 chiffres et commencer par 0.\n";
            }

            if (errorMessage.isEmpty()) {
                ArrayList<Object> infoList = new ArrayList<>();
                infoList.add(lastName);
                infoList.add(firstName);
                if (birthDate != null){
                    infoList.add(birthDate);
                } else {
                    infoList.add("");
                }
                infoList.add(address);
                infoList.add(email);
                infoList.add(phone);
                infoList.add(degree);

                onModifyButtonCallback.accept(infoList);
                
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
