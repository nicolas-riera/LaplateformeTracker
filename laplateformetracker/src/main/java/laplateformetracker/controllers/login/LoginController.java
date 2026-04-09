package laplateformetracker.controllers.login;

import javafx.stage.Stage;
import laplateformetracker.models.DataBase;
import laplateformetracker.views.LoginView;

public class LoginController {

    public LoginController(Stage stage) throws java.io.IOException {

        DataBase database = new DataBase();

        LoginView.view(stage);
    }
    
}
