package laplateformetracker.controllers.studentmenu;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import laplateformetracker.models.DataBase;
import laplateformetracker.models.StudentModel;

public class StudentMenuFXMLController implements Initializable{
    private DataBase database;
    private ArrayList<ArrayList<String>> allGradesData = new ArrayList<>();

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

    // Callbacks
    private Runnable onModifyStudentCallback;

    private Runnable onDeleteStudentCallback;

    private Runnable onChangePasswordCallback;

    private Runnable onLogOutCallback;

    private Runnable onQuitCallback;

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
    private TableView <ArrayList<String>> tableGrade;

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

    public void refreshTable() {
        if (database != null) {
            ArrayList<ArrayList<String>> gradesData = StudentModel.getAllInfos(database); 
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
    public void setOnModifyStudentCallback(Runnable callback){
        this.onModifyStudentCallback = callback;
    }

    @FXML
    public void handleModifyStudentAction() {
        if (onModifyStudentCallback != null) {
            onModifyStudentCallback.run();
        }
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
    public void setOnChangePasswordCallback(Runnable callback) {
        this.onChangePasswordCallback = callback;
    }

    @FXML
    public void handleChangePasswordAction() {
        if (onChangePasswordCallback != null) {
            onChangePasswordCallback.run();
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
