package laplateformetracker.controllers.mainMenu;

import javafx.stage.Stage;
import laplateformetracker.User;
import laplateformetracker.views.MainMenuView;
import laplateformetracker.models.ManagerModel;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import laplateformetracker.views.popup.ChangePasswordPopupView;

public class MainMenuController {

    private Stage stage;
    private User user;
    private MainMenuView mainmenuview;

    public MainMenuController(Stage stage, User user) throws java.io.IOException {
        this.stage = stage;
        this.user = user;
        this.mainmenuview = new MainMenuView(stage, user);

        this.mainmenuview.getFxmlController().setOnChangePasswordCallback(() -> {
            Integer manager_id = this.user.getId();
            
            if (manager_id != -1) {
                
                try {
                    ChangePasswordPopupView changePasswordPopupView = new ChangePasswordPopupView(this.stage);
                    changePasswordPopupView.getFxmlController().setOnConfirmButtonCallback((newPassword) -> {
                        ManagerModel.update(manager_id, "password_hash", this.hashPassword(newPassword), user.getDatabase());
                        String updated_db_password = String.format(ManagerModel.getInfos(manager_id, user.getDatabase()).get(0).get(2));
                        
                        if (!(updated_db_password.equals("null") || updated_db_password.equals("\\N"))) {
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setContentText("Mot de passe validé et enregistré.");
                            alert.show();
                        }
                        changePasswordPopupView.close();
                    });
                } catch (java.io.IOException e) {
                }  
            }
        });
    }

    private String hashPassword(String password) {
         return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

}