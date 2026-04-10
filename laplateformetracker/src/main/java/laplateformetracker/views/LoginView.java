package laplateformetracker.views;

import laplateformetracker.controllers.login.LoginFXMLController;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginView {

    private LoginFXMLController loginFxmlController;

    public LoginView(Stage stage) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("/loginMenu.fxml"));
        javafx.scene.Parent root = loader.load();
        Scene scene = new Scene(root, 1280, 720);
        this.loginFxmlController = loader.getController();
        stage.setScene(scene);
        stage.show();
    }

    public LoginFXMLController getFxmlController() {
        return loginFxmlController;
    }
}
