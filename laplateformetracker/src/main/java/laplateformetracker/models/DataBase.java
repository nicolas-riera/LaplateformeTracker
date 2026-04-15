package laplateformetracker.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

public class DataBase {
    Dotenv dotenv = null;
    Connection conn;

    public DataBase() {
        try {
            dotenv = Dotenv.load();
        } catch (Exception e) {
            TextArea textArea = new TextArea();
            textArea.setText(e.getMessage());
            textArea.setWrapText(true);
            textArea.setEditable(false);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Erreur avec le .env : ");
            alert.getDialogPane().setContent(textArea);
            alert.show();
        }

        if (dotenv != null) {
            try {
            String url = "jdbc:postgresql://"+ dotenv.get("POSTGRE_IP") + ":" + dotenv.get("POSTGRE_PORT") + "/laplateforme_tracker";
            Properties props = new Properties();
            props.setProperty("user", dotenv.get("POSTGRE_USER"));
            props.setProperty("password", dotenv.get("POSTGRE_PASSWORD"));
            props.setProperty("charSet", "UTF-8");
            this.conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                TextArea textArea = new TextArea();
                textArea.setText(String.format("""
                        Erreur SQL : %s
                        Code d'état : %s
                        """, e.getMessage(), e.getSQLState()));
                textArea.setWrapText(true);
                textArea.setEditable(false);
                Alert alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.getDialogPane().setContent(textArea);
                alert.showAndWait();
                exitIfConnectionError(e);
            }
        } 
    }

    public ArrayList<ArrayList<String>> runRequest(String request, Object... params) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        if (this.conn == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("La connexion à la base de données n'est pas établie.");
            alert.show();
            return result;
        }

    try (PreparedStatement pst = conn.prepareStatement(request)) {

        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
        }

        boolean isResultSet = pst.execute(); 

        if (isResultSet) {
            try (ResultSet rs = pst.getResultSet()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                while (rs.next()) {
                    ArrayList<String> line = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++) {
                        Object value = rs.getObject(i);
                        line.add(value != null ? value.toString() : "null");
                    }
                    result.add(line);
                }
            }
        }
    } catch (SQLException e) {
        TextArea textArea = new TextArea();
        textArea.setText(String.format("""
            Erreur SQL : %s
            Code d'état : %s""",e.getMessage(),e.getSQLState()));
        textArea.setWrapText(true);
        textArea.setEditable(false);
        System.err.printf("Requete : %s", request);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();  
        
        exitIfConnectionError(e);

        } catch (java.lang.NullPointerException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Le pointeur est nul.");
            alert.showAndWait();
        }
        return result;
    }

    public void close() {
        try {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
            }
        } catch (SQLException e) {
            TextArea textArea = new TextArea();
            textArea.setText(e.getMessage());
            textArea.setWrapText(true);
            textArea.setEditable(false);
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Erreur lors de la fermeture : ");
            alert.getDialogPane().setContent(textArea);
            alert.show();
        }
    }

    private void exitIfConnectionError(SQLException e) {
        String sqlState = e.getSQLState();
        if (sqlState != null) {
            sqlState = sqlState.trim();
            if (sqlState.startsWith("08")) {
                javafx.application.Platform.exit();
                System.exit(0);
            }
        }
    }
}
