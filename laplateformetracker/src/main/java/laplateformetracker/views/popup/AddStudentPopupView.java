package laplateformetracker.views.popup;

import laplateformetracker.controllers.popup.AddStudentPopupFXMLController;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddStudentPopupView {

    private AddStudentPopupFXMLController addStudentPopupFXMLController;
    public javafx.scene.Parent root;
    private Stage dialog;

    public AddStudentPopupView(Stage primaryStage) throws java.io.IOException {
        this.dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(AddStudentPopupView.class.getResource("/addStudentPopup.fxml"));
        this.root = loader.load();
        this.dialog.initModality(Modality.NONE);
        this.dialog.initOwner(primaryStage);
        Scene dialogScene = new Scene(root, 603, 399);
        this.dialog.setTitle("Ajouter un étudiant");
        this.addStudentPopupFXMLController = loader.getController();
        this.dialog.setScene(dialogScene);
        this.dialog.show();
    }

    public AddStudentPopupFXMLController getFxmlController() {
        return addStudentPopupFXMLController;
    }

    public void close(){
        this.dialog.close();
    }
}
