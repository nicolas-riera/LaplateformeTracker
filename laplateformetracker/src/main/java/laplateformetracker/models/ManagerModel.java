package laplateformetracker.models;

import java.util.ArrayList;

import javafx.scene.chart.PieChart.Data;

public class ManagerModel {
    public void create(String email, String password, String firstName, String lastName, DataBase database) {
        String request = "INSERT INTO manager (email, password, first_name, last_name) VALUES (?, ?, ?, ?)";
        database.runRequest(request, email, password, firstName, lastName);
    }

    public void update(int id, String column, Object data, DataBase database) {
        String request = "UPDATE manager SET " + column + " = ?  WHERE id = ?";
        database.runRequest(request, column, data, id);
    }

    public void delete(int id, DataBase database) {
        String request = "DELETE FROM manager WHERE id = ?";
        database.runRequest(request, id);
    }

    public ArrayList<ArrayList<String>> getInfos(int id, DataBase database) {
        String request = "SELECT * FROM manager WHERE id = ?";
        return database.runRequest(request, id);
    }

    public int getID(String email, DataBase database) {
        String request = "SELECT id FROM manager WHERE email = ?";
        ArrayList<ArrayList<String>> manager_infos = database.runRequest(request, email);

        if (!manager_infos.isEmpty()) {
            String idString = manager_infos.get(0).get(0);
            
            return Integer.parseInt(idString);
        }

        return -1; 
    }

}
