package laplateformetracker.controllers.studentmenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import laplateformetracker.models.DataBase;
import laplateformetracker.models.GradeModel;
import laplateformetracker.models.ManagerModel;
import laplateformetracker.models.StudentModel;

public class StudentMenuFXMLController implements Initializable {

    private DataBase database;
    private int userId;
    private ArrayList<ArrayList<String>> allGradesData = new ArrayList<>();
    private ArrayList<ArrayList<String>> studentInfos;

    // File Menu
    @FXML
    private MenuItem modifyStudentButton;
    @FXML
    private MenuItem deleteStudentButton;
    @FXML
    private MenuItem logOutButton;
    @FXML
    private MenuItem quitButton;

    // Options Menu
    @FXML
    private MenuItem changePasswordButton;

    @FXML
    private Button returnButton;

    // Callbacks
    private Runnable onModifyStudentCallback;
    private Runnable onDeleteStudentCallback;
    private Runnable onChangePasswordCallback;
    private Runnable onLogOutCallback;
    private Runnable onQuitCallback;
    private Runnable onReturnCallback;

    // Student infos
    @FXML
    private Label userEmailLabel;
    @FXML
    private Label userFirstNameLabel;
    @FXML
    private Label userLastNameLabel;
    @FXML
    private Label userBirthDateLabel;
    @FXML
    private Label userAddressLabel;
    @FXML
    private Label userPhoneLabel;
    @FXML
    private Label userDegreeLabel;
    @FXML
    private Label userManagerLabel;

    @FXML
    private TableView<ArrayList<String>> tableGrade;
    @FXML
    private TableColumn<ArrayList<String>, String> colId;
    @FXML
    private TableColumn<ArrayList<String>, String> colDate;
    @FXML
    private TableColumn<ArrayList<String>, String> colSkill;
    @FXML
    private TableColumn<ArrayList<String>, String> colGrade;

    // Methods
    public void setDataBase(DataBase db) {
        this.database = db;    
    }

    public void setStudentId(int id) {
        this.userId = id;    
    }

    public void pullStudentInfos(){
        this.studentInfos = StudentModel.getInfos(userId, database);
        
        userEmailLabel.setText(studentInfos.get(0).get(2));
        userFirstNameLabel.setText(studentInfos.get(0).get(4));
        userLastNameLabel.setText(studentInfos.get(0).get(5));
        userBirthDateLabel.setText(studentInfos.get(0).get(6));
        userAddressLabel.setText(studentInfos.get(0).get(7));
        userPhoneLabel.setText(studentInfos.get(0).get(8));
        userDegreeLabel.setText(studentInfos.get(0).get(10));
        ArrayList<ArrayList<String>> managerInfos = ManagerModel.getInfos(Integer.parseInt(studentInfos.get(0).get(1)), database);
        userManagerLabel.setText(String.format("%s %s", managerInfos.get(0).get(3), managerInfos.get(0).get(4)));

        String pwd = studentInfos.get(0).get(3);
        if (pwd.isEmpty() || pwd.equalsIgnoreCase("null") || pwd.equals("\\N")) {
            changePasswordButton.setText("Créer un compte");
        } else {
            changePasswordButton.setText("Changer le mot de passe");
        }
    }

    public void refreshTable() {
        if (database != null) {
            ArrayList<ArrayList<String>> gradesData = GradeModel.getStudentGrades(userId, database); 
            if (gradesData != null) {
                this.allGradesData = gradesData; 
                updateDisplay(allGradesData);
            }
        }
    }

    private void updateDisplay(ArrayList<ArrayList<String>> dataList) {
        ObservableList<ArrayList<String>> items = FXCollections.observableArrayList(dataList);
        tableGrade.setItems(items);
    }

    // File Menu
    public MenuItem getModifyStudentButton(){
        return modifyStudentButton;
    }
    public void setOnModifyStudentCallback(Runnable callback){
        this.onModifyStudentCallback = callback;
    }
    @FXML
    public void handleModifyStudentAction() {
        if (onModifyStudentCallback != null) {
            onModifyStudentCallback.run();
        }
    }

    public MenuItem getDeleteStudentButton(){
        return deleteStudentButton;
    }
    public void setOnDeleteStudentCallback(Runnable callback){
        this.onDeleteStudentCallback = callback;
    }
    @FXML
    public void handleDeleteStudentAction() {
        if (onDeleteStudentCallback != null) {
            onDeleteStudentCallback.run();
        }
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

    public void setOnQuitCallback(Runnable callback) {
        this.onQuitCallback = callback;
    }
    @FXML
    public void handleQuitAction() {
        if (onQuitCallback != null) {
            onQuitCallback.run();
        }
    }

    //Options Menu
    public MenuItem getChangePasswordButton(){
        return changePasswordButton;
    }
    public void setOnChangePasswordCallback(Runnable callback) {
        this.onChangePasswordCallback = callback;
    }
    @FXML
    public void handleChangePasswordAction() {
        if (onChangePasswordCallback != null) {
            onChangePasswordCallback.run();
        }
    }

    public Button getReturnButton(){
        return returnButton;
    }
    public void setOnReturnCallback(Runnable callback){
        this.onReturnCallback = callback;
    }
    @FXML
    public void handleReturnAction(){
        if (onReturnCallback != null){
            onReturnCallback.run();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        colDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        colSkill.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        colGrade.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
    }
}