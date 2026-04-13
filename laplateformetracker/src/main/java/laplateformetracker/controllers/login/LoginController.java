package laplateformetracker.controllers.login;

import javafx.stage.Stage;
import laplateformetracker.models.DataBase;
import laplateformetracker.models.ManagerModel;
import laplateformetracker.models.StudentModel;
import laplateformetracker.views.LoginView;
import laplateformetracker.views.popup.ChangePasswordPopupView;
import laplateformetracker.User;
import laplateformetracker.controllers.mainMenu.MainMenuController;

import java.util.ArrayList;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginController {
    private LoginView loginview;
    private DataBase database;
    private Stage stage;
    private User user;
    private MainMenuController mainMenuController;

    public LoginController(Stage stage) throws java.io.IOException {

        this.stage = stage;
        this.stage.setTitle("LaPlateforme Tracker");;
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

    private User instantiateUser(DataBase database, Integer id, ArrayList<ArrayList<String>> user_infos, boolean isManager){
        this.user = new User(database,
                             id,
                             user_infos.get(0).get(1),
                             user_infos.get(0).get(3),
                             user_infos.get(0).get(4),
                             isManager);
        return this.user;
    }

    private void instantiateMainMenu(User user) throws java.io.IOException {
        this.mainMenuController =  new MainMenuController(this.stage, user);
    }

    public void loginUser(String email, String password) throws java.io.IOException {
        Integer manager_id = ManagerModel.getID(email, database);
        Integer student_id = StudentModel.getID(email, database);
       
        if (manager_id != -1) {
            ArrayList<ArrayList<String>> user_infos = ManagerModel.getInfos(manager_id, database);
            String db_password = user_infos.get(0).get(2);
            if (db_password == "null") {
                ChangePasswordPopupView changePasswordPopupView = new ChangePasswordPopupView(this.stage);
                changePasswordPopupView.getFxmlController().setOnConfirmButtonCallback((newPassword) -> {
                    ManagerModel.update(manager_id, "password_hash", this.hashPassword(newPassword), database);
                    System.out.println(ManagerModel.getInfos(manager_id, database));
                    changePasswordPopupView.close();
                    try {
                        this.instantiateMainMenu(this.instantiateUser(database, manager_id, user_infos, true));
                    } catch (java.io.IOException e) {
                    }
                });
                
            }  else if (checkPassword(password, user_infos.get(0).get(2))) {
                this.instantiateMainMenu(this.instantiateUser(database, manager_id, user_infos, true));
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