package laplateformetracker.controllers.mainMenu;

import javafx.stage.Stage;
import laplateformetracker.User;
import laplateformetracker.controllers.login.LoginController;
import laplateformetracker.controllers.studentmenu.StudentMenuController;
import laplateformetracker.views.MainMenuView;
import laplateformetracker.models.ManagerModel;
import laplateformetracker.models.StudentModel;

import java.time.LocalDate;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import laplateformetracker.views.popup.AddStudentPopupView;
import laplateformetracker.views.popup.ChangePasswordPopupView;

public class MainMenuController {

    private Stage stage;
    private User user;
    private MainMenuView mainmenuview;
    private StudentMenuController studentMenuController;

    public MainMenuController(Stage stage, User user) throws java.io.IOException {
        this.stage = stage;
        this.user = user;
        this.mainmenuview = new MainMenuView(stage, user);

        this.mainmenuview.getFxmlController().setOnChangePasswordCallback(() -> {
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

        this.mainmenuview.getFxmlController().setOnLogOutCallback(() -> {
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

        this.mainmenuview.getFxmlController().setOnAddStudentCallback(() -> {
            try {
                AddStudentPopupView addStudentPopupView = new AddStudentPopupView(this.stage);
                
                addStudentPopupView.getFxmlController().setOnAddButtonCallback((infos) -> {
                    
                    StudentModel.create(
                        this.user.getId(),
                        infos.get(4),
                        infos.get(1),
                        infos.get(0),
                        LocalDate.parse(infos.get(2)),
                        infos.get(3),
                        infos.get(5),
                        infos.get(6),
                        this.user.getDatabase()
                    );

                    int student_id = StudentModel.getID(infos.get(4), this.user.getDatabase());

                    if (student_id != -1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Succès");
                        alert.setHeaderText(null);
                        alert.setContentText("L'étudiant " + infos.get(1) + " " + infos.get(0) + " a bien été ajouté.");
                        alert.showAndWait();
                        
                        addStudentPopupView.close();

                        mainmenuview.getFxmlController().refreshTable();
                        
                    }
                });
                
            } catch (java.io.IOException e) {
                e.printStackTrace(); 
            }
        });

        this.mainmenuview.getFxmlController().setOnStudentSelectedCallback(() -> {
            System.out.println();
            try{
                this.instantiateStudentMenu(this.user, this.mainmenuview.getFxmlController().getTableStudent().getSelectionModel().getSelectedItem().get(0));
            }catch (java.io.IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void instantiateStudentMenu(User user, String studentId) throws java.io.IOException {
        this.studentMenuController = new StudentMenuController(this.stage, user, Integer.parseInt(studentId));
        this.studentMenuController.initController();
    }

    private String hashPassword(String password) {
         return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

}