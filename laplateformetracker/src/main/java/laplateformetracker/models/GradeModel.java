package laplateformetracker.models;

import java.util.ArrayList;

public class GradeModel {
    public void create(int student_id, String skill, Float grade, DataBase database) {
        String request = "INSERT INTO grade (student_id, skill, grade) VALUES (?, ?, ?)";

        database.runRequest(request, student_id, skill, grade);
    }

    public void update(int id, String column, Object data, DataBase database) {
        String request = "UPDATE grade SET " + column + " = ?  WHERE id = ?";

        database.runRequest(request, data, id);
    }

    public void delete(int id, DataBase database) {
        String request = "DELETE FROM grade WHERE id = ?";
        database.runRequest(request, id);
    }

    public ArrayList<ArrayList<String>> getInfos(int id, DataBase database) {
        String request = "SELECT * FROM grade WHERE id = ?";
        return database.runRequest(request, id);
    }

}
