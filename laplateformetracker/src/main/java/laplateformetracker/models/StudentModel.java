package laplateformetracker.models;

import java.util.ArrayList;
import java.util.Date;

public class StudentModel {
    public void create(int manager_id, String email, String first_name, String last_name, Date date_of_birth , String address, String phone, String degree, DataBase database) {
        String request = "INSERT INTO student (manager_id, email, first_name, last_name, date_of_birth, address, phone, degree) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        java.sql.Date sqlDate = new java.sql.Date(date_of_birth.getTime());

        database.runRequest(request, manager_id, email, first_name, last_name, sqlDate, address, phone, degree);
    }

    public void update(int id, String column, Object data, DataBase database) {
        String request = "UPDATE student SET " + column + " = ?  WHERE id = ?";

        if (data instanceof java.util.Date) {
        data = new java.sql.Date(((java.util.Date) data).getTime());
        }

        database.runRequest(request, data, id);
    }

    public void delete(int id, DataBase database) {
        String request = "DELETE FROM student WHERE id = ?";
        database.runRequest(request, id);
    }

    public ArrayList<ArrayList<String>> getInfos(int id, DataBase database) {
        String request = "SELECT * FROM student WHERE id = ?";
        return database.runRequest(request, id);
    }

}
