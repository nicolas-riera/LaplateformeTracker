package laplateformetracker.controllers.studentmenu;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import laplateformetracker.User;
import laplateformetracker.controllers.login.LoginController;
import laplateformetracker.models.ManagerModel;
import laplateformetracker.views.StudentMenuView;
import laplateformetracker.views.popup.ChangePasswordPopupView;

public class StudentMenuController {
    private Stage stage;
    private User user;
    private Integer studentId;
    private StudentMenuView studentMenuView;

    public StudentMenuController(Stage stage, User user, Integer studentId){
        this.stage = stage;
        this.user = user;
        this.studentId = studentId;
    }

    public void initController(){
        this.instantiateStudentMenuView();
        if (this.studentMenuView != null) {
            this.setFXMLControllerOnChangePasswordCallback();
            this.setFXMLControllerOnLogoutCallback();
            // Add your other init calls here
        }
    }

    private void instantiateStudentMenuView(){
        try{
            this.studentMenuView = new StudentMenuView(this.stage, this.user);
        } catch (java.io.IOException e) {
            System.err.println(e);
            System.err.println("ERROR INSTANTIATING STUDENT MENU VIEW");         
        }
    }

    private void setFXMLControllerOnModifyStudentCallback(){
        this.studentMenuView.getFxmlController().setOnModifyStudentCallback(() -> {
             System.out.println("Modify student");
        });
    }

    private void setFXMLControllerOnDeleteStudentCallback(){
        this.studentMenuView.getFxmlController().setOnDeleteStudentCallback(() -> {
             System.out.println("Delete student");
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

    private String hashPassword(String password) {
         return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

}

        

        

