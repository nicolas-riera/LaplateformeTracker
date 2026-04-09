package laplateformetracker;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import laplateformetracker.controllers.*;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        
        @SuppressWarnings("unused")
        LoginController loginController = new LoginController(stage);
        
    }
}