module laplateformetracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.github.cdimascio.dotenv.java;
    requires java.sql;

    opens laplateformetracker to javafx.fxml;
    exports laplateformetracker;
    
}
