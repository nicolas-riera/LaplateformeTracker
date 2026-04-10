package laplateformetracker.views.popup;

import laplateformetracker.controllers.popup.ChangePasswordPopupFXMLController;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ChangePasswordPopupView {

    private ChangePasswordPopupFXMLController changePasswordPopupFXMLController;
    public javafx.scene.Parent root;

    public ChangePasswordPopupView(Stage primaryStage) throws java.io.IOException {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(ChangePasswordPopupView.class.getResource("/changePasswordPopup.fxml"));
        this.root = loader.load();
        dialog.initModality(Modality.NONE);
        dialog.initOwner(primaryStage);
        Scene dialogScene = new Scene(root, 600, 230);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
