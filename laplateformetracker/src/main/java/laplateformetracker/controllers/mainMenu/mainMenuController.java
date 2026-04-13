package laplateformetracker.controllers.mainMenu;

import javafx.stage.Stage;
import laplateformetracker.User;
import laplateformetracker.views.MainMenuView;

public class MainMenuController {

    private Stage stage;
    private User user;
    private MainMenuView mainmenuview;

    public MainMenuController(Stage stage, User user) throws java.io.IOException {

        this.stage = stage;
        this.user = user;

        this.mainmenuview = new MainMenuView(stage);
    }
}
