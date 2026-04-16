package laplateformetracker.views.popup;

import laplateformetracker.controllers.popup.ModifyStudentPopupFXMLController;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifyStudentPopupView {

    private ModifyStudentPopupFXMLController modifyStudentPopupFXMLController;
    public javafx.scene.Parent root;
    private Stage dialog;

    public ModifyStudentPopupView(Stage primaryStage) throws java.io.IOException {
        this.dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(ModifyStudentPopupView.class.getResource("/modifyStudentPopup.fxml"));
        this.root = loader.load();
        this.dialog.initModality(Modality.NONE);
        this.dialog.initOwner(primaryStage);
        Scene dialogScene = new Scene(root, 603, 399);
        this.dialog.setTitle("Modifier un étudiant");
        this.modifyStudentPopupFXMLController = loader.getController();
        this.dialog.setScene(dialogScene);
        this.dialog.show();
    }

    public ModifyStudentPopupFXMLController getFxmlController() {
        return modifyStudentPopupFXMLController;
    }

    public void close(){
        this.dialog.close();
    }
}
