@SuppressWarnings("all")
module laplateformetracker {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires io.github.cdimascio.dotenv.java;
    requires java.sql;
    requires bcrypt;

    opens laplateformetracker to javafx.fxml;
    exports laplateformetracker;
    opens laplateformetracker.controllers.login to javafx.fxml;
    exports laplateformetracker.models;
    opens laplateformetracker.controllers.popup to javafx.fxml;
    opens laplateformetracker.controllers.mainMenu to javafx.fxml;
    opens laplateformetracker.controllers.studentmenu to javafx.fxml;
}
