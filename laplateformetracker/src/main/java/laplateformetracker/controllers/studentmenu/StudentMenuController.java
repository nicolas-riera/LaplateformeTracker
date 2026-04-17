package laplateformetracker.controllers.studentmenu;

import java.util.ArrayList;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import laplateformetracker.User;
import laplateformetracker.controllers.login.LoginController;
import laplateformetracker.controllers.mainMenu.MainMenuController;
import laplateformetracker.models.GradeModel;
import laplateformetracker.models.ManagerModel;
import laplateformetracker.models.StudentModel;
import laplateformetracker.views.StudentMenuView;
import laplateformetracker.views.popup.AddGradePopupView;
import laplateformetracker.views.popup.ChangePasswordPopupView;
import laplateformetracker.views.popup.ModifyStudentPopupView;

//@SuppressWarnings("unused")
public class StudentMenuController {
    private Stage stage;
    private User user;
    private Integer studentId;
    private StudentMenuView studentMenuView;
    private MainMenuController mainMenuController;

    public StudentMenuController(Stage stage, User user, Integer studentId){
        this.stage = stage;
        this.user = user;
        this.studentId = studentId;
    }

    public void initController(){
        this.instantiateStudentMenuView();
        if (this.studentMenuView != null) {
            this.setFXMLControllerOnModifyStudentCallback();
            this.setFXMLControllerOnDeleteStudentCallback();
            this.setFXMLControllerOnChangePasswordCallback();
            this.setFXMLControllerOnAddGradeCallback();
            this.setFXMLControllerOnLogoutCallback();
            this.setFXMLControllerOnReturnCallback();
            this.setFXMLControllerOnQuitCallback();
        }
    }

    private void instantiateStudentMenuView(){
        try{
            this.studentMenuView = new StudentMenuView(this.stage, this.user, this.studentId);
        } catch (java.io.IOException e) {
            System.err.println(e);
            System.err.println("ERROR INSTANTIATING STUDENT MENU VIEW");         
        }
    }

    private void setFXMLControllerOnModifyStudentCallback(){
        this.studentMenuView.getFxmlController().setOnModifyStudentCallback(() -> {
            try {
                ModifyStudentPopupView modifyStudentPopupView = new ModifyStudentPopupView(stage);
                modifyStudentPopupView.getFxmlController().pullStudentInfos(studentId,user.getDatabase());
                modifyStudentPopupView.getFxmlController().setOnModifyButtonCallback((infoList) -> {
                    String column;
                    for (int i = 0; i < infoList.size(); i++){
                        column = "";
                        if ( !infoList.get(i).isEmpty() || infoList.get(i) == null ){
                            switch (i) {
                                case 0:
                                    column = "last_name";
                                    break;
                                case 1:
                                    column = "first_name";
                                    break;
                                case 2:
                                    column = "date_of_birth";
                                    break;
                                case 3:
                                    column = "address";
                                    break;
                                case 4:
                                    column = "email";
                                    break;
                                case 5:
                                    column = "phone";
                                    break;
                                case 6:
                                    column = "degree";
                                    break;
                                default:
                                    System.err.println("Unreferenced column in StudentMenuController.setFXMLControllerOnModifyStudentCallback()");
                                    break;
                            }
                            if (!column.isEmpty()){
                                StudentModel.update(studentId, column, infoList.get(i), user.getDatabase());
                            }
                        };
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("L'étudiant a bien été mis à jour.");
                    alert.showAndWait();
                    modifyStudentPopupView.close();
                    studentMenuView.getFxmlController().pullStudentInfos();
                }); 
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setFXMLControllerOnDeleteStudentCallback(){
        this.studentMenuView.getFxmlController().setOnDeleteStudentCallback(() -> {
            ArrayList<ArrayList<String>> studentInfos = StudentModel.getInfos(studentId, user.getDatabase());
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Suppression de l'étudiant");
            alert.setHeaderText(String.format("Vous êtes sur le point de supprimer %s %s.", studentInfos.get(0).get(4), studentInfos.get(0).get(5)));
            alert.setContentText("Êtes-vous sûr?");

            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            java.util.Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.YES) {
                StudentModel.update(studentId, "is_deleted", true, user.getDatabase());
                try{
                    instantiateMainMenu(this.user);
                }catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setFXMLControllerOnAddGradeCallback(){
        this.studentMenuView.getFxmlController().setOnAddGradeCallback(() -> {
            try {
                AddGradePopupView addGradePopupView = new AddGradePopupView(stage);
                addGradePopupView.getFxmlController().setOnConfirmButtonCallback((skill, grade) -> {
                    GradeModel.create(studentId, skill, grade, user.getDatabase());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("La note a bien été ajoutée.");
                    alert.showAndWait();
                    this.studentMenuView.getFxmlController().refreshTable();
                    addGradePopupView.close();
                });
            }  catch (java.io.IOException e) {
                e.printStackTrace(); 
            }
        });
    }

    private void setFXMLControllerOnLogoutCallback(){
        this.studentMenuView.getFxmlController().setOnLogOutCallback(() -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Déconnexion");
            alert.setHeaderText("Vous allez être déconnecté.");
            alert.setContentText("Voulez-vous vraiment quitter la session ?");

            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

            java.util.Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    if (user.getDatabase() != null) {
                        user.getDatabase().close();
                    }
                    new LoginController(this.stage);
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setFXMLControllerOnQuitCallback(){
        this.studentMenuView.getFxmlController().setOnQuitCallback(() -> {
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
        });
    }



    private void setFXMLControllerOnChangePasswordCallback(){
        this.studentMenuView.getFxmlController().setOnChangePasswordCallback(() -> {
            Integer manager_id = this.user.getId();
            
            if (manager_id != -1) {
                
                try {
                    ChangePasswordPopupView changePasswordPopupView = new ChangePasswordPopupView(this.stage);
                    changePasswordPopupView.getFxmlController().setOnConfirmButtonCallback((newPassword) -> {
                        ManagerModel.update(manager_id, "password_hash", this.hashPassword(newPassword), user.getDatabase());
                        String updated_db_password = String.format(ManagerModel.getInfos(manager_id, user.getDatabase()).get(0).get(2));
                        
                        if (!(updated_db_password.equals("null") || updated_db_password.equals("\\N"))) {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setContentText("Mot de passe validé et enregistré.");
                            alert.show();
                        }
                        changePasswordPopupView.close();
                    });
                } catch (java.io.IOException e) {
                }  
            }
        });
    }

    private void setFXMLControllerOnReturnCallback(){
        this.studentMenuView.getFxmlController().setOnReturnCallback(() -> {
            try{
                instantiateMainMenu(this.user);
            }catch (java.io.IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void instantiateMainMenu(User user) throws java.io.IOException {
        this.mainMenuController =  new MainMenuController(this.stage, user);
    }

    private String hashPassword(String password) {
         return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}