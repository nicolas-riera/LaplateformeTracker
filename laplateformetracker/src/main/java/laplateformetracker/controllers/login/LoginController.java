package laplateformetracker.controllers.login;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import laplateformetracker.models.DataBase;
import laplateformetracker.models.ManagerModel;
import laplateformetracker.models.StudentModel;
import laplateformetracker.views.LoginView;

import java.util.ArrayList;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginController {
    private LoginView loginview;
    private DataBase database;

    public LoginController(Stage stage) throws java.io.IOException {

         this.database = new DataBase();

        this.loginview = new LoginView(stage);
        this.loginview.getFxmlController().setOnLoginButtonCallback((user, pass) -> {

        }
        );
        Rectangle r = new Rectangle(25,25,250,250);
        r.setFill(Color.BLUE);
        this.loginview.addComponent(r);
        
    }

    // public String hashPassword(String password) {
    //     return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    // }

    public Boolean checkPassword(String password, String stored_password) {
        if (password.isEmpty()) {
            return false;
        } else {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), stored_password);
            return result.verified;
        }
    }

    public void loginUser(String email, String password) {
        Integer manager_id = ManagerModel.getID(email, database);
        Integer student_id = StudentModel.getID(email, database);

        if (manager_id != -1) {
            ArrayList<ArrayList<String>> user_infos = ManagerModel.getInfos(manager_id, database);
            if (user_infos.get(0).get(2) == null || user_infos.get(0).get(2).isEmpty()) {
                // Process password creation
            }  else if (checkPassword(password, user_infos.get(0).get(2))) {
                // Instantiate Manager
            } else {
                // Message "Email ou mot de passe incorrect."
            }
        } else if (student_id != -1) {
            ArrayList<ArrayList<String>> user_infos = StudentModel.getInfos(student_id, database);
            if (checkPassword(password, user_infos.get(0).get(2))) {
                // Instantiate Student ?
            } else {
                // Message "Email ou mot de passe incorrect."
            }
        } else {
            // Message "Email ou mot de passe incorrect."
        }
    }
}