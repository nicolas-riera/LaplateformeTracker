package laplateformetracker.models;

import java.util.ArrayList;

public class ManagerModel {
    public void create(String email, String password, String firstName, String lastName, DataBase database) {
        String request = "INSERT INTO manager (email, password, first_name, last_name) VALUES (?, ?, ?, ?)";
        database.runRequest(request, email, password, firstName, lastName);
    }

    public void update(int id, String column, String data, DataBase database) {
        String request = "UPDATE manager SET ? = ?  WHERE id = ?";
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

}
