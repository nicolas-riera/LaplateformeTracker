package laplateformetracker.controllers.login;

import javafx.stage.Stage;
import laplateformetracker.models.DataBase;
import laplateformetracker.views.LoginView;

public class LoginController {
    private LoginView loginview;

    public LoginController(Stage stage) throws java.io.IOException {

        DataBase database = new DataBase();

        this.loginview = new LoginView(stage);
        this.loginview.getFxmlController().setOnLoginButtonCallback((user, pass) -> {
            System.out.println(user);
            System.out.println(pass);
        }
        );
    }
}