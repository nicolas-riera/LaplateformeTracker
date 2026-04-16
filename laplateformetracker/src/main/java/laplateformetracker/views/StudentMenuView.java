package laplateformetracker.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import laplateformetracker.User;
import laplateformetracker.controllers.studentmenu.StudentMenuFXMLController;

public class StudentMenuView {

    public javafx.scene.Parent root;
    public StudentMenuFXMLController studentMenuFXMLController;

    public StudentMenuView(Stage stage, User user) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(StudentMenuView.class.getResource("/studentMenu.fxml"));
        this.root = loader.load();
        Scene scene = new Scene(root, 1280, 720);
        this.studentMenuFXMLController = loader.getController();
        this.studentMenuFXMLController.setDataBase(user.getDatabase());
        this.studentMenuFXMLController.setUserId(user.getId());
        this.studentMenuFXMLController.refreshTable();
        stage.setScene(scene);
        stage.show();
    }

    public StudentMenuFXMLController getFxmlController() {
        return studentMenuFXMLController;
    }
}
