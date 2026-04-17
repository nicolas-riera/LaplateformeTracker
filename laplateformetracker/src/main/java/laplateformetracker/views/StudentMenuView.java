package laplateformetracker.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import laplateformetracker.User;
import laplateformetracker.controllers.studentmenu.StudentMenuFXMLController;

public class StudentMenuView {

    public javafx.scene.Parent root;
    public StudentMenuFXMLController studentMenuFXMLController;

    public StudentMenuView(Stage stage, User user, Integer studentId) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader(StudentMenuView.class.getResource("/studentMenu.fxml"));
        this.root = loader.load();
        Scene scene = new Scene(root, 1280, 720);
        this.studentMenuFXMLController = loader.getController();
        this.studentMenuFXMLController.setDataBase(user.getDatabase());
        this.studentMenuFXMLController.setStudentId(studentId);
        this.studentMenuFXMLController.pullStudentInfos();
        this.studentMenuFXMLController.refreshTable();
        if(!user.getIsManager()){
            this.studentMenuFXMLController.getDeleteStudentButton().setDisable(true);
            this.studentMenuFXMLController.getModifyStudentButton().setDisable(true);
            this.studentMenuFXMLController.getReturnButton().setDisable(true);
            this.studentMenuFXMLController.getAddGradeButton().setDisable(true);
        }else{
            this.studentMenuFXMLController.setOnGradeRightClick();
        }
        stage.setScene(scene);
        stage.show();
    }

    public StudentMenuFXMLController getFxmlController() {
        return studentMenuFXMLController;
    }
}
