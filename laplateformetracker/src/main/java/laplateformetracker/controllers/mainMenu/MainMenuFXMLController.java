package laplateformetracker.controllers.mainMenu;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import laplateformetracker.models.DataBase;
import laplateformetracker.models.GradeModel;
import laplateformetracker.models.ManagerModel;
import laplateformetracker.models.StudentModel;
import javafx.scene.control.Label;

public class MainMenuFXMLController implements Initializable {

    // Variables

    private DataBase database;
    private ArrayList<ArrayList<String>> allStudentsData = new ArrayList<>();

    // File Menu
    @FXML
    private MenuItem addStudentButton;

    @FXML
    private MenuItem logOutButton;

    @FXML
    private MenuItem quitButton;

    //Options Menu
    @FXML
    private MenuItem changePasswordButton;

    private Runnable onChangePasswordCallback;

    private Runnable onLogOutCallback;

    //Student tab
    @FXML
    private Label studentNumberLabel;

    @FXML
    private TextField searchBarField;

    @FXML
    private Button searchButton;

    @FXML
    private TableView<ArrayList<String>> tableStudent;

    @FXML
    private TableColumn<ArrayList<String>, String> colID;
    @FXML
    private TableColumn<ArrayList<String>, String> colName;
    @FXML
    private TableColumn<ArrayList<String>, String> colAge;
    @FXML
    private TableColumn<ArrayList<String>, String> colDegree;
    @FXML
    private TableColumn<ArrayList<String>, String> colMean;
    @FXML
    private TableColumn<ArrayList<String>, String> colManager;
    @FXML
    private TableColumn<ArrayList<String>, String> colPhone;
    @FXML
    private TableColumn<ArrayList<String>, String> colAccount;   


    // Methods

    // File Menu
    @FXML
    public void handleAddStudentAction() {
        System.out.println("Add student");
    }

    public void setOnLogOutCallback(Runnable callback) {
        this.onLogOutCallback = callback;
    }

    @FXML
    public void handleLogOutAction() {
        if (onLogOutCallback != null) {
            onLogOutCallback.run();
        }
    }

    @FXML
    public void handleQuitAction() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Quitter");
        alert.setHeaderText("Vous allez quitter le programme.");
        alert.setContentText("Voulez-vous vraiment quitter le programme ?");

        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        java.util.Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.YES) {
            javafx.application.Platform.exit();
            System.exit(0);
        }
    
    }

    //Options Menu
    @FXML
    public void handleChangePasswordAction() {
        if (onChangePasswordCallback != null) {
            onChangePasswordCallback.run();
        }
    }

    public void setOnChangePasswordCallback(Runnable callback) {
        this.onChangePasswordCallback = callback;
    }

    //Student tab
    @FXML
    public void handleSearchAction() {
        String search = searchBarField.getText().toLowerCase().trim();

        if (search.isEmpty()) {
            updateDisplay(allStudentsData); 
            return;
        }

        ArrayList<ArrayList<String>> filteredList = new ArrayList<>();

        for (ArrayList<String> student : allStudentsData) {
            String firstname = student.get(4).toLowerCase(); 
            String lastname = student.get(5).toLowerCase();  

            if (firstname.contains(search) || lastname.contains(search)) {
                filteredList.add(student);
            }
        }

        updateDisplay(filteredList);
    }


    // Init
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        searchBarField.textProperty().addListener((observable, oldValue, newValue) -> {
            handleSearchAction();
        });

        colID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));

        colName.setCellValueFactory(data -> {
            String firstname = data.getValue().get(4);
            String lastname = data.getValue().get(5);
            return new SimpleStringProperty(lastname + " " + firstname);
        });

        colAge.setCellValueFactory(data -> {
            String birthDateString = data.getValue().get(6); 
            String displayAge;

            try {
                if (birthDateString != null && !birthDateString.isEmpty()) {
                    LocalDate birthDate = LocalDate.parse(birthDateString);
                    
                    int age = Period.between(birthDate, LocalDate.now()).getYears();
                    
                    displayAge = age + " ans";
                } else {
                    displayAge = "N/C";
                }
            } catch (DateTimeParseException e) {
                displayAge = "Format invalide";
            }

            return new SimpleStringProperty(displayAge);
        });

        colDegree.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(10)));

        colMean.setCellValueFactory(data -> {
            String studentId = data.getValue().get(0);
            
            if (database == null) {
                return new SimpleStringProperty("...");
            }
            
            String mean = calculateAverage(studentId);
            return new SimpleStringProperty(mean);
        });

        colManager.setCellValueFactory(data -> {
            String managerIDStr = data.getValue().get(1);

            if (database == null || managerIDStr == null || managerIDStr.equals("null") || managerIDStr.isEmpty()) {
                return new SimpleStringProperty("Inconnu");
            }

            try {
                int managerID = Integer.parseInt(managerIDStr);
                ArrayList<ArrayList<String>> managerData = ManagerModel.getInfos(managerID, database);

                if (managerData == null || managerData.isEmpty()) {
                    return new SimpleStringProperty("Inconnu");
                }

                ArrayList<String> firstRow = managerData.get(0);
                String firstname = firstRow.get(3);
                String lastname = firstRow.get(4);
                
                return new SimpleStringProperty(lastname + " " + firstname);

            } catch (NumberFormatException e) {
                return new SimpleStringProperty("ID Invalide");
            } catch (Exception e) {
                return new SimpleStringProperty("Erreur");
            }
        });

        colPhone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(8)));

        colAccount.setCellValueFactory(data -> {
            String passwordStudent = data.getValue().get(3);

            if (passwordStudent == null || 
                    passwordStudent.isEmpty() || 
                    passwordStudent.equalsIgnoreCase("null") || 
                    passwordStudent.equals("\\N")) {
                    
                    return new SimpleStringProperty("Non");
                } else {
                    return new SimpleStringProperty("Oui");
                }
            });
    }

    private String calculateAverage(String studentIdStr) {
        try {
            int studentId = Integer.parseInt(studentIdStr);
            ArrayList<ArrayList<String>> gradesData = GradeModel.getStudentGrades(studentId, database);

            if (gradesData == null || gradesData.isEmpty()) {
                return "N/C";
            }

            double sum = 0;
            int count = 0;

            for (ArrayList<String> row : gradesData) {
                double grade = Double.parseDouble(row.get(4)); 
                sum += grade;
                count++;
            }

            if (count == 0) return "N/C";
            return String.format("%.2f", sum / count);

        } catch (Exception e) {
            return "Error";
        }
    }

    public void setDataBase(DataBase db) {
        this.database = db;         
        refreshTable();
    }

    public void refreshTable() {
        if (database != null) {
            ArrayList<ArrayList<String>> studentData = StudentModel.getAllInfos(database); 
            if (studentData != null) {
                this.allStudentsData = studentData; 
                updateDisplay(allStudentsData);
            }
        }
    }

    private void updateDisplay(ArrayList<ArrayList<String>> dataList) {
        ObservableList<ArrayList<String>> items = FXCollections.observableArrayList(dataList);
        tableStudent.setItems(items);
        studentNumberLabel.setText(dataList.size() + " étudiants");
    }

}
