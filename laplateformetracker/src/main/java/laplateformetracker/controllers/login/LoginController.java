package laplateformetracker.controllers.login;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import laplateformetracker.models.DataBase;
import laplateformetracker.views.LoginView;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginController {
    private LoginView loginview;

    public LoginController(Stage stage) throws java.io.IOException {

        DataBase database = new DataBase();

        this.loginview = new LoginView(stage);
        this.loginview.getFxmlController().setOnLoginButtonCallback((user, pass) -> {
            System.out.println(user);
            System.out.println(hashPassword(pass));
        }
        );
        Rectangle r = new Rectangle(25,25,250,250);
        r.setFill(Color.BLUE);
        this.loginview.addComponent(r);
        
    }

    public String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public Boolean checkPassword(String email, String hashed_password) {
        String stored_password;
        return false;
    }
}