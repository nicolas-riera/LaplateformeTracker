package laplateformetracker.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import laplateformetracker.User;
import laplateformetracker.controllers.mainMenu.MainMenuFXMLController;

public class MainMenuView {

    public javafx.scene.Parent root;
    public MainMenuFXMLController mainMenuFXMLController;

    public MainMenuView(Stage stage, User user) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(MainMenuView.class.getResource("/mainMenu.fxml"));
        this.root = loader.load();
        Scene scene = new Scene(root, 1280, 720);
        this.mainMenuFXMLController = loader.getController();
        
        this.mainMenuFXMLController.setDataBase(user.getDatabase());

        stage.setScene(scene);
        stage.show();
    }
}
