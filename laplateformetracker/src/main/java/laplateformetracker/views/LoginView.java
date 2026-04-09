package laplateformetracker.views;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginView {

    public static void view(Stage stage) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(LoginView.class.getResource("/loginMenu.fxml"));
        javafx.scene.Parent root = loader.load();
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}
