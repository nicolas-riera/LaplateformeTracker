package laplateformetracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import laplateformetracker.models.DataBase;
import laplateformetracker.models.GradeModel;
import laplateformetracker.models.ManagerModel;
import laplateformetracker.models.StudentModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class ModelsTest {

    private DataBase database;

    @BeforeEach
    void setUp() {
        database = new DataBase();
    }

    @AfterEach
    void tearDown() {
    if (database != null) {
       database.close();
    }
    }

    @Test
    void testManager() {
        String email = "test@test.com";

        ManagerModel.create(email, "test1234", "Test_firstname", "Test_lastname", database);

        int id = ManagerModel.getID(email, database);

        System.out.println(id);

        assertNotEquals(-1, id);

        ManagerModel.update((id), "email", "test2@test.com", database);

        ArrayList<ArrayList<String>> info = ManagerModel.getInfos(id, database);

        assertEquals("test2@test.com", info.get(0).get(1));

        ManagerModel.delete(id, database);

        id = ManagerModel.getID(email, database);

        assertEquals(-1, id);
    }

    @Test
    void testStudent() {

        System.out.println( StudentModel.getAllInfos(database));

        StudentModel.create(1, "teststudent@test.com", "Test_student_first", "Test_studen_last", LocalDate.parse("2006-09-08"), "1 Infinite loop" , "0600000069", "1J", database);

        int id = StudentModel.getID("teststudent@test.com", database);

        System.out.println(id);

        assertNotEquals(-1, id);

        ArrayList<ArrayList<String>> info = StudentModel.getInfos(id, database);

        assertEquals("teststudent@test.com", info.get(0).get(2));

        StudentModel.update(id, "address", "2 infinite loop", database);
        StudentModel.update(id, "date_of_birth", LocalDate.parse("2008-10-08"), database);

        info = StudentModel.getInfos(id, database);

        System.out.println(info.get(0).get(6));

        assertEquals("2 infinite loop", info.get(0).get(7));
        assertEquals("2008-10-08", info.get(0).get(6));

        StudentModel.delete(id, database);

        id = StudentModel.getID("teststudent@test.com", database);

        assertEquals(-1, id);
    }

    @Test
    void testGrade() {
        GradeModel.create(1, "Test JAVA + SQL du programme", 16.5f, database);

        int id = GradeModel.getGradeID(1, "Test JAVA + SQL du programme", 16.5f, database);

        System.out.println(id);

        assertNotEquals(-1, id);

        ArrayList<ArrayList<String>> info = GradeModel.getInfos(id, database);

        assertEquals("16.5", info.get(0).get(4));

        ArrayList<ArrayList<String>> grades = GradeModel.getStudentGrades(1, database);

        System.out.println(grades);

        assertFalse(grades.isEmpty());

        GradeModel.update(id, "grade", 17.5f, database);

        info = GradeModel.getInfos(id, database);

        assertEquals("17.5", info.get(0).get(4));

        GradeModel.delete(id, database);

        id = GradeModel.getGradeID(1, "Test JAVA + SQL du programme", 17.5f, database);

        assertEquals(-1, id);
    }
    
}
