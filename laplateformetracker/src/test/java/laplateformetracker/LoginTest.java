package laplateformetracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.stage.Stage;
import laplateformetracker.controllers.login.LoginController;
import laplateformetracker.models.DataBase;

@ExtendWith(ApplicationExtension.class)
class LoginTest {

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */

    LoginController loginController;

    @Start
    private void start(Stage stage) {
        try{
            this.loginController = new LoginController(stage);
        } catch (java.io.IOException e){
        }
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    public void hashPasswordTest(FxRobot robot) {
        try{
            Method hashPassword = LoginController.class.getDeclaredMethod("hashPassword", String.class);

            // Make the private method accessible
            hashPassword.setAccessible(true);

            // Invoke the private method
            String hashPasswordResult = (String) hashPassword.invoke(this.loginController, "#GeeksForGeeks123@");
            assertTrue(hashPasswordResult.length() == 60 && hashPasswordResult.startsWith("$"));

        } catch (Exception e) {
           fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    public void checkPasswordTest(FxRobot robot){
        try{
            Method checkPassword = LoginController.class.getDeclaredMethod("checkPassword", String.class,String.class);

            // Make the private method accessible
            checkPassword.setAccessible(true);

            // Invoke the private method
            Boolean empty = (Boolean) checkPassword.invoke(this.loginController, "", "$2a$12$qL9fZrjCqoDdCCipQ0JUvOWI9tjCFTaruxAfqEgA0hftyKhBwXV0O");
            Boolean fits = (Boolean) checkPassword.invoke(this.loginController, "#GeeksForGeeks123@", "$2a$12$qL9fZrjCqoDdCCipQ0JUvOWI9tjCFTaruxAfqEgA0hftyKhBwXV0O");
            assertFalse(empty);
            assertTrue(fits);

        } catch (Exception e) {
           fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    public void instantiateUserTest(FxRobot robot) {
        //Can't mock dataBase because it violates several SOLID design principles
        //Mockito works best when classes are designed for "Dependency Injection," but DataBase is currently "tightly coupled" to its dependencies.
        //We should remove Dotenv.load() and DriverManager.getConnection() static methods and JavaFX alerts components from DataBase constructor
        //DataBase dataBase = Mockito.mock(DataBase.class);
        DataBase database = new DataBase();
        
        ArrayList<String> innerList = new ArrayList<>();
        innerList.add("1");
        innerList.add("mokujin@dummy.io");
        innerList.add("dummypass");
        innerList.add("Mokujin");
        innerList.add("Dummy");
        
        ArrayList<ArrayList<String>> userInfos = new ArrayList<>();
        userInfos.add(innerList);

        try {
            Method method = LoginController.class.getDeclaredMethod("instantiateUser", 
                DataBase.class, Integer.class, ArrayList.class, boolean.class);
            method.setAccessible(true);

            User user = (User) method.invoke(this.loginController, database, 1, userInfos, true);

            assertNotNull(user, "User should have been instantiated");
            assertEquals(1, user.getId());
            assertEquals("mokujin@dummy.io", user.getEmail());

        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    public void instantiateMainMenuTest(FxRobot robot){
        //User user = Mockito.mock(User.class);
        DataBase database = new DataBase();
        User user = new User(database, 1, "mokujin@dummy.io", "Mokujin", "Dummy", true);

    }
}
