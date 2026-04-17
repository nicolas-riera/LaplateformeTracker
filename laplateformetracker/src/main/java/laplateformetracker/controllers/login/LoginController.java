package laplateformetracker.controllers.login;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import laplateformetracker.models.DataBase;
import laplateformetracker.models.ManagerModel;
import laplateformetracker.models.StudentModel;
import laplateformetracker.views.LoginView;
import laplateformetracker.views.popup.ChangePasswordPopupView;
import laplateformetracker.User;
import laplateformetracker.controllers.mainMenu.MainMenuController;
import laplateformetracker.controllers.studentmenu.StudentMenuController;

import java.util.ArrayList;

import at.favre.lib.crypto.bcrypt.BCrypt;

@SuppressWarnings("unused")
public class LoginController {
    private LoginView loginview;
    private DataBase database;
    private Stage stage;
    private User user;
    private MainMenuController mainMenuController;
    private StudentMenuController studentMenuController;

    public LoginController(Stage stage) throws java.io.IOException {

        this.stage = stage;
        this.stage.setTitle("LaPlateforme Tracker");
        this.database = new DataBase();

        this.loginview = new LoginView(this.stage);
        this.loginview.getFxmlController().setOnLoginButtonCallback((user, pass) -> {
            try {
                loginUser(user, pass);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        );        
    }

    private String hashPassword(String password) {
         return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public Boolean checkPassword(String password, String stored_password) {
        if (password.isEmpty()) {
            return false;
        } else {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), stored_password);
            return result.verified;
        }
    }

    private User instantiateUser(DataBase database, Integer id, ArrayList<ArrayList<String>> user_infos, boolean isManager){
        if (isManager) {
            this.user = new User(database,
                                id,
                                user_infos.get(0).get(1),
                                user_infos.get(0).get(3),
                                user_infos.get(0).get(4),
                                isManager);
        } else {
            this.user = new User(database,
                                id,
                                user_infos.get(0).get(2),
                                user_infos.get(0).get(4),
                                user_infos.get(0).get(5),
                                isManager);
        }
        return this.user;
    }

    private void instantiateMainMenu(User user) throws java.io.IOException {
        this.mainMenuController =  new MainMenuController(this.stage, user);
    }

    private void instantiateStudentMenu(User user) throws java.io.IOException {
        this.studentMenuController = new StudentMenuController(this.stage, user, user.getId());
        this.studentMenuController.initController();
    }

    public void loginUser(String email, String password) throws java.io.IOException {
        Integer manager_id = ManagerModel.getID(email, database);
        Integer student_id = StudentModel.getID(email, database);
        Alert alert = new Alert(AlertType.NONE);
        if (manager_id != -1) {
            ArrayList<ArrayList<String>> user_infos = ManagerModel.getInfos(manager_id, database);
            String db_password = user_infos.get(0).get(2);
            if (db_password.equals("null")) {
                ChangePasswordPopupView changePasswordPopupView = new ChangePasswordPopupView(this.stage);
                changePasswordPopupView.getFxmlController().setOnConfirmButtonCallback((newPassword) -> {
                    ManagerModel.update(manager_id, "password_hash", this.hashPassword(newPassword), database);
                    String updated_db_password = String.format(ManagerModel.getInfos(manager_id, database).get(0).get(2));
                    if (!(updated_db_password.equals("null"))){
                        alert.setAlertType(AlertType.INFORMATION);
                        alert.setContentText("Mot de passe validé et enregistré.");
                        alert.showAndWait();
                        try {
                            this.instantiateMainMenu(this.instantiateUser(database, manager_id, user_infos, true));
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                        }
                    }
                    changePasswordPopupView.close();
                });
                
            }  else if (checkPassword(password, user_infos.get(0).get(2))) {
                this.instantiateMainMenu(this.instantiateUser(database, manager_id, user_infos, true));
            } else {
                alert.setAlertType(AlertType.WARNING);
                alert.setContentText("Email ou mot de passe incorrect.");
                alert.show();
            }
        } else if (student_id != -1) {
            ArrayList<ArrayList<String>> user_infos = StudentModel.getInfos(student_id, database);
            if (checkPassword(password, user_infos.get(0).get(2)) || true) {
                this.instantiateStudentMenu(this.instantiateUser(database, student_id, user_infos, false));
            } else {
                alert.setAlertType(AlertType.WARNING);
                alert.setContentText("Email ou mot de passe incorrect.");
                alert.show();
            }
        } else {
            alert.setAlertType(AlertType.WARNING);
            alert.setContentText("Email ou mot de passe incorrect.");
            alert.show();
        }
    }
}