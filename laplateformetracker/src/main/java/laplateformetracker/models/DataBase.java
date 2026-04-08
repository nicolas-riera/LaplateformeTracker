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

    public ArrayList<ArrayList<String>> runRequest(String request) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        try {
        Statement st = conn.createStatement();

        boolean isResultSet = st.execute(request);

        if (isResultSet) {

            ResultSet rs = st.getResultSet();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
        
            while (rs.next()) {
                ArrayList<String> line = new ArrayList<>();
                for (int i = 1 ; i <= columnCount ; i++) {
                    line.add(rs.getString(i));
                }
                result.add(line);
            
            }
            rs.close();
        }
        st.close();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
            System.out.println("Code d'état : " + e.getSQLState());
        } catch (java.lang.NullPointerException e) {
            System.out.println("Le pointeur est nul.");
        }
        return result;
    }
}
