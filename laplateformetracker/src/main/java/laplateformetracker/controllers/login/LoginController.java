package laplateformetracker.controllers.login;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import laplateformetracker.models.DataBase;
import laplateformetracker.models.ManagerModel;
import laplateformetracker.models.StudentModel;
import laplateformetracker.views.LoginView;
import laplateformetracker.views.popup.ChangePasswordPopupView;

import java.util.ArrayList;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginController {
    private LoginView loginview;
    private DataBase database;
    private Stage stage;

    public LoginController(Stage stage) throws java.io.IOException {

        this.stage = stage;
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
        //Rectangle r = new Rectangle(25,25,250,250);
        //r.setFill(Color.BLUE);
        //this.loginview.addComponent(r);
        
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

    public void loginUser(String email, String password) throws java.io.IOException{
        Integer manager_id = ManagerModel.getID(email, database);
        Integer student_id = StudentModel.getID(email, database);
        if (manager_id != -1) {
            ArrayList<ArrayList<String>> user_infos = ManagerModel.getInfos(manager_id, database);
            String db_password = user_infos.get(0).get(2);
            if (!(db_password != null && db_password.trim().isEmpty())) {
                ChangePasswordPopupView changePasswordPopupView = new ChangePasswordPopupView(this.stage);
                changePasswordPopupView.getFxmlController().setOnConfirmButtonCallback((newPassword) -> {
                    ManagerModel.update(manager_id, "password_hash", this.hashPassword(newPassword), database);
                    System.out.println(ManagerModel.getInfos(manager_id, database));
                    changePasswordPopupView.close();
                    System.out.println("Instantiation de manager.");
                    System.out.println("Instantiation de mainMenuController.");
                });
            }  else if (checkPassword(password, user_infos.get(0).get(2))) {
                System.out.println("Instantiation de manager.");
                System.out.println("Instantiation de mainMenuController.");
            } else {
                System.out.println("Email ou mot de passe incorrect.");
            }
        } else if (student_id != -1) {
            ArrayList<ArrayList<String>> user_infos = StudentModel.getInfos(student_id, database);
            if (checkPassword(password, user_infos.get(0).get(2))) {
                // Instantiate Student ?
            } else {
                System.out.println("Email ou mot de passe incorrect.");
            }
        } else {
            System.out.println("Email ou mot de passe incorrect.");
        }
    }
}