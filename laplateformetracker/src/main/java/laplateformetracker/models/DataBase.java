package laplateformetracker.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import io.github.cdimascio.dotenv.Dotenv;

public class DataBase {
    Dotenv dotenv = null;
    Connection conn;

    public DataBase() {
        try {
            dotenv = Dotenv.load();
        } catch (Exception e) {
            System.out.println("Erreur avec le .env : " + e);
        }

        if (dotenv != null) {
            try {
            String url = "jdbc:postgresql://"+ dotenv.get("POSTGRE_IP") + ":" + dotenv.get("POSTGRE_PORT") + "/laplateforme_tracker";
            Properties props = new Properties();
            props.setProperty("user", dotenv.get("POSTGRE_USER"));
            props.setProperty("password", dotenv.get("POSTGRE_PASSWORD"));
            this.conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
                System.out.println("Code d'état : " + e.getSQLState());
            }
        } 
    }

    public ArrayList<ArrayList<String>> runRequest(String request, Object... params) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        if (this.conn == null) {
        System.out.println("Erreur : La connexion à la base de données n'est pas établie.");
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
            System.out.println("Erreur SQL : " + e.getMessage());
            System.out.println("Code d'état : " + e.getSQLState());
            System.out.println("Requête : " + request);
        } catch (java.lang.NullPointerException e) {
            System.out.println("Le pointeur est nul.");
        }
        return result;
    }

    public void close() {
    try {
        if (this.conn != null && !this.conn.isClosed()) {
            this.conn.close();
        }
    } catch (SQLException e) {
        System.out.println("Erreur lors de la fermeture : " + e.getMessage());
    }
    }
}
