package laplateformetracker.models;

import java.util.ArrayList;
import java.time.LocalDate;

public class StudentModel {
    public static void create(int manager_id, String email, String first_name, String last_name, LocalDate date_of_birth , String address, String phone, String degree, DataBase database) {
        String request = "INSERT INTO student (manager_id, email, first_name, last_name, date_of_birth, address, phone, degree) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        database.runRequest(request, manager_id, email, first_name, last_name, date_of_birth, address, phone, degree);
    }

    public static void update(int id, String column, Object data, DataBase database) {
        String request = "UPDATE student SET " + column + " = ?  WHERE id = ?";

        database.runRequest(request, data, id);
    }

    public static void delete(int id, DataBase database) {
        String request = "DELETE FROM student WHERE id = ?";
        database.runRequest(request, id);
    }

    public static ArrayList<ArrayList<String>> getInfos(int id, DataBase database) {
        String request = "SELECT * FROM student WHERE id = ?";
        return database.runRequest(request, id);
    }

    public static int getID(String email, DataBase database) {
        String request = "SELECT id FROM student WHERE email = ?";
        ArrayList<ArrayList<String>> student_infos = database.runRequest(request, email);

        if (!student_infos.isEmpty()) {
            String idString = student_infos.get(0).get(0);
            
            return Integer.parseInt(idString);
        }

        return -1; 
    }

    public static ArrayList<ArrayList<String>> getAllInfos(DataBase database) {
        String request = "SELECT * FROM student WHERE NOT is_deleted";
        return database.runRequest(request);
    }

}
