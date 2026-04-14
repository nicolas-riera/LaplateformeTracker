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
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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
    private Button exportButton;

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

    // Statistic Tab
    @FXML
    private BarChart<String, Number> meanPerDegree;

    @FXML
    private BarChart<String, Number> avgAgePerDegree;

    @FXML
    private PieChart numStudentPerDegree;


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

        boolean isNumeric = search.matches("\\d+");

        for (ArrayList<String> student : allStudentsData) {
            if (isNumeric) {
                String id = student.get(0);
                if (id.equals(search)) {
                    filteredList.add(student);
                }
            } else {
                String firstname = student.get(4).toLowerCase(); 
                String lastname = student.get(5).toLowerCase();  

                if (firstname.contains(search) || lastname.contains(search)) {
                    filteredList.add(student);
                }
            }
        }
        updateDisplay(filteredList);
    }

    @FXML
    public void handleExportCSV() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Exporter les données affichées");

        String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"));
        fileChooser.setInitialFileName("export_étudiants_" + timestamp + ".csv");
        
        fileChooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Fichier CSV", "*.csv"));

        java.io.File file = fileChooser.showSaveDialog(this.tableStudent.getScene().getWindow());

        if (file != null) {
            saveAsCSV(file.getAbsolutePath(), tableStudent.getItems());
        }
    }

    private void saveAsCSV(String fullPath, java.util.List<ArrayList<String>> dataToExport) {
        java.io.File file = new java.io.File(fullPath);
        
        try (java.io.PrintWriter writer = new java.io.PrintWriter(file, java.nio.charset.StandardCharsets.ISO_8859_1)) {
            
            writer.println("ID;Nom complet;Age;Classe;Moyenne;Manager;Telephone;Possede un compte");

            for (ArrayList<String> student : dataToExport) {
                String phone = student.get(8);
                String formattedPhone = (phone != null) ? "\t" + phone : "";

                String managerName = "Inconnu";
                String managerIDStr = student.get(1);
                if (database != null && managerIDStr != null && !managerIDStr.isEmpty() && !managerIDStr.equals("null")) {
                    try {
                        int mID = Integer.parseInt(managerIDStr);
                        ArrayList<ArrayList<String>> mData = laplateformetracker.models.ManagerModel.getInfos(mID, database);
                        if (mData != null && !mData.isEmpty()) {
                            managerName = mData.get(0).get(4) + " " + mData.get(0).get(3);
                        }
                    } catch (Exception e) { managerName = "Erreur"; }
                }

                String ageStr = "N/C";
                try {
                    String birth = student.get(6);
                    if (birth != null && !birth.isEmpty()) {
                        ageStr = java.time.Period.between(java.time.LocalDate.parse(birth), java.time.LocalDate.now()).getYears() + " ans";
                    }
                } catch (Exception e) {}

                String mean = calculateAverage(student.get(0));
                String pwd = student.get(3);
                String hasAccount = (pwd == null || pwd.isEmpty() || pwd.equalsIgnoreCase("null") || pwd.equals("\\N")) ? "Non" : "Oui";

                StringBuilder line = new StringBuilder();
                line.append(student.get(0)).append(";");
                line.append(student.get(5)).append(" ").append(student.get(4)).append(";");
                line.append(ageStr).append(";");
                line.append(student.get(10)).append(";");
                line.append(mean).append(";");
                line.append(managerName).append(";");
                line.append(formattedPhone).append(";");
                line.append(hasAccount);

                writer.println(line.toString());
            }
            
            writer.flush();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Exportation réussi");
            alert.setHeaderText("Fichier enregistré.");
            alert.setContentText("Le fichier " + file.getName() + " a été enregistré avec succès.");
            alert.show();
            
        } catch (java.io.IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur d'export");
            alert.setHeaderText("Impossible d'enregistrer le fichier");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
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
                updateStatistics();
            }
        }
    }

    private void updateDisplay(ArrayList<ArrayList<String>> dataList) {
        ObservableList<ArrayList<String>> items = FXCollections.observableArrayList(dataList);
        tableStudent.setItems(items);
        studentNumberLabel.setText(dataList.size() + " étudiants");
    }

    // Statistic Tab
    @SuppressWarnings("unchecked")
    private void updateStatistics() {
        if (numStudentPerDegree == null || meanPerDegree == null || avgAgePerDegree == null) return;
        if (allStudentsData == null || allStudentsData.isEmpty()) return;

        java.util.Map<String, Integer> countPerDegree = new java.util.HashMap<>();
        java.util.Map<String, Double> sumMeanPerDegree = new java.util.HashMap<>();
        java.util.Map<String, Integer> countMeanPerDegree = new java.util.HashMap<>();
        java.util.Map<String, Double> sumAgePerDegree = new java.util.HashMap<>();
        java.util.Map<String, Integer> countAgePerDegree = new java.util.HashMap<>();

        for (ArrayList<String> student : allStudentsData) {
            String degree = student.get(10);
            String studentId = student.get(0);
            String birthDate = student.get(6);

            countPerDegree.put(degree, countPerDegree.getOrDefault(degree, 0) + 1);

            String meanStr = calculateAverage(studentId).replace(",", ".");
            if (!meanStr.equals("N/C") && !meanStr.equals("Error")) {
                double val = Double.parseDouble(meanStr);
                sumMeanPerDegree.put(degree, sumMeanPerDegree.getOrDefault(degree, 0.0) + val);
                countMeanPerDegree.put(degree, countMeanPerDegree.getOrDefault(degree, 0) + 1);
            }

            try {
                if (birthDate != null && !birthDate.isEmpty()) {
                    int age = java.time.Period.between(java.time.LocalDate.parse(birthDate), java.time.LocalDate.now()).getYears();
                    sumAgePerDegree.put(degree, sumAgePerDegree.getOrDefault(degree, 0.0) + age);
                    countAgePerDegree.put(degree, countAgePerDegree.getOrDefault(degree, 0) + 1);
                }
            } catch (Exception e) {}
        }

        javafx.collections.ObservableList<javafx.scene.chart.PieChart.Data> pieData = javafx.collections.FXCollections.observableArrayList();
        countPerDegree.forEach((d, c) -> pieData.add(new javafx.scene.chart.PieChart.Data(d, c)));
        numStudentPerDegree.setData(pieData);

        javafx.scene.chart.XYChart.Series<String, Number> s1 = new javafx.scene.chart.XYChart.Series<>();
        s1.setName("Moyenne");
        sumMeanPerDegree.forEach((d, sum) -> s1.getData().add(new javafx.scene.chart.XYChart.Data<>(d, sum / countMeanPerDegree.get(d))));
        meanPerDegree.getData().setAll(s1);

        int i = 0;
        for (XYChart.Data<String, Number> data : s1.getData()) {
            Node bar = data.getNode();
            bar.getStyleClass().add("default-color" + (i % 8)); 
            i++;
        }

        javafx.scene.chart.XYChart.Series<String, Number> s2 = new javafx.scene.chart.XYChart.Series<>();
        s2.setName("Âge moyen");
        sumAgePerDegree.forEach((d, sum) -> s2.getData().add(new javafx.scene.chart.XYChart.Data<>(d, sum / countAgePerDegree.get(d))));
        avgAgePerDegree.getData().setAll(s2);   

        i = 0;
        for (XYChart.Data<String, Number> data : s2.getData()) {
            Node bar = data.getNode();
            bar.getStyleClass().add("default-color" + (i % 8)); 
            i++;
        }

        meanPerDegree.setLegendVisible(false);
        avgAgePerDegree.setLegendVisible(false);
        numStudentPerDegree.setLegendVisible(false);
    }

}
