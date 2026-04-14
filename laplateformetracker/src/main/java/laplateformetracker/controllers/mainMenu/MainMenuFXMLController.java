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
import javafx.scene.control.Button;
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

    @FXML
    public void handleLogOutAction() {
        System.out.println("Log Out");
    }

    @FXML
    public void handleQuitAction() {
        javafx.application.Platform.exit();
        System.exit(0);
    }

    //Options Menu
    @FXML
    public void handleChangePasswordAction() {
        System.out.println("Change Password");
    }

    //Student tab
    @FXML
    public void handleSearchAction() {
        String search = searchBarField.getText();
        System.out.println(search);
    }

    // Init
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
                ObservableList<ArrayList<String>> items = FXCollections.observableArrayList(studentData);
                tableStudent.setItems(items);
            }
        }
    }

}
