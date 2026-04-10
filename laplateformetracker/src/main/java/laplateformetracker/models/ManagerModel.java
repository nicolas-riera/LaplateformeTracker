package laplateformetracker.models;

import java.util.ArrayList;

public class ManagerModel {
    public static void create(String email, String password_hash, String firstName, String lastName, DataBase database) {
        String request = "INSERT INTO manager (email, password_hash, first_name, last_name) VALUES (?, ?, ?, ?)";
        database.runRequest(request, email, password_hash, firstName, lastName);
    }

    public static void update(int id, String column, Object data, DataBase database) {
        String request = "UPDATE manager SET " + column + " = ?  WHERE id = ?";
        database.runRequest(request, data, id);
    }

    public static void delete(int id, DataBase database) {
        String request = "DELETE FROM manager WHERE id = ?";
        database.runRequest(request, id);
    }

    public static ArrayList<ArrayList<String>> getInfos(int id, DataBase database) {
        String request = "SELECT * FROM manager WHERE id = ?";
        return database.runRequest(request, id);
    }

    public static int getID(String email, DataBase database) {
        String request = "SELECT id FROM manager WHERE email = ?";
        ArrayList<ArrayList<String>> manager_infos = database.runRequest(request, email);

        if (!manager_infos.isEmpty()) {
            String idString = manager_infos.get(0).get(0);
            
            return Integer.parseInt(idString);
        }

        return -1; 
    }

}
