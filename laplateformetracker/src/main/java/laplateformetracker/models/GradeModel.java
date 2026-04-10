package laplateformetracker.models;

import java.util.ArrayList;

public class GradeModel {
    public static void create(int student_id, String skill, Float grade, DataBase database) {
        String request = "INSERT INTO grade (student_id, skill, grade) VALUES (?, ?, ?)";

        database.runRequest(request, student_id, skill, grade);
    }

    public static void update(int id, String column, Object data, DataBase database) {
        String request = "UPDATE grade SET " + column + " = ?  WHERE id = ?";

        database.runRequest(request, data, id);
    }

    public static void delete(int id, DataBase database) {
        String request = "DELETE FROM grade WHERE id = ?";
        database.runRequest(request, id);
    }

    public static ArrayList<ArrayList<String>> getInfos(int id, DataBase database) {
        String request = "SELECT * FROM grade WHERE id = ?";
        return database.runRequest(request, id);
    }

    public static int getGradeID(int student_id, String skill, Float grade, DataBase database) {
        String request = "SELECT * FROM grade WHERE student_id = ? AND skill = ? AND grade = ?";
        ArrayList<ArrayList<String>> grade_infos = database.runRequest(request, student_id, skill, grade);

         if (!grade_infos.isEmpty()) {
            String idString = grade_infos.get(0).get(0);
            
            return Integer.parseInt(idString);
        }

        return -1; 
    }
    
    public static ArrayList<ArrayList<String>> getStudentGrades(int student_id, DataBase database) {
        String request = "SELECT * FROM grade WHERE student_id = ?";
        return database.runRequest(request, student_id);
    }

}
