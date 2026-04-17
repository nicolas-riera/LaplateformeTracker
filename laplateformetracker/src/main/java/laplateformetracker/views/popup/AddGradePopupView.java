package laplateformetracker.views.popup;

import laplateformetracker.controllers.popup.AddGradePopupFXMLController;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddGradePopupView {

    private AddGradePopupFXMLController addGradePopupFXMLController;
    public javafx.scene.Parent root;
    private Stage dialog;

    public AddGradePopupView(Stage primaryStage) throws java.io.IOException {
        this.dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(AddGradePopupView.class.getResource("/addGradePopup.fxml"));
        this.root = loader.load();
        this.dialog.initModality(Modality.NONE);
        this.dialog.initOwner(primaryStage);
        Scene dialogScene = new Scene(root, 282, 230);
        this.dialog.setTitle("Ajouter note");
        this.addGradePopupFXMLController = loader.getController();
        this.dialog.setScene(dialogScene);
        this.dialog.show();
    }

    public AddGradePopupFXMLController getFxmlController() {
        return addGradePopupFXMLController;
    }

    public void close(){
        this.dialog.close();
    }
}
