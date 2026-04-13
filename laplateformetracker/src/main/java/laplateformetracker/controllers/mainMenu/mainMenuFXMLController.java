package laplateformetracker.controllers.mainMenu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class MainMenuFXMLController {
    @FXML
    private MenuItem addStudentButton;

    @FXML
    private MenuItem logOutButton;

    @FXML
    private MenuItem quitButton;


    @FXML
    public void handleAddStudentAction() {
        System.out.println("Add student");
    }

    @FXML
    public void handleLogOutAction() {
        System.out.println("Log Out");
    }

    @FXML
    public void handleQuitAction() {
        javafx.application.Platform.exit();
        System.exit(0);
    }

}
