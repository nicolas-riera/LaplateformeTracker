module laplateformetracker {
    requires javafx.controls;
    requires javafx.fxml;

    opens laplateformetracker to javafx.fxml;
    exports laplateformetracker;
}
