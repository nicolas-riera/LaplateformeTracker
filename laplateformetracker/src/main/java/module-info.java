module laplateformetracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens laplateformetracker to javafx.fxml;
    exports laplateformetracker;
}
