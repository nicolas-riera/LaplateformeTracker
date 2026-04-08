module laplateformetracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.github.cdimascio.dotenv.java;
    opens laplateformetracker to javafx.fxml;
    exports laplateformetracker;
    
}
