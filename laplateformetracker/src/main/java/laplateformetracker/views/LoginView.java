package laplateformetracker.views;

import laplateformetracker.controllers.login.LoginFXMLController;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.Node;

public class LoginView {

    private LoginFXMLController loginFxmlController;
    public javafx.scene.Parent root;

    public LoginView(Stage stage) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("/loginMenu.fxml"));
        this.root = loader.load();
        Scene scene = new Scene(root, 1280, 720);
        this.loginFxmlController = loader.getController();
        stage.setScene(scene);
        stage.show();
    }

    public void addComponent(Node node) {
        if (root instanceof Pane) {
            ((Pane) root).getChildren().add(node);
        }
    }

    public LoginFXMLController getFxmlController() {
        return loginFxmlController;
    }
}
