module laplateformetracker {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires io.github.cdimascio.dotenv.java;
    requires java.sql;

    opens laplateformetracker to javafx.fxml;
    exports laplateformetracker;
    opens laplateformetracker.controllers.login to javafx.fxml;
    
}
