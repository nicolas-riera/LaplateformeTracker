package laplateformetracker.views.popup;

import laplateformetracker.controllers.popup.ChangePasswordPopupFXMLController;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangePasswordPopupView {

    private ChangePasswordPopupFXMLController changePasswordPopupFXMLController;
    public javafx.scene.Parent root;
    private Stage dialog;

    public ChangePasswordPopupView(Stage primaryStage) throws java.io.IOException {
        this.dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(ChangePasswordPopupView.class.getResource("/changePasswordPopup.fxml"));
        this.root = loader.load();
        this.dialog.initModality(Modality.NONE);
        this.dialog.initOwner(primaryStage);
        Scene dialogScene = new Scene(root, 600, 230);
        this.changePasswordPopupFXMLController = loader.getController();
        this.dialog.setScene(dialogScene);
        this.dialog.show();
    }

    public ChangePasswordPopupFXMLController getFxmlController() {
        return changePasswordPopupFXMLController;
    }

    public void close(){
        this.dialog.close();
    }
}
